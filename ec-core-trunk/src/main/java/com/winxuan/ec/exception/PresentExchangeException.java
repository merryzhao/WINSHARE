/*
 * @(#)PresentExchangeException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-10-23
 */
public class PresentExchangeException extends PresentException{

	private static final long serialVersionUID = 1915301558858190919L;

	public PresentExchangeException() {
		super();
	}

	public PresentExchangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PresentExchangeException(String message) {
		super(message);
	}

	public PresentExchangeException(Throwable cause) {
		super(cause);
	}
	
}
