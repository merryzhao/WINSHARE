package com.winxuan.ec.front.controller.cps.extractor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public interface CpsExtractor {
	
	 String COOKIE_NAME_PREFIX = "cps_";
	
	 boolean isNeedSend();
	
	 void send(UnionOrder unionOrder);
	 
	 Cookie extract(HttpServletRequest request) throws CpsException;
}
