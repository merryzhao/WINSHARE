/*
 * @(#)ErpOrderItem.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.model.erp;

import java.io.Serializable;

/**
 * EC订单订单项
 * @author  HideHai
 * @version 1.0,2011-9-29
 */
public class ErpOrderItem implements Serializable{

	private static final long serialVersionUID = 1447756306177648567L;
	
	private Long orderId;
	
	private Long commodity;
	
	private int quantity;
	
	private int outOfStockQuantity;

	public Long getCommodity() {
		return commodity;
	}

	public void setCommodity(Long commodity) {
		this.commodity = commodity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantiry) {
		this.quantity = quantiry;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getOutOfStockQuantity() {
		return outOfStockQuantity;
	}

	public void setOutOfStockQuantity(int outOfStockQuantity) {
		this.outOfStockQuantity = outOfStockQuantity;
	}
	

}

