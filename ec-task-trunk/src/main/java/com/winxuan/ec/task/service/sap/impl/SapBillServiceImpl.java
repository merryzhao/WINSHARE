/*
 * @(#)SapBillServiceImpl.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.sap.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.task.dao.sap.SapDao;
import com.winxuan.ec.task.service.sap.SapBillService;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-11-10
 */
@Service("sapBillService")
@Transactional(rollbackFor = Exception.class)
public class SapBillServiceImpl implements SapBillService, Serializable{

	private static final long serialVersionUID = 7263583614221310804L;
	
	@Autowired
	private SapDao sapDao;
	
	@Override
	public void sendBillItems(Object[] params) {
		// TODO Auto-generated method stub
		sapDao.sendSapBillItems(params);
	}

}
