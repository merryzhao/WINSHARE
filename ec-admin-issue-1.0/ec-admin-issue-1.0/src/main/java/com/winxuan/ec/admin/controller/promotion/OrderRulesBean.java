/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.promotion;

import java.math.BigDecimal;
import java.util.List;
/**
 *  
 * 
 * @author df.rsy
 * @version 1.0,2011-9-22
 */
public class OrderRulesBean {
	private BigDecimal minAmount;
	private Long manner;
    private List<OrderPresnetBean> orderPresnetBeans;
	public void setOrderPresnetBeans(List<OrderPresnetBean> orderPresnetBeans) {
		this.orderPresnetBeans = orderPresnetBeans;
	}
	public List<OrderPresnetBean> getOrderPresnetBeans() {
		return orderPresnetBeans;
	}
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}
	public BigDecimal getMinAmount() {
		return minAmount;
	}
	public void setManner(Long manner) {
		this.manner = manner;
	}
	public Long getManner() {
		return manner;
	}
	 
}
