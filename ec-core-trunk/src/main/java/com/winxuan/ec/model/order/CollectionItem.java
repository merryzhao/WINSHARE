/**
 * 
 */
package com.winxuan.ec.model.order;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
import com.winxuan.ec.model.product.ProductSale;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-6
 */
@Entity
@Table(name = "collection_item")
public class CollectionItem implements Serializable {

	
	private static final long serialVersionUID = -2595999586497275529L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "_order")
	private String orderId;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="orderItem")
	private OrderItem orderItem;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="productsale")
	private ProductSale productSale;
	
	/**
	 * 集货仓
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fromdc")
	private Code fromDc ;
	
	/**
	 * 目的仓
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "todc")
	private Code toDc;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
	private Code status;
	
	private int collectQuantity;
	
	private int sendQuantity;
	
	private int receiveQuantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public int getCollectQuantity() {
		return collectQuantity;
	}

	public void setCollectQuantity(int collectQuantity) {
		this.collectQuantity = collectQuantity;
	}

	public int getSendQuantity() {
		return sendQuantity;
	}

	public void setSendQuantity(int sendQuantity) {
		this.sendQuantity = sendQuantity;
	}

	public int getReceiveQuantity() {
		return receiveQuantity;
	}

	public void setReceiveQuantity(int receiveQuantity) {
		this.receiveQuantity = receiveQuantity;
	}

	public Code getFromDc() {
		return fromDc;
	}

	public void setFromDc(Code fromDc) {
		this.fromDc = fromDc;
	}

	public Code getToDc() {
		return toDc;
	}

	public void setToDc(Code toDc) {
		this.toDc = toDc;
	}

	
}
