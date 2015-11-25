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
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-9
 */
public class LianMarkQueryExtractor implements QueryExtractor{

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
		Date beginDate = null;
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
		parameter.put("unionId", UnionOrder.UNION_LIANMARK);
		return parameter;
	}

	@Override
	public String generateResult(List<UnionOrder> unionOrders,Map<String,Object> queryMap) {
		if(unionOrders==null||unionOrders.size()==0){
			return null;
		}else{
			String separator="\t";
			String prdid = "003";
			StringBuffer sb=new StringBuffer("");
			for(UnionOrder unionOrder : unionOrders){
				Order order = unionOrder.getOrder();
				String lmk = unionOrder.getCookieInfo();
				String orderId = order.getId().toString();
				String hhmmss = new SimpleDateFormat("HHmmss").format(unionOrder.getCreateDate());
				String registerName = "";
				String customerId = order.getCustomer().getId().toString();
				for(OrderItem orderItem : order.getItemList()){
					sb.append(prdid + separator);
					sb.append(hhmmss + separator);
					sb.append(lmk + separator);
					sb.append(orderId + separator);
					sb.append(orderItem.getProductSale().getId() + separator);
					sb.append(customerId + separator);
					sb.append(registerName + separator);
					sb.append(String.valueOf(orderItem.getPurchaseQuantity()) + separator);
					sb.append(String.valueOf(orderItem.getSalePrice()) + separator);
					sb.append("" + "\t\n");
				}
			}
			return sb.toString();
		}
	}

}
