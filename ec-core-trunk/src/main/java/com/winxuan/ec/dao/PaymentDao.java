/*
 * @(#)PaymentDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.payment.Payment;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 支付DAO
 * 
 * @author wumaojie
 * @version 1.0,2011-7-18
 */
public interface PaymentDao {

	@Get
	Payment get(Long id);

	@Update
	void update(Payment payment);
    
	@Query("from Payment p where p.type.id = ? ")
	List<Payment> find(Long type);

	@Query("from Payment p")
	@Conditions({ 
		    @Condition(value = "p.available = :onlyAvailable"),
			@Condition(value = "p.show = :onlyShow")})
	List<Payment> find(
			@Parameter("onlyAvailable") Boolean onlyAvailable,
			@Parameter("onlyShow") Boolean onlyShow);
	
	@Save
	void save(Payment payment);

}
