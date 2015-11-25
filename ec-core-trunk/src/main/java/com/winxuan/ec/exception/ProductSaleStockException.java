/*
 * @(#)ProductSaleStockException.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.exception;

import java.io.Serializable;

/**
 * @description
 * 
 * @author YangJun
 * @version 1.0, 2013-7-29
 */
public class ProductSaleStockException extends ProductException {

	private static final long	serialVersionUID	= 3206921742694168150L;

	public ProductSaleStockException(Serializable businessObject, String message) {
		super(businessObject, message);
	}

}
