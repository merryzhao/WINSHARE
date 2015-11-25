/*
 * @(#)PromotionQueryForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.promotion;


/**
 * 商品信息查询表单验证类
 *
 * @author wumaojie
 * @version 1.0,2011-9-14
 */
public class PromotionQueryForm {
	
	//活动编号
	private Long promotionId;
	//活动标题
	private String promotionTitle;
	//活动类型
	private Long promotionType;
	//活动状态
	private Long promotionStatus;
	//开始有效时间
	private String promotionStartdate;
	//结束有效时间
	private String promotionEnddate;
    
	public Long getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
	public String getPromotionTitle() {
		if(promotionTitle==null||"".equals(promotionTitle.trim())){
			return null;
		}
		return "%"+promotionTitle.trim()+"%";
	}
	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}
	public Long getPromotionType() {
		if(promotionType.intValue()==-1){
			return null;
		}
		return promotionType;
	}
	public void setPromotionType(Long promotionType) {
		this.promotionType = promotionType;
	}
	public Long getPromotionStatus() {
		if(promotionStatus.intValue()==-1){
			return null;
		}
		return promotionStatus;
	}
	public void setPromotionStatus(Long promotionStatus) {
		this.promotionStatus = promotionStatus;
	}
	
	public String getPromotionStartdate() {
		return promotionStartdate;
	}
	public void setPromotionStartdate(String promotionStartdate) {
		this.promotionStartdate = promotionStartdate;
	}
	public String getPromotionEnddate() {
		return promotionEnddate;
	}
	public void setPromotionEnddate(String promotionEnddate) {
		this.promotionEnddate = promotionEnddate;
	}
	@Override
	public String toString() {
		return "PromotionQueryForm [promotionId=" + promotionId
				+ ", promotionTitle=" + promotionTitle + ", promotionType="
				+ promotionType + ", promotionStatus=" + promotionStatus
				+ ", promotionStartdate=" + promotionStartdate
				+ ", promotionEnddate=" + promotionEnddate + "]";
	}
	
	
	
}
