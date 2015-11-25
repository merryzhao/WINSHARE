/*
 * @(#)AttachDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.employee.EmployeeAttach;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
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
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-9-8
 */
public interface AttachDao {

	@Get
	EmployeeAttach get(Long id);

	@Save
	void save(EmployeeAttach employeeAttach);

	@Delete
	void delete(EmployeeAttach employeeAttach);

	@Query("from EmployeeAttach e")
	@Conditions({ @Condition("e.employee.id=:id") })
	@OrderBys({ @OrderBy("e.id desc") })
	List<EmployeeAttach> find(@ParameterMap Map<String, Object> parameters,
			@Order Short sort, @Page Pagination pagination);
}
