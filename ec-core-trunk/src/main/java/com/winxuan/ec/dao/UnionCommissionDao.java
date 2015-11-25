package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.union.UnionCommission;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;
/**
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
public interface UnionCommissionDao {
	
	@Save
	void save(UnionCommission unionCommission);
	
	@Update
	void update(UnionCommission unionCommission);
	
	@Get
	UnionCommission get(Long id);
	
	@Query("from UnionCommission u where u.union.id =? and u.time=? ")
	UnionCommission getByUnionAndDate(Long id, String time);
	
	@Query("from UnionCommission u ")
	@Conditions({
		@Condition("u.union.id =:unionId"),
		@Condition("u.union.name =:name"),
		@Condition("u.time in:time"),
		@Condition("u.isPay =:isPay")	
	})
	@OrderBys({
		@OrderBy("u.union.id desc")
	})
	List<UnionCommission> find(@ParameterMap Map<String, Object> parameters,
			@com.winxuan.framework.dynamicdao.annotation.query.Order Short sort,@Page Pagination pagination);
	
}
