/*
 * @(#)ProductRelated.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.product;

import java.io.Serializable;

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

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-29
 */
@Entity
@Table(name = "product_recommendation")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductRecommendation implements Serializable{

	public static final short MODE_BUY = 1;
	public static final short MODE_VIEW = 2;
	
	private static final long serialVersionUID = 7797333896639752181L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recommendid")
	private ProductSale productSale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recommendation")
	private ProductSale recommendation;

	@Column
	private float preference;

	@Column
	private short mode;

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

	public ProductSale getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(ProductSale recommendation) {
		this.recommendation = recommendation;
	}

	public float getPreference() {
		return preference;
	}

	public void setPreference(float preference) {
		this.preference = preference;
	}

	public short getMode() {
		return mode;
	}

	public void setMode(short mode) {
		this.mode = mode;
	}
}
