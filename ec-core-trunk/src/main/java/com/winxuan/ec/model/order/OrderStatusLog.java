/*
 * @(#)OrderStatusLog.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

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
import com.winxuan.ec.model.user.User;

/**
 * 订单状态日志
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name="order_status_log")
public class OrderStatusLog implements Serializable{

	private static final long serialVersionUID = -7405697057784444599L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="_order")
	private Order order;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="status")
	private Code status;
    
    @Column (name="updatetime")  
	private Date operateTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="operator")
	private User operator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}
}
