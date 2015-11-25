/*
 * @(#)PresentCardModifyForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.presentcard;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 修改密码form
 * 
 * @author zhongsen
 * @version 1.0,2011-9-6
 */
public class PresentCardModifyForm {
	
	@NotEmpty
	@NotBlank
	private String id;
	
	@NotEmpty
	@NotBlank
	private String password;

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
