package com.winxuan.ec.service.removecache;

import com.winxuan.framework.cache.CacheManager;
import com.winxuan.framework.cache.util.CacheKeyUtils;

/**
 * 缓存删除
 * @author sunflower
 *
 */
public class RemoveCacheServiceImpl implements RemoveCacheService{

	private CacheManager cacheManager;
	private boolean ignoreParams;
	private boolean sortParams;
	private boolean ignoreEmptyParams;
	private String[] excludeParams;
	
	@Override
	public boolean removeCacheByCacheUrl(String url) throws Exception {
		return cacheManager.remove(CacheKeyUtils.getPageCacheKeyByUrl(url,ignoreParams,sortParams,ignoreEmptyParams,excludeParams));
	}

	@Override
	public boolean removeCacheByCacheKey(String key) throws Exception {

		return cacheManager.remove(key);
	}
	
	@Override
	public boolean flushAll() {
		return cacheManager.clear();
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public boolean isIgnoreParams() {
		return ignoreParams;
	}

	public void setIgnoreParams(boolean ignoreParams) {
		this.ignoreParams = ignoreParams;
	}

	public boolean isSortParams() {
		return sortParams;
	}

	public void setSortParams(boolean sortParams) {
		this.sortParams = sortParams;
	}

	public boolean isIgnoreEmptyParams() {
		return ignoreEmptyParams;
	}

	public void setIgnoreEmptyParams(boolean ignoreEmptyParams) {
		this.ignoreEmptyParams = ignoreEmptyParams;
	}

	public String[] getExcludeParams() {
		return excludeParams;
	}

	public void setExcludeParams(String[] excludeParams) {
		this.excludeParams = excludeParams;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	
	
}
