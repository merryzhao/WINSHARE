/*
 * @(#)TransferArea.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.task.job.ec.area;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.erp.ErpOrderService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * @description
 * 
 * @author YangJun
 * @version 1.0, 2013-8-16
 */
@Component("transferArea")
public class TransferArea implements TaskAware, Serializable {
	private static final long serialVersionUID = -842767187513285135L;
	
	private static final Log LOG = LogFactory.getLog(TransferArea.class);

	@Autowired
	private ErpOrderService erpOrderService;
	
	@Override
	public String getName() {
		return "transferArea";
	}

	@Override
	public String getDescription() {
		return "回传区域、配送公司信息";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		try {
			this.erpOrderService.processErpArea();
		} catch (Exception e) {
			LOG.error("area transfer (ERP -> EC) exception: " + e.getMessage(), e);
		}
		try {
			this.erpOrderService.processErpDeliveryCompany();
		} catch (Exception e) {
			LOG.error("deliverycompany transfer (ERP -> EC) exception: " + e.getMessage(), e);
		}
		try {
			this.erpOrderService.processErpDeliveryArea();
		} catch (Exception e) {
			LOG.error("delivery_area transfer (ERP -> EC) exception: " + e.getMessage(), e);
		}
	}

}
