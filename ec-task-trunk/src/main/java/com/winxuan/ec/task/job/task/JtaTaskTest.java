/*
 * @(#)JtaTaskTest.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.task;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-8-30
 */
@Component("jtaTaskTest")
public class JtaTaskTest implements Serializable,TaskAware,TaskConfigure{


	private static final long serialVersionUID = 2826840264414600166L;
	private static final Log LOG = LogFactory.getLog(JtaTaskTest.class);

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private NotifyService notifyService;
	
	private static int i =0;


	public void start(){
		Employee employee = employeeService.getByName("hidehai");
		if(employee !=null){
			LOG.info("last login time: "+employee.getLastLoginTime());
		}else{
			LOG.info("Not Found Employee hidehai ");
		}
		LOG.info(i++);
		if(i % 2 == 0){
			doNotify("xxx");
		}
	}

	@Override
	public String getName() {
		return "jtaTaskTest";
	}

	@Override
	public String getDescription() {
		return "测试 任务";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public int getNotifyLevel() {
		return TaskConfigure.LEVEL_ALL;
	}

	@Override
	public void doNotify(String... msg) {
		notifyService.notify(this, "Not Found Employee root.hide");
	}

	


}

