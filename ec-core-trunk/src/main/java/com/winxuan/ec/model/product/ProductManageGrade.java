/*
 * @(#)ProductImage.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.product;

import java.io.Serializable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.User;

/**
 * 商品管理分级
 * 
 * @author yuhu
 * @version 1.0,2012-5-3
 */
@Entity
@Table(name="product_sale_manage_grade")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductManageGrade implements Serializable{

	private static final long serialVersionUID = -656996287982298902L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productsale")
	private ProductSale productSale;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="grade")
	private Code grade;
	
	@Column(name="purchasediscount")
	private Integer purchaseDiscount;
	
	@Column(name="hotsellingstart")
	private Date hotSellingStart;
	
	@Column(name="hotsellingend")
	private Date hotSellingEnd;
	
	@Column
	private String remark;
	
	@Column(name="operatetime")
	private Date operateTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="operator")
	private User operator;

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

	public Code getGrade() {
		return grade;
	}

	public void setGrade(Code grade) {
		this.grade = grade;
	}

	public Integer getPurchaseDiscount() {
		return purchaseDiscount;
	}

	public void setPurchaseDiscount(Integer purchaseDiscount) {
		this.purchaseDiscount = purchaseDiscount;
	}

	public Date getHotSellingStart() {
		return hotSellingStart;
	}

	public void setHotSellingStart(Date hotSellingStart) {
		this.hotSellingStart = hotSellingStart;
	}

	public Date getHotSellingEnd() {
		return hotSellingEnd;
	}

	public void setHotSellingEnd(Date hotSellingEnd) {
		this.hotSellingEnd = hotSellingEnd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}
	
	
}
