/*
 * @(#)BatchDefray.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 批量支付
 * @author  qiaoyao
 * @version 1.0,2011-8-11
 */
@Entity
@Table(name = "batchpay")
public class BatchPay {
	
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.winxuan.ec.support.util.DatePrefixSequenceIdentifierGenerator", 
		parameters = {
			@Parameter(name = "table", value = "serializable"),
			@Parameter(name = "column", value = "maxid"),
			@Parameter(name = "" +
					"target_name", value = "tablename"),
			@Parameter(name = "target_value", value = "batchpay"),
			@Parameter(name = "length", value = "8")
		}
	)
	private String id;
	
	
	@Column(name = "createtime")
	private Date createTime;
	
	@Column(name = "success")
	private boolean success;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)  
	@JoinTable(name = "order_batchpay", joinColumns = { @JoinColumn(name = "batchpay") },
		inverseJoinColumns = { @JoinColumn(name = "_order") }
	)
	private Set<Order> orderList;
	
	@Column(name = "totalmoney")
	private BigDecimal totalMoney;
	
	@Column(name = "digest")
	private String digest;
	
	@Column(name = "params")
	private String params;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Set<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(Set<Order> orderList) {
		this.orderList = orderList;
	}
	
	public void addOrder(Order order) {
		if (orderList == null) {
			orderList = new HashSet<Order>();
		}
		orderList.add(order);
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	
}
