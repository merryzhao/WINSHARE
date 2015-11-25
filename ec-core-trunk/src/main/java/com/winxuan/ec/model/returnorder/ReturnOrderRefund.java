/*
 * @(#)ReturnOrderRefund.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.returnorder;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.payment.Payment;

/**
 * 退换货订单退款项
 * 
 * @author liuan
 * @version 1.0,2011-9-14
 */
@Entity
@Table(name = "returnorder_refund")
public class ReturnOrderRefund implements Serializable{

	private static final long serialVersionUID = 2408707590456621217L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "returnorder")
	private ReturnOrder returnOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment")
	private Payment payment;

	@Column(name = "refundvalue")
	private BigDecimal refundValue;

	@Column(name = "refundobjectid")
	private String refundObjectId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReturnOrder getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(ReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public BigDecimal getRefundValue() {
		return refundValue;
	}

	public void setRefundValue(BigDecimal refundValue) {
		this.refundValue = refundValue;
	}

	public String getRefundObjectId() {
		return refundObjectId;
	}

	public void setRefundObjectId(String refundObjectId) {
		this.refundObjectId = refundObjectId;
	}

}
