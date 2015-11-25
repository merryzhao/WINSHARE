/*
 * @(#)ProductMetaDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductMetaEnum;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-29
 */
public interface ProductMetaDao {

	@Query("from ProductMeta")
	List<ProductMeta> findAllProductMeta();

	@Query("from ProductMeta p where p.available=true")
	List<ProductMeta> findAvailableProductMeta();

	@Get
	ProductMeta getProductMeta(Long id);
	
	@Query("from ProductMeta p")
	@Conditions({ @Condition("p.name = :name"),
			@Condition("p.show = :show"),
			@Condition("p.available = :available")})
	@OrderBys({
		@OrderBy("p.id desc"),
		@OrderBy("p.id asc")
	})
	List<ProductMeta> findProductMeta(
			@ParameterMap Map<String, Object> parameters,@Page Pagination pagination,@Order short orderIndex);
	
	@Query("from ProductMeta p")
	@Conditions({ @Condition("p.name = :name"),
			@Condition("p.category = :category")})	
	ProductMeta findProductMeta(@Parameter("name") String name,
			@Parameter("category") Integer category); 
	
	@Save
	void saveProductMeta(ProductMeta productMeta); 

	@Update
	void updateProductMeta(ProductMeta productMeta);

	@Get
	ProductMetaEnum getProductMetaEnum(Long id);

	@Save
	void saveProductMetaEnum(ProductMetaEnum productMetaEnum);

	@Update
	void updateProductMetaEnum(ProductMetaEnum productMetaEnum);
	
	@Delete
	void deleteProductMetaEnum(ProductMetaEnum productMetaEnum);
}
