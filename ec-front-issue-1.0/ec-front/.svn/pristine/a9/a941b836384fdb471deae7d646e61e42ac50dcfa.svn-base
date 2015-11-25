/*
 * @(#)OrderInvoiceForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.front.controller.customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * 
 *
 * @author zhongs
 * @version 1.0,2011-8-8
 */
public class OrderInvoiceCreateForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orderIds; // 订单号列表

	private String orderId; // 订单号

	private Long titleType;// 抬头类型（个人，公司）code

	private String title; // 发票抬头
	
	private BigDecimal money;// 发票金额（不能超出订单总金额）

	@NotEmpty
	@NotBlank
	private String consignee; // 收货人

	private String phone; // 收货人电话
	
	@NotEmpty
	@NotBlank
	private String mobile; // 收货人手机

	private String email;// 电子邮件
	
	@NotEmpty
	@NotBlank
	private String zipCode; // 邮编

	@NotNull
	private Long country; // 国家
	@NotNull
	@Min(0)
	private Long province; // 省
	@NotNull
	@Min(0)
	private Long city; // 市
	@NotNull
	@Min(0)
	private Long district; // 区

	private Long town;//乡镇
	
	@NotEmpty
	@NotBlank
	private String address; // 详细地址

	private String remark;// 备注

	private String content;// 发票内容：图书或百货（默认不可修改）

	
	private int quantity; // 数量
	
	private Map<String,BigDecimal> payMentMoney;
	
	public Map<String, BigDecimal> getPayMentMoney() {
		return payMentMoney;
	}

	public void setPayMentMoney(Map<String, BigDecimal> payMentMoney) {
		this.payMentMoney = payMentMoney;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTitleType() {
		return titleType;
	}

	public void setTitleType(Long titleType) {
		this.titleType = titleType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}
	
	public void setTown(Long town){
		this.town = town;
	}
	
	public Long getTown(){
		return town;
	}

}
