/*
 * @(#)BankConfig.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.bank;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

import com.winxuan.ec.util.ReflectOperation;


/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-7-26
 */
public class BankConfig {

	private static final Log LOG = LogFactory.getLog(BankConfig.class);
	
	private static final Properties PROPERTIES = new Properties();
	
	private static final Map<Long, Bank> BANK_BEAN_MAP = new HashMap<Long, Bank>();
	
	private static final Map<Class<?>, Long> BANK_CLASS_MAP = new HashMap<Class<?>, Long>();
	
	private static final String BANK_MARK = "bank_";
	
	static{
		try {
			PROPERTIES.load(new ClassPathResource("bank.properties").getInputStream());
			initBankClassMap();
			LOG.info("init bank class map success, size:" + BANK_CLASS_MAP.size());
			initBankBeanMap();
			LOG.info("init bank bean map success, size:" + BANK_BEAN_MAP.size());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		
	}
	
	
	public static Bank getBank(Long id) {
		return BANK_BEAN_MAP.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSimpleBank(Class<T> cls){
		Long id = BANK_CLASS_MAP.get(cls);
		if(id != null){
			return (T)BANK_BEAN_MAP.get(id);
		}
		return null;
	}
	
	private static void initBankClassMap() throws Exception{
		for (Object obj : PROPERTIES.keySet()) {
			String str = (String) obj;
			if(str.matches(BANK_MARK +"\\d+$")){
				try {
					BANK_CLASS_MAP.put(Class.forName(PROPERTIES.getProperty(str)),
							new Long(StringUtils.removeStart(str, BANK_MARK)));
				} catch (Exception e) {
					LOG.error("init class failed : " + PROPERTIES.getProperty(str) + e.getMessage() , e);
				}
				
			}
		}
	}
	
	private static void initBankBeanMap() throws Exception{
		for(Class<?> cls : BANK_CLASS_MAP.keySet()){
			Constructor<?> ct = cls.getConstructor();
			Object bean = ct.newInstance();
			Stack<Class<?>> stack = findSuperclassList((Bank)bean);
			while(!stack.empty()){
				String normalPrefix = StringUtils.uncapitalize(stack.pop().getSimpleName()) + ".";
				for (Object obj : PROPERTIES.keySet()) {
					String str = (String) obj;
					if (str.indexOf(normalPrefix) != -1) {
						String filed = str.substring(normalPrefix.length());
						Object value = PROPERTIES.get(str);
						if("refunder".equals(filed)){
							value = ReflectOperation.getInstance(Class.forName((String)value));
							BeanUtils.setProperty(value, "bank", bean);
						}
						BeanUtils.setProperty(bean, filed, value);
					}
				}
			}
			Bank bank = (Bank)bean;
			Long id = BANK_CLASS_MAP.get(cls);
			bank.setId(id);
			bank.initCallBack();
			BANK_BEAN_MAP.put(id, bank);
		}
	}
	
	
	private static Stack<Class<?>> findSuperclassList(Bank bank) {
		Stack<Class<?>> list = new Stack<Class<?>>();
		Class<?> cls = bank.getClass();
		while (!Object.class.equals(cls)) {
			list.push(cls);
			cls = cls.getSuperclass();
		}
		return list;
	}
	
}
