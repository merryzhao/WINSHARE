package com.winxuan.ec.service.search;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.SearchQueryExtendDao;
import com.winxuan.framework.cache.method.MethodCache;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 實現類
 * @author sunflower
 *
 */
@Service("searchQuery")
@Transactional(rollbackFor = Exception.class)
public class SearchQueryImpl implements SearchQuery, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -694232647323335697L;
	
	private static final int IDLESECONDS = 7*24*3600;
	
	
	
	@InjectDao
	SearchQueryExtendDao searchQueryExtendDao;

	@Autowired
	private SearchQueryExtendFactory searchQueryExtendFactory;
	
	@Override
	@MethodCache(idleSeconds = IDLESECONDS)//方法緩存
	public String getQuery(String keyword,String query) {
		
		QueryExtend queryExtend = searchQueryExtendFactory.getQueryExtend();
		String extend = queryExtend.extend(keyword,query);
		return extend;
	}

}
