/*
 * @(#)VerifyCodeService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.verifycode;

/**
 * description
 * @author  huangyixiang
 * @version 2011-8-17
 */
public interface VerifyCodeService {
	
	/**
	 * 验证码验证
	 * @param sRand  用户输入的验证码值
	 * @param verifyNumber  Cookie中加密后的值
	 * @return 验证成功返回true 否则返回false
	 */
	boolean verify(String sRand , String verifyNumber);
	
	
	/**
	 * 生成验证码cookie
	 * @param sRand
	 */
	void generateVerifyCodeCookie(String sRand);

}
