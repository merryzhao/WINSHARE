/*
 * @(#)SapReplenishmentServiceImpl.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.sap.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.ec.task.dao.sap.SapDao;
import com.winxuan.ec.task.service.sap.SapReplenishmentService;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-9-2
 */
@Service("sapReplenishmentService")
@Transactional(rollbackFor = Exception.class)
public class SapReplenishmentServiceImpl implements SapReplenishmentService{
	
	private static final Log LOG = LogFactory.getLog(SapReplenishmentServiceImpl.class);
	
	@Autowired
	private SapDao sapDao;
	
	@Override
	public void sendReplenishmentItems(Object[] params) {
		// TODO Auto-generated method stub
		sapDao.sendReplenishmentItems(params);
	}
	
	@Override
	public void updateReplenishmentItems() {
		// TODO Auto-generated method stub
		sapDao.updateReplenishmentItems();
	}

	@Override
	public void transferReplenishmentItems(
			 final List<MrProductFreeze> mrProductFreezes) {
		int transferNum = sapDao.sendReplenishmentItemsNew(mrProductFreezes);
		
		Long startSaving = System.currentTimeMillis();
		sapDao.batchUpdateMrProductFreezeFlag(mrProductFreezes);
		Long endSaving = System.currentTimeMillis();
		LOG.info("修改冻结表耗时："+(endSaving - startSaving));
		
		/**调整之前，是每向接口表里面写入100条记录，就将这100条记录的标志位由"T"更新为"C"
		 * 而调整之后，是将所有的数据都写入接口表之后，才更改记录的标志位。
		 * 将mr_product_freeze表中所有满足下传SAP调整的补货记录都以标识位"T"写入interface_replenishment
		 * 后，再一次性将表中所有标志位为"T"的记录更新为"C"
		 */	
//		sapDao.updateReplenishmentItems();
		
		LOG.info("成功下传的补货申请数为:" + transferNum);
	}
	
}
