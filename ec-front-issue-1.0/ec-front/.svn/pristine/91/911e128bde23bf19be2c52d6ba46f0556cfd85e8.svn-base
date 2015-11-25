package com.winxuan.ec.front.controller.cps.extractor;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.Product;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2012-3-15
 */
public class QQExtractor {
	private static final Log LOG = LogFactory.getLog(QQExtractor.class);
	
	private String qqCpsUrl;
	
	public void setQqCpsUrl(String qqCpsUrl) {
		this.qqCpsUrl = qqCpsUrl;
	}

	@Async
	public  void sendToQQCps(Order order){
		final int sucess = 200;		
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(qqCpsUrl+addParameter2QQ(order)
			).openConnection();
			if (conn.getResponseCode() == sucess){
				LOG.info("联盟 :"+order.getId()+"   订单号："+order.getId()+"状态："+conn.getResponseCode()+" ----cpsSend success!");
			}
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}	
	}
	
	private  String addParameter2QQ(Order order){
		 DateFormat dateform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Iterator<OrderItem> items = order.getItemList().iterator();
		String pName = "";
		while (items.hasNext()) {
			OrderItem orderItem = (OrderItem) items.next();
			Product product = orderItem.getProductSale().getProduct();
			pName = pName + product.getName()+",";
		}
		try {
			pName = URLEncoder.encode(pName.substring(0,pName.length()-1),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.info("qqfanli encoder error");
		}
		BigDecimal pp = order.getActualMoney();
		String createTime = "";
		try {
			 createTime = URLEncoder.encode(dateform.format(order.getCreateTime()),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.info("qqfanli encode error");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("?cid=299").append("&wid=435983")
				.append("&qqoid=" + order.getCustomer().getName())
				.append("&qqmid=winxuan").append("&ct=qqlogin1")
				.append("&on=" + order.getId()).append("&ta=1")
				.append("&pna=" + pName).append("&pp=" + String.valueOf(pp))
				.append("&sd=" + createTime).append("&encoding=UTF-8");	
		return sb.toString();
	}
}
