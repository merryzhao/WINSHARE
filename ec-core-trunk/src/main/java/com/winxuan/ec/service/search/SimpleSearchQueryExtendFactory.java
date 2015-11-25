package com.winxuan.ec.service.search;

import org.springframework.stereotype.Service;

import com.winxuan.ec.dao.SearchOpFreqStatisticsDao;
import com.winxuan.ec.dao.SearchQueryExtendDao;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 簡單工廠類
 * @author sunflower
 *
 */
@Service("searchQueryExtendFactory")
public class SimpleSearchQueryExtendFactory implements SearchQueryExtendFactory {
	
	@InjectDao
	SearchOpFreqStatisticsDao searchOpFreqStatisticsDao;
	
	@InjectDao
	SearchQueryExtendDao searchQueryExtendDao;
	
	@Override
	public QueryExtend getQueryExtend() {
		
		return new SimpleQueryExtend(searchOpFreqStatisticsDao,searchQueryExtendDao);
	}

}
