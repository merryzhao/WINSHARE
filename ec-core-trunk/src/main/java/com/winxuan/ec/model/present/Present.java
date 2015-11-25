/*
 * @(#)Present.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.present;

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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.User;

/**
 * 礼券
 * @author  yuhu
 * @version 1.0,2011-8-30
 */
@Entity
@Table(name = "present")
public class Present implements Serializable{

	private static final long serialVersionUID = -1939921210038631094L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_batch")
	private PresentBatch batch;
	
	@Column
	private String code;
	
	@Column(name = "_value")
	private BigDecimal value;
	
	@Column(name = "startdate")
	private Date startDate;
	
	@Column(name = "enddate")
	private Date endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_user")
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="state")
	private Code state;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="origin")
	private Code origin = new Code(Code.PRESENT_ORIGIN_WXSEND);
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="originorder")
	private Order originOrder;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_order")
	private Order order;
	
	@Column(name="paytime")
	private Date payTime;
	
	@Column(name="realpay")
	private BigDecimal realPay;
	
	@OneToMany(mappedBy = "present", cascade=CascadeType.ALL,targetEntity = PresentLog.class)
	private Set<PresentLog> logs;
	
	@Version
	private int version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PresentBatch getBatch() {
		return batch;
	}

	public void setBatch(PresentBatch batch) {
		this.batch = batch;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Code getOrigin() {
		return origin;
	}

	public void setOrigin(Code origin) {
		this.origin = origin;
	}

	public Order getOriginOrder() {
		return originOrder;
	}

	public void setOriginOrder(Order originOrder) {
		this.originOrder = originOrder;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getRealPay() {
		return realPay;
	}

	public void setRealPay(BigDecimal realPay) {
		this.realPay = realPay;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Code getState() {
		return state;
	}

	public void setState(Code state) {
		this.state = state;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Set<PresentLog> getLogs() {
		return logs;
	}

	public void setLogs(Set<PresentLog> logs) {
		this.logs = logs;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void addLog(Order order,User operator){
		if(logs == null){
			logs = new HashSet<PresentLog>();
		}
		PresentLog log = new PresentLog();
		log.setPresent(this);
		log.setState(this.getState());
		log.setOrder(order);
		log.setOperator(operator);
		log.setUpdateTime(new Date());
		logs.add(log);
	}
	
	public void changeStatus(Code status,Order order,User operator){
		this.state = status;
		addLog(order, operator);
	}
}
