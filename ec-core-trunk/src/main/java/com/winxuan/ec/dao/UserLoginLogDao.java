package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.user.UserLoginLog;
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
 * ******************************
 * 
 * @author:cast911
 * @lastupdateTime:2013-4-18 下午3:08:23 --!
 * 
 ******************************** 
 */
public interface UserLoginLogDao {

	@Save
	void save(UserLoginLog userLoginLog);

	@Get
	UserLoginLog get(Long id);

	@Update
	void update(UserLoginLog userLoginLog);

	@Update
	void updateLoginLog(Long id);

	@Query("from UserLoginLog userLoginLog where userLoginLog.name = ?")
	long countByName(String name);

	@Query("from UserLoginLog userLoginLog where userLoginLog.name = ?")
	List<UserLoginLog> listByName(String name);

	@Query("from UserLoginLog uli")
	@Conditions({ @Condition("uli.name = :name"),
			@Condition("uli.isLogin = :isLogin"),
			@Condition("uli.user = :user"),
			@Condition("uli.user.id = :userId"),
			@Condition("uli.loginTime >= :startTime"),
			@Condition("uli.loginTime <= :endTime") })
	@OrderBys({ @OrderBy("uli.loginTime desc"), @OrderBy("uli.loginTime asc") })
	List<UserLoginLog> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination, @Order short orderIndex);

	@Query("from UserLoginLog uli")
	@Conditions({ @Condition("uli.name = :name"),
			@Condition("uli.isLogin = :isLogin"),
			@Condition("uli.user = :user"),
			@Condition("uli.user.id = :userId"),
			@Condition("uli.loginTime >= :startTime"),
			@Condition("uli.loginTime <= :endTime") })
	long countfind(@ParameterMap Map<String, Object> parameters);
}
