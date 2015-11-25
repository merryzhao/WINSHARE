/*
 * @(#)CustomerFavoriteTag.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-1
 */
@Entity
@Table(name = "customer_favorite_tag")
public class CustomerFavoriteTag implements Serializable{

	public static final int FAVORITE_TAG_RECOMMEND_QUANTITY = 3;

	public static final String DEFAULT_TAG_NAME = "\u60f3\u770b\u7684";
	
	private static final long serialVersionUID = -6638931967823757873L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;

	@Column(name = "tagname")
	private String tagName;

	@Column(name = "createtime")
	private Date createTime;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "customer_favorite_tag_relation", joinColumns = { @JoinColumn(name = "tag") }, inverseJoinColumns = { @JoinColumn(name = "favorite") })
	private Set<CustomerFavorite> favoriteList;

	public CustomerFavoriteTag() {
	}

	public CustomerFavoriteTag(Customer customer, String tagName) {
		this.customer = customer;
		this.tagName = tagName;
		this.createTime = new Date();
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

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Set<CustomerFavorite> getFavoriteList() {
		return favoriteList;
	}

	public void setFavoriteList(Set<CustomerFavorite> favoriteList) {
		this.favoriteList = favoriteList;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void addFavorite(CustomerFavorite favorite) {
		if (favoriteList == null) {
			favoriteList = new HashSet<CustomerFavorite>();
		}
		favoriteList.add(favorite);
	}

	public void removeFavorite(CustomerFavorite favorite) {
		if (favoriteList != null) {
			favoriteList.remove(favorite);
		}
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
		if (!(obj instanceof CustomerFavoriteTag)) {
			return false;
		}
		CustomerFavoriteTag other = (CustomerFavoriteTag) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
}
