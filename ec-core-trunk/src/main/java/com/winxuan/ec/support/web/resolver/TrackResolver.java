/*
 * @(#)TrackResolver.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;

import com.winxuan.ec.support.web.resolver.model.Track;
import com.winxuan.framework.util.RandomCodeUtils;
import com.winxuan.framework.util.web.CookieUtils;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-12-6
 */
public class TrackResolver extends AbstractWebArgumentResolver {

	@Override
	protected boolean isApplicable(MethodParameter methodParameter) {
		return methodParameter.getParameterType() == Track.class;
	}

	@Override
	protected Object resolve(HttpServletRequest request,
			HttpServletResponse response, MethodParameter methodParameter)
			throws Exception {
		Cookie sessionCookie = getOrCreateCookie(request, response,
				Track.SESSION_COOKIE_NAME);
		Cookie persistenceCookie = getOrCreateCookie(request, response,
				Track.PERSISTENCE_COOKIE_NAME);
		Track track = new Track();
		track.setSessionId(sessionCookie.getValue());
		track.setPersistenceId(persistenceCookie.getValue());
		return track;
	}

	private Cookie getOrCreateCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		Cookie cookie = CookieUtils.getCookie(request, name);
		String value = cookie == null ? null : cookie.getValue();
		if (value == null || value.length() != Track.COOKIE_VALUE_LENGTH
				|| !RandomCodeUtils.verify(value, Track.COOKIE_VALUE_MODE)) {
			cookie = new Cookie(name, RandomCodeUtils.create(
					Track.COOKIE_VALUE_MODE, Track.COOKIE_VALUE_LENGTH, true));
			cookie.setDomain(Track.COOKIE_DOMAIN);
			cookie.setPath(Track.COOKIE_PATH);
			if (name.equals(Track.PERSISTENCE_COOKIE_NAME)) {
				cookie.setMaxAge(Track.PERSISTENCE_COOKIE_AGE);
			}
			CookieUtils.writeCookie(response, cookie);
		}
		return cookie;
	}

}
