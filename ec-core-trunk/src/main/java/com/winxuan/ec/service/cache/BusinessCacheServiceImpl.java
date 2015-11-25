package com.winxuan.ec.service.cache;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.framework.cache.CacheManager;

/**
 * 
 * 业务数据缓存
 * @author hidehai
 *
 */
@Service("bussinessCacheService")
@Transactional(rollbackFor = Exception.class)
public class BusinessCacheServiceImpl implements BusinessCacheService{

	@Autowired(required=false)
	private CacheManager cacheManager;

	@Override
	public void put(String key, Serializable object) {
		put(key, object, DateUtils.addHours(new Date(), 1));
	}

	@Override
	public void put(String key, Serializable object, Date date) {
		if(object != null && cacheManager != null) {
			cacheManager.put(key, object,date);
		}
	}

	@Override
	public void remove(String key) throws Exception {
		if(cacheManager!=null) {
			cacheManager.remove(key);
		}
	}

	@Override
	public boolean exist(String key) {
		return cacheManager!=null ? 
				cacheManager.get(key) == null ? false : true 
						: false;
	}

	@Override
	public Serializable get(String key) {
		return cacheManager != null ?
				cacheManager.get(key)
				:null;
	}



}
