/*
 * @(#)PromotionProductForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.promotion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionProductRule;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.product.ProductService;

/**
 * 买商品送商品的form
 * 
 * @author zhongsen
 * @version 1.0,2011-9-15
 */
public class PromotionProductForm {
	private static final String DATEFORMAT = "yyyy-MM-dd hh:mm";
	// 编号
	private Long id;
	// 活动类型
	private String promotionType;
	// 创建人
	private String createUser;
	// 创建时间
	private String createTime;
	// 审核人
	private String assessor;
	// 审核时间
	private String assessTime;
	// 主商品
	private List<Long> productSaleIds;
	private List<Integer> productQuantity;
	// 赠品
	private List<Long> giftIds;
	private List<Integer> giftQuantity;
	// 是否可重复享受赠品
	private boolean isreplication;
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
	
	public String getPromotionTitle() {
		return promotionTitle;
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

	public List<Long> getProductSaleIds() {
		return productSaleIds;
	}

	public void setProductSaleIds(List<Long> productSaleIds) {
		this.productSaleIds = productSaleIds;
	}

	public List<Integer> getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(List<Integer> productQuantity) {
		this.productQuantity = productQuantity;
	}

	public List<Long> getGiftIds() {
		return giftIds;
	}

	public void setGiftIds(List<Long> giftIds) {
		this.giftIds = giftIds;
	}

	public List<Integer> getGiftQuantity() {
		return giftQuantity;
	}

	public void setGiftQuantity(List<Integer> giftQuantity) {
		this.giftQuantity = giftQuantity;
	}

	public boolean isIsreplication() {
		return isreplication;
	}

	public void setIsreplication(boolean isreplication) {
		this.isreplication = isreplication;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAssessor() {
		return assessor;
	}

	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	public String getAssessTime() {
		return assessTime;
	}

	public void setAssessTime(String assessTime) {
		this.assessTime = assessTime;
	}

	public Promotion getPromotion(Promotion promotion,ProductService productService, Employee operator) {
		boolean isAdd=false;
		if (promotion == null) {
			promotion = new Promotion();
			promotion.setCreateTime(new Date());
			promotion.setCreateUser(operator);
			isAdd=true;
		}
		promotion.setType(new Code(Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT));
		promotion.setTitle(promotionTitle);
		promotion.setDescription(promotionDescription);
		promotion.setAdvert(advert);
		promotion.setAdvertUrl(advertUrl);
		promotion.setEffectiveTime(effectivetime);
		try {
			promotion.setStartDate(new SimpleDateFormat(DATEFORMAT).parse(promotionStartDate+" "+promotionStartTime));
		} catch (ParseException e) {
			promotion.setStartDate(new Date());
		}
		try {
			promotion.setEndDate(new SimpleDateFormat(DATEFORMAT).parse(promotionEndDate+" "+promotionEndTime));
		} catch (ParseException e) {
			promotion.setStartDate(new Date());
		}
		promotion.setReplication(isreplication);
		
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
		
		// 设置商品set
		Set<PromotionProductRule> promotionProductRules = new LinkedHashSet<PromotionProductRule>();
		// 设置主商品
		for (int i = 0; i < productSaleIds.size(); i++) {
			PromotionProductRule promotionProductRule = new PromotionProductRule();
			promotionProductRule.setProductSale(productService.getProductSale(productSaleIds.get(i)));
			promotionProductRule.setProductNum(productQuantity.get(i));
			promotionProductRule.setProductNums(0);
			promotionProductRule.setPromotion(p);
			promotionProductRule.setProductType(new Code(Code.PROMOTION_PRODUCT_TYPE_MAIN));
			promotionProductRules.add(promotionProductRule);
		}

		// 设置赠品
		for (int i = 0; i < giftIds.size(); i++) {
			PromotionProductRule promotionProductRule = new PromotionProductRule();
			promotionProductRule.setProductSale(productService.getProductSale(giftIds.get(i)));
			promotionProductRule.setProductNum(giftQuantity.get(i));
			promotionProductRule.setProductNums(0);
			promotionProductRule.setPromotion(p);
			promotionProductRule.setProductType(new Code(Code.PROMOTION_PRODUCT_TYPE_GIFT));
			promotionProductRules.add(promotionProductRule);
		}
		p.setProductRules(promotionProductRules);
		return p;
	}
}
