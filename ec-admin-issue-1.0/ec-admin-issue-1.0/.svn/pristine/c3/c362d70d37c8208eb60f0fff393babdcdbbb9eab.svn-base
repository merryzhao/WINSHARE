package com.winxuan.ec.admin.controller.returnorder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @description:
 * @Copyright: 四川文轩在线电子商务有限公司
 * @author: liming0
 * @version: 1.0
 * @date: 2015年1月9日 下午2:01:50
 */
public class ReturnOrderPackageQueryForm {
	
	//关联订单号
	private String orderid;
	//包件状态
	private Long status;
	//包件运单号
	private String expressid;
	//发件人
	private String customer;
	//发件人电话
	private String phone;
	//渠道退货单号
	private String returnid;
	//录入开始时间
	private String starttime;
	//录入结束时间
	private String endtime;
	
	// 时间格式
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getReturnid() {
		return returnid;
	}

	public void setReturnid(String returnid) {
		this.returnid = returnid;
	}

	public SimpleDateFormat getDateformat() {
		return dateformat;
	}

	public void setDateformat(SimpleDateFormat dateformat) {
		this.dateformat = dateformat;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getExpressid() {
		return expressid;
	}

	public void setExpressid(String expressid) {
		this.expressid = expressid;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	/**
	 * 返回开始时间
	 * @return
	 */
	public Date getStartDateTime() {
		if(StringUtils.isNotBlank(starttime)){
			try {
				Date date = dateformat.parse(starttime);
				return date;
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	/**
	 * 返回截止时间
	 * @return
	 */
	public Date getEndDateTime() {
		if(StringUtils.isNotBlank(endtime)){
			try {
				Date date = dateformat.parse(endtime);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				return calendar.getTime();
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 返回关联订单号
	 * @return
	 */
	public String getOrderidOrNull(){
		if(orderid == null || "".equals(orderid)){
			return null;
		}
		return orderid;
	}
	
	/**
	 * 返回运输单号
	 * @return
	 */
	public String getExpresidOrNull(){
		if(expressid == null || "".equals(expressid)){
			return null;
		}
		return expressid;
	}
	
	/**
	 * 返回发件人
	 * @return
	 */
	public String getCustomerOrNull(){
		if(customer == null || "".equals(customer)){
			return null;
		}
		return customer;
	}
	
	/**
	 * 返回发件人电话
	 * @return
	 */
	public String getPhoneOrNull(){
		if(phone == null || "".equals(phone)){
			return null;
		}
		return phone;
	}
	
	/**
	 * 返回渠道退货单号
	 * @return
	 */
	public String getReturnidOrNull(){
		if(returnid == null || "".equals(returnid)){
			return null;
		}
		return returnid;
	}
	
}
