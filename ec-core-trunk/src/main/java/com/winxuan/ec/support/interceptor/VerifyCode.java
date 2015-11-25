/*
 * @(#)VerifyCode.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description
 * 验证码注解
 * 视图中可以取得
 * verifyCodeErr = 1 验证码错误
 * returnUrl 成功后的returnUrl
 * @author  huangyixiang
 * @version 2011-12-8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface VerifyCode {
	
	/**
	 * 验证失败  返回的视图
	 * @return
	 */
	String view();
}
