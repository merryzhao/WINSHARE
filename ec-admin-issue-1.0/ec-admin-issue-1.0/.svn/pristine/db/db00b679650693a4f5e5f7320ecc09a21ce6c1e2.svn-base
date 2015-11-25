/*
 * @(#)SellerProductSales.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 卖家商品管理、渠道销售审批查询
 * 
 * @author wumaojie
 * @version 1.0,2011-9-28
 */
public class SellerProductSales {
	
	//编码类型
	private String productCode;
	//编码
	private String code;
	//店铺
	private Long shop;
	//经营分类
	private String classifiedManage;
	//审核状态
	private Long auditStatus;
	//商品上下架
	private Long productUpDown;
	//是否有图片
	private Boolean picture;
	//申请类型
	private Long applyType;
	//申请开始时间
	private String applyStartTime;
	//申请截止时间
	private String applyEndTime;
	//审核状态
	private Long  status;
	// 时间格式
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getShop() {
		return shop;
	}
	public void setShop(Long shop) {
		this.shop = shop;
	}
	public String getClassifiedManage() {
		if("".equals(classifiedManage)){
			return null;
		}
		return classifiedManage;
	}
	public void setClassifiedManage(String classifiedManage) {
		this.classifiedManage = classifiedManage;
	}
	public Long getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Long auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Long getProductUpDown() {
		return productUpDown;
	}
	public void setProductUpDown(Long productUpDown) {
		this.productUpDown = productUpDown;
	}
	public Boolean getPicture() {
		return picture;
	}
	public void setPicture(Boolean picture) {
		this.picture = picture;
	}
	public Long getApplyType() {
		return applyType;
	}
	public void setApplyType(Long applyType) {
		this.applyType = applyType;
	}
	public String getApplyStartTime() {
		return applyStartTime;
	}
	public void setApplyStartTime(String applyStartTime) {
		this.applyStartTime = applyStartTime;
	}
	public String getApplyEndTime() {
		return applyEndTime;
	}
	public void setApplyEndTime(String applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * 返回编码的Long集合
	 * @return
	 */
	public Object getCodes(){
		//如果为空
		if(code==null||"".equals(code.trim())){
			return null;
		}
		//按空格和回车截取字符串数组
		String[] codes = code.split("[\\D]");
		//如果自编码则返回字符串数组
		if("outerIds".equals(productCode)){
			//构建String List
			List<String> codestrs = new ArrayList<String>();
			for (String string : codes) {
				//去除空白字符串
				if(!"".equals(string)){
					codestrs.add(string);
				}
			}
			return codestrs;
		}
		//构建Long List
		List<Long> codeLongs = new ArrayList<Long>();
		//避免List长度为0;
		codeLongs.add(0l);
		//循环转换
		for (String string : codes) {
			try {
				codeLongs.add(Long.valueOf(string));
			} catch (NumberFormatException e) {
				continue;
			}
		}
		return codeLongs;
	}
	/**
	 * 返回申请开始时间
	 * 
	 * @return
	 */
	public Date getStartDate(){
		if (applyStartTime != null && !"".equals(applyStartTime)) {
			Date date;
			try {
				date = dateformat.parse(applyStartTime);
			} catch (ParseException e) {
				date = null;
			}
			return date;
		}
		return null;
	}
	/**
	 * 返回申请截止时间
	 * 
	 * @return
	 */
	public Date getEndDate(){
		if (applyEndTime != null && !"".equals(applyEndTime)) {
			Date date;
			try {
				date = dateformat.parse(applyEndTime);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				return calendar.getTime();
			} catch (ParseException e) {
				date = null;
			}
			return date;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "SellerProductSales [productCode=" + productCode + ", code="
				+ code + ", shop=" + shop + ", classifiedManage="
				+ classifiedManage + ", auditStatus=" + auditStatus
				+ ", productUpDown=" + productUpDown + ", picture=" + picture
				+ ", applyType=" + applyType + ", applyStartTime="
				+ applyStartTime + ", applyEndTime=" + applyEndTime
				+ ", status=" + status + "]";
	}
}
