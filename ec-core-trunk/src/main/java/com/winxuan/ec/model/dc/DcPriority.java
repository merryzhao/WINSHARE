package com.winxuan.ec.model.dc;


/**
 * description
 * 
 * @author zhoujun	
 * @version 1.0,2014-8-15
 */
public class DcPriority {
	
	private Long location;
	private Integer number;
	private Integer priority;
	private boolean available;	
	
	public Long getLocation() {
		return location;
	}
	public void setLocation(Long location) {
		this.location = location;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}	
	
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	@Override
	public String toString() {
		return "number:"+number+",location:"+location+",priority:"+priority;
	}

}
