package com.winxuan.ec.task.job.ec.settle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.settle.SapOrderItem;
import com.winxuan.ec.service.bill.BillService;
import com.winxuan.ec.service.settle.InnerSettleService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;

/**
 * @description: TODO
 * 
 * @createtime: 2014-2-10 上午10:35:40
 * 
 * @author zenghua
 * 
 * @version 1.0
 */
@Component("innerSettleJob")
public class InnerSettleJob implements TaskAware, Serializable, TaskConfigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8662301344397933001L;

	private static final Log LOG = LogFactory.getLog(InnerSettleJob.class);

	@Autowired
	private InnerSettleService innerSettleService;

	@Autowired
	private BillService billService;

	@Override
	public int getNotifyLevel() {
		return 0;
	}

	@Override
	public void doNotify(String... msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "innerSettleJob";
	}

	@Override
	public String getDescription() {
		return "内部结算2.0";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {

		int deliverNum = 0;
		int settleNum = 0;
		BigDecimal deliveryMoney = BigDecimal.ZERO;
		BigDecimal settleMoney = BigDecimal.ZERO;

		List<SapOrderItem> sapOrderItems = innerSettleService.getAllOrderItem();
		if (CollectionUtils.isNotEmpty(sapOrderItems)) {
			Bill bill = innerSettleService.createBill(sapOrderItems.get(0));
			for (SapOrderItem sapOrderItem : sapOrderItems) {
				// 统计发货数量
				deliverNum += sapOrderItem.getDeliveryQuantity();
				// 统计结算数量
				settleNum += sapOrderItem.getSettle();
				deliveryMoney = deliveryMoney.add(sapOrderItem.getListPrice()
						.multiply(
								new BigDecimal(sapOrderItem
										.getDeliveryQuantity())));
				settleMoney = settleMoney.add(sapOrderItem.getListPrice()
						.multiply(new BigDecimal(sapOrderItem.getSettle())));
				innerSettleService.sendSapOrderItem(new Object[] {
						sapOrderItem.getOuterOrder(),
						sapOrderItem.getOuterItem(), sapOrderItem.getDc(),
						sapOrderItem.getReturnFlag(),
						sapOrderItem.getCustomer(), sapOrderItem.getSapid(),
						sapOrderItem.getQuantity(), sapOrderItem.getOrder(),
						sapOrderItem.getOrderItem(), sapOrderItem.getChannel(),
						sapOrderItem.getDeliveryQuantity(),
						sapOrderItem.getSettle(), sapOrderItem.getListPrice(),
						bill.getId(), sapOrderItem.getDeliveryQuantity(),
						new Date()
						});
			}

			bill.setAllotment(deliveryMoney);
			bill.setSettlement(settleMoney);
			bill.setInvoice(String.valueOf(deliverNum));
			bill.setList(String.valueOf(settleNum));
			billService.update(bill);
			LOG.info("inserting sapOrderItem's size is : "
					+ sapOrderItems.size());
		}

		LOG.info("InnerSettleJob is finished!!!");
	}

}
