/*
 * @(#)PromotionValidateUtils.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.promotion;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.exception.PromotionException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionProductRule;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.service.promotion.PromotionService;
import com.winxuan.ec.support.util.DateUtils;

/**
 * description
 * @author  yuhu
 * @version 1.0,2011-9-20
 */
public class PromotionValidateUtils {
	
	private static final String BR = "<br />";
	private static final Log LOG = LogFactory.getLog(PromotionValidateUtils.class);
	
	public static void validatePromotion(Promotion promotion,PromotionService promotionService) throws PromotionException{
		if(Code.PROMOTION_TYPE_PRODUCT_AMOUNT.equals(promotion.getType().getId())
				|| Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT.equals(promotion.getType().getId())
				|| Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT.equals(promotion.getType().getId())){
			validateProductPromotion(promotion,promotionService);
		}else if(Code.PROMOTION_TYPE_CATEGORY_AMOUNT.equals(promotion.getType().getId())){
			validateCategoryPromotion(promotion, promotionService);
		}else {
			validatePresentPromotionRepeat(promotion, promotionService);
		}
	}

	/**
	 * 验证新建和修改后的买商品赠商品活动、单品调价活动、买部分商品满省时间是否与其他同类活动时间冲突
	 * @param promotion
	 * @throws PromotionException
	 */
	private static void validateProductPromotion(Promotion promotion,PromotionService promotionService) throws PromotionException{
		Set<PromotionProductRule> rules = promotion.getProductRules();
		StringBuilder err = new StringBuilder();
		if(rules != null && !rules.isEmpty()){
			//判断当前活动中是否有相同商品
			PromotionProductRule[] ruleArray = rules.toArray(new PromotionProductRule[]{});
			for(int i=0;i<ruleArray.length-1;i++){
				if(ruleArray[i].getProductSale() == null){
					throw new PromotionException("The productSale in PromotionProductRule can not be null when the Promotion is a product type promotion!!");
				}
				if(Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT.equals(promotion.getType().getId())
						&& Code.PROMOTION_PRODUCT_TYPE_GIFT.equals(ruleArray[i].getProductType().getId())){
					continue;
				}
				for(int j=i+1;j<ruleArray.length;j++){
					if(ruleArray[j].getProductSale() == null){
						throw new PromotionException("The productSale in PromotionProductRule can not be null when the Promotion is a product type promotion!!");
					}
					if(Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT.equals(promotion.getType().getId())
							&& Code.PROMOTION_PRODUCT_TYPE_GIFT.equals(ruleArray[j].getProductType().getId())){
						continue;
					}
					if(ruleArray[i].getProductSale().getId().equals(ruleArray[j].getProductSale().getId())){
						err.append("商品").append(ruleArray[i].getProductSale().getId()).append("在该活动中被设置了多次!").append(BR);
					}
				}
			}
			
			Code type = promotion.getType();
			Date startDate = promotion.getStartDate();
			Date endDate = promotion.getEndDate();
			if(Code.PROMOTION_TYPE_CATEGORY_AMOUNT.equals(type.getId()) || Code.PROMOTION_TYPE_PRODUCT_AMOUNT.equals(type.getId()) 
					|| Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT.equals(type.getId())){
				try {
					startDate = DateUtils.getEarlyInTheDay(startDate);
					endDate = DateUtils.getLateInTheDay(endDate);
				} catch (ParseException e) {
					LOG.error("promotion startDate or endDate parse error!", e);
				}
			}
			
			//判断活动中的商品与之前活动时间是否有冲突
			List<Promotion> pList = promotionService.findEffectivePromotion(new Long[]{type.getId()}, startDate,endDate);
			
			if(!CollectionUtils.isEmpty(pList)){
				for(PromotionProductRule rule : rules){
					if(Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT.equals(promotion.getType().getId())
							&& Code.PROMOTION_PRODUCT_TYPE_GIFT.equals(rule.getProductType().getId())){
						continue;
					}
					for(Promotion oldP : pList){
						if(!oldP.getId().equals(promotion.getId())){
							for(PromotionProductRule oldRule : oldP.getProductRules()){
								if(Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT.equals(oldP.getType().getId())
										&& Code.PROMOTION_PRODUCT_TYPE_GIFT.equals(oldRule.getProductType().getId())){
									continue;
								}
								if(rule.getProductSale().getId().equals(oldRule.getProductSale().getId())){
									err.append("商品 ")
										.append(rule.getProductSale().getId()).append(" 与活动 ")
										.append(getPromotionUpdateUrl(oldP)).append("时间冲突 ").append(BR);
								}
							}
						}
					}
				}
			}
		}
		if(err.length() > 0){
			err.append(BR).append("商品类型活动同一天只能有一个！");
			throw new PromotionException(err.toString());
		}
	}
	
