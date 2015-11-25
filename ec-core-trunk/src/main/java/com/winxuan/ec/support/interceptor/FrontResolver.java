/*
 * @(#)FrontResolver.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Seller;
import com.winxuan.framework.util.RandomCodeUtils;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-11-11
 */
public class FrontResolver extends AbstractArgumentResovler {

	private static final String COOKIE_CLIENT = "c";
	private static final int SHOPPINGCART_ID_LENGTH = 8;
	private static final short SHOPPINGCART_ID_MODE = 5;
	private static final int CLIENT_COOKIE_AGE = Integer.MAX_VALUE;
	private static final String CLIENT_COOKIE_PATH = "/";
	private static final String CLIENT_COOKIE_DOMAIN = ".winxuan.com";

	@Override
	protected Object limitResolver(Object object, InjectType type,
			InjectName value, NativeWebRequest webRequest) {
		Object result = UNRESOLVED;
		HttpServletRequest request = (HttpServletRequest) webRequest
				.getNativeRequest();
		HttpServletResponse response = (HttpServletResponse) webRequest
				.getNativeResponse();
		if (object.equals(Customer.class) || object.equals(Seller.class)) {
			result = getCurrentPrincipal();
		} else if (object.equals(Shoppingcart.class)) {
			result = getShoppingcart(request, response);
		}
		return result;
	}

	private Shoppingcart getShoppingcart(HttpServletRequest request,
			HttpServletResponse response) {
		Shoppingcart shoppingcart = null;
		String client = getClient(request);
		if (!StringUtils.isBlank(client)) {
			shoppingcart = shoppingcartService.get(client);
		} else {
			client = RandomCodeUtils.create(SHOPPINGCART_ID_MODE,
					SHOPPINGCART_ID_LENGTH);
			Cookie cookie = new Cookie(COOKIE_CLIENT, client);
			cookie.setMaxAge(CLIENT_COOKIE_AGE);
			cookie.setPath(CLIENT_COOKIE_PATH);
			cookie.setDomain(CLIENT_COOKIE_DOMAIN);
			response.addCookie(cookie);
		}
		if (shoppingcart == null) {
			shoppingcart = new Shoppingcart();
			shoppingcart.setId(client);
			Date now = new Date();
			shoppingcart.setCreateTime(now);
			shoppingcart.setUseTime(now);
		}
		if (shoppingcart.getCustomer() == null) {
			Customer customer = (Customer) getCurrentPrincipal();
			shoppingcart.setCustomer(customer);
		}
		return shoppingcart;
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

}
