/*
 * @(#)PresentBatchDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.present.PresentLog;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-8-30
 */
public interface PresentLogDao {
	@Get
	PresentLog get(Long id);

	@Save
	void save(PresentLog presentLog);

	@Update
	void update(PresentLog presentLog);

	@Query("from PresentLog p where p.order.id = ?")
	List<PresentLog> findByOrder(String orderId);
}
