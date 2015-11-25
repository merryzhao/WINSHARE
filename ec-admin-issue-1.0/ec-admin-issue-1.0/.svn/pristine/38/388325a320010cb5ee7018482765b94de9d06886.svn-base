package com.winxuan.ec.admin.controller.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 暂存款查询验证表单
 *
 * @author wumaojie
 * @version 1.0,2011-8-8
 */
public class AdvanceAccountFrom {

	private String orderId;

	private String customerName;

	private Date startDate;

	private Date endDate;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		if ("".equals(orderId)) {
			this.orderId = null;
		} else {
			this.orderId = orderId;
		}
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		if ("".equals(customerName)) {
			this.customerName = null;
		} else {
			this.customerName = customerName;
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		if(endDate!=null){
		//截止时间当天24:00;
		return new Date(endDate.getYear(), endDate.getMonth(), endDate.getDate()+1);
		}else{
			return null;
		}
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStartDateString() {
		if (startDate != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.format(startDate);
		}
		return null;
	}

	public String getEndDateString() {
		if (endDate != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.format(endDate);
		}
		return null;
	}

}
