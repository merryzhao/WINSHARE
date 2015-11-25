/*
 * @(#)Tag.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.tag;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.winxuan.ec.model.category.Category;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-8
 */
@Entity
@Table(name="tag")
public class Tag {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=Category.class)
	private Set<Category> categoryList;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="tag",targetEntity=TagItem.class)
	private Set<TagItem> itemList;

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

	public Set<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(Set<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Set<TagItem> getItemList() {
		return itemList;
	}

	public void setItemList(Set<TagItem> itemList) {
		this.itemList = itemList;
	}

}
