/*
 * @(#)TestValid.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.test;

import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotEmpty;

import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.validator.constraints.FieldMatch;



/**
 * description
 * @author  HideHai
 * @version 1.0,2011-8-16
 */
@FieldMatch.List({
	@FieldMatch(name = "passwordConfirm",fieldName="password",verifyName="passwordConfirm",groups={Default.class})
})
public class TestValid {

	@NotEmpty(groups={ChangeName.class,Default.class} )
	@Size(max=MagicNumber.SIXTEEN,min=MagicNumber.THREE,groups={ChangeName.class,Default.class})
	private String name;

	@NotEmpty(groups={ChangePass.class} )
	private String password;

	@NotEmpty( groups={ChangePass.class} )
	private String passwordConfirm;
	
	@NotEmpty(groups={Default.class} )
	private String randomCode;

	
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

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	/**
	 * @author hidehai
	 *
	 */
	public interface ChangeName{};
	/**
	 * @author hidehai
	 *
	 */
	public interface ChangePass{};
}

