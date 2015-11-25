package com.winxuan.ec.front.controller.cps.extractor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class HjlmExtractor extends AbstractExtractor{
	@Override
	public void addParameter(PostMethod postMethod, UnionOrder unionOrder) {
		Order order = unionOrder.getOrder();
		postMethod.addParameter("orders", String.valueOf(order.getId()));
		postMethod.addParameter("price",String.valueOf(order.getActualMoney()));		
	}

	@Override
	protected String extractCookieValue(HttpServletRequest request)
			throws CpsException {
		return "HuangJinLianMeng";
	}

	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}

}
