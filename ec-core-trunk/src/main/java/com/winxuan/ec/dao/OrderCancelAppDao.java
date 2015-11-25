/*
 * @(#)OrderInvoiceDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.OrderCancelApp;
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
 * @author chenlong
 * @version 1.0,2011-10-17
 */
public interface OrderCancelAppDao {

	@Save
	void save(OrderCancelApp cancelApp);

	@Query("from OrderCancelApp o ")
	@Conditions({ @Condition("o.order.id = :orderid"),
			@Condition("o.order.createTime>= :startCreateTime"),
			@Condition("o.order.createTime<= :endCreateTime"),
			@Condition("o.createUser = :createUser"),
			@Condition("o.status.id in :status")})
	List<OrderCancelApp> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	@Query("from OrderCancelApp o where o.order.id=?")
	boolean existByOrderId(String orderId);
	
	@Update
	void update(OrderCancelApp cancelApp);
}
