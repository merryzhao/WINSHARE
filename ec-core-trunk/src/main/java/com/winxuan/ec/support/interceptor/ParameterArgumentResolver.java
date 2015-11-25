/*
 * @(#)FrontArgument.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.interceptor;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
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

import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.Seller;
import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.pagination.PaginationParser;
import com.winxuan.framework.validator.IdentityValidator;
import com.winxuan.framework.validator.Principal;

/**
 * Controller 方法参数解析
 * 
 * @author HideHai
 * @version 1.0,2011-7-27
 */
public class ParameterArgumentResolver implements WebArgumentResolver {

	private static final Logger LOG = LoggerFactory
	.getLogger(ParameterArgumentResolver.class);
	private static final String COOKIE_CLIENT="c";

	/**
	 * 负载均衡设备IP
	 */
	@Value("${front.f5ip}")
	private String loadBalanceIp;

	@Autowired
	private IdentityValidator identityValidator;

	@Autowired
	private ShoppingcartService shoppingcartService;

	public Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception {
		long s = System.currentTimeMillis();
		HttpServletResponse response = (HttpServletResponse) webRequest
		.getNativeResponse();
		HttpServletRequest request = (HttpServletRequest) webRequest
		.getNativeRequest();
		Object object = methodParameter.getParameterType();
		Object result = UNRESOLVED;
		MyInject annotations = methodParameter
		.getParameterAnnotation(MyInject.class);
		if (annotations != null) {
			InjectType type = annotations.type();
			InjectName value = annotations.value();
			if (type == InjectType.TYPLE) {
				if (object.equals(Customer.class)
						|| object.equals(Employee.class)
						|| object.equals(Seller.class)) {
					result = getCurrentPrincipal();
				} else if (object.equals(Pagination.class)) {
					result = getCurrentPagination(request, response);
				} else if (object.equals(Shoppingcart.class)) {
					result = getShoppingcart(request);
				}
			}else if(type == InjectType.NAME){
				if(value.equals(InjectName.RequestIP)){
					result =getClientAddress(request);
				}
			}
			LOG.info(object + " resolver time : "
					+ (System.currentTimeMillis() - s));
		}
		return result;
	}

	/**
	 * 获取当前用户，没有则返回NULL
	 * 
	 * @return
	 */
	private Principal getCurrentPrincipal() {
		return identityValidator.currentPrincipal();
	}

	/**
	 * 通过上下文获取分页对象
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private Pagination getCurrentPagination(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		PaginationParser paginationParser = new PaginationParser(request,response);
		Pagination pagination = paginationParser.parse();
		return pagination;
	}

	private Shoppingcart getShoppingcart(HttpServletRequest request) {
		String client = getClient(request);
		if (!StringUtils.isBlank(client)) {
			return shoppingcartService.get(client);
		}
		return null;
	}

	private String getClient(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (COOKIE_CLIENT.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
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


}
