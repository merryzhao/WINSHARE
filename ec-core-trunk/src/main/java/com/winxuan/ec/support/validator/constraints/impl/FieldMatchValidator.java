/*
 * @(#)FieldMatchValidator.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.validator.constraints.impl;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winxuan.ec.support.validator.constraints.FieldMatch;

/**
 * 参数匹配实现
 * @author  HideHai
 * @version 1.0,2011-8-4
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch,Object>{
	
	Logger log = LoggerFactory.getLogger(getClass());

	private String fieldName;

	private String verifyName;

	public void initialize(FieldMatch match) {
		fieldName = match.fieldName();
		verifyName = match.verifyName();
	}

	public boolean isValid(Object object, ConstraintValidatorContext context) {
		try {
			Object fieldNameObj = BeanUtils.getProperty(object,fieldName);
			Object verifyNameObj = BeanUtils.getProperty(object, verifyName);
			return fieldNameObj == null && verifyNameObj == null 
			|| verifyNameObj != null 
			&& fieldNameObj.equals(verifyNameObj);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}

}

