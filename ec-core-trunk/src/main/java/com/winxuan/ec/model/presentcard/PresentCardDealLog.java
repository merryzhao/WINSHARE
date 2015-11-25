/*
 * @(#)PresentCardDealLog.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.presentcard;

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

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.User;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-8-29
 */
@Entity
@Table(name = "presentcard_deal_log")
public class PresentCardDealLog implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 497658711306562949L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "createdate")
	private Date createDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User operator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "presentcard")
	private PresentCard presentCard;
	/**
	 * 交易金额(正为使用，负为退还)
	 */
	@Column(name = "dealmoney")
	private BigDecimal dealMoney;

	/**
	 * 礼品卡当前余额
	 */
	@Column(name = "currentbalance")
	private BigDecimal currentBalance;

	/**
	 * 当前礼品卡的校验码
	 */
	@Column(name = "verifycode")
	private String verifyCode;
	
	/**
	 * 交易类型
	 */
	@Column(name = "type")
	private Long type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	public BigDecimal getDealMoney() {
		return dealMoney;
	}

	public void setDealMoney(BigDecimal dealMoney) {
		this.dealMoney = dealMoney;
	}

	public PresentCard getPresentCard() {
		return presentCard;
	}

	public void setPresentCard(PresentCard presentCard) {
		this.presentCard = presentCard;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	

	
}
