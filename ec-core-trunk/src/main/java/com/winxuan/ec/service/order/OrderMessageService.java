package com.winxuan.ec.service.order;

import java.util.List;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.ParentOrder;
import com.winxuan.ec.model.returnorder.ReturnOrder;


/**
 * 订单消息服务
 * 包含:短信
 * @author heyadong
 * @version 1.0, 2012-6-7
 */

public interface OrderMessageService {
	
	/**
	 * 订单取消
	 * 根据不同的取消原因发货不同的短信
	 * @param order
	 */
	void cancel(Order order);
	
	/**
	 * 发货-发送短信
	 * @param order
	 */
	void delivery(Order order);
	

	/**
	 * 改派发货公司-发送短信
	 * @param order
	 */
	void reassignmentDeliveryCompany(Order order);
	
	/**
	 * 订单退货
	 * @param returnOrder 退货订单
	 */
	void returnedPurchase(ReturnOrder returnOrder);
	
	/**
	 *  换货订单
	 * @param returnOrder
	 */
	void replace(ReturnOrder returnOrder);
	
	/**
	 * 根据短信状态重发短信
	 * @param orders
	 */
	void retry(List<Order> orders);
	
	/**
	 * 拆单之后立即发送不包含发货信息的拆单通知短信
	 */
	void splitOrderMessage(ParentOrder parentOrder);
}
