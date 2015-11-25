/*
 * @(#)AbstractWebArgumentResolver.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-12-3
 */
public abstract class AbstractWebArgumentResolver implements
		WebArgumentResolver {

	@Override
	public final Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception {
		if (isApplicable(methodParameter)) {
			return resolve(
					(HttpServletRequest) webRequest.getNativeRequest(),
					(HttpServletResponse) webRequest.getNativeResponse(),
					methodParameter);
		}
		return UNRESOLVED ;
	}

	protected abstract boolean isApplicable(MethodParameter methodParameter);

	protected abstract Object resolve(HttpServletRequest request,
			HttpServletResponse response, MethodParameter methodParameter)
			throws Exception;
}
