package com.winxuan.ec.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.CategoryException;

/**
 * 
 * @author HideHai
 * @version 0.1 ,2012-5-30
 **/
@Component
public class BussinessExceptionInterceptor implements HandlerExceptionResolver{
	
	 private Log log = LogFactory.getLog(BussinessExceptionInterceptor.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		log.error(ex.getMessage(),ex);
		if(ex instanceof CategoryException){
			modelAndView.setViewName("/category/result");
			modelAndView.addObject("result", "0");
			modelAndView.addObject("message", ex.getMessage());
			return modelAndView;
		}
		return null;
	}

}

