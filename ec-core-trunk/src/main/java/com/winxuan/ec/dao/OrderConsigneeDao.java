/*
 * @(#)OrderUpdateLogDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.framework.dynamicdao.annotation.Save;

/**
 * description
 *
 * @author hidehai
 * @version 1.0,2011-7-20
 */
public interface OrderConsigneeDao {
	
	@Save
	void save(OrderConsignee orderConsignee);
}
