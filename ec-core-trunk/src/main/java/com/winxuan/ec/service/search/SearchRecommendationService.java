/*
 * @(#)ProductRecommendationService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.search;

import java.util.List;

import com.winxuan.ec.support.web.enumerator.RecommendMode;

/**
 * description
 * @author  huangyixiang
 * @version 2011-11-14
 */
public interface SearchRecommendationService {
	
	/**
	 * 相关搜索
	 * @param keyword 关键词
	 * @param mode 模式
	 * @param count 推荐数量
	 * @return
	 */
	List<String> findRecommendByProductSale(String keyword, RecommendMode mode , int count);

}
