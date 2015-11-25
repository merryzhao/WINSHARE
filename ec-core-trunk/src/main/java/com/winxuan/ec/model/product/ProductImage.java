/*
 * @(#)ProductImage.java
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
@Table(name="product_image")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductImage implements Serializable{
    /**
     * 原图
     */
    public static final short TYPE_ORIGINAL = 0;

	/**
	 * 小图
	 */
	public static final short TYPE_SMALL = 1;
	
	/**
	 * 中图
	 */
	public static final short TYPE_MEDIUM = 2;
	
	/**
	 * 大图
	 */
	public static final short TYPE_LARGE = 3;
	
	/**
	 * 插图
	 */
	public static final short TYPE_ILLUSTRATION = 4;
	
	public static final short TYPE_SIZE_600 = 9;
	public static final short TYPE_SIZE_200 = 10;
	public static final short TYPE_SIZE_160 = 11;
	public static final short TYPE_SIZE_130 = 12;
	public static final short TYPE_SIZE_110 = 13;
	public static final short TYPE_SIZE_55 = 14;
	public static final short TYPE_SIZE_350 = 16;
	
	/**
	 * 封底图片
	 */
	public static final short TYPE_BACK_COVER = 15;
	
	/**
	 * 版权页图片
	 */
	public static final short TYPE_COLOPHON = 7;
	
	/**
	 * 条形码图片
	 */
	public static final short TYPE_BARCODE = 17;
	
	/**
	 * 默认图片
	 */
	public static final String DEFAULT_IMAGE_URL = "http://static.winxuancdn.com/goods/sml_blank.jpg";
	
	private static final long serialVersionUID = -656996287982298902L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product")
	private Product product;
	
	@Column
	private String url;
	
	@Column
	private short type;
	
	@Column(name="index_")
	private int index;
	
	@Column
	private String digest;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	
}
