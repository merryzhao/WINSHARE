package com.winxuan.ec.dao;

import com.winxuan.ec.model.search.SearchOpFreqStatistics;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * dao
 * @author sunflower
 *
 */
public interface SearchOpFreqStatisticsDao {

	@Query("from SearchOpFreqStatistics s where s.keyword = ? ")
	SearchOpFreqStatistics getStatisticsByKeyword(String keyword);

}
