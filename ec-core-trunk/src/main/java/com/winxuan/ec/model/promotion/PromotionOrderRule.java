/*
 * @(#)PromotionOrderRule.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.promotion;

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

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.present.PresentBatch;

/**
 * 促销活动中订单相关的活动规则
 * @author  yuhu
 * @version 1.0,2011-9-13
 */
@Entity
@Table(name = "promotion_orderrule")
public class PromotionOrderRule implements Serializable{

	private static final long serialVersionUID = 2373665891502299596L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_promotion")
	private Promotion promotion;
	
	@Column(name = "minamount")
	private BigDecimal minAmount;
	
	@Column(name = "maxamount")
	private BigDecimal maxAmount;
	
	@Column
	private BigDecimal amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_area")
	private Area area;
	
	@Column(name = "deliveryfee")
	private BigDecimal deliveryFee;
	
	@Column(name = "remitdeliveryfee")
	private Boolean remitDeliveryFee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_presentbatch")
	private PresentBatch presentBatch;
	
	@Column(name = "presentnum")
	private Integer presentNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Boolean getRemitDeliveryFee() {
		return remitDeliveryFee;
	}

	public void setRemitDeliveryFee(Boolean remitDeliveryFee) {
		this.remitDeliveryFee = remitDeliveryFee;
	}

	public PresentBatch getPresentBatch() {
		return presentBatch;
	}

	public void setPresentBatch(PresentBatch presentBatch) {
		this.presentBatch = presentBatch;
	}

	public Integer getPresentNum() {
		return presentNum;
	}

	public void setPresentNum(Integer presentNum) {
		this.presentNum = presentNum;
	}
	
	
}
