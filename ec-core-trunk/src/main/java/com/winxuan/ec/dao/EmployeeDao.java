/*
 * @(#)EmployeeDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 后台用户持久
 * @author  HideHai
 * @version 1.0,2011-8-3
 */
public interface EmployeeDao {

	@Get
	Employee get(Long id);

	@Save
	void save(Employee employee);

	@Update
	void update(Employee employee);

	@Query("from Employee e WHERE e.source.id = 40002 AND e.name = ?")
	Employee getByName(String name);

	@Query("from Employee e where e.name=? and e.password=?")
	Employee getByNameAndPassword(String name,String password);

	@Query("from Employee e where e.name=?")
	boolean nameIsExisted(String name);

	@Query("from Employee e WHERE e.source.id = 40002 AND e.email = ?")
	Employee getByEmail(String email);

	@Query("select distinct e from Employee e left join e.resourceGroups rg where e.source.id = 40002")
	@Conditions({ @Condition("rg.id = :groupId")})
	List<Employee> queryEmployees(@Page Pagination pagination, @Parameter("groupId") Long groupId);

	@Query("select distinct e from Employee e left join e.resourceGroups rg where e.source.id = 40002")
	@Conditions({ @Condition("e.name = :userName")})
	List<Employee> queryEmployeesByquery(@Page Pagination pagination, @Parameter("userName") String userName);
	
	@Query("select distinct e.name from Employee e left join e.resourceGroups rg where e.source.id = 40002")
	@Conditions({ @Condition("e.name like :userName")})
	List<String> queryByName(@Parameter("userName") String userName);

	@Merge
	void merge(Employee employee);
}

