package com.winxuan.ec.front.controller.cps.extractor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class AleadPayExtractor extends AbstractExtractor{
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException {
		String guid = request.getParameter("guid");
		if(StringUtils.isBlank(guid)){
			throw new CpsException("aleadpay Error:连接参数错误，请咨询网站负责人");
		}
		return guid;
	}
	@Override
	public void addParameter(PostMethod postMethod, UnionOrder unionOrder) {
		Order order = unionOrder.getOrder();
		postMethod.addParameter("p1", order.getId());
		postMethod.addParameter("currency","CNY");
		postMethod.addParameter("amount",String.valueOf(order.getActualMoney()));
		postMethod.addParameter("orderId",order.getId());	
	}	
	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}
}
