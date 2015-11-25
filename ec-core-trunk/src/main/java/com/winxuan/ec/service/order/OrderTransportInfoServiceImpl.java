/*
 * @(#)OrderTransportInfoServiceImpl.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.OrderTransportInfoDao;
import com.winxuan.ec.model.order.OrderTransportInfo;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * description
 * 
 * @author wangbiao
 * @version 1.0 date 2015-2-3
 */
@Service("orderTransportInfoService")
@Transactional(rollbackFor = Exception.class)
public class OrderTransportInfoServiceImpl implements OrderTransportInfoService {

	@InjectDao
	private OrderTransportInfoDao orderTransportInfoDao;
	
	@Override
	public void save(OrderTransportInfo orderTransportInfo) {
		this.orderTransportInfoDao.save(orderTransportInfo);
	}

}
