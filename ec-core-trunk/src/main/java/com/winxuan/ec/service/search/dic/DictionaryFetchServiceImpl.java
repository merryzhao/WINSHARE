package com.winxuan.ec.service.search.dic;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.DictionaryFetchDao;
import com.winxuan.ec.exception.SearchTaskConfigException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.search.dic.SearchDictionary;
import com.winxuan.ec.model.search.dic.SearchTaskConfig;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 词典抓取实现类
 * 
 * @author sunflower
 * 
 */
@Service("dictionaryFetchService")
@Transactional(rollbackFor = Exception.class)
public class DictionaryFetchServiceImpl implements DictionaryFetchService,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6908474580052305619L;

	private static final Log LOG = LogFactory
			.getLog(DictionaryFetchServiceImpl.class);

	private static final String SYNONYM_SCRIPT = "grabSynonym.js";

	private static ScriptEngine engine;
	static {
		engine = new ScriptEngineManager().getEngineByName("ECMAScript");
	}

	@Autowired
	HttpService httpService;

	@InjectDao
	private DictionaryFetchDao dictionaryFetchDao;

	@Override
	public List<SearchTaskConfig> queryTaskConfig() {
		return dictionaryFetchDao.findConfig();
	}

	@Override
	public List<SearchDictionary> analyze(List<String> data, String script,
			Code source) throws ScriptException, IOException {

		InputStream is = DictionaryFetchServiceImpl.class
				.getResourceAsStream(script);
		if (is == null) {
			throw new RuntimeException(script + " not found!!!");
		}
		Reader jsReader = new InputStreamReader(is);
		engine.eval(jsReader);
		Invocable invoke = (Invocable) engine;
		JSLib jslib = invoke.getInterface(JSLib.class);
		List<String> list = new ArrayList<String>();
		for (String text : data) {
			String word = null;
			try {
				word = jslib.analyze(text);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				continue;
			}
			if (word != null) {
				if (shouldBeAdd(word)) {// 是否满足添加条件
					list.add(word);
				}
			}
		}

		if (list == null || list.size() == 0) {
			return null;
		}
		List<SearchDictionary> dics = new ArrayList<SearchDictionary>();

		for (String s : list) {
			SearchDictionary searchDictionary = new SearchDictionary();
			searchDictionary.setSource(source);
			searchDictionary.setWord(s);
			searchDictionary.setCreateTime(new Date());
			dics.add(searchDictionary);
		}
		return dics;
	}

	/**
	 * 词长、字符验证
	 * 
	 * @param word
	 * @return
	 */
	private boolean shouldBeAdd(String word) {
		if (word.length() <= MagicNumber.ONE
				|| word.length() > MagicNumber.FOUR) {// 长度不在2-4之间不添加
			return false;
		}
		char[] chars = word.toCharArray();
		for (char c : chars) {
			if (CharacterHelper.isArabicNumber(c)) {// 数字不添加
				return false;
			} else if (CharacterHelper.isEnglishLetter(c)) {// 英文不添加
				return false;
			} else if (CharacterHelper.isSpaceLetter(c)) {// 空格不添加
				return false;
			} else if (CharacterHelper.isCJKCharacter(c)) {
				continue;
			} else {// 其他类型都不添加
				return false;
			}
		}
		return true;
	}

	/**
	 * js处理接口
	 * 
	 * @author sunflower
	 * 
	 */
	interface JSLib {
		String analyze(String text);
	}

	@Override
	public String grabSynonymWord(String word) throws ScriptException {

		InputStream is = DictionaryFetchServiceImpl.class
				.getResourceAsStream(SYNONYM_SCRIPT);
		if (is == null) {
			throw new RuntimeException(SYNONYM_SCRIPT + " not found!!!");
		}
		Reader jsReader = new InputStreamReader(is);
		engine.eval(jsReader);
		Invocable invoke = (Invocable) engine;
		GrabSynonym grabSynonym = invoke.getInterface(GrabSynonym.class);
		String url = grabSynonym.getSynonymUrl(word);
		List<String> list = httpService.fetchAsList(url,
				Charset.forName(grabSynonym.getCharsetName()));
		for (String text : list) {
			String grab = grabSynonym.analyzeSynonym(text);
			if (grab != null) {
				return grab;
			}
		}
		return null;
	}

	/**
	 * 抓取js接口
	 * 
	 * @author sunflower
	 * 
	 */
	interface GrabSynonym {
		String getSynonymUrl(String word);

		String analyzeSynonym(String text);

		String getCharsetName();
	}

	@Override
	public List<SearchTaskConfig> queryTaskConfig(Pagination pagination) {
		return dictionaryFetchDao.findConfig(pagination);
	}

	@Override
	public void delete(long id) throws SearchTaskConfigException {

		SearchTaskConfig config = dictionaryFetchDao
				.getSearchTaskConfigById(id);
		if (config == null) {
			throw new SearchTaskConfigException(config, "待删除的配置信息已经不存在");
		}
		dictionaryFetchDao.delete(config);
	}

	@Override
	public void addConfig(String address,String analyseScript,
			String charsetFormat, Employee employee) throws SearchTaskConfigException {
		
		SearchTaskConfig config = dictionaryFetchDao
		.getSearchTaskConfigByAddress(address);
		if (config != null) {
			throw new SearchTaskConfigException(config, "待添加的配置信息已经存在");
		}
		config = new SearchTaskConfig();
		config.setUrl(address);
		config.setScript(analyseScript);
		config.setCharsetName(charsetFormat);
		config.setSource(getSource(address,config));
		dictionaryFetchDao.addConfig(config);
	}

	private Code getSource(String address,SearchTaskConfig config) throws SearchTaskConfigException {
		if(address.toUpperCase().indexOf("DANGDANG")!=-1){
			return new Code(Code.DICTIONARY_SOURCE_DANGDANG);
		}else if(address.toUpperCase().indexOf("BAIDU")!=-1){
			return new Code(Code.DICTIONARY_SOURCE_BAIDU);
		}else if(address.toUpperCase().indexOf("SINA")!=-1){
			return new Code(Code.DICTIONARY_SOURCE_SINA);
		}else{
			throw new SearchTaskConfigException(config, "暂不支持该网站的抓取信息");
		}
	}

}
