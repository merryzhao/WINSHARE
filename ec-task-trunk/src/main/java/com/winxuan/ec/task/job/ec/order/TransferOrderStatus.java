/*
 * @(#)TransferUserInvoice.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.exception.erp.ErpOrderTransferException;
import com.winxuan.ec.task.model.erp.ErpOrder;
import com.winxuan.ec.task.service.erp.ErpOrderService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.framework.util.StringUtils;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 回传订单状态
 * 
 * @author HideHai
 * @version 1.0,2011-9-5
 */
@Component("transferOrderStatus")
public class TransferOrderStatus implements Serializable, TaskAware,
		TaskConfigure {

	private static final long serialVersionUID = -4617482302293022284L;
	private static final Log LOG = LogFactory.getLog(TransferOrderStatus.class);
	private static final int ERRORTIMES = MagicNumber.HUNDRED;
	@Autowired
	private ErpOrderService erpOrderService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Autowired
	private NotifyService notifyService;

	@Override
	public void start() {
		long s = System.currentTimeMillis();
		int errorCount = 0;
		List<ErpOrder> processOrder = new ArrayList<ErpOrder>();
		List<ErpOrder> erpOrders = erpOrderService.fetchOrderState();
		if (erpOrders == null || erpOrders.isEmpty()) {
			LOG.info("no erp order found!");
			return;
		}
		List<String> blockFailedOrders = erpOrderService
				.fetchNeedProcessOrder(); // 超过24小时未处理的拦截订单
		List<ErpOrder> blockOrders = erpOrderService.fetchErpRejectOrder(); // ERP12小时内的拦截订单
		LOG.info(String.format(
				"blockFailedOrders.size: %s ; blockOrders.size:%s",
				blockFailedOrders.size(), blockOrders.size()));
		for (ErpOrder erpOrder : erpOrders) {
			if (processOrder.contains(erpOrder)) {
				continue;
			}
			hibernateLazyResolver.openSession();
			processOrder.add(erpOrder);
			try {
				if (erpOrder.isOrder()) {
					processOrder(erpOrder, blockFailedOrders, blockOrders);
				} else if (erpOrder.isReturnOrder()) {
					processReturnOrder(erpOrder);
				} else {
					LOG.error(String.format("在处理过程中发现未知类型订单：%s",
							erpOrder.getOrderId()));
				}
			} catch (Exception e) {
				errorCount++;
				LOG.error(String.format("%s 在处理过程中发生异常:%s",
						erpOrder.getOrderId(), e.getMessage()), e);
			}
			if (errorCount > ERRORTIMES) {
				doNotify(String.format("订单状态回传因异常次数过多(%s)中断! ", ERRORTIMES));
				break;
			}
			hibernateLazyResolver.releaseSession();
		}
		List<String> blockFailedOrdersNotDeal = erpOrderService
				.fetchNeedProcessOrder(); // 超过24小时未处理的且没有推送09状态的拦截订单
		for (String orderId : blockFailedOrdersNotDeal) {
			hibernateLazyResolver.openSession();
			try {
				erpOrderService.blockFiledprocessErpDelivery(orderId);
			} catch (Exception e) {
				LOG.error(
						String.format("%s 在处理过程中发生异常:%s", orderId,
								e.getMessage()), e);
			}
			hibernateLazyResolver.releaseSession();
		}
		LOG.info(String.format("Order Count:%s ; process time: %s",
				erpOrders.size() + blockFailedOrdersNotDeal.size(),
				(System.currentTimeMillis() - s)));
	}

	/**
	 * 处理EC订单
	 * 
	 * @param erpOrder
	 * @throws Exception
	 */
	private void processOrder(ErpOrder erpOrder,
			List<String> blockFailedOrders, List<ErpOrder> blockOrders)
			throws Exception {
		validateErpOrderDelivery(erpOrder);
		if (blockFailedOrders.contains(erpOrder.getOrderId())) {
			erpOrderService.processUnsuccessBlockOrder(erpOrder);
			return;
		}
		// 如果订单在24小时拦截订单里面设置拦截人，并进行拦截记录处理
		if (!blockOrders.isEmpty()) {
			for (ErpOrder order : blockOrders) {
				if (erpOrder.getOrderId().equals(order.getOrderId())) {
					erpOrder.setCuser(order.getCuser());
					erpOrderService.processErpBlockOrder(erpOrder);
				}
			}
		}
		erpOrderService.processFetchOrder(erpOrder);
	}

	/**
	 * 处理EC退货单
	 * 
	 * @param erpOrder
	 * @throws Exception
	 */
	private void processReturnOrder(ErpOrder erpOrder) throws Exception {
		List<OrderPayment> newPayments = erpOrderService
				.fetchReturnOrder(erpOrder);
		erpOrderService.processReturnOrderReplace(newPayments, erpOrder);
	}

	/**
	 * 验证回传ERP接口数据是否符合回传的EC订单条件
	 * 
	 * @param erpOrder
	 * @throws ErpOrderTransferException
	 */
	private void validateErpOrderDelivery(ErpOrder erpOrder)
			throws ErpOrderTransferException {
		if (StringUtils.isNullOrEmpty(erpOrder.getState())) {
			throw new ErpOrderTransferException(String.format(
					"%s status is null!", erpOrder.getOrderId()));
		}
		if (erpOrder.getState().equals(ErpOrder.STATUS_DELIVERY)) {
			if (StringUtils.isNullOrEmpty(erpOrder.getCode())) {
				throw new ErpOrderTransferException(String.format(
						"%s code is null!", erpOrder.getOrderId()));
			} else if (StringUtils.isNullOrEmpty(erpOrder.getDeliveryCompany())) {
				throw new ErpOrderTransferException(String.format(
						"%s delivery company is null!", erpOrder.getOrderId()));
			} else if (erpOrder.getFhrq() == null) {
				throw new ErpOrderTransferException(String.format(
						"%s delivery time is null!", erpOrder.getOrderId()));
			}
		}
	}

	@Override
	public String getName() {
		return "transferOrderStatus";
	}

	@Override
	public String getDescription() {
		return "ERP->EC订单状态回传.2012-2-17";
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
