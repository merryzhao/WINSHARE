package com.winxuan.ec.support.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.winxuan.ec.support.exception.ExceptionConsumer;
import com.winxuan.framework.util.web.WebContext;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-23
 */
public class DatabaseAppender extends AppenderSkeleton {
	private static final int MAGIC_ZERO = 0;
	private static final int MAGIC_500 = 500;
	private static ExceptionConsumer exceptionConsumer;

	static{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:exception.xml");
		exceptionConsumer = (ExceptionConsumer)context.getBean("exceptionConsumer");	
	}

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent event) {
		HttpServletRequest request = WebContext.currentRequest();
		if(request != null && event != null){
			com.winxuan.ec.model.exception.ExceptionLog exceptionLog = com.winxuan.ec.support.exception.ExceptionUtil.newExceptionLog(request);
			exceptionLog.setSource(event.getLogger() == null ? "" : event.getLogger().getName());
			Object object = event.getMessage();
			if(object.toString() != null){
				exceptionLog.setMessage(object.toString().length() > MAGIC_500 ? object.toString().substring(MAGIC_ZERO, MAGIC_500) : object.toString());
			}
			exceptionLog.setTime(new Date());
			if(exceptionConsumer != null){
				exceptionConsumer.save(exceptionLog);
			}
		}
	}	
}
