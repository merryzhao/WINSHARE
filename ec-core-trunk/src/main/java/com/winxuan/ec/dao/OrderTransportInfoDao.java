/*
 * @(#)OrderTransportInfoDao.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.OrderTransportInfo;
import com.winxuan.framework.dynamicdao.annotation.Save;

/**
 * description
 * @author  wangbiao
 * @version 1.0
 * date 2015-2-3
 */
public interface OrderTransportInfoDao {

	@Save
	void save(OrderTransportInfo orderTransportInfo);
}
