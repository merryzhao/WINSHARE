/*
 * @(#)CacheService.java
 *
 */

package com.winxuan.ec.front.service.cache;


/**
 * description
 * @author  huangyixiang
 * @version 2012-11-22
 */
public interface CacheService {
	
	boolean payPasswordErrLimit(String customerId);

	void updateCache(String customerId, boolean isPasswordRight);
	
	String getPayPasswordTimesKey(String customerId);
	
	int getExsitPaypasswordErrTimes(String customerId);
	
	int getRestPaypasswordErrTimes(String customerId);
}
