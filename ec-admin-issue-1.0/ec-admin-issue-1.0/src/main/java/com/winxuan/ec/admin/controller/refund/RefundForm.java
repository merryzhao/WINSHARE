package com.winxuan.ec.admin.controller.refund;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.winxuan.framework.util.AcceptHashMap;
/**
 * 
 * @author youwen
 *
 */
public class RefundForm {
	public static final String SHORT_DATE_FORMAT_STR="yyyy-MM-dd";
	public static final String LONG_DATE_FORMAT_STR="yyyy-MM-dd HH:mm:ss";
	private static final DateFormat LONG_DATE_FORMAT=new SimpleDateFormat(LONG_DATE_FORMAT_STR);
	private static final String EARLY_TIME="00:00:00";
	private static final String LATE_TIME="23:59:59";
	private Long paymentId;
	private String outerId;
	private String orderIds;
	private short sort;
	private Long[] status;
	private String startTime;
	private String endTime;

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public short getSort() {
		return sort;
	}

	public void setSort(short sort) {
		this.sort = sort;
	}

	public Long[] getStatus() {
		return status;
	}

	public void setStatus(Long[] status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Map<String, Object> getRefundParams() throws ParseException{
		Map<String, Object> parameters = new AcceptHashMap<String, Object>()
				.acceptIf("paymentId",this.paymentId, this.paymentId.longValue()!=0)
				.accept("orderIds",getOrderIdList(this.orderIds))
				.acceptIf("status",status,status!=null)
				.acceptIf("startTime", StringUtils.isBlank(startTime) ? null:getEarlyInTheDay(this.startTime),startTime != null)
				.acceptIf("endTime",  StringUtils.isBlank(endTime) ? null:getLateInTheDay(this.endTime), endTime != null);
		return parameters;
	}
	
	private List<String> getOrderIdList(String orderIds){
		if(StringUtils.isBlank(orderIds)){
			return null;
		}
		List<String> orderIdList = new ArrayList<String>();
		String[] ids = orderIds.split("\r\n");
		for(String id : ids){
			orderIdList.add(id);
		}
		return orderIdList;
	}
	
	/**
	 * 得到某个日期在这一天中时间最早的日期对象
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getEarlyInTheDay(String date) throws ParseException{
		String dateString=date+" "+EARLY_TIME;
		return LONG_DATE_FORMAT.parse(dateString);
	}
	
	/**
	 * 得到某个日期在这一天中时间最晚的日期对象
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLateInTheDay(String date) throws ParseException{
		String dateString=date+" "+LATE_TIME;
		return LONG_DATE_FORMAT.parse(dateString);
	}

}
