/*
 * @(#)OrderStatusException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import java.io.Serializable;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.OrderInvoice;

/**
 * 发票异常类
 * @author HideHai
 *
 */
public class OrderInvoiceException extends BusinessException {

	private static final long serialVersionUID = -3071961101017000464L;

	public OrderInvoiceException(Serializable businessObject, Code[] expectedStatus) {
		super(businessObject, null);
	}
	
	public OrderInvoice getInvoice(){
		return (OrderInvoice) getBusinessObject();
	}
}
