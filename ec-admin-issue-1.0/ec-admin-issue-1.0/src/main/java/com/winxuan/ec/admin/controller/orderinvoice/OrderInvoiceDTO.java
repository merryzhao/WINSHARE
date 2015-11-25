/*
 * @(#)OrderInvoiceDTO.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.admin.controller.orderinvoice;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderInvoice;

/**
 * description
 * 
 *@author chenlong
 *@version 1.0,2011-8-30
 */
public class OrderInvoiceDTO {
	
	private Order order;
	
	private OrderInvoice orderInvoice;
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public OrderInvoice getOrderInvoice() {
		return orderInvoice;
	}
	
	public void setOrderInvoice(OrderInvoice orderInvoice) {
		if(orderInvoice==null){
			orderInvoice = new OrderInvoice();
		}
		this.orderInvoice = orderInvoice;
	}

}
