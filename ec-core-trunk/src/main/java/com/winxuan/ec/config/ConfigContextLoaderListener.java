/*
 * @(#)ConfigContextLoaderListener.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.config;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Min
 * @version 1.0,Nov 23, 2012
 */
public class ConfigContextLoaderListener extends ContextLoaderListener {
    
    private static final Log LOG = LogFactory.getLog(ConfigContextLoaderListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {

		super.contextInitialized(event);
		
		WebApplicationContext webContext = getCurrentWebApplicationContext();
		event.getServletContext().setAttribute("miscConfig",
				webContext.getBean("miscConfig"));
		MiscResources miscResources = webContext.getBean(MiscResources.class);
		LOG.info(miscResources);
	}
}
;