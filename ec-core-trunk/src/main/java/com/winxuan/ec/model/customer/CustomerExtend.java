/*
 * @(#)CustomerAccount.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
@Entity
@Table(name = "customer_extend")
public class CustomerExtend implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2286158214215249800L;
	
	@Id
	@Column(name = "customer")
	@GeneratedValue(generator = "customerFK")
	@GenericGenerator(name = "customerFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "customer"))
	private Long id;
	
	
	@OneToOne(mappedBy = "customerExtend", targetEntity = Customer.class,fetch=FetchType.LAZY)
	private Customer customer;

	@Column(name = "paymobile")
	private String payMobile;
	
	@Column(name = "paypassword")
	private String payPassword;
	
	@Column(name = "pay_email")
	private String payEmail;
	
	

	public String getPayEmail() {
		return payEmail;
	}

	public void setPayEmail(String payEmail) {
		this.payEmail = payEmail;
	}

	public String getPayMobile() {
		return payMobile;
	}

	public void setPayMobile(String payMobile) {
		this.payMobile = payMobile;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
