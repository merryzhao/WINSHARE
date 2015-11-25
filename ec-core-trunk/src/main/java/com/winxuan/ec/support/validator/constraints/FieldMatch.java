/*
 * @(#)FieldMatch.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.winxuan.ec.support.validator.constraints.impl.FieldMatchValidator;

/**
 * 参数匹配验证
 * @author  HideHai
 * @version 1.0,2011-8-4
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
@Documented
public @interface FieldMatch {

	String message() default "{com.winxuan.ec.validator.constraints.notequals.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 结果对应的属性名称
	 * @return
	 */
	String name() ;
	/**
	 * @return 要比较的字段1
	 */
	String fieldName();

	/**
	 * @return 要比较的字段2
	 */
	String verifyName();

	/**
	 * Defines several <code>@FieldMatch</code> annotations on the same element
	 *
	 * @see FieldMatch
	 */
	@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List
	{
		FieldMatch[] value();
	}
}

