/*
 * @(#)CustomerCashApply.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.User;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-22
 */
@Entity
@Table(name = "customer_cashapply")
public class CustomerCashApply implements Serializable {

	private static final long serialVersionUID = 1067920694941131444L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;
	
	@Column
	private BigDecimal money;
	
	@Column(name = "customerpayfee")
	private BigDecimal customerPayFee = BigDecimal.ZERO;

	@Column(name = "companypayfee")
	private BigDecimal companyPayFee = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;

	@Column
	private String alipay;

	@Column(name = "alipayname") 
	private String alipayName;
	
	@Column
	private String tenpay;

	@Embedded
	private CustomerBankAccount bankAccount;

	@Embedded
	private CustomerPostAccount postAccount;

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "processtime")
	private Date processTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status")
	private Code status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator")
	private User operator;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customerCashApply", targetEntity = CashApplyUpdateLog.class)
	@OrderBy("id")
	private Set<CashApplyUpdateLog> updateLogList;

	@Version
	private int version;

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

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getTenpay() {
		return tenpay;
	}

	public void setTenpay(String tenpay) {
		this.tenpay = tenpay;
	}

	public CustomerBankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(CustomerBankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public CustomerPostAccount getPostAccount() {
		return postAccount;
	}

	public void setPostAccount(CustomerPostAccount postAccount) {
		this.postAccount = postAccount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getCustomerPayFee() {
		return customerPayFee;
	}

	public void setCustomerPayFee(BigDecimal customerPayFee) {
		this.customerPayFee = customerPayFee;
	}

	public BigDecimal getCompanyPayFee() {
		return companyPayFee;
	}

	public void setCompanyPayFee(BigDecimal companyPayFee) {
		this.companyPayFee = companyPayFee;
	}
	
	public  boolean canCancelByCustomer(){
		return Code.CUSTOMER_CASH_STATUS_NEW.equals(status.getId())? true : false;
	}
	
	public  boolean canCancelByEmployee(){
		return Code.CUSTOMER_CASH_STATUS_PROCESSING.equals(status.getId())
				|| Code.CUSTOMER_CASH_STATUS_NEW.equals(status.getId()) ? true
				: false;
	}
	/**
	 * 添加更新记录
	 * @param updateLog
	 */
	public void addUpdateLog(CashApplyUpdateLog updateLog,User user,Code status,String remark) {
		if (updateLogList == null) {
			updateLogList = new HashSet<CashApplyUpdateLog>();
		}
		setProcessTime(new Date());
		updateLog.setCustomerCashApply(this);
		updateLog.setOperator(user);
		updateLog.setStatus(status);
		updateLog.setRemark(remark);
		updateLog.setOperateTime(new Date());
		updateLogList.add(updateLog);
	}
	public Set<CashApplyUpdateLog> getUpdateLogList() {
		return updateLogList;
	}
	
	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	@Override
	public String toString() {
		return "CustomerCashApply [getId()=" + getId() + ", getCustomer()="
				+ getCustomer() + ", getStatus()=" + getStatus()
				+ ", getMoney()=" + getMoney() + "]";
	}
}
