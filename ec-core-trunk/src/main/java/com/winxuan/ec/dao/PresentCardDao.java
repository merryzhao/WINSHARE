/*
 * @(#)PresentCardDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.presentcard.PresentCardDealLog;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
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
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-29
 */
public interface PresentCardDao {

	@Get
	PresentCard get(String id);

	@Update
	void update(PresentCard presentCard);

	@Save
	void save(PresentCard presentCard);
	
	
	
	@Query("from PresentCard p")
	@Conditions({
		    @Condition("p.id in :ids"),
			@Condition("p.type.id = :type"),
			@Condition("p.status.id = :status"),
			@Condition("p.status.id in :statusList"),
			@Condition("p.customer.id = :customerId"),
			@Condition("p.denomination = :denomination"),
			@Condition("p.createDate >= :startDate"),
			@Condition("p.createDate <= :endDate"),
			@Condition("p.order.id = :orderId"),
			@Condition("p.balance > :balanceBegin"),
			@Condition("p.expired = :expired"),
			@Condition("p.endDate >= :startExpiredDate"),
			@Condition("p.endDate <= :endExpiredDate")
	})
	@OrderBys({
		@OrderBy("p.id desc"), 
		@OrderBy("p.id asc"),
		@OrderBy("p.denomination asc")
	})
	List<PresentCard>find(@ParameterMap Map<String, Object> parameters, @Page Pagination pagination,
			@Order short orderIndex);
	
	
	@Query("from PresentCardDealLog pcdl where pcdl.presentCard.id = ? order by pcdl.id desc")
	List<PresentCardDealLog> findDealLogList(String id);
	
	@Query("from PresentCardDealLog pcdl ")
		@Conditions({
			@Condition("pcdl.order.id = :orderId"),
			@Condition("pcdl.operator.id = :customerId"),
			@Condition("pcdl.presentCard.id = :presentCardId")
	})
	@OrderBys({
		@OrderBy("pcdl.presentCard desc")
	})
	List<PresentCardDealLog> findDealLogList(@ParameterMap Map<String,Object> map,
			@Page Pagination pagination,@Order short orderIndex);
	
	@Query(value = "select sum(balance) from presentcard where customer=? and status in (18005,18006) and enddate>=now()", sqlQuery = true)
	BigDecimal getTotalBalanceByCustomer(Long customerId);
}
