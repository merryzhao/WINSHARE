/*
 * @(#)CustomerPointsException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.customer.CustomerAddress;

/**
 * description
 * 
 * @author cast911
 * @version 1.0,2011-10-23
 */
public class AddressPaymentException extends BusinessException {

	private static final long serialVersionUID = 9186270718893530529L;
	private CustomerAddress customerAddress;
	public AddressPaymentException(CustomerAddress customerAddress) {
		super(customerAddress, null);
		this.customerAddress = customerAddress;
	}

	public String getMessage() {
		return customerAddress.getAddress()+"不支持"+customerAddress.getPayment().getName() +"支付方式!";
	}

}
