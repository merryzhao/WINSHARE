/*
 * @(#)OrderConsignee.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.framework.util.StringUtils;

/**
 * 订单收货信息
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "order_consignee")
public class OrderConsignee implements Serializable{

	private static final long serialVersionUID = 8495614923928378479L;

	@Id
	@Column(name = "_order")
	@GeneratedValue(generator = "orderFK")
	@GenericGenerator(name = "orderFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "order"))
	private String id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="_order")
	private Order order;

	@Column
	private String consignee;

	@Column
	private String phone;

	@Column
	private String mobile;

	@Column
	private String email;

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
	@JoinColumn(name="town")
	private Area town;

	@Column
	private String address;

	@Column(name = "zipcode")
	private String zipCode;

	@Column(name = "needinvoice")
	private boolean needInvoice;

	@Column(name = "invoicetitle")
	private String invoiceTitle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoicetitletype")
	private Code invoiceTitleType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoicetype")
	private Code invoiceType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "outofstockoption")
	private Code outOfStockOption = new Code(Code.OUT_OF_STOCK_OPTION_SEND);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliveryoption")
	private Code deliveryOption = new Code(Code.DELIVERY_OPTION_ANYTIME);

	@Column
	private String remark;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unauditreason")
	private Code unAuditReason;

	

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

//	public Area getTown() {
//		return town;
//	}
//
//	public void setTown(Area town) {
//		this.town = town;
//	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(boolean needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public Code getOutOfStockOption() {
		return outOfStockOption;
	}

	public void setOutOfStockOption(Code outOfStockOption) {
		this.outOfStockOption = outOfStockOption;
	}

	public Code getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(Code deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Code getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Code invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Code getUnAuditReason() {
		return unAuditReason;
	}

	public void setUnAuditReason(Code unAuditReason) {
		this.unAuditReason = unAuditReason;
	}


	public Area getTown() {
		return town;
	}

	public void setTown(Area town) {
		this.town = town;
	}

	public Code getInvoiceTitleType() {
		return invoiceTitleType;
	}

	public void setInvoiceTitleType(Code invoiceTitleType) {
		this.invoiceTitleType = invoiceTitleType;
	}

	/**
	 * 获取订单的区域 
	 * @return
	 */
	public Area getOrderArea() {
		return this.getDistrict() != null ? this.getDistrict()
				: this.getCity() != null ? this.getCity()
						: this.getProvince() != null ? this.getProvince()
								: this.getCountry();
	}

	/**
	 * 有效的联系方式 手机||电话 == null ? null
	 * @return
	 */
	public String getEffectWay(){
		StringBuffer buffer = new StringBuffer();
		if(!StringUtils.isNullOrEmpty(mobile)){
			buffer.append(mobile);
		}else if(!StringUtils.isNullOrEmpty(phone)){
			buffer.append(phone);
		}
		return buffer.toString();
	}
	
	/**
	 * 组装地址
	 * @return
	 */
	public String getFullAddress(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getCountry().getName());
		sb.append(",");
		sb.append(this.getProvince().getName());
		sb.append(",");
		sb.append(this.getCity().getName());
		sb.append(",");
		sb.append(this.getDistrict().getName());
		sb.append(",");
		sb.append(this.getTown().getName());
		sb.append(",");
		sb.append(this.getAddress());
		sb.append(",");
		sb.append(this.getConsignee());
		return sb.toString();
	}

}
