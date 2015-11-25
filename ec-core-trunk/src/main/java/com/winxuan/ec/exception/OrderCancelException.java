/*
 * @(#)OrderCancelException.java
 *
 * Copyright 2015 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.exception;

import com.winxuan.ec.model.order.Order;

/**
 * description
 *  
 * @author YangJun
 * @version 1.0, 2015年1月14日
 */
public class OrderCancelException extends OrderException {

	private static final long serialVersionUID = -378082178277719368L;

	/**
	 * @param order
	 * @param message
	 */
	public OrderCancelException(Order order, String message) {
		super(order, message);
	}

}
