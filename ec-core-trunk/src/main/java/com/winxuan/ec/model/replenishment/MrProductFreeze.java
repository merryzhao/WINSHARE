/*
 * @(#)MrProductFreeze.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.replenishment;

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
import com.winxuan.ec.model.product.ProductSale;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-8-26
 */
@Entity
@Table(name = "mr_product_freeze")
public class MrProductFreeze {
	
	public static final int FLAG_FREEZED = 0;
	public static final int FLAG_UNFREEZED = 1;
	public static final int FLAG_SEND_SAP = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;
	
	/**
	 * 冻结起始时间
	 */
	@Column(name = "starttime")
	private Date startTime;
	
	/**
	 * 冻结结束时间
	 */
	@Column(name = "endtime")
	private Date endTime;
	
	@Column(name = "availablequantity")
	private int availableQuantity;
	
	@Column(name = "quantity")
	private int quantity;
	
	/**
	 * 冻结状态，0：冻结生效，商品不可再进入补货系统；1：冻结结束，商品可进入补货系统；2：已下传SAP
	 */
	@Column(name = "flag")
	private int flag;
	
	@Column(name = "createtime")
	private Date createTime;
	
	/**
	 * 发货仓库
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dc")
	private Code dc;
	
	/**
	 * 冻结类型：人工冻结、系统冻结等
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;
	
	/**
	 * 冻结原因描述
	 */
	@Column(name = "reason")
	private String reason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Code getDc() {
		return dc;
	}

	public void setDc(Code dc) {
		this.dc = dc;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	
}
