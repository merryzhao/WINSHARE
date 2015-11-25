/*
 * @(#)AbstractArgumentResovler.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.interceptor;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.pagination.PaginationParser;
import com.winxuan.framework.validator.IdentityValidator;
import com.winxuan.framework.validator.Principal;

/**
 * 参数解析器
 * @author  HideHai
 * @version 1.0,2011-11-11
 */
public abstract class AbstractArgumentResovler implements WebArgumentResolver{

	private static final Logger LOG = LoggerFactory.getLogger(AbstractArgumentResovler.class);

	/**
	 * 负载均衡设备IP
	 */
	@Value("${front.f5ip}")
	protected String loadBalanceIp;

	@Autowired
	protected IdentityValidator identityValidator;

	@Autowired
	protected ShoppingcartService shoppingcartService;

	public Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception{
		long s = System.currentTimeMillis();
		HttpServletResponse response = (HttpServletResponse) webRequest
		.getNativeResponse();
		HttpServletRequest request = (HttpServletRequest) webRequest
		.getNativeRequest();
		Object object = methodParameter.getParameterType();
		Object result = UNRESOLVED;
		MyInject annotations = methodParameter.getParameterAnnotation(MyInject.class);
		if (annotations != null) {
			InjectType type = annotations.type();
			InjectName value = annotations.value();
			if (type == InjectType.TYPLE) {
				if (object.equals(Pagination.class)) {
					result = getCurrentPagination(request, response);
				} else{
					result= limitResolver(object,type,value,webRequest);
				}
			}else if(type == InjectType.NAME){
				if(value.equals(InjectName.RequestIP)){
					result =getClientAddress(request);
				}
			}
			LOG.info(object + " resolver time : "+ (System.currentTimeMillis() - s));
		}
		return result;
	}

	protected abstract Object limitResolver(Object object,InjectType type,InjectName value,NativeWebRequest webRequest);

	private Pagination getCurrentPagination(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		PaginationParser paginationParser = new PaginationParser(request,response);
		Pagination pagination = paginationParser.parse();
		return pagination;
	}

	/**
	 *  获取客户端IP
	 * @param request
	 * @return
	 */
	private String getClientAddress(HttpServletRequest request){
		LOG.info("load blance ip : "+loadBalanceIp);
		String customerIp = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotBlank(customerIp)) {
			String[] array = customerIp.split(",");
			customerIp = array[0];
		} else {
			customerIp = request.getRemoteAddr();
		}
		if (loadBalanceIp != null && loadBalanceIp.equals(customerIp)) {
			customerIp = request.getHeader("X-Real-IP");
		}
		return customerIp;
	}
	
	protected Principal getCurrentPrincipal() {
		return identityValidator.currentPrincipal();
	}
}

