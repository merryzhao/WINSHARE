package com.winxuan.ec.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Bean工具类
 * @author Heyadong
 *
 */
public class BeansUtil {
	
	/**
	 * 根据传入对象,放入泛型集合返回
	 * @param <T>
	 * @param objs
	 * @return
	 */
	public static <T> List<T> createList(T ... objs){
		List<T> list = new ArrayList<T>();
		if (objs != null && objs.length > 0){
			for (T t : objs){
				list.add(t);
			}
		}
		return list;
	}
	
	
	/**
	 * 根据传入对象,放入泛型集合返回
	 * @param <T>
	 * @param objs
	 * @return
	 */
	public static <T> Set<T> createSet(T ... objs){
		Set<T> set = new HashSet<T>();
		if (objs != null && objs.length > 0){
			for (T t : objs){
				set.add(t);
			}
		}
		return set;
	}
}
