package com.winxuan.ec.front.controller.cps.query;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun 
 * @version 1.0,2011-11-8
 */
public class TpyQueryExtractor implements QueryExtractor {
	private static final Log LOG =LogFactory.getLog(TpyQueryExtractor.class);
	
	private static final String SPILT = "|";
	@Override
	public Map<String, Object> getQueryMap(HttpServletRequest request) throws CpsException {
		final int eight =8;
		Map<String,Object> parameter = new HashMap<String,Object>();
		String beginDateTemp= request.getParameter("st");
		String endDateTemp= request.getParameter("et");
		String codeId = request.getParameter("codeid");
		String orderId = request.getParameter("orderid");
		String cpsUserId = request.getParameter("userid");
		String processStatus = request.getParameter("zhuangtai");
		if(StringUtils.isBlank(orderId)){
			if(StringUtils.isBlank(beginDateTemp)){
				LOG.info("tpyQueryExtractor error:开始时间格80正确格式 例:st=20111220&et=20111221");
				throw new CpsException("error:开始时间格80正确格式 例:st=20111220&et=20111221");
			}
			if(StringUtils.isBlank(endDateTemp)){
				LOG.info("tpyQueryExtractor error:开始时间格80正确格式 例:st=20111220&et=20111221");
				throw new CpsException("error:结80式错误，正确格式80st=20111220&et=20111221");
			}
		}else{
			parameter.put("orderId",orderId);
		}
		if(!StringUtils.isBlank(beginDateTemp)){
			if(beginDateTemp.length()!=eight){
				throw new CpsException("80r:时间格式错误80式 例: st=20111220&et=20111221");
			}
		}
		if( !StringUtils.isBlank(endDateTemp)){
			if(endDateTemp.length()!=eight){
				throw new CpsException("error:80错误，正确格式 例: st=20111220&et=20111221");
			}
		}
		if(codeId == null || !codeId.equals(UnionOrder.UNION_TPY_ID)){
			throw new CpsException("error:联盟编号错误!");
		}
		if(cpsUserId != null){
			try {
				cpsUserId = URLDecoder.decode(cpsUserId,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new CpsException("error:联盟编码错误!");
			}
		} 
		parameter.put("unionUser",cpsUserId);
		parameter.put("processStatus",processStatus);		
		SimpleDateFormat dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance();
		dateFormat.applyPattern("yyyyMMdd");
		Date beginDate;
		Date endDate;
		if(beginDateTemp != null){
			try {
				beginDate = dateFormat.parse(beginDateTemp);
			} catch (ParseException e) {
				throw new CpsException("error: 联盟编码错误!");
			}
			parameter.put("beginDate",new SimpleDateFormat("yyyy-MM-dd").format(beginDate)+" 00:00:00");
		}
		if(endDateTemp != null){
			try {
				endDate = dateFormat.parse(endDateTemp);
			} catch (ParseException e) {
				throw new CpsException("error:联 盟编码错误!");
			}
			parameter.put("endDate",new SimpleDateFormat("yyyy-MM-dd").format(endDate)+" 23:59:59");
		}
		Map<String,Object> queryParameter = getSearchMap(parameter);
		queryParameter.put("unionId", UnionOrder.UNION_TPY);
		return queryParameter;
	}


	@Override
	public String generateResult(List<UnionOrder> unionOrders ,Map<String,Object> queryMap) {
			if (unionOrders == null || unionOrders.size() == 0) {
				return null;
			} else {
				StringBuilder sb = new StringBuilder();
				for(UnionOrder unionOrder : unionOrders){
					Order order = unionOrder.getOrder();
					sb.append(order.getId());
					sb.append(SPILT);
					Long orderState = order.getProcessStatus().getId();
					//退货
					if(order.isReturnOrder()){
						sb.append("d");
					} else {
						// 发货
						if (orderState.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED) || orderState.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)
								|| orderState.equals(Code.ORDER_PROCESS_STATUS_COMPLETED)) {
							sb.append("a");
						}
						// 取消
					else if (orderState
							.equals(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL)
							|| orderState
									.equals(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL)
							|| orderState
									.equals(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL)
							|| orderState
									.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL)) {
							sb.append("b");
						} else {
							sb.append("c");
						}
					}

					sb.append(SPILT);
					sb.append(order.getActualMoney());
					sb.append(SPILT);
					sb.append(getDateformat().format(unionOrder.getCreateDate()));
					sb.append(SPILT);
					String cookieInfo = unionOrder.getCookieInfo();
					try {
						cookieInfo = URLDecoder.decode(cookieInfo, "utf-8");
					} catch (UnsupportedEncodingException e) {
						LOG.info("--tpy Error:连接参数错误，请咨询网站负责人");
					}
					sb.append(cookieInfo);
					sb.append(SPILT);
					sb.append(order.getActualMoney().multiply(new BigDecimal("0.08")).setScale(2,BigDecimal.ROUND_HALF_UP));
					sb.append("\n");
				}
				return sb.toString();
			}
	}
	public Map<String, Object> getSearchMap(Map<String, Object> unionParameter) throws CpsException {
		Map<String,Object> queryParameter = new HashMap<String,Object>();
		Long[] processStatusId = new Long[0];
		final int three = 3;
		final int four = 4;
		final String a = "a";
		final String b = "b";
		final String c = "c";
		String unionUserId = (String) unionParameter.get("unionUser");
		if (!StringUtils.isBlank(unionUserId)) {
			queryParameter.put("cookieInfo", unionUserId);
		}	
		String subOrderStatus = (String) unionParameter.get("processStatus");
		if (!StringUtils.isBlank(subOrderStatus)) {
			if (subOrderStatus.equals(a)) {
				processStatusId = new Long[three];
				processStatusId[0] = Code.ORDER_PROCESS_STATUS_DELIVERIED;
				processStatusId[1] = Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG;
				processStatusId[2] = Code.ORDER_PROCESS_STATUS_COMPLETED;
			} else if (subOrderStatus.equals(b)) {
				processStatusId = new Long[four];
				processStatusId[0] = Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL;
				processStatusId[1] = Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL;
				processStatusId[2] = Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL;
				processStatusId[three] = Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL;
			} else if (subOrderStatus.equals(c)) {
				processStatusId = new Long[three];
				processStatusId[0] = Code.ORDER_PROCESS_STATUS_NEW;
				processStatusId[1] = Code.ORDER_PROCESS_STATUS_WAITING_PICKING;
				processStatusId[2] = Code.ORDER_PROCESS_STATUS_PICKING;
			}
		}
		if(processStatusId.length >0){
			queryParameter.put("processStatus", processStatusId);
		}
		String beginDate =(String)unionParameter.get("beginDate");
		String endDate =(String)unionParameter.get("endDate");
		if(!StringUtils.isBlank(beginDate)){
		Date startCreateDate = null;	
		try {
			startCreateDate = getDateformat().parse(beginDate);		
		} catch (ParseException e) {
 			throw new CpsException("参数 解析错误");
		}	
		queryParameter.put("startCreateDate", startCreateDate);
		}
		if(!StringUtils.isBlank(endDate)){
			Date endCreateDate = null;
			try {
				endCreateDate = getDateformat().parse(endDate);		
			} catch (ParseException e) {
	 			throw new CpsException("参数 解析错误");
			}	
			queryParameter.put("endCreateDate", endCreateDate);
		}
		String orderId =(String)unionParameter.get("orderId");
		if(!StringUtils.isBlank(orderId)){
			queryParameter.put("orderId", orderId);
		}
		return queryParameter;
	}

	protected DateFormat getDateformat(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
}
