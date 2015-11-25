/*
 * @(#)ReturnOrderNewForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.returnorder;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 新建退换货
 * 
 * @author wumaojie
 * @version 1.0,2011-9-16
 */
public class ReturnOrderNewForm {
	
    //原订单号
	private String originalorder;
	//退换货类型   
	private Long type;
	//责任方
	private Long responsible;
	//承担方
	private Long holder;
	//退换货原因
	private Long reason;
	//退换货方式
	private Long pickup;
	//发货运费退款
	private BigDecimal refunddeliveryfee;
	//付退金额
	private BigDecimal refundcompensating;
	//付退礼券
	private BigDecimal refundcouponvalue;
    //备注
	private String remark;
    //退换货商品ID
	private Long[] item;
	//退换货商品数量
	private Integer[] itemcount;
	//是否原包非整退
	private Long originalreturned;
	//退货运单号
	private String expressid;
	//包件唯一id
	private Long packageid;
	//商品暂存库位
	private String[] locations;
	//收货目标仓
	private Long targetDc;
	
	public String getOriginalorder() {
		return originalorder;
	}
	public void setOriginalorder(String originalorder) {
		this.originalorder = originalorder;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getResponsible() {
		return responsible;
	}
	public void setResponsible(Long responsible) {
		this.responsible = responsible;
	}
	public Long getHolder() {
		return holder;
	}
	public void setHolder(Long holder) {
		this.holder = holder;
	}
	public Long getReason() {
		return reason;
	}
	public void setReason(Long reason) {
		this.reason = reason;
	}
	public Long getPickup() {
		return pickup;
	}
	public void setPickup(Long pickup) {
		this.pickup = pickup;
	}
	public BigDecimal getRefunddeliveryfee() {
		if(refunddeliveryfee==null){
			refunddeliveryfee = new BigDecimal(0.00);
		}
		return refunddeliveryfee;
	}
	public void setRefunddeliveryfee(BigDecimal refunddeliveryfee) {
		this.refunddeliveryfee = refunddeliveryfee;
	}
	public BigDecimal getRefundcompensating() {
		if(refundcompensating==null){
			refundcompensating = new BigDecimal(0.00);
		}
		return refundcompensating;
	}
	public void setRefundcompensating(BigDecimal refundcompensating) {
		this.refundcompensating = refundcompensating;
	}
	public BigDecimal getRefundcouponvalue() {
		if(refundcouponvalue==null){
			refundcouponvalue = new BigDecimal(0.00);
		}
		return refundcouponvalue;
	}
	public void setRefundcouponvalue(BigDecimal refundcouponvalue) {
		this.refundcouponvalue = refundcouponvalue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long[] getItem() {
		if(item==null){
			item = new Long[0];
		}
		return item;
	}
	public void setItem(Long[] item) {
		this.item = item;
	}
	public Integer[] getItemcount() {
		if(itemcount==null){
			itemcount = new Integer[0];
		}
		return itemcount;
	}
	public void setItemcount(Integer[] itemcount) {
		this.itemcount = itemcount;
	}
	public Long getOriginalreturned() {
		return originalreturned;
	}
	public void setOriginalreturned(Long originalreturned) {
		this.originalreturned = originalreturned;
	}
	
	public String getExpressid() {
		return expressid;
	}
	public void setExpressid(String expressid) {
		this.expressid = expressid;
	}
	public Long getPackageid() {
		return packageid;
	}
	public void setPackageid(Long packageid) {
		this.packageid = packageid;
	}
	public String[] getLocations() {
		return locations;
	}
	public void setLocations(String[] locations) {
		this.locations = locations;
	}
	public Long getTargetDc() {
		return targetDc;
	}
	public void setTargetDc(Long targetDc) {
		this.targetDc = targetDc;
	}
	@Override
	public String toString() {
		return "ReturnOrderNewForm [originalorder=" + originalorder + ", type="
				+ type + ", responsible=" + responsible + ", holder=" + holder
				+ ", refunddeliveryfee=" + refunddeliveryfee + ", reason="
				+ reason + ", pickup=" + pickup + ", refundcompensating="
				+ refundcompensating + ", refundcouponvalue="
				+ refundcouponvalue + ", remark=" + remark + ", item="
				+ Arrays.toString(item) + ", itemcount="
				+ Arrays.toString(itemcount) + "]";
	}
	
}
