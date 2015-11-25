package com.winxuan.ec.service.search;

import com.winxuan.ec.dao.SearchOpFreqStatisticsDao;
import com.winxuan.ec.dao.SearchQueryExtendDao;
import com.winxuan.ec.model.search.SearchOpFreqStatistics;

/**
 * 抽象產品類
 * @author sunflower
 *
 */
public abstract class QueryExtend {

	protected SearchOpFreqStatisticsDao searchOpFreqStatisticsDao;
	protected SearchQueryExtendDao searchQueryExtendDao;
	
	public QueryExtend(SearchOpFreqStatisticsDao searchOpFreqStatisticsDao,SearchQueryExtendDao searchQueryExtendDao) {
		this.searchOpFreqStatisticsDao = searchOpFreqStatisticsDao;
		this.searchQueryExtendDao = searchQueryExtendDao;
	}

	public String extend(String keyword, String query) {

		SearchOpFreqStatistics searchOpFreqStatistics = searchOpFreqStatisticsDao
				.getStatisticsByKeyword(keyword);
		String extend = null;
		if(searchOpFreqStatistics == null){
			extend = query;
		}else{
			extend = getQueryExtend(searchOpFreqStatistics, query);
		}
		/*SearchQueryExtend searchQueryExtend = new SearchQueryExtend();
		searchQueryExtend.setKeyword(keyword);
		searchQueryExtend.setQuery(query);
		searchQueryExtend.setQueryExtend(extend);
		searchQueryExtend.setSatisfy(0L);
		searchQueryExtend.setUnsatisfy(0L);
		SearchQueryExtend sqe = searchQueryExtendDao.getByKeyword(keyword);
		if (sqe != null) {
			searchQueryExtendDao.delete(sqe);
		}
		searchQueryExtendDao.save(searchQueryExtend);*/
		return extend;
	}

	abstract String getQueryExtend(SearchOpFreqStatistics searchOpFreqStatistics, String query);
}
