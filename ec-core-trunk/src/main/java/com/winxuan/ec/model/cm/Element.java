/*
 * @(#)Element.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.cm;

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

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.MetaValue;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-26
 */
@Entity
@Table(name = "cm_element")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Element implements Serializable {

	private static final long serialVersionUID = 2255388718562499072L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private int sort;

	@Any(metaColumn = @Column(name = "datatype"), fetch = FetchType.LAZY)
	@AnyMetaDef(idType = "long", metaType = "string", metaValues = {
			@MetaValue(targetEntity = Link.class, value = "L"),
			@MetaValue(targetEntity = ProductSale.class, value = "P"),
			@MetaValue(targetEntity = News.class, value = "N"),
			@MetaValue(targetEntity = Text.class, value = "T") })
			@JoinColumn(name = "content")
			private Content content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fragment")
	private Fragment fragment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}

	@Override
	public int hashCode() {
		int param = MagicNumber.THIRTY_TWO;
		int result = 1;
		result = param * result + (fragment == null ? 0 : fragment.hashCode());
		result = param * result + (content == null ? 0 : content.hashCode());
		result = param * result + sort;
		result = param * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Element other = (Element) obj;
		if (fragment == null) {
			if (other.fragment != null){
				return false;
			}
		} else if (!fragment.equals(other.fragment)){
			return false;
		}
		if (content == null) {
			if (other.content != null){
				return false;
			}
		} else if (!content.equals(other.content)){
			return false;
		}
		if (id == null) {
			if (other.id != null){
				return false;
			}
		} else if (!id.equals(other.id)){
			return false;
		}
		if (sort != other.sort){
			return false;
		}
		return true;
	}



}
