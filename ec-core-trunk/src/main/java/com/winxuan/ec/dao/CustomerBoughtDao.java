/*
 * @(#)CustomerBoughtDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerBought;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Save;
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
 * @version 1.0,2011-9-26
 */
public interface CustomerBoughtDao {

	@Query("from CustomerBought cb where cb.customer=? and cb.productSale=?")
	CustomerBought getByCustomerAndProductSale(Customer customer,
			ProductSale productSale);

	@Save
	void save(CustomerBought bought);

	@Query("from CustomerBought cb where cb.customer = :customer")
	@Conditions({ 
		@Condition("cb.sort = :productSort"), 
		@Condition("cb.productSale.product.name like :productName"),
		@Condition("cb.buyTime >= :startDate"),
		@Condition("cb.buyTime <= :endDate"),
		@Condition("cb.order.id = :orderId")
	})
	@OrderBys({ 
		@OrderBy("cb.buyTime desc"), 
		@OrderBy("cb.buyTime asc"),
		@OrderBy("cb.buyPrice desc"), 
		@OrderBy("cb.buyPrice asc") ,
		@OrderBy("cb.productSale.salePrice/cb.productSale.product.listPrice desc") ,
		@OrderBy("cb.productSale.salePrice/cb.productSale.product.listPrice asc") 
	})
	List<CustomerBought> findBought(@Parameter("customer") Customer customer,
			@Parameter("productSort") Code productSort, @ParameterMap Map<String, Object> parameters, @Order short orderBy,
			@Page Pagination pagination);
	
	@Query("from CustomerBought cb where cb.customer = :customer")
	@Conditions({ 
		@Condition("cb.sort = :productSort"), 
		@Condition("cb.productSale.product.name like :productName"),
		@Condition("cb.buyTime >= :startDate"),
		@Condition("cb.buyTime <= :endDate") 
	})
	long findBoughtCount(@Parameter("customer") Customer customer,
			@Parameter("productSort") Code productSort, @ParameterMap Map<String, Object> parameters);
}
