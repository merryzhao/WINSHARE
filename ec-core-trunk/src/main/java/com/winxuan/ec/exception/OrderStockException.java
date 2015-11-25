/*
 * @(#)OrderStockException.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.exception;

import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.ProductSale;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-11-18
 */
public class OrderStockException extends OrderException {

	private static final long serialVersionUID = -5320174757271884657L;

	private OrderItem orderItem;
	
	private ProductSale productSale;

	public OrderStockException(OrderItem orderItem) {
		super(orderItem.getOrder(), null);
		this.orderItem = orderItem;
		this.productSale = orderItem.getProductSale();
		this.productSale.toString();
	}

	public String getMessage() {
		ProductSale productSale =this.productSale;
		return getOrder() + " 中 " + productSale + " 需要可用量"
				+ orderItem.getPurchaseQuantity() + ",实际可用量"
				+ productSale.getAvalibleQuantity();
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

}
