/*
 * @(#)OrderException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import java.io.Serializable;

import com.winxuan.ec.model.returnorder.ReturnOrder;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-7-14
 */
public class UnsupportBankException extends BusinessException{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6438375246362870754L;

	public UnsupportBankException(Serializable businessObject, String message) {
		super(businessObject, message);
	}
	
	public ReturnOrder getReturnOrder(){
		return (ReturnOrder) getBusinessObject();
	}


}
