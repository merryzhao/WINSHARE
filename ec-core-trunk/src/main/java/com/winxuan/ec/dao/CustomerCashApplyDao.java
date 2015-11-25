package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerCashApply;
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
 * @author  zhoujun
 * @version 1.0,2011-10-26
 */
public interface CustomerCashApplyDao {
	
	@Save
	void save(CustomerCashApply customerCashApply);
	
	@Get
	CustomerCashApply get(Long id);
	
	@Update
	void update(CustomerCashApply customerCashApply);
	
	@Query("from CustomerCashApply c")
	@Conditions({
		@Condition("c.customer = :customer"),
		@Condition("c.customer.name = :customerName"),
		@Condition("c.id = :id"),
		@Condition("c.id in :ids"),
		@Condition("c.status.id = :status"),
		@Condition("c.type.id = :type"),
		@Condition("c.createTime >= :startCreateTime"),
		@Condition("c.createTime <= :endCreateTime")
		
	})
		@OrderBys({
		@OrderBy("c.createTime desc"),
		@OrderBy("c.createTime asc")
	})
	List<CustomerCashApply> findCustomerCashApply(
			@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,@Order short orderIndex);
	
	
}
