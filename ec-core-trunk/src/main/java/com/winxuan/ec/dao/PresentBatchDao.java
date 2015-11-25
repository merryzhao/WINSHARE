/*
 * @(#)PresentBatchDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.present.PresentBatch;
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
 * @author  HideHai
 * @version 1.0,2011-8-30
 */
public interface PresentBatchDao {
	@Get
	PresentBatch get(Long id);
	
	@Save
	void save(PresentBatch presentBatch);
	
	@Update
	void update(PresentBatch presentBatch);
	
	@Query("from PresentBatch p where p.generalCode =?")
	PresentBatch getByGeneralCode(String generalCode);
	
	/**
	 * ��ѯ��ȯ
	 * 
	 * @param parameters
	 * <br/>
	 *            
	 */
	@Query("from PresentBatch p")
	@Conditions({ @Condition("p.id = :id"),
			@Condition("p.value = :value"),
			@Condition("p.batchTitle like :batchTitle"),
			@Condition("p.createUser.id = :createUser"),
			@Condition("p.createUser.name = :createUserName"),
			@Condition("p.state.id in :stateId"),
			@Condition("p.presentStartDate >= :presentStartDate"),
			@Condition("p.presentEndDate <= :presentEndDate")})
	@OrderBys({
		@OrderBy("p.id asc"),
    	@OrderBy("p.id desc"),
    	@OrderBy("p.createTime desc"),
    	@OrderBy("p.assessTime desc")
	})
	List<PresentBatch> find(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,@Order short orderBy);
	
	@Query("from PresentBatch p")
	@Conditions({ @Condition("p.id = :id"),
			@Condition("p.value = :value"),
			@Condition("p.batchTitle like :batchTitle")})
	List<PresentBatch> find(
			@ParameterMap Map<String, Object> parameters);
	
	@Query("from PresentBatch p where p.state.id = 16001")
	long getNeedVerifyCount();
	
	@Query("select p.value from  PresentBatch p group by p.value")
	List<BigDecimal> findValue();
}

