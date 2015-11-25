/*
 * @(#)DeliveryInfo.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.delivery;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 配送说明
 * 
 * @author liuan
 * @version 1.0,2011-7-14
 */
@Entity
@Table(name = "deliveryinfo")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeliveryInfo implements Serializable{

	private static final long serialVersionUID = -4599590142748401656L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliverytype")
	private DeliveryType deliveryType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="area")
	private Area area;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliveryFeeType")
	private Code deliveryFeeType;

	@Column(name="deliveryfeerule")
	private String deliveryFeeRule;

	@Column
	private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dc")
	private Code dc;
	
	@Column
	private boolean available;
	

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	public Code getDeliveryFeeType() {
		return deliveryFeeType;
	}

	public void setDeliveryFeeType(Code deliveryFeeType) {
		this.deliveryFeeType = deliveryFeeType;
	}

	public String getDeliveryFeeRule() {
		return deliveryFeeRule;
	}

	public void setDeliveryFeeRule(String deliveryFeeRule) {
		this.deliveryFeeRule = deliveryFeeRule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Code getDc() {
		return dc;
	}

	public void setDc(Code dc) {
		this.dc = dc;
	}

	/**
	 *  根据订单码洋获取运费
	 * @param salePrice
	 * @return
	 */
	public BigDecimal getDeliveryFee(BigDecimal listPrice){
		Code deliveryType = getDeliveryFeeType();
		BigDecimal fee = BigDecimal.ZERO;
		BigDecimal max= null;
		BigDecimal min = null;
		if(deliveryType.getId().equals(Code.DELIVERY_FEE_FIXED)){
			fee =new BigDecimal(getDeliveryFeeRule()).setScale(1);
		}else if(deliveryType.getId().equals(Code.DELIVERY_FEE_PERCENT)){
			String[] rules = getDeliveryFeeRule().split(",");
			if(rules.length==MagicNumber.THREE){
				fee = listPrice.multiply(new BigDecimal(rules[0]));
				max = new BigDecimal(rules[1]).setScale(2);
				min = new BigDecimal(rules[2]).setScale(2);
				fee = fee.compareTo(max) > 0 ? max : fee;
				fee = fee.compareTo(min) < 0 ? min : fee;
			}
		}
		return fee.setScale(1, BigDecimal.ROUND_HALF_UP).setScale(2);
	}

}
