package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.groupinfo.GroupShoppingInfo;
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
 * 团购信息
 * 
 * @author cast911 玄玖东
 * 
 */
public interface GroupInfoDao {

	@Query("from GroupShoppingInfo gsi")
	List<GroupShoppingInfo> find();

	/**
	 * 新加接口
	 */
	@Query("from GroupShoppingInfo gsi")
	@Conditions({ 
		@Condition("gsi.id = :groupShopping"),
		@Condition("gsi.companyName = :companyName"),
		@Condition("gsi.createTime>= :startTime"), 
		@Condition("gsi.createTime<= :endTime"),
		@Condition("gsi.state.id = :state")
	})
	@OrderBys({
    	@OrderBy("gsi.createTime desc"),
    	@OrderBy("gsi.createTime asc")
	})
	List<GroupShoppingInfo> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination, @Order Short sort);

	@Get
	GroupShoppingInfo get(Long id);

	@Save
	void save(GroupShoppingInfo groupShoppingInfo);

	@Update
	void update(GroupShoppingInfo groupShoppingInfo);

	@Delete
	void delete(GroupShoppingInfo groupShoppingInfo);
}
