package com.winxuan.ec.front.controller.cps.extractor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.UnionOrder;


/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class ChanetExtractor extends AbstractExtractor {
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException {
		String from = request.getParameter("from");
		if(StringUtils.isBlank(from)){
			throw new CpsException("连接参数错误，请咨询网站负责人");
		}
		return from;
	}

	@Override
	public void addParameter(PostMethod postMethod, UnionOrder unionOrder) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}
}
