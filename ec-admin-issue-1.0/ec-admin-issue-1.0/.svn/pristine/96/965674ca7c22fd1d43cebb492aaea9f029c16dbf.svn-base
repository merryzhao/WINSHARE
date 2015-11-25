/*
 * @(#)PromotionProductForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.promotion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionProductRule;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.category.CategoryService;

/**
 * 买商品送商品的form
 * 
 * @author zhongsen
 * @version 1.0,2011-9-15
 */
public class CategoryPriceForm {
	
	public static final String DATAFORMART = "yyyy-MM-dd hh:mm";
	
	//卖家
	private List<Long> sellers;
	//促销标题
	private String promotionTitle;
	//促销描述
	private String promotionDescription;
	//促销开始时间
	private String promotionStartDate;
	//促销结束时间
	private String promotionEndDate;
	//促销开始时间
	private String promotionStartTime;
	//促销结束时间
	private String promotionEndTime;
	//促销广告语
	private String advert;
	//促销广告图片
	private String advertImage;
	//促销专题链接
	private String advertUrl;
	//订单支付有效时间
	private Integer effectivetime;
	//折扣
	private List<Integer> categoryDiscounts;
	//折扣方式
	private List<Long> categoryDisTypes;
	//分类
	private List<Long> categorys;
	/**
	 * 更新时promotion为持久化了的对象
	 * 返回一个没有持久化的对象
	 * @param promotion
	 * @param e
	 * @return
	 */
	public Promotion getPromotion(Promotion promotion,Employee e,CategoryService categoryService) {
		boolean add = false;
		if(promotion == null){
			promotion = new Promotion();
			promotion.setCreateTime(new Date());
			promotion.setCreateUser(e);
			add = true;
		}
		// 促销标题
		promotion.setTitle(promotionTitle);
		// 促销描述
		promotion.setDescription(promotionDescription);
		// 促销开始时间
		try {
			promotion.setStartDate(new SimpleDateFormat(DATAFORMART).parse(promotionStartDate+" "+promotionStartTime));
		} catch (ParseException ee) {
			promotion.setStartDate(null);
		}
		// 促销结束时间
		try {
			promotion.setEndDate(new SimpleDateFormat(DATAFORMART).parse(promotionEndDate+" "+promotionEndTime));
		} catch (ParseException ee) {
			promotion.setEndDate(null);
		}
		// 促销广告语
		promotion.setAdvert(advert);
		promotion.setAdvertUrl(advertUrl);
		// 订单支付有效时间
		promotion.setEffectiveTime(effectivetime);
		StringBuilder sb = new StringBuilder();
		// 设置卖家
		for (Long seller : sellers) {
			if(!sb.toString().isEmpty()){
				sb.append(Promotion.COMMA);
			}
			sb.append(seller);
		}
		promotion.setShops(sb.toString());
		promotion.setType(new Code(Code.PROMOTION_TYPE_CATEGORY_AMOUNT));
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
		
		Set<PromotionProductRule> promotionProductRules = new HashSet<PromotionProductRule>();
		// 设置分类和折扣
		for (int i = 0; i < categorys.size(); i++) {
			PromotionProductRule promotionProductRule = new PromotionProductRule();
			Category c = categoryService.get(categorys.get(i));
			promotionProductRule.setCategory(c);
			promotionProductRule.setCategoryDiscount(categoryDiscounts.get(i));
			promotionProductRule.setCategoryDisType(new Code(categoryDisTypes.get(i)));
			promotionProductRule.setPromotion(p);
			promotionProductRule.setProductNums(0);
			promotionProductRules.add(promotionProductRule);
		}
		p.setProductRules(promotionProductRules);
		return p;
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

	public void setPromotionEndDate(String promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
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

	public List<Long> getSellers() {
		return sellers;
	}
	public void setSellers(List<Long> sellers) {
		this.sellers = sellers;
	}
	public List<Integer> getCategoryDiscounts() {
		return categoryDiscounts;
	}
	public void setCategoryDiscounts(List<Integer> categoryDiscount) {
		this.categoryDiscounts = categoryDiscount;
	}
	public List<Long> getCategoryDisTypes() {
		return categoryDisTypes;
	}
	public void setCategoryDisTypes(List<Long> categoryDisType) {
		this.categoryDisTypes = categoryDisType;
	}
	public List<Long> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<Long> category) {
		this.categorys = category;
	}
}
