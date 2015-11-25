/*
 * @(#)EmployeeService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.employee;

import java.util.List;

import com.winxuan.ec.model.report.Grid;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.validator.AuthenticationProvider;

/**
 * 后台用户逻辑
 * 
 * @author HideHai
 * @version 1.0,2011-8-3
 */
public interface EmployeeService extends AuthenticationProvider {

	/**
	 * 通过编号获取后台用户
	 * 
	 * @param id
	 *            后台用户编号
	 * @return
	 */
	Employee get(Long id);

	/**
	 * 通过名称获取后台用户
	 * 
	 * @param name
	 *            用户名称
	 * @return
	 */
	Employee getByName(String name);
	
	/**
	 * 通过邮箱获取后台用户
	 * @param name
	 * @return
	 */
	Employee getByEmail(String email);

	/**
	 * 更新用户
	 * 
	 * @param employee
	 * @return
	 */
	void update(Employee employee);

	/**
	 * 根据用户名和密码登录
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	Employee login(String name, String password);

	/**
	 * 检查用户名是否存在
	 * 
	 * @param name
	 * @return
	 */
	boolean nameIdExisted(String name);

	/**
	 * 添加收藏报表
	 * 
	 * @param grid
	 */
	void addFavoriteGrid(Employee employee, Grid grid);

	/**
	 * 移除收藏的报表
	 * 
	 * @param grid
	 */
	void removeFavoriteGrid(Employee employee, Grid grid);

	/**
	 * 增加雇员
	 * @param employee
	 */
	void addEmployee(Employee employee);

	
	/**
	 * 查询资源组拥有的雇员
	 * @param pagination
	 * @param groupId
	 * @return
	 */
	List<Employee> queryEmployees(Pagination pagination, Long groupId);

	/**
	 * 设置权限资源关系
	 * @param employee
	 */
	void merge(Employee employee);

	/**
	 * 启用禁用雇员
	 * @param employee
	 * @return 
	 */
	Employee enableOrDisable(Employee employee);	
	
	/**
	 * 查询
	 * @param pagination
	 * @param userName
	 * @return 
	 */
	List<Employee> queryEmployeesByquery(Pagination pagination, String userName);

	/**
	 * 按照名字进行查询
	 * @param userName
	 * @return 
	 */
	List<String> queryByName(String userName);
}
