/*
 * @(#)ReturnOrderQueryForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.returnorder;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 退换货查询form
 * 
 * @author wumaojie
 * @version 1.0,2011-9-18
 */
public class ReturnOrderQueryForm {
	
	//订单号类型
	private String idType;
	//订单号
	private String ids;
	//收货人
	private String consignee;
	//创建人
	private String creator;
	//退换货类型
	private Long type;
	//退换货状态
	private Long status;
	//时间类型
	private String timeType;
	//起始时间
	private String startDate;
	//截止时间
	private String endDate;
	
	private Long channel;
	
	 
	//是否应该退款
	private Long isrefund;
	
	private Long[] returnDc;
	
	private Long[] receiveDc;
	
	public Long[] getReturnDc() {
		return returnDc;
	}
	public void setReturnDc(Long[] returnDc) {
		this.returnDc = returnDc;
	}
	public Long[] getReceiveDc() {
		return receiveDc;
	}
	public void setReceiveDc(Long[] receiveDc) {
		this.receiveDc = receiveDc;
	}
	public String getIdType() {
		return idType==null?null:idType.trim();
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getCreator() {
		return creator;
	}
	
	public Long getChannel() {
		return channel;
	}
	public void setChannel(Long channel) {
		this.channel = channel;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getIsrefund() {
		return isrefund;
	}
	public void setIsrefund(Long isrefund) {
		this.isrefund = isrefund;
	}
	/**
	 * 返回Id数组
	 * @return
	 */
	public Object[] getIdS(){
		if(idType==null||ids==null||"".equals(ids.trim())){
			return null;
		}
		String[] strIds = ids.split("[\\D]");
		if("orderId".equals(idType.trim()) || "outerId".equals(idType.trim())){
			return strIds;
		}else{
			Long[] longIds = new Long[strIds.length];
			int i = 0;
			for (String string : strIds) {
				try {
					longIds[i] = Long.valueOf(string);
				} catch (NumberFormatException e) {
					continue;
				}
				i++;
			}
			return longIds;
		}
	}
	/**
	 * 获取开始时间名称
	 * @return
	 */
	public String getStartTimeType(){
		return "start"+timeType;
	}
	/**
	 * 获取截止时间名称
	 * @return
	 */
	public String getEndTimeType(){
		return "end"+timeType;
	}
	/**
	 * 返回收货人如果空字符串则返回空
	 * @return
	 */
	public String getConsigneeOrNull(){
		if(consignee==null||"".equals(consignee)){
			return null;
		}else{
			return consignee;
		}
	}
	/**
	 * 返回创建人如果空字符串则返回空
	 * @return
	 */
	public String getCreatorOrNull(){
		if(creator==null||"".equals(creator)){
			return null;
		}else{
			return creator;
		}
	}
	/**
	 * 返回开始时间
	 * @return
	 */
	public Date getStartDateTime() {
		if(StringUtils.isNotBlank(startDate)){
			try {
				return DateUtils.parseDate(startDate, new String[]{"yyyy-MM-dd HH:mm","yyyy-MM-dd"});
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	/**
	 * 返回截止时间
	 * @return
	 */
	public Date getEndDateTime() {
		if(StringUtils.isNotBlank(endDate)){
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(DateUtils.parseDate(endDate, new String[]{"yyyy-MM-dd HH:mm","yyyy-MM-dd"}));
				calendar.add(Calendar.DATE, 1);
				return calendar.getTime();
				//return DateUtils.parseDate(endDate, new String[]{"yyyy-MM-dd HH:mm","yyyy-MM-dd"});
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "ReturnOrderQueryForm [idType=" + idType + ", ids=" + ids
				+ ", consignee=" + consignee + ", creator=" + creator
				+ ", type=" + type + ", status=" + status + ", timeType="
				+ timeType + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}
    
}
