/*
 * @(#)DateDefinition.java
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
 * @author  liuan
 * @version 1.0,2011-12-3
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DateDefinition {
	
	/**
	 * 类型
	 * @return
	 */
	DateType type() default DateType.DEFAULT;

	/**
	 * 格式
	 * @return
	 */
	String format() default "yyyy-MM-dd";

	/**
	 * 参数名
	 * @return
	 */
	String target();
}
