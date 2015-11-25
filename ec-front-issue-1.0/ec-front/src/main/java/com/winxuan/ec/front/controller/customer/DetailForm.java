package com.winxuan.ec.front.controller.customer;

import java.util.Date;

/**
 * 用户基本信息
 * 
 * @author YangJun
 * @version 1.0,2011-10-14
 */
public class DetailForm {
	private String realName;
	private short gender;
	private Date birthday;
	private Long country;
	private Long province;
	private Long city;
	private String email;
	private String mobile;
	private String avatar;
	private String nickName;


	private short flag = 1;// 默认为查询

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public short getGender() {
		return gender;
	}

	public void setGender(short gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public short getFlag() {
		return flag;
	}

	public void setFlag(short flag) {
		this.flag = flag;
	}

}
