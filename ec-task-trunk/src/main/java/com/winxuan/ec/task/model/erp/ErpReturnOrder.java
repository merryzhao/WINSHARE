/*
 * @(#)ErpReturnOrder.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.model.erp;

import java.io.Serializable;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-30
 */
public class ErpReturnOrder implements Serializable{

	private static final long serialVersionUID = -9211995427984729214L;
	
	private String orderId;
	
	private String returnOrderId;
	
	private String erpReturnOrderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReturnOrderId() {
		return returnOrderId;
	}

	public void setReturnOrderId(String returnOrderId) {
		this.returnOrderId = returnOrderId;
	}

	public String getErpReturnOrderId() {
		return erpReturnOrderId;
	}

	public void setErpReturnOrderId(String erpReturnOrderId) {
		this.erpReturnOrderId = erpReturnOrderId;
	}

}

