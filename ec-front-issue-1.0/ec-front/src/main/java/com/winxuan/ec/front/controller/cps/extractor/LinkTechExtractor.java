package com.winxuan.ec.front.controller.cps.extractor;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class LinkTechExtractor extends AbstractExtractor{
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException {
		final String separator = "|";
		String aid  = request.getParameter("a_id");   
		String mid  = request.getParameter("m_id");   
		String cid  = request.getParameter("c_id");  
		String lid  = request.getParameter("l_id");   
		String ltype1 = request.getParameter("l_type1"); 
		String ltCookieInfo = request.getParameter("LTINFO");
		if(!StringUtils.isBlank(ltCookieInfo)){  
			return ltCookieInfo;
		}
		if(StringUtils.isBlank(aid) || StringUtils.isBlank(mid) || StringUtils.isBlank(cid) || StringUtils.isBlank(lid) || StringUtils.isBlank(ltype1) ) {
			throw new CpsException("LPMS: Parameter Error");
		}
		return aid + separator + cid +separator + lid + separator + ltype1 + separator;
	}
	
	@Override
	public void addParameter(PostMethod postMethod,UnionOrder unionOrder) {
		Order order = unionOrder.getOrder();
		String productCode = "";
		String productCount = "";
		String price = "";
		String category = "";
		String orderiId = order.getId();
		Iterator<OrderItem> items = order.getItemList().iterator();
		int index = 0;
		while (items.hasNext()) {
			OrderItem orderItem = (OrderItem) items.next();
			String productId = String.valueOf(orderItem.getProductSale().getId());
			String quantity = String.valueOf(orderItem.getPurchaseQuantity());
			String salePrice = String.valueOf(orderItem.getSalePrice());
			String categoryCode = orderItem.getProductSale().getProduct().getSort().getId().equals(Code.PRODUCT_SORT_BOOK) ? "0" : " 1";
			final String separator = "||"; 
			if (index > 0) {
				productCode += separator;
				productCount += separator;
				price += separator;
				category += separator;
			}
			index++;
			productCode += productId;
			productCount += quantity;
			price += salePrice;
			category += categoryCode;      
		}
		String userId = "xhbooks";
		String merchantId = "xhbooks";
		postMethod.addParameter("a_id", unionOrder.getCookieInfo());
		postMethod.addParameter("m_id", merchantId);
		postMethod.addParameter("mbr_id", userId + "()");
		postMethod.addParameter("o_cd", orderiId);
		postMethod.addParameter("p_cd", productCode);
		postMethod.addParameter("price", price);
		postMethod.addParameter("it_cnt", productCount);
		postMethod.addParameter("c_cd", category);	
	}

	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}

	
}
