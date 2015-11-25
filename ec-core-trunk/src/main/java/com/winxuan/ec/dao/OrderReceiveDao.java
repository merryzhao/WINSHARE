/*
 * @(#)OrderReceiveDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.OrderReceive;
import com.winxuan.framework.dynamicdao.annotation.Save;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-7-20
 */
public interface OrderReceiveDao {
	@Save
	void save(OrderReceive orderReceive);
}
