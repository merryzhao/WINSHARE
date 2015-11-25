/*
 * @(#)OrderNote.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-10-24
 */
@Entity
@Table(name="order_note")
public class OrderNote implements Serializable{

	private static final long serialVersionUID = 9158555297789924671L;
	
	@Id
	@Column(name = "_order")
	@GeneratedValue(generator = "orderFK")
	@GenericGenerator(name = "orderFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "order"))
	private String id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="_order")
	private Order order;
	
	@Column
	private String cookie;
	
	@Column(name="useragent")
	private String userAgent;
	
	@Column
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	

}

