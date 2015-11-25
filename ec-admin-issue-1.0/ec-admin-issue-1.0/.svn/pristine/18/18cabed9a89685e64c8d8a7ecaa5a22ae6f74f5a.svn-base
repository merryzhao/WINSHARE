package com.winxuan.ec.admin.controller.order;
/*
 * @(#)OrderQueryTrackForm.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 订单跟踪查询表单
 * 
 * @author  wumaojie
 * @version 1.0,2011-8-15
 */
public class OrderQueryTrackForm {

	public static final int THREE =3;
	//订单号
	private String orderId;
	//操作人
	private String employee;
	//注册名
	private String name;
	//类型
	private Long[] type;
	//时间选择1
	private int time1;
	//时间选择2
	private int time2;
	//时间选择3
	private int time3;
	//开始时间  1
	private Date startTime1;
	//开始时间  2
	private Date startTime2;
	//开始时间  3
	private Date startTime3;
	//结束时间  1
	private Date endTime1;
	//结束时间  2
	private Date endTime2;
	//结束时间  3
	private Date endTime3;
	//开始的订单跟踪创建日期
	private Date startTrackDate;
	//结束的订单跟踪创建日期
	private Date endTrackDate;
	//开始的下单日期
	private Date startOrderDate;
	//结束的下单日期
	private Date endOrderDate;
	//开始的发货日期
	private Date startDeliveryDate;
	//结束的发货日期
	private Date endDeliveryDate;
	
	public String getOrderId() {
		if("".equals(orderId)){
			return null;
		}
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getEmployee() {
		if("".equals(employee)){
			return null;
		}
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getName() {
		if("".equals(name)){
			return null;
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long[] getType() {
		return type;
	}
	public List<Long> getTypes() {
		if(type==null){
			return null;		
		}
		List<Long> types = new ArrayList<Long>();
		for (Long lon : type) {
			types.add(lon);
		}
		return types;
	}
	public void setType(Long[] type) {
		this.type = type;
	}
	public int getTime1() {
		return time1;
	}
	public void setTime1(int time1) {
		if(time1==1&&startTrackDate==null&&endTrackDate==null){
			startTrackDate = startTime1;
			endTrackDate = endTime1;
		}
		if(time1==2&&startOrderDate==null&&endOrderDate==null){
			startOrderDate = startTime1;
			endOrderDate = endTime1;
		}
		if(time1==THREE&&startDeliveryDate==null&&endDeliveryDate==null){
			startDeliveryDate = startTime1;
			endDeliveryDate = endTime1;
		}
		this.time1 = time1;
	}
	public int getTime2() {
		return time2;
	}
	public void setTime2(int time2) {
		if(time2==1&&startTrackDate==null&&endTrackDate==null){
			startTrackDate = startTime2;
			endTrackDate = endTime2;
		}
		if(time2==2&&startOrderDate==null&&endOrderDate==null){
			startOrderDate = startTime2;
			endOrderDate = endTime2;
		}
		if(time2==THREE&&startDeliveryDate==null&&endDeliveryDate==null){
			startDeliveryDate = startTime2;
			endDeliveryDate = endTime2;
		}
		this.time2 = time2;
	}
	public int getTime3() {
		return time3;
	}
	public void setTime3(int time3) {
		if(time3==1&&startTrackDate==null&&endTrackDate==null){
			startTrackDate = startTime3;
			endTrackDate = endTime3;
		}
		if(time3==2&&startOrderDate==null&&endOrderDate==null){
			startOrderDate = startTime3;
			endOrderDate = endTime3;
		}
		if(time3==THREE&&startDeliveryDate==null&&endDeliveryDate==null){
			startDeliveryDate = startTime3;
			endDeliveryDate = endTime3;
		}
		this.time3 = time3;
	}
	public Date getStartTime1() {
		return startTime1;
	}
	public void setStartTime1(Date startTime1) {
		this.startTime1 = startTime1;
	}
	public Date getStartTime2() {
		return startTime2;
	}
	public void setStartTime2(Date startTime2) {
		this.startTime2 = startTime2;
	}
	public Date getStartTime3() {
		return startTime3;
	}
	public void setStartTime3(Date startTime3) {
		this.startTime3 = startTime3;
	}
	public Date getEndTime1() {
		return endTime1;
	}
	public void setEndTime1(Date endTime1) {
		this.endTime1 = endTime1;
	}
	public Date getEndTime2() {
		return endTime2;
	}
	public void setEndTime2(Date endTime2) {
		this.endTime2 = endTime2;
	}
	public Date getEndTime3() {
		return endTime3;
	}
	public void setEndTime3(Date endTime3) {
		this.endTime3 = endTime3;
	}
	public Date getStartTrackDate() {
		return startTrackDate;
	}
	public void setStartTrackDate(Date startTrackDate) {
		this.startTrackDate = startTrackDate;
	}
	public Date getEndTrackDate() {
		return endTrackDate;
	}
	public void setEndTrackDate(Date endTrackDate) {
		this.endTrackDate = endTrackDate;
	}
	public Date getStartOrderDate() {
		return startOrderDate;
	}
	public void setStartOrderDate(Date startOrderDate) {
		this.startOrderDate = startOrderDate;
	}
	public Date getEndOrderDate() {
		return endOrderDate;
	}
	public void setEndOrderDate(Date endOrderDate) {
		this.endOrderDate = endOrderDate;
	}
	public Date getStartDeliveryDate() {
		return startDeliveryDate;
	}
	public void setStartDeliveryDate(Date startDeliveryDate) {
		this.startDeliveryDate = startDeliveryDate;
	}
	public Date getEndDeliveryDate() {
		return endDeliveryDate;
	}
	public void setEndDeliveryDate(Date endDeliveryDate) {
		this.endDeliveryDate = endDeliveryDate;
	}
	
}

