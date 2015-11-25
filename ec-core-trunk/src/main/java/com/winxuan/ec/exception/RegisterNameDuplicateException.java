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
public class RegisterNameDuplicateException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 706994938930508456L;

	public RegisterNameDuplicateException() {
		super();
	}

	public RegisterNameDuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegisterNameDuplicateException(String message) {
		super(message);
	}

	public RegisterNameDuplicateException(Throwable cause) {
		super(cause);
	}
	
}
