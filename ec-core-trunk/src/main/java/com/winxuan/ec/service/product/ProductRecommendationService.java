/*
 * @(#)ProductRecommendationService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.product;

import java.util.List;
import java.util.Set;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.support.web.enumerator.RecommendMode;

/**
 * description
 * @author  huangyixiang
 * @version 2011-11-14
 */
public interface ProductRecommendationService {
	
	/**
	 * 根据商品推荐商品
	 * @param productSale 商品 
	 * @param mode 模式：购买记录 or 浏览记录
	 * @param count 推荐商品数量
	 * @return
	 */
	List<ProductSale> findRecommendByProductSale(ProductSale productSale , RecommendMode mode , int count);

	List<ProductSale> findRecommend(ProductSale productSale , RecommendMode mode , int count);
	
	/**
	 * 根据多个商品推荐商品
	 * @param productSales 商品 
	 * @param mode 模式：购买记录 or 浏览记录
	 * @param count 推荐商品数量
	 * @return
	 */
	Set<ProductSale> findRecommendByProductSales(List<ProductSale> productSales , RecommendMode mode , int count);

}
