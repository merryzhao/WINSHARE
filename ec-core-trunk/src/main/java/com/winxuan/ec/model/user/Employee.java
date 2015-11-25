/*
 * @(#)Employee.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.winxuan.ec.model.authority.Resource;
import com.winxuan.ec.model.authority.ResourceGroup;
import com.winxuan.ec.model.report.Grid;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name = "user")
public class Employee extends User {

	public static final long SYSTEM = 6L;

	private static final long serialVersionUID = 1L;
	

	@Column
	private Long creator;
	
	@Column
	private Long updater;
	
	@Column(name = "updatetime")
	private Date updateTime;

	@OneToMany
	@JoinTable(name = "employee_grid", joinColumns = { @JoinColumn(name = "grid") }, inverseJoinColumns = { @JoinColumn(name = "employee") })
	private Set<Grid> favoriteGridList;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "employee_resourcegroup_relation", joinColumns = { @JoinColumn(name = "employee") }, inverseJoinColumns = { @JoinColumn(name = "resourcegroup") })
	private Set<ResourceGroup> resourceGroups;


	public Employee() {

	}

	public Employee(Long id) {
		setId(id);
	}
	
	
	public Set<ResourceGroup> getResourceGroups() {
		return resourceGroups;
	}

	public void setResourceGroups(Set<ResourceGroup> resourceGroups) {
		this.resourceGroups = resourceGroups;
	}
	
	public void addResourceGroup(ResourceGroup resourceGroup){
		if(resourceGroups == null){
			resourceGroups = new HashSet<ResourceGroup>();
		}
		resourceGroups.add(resourceGroup);
	}
	
	public void removeResourceGroup(ResourceGroup resourceGroup){
		if(resourceGroups != null){
			resourceGroups.remove(resourceGroup);
		}
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Set<Grid> getFavoriteGridList() {
		return favoriteGridList;
	}

	public void setFavoriteGridList(Set<Grid> favoriteGridList) {
		this.favoriteGridList = favoriteGridList;
	}

	public void addFavoriteGrid(Grid grid) {
		if (favoriteGridList == null) {
			favoriteGridList = new HashSet<Grid>();
		}
		favoriteGridList.add(grid);
	}

	public void removeFavoriteGrid(Grid grid) {
		if (favoriteGridList != null && !favoriteGridList.isEmpty()) {
			favoriteGridList.remove(grid);
		}
	}
	
	public Set<Resource> getResources(){
		Set<Resource> resources = new HashSet<Resource>();
		Iterator<ResourceGroup> resourceGroupIt = resourceGroups.iterator();
		while(resourceGroupIt.hasNext()){
			ResourceGroup resourceGroup = resourceGroupIt.next();
			resources.addAll(resourceGroup.getResources());
		}
		return resources;
	}

	public String getProtectionName(){
		return "文轩客服";
	}
}
