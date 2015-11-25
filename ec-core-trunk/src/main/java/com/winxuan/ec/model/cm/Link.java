/*
 * @(#)Link.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.cm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-10-26
 */
@Entity
@Table(name="cm_link")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Link implements Content{

	private static final long serialVersionUID = -5326440363200536783L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String title;

	@Column
	private String src;

	@Column
	private String href;
	
	@Column
	private String description;
	
	@Column(name="isimg")
	private boolean img;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isImg() {
		return img;
	}

	public void setImg(boolean img) {
		this.img = img;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	
	/**
	 * 李斌要desc这个属性
	 * @return
	 */
	public String getDesc(){
		return this.description;
	}
	
	
	public void setDescription(String desc){
		this.description = desc;
	}

	@Override
	public String getUrl() {
		return getHref();
	}
}
