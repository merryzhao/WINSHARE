package com.winxuan.ec.admin.controller.authority;

import java.io.Serializable;

/**
 * 用户资料详细
 * @author sunflower
 *
 */
public class UserDetailForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6240856129398412192L;
	
	private String realName;
	private String email;
	private String phone;
	private String mobile;
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

}
