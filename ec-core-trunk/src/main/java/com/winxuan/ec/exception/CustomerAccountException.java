/*
 * @(#)CustomerAccountException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.customer.CustomerAccount;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-15
 */
public class CustomerAccountException extends BusinessException {

	private static final long serialVersionUID = 5153971506704139857L;

	public CustomerAccountException(CustomerAccount account, String message) {
		super(account,message);
	}

}
