/*
 * @(#)CallBackForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.math.BigDecimal;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-9
 */
public class CallBackForm {
	
	private String batchId;
	
	private String outerTradeNo;
	
	private BigDecimal money;
	
	private Long paymentId;
	
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getOuterTradeNo() {
		return outerTradeNo;
	}

	public void setOuterTradeNo(String outerTradeNo) {
		this.outerTradeNo = outerTradeNo;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	@Override
	public String toString() {
		return "CallBackForm [batchId=" + batchId + ", outerTradeNo=" + outerTradeNo + ", money=" + money + ", paymentId=" + paymentId + "]@"+this.hashCode()+"";
	}
	
	
	
	
}
