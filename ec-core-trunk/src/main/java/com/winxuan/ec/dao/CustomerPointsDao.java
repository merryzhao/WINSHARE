/*
 * @(#)CustomerPointsDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.Date;
import java.util.List;

import com.winxuan.ec.model.customer.CustomerPoints;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-23
 */
public interface CustomerPointsDao {

	@Save
	void save(CustomerPoints points);

	@Query(value = "FROM CustomerPoints c where c.customer = :customer order by c.id desc")
	@Conditions({ @Condition("c.time >= :startDate"),
			@Condition("c.time <= :endDate") })
	List<CustomerPoints> find(@Parameter("customer") Customer customer,
			@Parameter("startDate") Date startDate,
			@Parameter("endDate") Date endDate, @Page Pagination pagination);
}
