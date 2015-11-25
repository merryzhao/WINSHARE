package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.user.UserLockState;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * ******************************
 * 
 * @author:cast911
 * @lastupdateTime:2013-4-18 下午3:08:23 --!
 * 
 ******************************** 
 */
public interface UserLockStateDao {

	@Save
	void save(UserLockState userLockState);

	@Update
	void update(UserLockState userLockState);
	
	@SaveOrUpdate
	void saveOrUpdate(UserLockState userLockState);
	
	@Delete
	void delelte(UserLockState userLockState);
	
	
	
	

	@Query("from UserLockState lockLog ")
	@Conditions({ @Condition("lockLog.user = :user"),
			@Condition("lockLog.user.id = :userId"),
			@Condition("lockLog.isLock = :isLock") })
	List<UserLockState> find(@ParameterMap Map<String, Object> parameters);
	
	@Query("from UserLockState lockLog ")
	@Conditions({ @Condition("lockLog.user = :user"),
			@Condition("lockLog.user.id = :userId"),
			@Condition("lockLog.isLock = :isLock") })
	long countFind(@ParameterMap Map<String, Object> parameters);

}
