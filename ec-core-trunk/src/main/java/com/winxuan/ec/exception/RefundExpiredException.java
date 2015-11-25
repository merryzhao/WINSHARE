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
public class RefundExpiredException extends BusinessException{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4634021962353255849L;

	public RefundExpiredException(Serializable businessObject, String message) {
		super(businessObject, message);
	}
	
	public ReturnOrder getReturnOrder(){
		return (ReturnOrder) getBusinessObject();
	}


}
