/*
 * @(#)PromotionDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionOrderRule;
import com.winxuan.ec.model.promotion.PromotionProductRule;
import com.winxuan.ec.model.promotion.PromotionRegisterRule;
import com.winxuan.ec.model.promotion.PromotionTag;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.FlushSession;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author zhongsen
 * @version 1.0,2011-9-14
 */
public interface PromotionDao {

	@Get
	Promotion get(Long id);
	
	@Save
	void save(Promotion promotion);

	@Update
	void update(Promotion promotion);
	
	@Delete
	void delete(PromotionProductRule productRule);
	
	@Delete
	void delete(PromotionOrderRule orderRule);
	
	@Delete
	void delete(PromotionRegisterRule registerRule);

	@Query("from Promotion p")
	@Conditions({ 
		@Condition("p.id  =:pId"),
		@Condition("p.title like :pTitle"), 
		@Condition("p.type.id in :pTypeId"),
		@Condition("p.status.id in :pStatusId"),
		@Condition("p.startDate >= :pStartDateBegin"),
		@Condition("p.startDate < :pStartDateOver"),
		@Condition("p.endDate >= :pEndDateBegin"),
		@Condition("p.endDate < :pEndDateOver")
		})
	@OrderBys({
    	@OrderBy("p.createTime desc")
	})
	List<Promotion> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,@Order short orderIndex);
	
	@Query("select distinct p from Promotion p left join p.productRules pr ")
	@Conditions({ 
		@Condition("p.id  =:pId"),
		@Condition("p.title like :pTitle"), 
		@Condition("p.type.id in :pTypeId"),
		@Condition("p.status.id in :pStatusId"),
		@Condition("p.startDate >= :pStartDateBegin"),
		@Condition("p.startDate < :pStartDateOver"),
		@Condition("p.endDate >= :pEndDateBegin"),
		@Condition("p.endDate < :pEndDateOver"),
		@Condition("pr.productSale in :productSales")
		})
	@OrderBys({ 
		@OrderBy("p.type.id asc")
	})
	List<Promotion> find(@ParameterMap Map<String, Object> parameters,@Order short orderIndex);
	
	
	@Query("from Promotion p where p.status.id = 29001")
	long getNeedVerifyCount();
	
	@Query("from Code c where c.parent.id=20000")
	List<Code> findActivityTypes();
	
	@Query(sqlQuery=true,executeUpdate=true,value="{call up_productsale_prom(?)}")
	void callUpdateProcedure(Long promotionId);
	
	@FlushSession
	void flush();
	
	@Get
	PromotionTag getPromotionTag(Long id);
	
	@Query("from PromotionTag pt")
	@Conditions({
		@Condition("pt.id =:id"),
		@Condition("pt.type =:type"),
		@Condition("pt.available =:available")
	})
	@OrderBys({
		@OrderBy("pt.rank asc")
	})
	List<PromotionTag> findPromotionTag(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,@Order short orderIndex);
	
	@SaveOrUpdate
	void saveorupdate(PromotionTag promotionTag);
	
}
