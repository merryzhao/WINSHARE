/*
 * @(#)ShoppingcartResolver.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;

import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.support.web.resolver.model.Track;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-12-6
 */
public class ShoppingcartResolver extends TrackResolver {

	@Autowired
	private ShoppingcartResolverService shoppingcartResolverService;

	@Override
	protected boolean isApplicable(MethodParameter methodParameter) {
		return methodParameter.getParameterType() == Shoppingcart.class;
	}

	@Override
	protected Object resolve(HttpServletRequest request,
			HttpServletResponse response, MethodParameter methodParameter)
			throws Exception {
		Track track = (Track) super.resolve(request, response, methodParameter);
		return shoppingcartResolverService.resolve(track.getPersistenceId(),response);
	}

}
