/*
 * @(#)PromotionDeliveryFeeForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.promotion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionOrderRule;
import com.winxuan.ec.model.user.Employee;

/**
 * 订单减运费活动
 * 
 * @author zhongsen
 * @version 1.0,2011-9-15
 */
public class PromotionDeliveryFeeForm {
	private static final String DATEFORMAT = "yyyy-MM-dd hh:mm";
	// 编号
	private Long id;
	// 活动类型
	private String promotionType;
	// 是否全免运费
	private boolean remitdeliveryfee;
	// 运费减免金额
	private BigDecimal deliveryfee;
	// 订单最小金额
	private BigDecimal minAmount;
	// 订单最大金额
	private BigDecimal maxAmount;
	// 卖家列表
	private List<Long> sellers;
	// 区域
	private List<String> areas;
	// 促销标题
	private String promotionTitle;
	// 促销描述
	private String promotionDescription;
	// 促销开始时间
	private String promotionStartDate;
	// 促销结束时间
	private String promotionEndDate;
	//促销开始时间
	private String promotionStartTime;
	//促销结束时间
	private String promotionEndTime;
	// 促销广告语
	private String advert;
	// 促销广告图片
	private String advertImage;
	//促销专题链接
	private String advertUrl;
	// 订单支付有效时间
	private Integer effectivetime;

	public String getPromotionTitle() {
		return promotionTitle;
	}
	
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
	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}

	public String getPromotionDescription() {
		return promotionDescription;
	}

	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}

	public String getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionStartDate(String promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	public String getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionEndDate(String promotiionEndDate) {
		this.promotionEndDate = promotiionEndDate;
	}

	public String getAdvert() {
		return advert;
	}

	public void setAdvert(String advert) {
		this.advert = advert;
	}

	public String getAdvertImage() {
		return advertImage;
	}

	public void setAdvertImage(String advertImage) {
		this.advertImage = advertImage;
	}

	public String getAdvertUrl() {
		return advertUrl;
	}

	public void setAdvertUrl(String advertUrl) {
		this.advertUrl = advertUrl;
	}

	public Integer getEffectivetime() {
		return effectivetime;
	}

	public void setEffectivetime(Integer effectivetime) {
		this.effectivetime = effectivetime;
	}

	public boolean isRemitdeliveryfee() {
		return remitdeliveryfee;
	}

	public void setRemitdeliveryfee(boolean remitdeliveryfee) {
		this.remitdeliveryfee = remitdeliveryfee;
	}

	public BigDecimal getDeliveryfee() {
		return deliveryfee;
	}

	public void setDeliveryfee(BigDecimal deliveryfee) {
		this.deliveryfee = deliveryfee;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public List<Long> getSellers() {
		return sellers;
	}

	public void setSellers(List<Long> sellers) {
		this.sellers = sellers;
	}

	public List<String> getAreas() {
		return areas;
	}

	public void setAreas(List<String> areas) {
		this.areas = areas;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public Promotion getPromotion(Promotion promotion, Employee operator) {
		boolean add = false;
		if(promotion == null){
			promotion = new Promotion();
			promotion.setCreateTime(new Date());
			promotion.setCreateUser(operator);	
			add = true;
		}
		promotion.setType(new Code(Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE));
		promotion.setTitle(promotionTitle);
		promotion.setDescription(promotionDescription);
		promotion.setAdvert(advert);
		promotion.setAdvertUrl(advertUrl);
		promotion.setEffectiveTime(effectivetime);
		try {
			promotion.setStartDate(new SimpleDateFormat(DATEFORMAT)
					.parse(promotionStartDate+" "+promotionStartTime));
		} catch (ParseException e) {
			promotion.setStartDate(new Date());
		}
		try {
			promotion.setEndDate(new SimpleDateFormat(DATEFORMAT)
					.parse(promotionEndDate+" "+promotionEndTime));
		} catch (ParseException e) {
			promotion.setStartDate(new Date());
		}
		StringBuilder sb = new StringBuilder();
		for (Long seller : sellers) {
			if(!sb.toString().isEmpty()){
				sb.append(Promotion.COMMA);
			}
			sb.append(seller);
		}
		promotion.setShops(sb.toString());
		Promotion p = null;
		try {
			if(add){
				p = promotion;
			}else{
				p = (Promotion) BeanUtils.cloneBean(promotion);
			}
		} catch (Exception e1) {
			throw new RuntimeException();
		} 
		
		// 区域
		LinkedHashSet<String> areaList = new LinkedHashSet<String>();
		areaList.addAll(areas);
		// 设置运费活动
		Set<PromotionOrderRule> promotionOrderRules = new LinkedHashSet<PromotionOrderRule>();
		for (String areaId : areaList) {
			PromotionOrderRule promotionOrderRule = new PromotionOrderRule();
			promotionOrderRule.setPromotion(p);
			Area area = new Area();
			area.setId(Long.valueOf(areaId));
			promotionOrderRule.setArea(area);
			promotionOrderRule.setRemitDeliveryFee(remitdeliveryfee);
			promotionOrderRule.setDeliveryFee(deliveryfee);
			promotionOrderRule.setMaxAmount(maxAmount);
			promotionOrderRule.setMinAmount(minAmount);
			promotionOrderRules.add(promotionOrderRule);
		}
		p.setOrderRules(promotionOrderRules);
		return p;
	}
}
