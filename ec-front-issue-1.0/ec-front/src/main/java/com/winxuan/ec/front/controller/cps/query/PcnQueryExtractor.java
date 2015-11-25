package com.winxuan.ec.front.controller.cps.query;

import java.math.BigDecimal;
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
import com.winxuan.framework.util.DateUtils;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-9
 */
public class PcnQueryExtractor implements QueryExtractor{

	@Override
	public Map<String, Object> getQueryMap(HttpServletRequest request)
			throws CpsException {
		final int hundred =100;
		final int ten = 10 ;
		String codeid = request.getParameter("codeid");
		String dtn = request.getParameter("dtn");
		String state = request.getParameter("state");

		if(StringUtils.isBlank(codeid) || !("183545").equals(codeid)) {
			throw new CpsException("请确认codeid传参正确..");
		}
		
		if(StringUtils.isBlank(dtn) || dtn.length() > hundred){
			throw new CpsException("请确认dtn传参正确..");
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
	
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dtn",dtn);
		map.put("beginDate",beginDate);
		map.put("endDate",endDate);
		map.put("state",state);
		map.put("unionId", UnionOrder.UNION_PCN);
		Map<String,Object> queryParameter = getSearchMap(map);
		return queryParameter;
	}

	@Override
	public String generateResult(List<UnionOrder> unionOrders,Map<String,Object> queryMap) {
		if (unionOrders == null || unionOrders.size() == 0) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			final String separator = "|";
			final int sixty = 60;
			final BigDecimal commissionRate = new BigDecimal("0.08");
			for(UnionOrder unionOrder : unionOrders){
				Order order = unionOrder.getOrder();
				String date = getDateformat().format(
						unionOrder.getCreateDate());
				sb.append(order.getId());
				sb.append(separator);
				sb.append(unionOrder.getCookieInfo());
				sb.append(separator);
				Long orderState = order.getProcessStatus().getId();
				//退货		
					if (order.isDeliveried()) {
						sb.append("VALID");
					} else if (orderState == Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL ||
							orderState == Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL||
							orderState == Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL ||
							orderState == Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL) {
						sb.append("INVALID");
					} else {
						sb.append("WAIT");
					}
				sb.append(separator);
				sb.append(order.getActualMoney());
				sb.append(separator);
				long mutDay = DateUtils.subtractNowDay(unionOrder.getCreateDate());
				if ( mutDay > sixty) {
					//非百货商品价格
					BigDecimal winsharePrice = BigDecimal.ZERO;
					for(OrderItem orderItem : order.getItemList()){
						if(orderItem.getProductSale().getProduct().getSort().getId() != Code.PRODUCT_SORT_MERCHANDISE){
							winsharePrice.add(orderItem.getSalePrice()
											);
						}
					}
					sb.append(winsharePrice.multiply(commissionRate)
							.setScale(2, BigDecimal.ROUND_HALF_UP));
				} else {
					sb.append(BigDecimal.ZERO);
				}
				sb.append("|");
				sb.append(date);
				sb.append("\n");
			}
			return sb.toString();
		}
	}
	public Map<String, Object> getSearchMap(Map<String, Object> unionParameter) throws CpsException {
		Map<String,Object> queryParameter = new HashMap<String,Object>();
		Long[] processStatusId = new Long[0];
		String cookieInfo = (String) unionParameter.get("dtn");
		if (cookieInfo != null) {
			queryParameter.put("cookieInfo", cookieInfo);
		}
		String subOrderStatus = (String) unionParameter.get("state");
		final int three = 3;
		final int four = 4;
		if (subOrderStatus != null) {
			if ("VALID".equals(subOrderStatus)) {
				processStatusId = new Long[three];
				processStatusId[0] = Code.ORDER_PROCESS_STATUS_DELIVERIED;
				processStatusId[1] = Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG;
				processStatusId[2] = Code.ORDER_PROCESS_STATUS_COMPLETED;

			} else if ("INVALID".equals(subOrderStatus)) {		
				processStatusId = new Long[four];
				processStatusId[0] = Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL;
				processStatusId[1] = Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL;
				processStatusId[2] = Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL;
				processStatusId[three] = Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL;
			} else if ("WAIT".equals(subOrderStatus)) {
				processStatusId = new Long[three];
				processStatusId[0] = Code.ORDER_PROCESS_STATUS_NEW;
				processStatusId[1] = Code.ORDER_PROCESS_STATUS_WAITING_PICKING;
				processStatusId[2] = Code.ORDER_PROCESS_STATUS_PICKING;
			}
		}
		Date startCreateDate = null;
		Date endCreateDate = null;
		try {
			startCreateDate = getDateformat().parse((String)unionParameter.get("beginDate"));	
			endCreateDate = getDateformat().parse((String)unionParameter.get("endDate"));
		} catch (ParseException e) {
 			throw new CpsException("参数解析错误");
		}
		queryParameter.put("startCreateDate", startCreateDate);
		queryParameter.put("endCreateDate",  endCreateDate);
		if(processStatusId.length >0){
			queryParameter.put("processStatus", processStatusId);
		}
		return queryParameter;
	}
	
	protected DateFormat getDateformat(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

}
