/*
 * @(#)PromotionQueryForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.promotion;

 
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionOrderRule;
import com.winxuan.ec.model.user.Employee;
/**
 * 订单满省
 *
 * @author wumaojie
 * @version 1.0,2011-9-20
 */
public class PromotionOrderPriceForm {

	//活动编号
	private Long promotionId;
	//活动标题
	private String promotionTitle;
	//活动类型
	private Long promotionType;
	//开始有效时间
	private String promotionStartDate;
	//结束有效时间
	private String promotionEndDate;
	//促销开始时间
	private String promotionStartTime;
	//促销结束时间
	private String promotionEndTime;
 	// 促销描述
	private String promotionDescription;
	// 促销广告语
	private String advert;
	//促销广告图片
	private String advertImage;
	//促销专题链接
	private String advertUrl;
	// 订单支付有效时间
	private Integer effectivetime;
	// 卖家
	private Long[] orderPricesellers;
	// 促销方式
	private Boolean orderPricemanner;
	// 普通消费金额
	private BigDecimal generalPriceSpends;
	// 普通节省金额
	private BigDecimal generalPriceSpares;
	// 梯度消费金额
	private BigDecimal[] gradsPriceSpends;
	// 梯度节省金额
	private BigDecimal[] gradsPriceSpares;
	// 时间格式
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
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
	public String getPromotionStartDate() {
		return promotionStartDate;
	}
	public void setPromotionStartDate(String promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}
	public String getPromotionEndDate() {
		return promotionEndDate;
	}
	public void setPromotionEndDate(String promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
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
	public Long[] getOrderPricesellers() {
		return orderPricesellers;
	}
	public void setOrderPricesellers(Long[] orderPricesellers) {
		this.orderPricesellers = orderPricesellers;
	}
	public Boolean getOrderPricemanner() {
		return orderPricemanner;
	}
	public void setOrderPricemanner(Boolean orderPricemanner) {
		this.orderPricemanner = orderPricemanner;
	}
	public SimpleDateFormat getDateformat() {
		return dateformat;
	}
	public void setDateformat(SimpleDateFormat dateformat) {
		this.dateformat = dateformat;
	}
	public BigDecimal getGeneralPriceSpends() {
		return generalPriceSpends;
	}
	public void setGeneralPriceSpends(BigDecimal generalPriceSpends) {
		this.generalPriceSpends = generalPriceSpends;
	}
	public BigDecimal getGeneralPriceSpares() {
		return generalPriceSpares;
	}
	public void setGeneralPriceSpares(BigDecimal generalPriceSpares) {
		this.generalPriceSpares = generalPriceSpares;
	}
	public BigDecimal[] getGradsPriceSpends() {
		return gradsPriceSpends;
	}
	public void setGradsPriceSpends(BigDecimal[] gradsPriceSpends) {
		this.gradsPriceSpends = gradsPriceSpends;
	}
	public BigDecimal[] getGradsPriceSpares() {
		return gradsPriceSpares;
	}
	public void setGradsPriceSpares(BigDecimal[] gradsPriceSpares) {
		this.gradsPriceSpares = gradsPriceSpares;
	}
	/**
	 * 返回促销有效起始时间的Date
	 * @return
	 */
	public Date getStartDate(){
		if(promotionStartDate!=null && !"".equals(promotionStartDate.trim())){
			try {
				Date date = dateformat.parse(promotionStartDate+" "+promotionStartTime);
				return date;
			} catch (ParseException e) {
				return null;
			}
		}
		return null;	
	}
	
	/**
	 * 返回促销有效截止时间的Date
	 * @return
	 */
	public Date getEndDate(){
		if(promotionEndDate!=null && !"".equals(promotionEndDate.trim())){
			try {
				Date date = dateformat.parse(promotionEndDate+" "+promotionEndTime);
				return date;
			} catch (ParseException e) {
				return null;
			}
		}
		return null;	
	}
	/**
	 * 新建满省促销活动时获取Promotion
	 * @param sellerService
	 * @param img
	 * @param operator
	 * @return
	 */
	public Promotion getPromotion(Promotion promotion,MultipartFile img,Employee operator){
		boolean isAdd=false;
		if(promotion == null){
			promotion = new Promotion();
			promotion.setCreateTime(new Date());
			promotion.setCreateUser(operator);
			promotion.setStatus(new Code(Code.PROMOTION_STATUS_CREATE));
			isAdd=true;
		}
		// 促销标题
		promotion.setTitle(this.getPromotionTitle());
		// 促销描述
		promotion.setDescription(this.getPromotionDescription());
		// 促销广告语
		promotion.setAdvert(this.getAdvert());
		promotion.setAdvertUrl(this.getAdvertUrl());
		// 促销订单支付有效时间
		promotion.setEffectiveTime(this.getEffectivetime());
		// 促销有效起始时间
		promotion.setStartDate(this.getStartDate());
		// 促销有效截止时间
		promotion.setEndDate(this.getEndDate());
		// 订单满省活动
		promotion.setType(new Code(Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT));
		// 促销方式
		promotion
				.setManner(new Code(
						this.getOrderPricemanner() ? Code.PROMOTION_ORDER_SAVEORSEND_TYPE_GRADIENT
								: Code.PROMOTION_ORDER_SAVEORSEND_TYPE_NORMAL));
		// 设置卖家
		StringBuilder sb = new StringBuilder();
		for (Long seller : orderPricesellers) {
			if(!sb.toString().isEmpty()){
				sb.append(Promotion.COMMA);
			}
			sb.append(seller);
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
		// 设置活动规则
		Set<PromotionOrderRule> promotionOrderRules = new LinkedHashSet<PromotionOrderRule>();
		if (this.getOrderPricemanner()) {
			// 梯度满
			BigDecimal[] gradsPriceSpends = this.getGradsPriceSpends();
			// 梯度省
			BigDecimal[] gradsPriceSpares = this.getGradsPriceSpares();
			for (int i = 0; i < gradsPriceSpends.length; i++) {
				if (gradsPriceSpends[i] != null && gradsPriceSpares[i] != null) {
					PromotionOrderRule promotionOrderRule = new PromotionOrderRule();
					promotionOrderRule.setPromotion(p);
					// 满
					promotionOrderRule.setMinAmount(gradsPriceSpends[i]);
					// 省
					promotionOrderRule.setAmount(gradsPriceSpares[i]);
					promotionOrderRules.add(promotionOrderRule);
				}
			}
		} else {
			PromotionOrderRule promotionOrderRule = new PromotionOrderRule();
			promotionOrderRule.setPromotion(p);
			// 满
			promotionOrderRule.setMinAmount(this.getGeneralPriceSpends());
			// 省
			promotionOrderRule.setAmount(this.getGeneralPriceSpares());
			promotionOrderRules.add(promotionOrderRule);
		}
		// 将给则加入到促销活动中
		p.setOrderRules(promotionOrderRules);
		//返回
		return p;
	}
	
	@Override
	public String toString() {
		return "PresentOrderPriceForm [promotionId=" + promotionId
				+ ", promotionTitle=" + promotionTitle + ", promotionType="
				+ promotionType
				+ ", promotionStartDate=" + promotionStartDate
				+ ", promotionEndDate=" + promotionEndDate
				+ ", promotionDescription=" + promotionDescription
				+ ", advert=" + advert + ", advertImage=" + advertImage
				+ ", advertUrl="+advertUrl+", effectivetime=" + effectivetime
				+ ", orderPricesellers=" + Arrays.toString(orderPricesellers)
				+ ", orderPricemanner=" + orderPricemanner
				+ ", generalPriceSpends=" + generalPriceSpends
				+ ", generalPriceSpares=" + generalPriceSpares
				+ ", gradsPriceSpends=" + Arrays.toString(gradsPriceSpends)
				+ ", gradsPriceSpares=" + Arrays.toString(gradsPriceSpares)
				+ ", dateformat=" + dateformat + "]";
	}
	
}
