/*
 * @(#)OrderUpdateLogDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.OrderUpdateLog;
import com.winxuan.framework.dynamicdao.annotation.Save;

/**
 * description
 *
 * @author zhongsen
 * @version 1.0,2011-7-20
 */
public interface OrderUpdateLogDao {
	
	@Save
	void save(OrderUpdateLog orderUpdateLog);
}
