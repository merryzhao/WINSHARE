/*
 * @(#)PromotionQueryForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.promotion;

 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.user.Employee;
/**
 * 送礼卷
 *
 * @author rensy
 * @version 1.0,2011-9-16
 */
public class PresentSendForm {
	private static final String DATEFORMAT = "yyyy-MM-dd hh:mm";
	//活动
 	//活动编号
	private Long promotionId;
	//活动标题
	private String promotionTitle;
	//活动类型
	private Long promotionType;
	//活动数据
	private String promotionData1;
	//开始有效时间
	private String promotionStartDate;
	//结束有效时间
	private String promotionEndDate;
	//开始有效时间
	private String promotionStartTime;
	//结束有效时间
	private String promotionEndTime;
	// 卖家
	private List<Long> sellers;
	// 礼券发放时间
	private Long sendTime;
	// 促销方式
	private Long smanner;
	// 订单支付有效时间
	private Integer effectivetime;
 	// 促销描述
	private String promotionDescription;
	// 促销广告语
	private String advert;
	//促销广告图片
	private String advertImage;
	//促销专题链接
	private String advertUrl;
    
	public String getPromotionStartTime() {
		return promotionStartTime;
	}
	public void setPromotionStartTime(String promotionStartTime) {
		this.promotionStartTime = promotionStartTime;
	}
	public String getPromotionEndTime() {
		return promotionEndTime;
	}
	public void setPromotionEndTime(String promotionEndTime) {
		this.promotionEndTime = promotionEndTime;
	}
	public Long getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
	public String getPromotionTitle() {
		return promotionTitle;
	}
	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}
	public Long getPromotionType() {
		return promotionType;
	}
	public void setPromotionType(Long promotionType) {
		this.promotionType = promotionType;
	}
	public String getPromotionData1() {
 		return promotionData1;
	}
	public void setPromotionData1(String promotionData1) {
		if(promotionData1.endsWith(",")){
			promotionData1=promotionData1.substring(0, promotionData1.length()-1);
		}
		this.promotionData1 = promotionData1;
	}
	public Long getSendTime() {
		return sendTime;
	}
	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getEffectivetime() {
		return effectivetime;
	}
	public void setEffectivetime(Integer effectivetime) {
		this.effectivetime = effectivetime;
	}
	public String getPromotionDescription() {
		return promotionDescription;
	}
	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}
	public String getAdvert() {
		return advert;
	}
	public void setAdvert(String advert) {
		this.advert = advert;
	}
	public void setAdvertImage(String advertImage) {
		this.advertImage = advertImage;
	}
	public String getAdvertImage() {
		return advertImage;
	}
	public String getAdvertUrl() {
		return advertUrl;
	}
	public void setAdvertUrl(String advertUrl) {
		this.advertUrl = advertUrl;
	}
	public void setPromotionStartDate(String promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}
	public String getPromotionStartDate() {
		return promotionStartDate;
	}
	public void setPromotionEndDate(String promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}
	public String getPromotionEndDate() {
		return promotionEndDate;
	}
	
	public List<Long> getSellers() {
		return sellers;
	}
	public void setSellers(List<Long> sellers) {
		this.sellers = sellers;
	}
	public void setSmanner(Long smanner) {
		this.smanner = smanner;
	}
	public Long getSmanner() {
		return smanner;
	}
 
	public Promotion getPromotion(Promotion promotion,Employee operator) throws ParseException {
		boolean isAdd=false;
		if(promotion==null){
			promotion=new Promotion();
			promotion.setCreateUser(operator);
			promotion.setCreateTime(new Date());
			isAdd=true;
		}
		// 促销标题
		promotion.setTitle(this.promotionTitle);
		// 促销描述
		promotion.setDescription(this.promotionDescription);
		// 活动类型
		promotion.setType(new Code(Code.PROMOTION_TYPE_ORDER_SEND_PRESENT));
		// 促销开始时间
		promotion.setStartDate(new SimpleDateFormat(DATEFORMAT)
				.parse(this.promotionStartDate+" "+promotionStartTime));
		// 促销结束时间
		promotion.setEndDate(new SimpleDateFormat(DATEFORMAT)
				.parse(this.promotionEndDate+" "+promotionEndTime));
		// 促销广告语
		promotion.setAdvert(this.advert);
		promotion.setAdvertUrl(advertUrl);
   		// 订单支付有效时间
		promotion.setEffectiveTime(this.effectivetime);
		// 发放时间
		promotion.setEffectiveState(new Code(this.sendTime));
		// 促销方式
		promotion.setManner(new Code(this.smanner));
		// 会员
		StringBuilder sb = new StringBuilder();
		for (Long str : this.sellers) {
			if(!sb.toString().isEmpty()){
				sb.append(Promotion.COMMA);
			}
			sb.append(str);
		}
		promotion.setShops(sb.toString());
		
		Promotion p = null;
		try {
			if(isAdd){
				p = promotion;
			}else{
				p = (Promotion) BeanUtils.cloneBean(promotion);
			}
		} catch (Exception e1) {
			throw new RuntimeException();
		}
		return p;
	}
}
