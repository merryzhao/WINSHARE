/*
 * @(#)OrderInvoice.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.user.User;

/**
 * 订单发票
 * 
 * @author liuan
 * @version 1.0,2011-7-30
 */
@Entity
@Table(name = "order_invoice")
public class OrderInvoice implements Serializable{

	private static final long serialVersionUID = 4980612652159592252L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.winxuan.ec.support.util.InvoiceSequenceIdentifierGenerator", parameters = {
			@Parameter(name = "table", value = "serializable"),
			@Parameter(name = "column", value = "maxid"),
			@Parameter(name = "" + "target_name", value = "tablename"),
			@Parameter(name = "target_value", value = "invoice"),
			@Parameter(name = "length", value = "6") })
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;

	@Column
	private BigDecimal money;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator")
	private User operator;

	@Column(name = "createtime")
	private Date createTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mode")
	private Code mode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type = new Code(Code.INVOICE_TYPE_GENERAL);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "titletype")
	private Code titleType;

	@Column
	private String title;

	@Column
	private String content;

	@Column
	private String consignee;

	@Column
	private String phone;

	@Column
	private String mobile;

	@Column
	private String email;

	@Column(name = "zipcode")
	private String zipCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country")
	private Area country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "province")
	private Area province;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city")
	private Area city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district")
	private Area district;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "town")
	private Area town;
	
	@Column
	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliveryoption")
	private Code deliveryOption;

	@Column(name = "deliverycode")
	private String deliveryCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliverycompany")
	private DeliveryCompany deliveryCompany;

	@Column(name = "deliverytime")
	private Date deliveryTime;

	@Column
	private String remark;

	@Column
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="status")
	private Code status;
	
	@Column(name = "istransferred")
	private boolean transferred;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dc")
	private Code dc;
	
	public Code getDc(){
		return dc;
	}
	
	public void setDc(Code dc){
		this.dc = dc;
	}
	
	public void setTown(Area town){
		this.town = town;
	}
	
	public Area getTown(){
		return town;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Code getMode() {
		return mode;
	}

	public void setMode(Code mode) {
		this.mode = mode;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public DeliveryCompany getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(DeliveryCompany deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Area getCountry() {
		return country;
	}

	public void setCountry(Area country) {
		this.country = country;
	}

	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}

	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
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

	public Code getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(Code deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Code getTitleType() {
		return titleType;
	}

	public void setTitleType(Code titleType) {
		this.titleType = titleType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isTransferred() {
		return transferred;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}
	
	
	
}
