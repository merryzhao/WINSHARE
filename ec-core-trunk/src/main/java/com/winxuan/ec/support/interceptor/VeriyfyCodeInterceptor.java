/*
 * @(#)VeryfyCodeInterceptor.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.service.verifycode.VerifyCodeService;
import com.winxuan.ec.service.verifycode.VerifyCodeServiceImpl;
import com.winxuan.framework.util.web.CookieUtils;
import com.winxuan.framework.util.web.WebContext;

/**
 * description
 * @author  huangyixiang
 * @version 2011-12-8
 */
@Component
@Aspect
public class VeriyfyCodeInterceptor{
	
	public static final String SRAND_TARGET = "verifyCode";
	public static final String ERROR = "verifyCodeErr";
	public static final String RETURN_URL = "returnUrl";
	
	@Autowired
	private VerifyCodeService verifyCodeService;
	
	@Pointcut("execution(public * com.winxuan.ec.*.controller..*(..))")  
    public void pointCut() {}
	
	@Around("pointCut()")  
	public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
		 MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
         Method targetMethod = methodSignature.getMethod();

		VerifyCode verifyCodeAnnotation =  targetMethod.getAnnotation(VerifyCode.class);
		if(verifyCodeAnnotation == null){
			return pjp.proceed();
		}
		String view = verifyCodeAnnotation.view();
		ModelAndView modelAndView = new ModelAndView(view);
		HttpServletRequest request = WebContext.currentRequest();
		Cookie verifyCookie = CookieUtils.getCookie(VerifyCodeServiceImpl.COOKIE_VERIFY_NUMBER);
		String retUrl = null;
		if(verifyCookie != null){
			String verifyNumber = verifyCookie.getValue();
			String sRand = request.getParameter(SRAND_TARGET);
			retUrl = request.getParameter(RETURN_URL);
			
			if(verifyCodeService.verify(sRand, verifyNumber)){
				return pjp.proceed();
			}
		}
		
		modelAndView.addObject(ERROR, 1);
		modelAndView.addObject(RETURN_URL, StringUtils.isBlank(retUrl) ? null : retUrl);
		return modelAndView;
	}



}
