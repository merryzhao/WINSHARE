package com.winxuan.ec.support.web.listenner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.winxuan.framework.loadbalance.Router;

/**
 * DataSource监听器,启动
 * @author Heyadong
 * @version 1.0 , 2011-12-6
 */
public class DataSourceSetupListenner implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent context) {
	
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
		Router router = app.getBean("router",Router.class);
		Log log = LogFactory.getLog(DataSourceSetupListenner.class);
		try {
			router.setup();
			log.info("===启动 router 监控器===");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} 
	}
}
