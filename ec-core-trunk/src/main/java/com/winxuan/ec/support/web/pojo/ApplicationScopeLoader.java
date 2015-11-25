package com.winxuan.ec.support.web.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.WebApplicationObjectSupport;

/**
 * Web Application变量加载
 * 加载指定类路径下的静态变量到web的application
 * @author HideHai
 *
 */
public class ApplicationScopeLoader extends WebApplicationObjectSupport{

	private final Logger log = LoggerFactory.getLogger(ApplicationScopeLoader.class);
	private Map<String, String> loadTargetClass;

	@Override
	protected void initApplicationContext() throws BeansException {
		try {
			loadTarget();
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(),e);
		}
		super.initApplicationContext();
	}

	private void loadTarget() throws ClassNotFoundException{
		for(Map.Entry<String, String> entry : loadTargetClass.entrySet()){
			Map<String, Object> result = new HashMap<String, Object>();
			String key = entry.getKey();
			String classValue = entry.getValue();
			Class<?> clazz = this.getClass().getClassLoader().loadClass(classValue);
			Field[] fields = clazz.getDeclaredFields();
			int len = fields.length;
			int i=0;
			for(;i<len;i++){
				Field field = fields[i];
				int modifier = field.getModifiers();
				if(Modifier.isFinal(modifier) && !Modifier.isPrivate(modifier)){ //Final 以及 非private的静态属性
					try {
						String name = field.getName();
						Object value = field.get(this);
						log.debug(String.format("put constants{%s}: name={%s},value={%s}",key,name,value)); 
						result.put(name, value);
					} catch (IllegalAccessException e) {
						log.error(e.getMessage(),e);
					}
				}
			}
			this.getServletContext().setAttribute(key, result);
		}
	}

	public void setLoadTargetClass(Map<String, String> loadTargetClass) {
		this.loadTargetClass = loadTargetClass;
	}
	
	
}
