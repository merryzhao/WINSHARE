/*
 * @(#)CustomerPointsException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author cast911
 * @version 1.0,2011-10-23
 */
public class CustomerAddressException extends BusinessException {

	private static final long serialVersionUID = 9186270718893530529L;
	Customer customer;
    String message;
	public CustomerAddressException(Customer customer) {
		super(customer, null);
		this.customer = customer;
	}

	public CustomerAddressException(Customer customer, String message) {
		super(customer.getId(), message);
		this.customer = customer;
		this.message = message;
	}

	public String getMessage() {
		return this.customer+": "+this.message;
	}

}
