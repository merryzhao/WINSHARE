/*
 * @(#)WebApplicationContextUtils.java
 *
 * Copyright 2008 Xinhua Online, Inc. All rights reserved.
 */

/**
 * 
 */
package com.winxuan.ec.support.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2008-6-6
 */
/**
 * @author liuan
 * 
 */
public class WebApplicationContextUtils {
	public static ApplicationContext getContext(ServletContext sc) {
		return org.springframework.web.context.support.WebApplicationContextUtils
				.getWebApplicationContext(sc);
	}

	public static Object getService(String beanName, ServletContext sc) {
		return getContext(sc).getBean(beanName);
	}
}
