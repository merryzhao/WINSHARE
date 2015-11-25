/*
 * @(#)TransferUserInvoice.java
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.service.order.OrderInvoiceService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.model.erp.ErpOrderInvoice;
import com.winxuan.ec.task.service.erp.ErpOrderService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-5
 */
@Component("transferOrderInvoice")
public class TransferOrderInvoice implements Serializable, TaskAware,TaskConfigure {

	private static final long serialVersionUID = -4617482302293022284L;
	private static final Log LOG = LogFactory.getLog(TransferOrderInvoice.class);
	private static final Integer PAGE_SIZE = Integer.valueOf(40);
	private static List<String> errorOrder = new ArrayList<String>();
	private static Map<String, Object> parameters = new HashMap<String, Object>();
	static{
		parameters.put("transferred", false);
	}

	@Autowired
	private ErpOrderService erpOrderService;
	@Autowired
	private OrderInvoiceService orderInvoiceService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Autowired
	private NotifyService notifyService;

	@Override
	public String getName() {
		return "transferOrderInvoice";
	}

	@Override
	public String getDescription() {
		return "EC->ERP发票";
	}
	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		int currentPage = MagicNumber.ONE;
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(currentPage);
		pagination.setPageSize(PAGE_SIZE);
		List<OrderInvoice> needTransferInvoice = null;
		errorOrder.clear();
		hibernateLazyResolver.openSession();
		while((needTransferInvoice = orderInvoiceService.find(parameters, pagination))!=null
				&& !needTransferInvoice.isEmpty()){
			boolean flag = true;	
			for(OrderInvoice invoice : needTransferInvoice){
				try {
					if(!errorOrder.contains(invoice.getId())){
						flag = false;
						erpOrderService.transferOrderInvoice(invoice);
					}
				} catch (Exception e) {
					errorOrder.add(invoice.getId());
					LOG.error(invoice.getId() + ": transfers exception: "+e.getMessage(),e);
				}
			}
			if(!errorOrder.isEmpty() && flag){
				doNotify();
				break;
			}
		}
		hibernateLazyResolver.releaseSession();

		List<ErpOrderInvoice> eois = null;
		while((eois = this.erpOrderService.fetchOrderInvoice()) != null && !eois.isEmpty()){
			for(ErpOrderInvoice eoi : eois){
				try{
					String deliveryCode = eoi.getDeliveryCode();
					Integer deliveryCompany = eoi.getDeliveryCompanyId();
					Date deliveryTime = eoi.getDeliveryTime();
					String id = eoi.getId();
					this.erpOrderService.updateOrderInvoiceDelivery(deliveryCode, deliveryCompany, deliveryTime, id);
					this.erpOrderService.updateOrderInvoiceStatus(id);
				}catch(Exception e){
					LOG.error(eoi.getId() + " : updateOrderInvoiceDelivery Exception : " + e.getMessage(), e);
				}
			}
		}

		LOG.info(getName()+" over!");
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

