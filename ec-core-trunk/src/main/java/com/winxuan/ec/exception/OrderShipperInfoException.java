/*
 * @(#)OrderShipperInfoException.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.exception;

import com.winxuan.ec.model.order.Order;

/**
 * 订单发货/取消回传DMS系统异常
 * @author  wangbiao
 * @version 1.0
 * date 2015-1-16
 */
public class OrderShipperInfoException extends OrderException {

	private static final long serialVersionUID = 775465343728186947L;

	public OrderShipperInfoException(Order order, String message) {
		super(order, message);
	}

}
