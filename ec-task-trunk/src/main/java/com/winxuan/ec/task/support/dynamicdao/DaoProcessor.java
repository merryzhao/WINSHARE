/*
 * @(#)DaoProcessor.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.support.dynamicdao;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.framework.dynamicdao.DynamicDaoFactory;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 使用动态DAO的Service属性注入
 * @author  HideHai
 * @version 1.0,2011-8-25
 */
@Component("DaoProcessor")
public class DaoProcessor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8018811682389549179L;
    @Autowired
	private SessionFactory sessionFactory;

	public  Object processService(Object bean, String beanName)
	throws BeansException {
		Field[] fields = bean.getClass().getDeclaredFields();
		if (fields != null) { 
			for (Field field : fields) {
				InjectDao annotation = field.getAnnotation(InjectDao.class);
				if (annotation == null) {
					continue;
				}
				Object dao =  processDao(field.getType());
				field.setAccessible(true);
				try {
					field.set(bean, dao);
				} catch (IllegalArgumentException e) {
					throw new BeanInitializationException(e.getMessage());
				} catch (IllegalAccessException e) {
					throw new BeanInitializationException(e.getMessage());
				}
			}
		}
		return bean;
	}
	
	public Object processDao(Class<?> clazz){
		return DynamicDaoFactory.create(clazz, sessionFactory);
	}
}

