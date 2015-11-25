package com.winxuan.ec.admin.controller.excel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * Excel订单支付信息 
 * @author Heyadong
 * @version 1.0 , 2012-2-3
 */
public class ExcelOrderPayListForm {
	@NotNull
	private String orderId;
	private String platform;
	@NotNull
	private String credential;
	@NotNull
	private BigDecimal money;
	@NotNull
	private String account;
	private String payer;
	private String address;
	@NotNull
	private String payTime;
	private String remark;
	
	private BigDecimal requidPayMoney;
	public BigDecimal getRequidPayMoney() {
		return requidPayMoney;
	}
	public void setRequidPayMoney(BigDecimal requidPayMoney) {
		this.requidPayMoney = requidPayMoney;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getCredential() {
		return credential;
	}
	public void setCredential(String credential) {
		this.credential = credential;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
