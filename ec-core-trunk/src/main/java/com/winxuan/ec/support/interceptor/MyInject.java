/*
 * @(#)MyInject.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 参数拦截注解
 * @author  HideHai
 * @version 1.0,2011-7-30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface MyInject {

	 InjectName value() default InjectName.NULL;
	
	 InjectType type() default InjectType.TYPLE;
	
}


