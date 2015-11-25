package com.winxuan.ec.service.search.dic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.DictionaryDao;
import com.winxuan.ec.exception.DictionaryException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.search.dic.SearchDictionary;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 词典处理类
 * @author sunflower
 *
 */
@Service("dictionaryService")
@Transactional(rollbackFor = Exception.class)
public class DictionaryServiceImpl implements DictionaryService , Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6525464601878500588L;

	private static final Log LOG = LogFactory.getLog(DictionaryServiceImpl.class);

	@InjectDao
	private DictionaryDao dictionaryDao;

	@Override
	public List<SearchDictionary> queryDictionary(Pagination pagination) {
		return dictionaryDao.queryDictionary(pagination);
	}

	@Override
	public void addWord(String word, Employee employee)
			throws DictionaryException {

		SearchDictionary dic = new SearchDictionary();
		dic.setWord(word);
		dic.setAudit(true);
		dic.setAuditBy(employee);
		dic.setAuditTime(new Date());
		dic.setCreateTime(new Date());
		dic.setSource(new Code(Code.DICTIONARY_SOURCE_PERSON));
		if (isWordExist(dic)) {
			throw new DictionaryException(dic, "该词语已经存在");
		}
		dictionaryDao.addWord(dic);
	}

	private boolean isWordExist(SearchDictionary dic) {
		return querySearchDictionaryByWord(dic.getWord()) != null;
	}
	
	@Override
	public SearchDictionary querySearchDictionaryByWord(String word) {
		return dictionaryDao.find(word);
	}

	@Override
	public void addWords(List<SearchDictionary> dics) {

		for (SearchDictionary dic : dics) {
			if (isWordExist(dic)) {
				continue;
			}
			dictionaryDao.addWord(dic);
		}
	}

	@Override
	public List<SearchDictionary> queryAuditDictionary(Pagination pagination) {

		return dictionaryDao.queryAuditDictionary(pagination);
	}

	@Override
	public void audit(long id, Employee employee) throws DictionaryException {

		SearchDictionary dic = getSearchDictionaryById(id);
		if (dic == null) {
			throw new DictionaryException(dic, "该词语不存在");
		}
		dic.setAudit(true);
		dic.setAuditBy(employee);
		dic.setAuditTime(new Date());
		dictionaryDao.update(dic);
	}

	private SearchDictionary getSearchDictionaryById(long id) {

		return dictionaryDao.getSearchDictionaryById(id);
	}

	@Override
	public void unaudited(long id, Employee employee)
			throws DictionaryException {

		SearchDictionary dic = getSearchDictionaryById(id);
		if (dic == null) {
			throw new DictionaryException(dic, "该词语不存在");
		}
		dic.setAudit(false);
		dic.setAuditBy(employee);
		dic.setAuditTime(new Date());
		dictionaryDao.update(dic);
	}

	@Override
	public void delete(long id) throws DictionaryException {
		SearchDictionary dic = getSearchDictionaryById(id);
		if (dic == null) {
			throw new DictionaryException(dic, "该词语已经被删除了");
		}
		cleanRelationship(dic);
		dic.setDelete(true);
		dic.setParent(null);
		dictionaryDao.update(dic);
	}
	
	private void cleanRelationship(SearchDictionary dic){
		Set<SearchDictionary> synonymSet = dic.getSynonym2s();
		Iterator<SearchDictionary> it = synonymSet.iterator();
		while(it.hasNext()){
			SearchDictionary sd = it.next();
			sd.removeSynonym2(dic);
			dictionaryDao.merge(sd);
		}
		dic.setSynonym2s(null);
		Set<SearchDictionary> recommendSet = dic.getChildren();
		Iterator<SearchDictionary> rit = recommendSet.iterator();
		while(rit.hasNext()){
			SearchDictionary sd = rit.next();
			sd.setParent(null);
			dictionaryDao.merge(sd);
		}
		dic.setChildren(null);
		dictionaryDao.merge(dic);
	}

	@Override
	public void editWord(long id, String word, Employee employee)
			throws DictionaryException {

		SearchDictionary dic = getSearchDictionaryById(id);
		if (dic == null) {
			throw new DictionaryException(dic, "该条词语可能已经被删除了");
		}
		if (!dic.getWord().equals(word)) {
			if (querySearchDictionaryByWord(word) != null) {
				throw new DictionaryException(dic, "待修改词语已经存在");
			}
			dic.setWord(word);
			dictionaryDao.update(dic);
		}
	}



	@Override
	public void manageWord(String word, String synonymWord,
			String recommendWord, Employee employee) {

		String[] synonymWords = isBlankOrNull(synonymWord)?new String[0]:synonymWord.split(",");
		String[] recommendWords = isBlankOrNull(recommendWord)?new String[0]:recommendWord.split(",");

		//添加词语，如果存在不做处理
		try {
			addWord(word, employee);
		} catch (DictionaryException e) {
			LOG.info("词语："+word+"已经添加成功");
		}
		for(String s : synonymWords){
			try {
				addWord(s, employee);
			} catch (DictionaryException e) {
				LOG.info("词语："+word+"已经添加成功");
			}
		}
		for(String r : recommendWords){
			try {
				addWord(r, employee);
			} catch (DictionaryException e) {
				LOG.info("词语："+word+"已经添加成功");
			}
		}
		
		SearchDictionary dic = querySearchDictionaryByWord(word);
		List<SearchDictionary> synonymDics = new ArrayList<SearchDictionary>();
		for(String s : synonymWords){
			synonymDics.add(querySearchDictionaryByWord(s));
		}
		List<SearchDictionary> recommendDics = new ArrayList<SearchDictionary>();
		for(String s : recommendWords){
			recommendDics.add(querySearchDictionaryByWord(s));
		}
		
		//去掉原有关系
		cleanRelationship(dic);
		
		//增加新关系
		for(SearchDictionary dictionary : synonymDics){
			dic.addSynonym2(dictionary);
			dictionary.addSynonym2(dic);
			for(SearchDictionary d : synonymDics){
				if(!d.equals(dictionary)){
					dictionary.addSynonym2(d);
				}
			}
			dictionaryDao.merge(dictionary);
		}
		for(SearchDictionary r : recommendDics){
			dic.addChild(r);
			r.setParent(dic);
			dictionaryDao.update(r);
		}
		dictionaryDao.merge(dic);
	}
	
	private boolean isBlankOrNull(String s) {
		return s==null || "".equals(s);
	}

	@Override
	public List<SearchDictionary> queryDictionary(Pagination pagination,
			String queryWord) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("word", queryWord);
		return dictionaryDao.queryDictionary(parameters,pagination);
	}

	@Override
	public List<SearchDictionary> queryAuditDictionary(Pagination pagination,
			String queryWord) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("word", queryWord);
		return dictionaryDao.queryAuditDictionary(parameters,pagination);
	}

	@Override
	public List<SearchDictionary> queryUnauditDictionary(Pagination pagination) {
		return dictionaryDao.queryUnauditDictionary(null,pagination);
	}

	@Override
	public List<SearchDictionary> queryUnauditDictionary(Pagination pagination,
			String queryWord) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("word", queryWord);
		return dictionaryDao.queryUnauditDictionary(parameters,pagination);
	}
}
