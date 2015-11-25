/*
 * @(#)TransferReplenishment.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.bill.BillItemSap;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.service.bill.BillService;
import com.winxuan.ec.task.service.sap.SapBillService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 内部结算勾单，下传SAP
 * 
 * @author yangxinyi
 * @version 1.0,2013-11-07
 */
@Component("orderSettleJob")
public class OrderSettleJob implements Serializable, TaskAware, TaskConfigure {

	private static final long serialVersionUID = -1055321689842131417L;
	private static final Log LOG = LogFactory.getLog(OrderSettleJob.class);
	
	@Autowired
	private BillService billService;

	@Autowired
	private SapBillService sapBillService;

	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;

	@Autowired
	private NotifyService notifyService;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "orderSettleJob";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "内部结算勾单审核后，下传SAP";
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		hibernateLazyResolver.openSession();
		List<Bill> bills = findConfirmedBills();
		for (Bill bill : bills) {
			//将SAP已开票的单项状态改为90009
			billService.updateBillStatusBySapInvoice(bill);
			
			//查找下传SAP的订单项
			List<BillItemSap> billItemSapList = billService
					.findSapBillItems(bill);
			for (BillItemSap billItemSap : billItemSapList) {
				sapBillService.sendBillItems(new Object[] {
						billItemSap.getOuterOrder(),
						billItemSap.getOuterItem(), billItemSap.getBill(),
						billItemSap.getReturnFlag(), billItemSap.getCustomer(),
						billItemSap.getSapid(), billItemSap.getQuantity(),
						billItemSap.getOrder(), billItemSap.getOrderItem(),
						billItemSap.getChannel(),
						billItemSap.getDeliveryQuantity(),
						billItemSap.getSettle(),
						billItemSap.getHistorySettle(),
						billItemSap.getListPrice() });
			}
			LOG.info("内部结算已写入SAP接口数量：" + billItemSapList.size());
			//更新结算单状态为已下传SAP
			billService.updateBillItemSapStatus(bill);
			bill.setStatus(new Code(Code.BILL_PROCESSED));
			billService.update(bill);
			LOG.info("内部结算下传SAP完成，更新BILL状态");
		}
		hibernateLazyResolver.releaseSession();
	}

	/**
	 * 搜索需要下传SAP的BILL
	 */
	private List<Bill> findConfirmedBills() {
		Long[] statusCodes = { Code.BILL_CONFIRMED };
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("statusCodes", statusCodes);
		return billService.queryByStatus(maps, null, (short) 0);
	}

	@Override
	public int getNotifyLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void doNotify(String... msg) {
		// TODO Auto-generated method stub
		notifyService.notify(this, String.format("%s 由于过多异常中断!", getName()));
	}

}
