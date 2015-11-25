/*
 * @(#)OrderInvoiceService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.framework.pagination.Pagination;

/**
 * 订单发票
 * @author  HideHai
 * @version 1.0,2011-9-5
 */
public interface OrderInvoiceService {
	
	OrderInvoice get(String id);
	
	void save(OrderInvoice orderInvoice);
	
	void remove(OrderInvoice orderInvoice);
	
	List<OrderInvoice> find(Map<String, Object> parameters,Pagination pagination);
	
	/**
	 * 查询发票
	 * 
	 * @param parameters
	 * <br/>
	 *            id:String[] 订单号 <br/>
	 *            outerId:String[] 外部订单号<br/>
	 *            startDate:Date 下单开始日期<br/>
	 *            endDate:Date 下单结束日期<br/>
	 *            customerName:String 用户名<br/>
	 * @param pagination
	 * @return objArray[0]订单，objArray[1]发票（可能为空）
	 */
	List<Object[]> findOrderInvoice(Map<String, Object> parameters,
			Pagination pagination);
	
	/**
	 * 创建发票<br/>
	 * issues #422
	 * @param orderList
	 *            订单列表
	 * @param orderInvoice
	 *            发票
	 * @param mode
	 *            开票类型，参见OrderInvoice常量定义
	 * @throws OrderStatusException
	 *             如果订单不处于已发货状态，抛出此异常
	 */
	void createOrderInvoice(OrderInvoice orderInvoice)
			throws OrderStatusException;
	
	/**
	 *  取消订单关联的发票
	 * @param orderInvoice
	 */
	void cancel(Order order);
	
	/**
	 * 完成下传
	 * @param orderInvoice
	 */
	void completeTranfer(OrderInvoice orderInvoice);
	
	
	/**
	 * 得到订单可以开的发票金额
	 *   如果用户为代理商，可以为不超过订单码洋的金额
	 *   其他类型则根据订单内的计算方法计算。
	 * @param order
	 * @param desireValue
	 * @return
	 */
	BigDecimal calcInvoiceMoney(Order order,BigDecimal desireValue);
	
	void update(OrderInvoice orderInvoice);

}

