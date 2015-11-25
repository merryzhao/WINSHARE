/*
 * @(#)BusinessException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import java.io.Serializable;

/**
 * 业务异常基类
 * 
 * @author liuan
 * @version 1.0,2011-11-18
 */
public abstract class BusinessException extends Exception {

	private static final long serialVersionUID = -1511006574202802731L;

	private Serializable businessObject;

	public BusinessException(Serializable businessObject, String message) {
		super(businessObject + " " + message);
		this.businessObject = businessObject;
	}

	public Serializable getBusinessObject() {
		return businessObject;
	}
}
