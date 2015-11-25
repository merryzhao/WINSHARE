package com.winxuan.ec.front.controller.cps.query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
 * @version 1.0,2011-11-9
 */
public class LinkTechQueryExtractor implements QueryExtractor{

	@Override
	public Map<String, Object> getQueryMap(HttpServletRequest request)
			throws CpsException {
		final long second =24*60*60*1000; 
		final int eight =8;
		final int  fifty =50;
		String beginDateTemp= request.getParameter("beginDate");
		String endDateTemp= request.getParameter("endDate");
		
		if(StringUtils.isBlank(beginDateTemp)){
			throw new CpsException("请输入查询的开始日期..");
		}
		if(StringUtils.isBlank(endDateTemp)){
			throw new CpsException("请输入查询的结束日期..");
		}
		if(beginDateTemp.length()!=eight||endDateTemp.length()!=eight){
			throw new CpsException("时间格式错误，正确80: beginDate=280808&endDate=20080809");
		}
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate =null;
		Date endDate = null;
		try {
			beginDate = df.parse(beginDateTemp);
			endDate = df.parse(endDateTemp);
		} catch (ParseException e) {
			throw new CpsException("日期格式错误");
		}
		String searchDate1=df2.format(beginDate);
		String searchDate2=df2.format(endDate);
		searchDate1+=" 00:00:00";
		searchDate2+=" 23:59:59";
		try {
			beginDate=df3.parse(searchDate1);
			endDate=df3.parse(searchDate2);
		} catch (ParseException e) {
			throw new CpsException("日期格式错误");
		}
		long day=(endDate.getTime()-beginDate.getTime())/second;  
		if(day>fifty){
			throw new CpsException("查询日期大于了50天,请重新输入");
		}
		Map<String,Object> parameter =new HashMap<String,Object>();
		parameter.put("startCreateDate", beginDate);
		parameter.put("endCreateDate", endDate);
		parameter.put("unionId", UnionOrder.UNION_LINKTECH);
		return 	parameter;
	}

	@Override
	public String generateResult(List<UnionOrder> unionOrders ,Map<String,Object> queryMap) {
		if (unionOrders == null || unionOrders.size() == 0) {
			return null;
		} else {
			String separator="\t";
			final int check = 2;
			final String creator = "xhbooks()";
			DateFormat df = new SimpleDateFormat("HHmmss");
			StringBuffer sb = new StringBuffer("\n");
			for(UnionOrder unionOrder : unionOrders){
				Order order = unionOrder.getOrder();
				for(OrderItem orderItem : order.getItemList()){
					sb.append(check + separator);//固定值check=2
					sb.append(df.format(unionOrder.getCreateDate()) + separator);//hhmiss
					sb.append(unionOrder.getCookieInfo() + separator);//cookie
					sb.append(order.getId() + separator);
					sb.append(orderItem.getProductSale().getId() + separator);
					sb.append(creator + separator);//下单人
					sb.append(orderItem.getPurchaseQuantity() + separator);
					sb.append(orderItem.getSalePrice() + separator);
					sb.append((orderItem.getProductSale().getProduct().getSort().getId().equals(Code.PRODUCT_SORT_BOOK) ? "0" : " 1") + separator);//商品分类
					sb.append("\n");
				}
			}
			return sb.toString();
		}
	}

}
