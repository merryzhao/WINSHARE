/*
 * @(#)AttachService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.employee;


import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.AttachException;
import com.winxuan.ec.model.employee.EmployeeAttach;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-8
 */
public interface AttachService {
	
	EmployeeAttach get(Long id);
	
	void create(EmployeeAttach employeeAttach);

	String addAttach(EmployeeAttach employeeAttach, List<String> head,
			List<List<String>> data) throws AttachException;

	List<EmployeeAttach> find(Map<String, Object> parameters,Short sort,Pagination pagination);

	/**
     * 生成消息
     * @param employee
     * @param name
     * @param head
     * @param data
     */
    void makeAttach(Employee employee, String name, List<String> head, List<List<String>> data);
}

