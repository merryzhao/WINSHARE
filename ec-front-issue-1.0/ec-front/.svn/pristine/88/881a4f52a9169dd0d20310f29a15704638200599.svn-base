/*
 * @(#)CacheServiceImpl.java
 *
 */

package com.winxuan.ec.front.service.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.framework.cache.CacheManager;

/**
 * description
 * @author  huangyixiang
 * @version 2012-11-22
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService{
	
	public static final int PAY_PASSWORD_ERROR_TIMES_LIMIT = 5;
	public static final String ACCOUNT_FROZEN_MESSAGE = "您已经连续输入错误密码"+ CacheServiceImpl.PAY_PASSWORD_ERROR_TIMES_LIMIT +"次，系统将暂时冻结您的暂存款";
	
	private static final Log LOG = LogFactory.getLog(CacheServiceImpl.class);
	private static final String PAY_PASSWORD_TIMES_KEY = "www_pay_password_times_key";
	private static final int PAY_PASSWORD_ERROR_IDLE_SECONDS = 3600 * 12;
	
	
	
	@Autowired
	private CacheManager cacheManager;
	
	
	public int getExsitPaypasswordErrTimes(String customerId){
		String key = getPayPasswordTimesKey(customerId);
		Integer exsitTimes = (Integer)cacheManager.get(key);
		exsitTimes = (exsitTimes == null) ? 0 : exsitTimes;
		return exsitTimes;
	}
	
	public boolean payPasswordErrLimit(String customerId){
		String key = getPayPasswordTimesKey(customerId);
		Integer exsitTimes = (Integer)cacheManager.get(key);
		exsitTimes = (exsitTimes == null) ? 0 : exsitTimes;
		
		if(exsitTimes >= PAY_PASSWORD_ERROR_TIMES_LIMIT){
			return true;
		}
		return false;
	}
	
	public void updateCache(String customerId, boolean isPasswordRight){
		String key = getPayPasswordTimesKey(customerId);
		Integer exsitTimes = (Integer)cacheManager.get(key);
		exsitTimes = (exsitTimes == null) ? 0 : exsitTimes;
		
		if(exsitTimes >= PAY_PASSWORD_ERROR_TIMES_LIMIT){
			return;
		}
		
		if(isPasswordRight){
			try {
				cacheManager.remove(key);
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}
		else{
			if(exsitTimes < PAY_PASSWORD_ERROR_TIMES_LIMIT){
				cacheManager.put(key, ++exsitTimes, PAY_PASSWORD_ERROR_IDLE_SECONDS);
			}
		}
		
	}
	
	public String getPayPasswordTimesKey(String customerId){
		return PAY_PASSWORD_TIMES_KEY + customerId;
	}

	@Override
	public int getRestPaypasswordErrTimes(String customerId) {
		int exsitTimes = getExsitPaypasswordErrTimes(customerId);
		int restTimes = CacheServiceImpl.PAY_PASSWORD_ERROR_TIMES_LIMIT - exsitTimes;
		return restTimes;
	}

}
