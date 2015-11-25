/*
 * @(#)OrderShipperInfoMatchException.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.exception;

import com.winxuan.ec.model.order.Order;

/**
 * 订单匹配承运商信息异常
 * @author  wangbiao
 * @version 1.0
 * date 2015-1-15
 */
public class OrderShipperInfoMatchException extends OrderInitException {

	private static final long serialVersionUID = -4547425285081891865L;

	private String errorMessage;
	
	public OrderShipperInfoMatchException(Order order, String message) {
		super(order, message);
		this.errorMessage = message;
	}

	@Override
	public String getMessage() {
		return this.errorMessage;
	}
	
	

}
