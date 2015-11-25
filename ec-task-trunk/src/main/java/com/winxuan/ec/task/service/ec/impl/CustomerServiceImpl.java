/*
 * @(#)CustomerServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.ec.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.winxuan.ec.task.dao.quartz.TriggerDao;
import com.winxuan.ec.task.model.quartz.TaskTrigger;
import com.winxuan.ec.task.service.ec.CustomerService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-8-22
 */
@Service("customerService2")
public class CustomerServiceImpl implements CustomerService,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1206222905123315504L;

	@InjectDao
	private TriggerDao triggerDao;
	

//	private QuartzTriggerDao getQuartzTriggerDao(){
//		MethodInvokingDataMap.register();
//		SessionFactory sessionFactory = MethodInvokingDataMap.getSessionFactory();
//		QuartzTriggerDao quartzTriggerDao =(QuartzTriggerDao) DynamicDaoFactory.create(QuartzTriggerDao.class, sessionFactory);
//		return quartzTriggerDao;
//	}


	public void processCustomerGrade() {
		System.out.println("processCustomerGrade");
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(1);
		pagination.setPageSize(20);
		List<TaskTrigger> triggers  = null;
		triggers = triggerDao.find(pagination);
		System.out.println("processCustomerGrade"+triggers == null ? 0 : triggers.size());
		for(TaskTrigger trigger : triggers){
			trigger.setDescription("2011-8-29 10:23:53");
			triggerDao.update(trigger);			
		}
	}


	public void processTest() {
		System.out.println("A"+"B");
		System.out.println("C"+triggerDao);
	}

}

