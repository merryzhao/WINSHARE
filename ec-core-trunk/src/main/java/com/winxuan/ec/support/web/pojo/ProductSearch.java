/*
 * @(#)ProductSearch.java
 *
 * Copyright 2011 Xinhua Online; Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.pojo;

import java.math.BigDecimal;

/**
 * description
 * @author  huangyixiang
 * @version 2011-12-15
 */
public class ProductSearch {
	private String code;
	private Integer availableQuantity;
	private Boolean hasPicture;
	private Long onShelfDate;
	private Long publishDate;
	private Boolean promotionPrice;
	private Boolean complex;
	private BigDecimal discount;
	private Long storageType;
	private Long psid;
	
	


	@Override
	public int hashCode(){
		int result = 1; 
		final int prime = 31;
		result = prime * result + (code == null ? 0 : code.hashCode()); 
		result = prime * result + (availableQuantity == null ? 0 : availableQuantity.hashCode());
		result = prime * result + (hasPicture == null ? 0 : hasPicture.hashCode());
		result = prime * result + (onShelfDate == null ? 0 : onShelfDate.hashCode());
		result = prime * result + (publishDate == null ? 0 : publishDate.hashCode());
		result = prime * result + (promotionPrice == null ? 0 : promotionPrice.hashCode());
		result = prime * result + (complex == null ? 0 : complex.hashCode());
		result = prime * result + (discount == null ? 0 : discount.hashCode());
		result = prime * result + (storageType == null ? 0 : storageType.hashCode());
		return result;
	}
	
	
	public Long getStorageType() {
		return storageType;
	}


	public void setStorageType(Long storageType) {
		this.storageType = storageType;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public Boolean getHasPicture() {
		return hasPicture;
	}
	public void setHasPicture(Boolean hasPicture) {
		this.hasPicture = hasPicture;
	}
	public Long getOnShelfDate() {
		return onShelfDate;
	}
	public void setOnShelfDate(Long onShelfDate) {
		this.onShelfDate = onShelfDate;
	}
	public Long getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Long publishDate) {
		this.publishDate = publishDate;
	}
	public Boolean getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(Boolean promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public Boolean getComplex() {
		return complex;
	}
	public void setComplex(Boolean complex) {
		this.complex = complex;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public Long getPsid() {
		return psid;
	}


	public void setPsid(Long psid) {
		this.psid = psid;
	}
}
