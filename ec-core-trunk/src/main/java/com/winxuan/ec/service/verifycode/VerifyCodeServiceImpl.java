/*
 * @(#)VerifyCodeServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.verifycode;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.winxuan.ec.controller.VerifyCodeController;
import com.winxuan.framework.util.security.MD5Utils;
import com.winxuan.framework.util.web.CookieUtils;
import com.winxuan.framework.util.web.WebContext;

/**
 * description
 * 
 * @author huangyixiang
 * @version 2011-8-17
 */
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements VerifyCodeService {

    public static final String COOKIE_VERIFY_NUMBER = "verify_number";
    public static final String COOKIE_PATH = "/";
    public static final long COOKIE_EXPIRE_TIME = 2 * 60 * 1000;

    private static final Log LOG = LogFactory.getLog(VerifyCodeController.class);

    public boolean verify(String sRand, String verifyNumber) {
        if (StringUtils.isBlank(sRand) || StringUtils.isBlank(verifyNumber)) {
            return false;
        }
        sRand = sRand.trim();
        verifyNumber = verifyNumber.trim();
        String[] verifys = verifyNumber.split("_");
        String dateString = verifys[1];
        String key = sRand + dateString;
        String md5Key = MD5Utils.encryptWithKey(key.toLowerCase());
        boolean expired = System.currentTimeMillis() - Long.parseLong(dateString) > COOKIE_EXPIRE_TIME;
        boolean isSuccess = md5Key.equals(verifys[0]) && !expired;
        if (isSuccess) {
            CookieUtils.removeCookie(WebContext.currentRequest(), WebContext.currentResponse(), COOKIE_VERIFY_NUMBER);
        }
        return isSuccess;
    }

    public void generateVerifyCodeCookie(String sRand) {
        // 将加密后的认证码存入COOKIE
        String dateString = String.valueOf(System.currentTimeMillis());
        String key = sRand + dateString;

        String md5Key = MD5Utils.encryptWithKey(key.toLowerCase());
        Cookie cookie = new Cookie(COOKIE_VERIFY_NUMBER, md5Key + "_" + dateString);
        cookie.setPath(COOKIE_PATH);
        CookieUtils.writeCookie(cookie);
    }

}
