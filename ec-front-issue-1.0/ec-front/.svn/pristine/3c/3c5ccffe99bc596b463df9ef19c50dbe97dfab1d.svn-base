package com.winxuan.ec.front.controller.cps.extractor;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.framework.util.security.MD5NORMAL;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class TpyExtractor extends AbstractExtractor{
public  static final Log LOG = LogFactory.getLog(TpyExtractor.class);
	
	@Override
	protected String extractCookieValue(HttpServletRequest request)
			throws CpsException {
		String cookieValue ="";
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			throw new CpsException("tpy Error:连接参数错误，请咨询网站负责人");
		}
		String userId = request.getParameter("userid");		
		if(StringUtils.isBlank(userId)){
			throw new CpsException("tpy Error:连接参数错误，请咨询网站负责人");
		}	
		try {
			String typId = URLDecoder.decode(userId, "utf-8");
			cookieValue =URLEncoder.encode(
					 typId, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new CpsException("tpy Error:连接参数错误，请咨询网站负责人");
		}
		
	   return cookieValue;	
	}

	@Override
	public void addParameter(PostMethod postMethod, UnionOrder unionOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendToCpsGet(UnionOrder unionOrder,String sendUrl) {
		final int sucess = 200;
		Order order = unionOrder.getOrder();
		StringBuilder keyBuffer = new StringBuilder();
		String codeId = UnionOrder.UNION_TPY_ID;
		String cpsUserId = "";
		cpsUserId = unionOrder.getCookieInfo();

		String orderDate = new SimpleDateFormat("yyyyMMdd").format(unionOrder.getCreateDate());

		String orderTime = new SimpleDateFormat("HHmmss").format(unionOrder.getCreateDate());
		
		String price = String.valueOf(order.getActualMoney());
		String zhuangtai = "c";
		keyBuffer.append(cpsUserId);
		keyBuffer.append(codeId);
		keyBuffer.append(order.getId());
		keyBuffer.append(price);
		keyBuffer.append(zhuangtai);
		keyBuffer.append(UnionOrder.UNION_TPY_KEY);
		String key = MD5NORMAL.getMD5(keyBuffer.toString());
		String userid = "";
		try {		
			 userid =URLEncoder.encode(cpsUserId, "UTF-8");			
		} catch (UnsupportedEncodingException e) {
			LOG.info(e.getMessage());
		}
		String urlBuffer =sendUrl+"?userid=" + userid + "&codeid=" + codeId
				+ "&order_date=" + orderDate + "&order_time=" + orderTime
				+ "&orderid=" + order.getId() + "&price=" + price
				+ "&zhuangtai="+zhuangtai+"&key="+key;
	try {
		HttpURLConnection conn = (HttpURLConnection) new URL(urlBuffer
					.toString()).openConnection();
		if (conn.getResponseCode() == sucess){
			LOG.info("联盟 :"+unionOrder.getUnion().getId()+"   订单号："+unionOrder.getOrder().getId()+"状态："+conn.getResponseCode()+" ----cpsSend success!");
		}
	} catch (Exception e) {
		LOG.info(e.getMessage());
	}	
	}
}
