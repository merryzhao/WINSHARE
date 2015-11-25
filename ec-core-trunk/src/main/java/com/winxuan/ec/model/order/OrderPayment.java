/*
 * @(#)OrderPayment.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
import com.winxuan.ec.model.payment.PaymentCredential;

/**
 * 订单支付信息
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "order_payment")
public class OrderPayment implements Serializable{

	private static final long serialVersionUID = 9194848162202425812L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment")
	private Payment payment;

	@Column(name = "money")
	private BigDecimal payMoney;

	@Column(name = "ispay")
	private boolean pay;

	@Column(name = "deliverymoney")
	private BigDecimal deliveryMoney = BigDecimal.ZERO;

	@Column
	private BigDecimal returnMoney = BigDecimal.ZERO;

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "paymenttime")
	private Date payTime;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "credential")
	private PaymentCredential credential;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public BigDecimal getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(BigDecimal deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public BigDecimal getReturnMoney() {
		return returnMoney == null?BigDecimal.ZERO:returnMoney;
	}

	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isPay() {
		return pay;
	}

	public void setPay(boolean pay) {
		this.pay = pay;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public PaymentCredential getCredential() {
		return credential;
	}

	public void setCredential(PaymentCredential credential) {
		this.credential = credential;
	}
	
	/**
	 * 可退款金额
	 * @return
	 */
	public BigDecimal getCanRefundMoney() {
		if (isPay()) {
			return getPayMoney().subtract(getReturnMoney());
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * 是否下单预付款方式
	 * @return
	 */
	public boolean isPrePay(){
		if(Payment.ACCOUNT.equals(getPayment().getId())
				|| Payment.COUPON.equals(getPayment().getId())
				|| Payment.GIFT_CARD.equals(getPayment().getId())){
			return true;
		}
		return false;
	}

}
