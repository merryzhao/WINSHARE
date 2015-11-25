package com.winxuan.ec.admin.controller.order;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/*
 * @(#)LoginForm.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

/**
 * 登录表单
 * @author  HideHai
 * @version 1.0,2011-7-15
 */
public class OrderTrackForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ID_LENGTH = 14;
	private static final int MAX_CONTENT_LENGTH = 120;
	private static final int MIN_CONTENT_LENGTH = 1;
	
	@NotEmpty
	@NotBlank
	@Size(max=ID_LENGTH,min=ID_LENGTH)
	private String orderid;

	@NotEmpty
	@NotBlank
	private String typeId;
	
	@NotEmpty
	@NotBlank
	@Size(min=MIN_CONTENT_LENGTH)
	private String content;

	/**
	 * @return the orderid
	 */
	public String getOrderid() {
		return orderid;
	}

	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}


	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	
}

