/*
 * @(#)PresentLog.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.present;

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
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.User;

/**
 * description
 * @author  yuhu
 * @version 1.0,2011-8-30
 */
@Entity
@Table(name = "present_log")
public class PresentLog implements Serializable{

	private static final long serialVersionUID = 7718591289620995689L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_present")
	private Present present;
	
	@Column(name = "updatetime")
	private Date updateTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="state")
	private Code state;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_order")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="operator")
	private User operator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Present getPresent() {
		return present;
	}

	public void setPresent(Present present) {
		this.present = present;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Code getState() {
		return state;
	}

	public void setState(Code state) {
		this.state = state;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}
}
