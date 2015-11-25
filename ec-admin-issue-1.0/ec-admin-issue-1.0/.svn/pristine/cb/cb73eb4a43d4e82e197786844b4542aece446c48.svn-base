/*
 * @(#)ProductQueryForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.support.util.MagicNumber;
/**
 * 商品信息查询表单验证类
 *
 * @author  
 * @version 1.0,2011-11-1
 */
public class ComplexQueryForm {
	private static final String EARLY_TIME="00:00:00";
	private static final String LATE_TIME="23:59:59";
	// 编码内容
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String codingContent;
	// 商品名称关键字
	private String productName;
	// 上下架状态
	private Long status;	
	//创建开始时间
	private String startDate;
	//创建结束时间
	private String endDate;
	//子商品
	private String items;
	
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getCodingContent() {
		return codingContent;
	}
	public void setCodingContent(String codingContent) {
		this.codingContent = codingContent;
	}
	public String getProductName() {
		return StringUtils.isBlank(productName)?null:productName;
	}
	public String getQueryProductName() {
		return StringUtils.isBlank(productName)?null:"%"+productName+"%";
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getSaleStatus() {
		return status==0?null:status;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public Date getStartTime(){
		if(!StringUtils.isBlank(startDate)){
			try {
				return simpleDateFormat.parse(startDate+" "+EARLY_TIME);
			} catch (ParseException e) {
				return null;
  			}
		}
		return null;
	}
	
	public Date getEndTime(){
		if(!StringUtils.isBlank(endDate)){
			try {			
				return simpleDateFormat.parse(endDate+" "+LATE_TIME);
			} catch (ParseException e) {
				return null;
 			}
		}
		return null;
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
	public List<Long> getPorIds(){
		return fetchListSplitN(codingContent);
	}
	
	public List<Long> getItemIds(){
		return fetchListSplitN(items);
	}
	
	private List<Long> fetchListSplitN(String items){
		List<Long> proIds=new ArrayList<Long>();
		if(!StringUtils.isBlank(items)){
	 		String[] theIds=items.split("\n");
			for(int i=MagicNumber.ZERO;i<theIds.length;i++){ 
				if(!StringUtils.isBlank(theIds[i])){
					try{
						proIds.add(Long.valueOf(theIds[i].trim()));
					}catch (Exception e) {
						continue;
						}
				}
			}
		}
		return proIds.isEmpty()?null:proIds;
	}

}
