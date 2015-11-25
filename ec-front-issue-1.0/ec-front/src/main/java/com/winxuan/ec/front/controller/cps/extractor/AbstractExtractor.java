package com.winxuan.ec.front.controller.cps.extractor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public abstract class AbstractExtractor implements CpsExtractor{
private   static final   Log LOG =LogFactory.getLog(AbstractExtractor.class);   

private static final int DEFAULT_COOKIE_MAX_AGE = 3600 * 24 * 1;
    
	private String sendUrl ;
	
	private boolean needSend;
	
	private boolean hasPost;
	
	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}


	public void setNeedSend(boolean needSend) {
		this.needSend = needSend;
	}

	@Override
	public void send(UnionOrder unionOrder) {
		sendToCps(unionOrder,hasPost);
	}
	
	@Override
	public boolean isNeedSend() {
		return needSend;
	}
	 

	public boolean isHasPost() {
		return hasPost;
	}

	public void setHasPost(boolean hasPost) {
		this.hasPost = hasPost;
	}

	@Override
	public Cookie extract(HttpServletRequest request) throws CpsException {
		String uri = request.getRequestURI();
		String cookieName = uri.substring(uri.lastIndexOf("/") + 1);
		Cookie cookie = new Cookie(COOKIE_NAME_PREFIX + cookieName,
				extractCookieValue(request));
		cookie.setMaxAge(maxAge());
		return cookie;
	}
	
	protected abstract String extractCookieValue(HttpServletRequest request) throws CpsException;
	
	
	protected int maxAge(){
		return DEFAULT_COOKIE_MAX_AGE;
	}
	
	/**
	 * 发送联盟订单
	 * @param unionOrder
	 */
	private void sendToCps(UnionOrder unionOrder,boolean isGet) {
		if(hasPost){
			sendToCpsPost(unionOrder);
		}else{
			sendToCpsGet(unionOrder,sendUrl);
		}
	}
	
	private void sendToCpsPost(UnionOrder unionOrder) {
		final int timeOutMi = 6000;
		PostMethod postMethod = new PostMethod(sendUrl);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOutMi);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOutMi);		
		Header head = new Header();
		head.setName("Content-Type");
		head.setValue("application/x-www-form-urlencoded;charset=utf-8");
		postMethod.setRequestHeader(head);
		addParameter(postMethod, unionOrder);
		try {
			int status = httpClient.executeMethod(postMethod);
			if(status == HttpStatus.SC_OK){
				LOG.info("联盟 :"+unionOrder.getUnion().getId()+"   订单号："+unionOrder.getOrder().getId()+"状态："+status+" ----cpsSend success!");
			}else{
				LOG.info("联盟:"+unionOrder.getUnion().getId()+"  订单号："+unionOrder.getOrder().getId()+"状态："+status+" ----cpsSend filure!");	
			}
		} catch (Exception e) {
			LOG.info("异常  联盟:"+unionOrder.getUnion().getId()+"  订单号："+unionOrder.getOrder().getId()+"cpsSend filure!",e);
			LOG.error(e.getMessage(),e);
		} finally {
			postMethod.releaseConnection(); 
		}
		}
	
	public abstract void sendToCpsGet(UnionOrder unionOrder,String sendUrl);
	
	/**
	 * 添加发送参数
	 * @param postMethod
	 */
	public abstract void addParameter(PostMethod postMethod,UnionOrder unionOrder);
	

}
