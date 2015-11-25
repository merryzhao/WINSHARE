/*
 * @(#)News.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.cm;

import java.util.Date;

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

import com.winxuan.ec.model.user.Employee;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-26
 */
@Entity
@Table(name = "cm_news")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class News implements Content {

	private static final long serialVersionUID = -5951362970936559264L;

	private static final String NEWS_URL = "http://www.winxuan.com/news/";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String title;

	@Column
	private String content;

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "updatetime")
	private Date updateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator")
	private Employee creator;
	
	@Column
	private short type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Employee getCreator() {
		return creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
	}

	@Override
	public String getName() {
		return getTitle();
	}

	@Override
	public String getUrl() {
		return NEWS_URL + getId();
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
}
