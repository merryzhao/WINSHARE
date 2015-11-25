package com.winxuan.ec.front.controller.cps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.winxuan.ec.front.controller.cps.extractor.CpsExtractor;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.model.union.Union;
import com.winxuan.ec.service.order.UnionOrderService;
import com.winxuan.ec.service.union.UnionService;

/**
 * description test1
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
@Component
public class Cps {	
	
	private static final String DEFAULT_REDIRECT_URL = "http://www.winxuan.com/";
	private static final int DEFAULT_COOKIE_MAX_AGE = -1;
	private static final String DEFAULT_COOKIE_DOMAIN = ".winxuan.com";
	private static final String DEFAULT_COOKIE_PATH = "/";
	private static final String DEFAULT_ENCODING = "UTF-8";

	private String redirectUrlTarget;

	private int cookieMaxAge = DEFAULT_COOKIE_MAX_AGE;

	private String cookieDomain = DEFAULT_COOKIE_DOMAIN;

	private String cookiePath = DEFAULT_COOKIE_PATH;

	private String encoding = DEFAULT_ENCODING;

	private CpsExtractor cpsExtractor;
	
	@Autowired
	private UnionService unionService ;
	
	@Autowired
	private UnionOrderService unionOrderService;	
	
   
	/**
	 * 发送联盟订单并保存
	 * @param request
	 * @param order
	 */
	@Async
	public void saveAndSend(HttpServletRequest request,Order order){
		UnionOrder unionOrder = getUnionOrder(request, order);
		if(unionOrder !=null){
			save(unionOrder);
			if(cpsExtractor.isNeedSend()){
				send(unionOrder);
			}
		}
	}
	/**
	 * 发送
	 * @param request
	 * @param order
	 */
	public void send(UnionOrder unionOrder){
		cpsExtractor.send(unionOrder);
	}
	
	/**
	 * 保存联盟订单
	 * @param request
	 * @param order
	 */
	public void save(UnionOrder unionOrder){
		unionOrderService.save(unionOrder);
	}
	
	public UnionOrder getUnionOrder(HttpServletRequest request,Order order){
		Long cpsId = null;
		Union union = null;
		String cookieInfo = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.startsWith(CpsExtractor.COOKIE_NAME_PREFIX)) {
			         String id = cookieName.substring(CpsExtractor.COOKIE_NAME_PREFIX.length());
			         cpsId = Long.parseLong(id);
			         cookieInfo = cookie.getValue();		         
				}
			}
		}
		else{
			return null;
		}
		if(cpsId !=null){
			 union = unionService.get(cpsId);
		}
		if(union==null ||order ==null){
			return null;
		}
		UnionOrder unionOrder = new UnionOrder();
		unionOrder.setCookieInfo(cookieInfo);
		unionOrder.setCreateDate(order.getCreateTime());
		unionOrder.setOrder(order);
		unionOrder.setUnion(union);
		return unionOrder;
	}
	
	

	public void track(HttpServletRequest request, HttpServletResponse response)
			throws IOException, CpsException {
		addHeader(response);
		deleteCookie(request, response);
		writeCookie(request, response);
		redirect(request, response);
	}

	protected void addHeader(HttpServletResponse response) {
		response.addHeader("P3P", "CP=\"NOI ADM DEV PSAi COM NAV OUR BUS NUI\"");
	}

	protected void writeCookie(HttpServletRequest request,
			HttpServletResponse response) throws CpsException {
		Cookie cookie = extractCookie(request);
		cookie.setDomain(cookieDomain);
		cookie.setPath(cookiePath);
		cookie.setMaxAge(cookieMaxAge);
		response.addCookie(cookie);
	}

	protected void deleteCookie(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (cookieName.startsWith(CpsExtractor.COOKIE_NAME_PREFIX)) {
					Cookie deleteCookie = new Cookie(cookieName, null);
					deleteCookie.setMaxAge(0);
					deleteCookie.setPath(cookiePath);
					deleteCookie.setDomain(cookieDomain);
					response.addCookie(deleteCookie);
				}
			}
		}
	}

	protected Cookie extractCookie(HttpServletRequest request) throws CpsException {
		return cpsExtractor.extract(request);
	}

	protected void redirect(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String redirectUrl = getParameter(request, redirectUrlTarget);
		response.sendRedirect(StringUtils.isBlank(redirectUrl) ? DEFAULT_REDIRECT_URL
				: redirectUrl);
	}

	protected final String getParameter(HttpServletRequest request,
			String parameterName) {
		String value = request.getParameter(parameterName);
		if (!StringUtils.isBlank(value)) {
			try {
				value = URLDecoder.decode(value, encoding);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return value;
	}

	public void setRedirectUrlTarget(String redirectUrlTarget) {
		this.redirectUrlTarget = redirectUrlTarget;
	}

	public void setCookieMaxAge(int cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}

	public String getRedirectUrlTarget() {
		return redirectUrlTarget;
	}

	public int getCookieMaxAge() {
		return cookieMaxAge;
	}

	public String getCookieDomain() {
		return cookieDomain;
	}

	public String getCookiePath() {
		return cookiePath;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public CpsExtractor getCpsExtractor() {
		return cpsExtractor;
	}
	public void setCpsExtractor(CpsExtractor cpsExtractor) {
		this.cpsExtractor = cpsExtractor;
	}	

	
}
