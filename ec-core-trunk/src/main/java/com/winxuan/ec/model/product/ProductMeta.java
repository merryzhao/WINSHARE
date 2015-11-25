/*
 * @(#)ProductMeta.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.code.Code;
import com.winxuan.framework.util.StringUtils;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-7
 */

@Entity
@Table(name = "product_meta")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductMeta implements Serializable{

	/**
	 * 商品重量metaId
	 */
	public static final Long META_WEIGHT=73L;
	
	public static final Long META_EDITOR_RECOMMENDATION = 9L;
	public static final Long INTRODUCTION = 10L;
	
	/**
	 * 书的作者相关信息
	 */
	public static final int META_BOOK_TRANSLATOR = 49;
	public static final int META_BOOK_CHIEF_EDITOR = 79;
	public static final int META_BOOK_OTHER_EDITOR = 142;
	public static final int META_BOOK_EDITOR = 80;
	public static final int META_BOOK_COUNTRY = 140;
	public static final int META_BOOK_DYNASTY = 141;
	
	/**
	 * 电子书页数id
	 */
	public static final Long META_EBOOK_PAGE_COUNT=117L;
	
	private static final long serialVersionUID = -6042818052787901604L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;
	
	@Column
	private String description;	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="type")
	private Code type;

	@Column(name = "allownull")
	private boolean allowNull;

	@Column
	private int length;

	@Column(name = "defaultvalue")
	private String defaultValue;

	@Column
	private boolean available;

	@Column(name = "isshow")
	private boolean show;
	
	@Column
	private Integer category;
	

	/**
	 * 冗余字段，不记录到数据库
	 */
	@Transient
	private String value;
	
	/**
	 * 冗余字段，不记录到数据库
	 */
	@Transient
	private String err;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productMeta", targetEntity = ProductMetaEnum.class)
	@OrderBy("index")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductMetaEnum> enumList;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="open")
    private Code open;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public boolean isAllowNull() {
		return allowNull;
	}

	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getValue() {
		return StringUtils.isNullOrEmpty(value)?this.defaultValue:value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Set<ProductMetaEnum> getEnumList() {
		return enumList;
	}

	public void setEnumList(Set<ProductMetaEnum> enumList) {
		this.enumList = enumList;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public void addEnum(ProductMetaEnum metaEnum) {
		if (enumList == null) {
			enumList = new HashSet<ProductMetaEnum>();
		}
		int maxIndex = -1;
		for (ProductMetaEnum existedEnum : enumList) {
			int index = existedEnum.getIndex();
			maxIndex = maxIndex < index ? index : maxIndex;
		}
		metaEnum.setIndex(maxIndex + 1);
		metaEnum.setProductMeta(this);
		enumList.add(metaEnum);
	}

	public void removeEnum(ProductMetaEnum metaEnum) {
		if (enumList != null) {
			enumList.remove(metaEnum);
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}
	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

    public Code getOpen() {
        return open;
    }

    public void setOpen(Code open) {
        this.open = open;
    }
}
