package com.winxuan.ec.front.controller.cps;

import javax.servlet.http.Cookie;

import com.winxuan.ec.front.controller.cps.extractor.CpsExtractor;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.util.web.WebContext;
/**
 * description
 * 
 * @author zhoujun
 * @version 1.0,2011-9-29
 */
public class UnionOrderValidate {
	
	 /**
     * 判断是否是联盟订单
     * 如果用户的渠道属性为代理，则直接返回False
     * @param request
     * @return
     */
	public boolean isUnionOrder(Customer customer){
		if(customer.getChannel() != null && customer.getChannel().getId().equals(Channel.CHANNEL_AGENT)){
				return false;
		}
		Cookie[] cookies = WebContext.currentRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.startsWith(CpsExtractor.COOKIE_NAME_PREFIX)) {
					return true;
				}
			}
			
		}
		return false;
	}
	/**
	 * 获取联盟id
	 * @param request
	 * @return
	 */
	public Long getUnionId(){
		Long  cpsId = null ;
		Cookie[] cookies = WebContext.currentRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.startsWith(CpsExtractor.COOKIE_NAME_PREFIX)) {
			         String id = cookieName.substring(CpsExtractor.COOKIE_NAME_PREFIX.length());
			         cpsId = Long.parseLong(id);   
				}
			}
		}
		 return cpsId;
	}
}
