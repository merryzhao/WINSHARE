/*
 * @(#)PresentDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.present.Present;
import com.winxuan.framework.dynamicdao.annotation.Evict;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 礼券
 * @author  HideHai
 * @version 1.0,2011-8-30
 */
public interface PresentDao {

	@Get
	Present get(Long id);

	@Save
	void save(Present present);

	@Update
	void update(Present present);
	
	@SaveOrUpdate
	void saveOrUpdate(Present present);
	
	@Merge
	void merge(Present present);
	
	@Evict
	void evict(Present present);
	
	@Query("from Present p where p.code =?")
	boolean isExisted(String code);
	
	@Query("from Present p where p.code =? and (p.state.id=17001 or p.state.id=17002)")
	long isExistedByCode(String code);
	

	@Query("from Present p where p.code =? and (p.state.id=17001 or p.state.id=17002)")
	Present getGeneratePresentByCode(String code);

	@Query("from Present p")
	@Conditions({
		@Condition("p.customer.id = :customer"),
		@Condition("p.order.id = :order"),
		@Condition("p.state = :state"),
		@Condition("p.startDate <= :endDate"),
		@Condition("p.endDate >= :startDate")
	})
	List<Present> findPresnet(@ParameterMap Map<String, Object> parameters);

	@Query("from Present p order by p.endDate desc")
	@Conditions({
		@Condition("p.id in :id"),
		@Condition("p.order.id = :order"),
		@Condition("p.code in :code"),
		@Condition("p.value = :value"),
		@Condition("p.batch.id in :batch"),
		@Condition("p.batch.state.id in :batchstate"),
		@Condition("p.payTime = :payTime"),
		@Condition("p.customer.id = :customer"),
		@Condition("p.customer.name in :customers"),
		@Condition("p.state.id in :state"),
		@Condition("p.startDate <= :endDate"),
		@Condition("p.endDate >= :startDate"),
		@Condition("p.endDate < :currentDate")
	})
	List<Present> find(@ParameterMap Map<String, Object> parameters, @Page Pagination pagination);
	
	@Query("from Present p where p.batch.id =?")
	List<Present> findByBatch(Long id, @Page Pagination pagination);

	@Query("from Present p where p.batch.id = ? and p.state.id = ?")
	long getCountByBatchAndState(Long batchId,Long stateId);
	
	@Query("from Present p where p.batch.id = ?")
	long getCount(Long batchId);
	
	@Query("from Present p where p.batch.id = ? and p.customer.id = ?")
	long getCount(Long batchId,Long customerId);
}

