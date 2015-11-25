/*
 * @(#)CustomerPostAccount.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.winxuan.ec.model.area.Area;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-22
 */
@Embeddable
public class CustomerPostAccount implements Serializable {

	private static final long serialVersionUID = 8173171197064461786L;

	@Column(name = "postaccountname")
	private String postAccountName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postcountry")
	private Area postCountry;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postprovince")
	private Area postProvince;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postcity")
	private Area postCity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postdistrict")
	private Area postDistrict;

	@Column(name = "postaddress")
	private String postAddress;

	@Column(name = "postcode")
	private String postCode;

	@Column(name = "postcontactphone")
	private String postContactPhone;

	public String getPostAccountName() {
		return postAccountName;
	}

	public void setPostAccountName(String postAccountName) {
		this.postAccountName = postAccountName;
	}

	public Area getPostCountry() {
		return postCountry;
	}

	public void setPostCountry(Area postCountry) {
		this.postCountry = postCountry;
	}

	public Area getPostProvince() {
		return postProvince;
	}

	public void setPostProvince(Area postProvince) {
		this.postProvince = postProvince;
	}

	public Area getPostCity() {
		return postCity;
	}

	public void setPostCity(Area postCity) {
		this.postCity = postCity;
	}

	public Area getPostDistrict() {
		return postDistrict;
	}

	public void setPostDistrict(Area postDistrict) {
		this.postDistrict = postDistrict;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostContactPhone() {
		return postContactPhone;
	}

	public void setPostContactPhone(String postContactPhone) {
		this.postContactPhone = postContactPhone;
	}
}
