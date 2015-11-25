/*
 * @(#)PaginationResolver.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;

import com.winxuan.ec.support.web.resolver.annotation.PaginationDefinition;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.pagination.PaginationParser;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-12-6
 */
public class PaginationResolver extends AbstractWebArgumentResolver {

	@Override
	protected boolean isApplicable(MethodParameter methodParameter) {
		return methodParameter.getParameterType() == Pagination.class;
	}

	@Override
	protected Object resolve(HttpServletRequest request,
			HttpServletResponse response, MethodParameter methodParameter)
			throws Exception {
		PaginationParser paginationParser = new PaginationParser(request,
				response);
		PaginationDefinition definition = methodParameter
				.getParameterAnnotation(PaginationDefinition.class);
		if (definition != null) {
			paginationParser.setPageSizeTarget(definition.pageSizeTarget());
			paginationParser.setCurrentPageTarget(definition
					.currentPageTarget());
		}
		Pagination pagination = paginationParser.parse();
		if (pagination != null && definition != null) {
			int size = definition.size();
			int maxSize = definition.maxSize();
			int maxPage = definition.maxPage();
			if (size > 0) {
				pagination.setPageSize(size);
			}
			if (maxSize > 0 && pagination.getPageSize() > maxSize) {
				pagination.setPageSize(maxSize);
			}
			if (maxPage > 0 && pagination.getCurrentPage() > maxPage) {
				pagination.setCurrentPage(maxPage);
			}
		}
		return pagination;
	}

}
