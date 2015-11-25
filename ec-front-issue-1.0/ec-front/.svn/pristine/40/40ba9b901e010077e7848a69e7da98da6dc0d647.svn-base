package com.winxuan.ec.front.controller.cps.extractor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class WeiyiExtractor extends AbstractExtractor{
public static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	protected String extractCookieValue(HttpServletRequest request)
			throws CpsException {
		String cid = request.getParameter("cid");
		if (StringUtils.isBlank(cid)) {
			throw new CpsException("LMK Error:连接参数错误，请咨询网站负责人");
		}
		return cid;
	}

	@Override
	public void addParameter(PostMethod postMethod,UnionOrder unionOrder) {
	final String separator = "|";
	Order order = unionOrder.getOrder();
	String mid = "xinhua";
	String oDate = dateFormat.format(order.getCreateTime());
	String cid = unionOrder.getCookieInfo();
	String bid = order.getCustomer().getName();
	String oid = order.getId();
	String pid = "";
	String ptype = "";
	String pnum = "";
	String price = "";

	Iterator items = order.getItemList().iterator();
	int index = 0;
	while (items.hasNext()) {
		OrderItem orderItem = (OrderItem) items.next();
		String productId = String.valueOf(orderItem.getProductSale().getId());
		String quantity = String.valueOf(orderItem.getPurchaseQuantity());
		String salePrice = String.valueOf(orderItem.getSalePrice());
		if (index > 0) {
			pid += separator;
			ptype += separator;
			pnum += separator;
			price += separator;
		}
		index++;
		pid += productId;// 一种商品编号
		ptype += "book";// 一种商品类型
		pnum += quantity;// 一种商品数量
		price += salePrice;// 一种商品价格
	}
	postMethod.addParameter("mid",mid);
	postMethod.addParameter("oDate",oDate);
	postMethod.addParameter("cid",cid);
	postMethod.addParameter("bid",bid);
	postMethod.addParameter("oid",oid);
	postMethod.addParameter("pid",pid);
	postMethod.addParameter("ptype",ptype);
	postMethod.addParameter("pnum",pnum);
	postMethod.addParameter("price",price);
	postMethod.addParameter("ostat","0");
	}

	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}
}
