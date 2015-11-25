/*
 * @(#)OrderTransportInfo.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单对应货运公司
 * 
 * @author wangbiao
 * @version 1.0 date 2015-2-3
 */
@Entity
@Table(name = "order_transport_info")
public class OrderTransportInfo implements Serializable {

	private static final long serialVersionUID = 8282487789169182912L;

	@Id
	@Column(name = "_order")
	private String orderId;

	/**
	 * 货运公司ID
	 */
	@Column(name = "transportid")
	private Long transportId;

	/**
	 * 货运公司名称
	 */
	@Column(name = "transportname")
	private String transportName;

	/**
	 * 运输单号
	 */
	@Column(name = "transportcode")
	private String transportCode;

	/**
	 * 大包号
	 */
	@Column(name = "parcel")
	private String parcel;

	/**
	 * 小包信息（用逗号分隔存储）
	 */
	@Column(name = "packets")
	private String packets;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getTransportId() {
		return transportId;
	}

	public void setTransportId(Long transportId) {
		this.transportId = transportId;
	}

	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	public String getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}

	public String getParcel() {
		return parcel;
	}

	public void setParcel(String parcel) {
		this.parcel = parcel;
	}

	public String getPackets() {
		return packets;
	}

	public void setPackets(String packets) {
		this.packets = packets;
	}

}
