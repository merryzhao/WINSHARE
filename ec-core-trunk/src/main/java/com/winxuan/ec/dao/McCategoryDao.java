/*
 * @(#)McCategoryDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.category.McCategory;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.dynamicdao.type.FlushMode;

/**
 * description
 * 
 * @author renshiyong
 * @version 1.0,2011-7-18
 */
public interface McCategoryDao {

	@Get
	McCategory get(String id);

	@Query(value = "select 1 from mccode where mccode = ? and type=1", sqlQuery = true, flushMode=FlushMode.NEVER)
	boolean isExistedFromOffShelf(String id);
	
	@Query(sqlQuery=true,value = "select mccode from mccode where type=1")
	List<String> findAll();
}
