package com.winxuan.ec.admin.controller.authority;

import java.io.Serializable;

/**
 * 用户创建form
 * @author sunflower
 *
 */
public class CreateUserForm implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 6731429777340678197L;
	private String name;
	private String password;
	private String email;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
