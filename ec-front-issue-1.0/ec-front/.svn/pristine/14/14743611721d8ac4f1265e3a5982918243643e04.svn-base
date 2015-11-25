package com.winxuan.ec.front.controller.cps.extractor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.support.util.DateUtils;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class EqifaExtractor extends AbstractExtractor{
	
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException {
		String url = request.getParameter("url");
		String from = request.getParameter("from");
		String wid = request.getParameter("wid");
		String fbt = request.getParameter("fbt");
		if(StringUtils.isBlank(url)||StringUtils.isBlank(from)){
			throw new CpsException("eqifa连接失败，请咨询网站负责人");
		}
		String eqifaInfoCookieValue = from;
		if (!StringUtils.isBlank(wid)) {
			eqifaInfoCookieValue += "|" + wid;
		}
		if (!StringUtils.isBlank(fbt)) {
			eqifaInfoCookieValue += "|" + fbt;
		}
		return eqifaInfoCookieValue;
	}


	@Override
	public void addParameter(PostMethod postMethod,UnionOrder unionOrder) {
			addParameter2Eqifa(postMethod, unionOrder);
	}
	/**
	 * 添加到eqifa参数
	 * @param postMethod
	 * @param unionOrder
	 */
	private void addParameter2Eqifa(PostMethod postMethod,UnionOrder unionOrder){
		final String separator = "|";
		final int three = 3;
		DateFormat format = new SimpleDateFormat(DateUtils.LONG_DATE_FORMAT_STR);
		Order order = unionOrder.getOrder();
		String cid = "299";
		String on = order.getId();
		String sd = format.format(unionOrder.getCreateDate());
		String eqifaId = unionOrder.getCookieInfo();
		String []s = eqifaId.split("\\|");
		String wid = "";
		String fbt = "";
		if(s!=null&&s.length>=2){
			wid = s[1];
		}
		if(s!=null&&s.length>=three){
			fbt = s[2];
		}
		String pn = "";
		String pna = "";
		String ct = "";
		String ta = "";
		String pp = "";
		String vwid = "";
		Iterator<OrderItem> items = order.getItemList().iterator();
		boolean b = false;
		while (items.hasNext()) {
			OrderItem orderItem = (OrderItem) items.next();
			Product product =orderItem.getProductSale().getProduct();
			String productId = String.valueOf(orderItem.getProductSale().getId());
			String name = product.getName();
			String count = String.valueOf(orderItem.getPurchaseQuantity());
			String salePrice = String.valueOf(orderItem.getSalePrice());
			Long sort = orderItem.getProductSale().getProduct().getSort().getId();
		    String sortStr = Code.PRODUCT_SORT_BOOK.equals(sort)||Code.PRODUCT_SORT_VIDEO.equals(sort) ? "1" : "0";
			if (b) {
				pn += separator;
				pna += separator;
				ct += separator;
				ta += separator;
				pp +=separator;
			}
			b=true;
			pn += productId;
			pna += name;
			ct += sortStr;
			ta += count;
			pp += salePrice;
		}
		postMethod.addParameter("cid", cid);
		postMethod.addParameter("wid", wid);
		postMethod.addParameter("vwid", vwid);
		postMethod.addParameter("on", on);
		postMethod.addParameter("pn", pn);
		postMethod.addParameter("pna", pna);
		postMethod.addParameter("ct", ct);
		postMethod.addParameter("ta", ta);
		postMethod.addParameter("pp", pp);
		postMethod.addParameter("sd", sd);
		postMethod.addParameter("fbt", fbt);
		postMethod.addParameter("encoding", "utf-8");
	}
	
	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}



}
