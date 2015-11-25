/*
 * @(#)ErpOrder.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.model.erp;

import java.io.Serializable;
import java.util.Date;

/**
 * ERP订单对象
 * @author  HideHai
 * @version 1.0,2011-9-29
 */
public class ErpOrder implements Serializable{


	/**
	 * 销售订单
	 */
	public static final String DDLXID_SALE_ORDER = "1";
	/**
	 * 退换货订单
	 */
	public static final String DDLXID_CHARGE_OFF_ORDER = "3";
	/**
	 * 退货入库(只有订单类型为3退货订单时才有可能返回)
	 */
	public static final String REJECT_GOODS_CONFIRM = "4";

	/**
	 * 退换货方式-销售退货
	 */
	public static final String RETURN_TYPE_SALE_RETURN = "4";

	/**
	 * 审核
	 */
	public static final String STATUS_AUDITING ="01";
	/**
	 * 导入
	 */
	public static final String STATUS_IMPORT ="02";
	/**
	 * 集货
	 */
	public static final String STATUS_PICKING ="03";
	/**
	 * 质检包装
	 */
	public static final String STATUS_TESTING = "04";
	/**
	 * 等待发运
	 */
	public static final String STATUS_WAITING_DELIVERY = "05";
	/**
	 * 已发出
	 */
	public static final String STATUS_DELIVERY = "06";
	/**
	 * 妥投
	 */
	public static final String STATUS_CONFIRM = "07";
	/**
	 * 缺货取消
	 */
	public static final String STATUS_CANCELLED_FOR_OUT_OF_STOCK = "08";
	/**
	 * 拒收取消
	 */
	public static final String STATUS_CANCELLED_FOR_REJECT = "09";

	/**
	 * 客户取消
	 */
	public static final String STATUS_CANCELLED_BY_CUSTOMER = "10";
	/**
	 * 逾期支付取消
	 */
	public static final String STATUS_PAY_OVERDUE = "12";
	/**
	 * 无法确认取消
	 */
	public static final String STATUS_CANCELLED_BY_UNRECIVE = "13";
	/**
	 * 退货上架
	 */
	public static final String STATUS_RETURN_ONSHELF = "091";
	
	private static final long serialVersionUID = 2781602314041198748L;


	private String orderId;
	private String state;
	private String code;
	private String deliveryCompany;
	private String ddlxid;
	private String returnTypeId;
	private Date fhrq;
	private String dc;
	
	/**
	 * 中启订单操作人
	 */
	private String cuser;

	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public static String getDdlxidChargeOffOrder() {
		return DDLXID_CHARGE_OFF_ORDER;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDeliveryCompany() {
		return deliveryCompany;
	}
	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}
	public String getDdlxid() {
		return ddlxid;
	}
	public void setDdlxid(String ddlxid) {
		this.ddlxid = ddlxid;
	}
	public Date getFhrq() {
		return fhrq;
	}
	public void setFhrq(Date fhrq) {
		this.fhrq = fhrq;
	}
	public String getReturnTypeId() {
		return returnTypeId;
	}
	public void setReturnTypeId(String returnTypeId) {
		this.returnTypeId = returnTypeId;
	}
	
	public String getCuser() {
		return cuser;
	}
	public void setCuser(String cuser) {
		this.cuser = cuser;
	}
	
	/**
	 * 判断回传的数据是否是EC订单
	 * @return
	 */
	public boolean isOrder(){
		if(DDLXID_SALE_ORDER.equals(getDdlxid()) && 
				!RETURN_TYPE_SALE_RETURN.equals(getReturnTypeId())){
			return true;
		}
		return false;
	}

	/**
	 * 判断回传的数据是否是EC退货单
	 * @return
	 */
	public boolean isReturnOrder(){
		if(DDLXID_CHARGE_OFF_ORDER.equals(getDdlxid())){
			return true;
		}else if(DDLXID_SALE_ORDER.equals(getDdlxid()) && RETURN_TYPE_SALE_RETURN.equals(getReturnTypeId())){
			return true;
		}
		return false;
	}
}

