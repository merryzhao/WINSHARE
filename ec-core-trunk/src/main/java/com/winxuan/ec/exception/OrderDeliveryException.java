/*
 * @(#)OrderDeliveryException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.order.Order;

/**
 * 订单发货异常，在订单进行发货时，数据不正确抛出此异常
 * 
 * @author liuan
 * @version 1.0,2011-7-14
 */
public class OrderDeliveryException extends OrderException {

	private static final long serialVersionUID = -8620687265063985582L;

	public OrderDeliveryException(Order order, String message) {
		super(order, message);
	}

}
