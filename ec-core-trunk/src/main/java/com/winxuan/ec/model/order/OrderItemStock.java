package com.winxuan.ec.model.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.product.ProductSale;


/**
 * 订单项库存信息
 * @author: HideHai 
 * @date: 2013-10-25
 */
@Entity
@Table(name = "order_item_stock")
public class OrderItemStock implements Serializable{

	private static final long serialVersionUID = 5602120033666721417L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="_order")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productsale")
	private ProductSale productSale; 

	@Column
	private int stock;

	@Column(name="stockinfo")
	private String stockInfo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getStockInfo() {
		return stockInfo;
	}

	public void setStockInfo(String stockInfo) {
		this.stockInfo = stockInfo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}



}

