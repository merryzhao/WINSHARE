/*
 * @(#)OrderPayForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.admin.controller.order;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * description
 * 
 *@author chenlong
 *@version 1.0,2011-8-2
 */
public class OrderPayForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String customer;
	
	@NotNull
	private Long payment;
	
	@NotEmpty
	@NotBlank
	private String payTime;
	@NotEmpty
	@NotBlank
	private String money;
	
	@NotEmpty
	@NotBlank
	private String outerId;
	
	private String payer;
	
	private String remark;
	
	
	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customer) {
		this.customer = customer;
	}


	public Long getPayment() {
		return payment;
	}


	public void setPayment(Long payment) {
		this.payment = payment;
	}


	public String getPayTime() {
		return payTime;
	}


	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}


	public String getMoney() {
		return money;
	}


	public void setMoney(String money) {
		this.money = money;
	}


	public String getOuterId() {
		return outerId;
	}


	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	
	public String getPayer() {
		return payer;
	}

	
	public void setPayer(String payer) {
		this.payer = payer;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}



	
}
