/*
 * @(#)PromotionLog.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.promotion;

import java.io.Serializable;
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

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;

/**
 * description
 * @author  yuhu
 * @version 1.0,2011-9-15
 */
@Entity
@Table(name = "promotion_log")
public class PromotionLog implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_promotion")
	private Promotion promotion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="status")
	private Code status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updateuser")
	private Employee updateUser;
	
	@Column(name = "updatetime")
	private Date updateTime;
	
	public PromotionLog() {
		super();
	}

	public PromotionLog(Promotion promotion,Employee employee) {
		super();
		this.promotion = promotion;
		this.status=promotion.getStatus();
		this.updateTime=new Date();
		this.updateUser=employee;
		promotion.setAssessor(employee);
		promotion.setAssessTime(new Date());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Employee getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Employee updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
