/*
 * @(#)OrderStockException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;


/**
 * description
 * 
 * @author cast911
 * @version 1.0,2011-11-18
 */
public class ProductSaleIsNullException extends BusinessException {

	private static final long serialVersionUID = -5320174757271884657L;


	private String productSaleId;

	public ProductSaleIsNullException(String productSaleId) {
		super(productSaleId, null);
		this.productSaleId = productSaleId;
	}

	public String getMessage() {
		return  productSaleId+",商品不存在!";
	}


}
