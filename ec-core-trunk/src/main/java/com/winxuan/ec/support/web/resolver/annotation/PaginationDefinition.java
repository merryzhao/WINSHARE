/*
 * @(#)PaginationDefinetion.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.resolver.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-12-6
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PaginationDefinition {

	int MAX_SIZE = 50;
	
	int DEFAULT_SIZE = 20;

	/**
	 * 每页显示数量的参数名
	 * 
	 * @return
	 */
	String pageSizeTarget() default "size";

	/**
	 * 当前页的参数名
	 * 
	 * @return
	 */
	String currentPageTarget() default "page";

	/**
	 * 每页显示的最大数量
	 * 
	 * @return
	 */
	int maxSize() default MAX_SIZE;

	/**
	 * 最大页数
	 * 
	 * @return
	 */
	int maxPage() default 0;

	/**
	 * 每页显示数量
	 * @return
	 */
	int size() default DEFAULT_SIZE;
}
