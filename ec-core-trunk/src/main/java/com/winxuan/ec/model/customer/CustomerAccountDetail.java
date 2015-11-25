/*
 * @(#)CustomerAccountDetail.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.User;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
@Entity
@Table(name="customer_account_detail")
public class CustomerAccountDetail implements Serializable{
	
	private static final long serialVersionUID = 3580399110327347282L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account")
	private CustomerAccount account;
    
    @Column(name="usetime")
	private Date useTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="type")
	private Code type;
    
    @Column
	private BigDecimal amount;
    
    @Column(name="balance")
	private BigDecimal balance;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="operator")
	private User operator;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="_order")
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerAccount getAccount() {
		return account;
	}

	public void setAccount(CustomerAccount account) {
		this.account = account;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}
	public boolean getHasAdd(){
		return amount.compareTo(BigDecimal.ZERO)>=0 ? true : false;
	}
	public BigDecimal getAbsAmount(){
		return amount.abs();
	}

}
