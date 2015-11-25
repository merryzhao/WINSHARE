/*
 * @(#)OrderLogistics.java
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
import javax.persistence.Transient;

import com.winxuan.ec.model.code.Code;


/*******************************************
* @ClassName: OrderLogistics 
* @Description: TODO
* @author:cast911
* @date:2014年9月13日 下午12:55:30 
*********************************************/
@Entity
@Table(name = "order_logistics")
public class OrderLogistics implements Serializable {

	private static final long serialVersionUID = -5763707766095186204L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "eventcontent")
	private String eventContent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventcode")
	private Code eventCode;

	@Column(name = "eventtime")
	private Date eventTime;

	@Transient
	private String time;

	@Transient
	private String context;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public Code getEventCode() {
		return eventCode;
	}

	public void setEventCode(Code eventCode) {
		this.eventCode = eventCode;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	@Override
	public String toString() {
		return "OrderLogistics [id=" + id + ", order=" + order.getId() + ", createTime=" + createTime + ","
				+ " eventContent=" + eventContent + ", eventCode=" + eventCode + ", eventTime=" + eventTime + ", time="
				+ time + ", context=" + context + "]";
	}

	
	
}
