/*
 * @(#)OrderCourierReceipt.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.winxuan.ec.model.channel.Channel;

/**
 * 渠道订单的面单信息
 * 
 * @author wangbiao
 * @version 1.0 date 2014-4-3 下午2:49:56
 */

public class OrderCourierReceipt implements Serializable {
	/**
	 * 100-当当COD面单类型
	 */
	public static final Integer DANG_DANG_COD = 100;
	
	/**
	 * 360BUY_COD面单类型
	 */
	public static final Integer JINGDONG_COD=101;

	public static final Map<Long, OrderCourierReceipt> SURFACE_TYPE_MAP = new HashMap<Long, OrderCourierReceipt>(); 
	
	private static final long serialVersionUID = -8710565734675317114L;
	
	/**
	 * EC订单号
	 */
	private String orderId;

	/**
	 * 渠道订单号
	 */
	private String outerOrderId;

	/**
	 * 面单类型:100-当当COD面单
	 */
	private Integer type;

	/**
	 * 目标仓 :101-当当成都仓,100-当当北京仓
	 */
	private Integer targetWarehouse;
	
	/**
	 * 拒收返回仓
	 */
	private String rejectWarehouse;

	/**
	 * 发货仓
	 */
	private String sendGoodsWarehouse;

	/**
	 * 来源仓
	 */
	private String shopWarehouse;

	/**
	 * 快递公司
	 */
	private String expressCompany;

	/**
	 * 送货上门时间
	 */
	private String sendGoodsTime;

	/**
	 * 发货省市
	 */
	private String consignerAddr;

	/**
	 * 应收金额
	 */
	private BigDecimal totalBarginPrice;

	/**
	 * 商家编号
	 */
	private Integer shopId;
	
	/**
	 * 运单号
	 */
	private String waybillNumber;
	
	private String remark;
	
	static {
		SURFACE_TYPE_MAP.put(Channel.CHANNEL_ID_360BUY, new OrderCourierReceipt(JINGDONG_COD));
		SURFACE_TYPE_MAP.put(Channel.CHANNEL_ID_DANGDANG,new OrderCourierReceipt(DANG_DANG_COD));
		SURFACE_TYPE_MAP.put(Channel.CHANNEL_DANGDANG_JYZ,new OrderCourierReceipt(DANG_DANG_COD));
	}
	public OrderCourierReceipt(Integer type) {
		super();
		this.type = type;
	}

	public OrderCourierReceipt() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOuterOrderId() {
		return outerOrderId;
	}

	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTargetWarehouse() {
		return targetWarehouse;
	}

	public void setTargetWarehouse(Integer targetWarehouse) {
		this.targetWarehouse = targetWarehouse;
	}

	public String getSendGoodsWarehouse() {
		return sendGoodsWarehouse;
	}

	public void setSendGoodsWarehouse(String sendGoodsWarehouse) {
		this.sendGoodsWarehouse = sendGoodsWarehouse;
	}

	public String getShopWarehouse() {
		return shopWarehouse;
	}

	public void setShopWarehouse(String shopWarehouse) {
		this.shopWarehouse = shopWarehouse;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getSendGoodsTime() {
		return sendGoodsTime;
	}

	public void setSendGoodsTime(String sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}

	public String getConsignerAddr() {
		return consignerAddr;
	}

	public void setConsignerAddr(String consignerAddr) {
		this.consignerAddr = consignerAddr;
	}

	public BigDecimal getTotalBarginPrice() {
		return totalBarginPrice;
	}

	public void setTotalBarginPrice(BigDecimal totalBarginPrice) {
		this.totalBarginPrice = totalBarginPrice;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getRejectWarehouse() {
		return rejectWarehouse;
	}

	public void setRejectWarehouse(String rejectWarehouse) {
		this.rejectWarehouse = rejectWarehouse;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getWaybillNumber() {
		return waybillNumber;
	}

	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 根据渠道号获取面单值
	 * @param channel
	 * @return
	 */
	public Integer getSurface(Long channel){
		OrderCourierReceipt ocr = SURFACE_TYPE_MAP.get(channel);
		if(ocr!=null){
			return ocr.getType();
		}
		return null;
	}

}
