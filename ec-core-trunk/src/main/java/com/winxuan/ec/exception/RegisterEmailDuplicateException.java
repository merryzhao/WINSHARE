/*
 * @(#)RegisterNameDuplicateException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-7-13
 */
public class RegisterEmailDuplicateException extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = 133098285209862462L;

	public RegisterEmailDuplicateException() {
		super();
	}

	public RegisterEmailDuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegisterEmailDuplicateException(String message) {
		super(message);
	}

	public RegisterEmailDuplicateException(Throwable cause) {
		super(cause);
	}
	
}