	/**
	 * 检测分类活动是否与已有活动时间有冲突
	 * @param promotion
	 * @param promotionService
	 * @throws PromotionException
	 */
	private static void validateCategoryPromotion(Promotion promotion,PromotionService promotionService) throws PromotionException{
		//检测是否有分类调价与该活动的时间冲突
		StringBuilder err = new StringBuilder();
		Set<PromotionProductRule> ruleList = promotion.getProductRules();
		if(ruleList != null && !ruleList.isEmpty()){   
			PromotionProductRule[] rules = ruleList.toArray(new PromotionProductRule[]{});
			for(int i=0;i<rules.length-1;i++){   //检查当前活动中的分类是否有冲突 
				if(rules[i].getCategory() == null){
					throw new PromotionException("The category in PromotionProductRule can not be null when the Promotion is a category type promotion!!");
				}
				for(int j=i+1;j<rules.length;j++){
					if(rules[j].getCategory() == null){
						throw new PromotionException("The category in PromotionProductRule can not be null when the Promotion is a category type promotion!!");
					}
					if (rules[i].getCategory().checkParent(
							rules[j].getCategory())
							|| rules[j].getCategory().checkParent(
									rules[i].getCategory())) {
						err.append("分类  ")
								.append(rules[i].getCategory().getAllName()).append(" 与分类  ")
								.append(rules[j].getCategory().getAllName()).append("相冲突!").append(BR);
					}
				}
			}
			List<Promotion> pList = promotionService.findEffectivePromotion(new Long[]{promotion.getType().getId()}, promotion.getStartDate(),promotion.getEndDate());
			if(pList!=null && !pList.isEmpty()){
				for(int i=0;i<rules.length;i++){
					for(Promotion oldP : pList){
						if(!oldP.getId().equals(promotion.getId())){
							for(PromotionProductRule oldRule : oldP.getProductRules()){
								if(rules[i].getCategory().checkParent(oldRule.getCategory()) || oldRule.getCategory().checkParent(rules[i].getCategory())){
									err.append("分类  ")
									.append(rules[i].getCategory().getAllName()).append(" 与活动 ")
									.append(getPromotionUpdateUrl(oldP)).append("的分类 ")
									.append(oldRule.getCategory().getAllName()).append("相冲突!").append(BR);
								}
							}
						}
					}
				}
			}
		}
		if(err.length() > 0){
			throw new PromotionException(err.toString());
		}
	}
	
	/**
	 * 检查与promotion是否有同类别的送礼券类型活动时间重合
	 * @param promotion
	 * @param promotionService
	 * @throws PromotionException
	 */
	private static void validatePresentPromotionRepeat(Promotion promotion,PromotionService promotionService) throws PromotionException{
		List<Promotion> pList = promotionService.findEffectivePromotion(new Long[]{promotion.getType().getId()}, promotion.getStartDate(),promotion.getEndDate());
		StringBuilder err = new StringBuilder();
		if (pList != null && !pList.isEmpty()) {
			for (Promotion p : pList) {
				if (!p.getId().equals(promotion.getId())) {
					if (checkOrderTypeRepeat(p, promotion)) {
						if(StringUtils.isBlank(err.toString())){
							err.append("该活动与同类型的").append(BR);
						}
						err.append(getPromotionUpdateUrl(p)).append(BR);
					}
				}
			}
			if(!StringUtils.isBlank(err.toString())){
				err.append("时间相冲突！");
			}
		}
		if (err.length() > 0) {
			throw new PromotionException(err.toString());
		}
	}
	/**
	 * 检查运费减免活动、满省、满赠活动是否重复，主要检查相同卖家是否已经设置过
	 * @param newP
	 * @param oldP
	 * @return
	 */
	private static boolean checkOrderTypeRepeat(Promotion newP,Promotion oldP) {
		String[] shopIds = newP.getShopIds();
		if(shopIds	!=	null){
		for (int i=0;i<shopIds.length;i++) {
			Shop s = new Shop();
			s.setId(Long.valueOf(shopIds[i]));
			if (oldP.checkContainsShop(s)) {
				return true;
			}
		}
		}
		return false;
	}
	
	/**
	 * 获取促销活动修改页面链接
	 * @param promotion
	 * @return
	 */
	private static String getPromotionUpdateUrl(Promotion promotion){
		if(promotion == null){
			return null;
		}
		return "<a href='/promotion/"+promotion.getId()+"/edit' target='_blank'>"+promotion.getId()+"</a>";
	}
}
