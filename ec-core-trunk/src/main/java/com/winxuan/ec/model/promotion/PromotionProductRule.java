/*
 * @(#)PromotionProductRule.java
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

import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;

/**
 * 促销活动中商品相关的活动规则
 * @author  yuhu
 * @version 1.0,2011-9-13
 */
@Entity
@Table(name = "promotion_productrule")
public class PromotionProductRule implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_promotion")
	private Promotion promotion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_productsale")
	private ProductSale productSale;
	
	@Column(name = "saleprice")
	private BigDecimal salePrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="producttype")
	private Code productType;
	
	@Column(name = "productnum")
	private Integer productNum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_category")
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="categorydistype")
	private Code categoryDisType;
	
	@Column(name = "categorydiscount")
	private Integer categoryDiscount;

	@Column(name = "productnums")
	private Integer productNums;
	
	

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

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Code getProductType() {
		return productType;
	}

	public void setProductType(Code productType) {
		this.productType = productType;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Code getCategoryDisType() {
		return categoryDisType;
	}

	public void setCategoryDisType(Code categoryDisType) {
		this.categoryDisType = categoryDisType;
	}

	public Integer getCategoryDiscount() {
		return categoryDiscount;
	}

	public void setCategoryDiscount(Integer categoryDiscount) {
		this.categoryDiscount = categoryDiscount;
	}
	public Integer getProductNums() {
		return productNums;
	}

	public void setProductNums(Integer productNums) {
		this.productNums = productNums;
	}
}
