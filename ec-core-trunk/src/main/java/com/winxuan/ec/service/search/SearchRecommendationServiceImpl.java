/*
 * @(#)ProductRecommendationServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.search;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.RecommendationDao;
import com.winxuan.ec.support.web.enumerator.RecommendMode;
import com.winxuan.framework.cache.method.MethodCache;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * @author  huangyixiang
 * @version 2011-11-14
 */
@Service("searchRecommendationService")
@Transactional(rollbackFor = Exception.class)
public class SearchRecommendationServiceImpl implements SearchRecommendationService,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2020739399811886829L;

	private static final short DEFAULT_ORDER = 0; 
	
	private static final int IDLESECONDS = 3*24*3600;
	
	@InjectDao
	RecommendationDao recommendationDao;
	
	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@MethodCache(idleSeconds = IDLESECONDS)//方法緩存
	public List<String> findRecommendByProductSale(String keyword , RecommendMode mode , int count){
		return recommendationDao.find(keyword, mode.getMode(), count ,DEFAULT_ORDER);
	}
}
