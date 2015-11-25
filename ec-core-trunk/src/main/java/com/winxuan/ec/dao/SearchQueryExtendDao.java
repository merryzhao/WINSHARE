package com.winxuan.ec.dao;

import com.winxuan.ec.model.search.SearchQueryExtend;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * dao
 * @author sunflower
 *
 */
public interface SearchQueryExtendDao {

	
	@Query("from SearchQueryExtend q WHERE q.keyword = ?")
	SearchQueryExtend getByKeyword(String keyword);

	@Delete
	void delete(SearchQueryExtend searchQueryExtend);

	@Save
	void save(SearchQueryExtend searchQueryExtend);
}
