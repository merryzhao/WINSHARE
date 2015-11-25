/*
 * @(#)TransferNewOrder.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.dao.ec.EcDao;
import com.winxuan.ec.task.service.erp.ErpOrderService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * ec order 2 erp.
 * 
 * @author HideHai
 * @version 1.0,2011-9-4
 */
@Component("transferNewOrder")
public class TransferNewOrder implements Serializable, TaskAware, TaskConfigure {

	private static final long serialVersionUID = 1775830669144953591L;
	private static final Log LOG = LogFactory.getLog(TransferNewOrder.class);

	private static final Integer PAGE_SIZE = Integer
			.valueOf(MagicNumber.FIVE_HUNDRED);
	private static Map<String, Object> parameters = new HashMap<String, Object>();
	private static List<String> errorOrder = new ArrayList<String>();
	private static List<String> noTransferOrder = new ArrayList<String>();
	static {
		parameters.put("supplyType",
				new Long[] { Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL });
		parameters
				.put("storageType",
						new Long[] {
								Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM,
								Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_PLATFORM });
		parameters.put("transferResult", Code.EC2ERP_STATUS_NONE);
		parameters.put("processStatus",
				new Long[] { Code.ORDER_PROCESS_STATUS_WAITING_PICKING });
		parameters.put("virtual", false);
		parameters.put("initStatus", 550002L);
	}

	@Autowired
	private ErpOrderService erpOrderService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private EcDao ecDao;

	public void start() {
		noTransferOrder.clear();
		noTransferOrder = ecDao.findOrderNoTransfer();
		int currentPage = MagicNumber.ONE;
		int i = MagicNumber.ZERO;
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(currentPage);
		pagination.setPageSize(PAGE_SIZE);
		List<Order> needTransferOrder = null;
		errorOrder.clear();
//		do {
//			Date timePara = DateUtils.addMinutes(new Date(), -5);
//			parameters.put("endLastProcessTime", timePara);
//			if ((needTransferOrder = orderService.find(parameters,
//					Short.valueOf("1"), pagination)) != null
//					&& !needTransferOrder.isEmpty()) {
//				hibernateLazyResolver.openSession();
//				for (Order order : needTransferOrder) {
//					if (noTransferOrder.contains(order.getId())) {
//						continue;
//					}
//					if (!ecDao.isTransfer(order.getId())) {
//						continue;
//					}
//					try {
//						if (!errorOrder.contains(order.getId())) {
//							erpOrderService.transferOrder(order);
//						}
//					} catch (Exception e) {
//						errorOrder.add(order.getId());
//						LOG.error(
//								order.getId() + ": transfer exception: "
//										+ e.getMessage(), e);
//					}
//				}
//				hibernateLazyResolver.releaseSession();
//			}
//			++i;
//		} while (i < MagicNumber.THREE);
		hibernateLazyResolver.openSession();
		Order order = orderService.get("20150209705731");
		try {
			erpOrderService.transferOrder(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hibernateLazyResolver.releaseSession();
		if (errorOrder.size() > MagicNumber.FIVE) {
			doNotify();
		}
	}

	@Override
	public String getName() {
		return "transferNewOrder";
	}

	@Override
	public String getDescription() {
		return "EC订单下传ERP.2013-10-12";
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
