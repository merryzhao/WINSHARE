package com.winxuan.ec.front.controller.cps.extractor;

import java.math.BigDecimal;
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

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class VgouExtractor extends AbstractExtractor{
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void addParameter(PostMethod postMethod, UnionOrder unionOrder) {
		final String separator = ","; 
		Order order = unionOrder.getOrder();
		Iterator it = order.getItemList().iterator();
		BigDecimal fanli = new BigDecimal(0);
		StringBuffer productFlMoney = new StringBuffer();
		StringBuffer productId = new StringBuffer();
		StringBuffer productPrice = new StringBuffer();
		StringBuffer productNum = new StringBuffer();
		int index=0;
		while(it.hasNext()){
			OrderItem item = (OrderItem) it.next();
			BigDecimal temp = item.getSalePrice().multiply(new BigDecimal(item.getDeliveryQuantity()));
			if(index>0){
				productFlMoney.append(separator);
				productId.append(separator);
				productPrice.append(separator);
				productNum.append(separator);
			}
			index++;
			if(item.getProductSale().getProduct().getSort().getId()==Code.PRODUCT_SORT_MERCHANDISE){
				BigDecimal productFanli = temp.multiply(new BigDecimal("0.01")); 
				productFlMoney.append(productFanli.toString());
				fanli = fanli.add(productFanli);
			}else{
				BigDecimal productFanli = temp.multiply(new BigDecimal("0.1")); 
				productFlMoney.append(productFanli.toString());
				fanli = fanli.add(productFanli);
			}
			productId.append(item.getProductSale().getId());
			productPrice.append(item.getSalePrice());
			productNum.append(item.getPurchaseQuantity());
		}

		String spCode ="xhbs";

		postMethod.addParameter("spcode", spCode);
		postMethod.addParameter("unionParams", unionOrder.getCookieInfo());
		postMethod.addParameter("orderCode", order.getId());
		postMethod.addParameter("orderTime", dateFormat.format(order.getCreateTime()));
		postMethod.addParameter("orderMoney", order.getSalePrice().toString());
		postMethod.addParameter("orderTotalMoney", order.getSalePrice().toString());
		postMethod.addParameter("productCount",String.valueOf(order.getPurchaseQuantity()));
		postMethod.addParameter("productId", productId.toString());
		postMethod.addParameter("productNum", productNum.toString());
		postMethod.addParameter("productPrice", productPrice.toString());
		postMethod.addParameter("productFlMoney", productFlMoney.toString());
		postMethod.addParameter("fanli", fanli.toString());
		
	}
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException {
		String unionParams = request.getParameter("unionParams");
		if(StringUtils.isBlank(unionParams)){
			throw new CpsException("vgou Error:连接参数错误，请咨询网站负责人");
		}
		return unionParams;
	}
	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}
	
}
