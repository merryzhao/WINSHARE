package com.winxuan.ec.front.controller.customer;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;

/*
 * @(#)UpdatePasswordForm
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

/**
 * 登录表单
 * 
 * @author huangyixiang
 * @version 1.0,2011-8-9
 */
public class UpdatePasswordForm implements Serializable {

	/**
	 * 
	 */
	public static final int PASSWORD_MIN = 6;
	public static final int PASSWORD_MAX = 16;
	private static final long serialVersionUID = 8475505895354141095L;
	
	@Email(message="请输入正确的邮箱地址.")
	private String username;
	
	private String oldPassword;
		
	private String newPassword;

	private String newPasswordConfirm;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}

	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}