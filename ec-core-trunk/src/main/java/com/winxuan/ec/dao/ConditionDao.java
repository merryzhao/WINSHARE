/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.report.Condition;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;

/**
 * description
 * @author liyang
 * @version 1.0,2011-8-22
 */

public interface ConditionDao {
	@Get
	Condition get(Long id);
	
	@Update
	void update(Condition condition);
	
	@Save
	void save(Condition condition);
	
	@Delete
	void delete(Condition condition);
}
