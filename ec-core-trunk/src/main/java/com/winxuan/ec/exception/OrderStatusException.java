/*
 * @(#)OrderStatusException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;

/**
 * 订单状态异常，在涉及订单状态判断和处理时，如果不符合逻辑，抛出此异常
 * 
 * @author liuan
 * @version 1.0,2011-7-14
 */
public class OrderStatusException extends OrderException {

	private static final long serialVersionUID = 1391968845646194057L;

	private Code[] expectedStatus;

	/**
	 * 
	 * @param order
	 *            订单
	 * @param expectedStatus
	 *            期望的订单状态
	 */
	public OrderStatusException(Order order, Code[] expectedStatus) {
		super(order, null);
		this.expectedStatus = expectedStatus;
	}

	public String getMessage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getOrder()).append(" 当前操作允许的状态为:");
		if (expectedStatus == null) {
			buffer.append("null");
		} else {
			for (Code anExpectedStatus : expectedStatus) {
				buffer.append(anExpectedStatus).append(",");
			}
			buffer.deleteCharAt(buffer.length() - 1);
		}
		buffer.append(",实际的状态为:").append(getOrder().getProcessStatus());
		return buffer.toString();
	}

	public Code[] getExpectedStatus() {
		return expectedStatus;
	}
}
