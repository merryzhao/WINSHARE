/*
 * @(#)PromotionService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.promotion;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.PromotionException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionTag;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.pagination.Pagination;

/**
 * 促销活动业务接口
 * @author  yuhu
 * @version 1.0,2011-9-14
 */
public interface PromotionService {

	/**
	 * 获取促销活动
	 * @param id 促销活动id
	 * @return
	 */
	Promotion get(Long id);
	
	/**
	 * 保存促销活动
	 * @param promotion
	 * @param employee
	 */
	void save(Promotion promotion,Employee operator) throws PromotionException;

	/**
	 * 更新促销活动
	 * 当促销活动状态为已激活状态，且活动为 单品价格优惠时需将相应的活动信息更新到ProductSale
	 * 当活动为单品价格优惠时，是通过调用存储过程来更新productpromotion的，具体方法是callUpdateProcedure
	 * 
	 * @param promotion
	 * @param employee
	 */
	void update(Promotion promotion,Employee operator) throws PromotionException;
	
	/**
	 * 审核促销活动
	 * 当促销活动状态为已激活状态，且活动为 单品价格优惠时需将相应的活动信息更新到ProductSale
	 * 当活动为单品价格优惠时，是通过调用存储过程来更新productpromotion的，具体方法是callUpdateProcedure
	 * 
	 * @param promotion  为审核前的promotion
	 * @param isPass 是否审核通过
	 * @param operator
	 * @throws PromotionException
	 */
	void audit(Promotion promotion,Boolean isPass,Employee operator) throws PromotionException;
	
	/**
	 * 停用促销活动
	 * 当促销活动状态为已激活状态，且活动为 单品价格优惠时需将相应的活动信息更新到ProductSale
	 * 当活动为单品价格优惠时，是通过调用存储过程来更新productpromotion的，具体方法是callUpdateProcedure
	 * @param promotion
	 * @param operator
	 * @throws PromotionException
	 */
	void stop(Promotion promotion,Employee operator) throws PromotionException;
	
	/**
	 * 取得需要审核的促销活动数量
	 * @return
	 */
	long getNeedVerifyCount();
	
	/**
	 * 查询促销活动
	 * 查询条件中pTypeId、pStatusId、pbaseRulesId、pChannelId、pShopId为数组
	 * @param parameters
	 * @param pagination 
	 * @return
	 */
	List<Promotion> find(Map<String,Object> parameters,Pagination pagination);
	
	/**
	 * 查询促销活动
	 * @param parameters
	 * @return
	 */
	List<Promotion> find(Map<String,Object> parameters);
	
	/**
	 * 查询所有促销活动类型
	 * @return
	 */
	List<Code> findActivityType();
	
	/**
	 * 获取与给定时间段有时间冲突的promotionTypeIds类别的活动 
	 * @param promotionTypeIds
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Promotion> findEffectivePromotion(Long[] promotionTypeIds,Date startDate,Date endDate);
	
	/**
	 * 根据商品获取当前生效的促销活动
	 * 该接口主要用于购物车和下单验证
	 * @param products  商品集合
	 * @return
	 */
	List<Promotion> findEffectivePromotion(List<ProductSale> products) throws PromotionException;
	
	/**
	 * 根据商品获取当前生效的促销活动
	 * 商品底层页展示
	 * @param ps
	 * @return
	 */
	List<Promotion> findEffectivePromotion(ProductSale ps);
	
	/**
	 * 对购物车设置促销信息
	 * 主要用于购物车展示
	 * @param shoppingcart
	 * @param isUnion
	 */
	void setupShoppingcartPromotion(Shoppingcart shoppingcart);
	/**
	 * 设置订单促销活动
	 * 
	 * @param order order传入该方法时为一个原生的order，没有加入任何促销信息的
	 * @return
	 */
	void setupOrderPromotion(Order order);
	
	/**
	 * 促销生效
	 * 针对非提交订单时生效的促销活动
	 * 现仅用于满送礼券活动
	 * @param order
	 */
	void orderPromotionEffect(Order order) throws PromotionException;
	
	/**
	 * 促销活动标签
	 * @return
	 */
	List<PromotionTag> findPromotionTag(Map<String, Object> parameters,Pagination pagination);
	
	/**
	 * 查询促销活动标签
	 * @param id
	 * @return
	 */
	PromotionTag getPromotionTag(Long id);
	
	/**
	 * 保存修改促銷活動標簽
	 * @param promotionTag
	 */
	void saveorupdateTag(PromotionTag promotionTag);

}
