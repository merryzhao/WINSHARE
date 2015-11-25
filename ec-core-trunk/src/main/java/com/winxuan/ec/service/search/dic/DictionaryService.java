package com.winxuan.ec.service.search.dic;

import java.util.List;

import com.winxuan.ec.exception.DictionaryException;
import com.winxuan.ec.model.search.dic.SearchDictionary;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.pagination.Pagination;

/**
 * 词典处理
 * @author sunflower
 *
 */
public interface DictionaryService {

	/**
	 * 查询已经通过审核的词典
	 * @param pagination
	 * @return
	 */
	List<SearchDictionary> queryDictionary(Pagination pagination);

	/**
	 * 人工添加新词
	 * @param word
	 * @param employee
	 * @throws DictionaryException
	 */
	void addWord(String word, Employee employee) throws DictionaryException;

	/**
	 * 抓取到的新词添加
	 * @param dics
	 */
	void addWords(List<SearchDictionary> dics);
	

	/**
	 * 查询待审核的词典
	 * @param pagination
	 * @return
	 */
	List<SearchDictionary> queryAuditDictionary(Pagination pagination);

	/**
	 * 审核通过
	 * @param id
	 * @param employee
	 * @throws DictionaryException 
	 */
	void audit(long id, Employee employee) throws DictionaryException;

	
	/**
	 * 审核不通过
	 * @param id
	 * @param employee
	 * @throws DictionaryException 
	 */
	void unaudited(long id, Employee employee) throws DictionaryException;

	/**
	 * 删除已经审核的词语
	 * @param id
	 * @throws DictionaryException 
	 */
	void delete(long id) throws DictionaryException;

	/**
	 * 修改词语
	 * @param id
	 * @param word
	 * @param employee
	 * @throws DictionaryException 
	 */
	void editWord(long id, String word, Employee employee) throws DictionaryException;


	/**
	 * 抓取词语信息包括（同义词和推荐词）
	 * @param word
	 * @return
	 */
	SearchDictionary querySearchDictionaryByWord(String word);

	/**
	 * 词典维护
	 * @param word
	 * @param synonymWord
	 * @param recommendWord
	 * @param employee
	 */
	void manageWord(String word, String synonymWord, String recommendWord,
			Employee employee);

	/**
	 * 词典查询
	 * @param pagination
	 * @param queryWord
	 * @return
	 */
	List<SearchDictionary> queryDictionary(Pagination pagination,
			String queryWord);

	/**
	 * 查询未审核词语
	 * @param pagination
	 * @param queryWord
	 * @return
	 */
	List<SearchDictionary> queryAuditDictionary(Pagination pagination,
			String queryWord);

	/**
	 * 查询审核未通过词语列表
	 * @param pagination
	 * @return
	 */
	List<SearchDictionary> queryUnauditDictionary(Pagination pagination);

	/**
	 * 查询未审核通过词语
	 * @param pagination
	 * @param queryWord
	 * @return
	 */
	List<SearchDictionary> queryUnauditDictionary(Pagination pagination,
			String queryWord);


}
