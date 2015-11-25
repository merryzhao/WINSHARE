/*
 * @(#)OrderReceive.java
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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.code.Code;

/**
 * 订单确认收货
 * 
 * @author liuan
 * @version 1.0,2011-7-15
 */
@Entity
@Table(name = "order_receive")
public class OrderReceive implements Serializable{

	private static final long serialVersionUID = -8261419481340782026L;

	@Id
	@Column(name="_order")
	@GeneratedValue(generator = "orderReceiveFK")
	@GenericGenerator(name = "orderReceiveFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "order"))	
    private String id;
	
	@OneToOne(mappedBy = "receive", targetEntity = Order.class, fetch = FetchType.LAZY)
	private Order order;

	@Column(name = "receivetime")
	private Date receiveTime;

	@Column(name = "createtime")
	private Date createTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="assess")
	private Code assess;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="expressspeed")
	private Code expressSpeed;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="expressmanner")
	private Code expressManner;

	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="source")
	private Code source;


	public Code getSource() {
		return source;
	}



	public void setSource(Code source) {
		this.source = source;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Order getOrder() {
		return order;
	}

	

	public void setOrder(Order order) {
		this.order = order;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Code getAssess() {
		return assess;
	}

	public void setAssess(Code assess) {
		this.assess = assess;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Code getExpressSpeed() {
		return expressSpeed;
	}

	public void setExpressSpeed(Code expressSpeed) {
		this.expressSpeed = expressSpeed;
	}

	public Code getExpressManner() {
		return expressManner;
	}

	public void setExpressManner(Code expressManner) {
		this.expressManner = expressManner;
	}

}
