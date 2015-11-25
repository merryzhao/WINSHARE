/*
 * @(#)TestServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.ec.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.task.dao.quartz.TriggerDao;
import com.winxuan.ec.task.model.quartz.TaskTrigger;
import com.winxuan.ec.task.service.ec.CustomerService;
import com.winxuan.ec.task.service.ec.TestService;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-8-25
 */
@Service("testService")
public class TestServiceImpl implements TestService ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4241476899711830693L;
	private TriggerDao quartzTriggerDao;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CustomerService customerService2;

	public void testDao() {
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(1);
		pagination.setPageSize(20);
		List<TaskTrigger> triggers = quartzTriggerDao.find(pagination);
		System.out.println(triggers.size());
	}

	@Override
	public void testEmployee() {
		customerService2.processCustomerGrade();
		System.out.println("employeeService:");
		Employee employee =  employeeService.getByName("root.hide");
		if(employee != null){
			System.out.println(employee.getName() +" - "+ employee.getEmail());
			employee.setEmail("hideh4iii@gmail.com");
			employeeService.update(employee);
		}else{
			System.out.println("没有找到雇员!");
		}
	}


}

