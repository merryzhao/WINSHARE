package com.winxuan.ec.service.search;

import java.util.List;


import com.winxuan.ec.model.search.SearchListRules;

/**
 * 搜索规则
 * @author 赵芮
 *
 * 上午11:00:46
 */
public interface SearchListRulesService {
	SearchListRules get(int id);
	
	void save(SearchListRules rule); 
	
	void update(SearchListRules rule);
	
	void saveorupdate(SearchListRules rule);
	
	List<SearchListRules> findAll();

}
