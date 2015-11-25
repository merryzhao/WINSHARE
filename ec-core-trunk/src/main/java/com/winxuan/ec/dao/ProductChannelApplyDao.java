/*
 * @(#)ProductChannelApplyDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.ProductChannelApply;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-9-29
 */
public interface ProductChannelApplyDao {

	@Get
	ProductChannelApply get(Long id);

	@Save
	void save(ProductChannelApply productChannelApply);

	@Update
	void update(ProductChannelApply productChannelApply);

	@Query("from ProductChannelApply p")
	@Conditions({ @Condition("p.productSale.id =:saleId"),
		@Condition("p.productSale.id in :productSaleIds"),
		@Condition("p.type.id =:typeId"),
		@Condition("p.state.id =:stateId"),
		@Condition("p.createDate >= :createStartDate"),
		@Condition("p.createDate <= :createEndDate"),
		@Condition("p.productSale.shop.id =:shopId"),
		@Condition("p.productSale.outerId in:outerIds"),
		@Condition("p.productSale.outerId =:outerId")		
	})
	List<ProductChannelApply> find(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	@Query("from ProductChannelApply p")
	@Conditions({ @Condition("p.productSale.id =:saleId"),
		@Condition("p.productSale.id in :productSaleIds"),
		@Condition("p.type.id =:typeId"),
		@Condition("p.state.id =:stateId"),
		@Condition("p.createDate >= :createStartDate"),
		@Condition("p.createDate <= :createEndDate"),
		@Condition("p.productSale.shop.id =:shopId"),
		@Condition("p.productSale.outerId in:outerIds"),
		@Condition("p.productSale.outerId =:outerId")		
	})
	List<ProductChannelApply> find(
			@ParameterMap Map<String, Object> parameters);
}
