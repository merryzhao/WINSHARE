package com.winxuan.ec.service.removecache;

/**
 * 缓存删除接口类
 * @author sunflower
 *
 */
public interface RemoveCacheService {

	/**
	 * 通过url删除缓存
	 * @param url
	 * @return 
	 */
	boolean removeCacheByCacheUrl(String url) throws Exception;
	
	
	/**
	 * 通过key删除缓存
	 * @param key
	 * @return 
	 * @throws Exception 
	 */
	boolean removeCacheByCacheKey(String key) throws Exception;
	
	/**
	 * 刷新所有缓存
	 * @return
	 */
	boolean flushAll();
}
