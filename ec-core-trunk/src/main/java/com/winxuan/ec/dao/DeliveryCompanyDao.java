/*
 * @(#)DeliveryCompanyDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-7-15
 */
public interface DeliveryCompanyDao {

	@Get
	DeliveryCompany get(Long id);

	@Query("from DeliveryCompany d")
	@Conditions({
		@Condition("d.available=:available")
	})	
	List<DeliveryCompany> find(@Parameter("available") Boolean available);

	@Update
	void update(DeliveryCompany deliveryCompany);
}
