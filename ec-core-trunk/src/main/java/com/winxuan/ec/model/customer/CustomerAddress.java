/*
 * @(#)CustomerAddress.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-18
 */
@Entity
@Table(name = "customer_address")
public class CustomerAddress implements Serializable{

	private static final long serialVersionUID = -9178024363744702812L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customer")
	private Customer customer;

	@Column
	private String consignee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="country")
	private Area country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="city")
	private Area city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="province")
	private Area province;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="district")
	private Area district;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="town")
	private Area town;

	@Column
	private String address;

	@Column(name = "zipcode")
	private String zipCode;

	@Column
	private String phone;

	@Column
	private String mobile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="payment")
	private Payment payment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliverytype")
	private DeliveryType deliveryType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliveryoption")
	private Code deliveryOption;
	
	@Column
	private String email;

	@Column
	private boolean usual;

	@Column(name = "index_")
	private int index;


	public Area getTown() {
		return town;
	}

	public void setTown(Area town) {
		this.town = town;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() { 
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	public boolean isUsual() {
		return usual;
	}

	public void setUsual(boolean usual) {
		this.usual = usual;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Code getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(Code deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	

}
