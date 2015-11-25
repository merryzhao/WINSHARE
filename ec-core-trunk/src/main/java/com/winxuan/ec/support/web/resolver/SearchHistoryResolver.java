/*
 * @(#)HistoryResolver.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;

import com.winxuan.ec.support.web.resolver.model.SearchHistoryTrack;
import com.winxuan.ec.support.web.resolver.model.Track;

/**
 * description
 * @author  huangyixiang
 * @version 2011-12-7
 */
public class SearchHistoryResolver extends TrackResolver{

	@Override
	protected boolean isApplicable(MethodParameter methodParameter) {
		return methodParameter.getParameterType() == SearchHistoryTrack.class;
	}

	@Override
	protected Object resolve(HttpServletRequest request,
			HttpServletResponse response, MethodParameter methodParameter)
			throws Exception {
		Track track = (Track) super.resolve(request, response, methodParameter);
		SearchHistoryTrack searchHistory = new SearchHistoryTrack();
		searchHistory.setClientId(track.getPersistenceId());
		searchHistory.setSessionId(track.getSessionId());
		return searchHistory;
	}

}
