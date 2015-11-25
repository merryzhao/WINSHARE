/*
 * @(#)PromotionProductForm.java
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
public class SingleProductForm {
	public static final String DATAFORMART = "yyyy-MM-dd hh:mm";
	//主商品
	private List<Long> productSaleIds;
	//实洋
	private List<BigDecimal> salePrice;
	//限购数量
	private List<Integer> num;
	//限购总数量
	private List<Integer> nums;
	//促销标题
	private String promotionTitle;
	//促销描述
	private String promotionDescription;
	//促销开始日期
	private String promotionStartDate;
	//促销结束日期
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
	//获得promotion
	public Promotion getPromotion(Promotion promotion,ProductService productService,Employee e){
		boolean add = false;
		if(promotion == null){
			promotion = new Promotion();
			promotion.setCreateTime(new Date());
			promotion.setCreateUser(e);
			add = true;
		}
		promotion.setTitle(promotionTitle);
		promotion.setDescription(promotionDescription);
		try {
			promotion.setStartDate(new SimpleDateFormat(DATAFORMART).parse(promotionStartDate+" "+promotionStartTime));
		} catch (ParseException ee) {
			promotion.setStartDate(null);
		}
		try {
			promotion.setEndDate(new SimpleDateFormat(DATAFORMART).parse(promotionEndDate+" "+promotionEndTime));
		} catch (ParseException ee) {
			promotion.setEndDate(null);
		}
		promotion.setAdvert(advert);
		promotion.setAdvertUrl(advertUrl);
		promotion.setEffectiveTime(effectivetime);
		promotion.setType(new Code(Code.PROMOTION_TYPE_PRODUCT_AMOUNT));
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
		Set<PromotionProductRule> promotionProductRules = new LinkedHashSet<PromotionProductRule>();
		for (int i = 0; i < productSaleIds.size(); i++) {
			PromotionProductRule promotionProductRule = new PromotionProductRule();
			promotionProductRule.setProductSale(productService.getProductSale(productSaleIds.get(i)));
			promotionProductRule.setSalePrice(salePrice.get(i));
			promotionProductRule.setProductNum(num.get(i));
			promotionProductRule.setProductNums(nums.get(i));
			promotionProductRules.add(promotionProductRule);
			promotionProductRule.setPromotion(p);
		}
		p.setProductRules(promotionProductRules);
		return p;	
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

	public List<BigDecimal> getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(List<BigDecimal> salePrice) {
		this.salePrice = salePrice;
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
	public List<Integer> getNum() {
		return num;
	}
	public void setNum(List<Integer> num) {
		this.num = num;
	}
	public List<Integer> getNums() {
		return nums;
	}
	public void setNums(List<Integer> nums) {
		this.nums = nums;
	}
}
