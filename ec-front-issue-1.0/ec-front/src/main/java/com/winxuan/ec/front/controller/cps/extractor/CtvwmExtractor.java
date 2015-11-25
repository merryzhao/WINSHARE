package com.winxuan.ec.front.controller.cps.extractor;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class CtvwmExtractor extends AbstractExtractor{
	private static final Log LOG = LogFactory.getLog(CtvwmExtractor.class);
	
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException {
		String aid = request.getParameter("a_id");
		String mid = request.getParameter("m_id");
		String cid = request.getParameter("c_id");
		String lid = request.getParameter("l_id");
		String memid = request.getParameter("mem_id");
		String wid = request.getParameter("w_id");
		String rd = request.getParameter("rd");
		String muid = StringUtils.isBlank(request.getParameter("muid")) ? ""
				: request.getParameter("muid");
		if (StringUtils.isBlank(aid) || StringUtils.isBlank(mid)
				|| StringUtils.isBlank(cid) || StringUtils.isBlank(lid)
				|| StringUtils.isBlank(memid) || StringUtils.isBlank(wid)
				|| StringUtils.isBlank(rd)){
			throw new CpsException("连接失败，请咨询网站负责人");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(aid+'|'+mid+'|'+cid+'|'+lid+'|'+memid+'|'+wid+'|'+muid);
		
		return sb.toString();
	}


	@Override
	public void addParameter(PostMethod postMethod,UnionOrder unionOrder) {
		
	}
	
	
	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		final int sucess = 200;
		final String separator = "||";
		Order order = unionOrder.getOrder();
		StringBuffer sb = new StringBuffer();
		sb.append(sendUrl);
		String cookieInfo = unionOrder.getCookieInfo();
		sb.append("?a_id="+cookieInfo);
		sb.append("&m_id=winxuancps");
		sb.append("&mbr_id="+order.getCustomer().getId());
		sb.append("&o_cd="+order.getId());
		String categoryCode = "";
		String productId = "";
		String productNum = "";
		String price = ""; 
		Iterator<OrderItem> items = order.getItemList().iterator();
		while(items.hasNext()){
			OrderItem orderItem = (OrderItem) items.next();
			categoryCode = categoryCode + separator + "0";
			productId = productId + separator + orderItem.getProductSale().getId();
			productNum = productNum + separator + orderItem.getPurchaseQuantity();
			price = price + separator + orderItem.getSalePrice();
		}
		sb.append("&c_cd="+categoryCode.substring(separator.length()));
		sb.append("&p_cd="+productId.substring(separator.length()));
		sb.append("&it_cnt="+productNum.substring(separator.length()));
		sb.append("&price="+price.substring(separator.length()));
		sb.append("&o_stat="+"100");
	try {
		HttpURLConnection conn = (HttpURLConnection) new URL(sb
					.toString()).openConnection();
		if (conn.getResponseCode() == sucess){
			LOG.info("联盟 :"+unionOrder.getUnion().getId()+"   订单号："+unionOrder.getOrder().getId()+"状态："+conn.getResponseCode()+" ----cpsSend success!");
		}
	} catch (Exception e) {
		LOG.info(e.getMessage());
	}	
		
	}



}
