/*
 * @(#)QuartzException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.exception.quartz;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-15
 */
public class QuartzException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6157390910273976282L;
	
	public QuartzException() {
		super();
	}

	public QuartzException(String message, Throwable cause) {
		super(message, cause);
	}

	public QuartzException(String message) {
		super(message);
	}

	public QuartzException(Throwable cause) {
		super(cause);
	}

}

