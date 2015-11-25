package com.winxuan.ec.service.settle;

import java.util.List;

import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.settle.SapOrderItem;

/**
 * @description: TODO
 *
 * @createtime: 2014-2-10 下午2:13:34
 *
 * @author zenghua
 *
 * @version 1.0
 */

public interface InnerSettleService {
	/**
	 * 获取发货订单数据
	 * @return
	 */
	List<SapOrderItem> getDeliveryOrderItem(String startDate, String endDate);
	/**
	 * 获取退货订单数据
	 * @return
	 */
	List<SapOrderItem> getReturnOrderItem(String startDate, String endDate);
	/**
	 * 获取拒收订单数据
	 * @return
	 */
	List<SapOrderItem> getRejectOrderItem(String startDate, String endDate);
	
	/**
	 * 获取全部要下传sap的订单数据
	 * @return
	 */
	List<SapOrderItem> getAllOrderItem();
	
	/**
	 * 把结算的订单写入接口表
	 * @param params
	 */
	void sendSapOrderItem(Object[] params);
	
	/**
	 * 创建结算记录
	 * @param params
	 * @return
	 */
	Bill createBill(SapOrderItem sapOrderItem);
	
}
