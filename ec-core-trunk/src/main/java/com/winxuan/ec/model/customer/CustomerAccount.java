/*
 * @(#)CustomerAccount.java
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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.User;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
@Entity
@Table(name = "customer_account")
public class CustomerAccount implements Serializable {

	private static final long serialVersionUID = 1570636868177078729L;

	@Id
	@Column(name = "customer")
	@GeneratedValue(generator = "customerFK")
	@GenericGenerator(name = "customerFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "customer"))
	private Long id;

	@OneToOne(mappedBy = "account", targetEntity = Customer.class, fetch = FetchType.LAZY)
	private Customer customer;

	@Column
	private BigDecimal balance = BigDecimal.ZERO;

	@Column
	private BigDecimal frozen = BigDecimal.ZERO;
	
	@Column
	private int points;

	@Column(name = "lastusetime")
	private Date lastUseTime;

	@Version
	private int version;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account", targetEntity = CustomerAccountDetail.class)
	private Set<CustomerAccountDetail> detailList;

	public CustomerAccount() {

	}

	public CustomerAccount(Customer customer) {
		this.customer = customer;
		this.lastUseTime = new Date();
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getLastUseTime() {
		return lastUseTime;
	}

	public void setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public BigDecimal getFrozen() {
		return frozen;
	}

	public void setFrozen(BigDecimal frozen) {
		this.frozen = frozen;
	}

	public Set<CustomerAccountDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(Set<CustomerAccountDetail> detailList) {
		this.detailList = detailList;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	public BigDecimal getTotal(){
		return balance.add(frozen).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * @param money 变动金额
	 * @param balance	暂存款余额
	 * @param type	变动类型
	 * @param order	订单号
	 * @param user	操作人
	 * @return
	 */
	public CustomerAccountDetail getAccountDetail(BigDecimal money,BigDecimal balance,Code type,Order order,User user){
		CustomerAccountDetail detail = new CustomerAccountDetail();
		detail.setAccount(this);
		detail.setAmount(money);
		detail.setBalance(balance);
		detail.setOperator(user);
		detail.setOrder(order);
		detail.setType(type);
		detail.setUseTime(lastUseTime);
		return detail;
	}
	
	public void addAccountDetail(CustomerAccountDetail detail){
		if(detailList == null){
			detailList = new HashSet<CustomerAccountDetail>();
		}
		detail.setAccount(this);
		detailList.add(detail);
	}

	@Override
	public String toString() {
		return "CustomerAccount [getId()=" + getId() + ", getCustomer()="
				+ getCustomer() + ", getBalance()=" + getBalance()
				+ ", getFrozen()=" + getFrozen() + "]";
	}

}
