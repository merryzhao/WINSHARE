package com.winxuan.ec.model.erp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ERP订单明细
 * 
 * @author yujing1
 * @version 1.0,2014-10-30
 */
@Entity
@Table(name = "erp_order_item")
public class EcErpOrderItem implements Serializable{
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
	 * 主商品id
	 */
	@Column
	private Integer merchid;
	
	/**
	 * 发货数量
	 */
	@Column(name="deliveryquantity")
	private Integer deliveryQuantity;
	
	/**
	 * 报缺数量
	 */
	@Column(name="outofstockquantity")
	private Integer outOfStockQuantity;

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

	public Integer getMerchid() {
		return merchid;
	}

	public void setMerchid(Integer merchid) {
		this.merchid = merchid;
	}

	public Integer getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public Integer getOutOfStockQuantity() {
		return outOfStockQuantity;
	}

	public void setOutOfStockQuantity(Integer outOfStockQuantity) {
		this.outOfStockQuantity = outOfStockQuantity;
	}

	@Override
	public String toString() {
		return "ErpOrderItem [id=" + id + ", order=" + order + ", merchid="
				+ merchid + ", deliveryQuantity=" + deliveryQuantity
				+ ", outOfStockQuantity=" + outOfStockQuantity + "]";
	}
	
	
}
