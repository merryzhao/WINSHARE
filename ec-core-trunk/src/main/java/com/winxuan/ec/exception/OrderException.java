/*
 * @(#)OrderException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.order.Order;

/**
 * 订单异常基类
 * 
 * @author liuan
 * @version 1.0,2011-7-14
 */
public abstract class OrderException extends BusinessException {

	private static final long serialVersionUID = -7981076269310670933L;

	public OrderException(Order order, String message) {
		super(order, message);
	}

	public Order getOrder() {
		return (Order) getBusinessObject();
	}

}
