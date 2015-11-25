/*
 * @(#)TransferUserInvoice.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.service.returnorder.ReturnOrderService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.service.erp.ErpOrderService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-9-5
 */
@Component("transferReturnOrder")
public class TransferReturnOrder implements Serializable, TaskAware, TaskConfigure {

	private static final long serialVersionUID = -7986967602851197137L;
	private static final Log LOG = LogFactory.getLog(TransferReturnOrder.class);
	private static final Integer PAGE_SIZE = Integer.valueOf(MagicNumber.FIVE_HUNDRED);
	private static Map<String, Object> parameters = new HashMap<String, Object>();
	private static List<Long> errorReturnOrder = new ArrayList<Long>();
	static {
		parameters.put("needtransfers", true);
		parameters.put("transferred", false);
		parameters.put("storageType", new Long[] { Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM,
				Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_PLATFORM });
		parameters.put("virtual", false);
		parameters.put("status", new Long[] { Code.RETURN_ORDER_STATUS_AUDITED });
	}

	@Autowired
	private ErpOrderService erpOrderService;
	@Autowired
	private ReturnOrderService returnOrderService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Autowired
	private NotifyService notifyService;

	@Override
	public void start() {
		int currentPage = MagicNumber.ONE;
		int i = MagicNumber.ZERO;
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(currentPage);
		pagination.setPageSize(PAGE_SIZE);
		List<ReturnOrder> needTransferReturnOrder = null;
		errorReturnOrder.clear();
		do {
			if ((needTransferReturnOrder = returnOrderService.find(parameters, pagination)) != null && !needTransferReturnOrder.isEmpty()) {
				hibernateLazyResolver.openSession();
				for (ReturnOrder returnOrder : needTransferReturnOrder) {
					try {
						if (!errorReturnOrder.contains(returnOrder.getId())) {
							erpOrderService.transferReturnOrder(returnOrder);
						}
					} catch (Exception e) {
						LOG.error(returnOrder.getId() + ": transfer exception: " + e.getMessage(), e);
						errorReturnOrder.add(returnOrder.getId());
					}
				}
				hibernateLazyResolver.releaseSession();
			}
			++i;
		} while (i < MagicNumber.ONE);
		if (errorReturnOrder.size() > MagicNumber.FIVE) {
			doNotify();
		}
	}

	@Override
	public String getName() {
		return "transferReturnOrder";
	}

	@Override
	public String getDescription() {
		return "EC退换货订单";
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
		notifyService.notify(this, String.format("%s 由于过多异常中断!", getName()));
	}

}
