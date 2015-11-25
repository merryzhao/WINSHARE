/*
 * @(#)AnonymousVerifier.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer.verifier;

import com.winxuan.framework.validator.Verifier;

/**
 * 匿名登录
 * @author  liuan
 * @version 1.0,2012-2-24
 */
public class AnonymousVerifier implements Verifier{

	private String mail;
	private String mobile;
	private String password;
	private String name;
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
}
