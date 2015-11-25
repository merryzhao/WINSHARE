/*
 * @(#)UserResolver.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;

import com.winxuan.framework.validator.IdentityValidator;
import com.winxuan.framework.validator.Principal;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-12-6
 */
public class UserResolver extends AbstractWebArgumentResolver {

	@Autowired
	private IdentityValidator identityValidator;

	@Override
	protected boolean isApplicable(MethodParameter methodParameter) {
		return Principal.class.isAssignableFrom(methodParameter
				.getParameterType());
	}

	@Override
	protected Object resolve(HttpServletRequest request,
			HttpServletResponse response, MethodParameter methodParameter)
			throws Exception {
		return identityValidator.currentPrincipal();
	}

}
