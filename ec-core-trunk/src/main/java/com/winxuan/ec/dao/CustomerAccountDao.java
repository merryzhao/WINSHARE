/*
 * @(#)CustomerAccountDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.customer.CustomerAccountDetail;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-30
 */
public interface CustomerAccountDao {
	
	@Save
	void save(CustomerAccount customerAccount);

	@Query("from CustomerAccountDetail c")
	@Conditions({ @Condition("c.account.customer.name = :customerName"),
			@Condition("c.account = :account"),
			@Condition("c.order.id = :orderId"),
			@Condition("c.useTime >= :startDate"),
			@Condition("c.useTime <= :endDate") })
	@OrderBys({
		@OrderBy("c.useTime desc"),
		@OrderBy("c.useTime asc")
	})
	List<CustomerAccountDetail> findAccountDetail(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,@Order short orderIndex);

	@Query("from CustomerAccountDetail c where c.account = ? and c.order = ? order by c.id")
	List<CustomerAccountDetail> findAccountDetail(CustomerAccount account,
			com.winxuan.ec.model.order.Order order);
	
	@Update
	void update(CustomerAccount account);
}
