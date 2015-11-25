/*
 * @(#)CustomerNotify
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
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author huangyixiang
 * @version 1.0,2011-10-24
 */
@Entity
@Table(name = "customer_notify")
public class CustomerNotify implements Serializable{


	private static final long serialVersionUID = -3847649620417179714L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "addstatus")
	private Code addStatus;

	@Column(name = "addsellprice")
	private BigDecimal addSellPrice;
	
	@Column(name = "addlistprice")
	private BigDecimal addListPrice;
   
	@Column(name="expectedprice")
	private BigDecimal expectedprice;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;

	
	@Column(name = "addtime")
	private Date addTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sort")
	private Code sort;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;
	
	@Column(name = "isnotified")
	private boolean isNotified;


	public boolean isNotified() {
		return isNotified;
	}

	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

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

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Code getAddStatus() {
		return addStatus;
	}

	public Long getAddStatusCode() {
		return getAddStatus().getId();
	}

	public void setAddStatus(Code addStatus) {
		this.addStatus = addStatus;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Code getCurrentStatus() {
		return productSale.getSaleStatus();
	}

	public Long getCurrentStatusCode() {
		return getCurrentStatus().getId();
	}

	public BigDecimal getCurrentPrice() {
		return productSale.getSalePrice();
	}

	public BigDecimal getCurrentDiscount() {
		return productSale.getDiscount();
	}

	public Code getSort() {
		return sort;
	}

	public void setSort(Code sort) {
		this.sort = sort;
	}

	public BigDecimal getAddSellPrice() {
		return addSellPrice;
	}

	public void setAddSellPrice(BigDecimal addSellPrice) {
		this.addSellPrice = addSellPrice;
	}

	public BigDecimal getAddListPrice() {
		return addListPrice;
	}

	public void setAddListPrice(BigDecimal addListPrice) {
		this.addListPrice = addListPrice;
	}

	public Long getProductSaleId() {
		return getProductSale().getId();
	}

	public String getImageUrl() {
		return getProductSale().getProduct().getImageUrlFor110px();
	}

	public String getName() {
		return getProductSale().getProduct().getName();
	}

	public String getUrl() {
		return getProductSale().getProduct().getUrl();
	}

	public BigDecimal getListPrice() {
		return getProductSale().getProduct().getListPrice();
	}

	public BigDecimal getSalePrice() {
		return getProductSale().getSalePrice();
	}

	public BigDecimal getDiscount() {
		return getProductSale().getDiscount();
	}
	
	public BigDecimal getExpectedprice() {
		return expectedprice;
	}

	public void setExpectedprice(BigDecimal expectedprice) {
		this.expectedprice = expectedprice;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CustomerNotify)) {
			return false;
		}
		CustomerNotify other = (CustomerNotify) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
