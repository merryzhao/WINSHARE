/*
 * @(#)CustomerFavorite.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-8-31
 */
@Entity
@Table(name = "customer_favorite")
public class CustomerFavorite implements Serializable{

	private static final long serialVersionUID = -6699403107565679622L;

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

	@Column(name = "addprice")
	private BigDecimal addPrice;

	@Column(name = "addtime")
	private Date addTime;

	@Column
	private boolean purchased;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sort")
	private Code sort;

	@ManyToMany(mappedBy = "favoriteList", fetch = FetchType.LAZY)
	private Set<CustomerFavoriteTag> tagList;

	@Transient
	private String defaultImageUrl;
	
	public CustomerFavorite() {
	}

	public CustomerFavorite(Customer customer, ProductSale productSale) {
		this.customer = customer;
		this.productSale = productSale;
		this.addStatus = productSale.getSaleStatus();
		this.addPrice = productSale.getEffectivePrice();
		this.sort = productSale.getProduct().getSort();
		this.addTime = new Date();
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

	public BigDecimal getAddPrice() {
		return addPrice;
	}

	public void setAddPrice(BigDecimal addPrice) {
		this.addPrice = addPrice;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
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

	public Set<CustomerFavoriteTag> getTagList() {
		return tagList;
	}

	public void setTagList(Set<CustomerFavoriteTag> tagList) {
		this.tagList = tagList;
	}

	public void addTag(CustomerFavoriteTag tag) {
		if (tagList == null) {
			tagList = new HashSet<CustomerFavoriteTag>();
		}
		tagList.add(tag);
		tag.addFavorite(this);
	}

	public void removeTag(CustomerFavoriteTag tag) {
		if (tagList != null) {
			tagList.remove(tag);
		}
		tag.removeFavorite(this);
	}

	public boolean contains(CustomerFavoriteTag tag) {
		return tagList == null ? false : tagList.contains(tag);
	}

	public String getTagNames() {
		if (tagList != null && !tagList.isEmpty()) {
			StringBuffer buffer = new StringBuffer();
			for (CustomerFavoriteTag tag : tagList) {
				buffer.append(tag.getTagName()).append(" ");
			}
			buffer.deleteCharAt(buffer.length() - 1);
			return buffer.toString();
		}
		return null;
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
		if (!(obj instanceof CustomerFavorite)) {
			return false;
		}
		CustomerFavorite other = (CustomerFavorite) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	public void setDefaultImageUrl(int imageType){
		Set<ProductImage> imageList = this.getProductSale().getProduct().getImageList();
		for(ProductImage productImage : imageList){
			if((int)productImage.getType() == imageType){
				this.defaultImageUrl = productImage.getUrl();
				break;
			}
		}
	}
	
	public String getDefaultImageUrl(){
		if(this.defaultImageUrl == null){
			this.defaultImageUrl = this.getProductSale().getProduct().getImageUrlFor160px();
		}
		return this.defaultImageUrl;
	}
	
	public BigDecimal getAverageRank(){
		return this.getProductSale().getAverageRank();
	}
}
