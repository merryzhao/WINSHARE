/*
 * @(#)Reflect.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * description 本类封装常用反射操作
 * 
 * @author huangyixiang
 * @version 2011-6-30
 */
public class ReflectOperation {

	/**
	 * 反射生成类实例，需无参构造函数
	 * 
	 * @param classType
	 * @return 实例
	 * @throws Exception
	 */
	public static Object getInstance(Class<?> classType) throws Exception {
		return classType.getConstructor(new Class[] {}).newInstance(
				new Object[] {});
	}

	/**
	 * 反射生成实例类,构造参数 填充值
	 * 
	 * @param className
	 *            类
	 * @param constructorType
	 *            构造方法的参数类型.e:String.class
	 * @param constructorPara
	 *            构造方法的参数值.e:String s = "str";
	 * @return 实例
	 * @throws Exception
	 */
	public static Object getInstance(Class<?> classType,
			Class<?> constructorType, Object constructorPara) throws Exception {
		Constructor<?> con = classType.getConstructor(constructorType);
		return con.newInstance(constructorPara);
		
	}

	/**
	 * 给对象成员变量赋值
	 * 
	 * @param object
	 *            被赋值对象
	 * @param field
	 *            成员变量名
	 * @param value
	 *            值
	 * @throws Exception
	 */
	public static void setField(Object object, String field, Object value)
			throws Exception {
		Field fieldObj = object.getClass().getDeclaredField(field);
		fieldObj.setAccessible(true);
		fieldObj.set(object, value);
	}

	/**
	 * 取得成员变量
	 * 
	 * @param object
	 * @param field
	 * @return
	 * @throws Exception
	 */
	public static Object getField(Object object, String field) throws Exception {
		Field fieldObj = object.getClass().getDeclaredField(field);
		fieldObj.setAccessible(true);
		return fieldObj.get(object);
	}

	/**
	 * 调用对象的方法 ，注意：方法参数不能为基本类型，需转换成包装类型，否则抛出异常,因为基本类型传进来时自动转换为包装类型了
	 * 
	 * @param object
	 *            被调用对象
	 * @param methodName
	 *            方法名
	 * @param parameters
	 *            参数
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Object object, String methodName,
			Object... parameters) throws Exception {
		Class<?>[] parameterTypes = new Class[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			parameterTypes[i] = parameters[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName,
				parameterTypes);
		method.setAccessible(true);
		return method.invoke(object, parameters);
	}
}
