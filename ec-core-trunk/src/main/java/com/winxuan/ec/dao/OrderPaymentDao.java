/*
 * @(#)OrderPaymentDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 *@author chenlong
 *@version 1.0,2011-7-20
 */
public interface OrderPaymentDao {
	@Save
	void save(OrderPayment orderPayment);
	
	@Query("from OrderPayment c where c.order.id=?")
	List<OrderPayment> findByOrderId(String orderId);
}
