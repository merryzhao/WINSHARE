/*
 * @(#)OrderStatusLogDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.order.OrderStatusLog;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 *
 * @author zhongsen
 * @version 1.0,2011-7-20
 */
public interface OrderStatusLogDao {
	@Save
	void save(OrderStatusLog orderStatusLog);
	
	@Query("from OrderStatusLog o where o.order.id =?")
	List<OrderStatusLog> getByOrderId(String orderId);
}
