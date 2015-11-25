package com.winxuan.ec.admin.controller.groupshop;

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
public class GroupShopForm {

	
	private String companyName;
	private Date startTime;
	private Date endTime;
	private long state;
	
	public long getState() {
		return state;
	}
	public void setState(long state) {
		this.state = state;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Map<String, Object> generateQueryMap() throws ParseException{
		Map<String, Object> parameters =new AcceptHashMap<String, Object>()
		.acceptIf("startTime", this.startTime == null?null:OrderForm.getEarlyInTheDay(this.startTime), startTime != null)
		.acceptIf("endTime", this.endTime == null?null:OrderForm.getLateInTheDay(this.endTime),this.endTime!=null)
		.acceptIf("companyName", this.companyName,!StringUtils.isBlank(companyName))
		.acceptIf("state", this.state,this.state!=0);
		return parameters;
	}
}
