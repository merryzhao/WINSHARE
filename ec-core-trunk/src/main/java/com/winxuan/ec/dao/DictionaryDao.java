package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.search.dic.SearchDictionary;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 词典处理DAO
 * @author sunflower
 *
 */
public interface DictionaryDao {

	/**
	 * 查询审核通过的词典
	 * @param pagination
	 * @return
	 */
	@Query("select distinct dic from SearchDictionary dic left join dic.synonym2s where dic.isAudit = 1 and dic.isDelete = 0 order by dic.auditTime desc")
	List<SearchDictionary> queryDictionary(@Page Pagination pagination);

	@Save
	void addWord(SearchDictionary dic);

	@Query("select distinct dic from SearchDictionary dic left join dic.synonym2s  where dic.word = ? and dic.isDelete = 0 ")
	SearchDictionary find(String word);

	/**
	 * 查询待审核的词典列表
	 * @param pagination
	 * @return
	 */
	@Query("from SearchDictionary dic where dic.isAudit = 0 and dic.auditBy is null and dic.isDelete = 0 order by dic.createTime desc ")
	List<SearchDictionary> queryAuditDictionary(@Page Pagination pagination);

	@Get
	SearchDictionary getSearchDictionaryById(long id);

	@Update
	void update(SearchDictionary dic);

	@Delete
	void delete(SearchDictionary dic);

	@Merge
	void merge(SearchDictionary dic);

	@Query("select distinct dic from SearchDictionary dic left join dic.synonym2s where dic.isAudit = 1 and dic.isDelete = 0")
	@Conditions({
		@Condition("dic.word like CONCAT('%',:word ,'%')")
	})
	@OrderBys({
		@OrderBy("dic.auditTime desc")
	})
	List<SearchDictionary> queryDictionary(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);

	@Query("select distinct dic from SearchDictionary dic left join dic.synonym2s where dic.isAudit = 0 and dic.auditBy is null and dic.isDelete = 0")
	@Conditions({
		@Condition("dic.word like CONCAT('%',:word ,'%')")
	})
	@OrderBys({
		@OrderBy("dic.createTime desc")
	})
	List<SearchDictionary> queryAuditDictionary(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	@Query("select distinct dic from SearchDictionary dic left join dic.synonym2s where dic.isAudit = 0 and dic.auditBy is not null and dic.isDelete = 0")
	@Conditions({
		@Condition("dic.word like CONCAT('%',:word ,'%')")
	})
	@OrderBys({
		@OrderBy("dic.createTime desc")
	})
	List<SearchDictionary> queryUnauditDictionary(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
}
