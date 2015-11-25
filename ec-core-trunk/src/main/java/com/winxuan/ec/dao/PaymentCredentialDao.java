/*
 * @(#)PaymentCredentialDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 支付凭证DAO
 *
 * @author wumaojie
 * @version 1.0,2011-7-18
 */
public interface PaymentCredentialDao {
	
	@Save
	void save(PaymentCredential paymentCredential);

	@Query("FROM PaymentCredential p WHERE p.payment=? AND p.outerId=?")
	boolean isExisted(Payment payment, String outerId);
	
	@Query("FROM PaymentCredential p WHERE p.payment=? AND p.outerId=?")
	PaymentCredential getByOuterIdAndPayment(Payment payment, String outerId);
	
	@Get
	PaymentCredential get(Long id);
}
