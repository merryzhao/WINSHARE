package com.winxuan.ec.front.controller.cps.extractor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-8
 */
public class ADExtractor  extends AbstractExtractor{
	private static final Log LOG =LogFactory.getLog(ADExtractor.class);
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final int COOKIE_MAX_AGE = 3600 * 24 * 30;
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException{
		StringBuffer message = new StringBuffer();
		String customerID = request.getParameter("customerID"); //广告联盟ID
		String stan = request.getParameter("stan");
		String url = request.getParameter("url");
		if (!StringUtils.isBlank(url)) {
		  if(url.indexOf("keyword=")>0){
		  int index=url.indexOf("keyword=")+"keyword=".length();	            
		       try {
				url= url.substring(0,index)+URLEncoder.encode(url.substring(index),"utf-8");
			} catch (UnsupportedEncodingException e) {
				LOG.info("转码异常");
				throw new CpsException("参数url异常");
			}     
		   }
		}	
		if(StringUtils.isBlank(customerID)||StringUtils.isBlank(stan)){
			throw new CpsException("连接参数错误，请咨询网站负责人");
		}
		
		String cookieValue = message.append(customerID).append("|").append(stan).toString();
		return cookieValue;
	}
	
	@Override
	public void addParameter(PostMethod postMethod, UnionOrder unionOrder) {
		Order order = unionOrder.getOrder();
		postMethod.addParameter("source", unionOrder.getCookieInfo());
		postMethod.addParameter("orderid", order.getId());
		postMethod.addParameter("price",
				String.valueOf(order.getActualMoney()));
		postMethod.addParameter("listprice",
				String.valueOf(order.getListPrice()));
		postMethod.addParameter("orderdate",
				dateFormat.format(order.getCreateTime()));
	}

	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected int maxAge(){
		return COOKIE_MAX_AGE;
	}

}
