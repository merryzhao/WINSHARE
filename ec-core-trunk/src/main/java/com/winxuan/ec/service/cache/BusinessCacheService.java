package com.winxuan.ec.service.cache;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hidehai
 *
 */
public interface BusinessCacheService {
	
	
	/**
	 * 放入缓存
	 * @param key
	 * @param object
	 */
	public void put(String key,Serializable object);
	
	/**
	 * 放入缓存/超时时间
	 * @param key
	 * @param object
	 * @param date
	 */
	public void put(String key,Serializable object,Date date);
	
	/**
	 * 移除缓存
	 * @param key
	 * @param object
	 */
	public void remove(String key) throws Exception ;
	
	/**
	 * 是否存在
	 * @param key
	 * @return
	 */
	public boolean exist(String key);
	
	public Serializable get(String key);

}
