/*
 * @(#)Keenness.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.society;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import com.winxuan.ec.model.cm.Content;
import com.winxuan.ec.model.cm.News;
import com.winxuan.ec.model.product.ProductSale;

/**
 * 系统关键词
 * @author  HideHai
 * @version 1.0,2011-11-4
 */
@Entity
@Table(name="society_keenness")
public class Keenness {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String sensitive;
	
	@Column
	private String placid;
	
	@Column
	private boolean available;
	
	@Any(metaColumn = @Column(name = "datatype"), fetch = FetchType.LAZY)
	@AnyMetaDef(idType = "long", metaType = "string", metaValues = {
	@MetaValue(targetEntity = ProductSale.class, value = "P"),
	@MetaValue(targetEntity = News.class, value = "N")})
	@JoinColumn(name = "content")
	private Content content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSensitive() {
		return sensitive;
	}

	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}

	public String getPlacid() {
		return placid;
	}

	public void setPlacid(String placid) {
		this.placid = placid;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
	

}

