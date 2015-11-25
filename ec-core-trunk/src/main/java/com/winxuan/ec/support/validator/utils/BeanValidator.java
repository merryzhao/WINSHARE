/*
 * @(#)BeanValidator.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.validator.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import javax.validation.Path.Node;
import javax.validation.groups.Default;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.winxuan.ec.support.validator.constraints.FieldMatch;


/**
 * Bean验证工具类
 * @author  HideHai
 * @version 1.0,2011-8-17
 */
public class BeanValidator {
	
	public static boolean isValid(Errors result , Object object,Class<?>...classes ){
		if(classes == null || classes.length == 0  || classes[0]==null){
			classes = new Class<?>[]{Default.class};
		}
		Validator  validator = BeanValidatorFactory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(object, classes);
		for(ConstraintViolation<Object> violation : violations){
			Path path = violation.getPropertyPath();
			String propertyName = "";
			if(path != null){
				String dot = "";
				for(Node node : path){
					propertyName += node.getName() + dot;
					dot = ".";
				}
			}
			String constraintName = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
			if(propertyName == null || StringUtils.isEmpty(propertyName) || "null".equals(propertyName)){
				Object annotationObj = violation.getConstraintDescriptor().getAnnotation().annotationType();
				if(annotationObj != null && annotationObj.equals(FieldMatch.class)){
					FieldMatch fieldMatch = (FieldMatch) violation.getConstraintDescriptor().getAnnotation();
					result.rejectValue(fieldMatch.name(),constraintName , violation.getMessage());
				}else{
					result.reject(constraintName,violation.getMessage());					
				}
			}else{
				result.rejectValue(propertyName,constraintName , violation.getMessage());
			}
		}
//		result.rejectValue("name","NameCompare","xxx");自定义错误信息
		return violations.size()==0;
	}
}

