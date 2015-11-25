/*
 * @(#)OrderUpdateLog.java
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

import com.winxuan.ec.model.user.User;

/**
 * 订单修改日志
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "order_update_log")
public class OrderUpdateLog implements Serializable{
	
	public static final String CONSIGNEE="\u6536\u8d27\u4eba";
	public static final String PHONE="\u7535\u8bdd";
	public static final String MOBILE="\u624b\u673a";
	public static final String EMAIL="\u7535\u5b50\u90ae\u4ef6";
	public static final String PROVINCE="\u7701";
	public static final String CITY="\u5e02";
	public static final String TOWN="\u4e61\u9547";
	public static final String DISTRICT="\u533a";
	public static final String ADDRESS="\u8be6\u7ec6\u5730\u5740";
	public static final String DELIVERY_TYPE="\u9001\u8d27\u65b9\u5f0f";
	public static final String DELIVERY_INFO="\u8fd0\u5355\u4fe1\u606f";
	public static final String DELIVERY_OPTION="\u9001\u8d27\u65f6\u95f4";
	public static final String NEED_INVOICE="\u662f\u5426\u9700\u8981\u53d1\u7968";
	public static final String NEED_INVOICE_TRUE="\u9700\u8981";
	public static final String NEED_INVOICE_FALSE="\u4e0d\u9700\u8981";
	public static final String INVOICE_TYPE="\u53d1\u7968\u7c7b\u578b";
	public static final String INVOICE_TITLE="\u53d1\u7968\u62ac\u5934";
	public static final String REMARK="\u5907\u6ce8";
	public static final String ZIPCODE="\u90ae\u7f16";
	public static final String PAYMENT="\u652f\u4ed8\u65b9\u5f0f";
	public static final String ORIGINAL_DC = "\u53d1\u8d27DC";
	public static final String ORDER_STATE = "\u8ba2\u5355\u72b6\u6001"; 
	
	
	private static final long serialVersionUID = 7029443128630396767L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;

	@Column(name = "fieldname")
	private String fieldName;

	@Column(name = "originalvalue")
	private String originalValue;

	@Column(name = "changedvalue")
	private String changedValue;

	@Column(name = "updatetime")
	private Date updateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_user")
	private User operator;

	public OrderUpdateLog() {

	}

	public OrderUpdateLog(User operator, Order order, String fieldName,
			String originalValue, String changedValue) {
		this.operator = operator;
		this.order = order;
		this.fieldName = fieldName;
		this.originalValue = originalValue;
		this.changedValue = changedValue;
		this.updateTime = new Date();
	}

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

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}

	public String getChangedValue() {
		return changedValue;
	}

	public void setChangedValue(String changedValue) {
		this.changedValue = changedValue;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

}
