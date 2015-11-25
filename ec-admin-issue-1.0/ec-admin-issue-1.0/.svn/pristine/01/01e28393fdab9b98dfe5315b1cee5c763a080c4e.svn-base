package com.winxuan.ec.admin.controller.complaint;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.admin.controller.order.OrderForm;
import com.winxuan.framework.util.AcceptHashMap;

/**
 * 
 * @author fred
 *
 */
public class ComplainForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	private String orderId;
	private String customerName;
	private long state;
	

	private Date startCreateTime;
	private Date endCreateTime;
	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getCustomer() {
		return customerName;
	}

	public void setCustomer(String customerName) {
		this.customerName = customerName;
	}
	



	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}
	public Map<String, Object> generateQueryMap() throws ParseException{
		Map<String, Object> parameters =new AcceptHashMap<String, Object>()
		.acceptIf("startCreateTime", this.startCreateTime == null?null:OrderForm.getEarlyInTheDay(this.startCreateTime), startCreateTime != null)
		.acceptIf("endCreateTime", this.endCreateTime == null?null:OrderForm.getLateInTheDay(this.endCreateTime),this.endCreateTime!=null)
		.acceptIf("orderId", this.orderId,!StringUtils.isBlank(this.orderId))
		.acceptIf("customerName", this.customerName,!StringUtils.isBlank(customerName))
		.acceptIf("state", this.state,this.state!=0);
		
		return parameters;
		
		
	}
	
}
