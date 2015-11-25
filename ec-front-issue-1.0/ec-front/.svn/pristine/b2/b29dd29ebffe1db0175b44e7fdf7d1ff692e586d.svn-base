package com.winxuan.ec.front.controller.cps.query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-8
 */
public class CtvwmQueryExtractor implements QueryExtractor {
	
	@Override
	public Map<String, Object> getQueryMap(HttpServletRequest request) throws CpsException {
		final long second = 24*60*60*1000; 
		final int dateLength = 8;
		final int  maxTimePeriod = 50;
		String beginDateTemp= request.getParameter("startdate");
		String endDateTemp= request.getParameter("enddate");
		if(StringUtils.isBlank(beginDateTemp)){
			throw new CpsException("请输入查询的开始日期..");
		}
		if(StringUtils.isBlank(endDateTemp)){
			throw new CpsException("请输入查询的结束日期..");
		}
		if(beginDateTemp.length() != dateLength||endDateTemp.length() !=dateLength){
			throw new CpsException("时间格式错误，正确80: startdate=20080808&enddate=20080809");
		}
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = df.parse(beginDateTemp);
			endDate = df.parse(endDateTemp);
		} catch (ParseException e) {
			throw new CpsException("时间转化错误");
		}
		long day=(endDate.getTime()- beginDate.getTime())/second;  
		if(day > maxTimePeriod){
			throw new CpsException("查询日期大于了50天,请重新输入");
		}
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("startCreateDate", beginDate);
		parameter.put("endCreateDate", endDate);
		parameter.put("unionId", UnionOrder.UNION_CTVWM);
		return parameter;
	}


	@Override
	public String generateResult(List<UnionOrder> unionOrders , Map<String,Object> queryMap) {
		final String separator = "||";
		final DateFormat df = new SimpleDateFormat("HHmmss");
		final String tab = "\t";
		final String newLine = "\n";
		if (unionOrders == null || unionOrders.size() == 0) {
			return null;
		} else {
			StringBuffer sb = new StringBuffer();
			for(UnionOrder unionOrder : unionOrders){
				Order order = unionOrder.getOrder();
				String cookieInfo = unionOrder.getCookieInfo();
				sb.append(df.format(order.getCreateTime())).append(tab);//创建时间					
				sb.append(cookieInfo).append(tab);
				sb.append("winxuancps").append(tab);
				sb.append(String.valueOf(order.getCustomer().getId())).append(tab);//用户id
				sb.append(order.getId()).append(tab); //订单号
				String categoryCode = "";
				String productId = "";
				String productNum = "";
				String price = "";
				String state = "";
				Iterator<OrderItem> items = order.getItemList().iterator();
				while(items.hasNext()){
					OrderItem orderItem = (OrderItem) items.next();
					categoryCode = categoryCode + separator + "0";
					productId = productId + separator + orderItem.getProductSale().getId();
					productNum = productNum + separator + orderItem.getPurchaseQuantity();
					price = price + separator + orderItem.getSalePrice();
				} 
				sb.append(productId.substring(separator.length())).append(tab);//商品id
				sb.append(price.substring(separator.length())).append(tab);//订单项中商品价格
				sb.append(productNum.substring(separator.length())).append(tab);//商品数量
				sb.append(categoryCode.substring(separator.length())).append(tab);//商品分类
				Long orderState = order.getProcessStatus().getId();
				if( Code.ORDER_PROCESS_STATUS_NEW.equals(orderState)
						||Code.ORDER_PROCESS_STATUS_WAITING_PICKING.equals(orderState)
						||Code.ORDER_PROCESS_STATUS_PICKING.equals(orderState)){
					state = "100";
					
				} else if (orderState
						.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED)
						|| orderState
								.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)
						|| orderState
								.equals(Code.ORDER_PROCESS_STATUS_COMPLETED)) {
					state = "200";
				}else {
					state = "300";
				}
				sb.append(state).append(newLine);	
			}
			return sb.toString();
		}	
	}
}
