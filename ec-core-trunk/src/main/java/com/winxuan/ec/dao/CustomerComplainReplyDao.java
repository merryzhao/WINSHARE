package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;


import com.winxuan.ec.model.customer.CustomerComplainReply;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author cast911 玄玖东
 *
 */
public interface CustomerComplainReplyDao {
	
	@Query("from CustomerComplainReply c")
	@Conditions({
		@Condition("c.customer.id = :customer")
	})
	List<CustomerComplainReply> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	@Get
	CustomerComplainReply get(Long id);
	
	@Save
	void save(CustomerComplainReply complainReplyInfo);
	@Update
	void update(CustomerComplainReply complainReplyInfo);
	@Delete
	void delete(CustomerComplainReply complainReplyInfo);

}
