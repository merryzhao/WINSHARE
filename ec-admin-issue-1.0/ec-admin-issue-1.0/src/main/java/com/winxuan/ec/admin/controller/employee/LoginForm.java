/*
 * @(#)LoginForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.employee;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.winxuan.ec.support.util.MagicNumber;


/**
 * 后台用户登录表单
 * @author  HideHai
 * @version 1.0,2011-8-3
 */
public class LoginForm {

	@NotEmpty
	@Size(min=MagicNumber.THREE,max=MagicNumber.SIXTEEN)
	private String name;

	@NotEmpty
	@Size(max=MagicNumber.SIXTEEN,min=MagicNumber.FIVE)
	private String password;

	private String from;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}

