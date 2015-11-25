package com.winxuan.ec.admin.controller.order;

import java.util.Date;

import com.winxuan.ec.model.area.Area;

/**
 * 订单发货报表-FORM
 * @author heyadong
 * @version 1.0, 2012-8-9 下午03:24:20
 */
public class OrderDeliveryReportForm {
	
	private Long[] channels;
	
	
	private Area[] areas;
	private String orders;
	
	
    private Date startDeliveryTime;
	
	private Date endDeliveryTime;

	private Long[] country;
	private Long[] province;
	private Long[] city;
	private Long[] district;
	
	//包件回填状态
	private Integer state;
	
	public Long[] getCountry() {
		return country;
	}
	public void setCountry(Long[] country) {
		this.country = country;
	}
	public Long[] getProvince() {
		return province;
	}
	public void setProvince(Long[] province) {
		this.province = province;
	}
	public Long[] getCity() {
		return city;
	}
	public void setCity(Long[] city) {
		this.city = city;
	}
	public Long[] getDistrict() {
		return district;
	}
	public void setDistrict(Long[] district) {
		this.district = district;
	}
	
	public Long[] getChannels() {
		return channels;
	}
	public void setChannels(Long[] channels) {
		this.channels = channels;
	}
	public Date getStartDeliveryTime() {
		return startDeliveryTime;
	}
	public void setStartDeliveryTime(Date startDeliveryTime) {
		this.startDeliveryTime = startDeliveryTime;
	}
	
	public Date getEndDeliveryTime() {
		return endDeliveryTime;
	}
	public void setEndDeliveryTime(Date endDeliveryTime) {
		this.endDeliveryTime = endDeliveryTime;
	}
	
	public Area[] getAreas() {
		return areas;
	}
	public void setAreas(Area[] areas) {
		this.areas = areas;
	}
	public boolean ignore(){
		return country == null && province == null && city == null && district == null;
	}
	public String getOrders() {
        return orders;
    }
    public void setOrders(String orders) {
        this.orders = orders;
    }
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

    
}
