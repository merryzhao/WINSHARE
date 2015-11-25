/*
 * @(#)ProductExtend.java
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
 * @version 1.0,2011-7-7
 */
@Entity
@Table(name="product_extend")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductExtend implements Serializable{
   
	private static final long serialVersionUID = -4993895211533912683L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product")
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="meta")
	private ProductMeta productMeta;
	
	@Column
	private String name;
	
	@Column(name="_value")
	private String value;
	
	@Column(name="index_")
	private int index;
	
	@Column(name="isshow")
	private boolean show;

	public ProductExtend() {
		super();
	}
	
	public ProductExtend(ProductMeta meta,Product product) {
		super();
		this.value = meta.getValue();
		this.name = meta.getName();
		this.productMeta = meta;
		this.index=0;
		this.show = meta.isShow();
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductMeta getProductMeta() {
		return productMeta;
	}

	public void setProductMeta(ProductMeta productMeta) {
		this.productMeta = productMeta;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
}
