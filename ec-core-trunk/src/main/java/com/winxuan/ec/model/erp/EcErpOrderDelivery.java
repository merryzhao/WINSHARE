package com.winxuan.ec.model.erp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ERP运单信息
 * 
 * @author yujing1
 * @version 1.0,2014-10-30
 */
@Entity
@Table(name = "erp_order_delivery")
public class EcErpOrderDelivery  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="_order")
	private String order;
	
	/**
	 * erp状态
	 */
	@Column
	private String state;
	
	/**
	 * 运输单号
	 */
	@Column(name="orderdelivery")
	private String orderDelivery;
	
	/**
	 * 承运商编号
	 */
	@Column(name="deliverycompany ")
	private Integer deliveryCompany;
	
	@Column
	private String ddlxid;
	
	/**
	 * 发货时间
	 */
	@Column(name="deliverytime")
	private Date deliveryTime;
	
	/**
	 * dc信息
	 */
	@Column
	private String dc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrderDelivery() {
		return orderDelivery;
	}

	public void setOrderDelivery(String orderDelivery) {
		this.orderDelivery = orderDelivery;
	}

	public Integer getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(Integer deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public String getDdlxid() {
		return ddlxid;
	}

	public void setDdlxid(String ddlxid) {
		this.ddlxid = ddlxid;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	@Override
	public String toString() {
		return "ErpOrderDelivery [id=" + id + ", order=" + order + ", state="
				+ state + ", orderDelivery=" + orderDelivery
				+ ", deliveryCompany=" + deliveryCompany + ", ddlxid=" + ddlxid
				+ ", deliveryTime=" + deliveryTime + ", dc=" + dc + "]";
	}
	
}
