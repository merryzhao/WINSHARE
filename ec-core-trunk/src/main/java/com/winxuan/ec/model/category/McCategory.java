/*
 * @(#)McCategory.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.winxuan.ec.model.tree.Tree;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-7
 */
@Entity
@Table(name="mccategory")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class McCategory extends Tree<McCategory>{
	
	private static final long serialVersionUID = -6107111711043450398L;

	@Id
	@GeneratedValue(generator = "categoryGenerator")     
	@GenericGenerator(name = "categoryGenerator", strategy = "assigned")  
	private String id;
	
	@Column
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent")
	private McCategory parent;
	
	@Column(name="index_")
	private int index;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = McCategory.class)
	@OrderBy("index")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<McCategory> children;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "category_mccategory", joinColumns = { @JoinColumn(name = "mccategory") }, inverseJoinColumns = { @JoinColumn(name = "category") })
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<Category> categories;
	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public McCategory getParent() {
		return parent;
	}

	public void setParent(McCategory parent) {
		this.parent = parent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Set<McCategory> getChildren() {
		return children;
	}

	public void setChildren(Set<McCategory> children) {
		this.children = children;
	}

	public Category getCategory() {
		return this.getCategories() == null ? null : this.getCategories().get(0);
	}

	public void setCategory(Category category) {
		if(this.getCategories() == null){
			this.setCategories(new ArrayList<Category>());
			this.getCategories().add(category);
		} else {
			this.getCategories().set(0, category);
		}
		
	}

	public boolean isAvailable() {
		return true;
	}

}
