package com.winxuan.ec.support.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * ****************************** 
 * @author:cast911非web环境下获取上下文
 * @lastupdateTime:2013-6-4 下午4:53:54  --!
 * 
 ********************************
 */

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ApplicationContextUtils.applicationContext = applicationContext;
	}

    /**
     * 非web环境下获取Spring上下文.
     * @return
     */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
	public static Object getBean(String beanName){
		return ApplicationContextUtils.getApplicationContext().getBean(beanName);
	}
	
	
	public static <T> T getBean(Class<T> classz){
		return ApplicationContextUtils.getApplicationContext().getBean(classz);
	}
	
	
	

}
