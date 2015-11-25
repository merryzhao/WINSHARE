package com.winxuan.ec.service.search.dic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.script.ScriptException;

import com.winxuan.ec.exception.SearchTaskConfigException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.search.dic.SearchDictionary;
import com.winxuan.ec.model.search.dic.SearchTaskConfig;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.pagination.Pagination;

/**
 * 词典抓取
 * @author sunflower
 *
 */
public interface DictionaryFetchService {

	/**
	 * 获取任务配置信息
	 * @return
	 */
	List<SearchTaskConfig> queryTaskConfig();
	

	/**
	 * 获取任务配置信息
	 * @param pagination
	 * @return
	 */
	List<SearchTaskConfig> queryTaskConfig(Pagination pagination);
	
	/**
	 * 解析文本，使其转化为可添加的词典形式
	 * @param data
	 * @param script 
	 * @param source
	 * @return
	 * @throws FileNotFoundException 
	 * @throws ScriptException 
	 * @throws IOException 
	 */
	List<SearchDictionary> analyze(List<String> data, String script,Code source) throws FileNotFoundException, ScriptException, IOException;

	/**
	 * 抓取指定词语的同义词
	 * @param word
	 * @return
	 * @throws ScriptException 
	 */
	String grabSynonymWord(String word) throws ScriptException;


	/**
	 * 删除指定id的抓取配置信息
	 * @param id
	 * @throws SearchTaskConfigException 
	 */
	void delete(long id) throws SearchTaskConfigException;


	/**
	 * 添加抓取页面配置信息
	 * @param address
	 * @param analyseScript
	 * @param charsetFormat
	 * @param employee
	 * @throws SearchTaskConfigException 
	 */
	void addConfig(String address, String analyseScript,
			String charsetFormat, Employee employee) throws SearchTaskConfigException;
}
