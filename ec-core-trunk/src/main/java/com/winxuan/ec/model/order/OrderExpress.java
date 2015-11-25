package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;

/**
 * 订单快递信息备份
 * @author Administrator
 *
 */
@Entity
@Table(name = "order_express")
public class OrderExpress implements Serializable {

	private static final long serialVersionUID = -314329576290809721L;
	
	private Long id;
	private String order;
	private String deliveryCode;
	private int deliveryQuantity;
	private BigDecimal deliveryListPrice;
	private String province;
	private String city;
	private Code processStatus;
	private int weight;
	private Date createTime;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "_order")
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	@Column(name = "deliverycode")
	public String getDeliveryCode() {
		return deliveryCode;
	}
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}
	
	@Column(name = "deliveryquantity")
	public int getDeliveryQuantity() {
		return deliveryQuantity;
	}
	public void setDeliveryQuantity(int deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}
	
	@Column(name = "deliverylistprice")
	public BigDecimal getDeliveryListPrice() {
		return deliveryListPrice;
	}
	public void setDeliveryListPrice(BigDecimal deliveryListPrice) {
		this.deliveryListPrice = deliveryListPrice;
	}
	
	@Column(name = "province")
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "weight")
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Column(name = "createtime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processstatus")
	public Code getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(Code processStatus) {
		this.processStatus = processStatus;
	}
	
}