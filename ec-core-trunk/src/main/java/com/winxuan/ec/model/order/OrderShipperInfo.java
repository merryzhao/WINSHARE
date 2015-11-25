/*
 * @(#)OrderDeliveryInfo.java
 *
 * Copyright 2015 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * description 订单承运商信息
 * 
 * @author YangJun
 * @version 1.0, 2015年1月14日
 */
@Entity
@Table(name = "order_shipper_info")
public class OrderShipperInfo implements Serializable {

	/**
	 * 人工审核：不满足自动审核的条件需要人工审核
	 */
	public static final Short DEFAULT_STATUS = 0;
	
	/**
	 * 自动审核：即DMS自动匹配承运商
	 */
	public static final Short MACHING_STATUS = 1;
	
	/**
	 * 异常审核：调用DMS自动匹配有异常错误信息，需要人工审核
	 */
	public static final Short ERROR_STATUS = 2;
	
	/**
	 * 默认作业类型：当需要人工审核时，使用这个默认值填补数据(当状态为0或2时)
	 */
	public static final String DEFAULT_JOB_TYPE = "DEFAULT_JOB_TYPE";
	
	/**
	 * 默认运输类型：当需要人工审核时，使用这个默认值填补数据(当状态为0或2时)
	 */
	public static final long DEFAULT_CARRIAGE_TYPE = 0;
	
	private static final long serialVersionUID = 908910668117049825L;
	
	@Id
	@Column(name = "_order")
	private String orderId;

	// 运输类型
	@Column(name = "carriagetype")
	private long carriageType;

	// 作业类型
	@Column(name = "jobtype")
	private String jobType;

	// 交货地
	@Column(name = "deliveryplace")
	private Long deliveryPlace;

	// 干线
	@Column(name = "dryline")
	private Long dryline;

	// 客户
	@Column(name = "clientid")
	private Long clientId;

	@Column(name = "createtime")
	private Date createTime;

	/**
	 * 0-人工审核，1-自动审核(DMS自动匹配)，2-异常审核(DMS匹配异常)
	 */
	@Column(name = "status")
	private short status;
	
	@Column(name = "errormessage")
	private String errorMessage;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public long getCarriageType() {
		return carriageType;
	}

	public void setCarriageType(long carriageType) {
		this.carriageType = carriageType;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Long getDeliveryPlace() {
		return deliveryPlace;
	}

	public void setDeliveryPlace(Long deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}

	public Long getDryline() {
		return dryline;
	}

	public void setDryline(Long dryline) {
		this.dryline = dryline;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
