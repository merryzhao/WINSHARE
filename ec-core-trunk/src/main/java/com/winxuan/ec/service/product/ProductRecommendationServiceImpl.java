/*
 * @(#)ProductRecommendationServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.RecommendationDao;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.support.web.enumerator.RecommendMode;
import com.winxuan.framework.cache.method.MethodCache;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * 
 * @author huangyixiang
 * @version 2011-11-14
 */
@Service("productRecommendationService")
@Transactional(rollbackFor = Exception.class)
public class ProductRecommendationServiceImpl implements
		ProductRecommendationService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2020739399811886829L;

	private static final short DEFAULT_ORDER = 0;

	@InjectDao
	RecommendationDao productRecommendationDao;

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@MethodCache(idleSeconds = MethodCache.SECONDS_OF_DAY)
	public List<ProductSale> findRecommendByProductSale(
			ProductSale productSale, RecommendMode mode, int count) {
		return productRecommendationDao.find(productSale, mode.getMode(),
				count, DEFAULT_ORDER);
	}

	public List<ProductSale> findRecommend(ProductSale productSale,
			RecommendMode mode, int count) {

		return productRecommendationDao.find(productSale, mode.getMode(),
				count, DEFAULT_ORDER);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Set<ProductSale> findRecommendByProductSales(
			List<ProductSale> productSales, RecommendMode mode, int count) {
		Set<ProductSale> returnSet = new HashSet<ProductSale>();

		List<List<ProductSale>> recommendList = new ArrayList<List<ProductSale>>();

		for (int i = 0; i < productSales.size(); i++) {
			recommendList.add(findRecommendByProductSale(productSales.get(i),
					mode, count));
		}
		for (int j = 0; j < count; j++) {
			for (List<ProductSale> list : recommendList) {
				if (returnSet.size() < count && j < list.size()) {
					returnSet.add(list.get(j));
				}
			}
		}
		return returnSet;
	}

}
