package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.search.dic.SearchTaskConfig;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 抓取任务配置类
 * @author sunflower
 *
 */
public interface DictionaryFetchDao {

	
	@Query("from SearchTaskConfig s ")
	List<SearchTaskConfig> findConfig();

	@Query("from SearchTaskConfig s order by s.id desc")
	List<SearchTaskConfig> findConfig(@Page Pagination pagination);
	
	@Get
	SearchTaskConfig getSearchTaskConfigById(long id);
	
	@Delete
	void delete(SearchTaskConfig config);

	@Query("from SearchTaskConfig s where s.url=?")
	SearchTaskConfig getSearchTaskConfigByAddress(String address);

	@Save
	void addConfig(SearchTaskConfig config);
}
