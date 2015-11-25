package com.winxuan.ec.util;

import javax.servlet.http.HttpServletRequest;

import com.winxuan.framework.util.security.MD5NORMAL;


/**
 * ****************************** 
 * @author:cast911
 * @lastupdateTime:2013-4-9 下午1:59:10  --!
 * 
 ********************************
 */
public class AccountCacheKeyStrategy {
	
	
	/**
	 * 错误次数达到5次锁定账号
	 */
	public static final Integer DEFAULT_LOCK_LIMIT = 5;
	
	private static final String LOGIN_LIMIT_PRIFIX_NAME_OR_PASS_ERROR = "passport_login_error_times";
	private static final String LOGIN_LIMIT_PRIFIX_IP = "passport_login_ip_limit";
	private static final String LOGIN_LIMIT_ERROR_LOCK = "login_limit_error_lock";
	private static final String LOGIN_LIMIT_ERROR_LOCK_GROUP = "login_limit_error_lock_group";

	
	/**
	 * 用户账号锁定缓存用的key
	 * @param username
	 * @return
	 */
	public static  String getUserAccountLockCacheKey(String username){
		return MD5NORMAL.getMD5(LOGIN_LIMIT_ERROR_LOCK+username.trim());
	}
	
	
	/**
	 * 缓存分组的key,用于存贮一组的对象
	 * @return
	 */
	public static String getUserLockGroupCacheKey(){
		return MD5NORMAL.getMD5(LOGIN_LIMIT_ERROR_LOCK_GROUP);
	}
	
	
	/**
	 * 当同一用户名一小时内输错一次密码，则要求用户填写验证码；使用的key
	 * @param username
	 * @return
	 */
	public static  String getLoginErrorLimitKey(String username){
		return MD5NORMAL.getMD5(LOGIN_LIMIT_PRIFIX_NAME_OR_PASS_ERROR + username.trim());
	}
	/**
	 * 当同一ip某5分钟内登录超过3次，则要求用户填写验证码;使用的key
	 * @param httpServletRequest
	 * @return
	 */
	public  static String getLoginIPLimitKey(HttpServletRequest httpServletRequest){
		String ip = httpServletRequest.getRemoteAddr();
		//LOG.info("x-forwarded-for : " + httpServletRequest.getHeader("x-forwarded-for") + " RemoteAddress : " + ip);
		return MD5NORMAL.getMD5(LOGIN_LIMIT_PRIFIX_IP + ip);
	}

}
