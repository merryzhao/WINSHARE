/*
 * @(#)BaseTests.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.winxuan.ec.service.order.OrderBeans;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-11-17
 */
@ContextConfiguration({ "classpath:applicationContext.xml" })
public abstract class BaseTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	protected Services services;
	@Autowired
	protected OrderBeans orderBeans;

	@Autowired
    private SessionFactory sessionFactory;
	
	protected void flush() throws BeansException {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.flush();
    }
	
}
