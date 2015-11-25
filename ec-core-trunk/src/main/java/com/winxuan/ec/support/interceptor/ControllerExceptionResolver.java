package com.winxuan.ec.support.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.BusinessException;



/**
 * SpringMVC 异常 拦截器
 * @author Heyadong
 *
 */
//@Component
public class ControllerExceptionResolver implements HandlerExceptionResolver{
	
	private static final Logger LOG = Logger.getLogger(ControllerExceptionResolver.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired(required=false)
	private ExceptionLog exceptionLog;
	/**
	 * processCache   K:异常类     V:异常类的处理方法   
	 */
	private Map<Class<? extends Exception>, ExceptionProcessor> processCache;
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if (processCache == null){
			String[] processorNames = applicationContext.getBeanNamesForType(ExceptionProcessor.class);
			processCache = new HashMap<Class<? extends Exception>, ExceptionProcessor>(processorNames.length);
			for (String name : processorNames) {
				ExceptionProcessor processor = applicationContext.getBean(name, ExceptionProcessor.class);
				Object old = processCache.put(processor.target(), processor);
				if (old != null){
					LOG.warn(String.format("%s 与  %s 重复处理了 %s 异常", old.getClass(),processor.getClass(),processor.target()));
				}
			}
		}
		//如果实现了异常日志记录,则记录日志
		if (exceptionLog != null){
			exceptionLog.log(request, response, handler, ex);
		}
		
		ModelAndView mav = null;
		if (ex instanceof BusinessException) {
			
			ExceptionProcessor processor = processCache.get(ex.getClass());
			if (processor == null){
				LOG.warn(String.format("%s异常,没有得到处理", ex));
			} else {
				mav = processor.process(request, response, handler, ex);
			}
		} else {
			LOG.warn(String.format("\n[%s]发现其非BusinessException异常 %s \n  ExceptionMsg: %s ", handler==null?null:handler.getClass() , ex.getClass(),ex.getMessage()));
			ExceptionProcessor processor = processCache.get(Exception.class);
			if (null != processor){
				mav =  processor.process(request, response, handler, ex);
			}
		}
		
		return mav;
	}
	
}
