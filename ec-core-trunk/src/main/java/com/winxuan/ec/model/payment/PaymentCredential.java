/*
 * @(#)PaymentLog.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.model.user.Customer;

/**
 * 支付凭据
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "payment_credential")
public class PaymentCredential implements Serializable{

	private static final long serialVersionUID = -1874161884909133975L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customer")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="payment")
	private Payment payment;

	@Column(name = "paytime")
	private Date payTime;

	@Column
	private BigDecimal money;

	@Column(name = "outerid")
	private String outerId;
	
	@Column
	private String payer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="operator")
	private User operator;
	
	@Column
	private String remark;

	@OneToMany(mappedBy = "credential", cascade=CascadeType.ALL,targetEntity = OrderPayment.class)
	private Set<OrderPayment> orderPaymentList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<OrderPayment> getOrderPaymentList() {
		return orderPaymentList;
	}

	public void setOrderPaymentList(Set<OrderPayment> orderPaymentList) {
		this.orderPaymentList = orderPaymentList;
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
	
	@Override
	public String toString(){
		return String.format("凭证[OuterID:%s, Payer:%s, Money:%s]", outerId,payer,money);
	}
}
