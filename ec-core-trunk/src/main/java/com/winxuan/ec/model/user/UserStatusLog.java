/*
 * @(#)UserStatusLog.java
 *
 * Copyright 2012 Winxuan Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.user;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.winxuan.ec.model.channel.Channel;

/**
 * 用户操作日志，用户信息变更后保存此对象
 * 目前只用于代理商的变更，只有channel属性。
 * @author  HideHai
 * @version 1.0,2012-1-13
 */
@Entity
@Table(name="user_status_log")
public class UserStatusLog implements Serializable{

	private static final long serialVersionUID = 8617054655642209599L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel")
	private Channel channel;
	
	@Column
	private BigDecimal discount = BigDecimal.ONE;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator")
	private User operator;
	
	@Column(name="updatetime")
	private Date updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public User getOperator() {
		return operator;
	}
	public void setOperator(User operator) {
		this.operator = operator;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
	
}

