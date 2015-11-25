/*
 * @(#)ProductDescription.java
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

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * 商品描述信息
 * 
 * @author liuan
 * @version 1.0,2011-7-7
 */
@Entity
@Table(name = "product_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductDescription implements Serializable {

	private static final long serialVersionUID = -2150602847221282165L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meta")
	private ProductMeta productMeta;

	@Column(name = "name")
	private String name;

	@Column(name = "content")
	private String content;

	@Column(name = "index_")
	private int index;

	@Column
	private String digest;

	public ProductDescription() {
		super();
	}

	public ProductDescription(ProductMeta meta, Product product) {
		super();
		this.content = meta.getValue();
		this.name = meta.getName();
		this.productMeta = meta;
		this.index = 0;
		this.digest = "";
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ProductMeta getProductMeta() {
		return productMeta;
	}

	public void setProductMeta(ProductMeta productMeta) {
		this.productMeta = productMeta;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	/**
	 * 去除所有的html标签,包括 &nbsp
	 * 
	 * @return String,
	 */
	public String getNoTagContent() {
		if (StringUtils.isBlank(this.content)) {
			return this.content;
		}
		String result = Jsoup.clean(this.content, Whitelist.none());
		return result.replaceAll("&nbsp;", "");
	}

}
