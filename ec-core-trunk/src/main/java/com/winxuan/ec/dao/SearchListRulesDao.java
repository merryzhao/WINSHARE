package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.search.SearchListRules;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 搜索规则
 * @author 赵芮
 *
 * 上午11:12:39
 */
public interface SearchListRulesDao {
	@Save
	void save(SearchListRules rule);
	
	@Update
	void update(SearchListRules rule);
	
	@SaveOrUpdate
	void saveorupdate(SearchListRules rule);
	
	@Query("from SearchListRules r")
	List<SearchListRules> findAll();
	
	@Get
	SearchListRules get(int id);
}
