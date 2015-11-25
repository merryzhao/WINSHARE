/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.report.Enumeration;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author liyang
 * @version 1.0,2011-8-22
 */

public interface EnumerationDao {
	@Get
	Enumeration get(Long id);
	
	@Update
	void update(Enumeration enumeration);
	
	@Save
	void save(Enumeration enumeration);
	
	@Query("from Enumeration e")
	@Conditions({
		@Condition("e.available=:available")
	})
	List<Enumeration> find(@ParameterMap Map<String, Object> parameters);
	
	@Delete
	void delete(Enumeration enumeration);
}
