/*
 * @(#)CustomerPayee.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
 * @author  liuan
 * @version 1.0,2011-10-22
 */
@Entity
@Table(name = "customer_payee")
public class CustomerPayee implements Serializable{

	private static final long serialVersionUID = 47402408179660912L;

	@Id
	@Column(name = "customer")
	@GeneratedValue(generator = "customerFK")
	@GenericGenerator(name = "customerFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "customer"))
	private Long id;

	@OneToOne(mappedBy = "payee", targetEntity = Customer.class, fetch = FetchType.LAZY)
	private Customer customer;
	
	@Column
	private String alipay;
	
	@Column(name = "alipayname") 
	private String alipayName; 
	
	@Column
	private String tenpay;
	
	@Embedded
	private CustomerBankAccount bankAccount;
	
	@Embedded
	private CustomerPostAccount postAccount;

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

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getTenpay() {
		return tenpay;
	}

	public void setTenpay(String tenpay) {
		this.tenpay = tenpay;
	}

	public CustomerBankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(CustomerBankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public CustomerPostAccount getPostAccount() {
		return postAccount;
	}

	public void setPostAccount(CustomerPostAccount postAccount) {
		this.postAccount = postAccount;
	}

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}
	
}
