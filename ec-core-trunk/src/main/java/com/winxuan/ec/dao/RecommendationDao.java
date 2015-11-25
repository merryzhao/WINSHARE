/*
 * @(#)CategoryDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.product.ProductRecommendation;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 *@author huangyixiang
 *@version 1.0,2011-11-14
 */
public interface RecommendationDao {
	
	@Get
	ProductRecommendation get(Long id);
	
	@Query("select pr.recommendation from ProductRecommendation pr where pr.preference > 0.97 ")
	@Conditions({
			@Condition("pr.productSale = :productSale"),
			@Condition("pr.mode = :mode")})
	@OrderBys({
			@OrderBy("pr.preference desc")})
	List<ProductSale> find(@Parameter("productSale") ProductSale productSale,
			@Parameter("mode") Short mode,
			@MaxResults int count,
			@Order short order);
	
	@Query("select pr.recommendation from SearchRecommendation pr where pr.preference > 0.975 ")
	@Conditions({
			@Condition("pr.recommendid = :recommendid"),
			@Condition("pr.mode = :mode")})
	@OrderBys({
			@OrderBy("pr.preference desc")})
	List<String> find(@Parameter("recommendid") String keyword,
			@Parameter("mode") Short mode,
			@MaxResults int count,
			@Order short order);
	
	
}
