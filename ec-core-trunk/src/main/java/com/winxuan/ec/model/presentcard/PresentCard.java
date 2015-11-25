/*
 * @(#)PresentCard.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.presentcard;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.support.presentcard.PresentCardUtils;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-29
 */
@Entity
@Table(name = "presentcard")
public class PresentCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7616426447381214816L;
	/**
	 * 礼品卡编号
	 */
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.winxuan.ec.support.presentcard.PresentCardIdGenerator", 
			parameters = {
			@Parameter(name = "table", value = "serializable"),
			@Parameter(name = "column", value = "maxid"),
			@Parameter(name = "" +
					"target_name", value = "tablename"),
					@Parameter(name = "target_value", value = "presentcard"),
					@Parameter(name = "length", value = "5")
	}
	)
	private String id;
	/**
	 * 密码
	 */
	@Column(name = "password")
	private byte[] password;
	/**
	 * 状态
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status")
	private Code status;
	/**
	 * 类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;
	/**
	 * 创建日期
	 */
	@Column(name = "createdate")
	private Date createDate;
	/**
	 * 有效日期
	 */
	@Column(name = "enddate")
	private Date endDate;
	/**
	 * 面额
	 */
	@Column(name = "denomination")
	private BigDecimal denomination;
	/**
	 * 余额
	 */
	@Column(name = "balance")
	private BigDecimal balance;
	/**
	 * 校验码
	 */
	@Column(name = "verifycode")
	private String verifyCode;

	/**
	 * 是否失效
	 * 0 未失效
	 * 1 失效
	 */
	@Column(name = "expired")
	private boolean expired;

	/**
	 * 最近修改密码时间
	 */
	@Column(name = "latestmodifypasswordtime")
	private Date latestModifyPasswordTime;

	/**
	 * 最近使用时间
	 */
	@Column(name = "latestusedtime")
	private Date latestUsedTime;

	/**
	 * 最近登录时间
	 */
	@Column(name = "latestlogintime")
	private Date latestLoginTime;

	/**
	 * 用户ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customer")
	private Customer customer;

	/**
	 * 订单ID
	 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_order")
	private Order order;

	/**
	 * 状态日志
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "presentCard", targetEntity = PresentCardStatusLog.class)
	@OrderBy("id")
	private Set<PresentCardStatusLog> statusLogList;
	/**
	 * 交易日志
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "presentCard", targetEntity = PresentCardDealLog.class)
	@OrderBy("id")
	private Set<PresentCardDealLog> dealLogList;

	/**
	 * 修改密码日志
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "presentCard", targetEntity = PresentCardModifyPasswordLog.class)
	@OrderBy("id")
	private Set<PresentCardModifyPasswordLog> modifyPasswordLogList;

	@Column(name = "bindtime")
	private Date bindTime;
	
	@Version
	private int version;

	/**
	 * 添加状态改变日志
	 * @param log
	 * @param employee 操作人
	 */
	public void addStatusLog(User operator){
		if (statusLogList == null){
			statusLogList = new HashSet<PresentCardStatusLog>();
		}
		PresentCardStatusLog statusLog = new PresentCardStatusLog();
		statusLog.setCreateDate(new Date());
		statusLog.setOperator(operator);
		statusLog.setPresentCard(this);
		statusLog.setStatus(getStatus());
		statusLog.setVerifyCode(getVerifyCode());

		statusLogList.add(statusLog);
	}

	/**
	 * 添加交易日志
	 * @param operator
	 * @param cosumeMoney
	 * @param order
	 */
	public void addDealLog(User operator, BigDecimal consumeMoney, Order order){
		if(dealLogList==null){
			dealLogList=new HashSet<PresentCardDealLog>();
		}
		PresentCardDealLog dealLog = new PresentCardDealLog();
		dealLog.setCreateDate(new Date());
		dealLog.setOperator(operator);
		dealLog.setDealMoney(consumeMoney);
		dealLog.setCurrentBalance(balance);
		dealLog.setOrder(order);
		dealLog.setPresentCard(this);
		dealLog.setVerifyCode(getVerifyCode());
		if(denomination.equals(balance) || order == null){
			dealLog.setType(new Long(0));
		}else{
			dealLog.setType(order.getProcessStatus().getId());
		}

		dealLogList.add(dealLog);
	}


	public void addModifyPasswordLog(User operator, byte[] oldPassword) {
		if(modifyPasswordLogList == null){
			modifyPasswordLogList = new HashSet<PresentCardModifyPasswordLog>();
		}
		PresentCardModifyPasswordLog modifyPasswordLog = new PresentCardModifyPasswordLog();
		modifyPasswordLog.setCreateDate(new Date());
		modifyPasswordLog.setNewPassword(getPassword());
		modifyPasswordLog.setOldPassword(oldPassword);
		modifyPasswordLog.setOperator(operator);
		modifyPasswordLog.setVerifyCode(getVerifyCode());
		modifyPasswordLog.setPresentCard(this);

		modifyPasswordLogList.add(modifyPasswordLog);
	}

	/**
	 * 是否可以使用
	 * @return
	 */
	public boolean canUse(Long customerId) {
		return (Code.PRESENT_CARD_STATUS_ACTIVATE.equals(status.getId()) 
				|| Code.PRESENT_CARD_STATUS_USE.equals(status.getId()))
				&& balance.compareTo(BigDecimal.ZERO) > 0
				&& endDate.compareTo(new Date()) >= 0 
				&& (getCustomer() == null || getCustomer().getId().equals(customerId));
	}
	
	/**
	 * 不带客户id
	 * @return
	 */
	public boolean canUse(){
		return (Code.PRESENT_CARD_STATUS_ACTIVATE.equals(status.getId()) 
				|| Code.PRESENT_CARD_STATUS_USE.equals(status.getId()))
				&& balance.compareTo(BigDecimal.ZERO) > 0
				&& endDate.compareTo(new Date()) >= 0;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getDenomination() {
		return denomination;
	}
	public void setDenomination(BigDecimal denomination) {
		this.denomination = denomination;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Date getLatestModifyPasswordTime() {
		return latestModifyPasswordTime;
	}
	public void setLatestModifyPasswordTime(Date latestModifyPasswordTime) {
		this.latestModifyPasswordTime = latestModifyPasswordTime;
	}
	public Date getLatestUsedTime() {
		return latestUsedTime;
	}
	public void setLatestUsedTime(Date latestUsedTime) {
		this.latestUsedTime = latestUsedTime;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}

	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public Date getLatestLoginTime() {
		return latestLoginTime;
	}
	public void setLatestLoginTime(Date latestLoginTime) {
		this.latestLoginTime = latestLoginTime;
	}



	public Set<PresentCardStatusLog> getStatusLogList() {
		return statusLogList;
	}

	public void setStatusLogList(Set<PresentCardStatusLog> statusLogList) {
		this.statusLogList = statusLogList;
	}

	public Set<PresentCardDealLog> getDealLogList() {
		return dealLogList;
	}

	public void setDealLogList(Set<PresentCardDealLog> dealLogList) {
		this.dealLogList = dealLogList;
	}

	public Set<PresentCardModifyPasswordLog> getModifyPasswordLogList() {
		return modifyPasswordLogList;
	}

	public void setModifyPasswordLogList(
			Set<PresentCardModifyPasswordLog> modifyPasswordLogList) {
		this.modifyPasswordLogList = modifyPasswordLogList;
	}



	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getProclaimVerifyCode() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.id);
		buffer.append(this.getProclaimPassword());
		buffer.append(this.status.getId());
		buffer.append(this.type.getId());
		buffer.append(this.denomination == null ? BigDecimal.ZERO : this.denomination);
		buffer.append(this.balance == null ? BigDecimal.ZERO : this.balance);
		return buffer.toString();
	}


	public String getProclaimPassword() {
		return PresentCardUtils.getProclaimPassword(this);
	}


	public boolean verifyVCode() {
		String proclaimVerifyCode = getProclaimVerifyCode();
		String decryptVerifyCode = PresentCardUtils.decryptVerifyCode(getVerifyCode());
		if(proclaimVerifyCode.equals(decryptVerifyCode)){
			return true;
		}
		return false;
	}

	public String generateVerifyCode() {
		return PresentCardUtils.generateVerifyCode(this);
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}

