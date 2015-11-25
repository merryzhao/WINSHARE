/*
 * @(#)ErpOrderTransferException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.exception.erp;

/**
 * 订单传输异常
 * 在订单或者订单项不符合规则或则传输失败时抛出此异常
 * @author  HideHai
 * @version 1.0,2011-9-4
 */
public class ErpOrderTransferException extends Exception{

	private static final long serialVersionUID = 8342413378844376480L;

	public ErpOrderTransferException() {
		super();
	}

	public ErpOrderTransferException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErpOrderTransferException(String message) {
		super(message);
	}

	public ErpOrderTransferException(Throwable cause) {
		super(cause);
	}

	

}

