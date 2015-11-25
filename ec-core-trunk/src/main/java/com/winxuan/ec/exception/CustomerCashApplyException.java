/*
 * @(#)CustomerCashApplyException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.customer.CustomerCashApply;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-11-24
 */
public class CustomerCashApplyException extends BusinessException {

	private static final long serialVersionUID = -7886631609012224978L;

	public CustomerCashApplyException(CustomerCashApply customerCashApply,
			String message) {
		super(customerCashApply, message);
	}

}
