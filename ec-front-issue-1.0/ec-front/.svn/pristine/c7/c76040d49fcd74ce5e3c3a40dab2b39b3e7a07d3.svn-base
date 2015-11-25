package com.winxuan.ec.front.controller.cps.extractor;

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
public class LianMarkExtractor extends AbstractExtractor{
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException {
		final String separator = "|";	
		String ct= request.getParameter("ct");
		String mId = request.getParameter("mId");
	 	String linkId = request.getParameter("linkId");
	 	String linkType = request.getParameter("linkType");
	 	String cid = request.getParameter("c_id");
		String rtUrl = request.getParameter("rtUrl");
		if(StringUtils.isBlank(mId) || StringUtils.isBlank(linkId) || StringUtils.isBlank(linkType) || StringUtils.isBlank(rtUrl)) {
			throw new CpsException("LMK Error:连接参数错误，请咨询网站负责人");
		}
		return mId + separator + linkId +separator + linkType + separator + ct + separator + cid;
	}
	
	@Override
	public void addParameter(PostMethod postMethod,UnionOrder unionOrder) {
		final String separator = "|";
		Order order = unionOrder.getOrder();
		String prdId = "003";
		String gid = "G000000909";// 联马克分配给广告主的ID固定编号(由联马克技术部门提供)
		String pcd = ""; // 产品编号(如果没有,和订单号写一样的)
		String cnt = ""; // 数量
		String price = ""; // 价格
		String categoryId = ""; // 分类编号(没有就写订单号)
		String lmk = unionOrder.getCookieInfo(); // 联马克的cookie (就是cid)
		if (StringUtils.isBlank(lmk)) {
			return;
		}
		String ordreId = order.getId(); // 订单编号(必须有且必须唯一)
		String customerId = String.valueOf(order.getCustomer().getId()); // 购买人ID(尽量写,如果没有写订单号)
		String customerName = order.getCustomer().getName(); // 购买人姓名(没有就写member)
		Iterator<OrderItem> items = order.getItemList().iterator();
		int index = 0;
		while (items.hasNext()) {
			OrderItem orderItem = (OrderItem) items.next();
			String productId = String.valueOf(orderItem.getProductSale()
					.getId());
			String count = String.valueOf(orderItem.getPurchaseQuantity());
			String salePrice = String.valueOf(orderItem.getSalePrice());
			String categoryCode = orderItem.getProductSale().getProduct()
					.getCategory().getName();
			if (index > 0) {
				pcd += separator;
				cnt += separator;
				price += separator;
				categoryId += separator;
			}
			index++;
			pcd += productId;
			cnt += count;
			price += salePrice;
			categoryId += categoryCode;
		}
		postMethod.addParameter("prd_id", prdId);
		postMethod.addParameter("g_id", gid);
		postMethod.addParameter("lmk", lmk);
		postMethod.addParameter("o_cd", ordreId);
		postMethod.addParameter("p_cd", pcd);
		postMethod.addParameter("cnt", cnt);
		postMethod.addParameter("price", price);
		postMethod.addParameter("id", customerId);
		postMethod.addParameter("name", customerName);
		postMethod.addParameter("c_cd", categoryId);
	}


	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}
}
