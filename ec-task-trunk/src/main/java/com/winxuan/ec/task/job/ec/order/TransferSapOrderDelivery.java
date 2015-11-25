/*
 * @(#)TransferUserInvoice.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.service.sap.SapOrderService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 回传订单状态
 * 
 * @author HideHai
 * @version 1.0,2011-9-5
 */
@Component("transferSapOrderDelivery")
public class TransferSapOrderDelivery implements Serializable, TaskAware,
		TaskConfigure {

	private static final long serialVersionUID = 3900038750853679102L;
	private static final Log LOG = LogFactory.getLog(TransferSapOrderDelivery.class);
	int errorTimes = MagicNumber.HUNDRED;
	@Autowired
	private SapOrderService sapOrderService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Autowired
	private NotifyService notifyService;

	@Override
	public void start() {
		int errorCount = 0;
		List<String> orderIds = sapOrderService.listSapOrderDelivery();
		if (orderIds == null || orderIds.isEmpty()) {
			LOG.info("no sap order found!");
			return;
		}
		for (String orderId : orderIds) {
			hibernateLazyResolver.openSession();
			try {
				sapOrderService.processFetchOrder(orderId);
			} catch (Exception e) {
				errorCount++;
				LOG.error(String.format("%s 在处理过程中发生异常:%s",
						orderId, e.getMessage()), e);
			}
			hibernateLazyResolver.releaseSession();
			if (errorCount > errorTimes) {
				doNotify(String.format("订单状态回传因异常次数过多(%s)中断! ", errorTimes));
				break;
			}
		}
	}

	@Override
	public String getName() {
		return "transferSapOrderDelivery";
	}

	@Override
	public String getDescription() {
		return "SAP->EC转储发货回传";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public int getNotifyLevel() {
		return TaskConfigure.LEVEL_ALL;
	}

	@Override
	public void doNotify(String... msg) {
		notifyService.notify(this, msg[0]);
	}

}
