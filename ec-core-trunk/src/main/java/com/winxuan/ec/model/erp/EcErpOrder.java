package com.winxuan.ec.model.erp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ERP订单
 * 
 * @author yujing1
 * @version 1.0,2014-10-30
 */
@Entity
@Table(name = "erp_order")
public class EcErpOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 未处理
	 */
	public static final Integer NOT_DEAL = 0;
	/**
	 * 已处理
	 */
	public static final Integer AL_DEAL = 1;

	@Id
	@Column(name = "_order")
	private String order;

	/**
	 * erp状态
	 */
	@Column
	private String state;

	/**
	 * 运输单号
	 */
	@Column(name = "orderdelivery")
	private String orderDelivery;

	/**
	 * 承运商编号
	 */
	@Column(name = "deliveryCompany ")
	private Integer deliveryCompany;

	@Column
	private String ddlxid;

	/**
	 * 发货时间
	 */
	@Column(name = "deliverytime")
	private Date deliveryTime;

	/**
	 * 退货方式
	 */
	@Column
	private String gkthfsid;

	/**
	 * dc信息
	 */
	@Column
	private String dc;

	@Column
	private Integer status;

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

	public String getGkthfsid() {
		return gkthfsid;
	}

	public void setGkthfsid(String gkthfsid) {
		this.gkthfsid = gkthfsid;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	@Override
	public String toString() {
		return "ErpOrder [order=" + order + ", state=" + state
				+ ", orderDelivery=" + orderDelivery + ", deliveryCompany="
				+ deliveryCompany + ", ddlxid=" + ddlxid + ", deliveryTime="
				+ deliveryTime + ", gkthfsid=" + gkthfsid + ", dc=" + dc + "]";
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isDeal() {
		if (NOT_DEAL.equals(this.status)) {
			return false;
		}
		return true;
	}
}
