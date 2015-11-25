package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerComplain;
import com.winxuan.framework.dynamicdao.annotation.Delete;
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
 * 
 * @author cast911 玄玖东
 *
 */
public interface CustomerComplainDao {
	
	@Query("from CustomerComplain c")
	@Conditions({
		@Condition("c.customer.id = :customer"),
		@Condition("c.customer.name = :customerName"),
		@Condition("c.createTime>= :startCreateTime"), 
		@Condition("c.createTime<= :endCreateTime"),
		@Condition("c.order.id = :orderId"),
		@Condition("c.state.id = :state")
		
	})
	@OrderBys({
    	@OrderBy("c.createTime desc"),
    	@OrderBy("c.createTime asc")
	})
	List<CustomerComplain> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,@Order Short sort);
	
	@Get
	CustomerComplain get(Long id);
	
	@Save
	void save(CustomerComplain complainInfo);
	@Update
	void update(CustomerComplain complainInfo);
	@Delete
	void delete(CustomerComplain complainInfo);

}
