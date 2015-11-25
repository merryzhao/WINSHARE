/*
 * @(#)ProductSalePriceLog.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.product;

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
import com.winxuan.ec.model.user.User;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-11-9
 */
@Entity
@Table(name = "product_sale_log")
public class ProductSaleLog implements Serializable {

	private static final long serialVersionUID = -7338314487105003677L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;

	@Column(name = "originalprice")
	private BigDecimal originalPrice;

	@Column(name = "newprice")
	private BigDecimal newPrice;
	
	@ManyToOne
	@JoinColumn(name = "originalstatus")
	private Code originalStatus;

	@ManyToOne
	@JoinColumn(name = "newstatus")
	private Code newStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator")
	private User operator;

	@Column(name = "updatetime")
	private Date updateTime;

    @Column(name = "remark")
    private String remark;

	public ProductSaleLog() {
	}

	public ProductSaleLog(ProductSale productSale, BigDecimal newPrice,Code newStatus,
			User operator) {
		this.productSale = productSale;
		this.originalPrice = productSale.getSalePrice();
		this.newPrice = newPrice;
		this.originalStatus = productSale.getSaleStatus();
		this.newStatus = newStatus;
		this.operator = operator;
		this.updateTime = new Date();
	}

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

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}

	public Code getOriginalStatus() {
		return originalStatus;
	}

	public void setOriginalStatus(Code originalStatus) {
		this.originalStatus = originalStatus;
	}

	public Code getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Code newStatus) {
		this.newStatus = newStatus;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
