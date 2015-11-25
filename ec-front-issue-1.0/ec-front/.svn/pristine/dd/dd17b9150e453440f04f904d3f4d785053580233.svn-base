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
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-11
 */
public class ZhigouQueryExtractor implements QueryExtractor{

	@Override
	public Map<String, Object> getQueryMap(HttpServletRequest request)
			throws CpsException {
		final int ten =10;
		String pwd = request.getParameter("pwd");	
		if(!"888888".equals(pwd)) {
			throw new CpsException("请确认pwd传参正确..");
		}	
		String beginDate= request.getParameter("startdate");
		String endDate= request.getParameter("enddate");
		if(StringUtils.isBlank(beginDate)){
			throw new CpsException("请输入查询的开始日期..");
		}
		if(StringUtils.isBlank(endDate)){
			throw new CpsException("请输入查询的结束日期..");
		}
		if(beginDate.length()!=ten || endDate.length()!=ten){
			throw new CpsException("时间格式错误，正确格式 例: startime=2008-08-08&endtime=2008-08-09");
		}
		beginDate += " 00:00:00";
		endDate += " 00:00:00";
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date searchBeginDate = null;
		Date searchEndDate = null;
		try {
			searchBeginDate = df.parse(beginDate);
			 searchEndDate = df.parse(endDate);
		} catch (ParseException e) {
			throw new CpsException("格式错误");
		}
		Map<String,Object> parameter =new HashMap<String,Object>();
		parameter.put("startCreateDate", searchBeginDate);
		parameter.put("endCreateDate", searchEndDate);
		parameter.put("unionId", UnionOrder.UNION_ZHIGOU);
		return parameter;
	}

	@Override
	public String generateResult(List<UnionOrder> unionOrders,Map<String,Object> queryMap) {
		if(unionOrders==null || unionOrders.size()==0) {
			return null;
		} else {
			final String separator = "\t";
			final int three =3;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StringBuffer sb = new StringBuffer("");
			for(int i=0; i<unionOrders.size(); i++){
				UnionOrder unionOrder = (UnionOrder)unionOrders.get(i);
				Order order=unionOrder.getOrder();
				String orderDate = dateFormat.format(unionOrder.getCreateDate()) ;
				short status = 0;
				if(order.getPaymentStatus().getId() == Code.ORDER_PAY_STATUS_NONE) {
					status = 0;
				} else if((order.getPaymentStatus().getId() == Code.ORDER_PAY_STATUS_PART || order.getPaymentStatus().getId() == Code.ORDER_PAY_STATUS_COMPLETED) 
							&& (order.getProcessStatus().getId() == Code.ORDER_PROCESS_STATUS_NEW  || 
									order.getProcessStatus().getId() == Code.ORDER_PROCESS_STATUS_WAITING_PICKING
									|| order.getProcessStatus().getId() == Code.ORDER_PROCESS_STATUS_PICKING)) {
					status = 1;
				} else if(order.isDeliveried()) {
					status = 2;
				} else {
					status = three;
				}
				
				sb.append(orderDate + separator);
				sb.append(order.getId() + separator);
				sb.append(order.getSalePrice() + separator);
				sb.append(status + separator);
				sb.append(unionOrder.getCookieInfo() + "\t\n");
			}
			return sb.toString();
		}
	}

}
