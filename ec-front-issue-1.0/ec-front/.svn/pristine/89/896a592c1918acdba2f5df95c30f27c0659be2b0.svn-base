package com.winxuan.ec.front.controller.cps.extractor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class ZhigouExtractor  extends AbstractExtractor{
	@Override
	public void addParameter(PostMethod postMethod, UnionOrder unionOrder) {
		Order order = unionOrder.getOrder();

		String merid = "54871";
		String orderno = order.getId();
		String money = String.valueOf(order.getSalePrice());

		String orderstate = "";
		if(order.getPaymentStatus().getId() == Code.ORDER_PAY_STATUS_COMPLETED) {
			orderstate = "0";
		} else {
			orderstate = "1";
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String orderdate = format.format(order.getCreateTime());
		String source = unionOrder.getCookieInfo();
		postMethod.addParameter("merid", merid);
		postMethod.addParameter("orderno", orderno);
		postMethod.addParameter("money", money);
		postMethod.addParameter("orderstate", orderstate);
		postMethod.addParameter("orderdate", orderdate);
		postMethod.addParameter("source", source);
	}
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException {
		String source = request.getParameter("source");
		if(StringUtils.isBlank(source)){
			throw new CpsException("LMK Error:连接参数错误，请咨询网站负责人");
		}
		return source;
	}
	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}
	
}
