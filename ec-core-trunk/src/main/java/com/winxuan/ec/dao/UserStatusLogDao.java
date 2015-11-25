/*
 * @(#)UserStatusLogDao.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.user.User;
import com.winxuan.ec.model.user.UserStatusLog;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author  HideHai
 * @version 1.0,2012-1-31
 */
public interface UserStatusLogDao {

	@Save
	void save(UserStatusLog userStatusLog);
	
	@Query("FROM UserStatusLog l WHERE l.user = ? ORDER BY l.updateTime desc")
	List<UserStatusLog> find(User user , @MaxResults int size);
}

