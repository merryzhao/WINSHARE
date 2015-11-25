/*
 * @(#)OrderStockException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.product.ProductSale;

/**
 * description
 * 
 * @author cast911
 * @version 1.0,2011-11-18
 */
public class OrderCalculateException extends BusinessException {

	private static final long serialVersionUID = -5320174757271884657L;

	private ProductSale productSale;
	private int count;


	public OrderCalculateException(ProductSale productSale,int count) {
		super(productSale, null);
		this.productSale = productSale;
		this.count = count;
	}

	public String getMessage() {
		return   productSale + " 需要可用量"+count+ ",实际可用量"
				+ productSale.getAvalibleQuantity();
	}


}
