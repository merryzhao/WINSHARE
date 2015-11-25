package com.winxuan.ec.front.controller.cps.query;

import java.net.URLDecoder;
import java.net.URLEncoder;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.DateUtils;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-8
 */
public class EqifaQueryExtractor implements QueryExtractor {
	
	
	
	private static final Log LOG =LogFactory.getLog(EqifaQueryExtractor.class);

	@Autowired
	private OrderService ordeService;
	
	@Override
	public Map<String, Object> getQueryMap(HttpServletRequest request) throws CpsException {
		final long second =24*60*60*1000; 
		final int twelve =12;
		final int  fifty =50;
		String beginDateTemp= request.getParameter("beginDate");
		String endDateTemp= request.getParameter("endDate");
		if(StringUtils.isBlank(beginDateTemp)){
			throw new CpsException("请输入查询的开始日期..");
		}
		if(StringUtils.isBlank(endDateTemp)){
			throw new CpsException("请输入查询的结束日期..");
		}
		if(beginDateTemp.length()!=twelve||endDateTemp.length()!=twelve){
			throw new CpsException("时间格式错误，正确80: beginDate=200808080000&endDate=200808090000");
		}
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");
		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = df.parse(beginDateTemp);
			endDate = df.parse(endDateTemp);
		} catch (ParseException e) {
			throw new CpsException("时间转化错误");
		}
		long day=(endDate.getTime()- beginDate.getTime())/second;  
		if(day>fifty){
			throw new CpsException("查询日期大于了50天,请重新输入");
		}
		Map<String,Object> parameter =new HashMap<String,Object>();
		parameter.put("startCreateDate", beginDate);
		parameter.put("endCreateDate", endDate);
		parameter.put("unionId", UnionOrder.UNION_EQIFA);
		return parameter;
	}


	@Override
	public String generateResult(List<UnionOrder> unionOrders,
			Map<String, Object> queryMap) {
		StringBuffer sb = new StringBuffer("");
		sb.append("[QUERY]");
		List<Order> orderList = null;
		Pagination pagination = new Pagination();
		int currentPage = 1;
		while ((orderList = ordeService.find(generateQQResult(queryMap),
				Short.parseShort("0"), pagination)) != null
				&& orderList.size() > 0) {
			for (Order order : orderList) {
				sb.append(resolveOrder(order, null,false));
			}
			currentPage = currentPage + 1;
			pagination.setCurrentPage(currentPage);
		}
		for (int i = 0; i < unionOrders.size(); i++) {
			UnionOrder unionOrder = (UnionOrder) unionOrders.get(i);
			Order order = unionOrder.getOrder();
			sb.append(resolveOrder(order, unionOrder.getCookieInfo(),true));
		}
		sb.append("[/QUERY]");
		return sb.toString();
	}
	
	private Map<String,Object> generateQQResult(Map<String,Object> queryMap){
		Map<String, Object> qqQueryMap = new HashMap<String, Object>();
		qqQueryMap.put("startCreateTime", queryMap.get("startCreateDate"));
		qqQueryMap.put("endCreateTime", queryMap.get("endCreateDate"));
		qqQueryMap.put("source", Code.USER_SOURCE_QQ);
		qqQueryMap.put("unite", true);
		return qqQueryMap;
	}
	
	private String resolveOrder(Order order,String cookieInfo,boolean isUnionOrder){
		StringBuffer sb = new StringBuffer("");
		final String separator = "||";
		final int three =3;
		final String code ="utf-8";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String euid = "";
		String websiteid = "";
		if (!StringUtils.isBlank(cookieInfo)) {
			String[] s = cookieInfo.split("\\|");
			if (s != null && s.length >= 2) {
				websiteid = s[1];
			}
			if (s != null && s.length >= three) {
				euid = s[2];
			}
		}
		Iterator items = order.getItemList().iterator();
		String productName = "";
		String registerName = "";
		
		while (items.hasNext()) {
			OrderItem orderItem = (OrderItem) items.next();
			try {
				productName = URLEncoder.encode(orderItem.getProductSale().getProduct().getName(), code);
				productName = URLDecoder.decode(productName,code);
				registerName = URLEncoder.encode(order.getCustomer().getName(), code);
				registerName = URLDecoder.decode(registerName, code);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
			Long sort = orderItem.getProductSale().getProduct().getSort().getId();
			String ct = Code.PRODUCT_SORT_BOOK.equals(sort)||Code.PRODUCT_SORT_VIDEO.equals(sort) ? "1" : "0";
			sb.append(websiteid + separator);
			sb.append(registerName + separator);
			sb.append(df.format(order.getCreateTime()) + separator);
			sb.append(order.getId() + separator);
			sb.append(orderItem.getProductSale().getId() + separator);
			sb.append(productName + separator);
			sb.append(orderItem.getId() + separator);
			sb.append(orderItem.getPurchaseQuantity() + separator);
			sb.append(orderItem.getSalePrice() + separator);
			Date date = null;
			if(isUnionOrder){
				try {
					date = DateUtils.parserStringToDate("2012-11-01 21:00:00", "yyyy-MM-dd HH:mm:ss");
				} catch (ParseException e) {
					LOG.info("时间转化错误");
				}
				if (orderItem.getOrder().getCreateTime()
						.after(date)) {
					sb.append(ct + separator);
				}
				sb.append(euid);
			} else {
				if (Code.USER_SOURCE_QQ.equals(order.getCustomer().getSource()
						.getId())) {
					if (order.isUnite()) {
						sb.append("qqlogin1" + separator);
					} else {
						sb.append(euid + separator);
						sb.append("" + separator);
					}
					sb.append("winxuan" + separator);
					sb.append(order.getCustomer().getName());
				}
			}
			sb.append("|_|");
		}
		return sb.toString();
	}
}
