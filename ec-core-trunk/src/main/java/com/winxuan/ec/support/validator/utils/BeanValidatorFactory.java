/*
 * @(#)BeanValidatorFactory.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.validator.utils;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 获取Hibernate验证对象
 * @author  HideHai
 * @version 1.0,2011-8-17
 */
public class BeanValidatorFactory {

	private static Validator  validator = null;

	public BeanValidatorFactory() {}

	public static Validator getValidator(){
		if(validator == null){
			validator = Validation.buildDefaultValidatorFactory().getValidator();			
		}
		return validator;
	}
}

