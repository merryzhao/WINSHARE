/*
 * @(#)OrderInitException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.order.Order;

/**
 * 订单初始化异常，在下单时如果订单属性不完整或者属性错误，抛出此异常
 * 
 * @author liuan
 * @version 1.0,2011-7-14
 */
public class OrderInitException extends OrderException {

	private static final long serialVersionUID = -6210993425829589183L;

	public OrderInitException(Order order, String message) {
		super(order, message);
	}
}
