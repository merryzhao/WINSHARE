/*
 * @(#)AddressForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;


import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.winxuan.ec.model.area.Area;

/**
 *  用户常用地址
 * @author  HideHai
 * @version 1.0,2011-8-9
 */
public class AddressForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5968030363155607481L;

	private Long id;

	@NotEmpty(message="姓名不能为空!")
	private String consignee;

	@NotEmpty(message="地址不能为空!")
	private String address;

	@NotNull(message="国家不能为空!")
	private Area country;

	@NotNull(message="城市不能为空!")
	private Area city;

	@NotNull(message="省不能为空!")
	private Area province;

	@NotNull(message="区域不能为空!")
	private Area district;
	
	@NotNull(message="乡镇不能为空!")
	private Area town;
	
	private String phone;
	
	private String mobile;
	
	@NotEmpty(message="邮编不能为空!")
	private String zipCode;
	
	@NotEmpty(message="邮箱不能为空!")
	@Email(message="邮箱格式不正确!")
	private String email;
	
	private boolean usual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isUsual() {
		return usual;
	}

	public void setUsual(boolean usual) {
		this.usual = usual;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Area getCountry() {
		return country;
	}

	public void setCountry(Area country) {
		this.country = country;
	}

	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}

	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}

	public Area getDistrict() {
		return district;
	}

	public void setDistrict(Area district) {
		this.district = district;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Area getTown() {
		return town;
	}

	public void setTown(Area town) {
		this.town = town;
	}

}

