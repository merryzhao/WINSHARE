/*
 * @(#)OrderItem.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;

import com.winxuan.ec.model.bill.BillItemStatistics;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.services.pss.model.vo.ProductSaleStockVo;

/**
 * 订单商品项
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name="order_item")
public class OrderItem implements Serializable{

	private static final long serialVersionUID = -7445783693044492368L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="_order")
	private Order order;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productsale")
	private ProductSale productSale;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop")
    private Shop shop;
    
    @Column(name="listprice")
	private BigDecimal listPrice;
    
    @Column(name="saleprice")
	private BigDecimal salePrice;
    
    @Column(name="balanceprice")
    private BigDecimal balancePrice;
    
    @Column(name="purchasequantity")
	private int purchaseQuantity;
    
    @Column(name="deliveryquantity")
	private int deliveryQuantity;
    
    @Column(name="returnquantity")
	private int returnQuantity;
    
    @Column(name="stockQuantity")
    private int stockQuantity;
    
    @Column(name="saleQuantity")
    private int saleQuantity;
    
    @Column(name="outofstockquantity")
    private int outOfStockQuantity;
    
    @Column
    private int points;
    
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private BillItemStatistics billItemStatistics;
    
    @OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
    private OrderItemStock orderItemStock;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderItem", targetEntity = CollectionItem.class)
    @OrderBy("id")
    private Set<CollectionItem> collectionItemList;
    
    @Column
    private boolean virtual;
    
    @Transient
    private Set<ProductSaleStockVo> productSaleStockVos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public int getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(int deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public int getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(int returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public int getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(int saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isVirtual() {
		return virtual;
	}

	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}

	public BigDecimal getTotalSalePrice() {
		return getSalePrice().multiply(new BigDecimal(purchaseQuantity));
	}
	
	public BigDecimal getTotalListPrice() {
		return getListPrice().multiply(new BigDecimal(purchaseQuantity));
	}

	public BigDecimal getBalancePrice() {
		return balancePrice;
	}

	public void setBalancePrice(BigDecimal balancePrice) {
		this.balancePrice = balancePrice;
	}
	
	public BigDecimal getDiscount(){
		return salePrice.divide(listPrice, 2, RoundingMode.HALF_UP);
	}

	public BigDecimal getTotalBalancePrice() {
		return getBalancePrice().multiply(new BigDecimal(purchaseQuantity));
	}
	
	public BigDecimal getDeliveryBalancePrice() {
		return getBalancePrice().multiply(new BigDecimal(deliveryQuantity));
	}
	public BigDecimal getItemEffiveMoney(){
		return getDeliveryBalancePrice().subtract(getBalancePrice().multiply(new BigDecimal(returnQuantity)));
	}
	
	public int getCanReturnQuantity(){
		return getDeliveryQuantity() - getReturnQuantity();
	}
	public int getOutOfStockQuantity() {
		return outOfStockQuantity;
	}
	public void setOutOfStockQuantity(int outOfStockQuantity) {
		this.outOfStockQuantity = outOfStockQuantity;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BillItemStatistics getBillItemStatistics() {
		return billItemStatistics;
	}

	public void setBillItemStatistics(BillItemStatistics billItemStatistics) {
		this.billItemStatistics = billItemStatistics;
	}
	
	public int getAvailableQuantity() {
		if(this.getBillItemStatistics()!=null) {
			return this.getBillItemStatistics().getAvailableQuantity();
		}
		return this.getDeliveryQuantity();
	}
	
	public BigDecimal getAvailablePrice() {
		return this.getSalePrice().multiply(new BigDecimal(this.getAvailableQuantity()));
	}

	public OrderItemStock getOrderItemStock() {
		return orderItemStock;
	}

	public void setOrderItemStock(OrderItemStock orderItemStock) {
		this.orderItemStock = orderItemStock;
	}

	public Set<CollectionItem> getCollectionItemList() {
		return collectionItemList;
	}

	public void setCollectionItemList(Set<CollectionItem> collectionItemList) {
		this.collectionItemList = collectionItemList;
	}

	public Set<ProductSaleStockVo> getProductSaleStockVos() {
		return productSaleStockVos;
	}

	public void setProductSaleStockVos(Set<ProductSaleStockVo> productSaleStockVos) {
		this.productSaleStockVos = productSaleStockVos;
	}

	public ProductSaleStockVo getProductSaleStockVo(long dc){
		if(CollectionUtils.isNotEmpty(productSaleStockVos)){
			for(ProductSaleStockVo pssv : productSaleStockVos){
				if(dc == pssv.getDc()){
					return pssv;
				}
			}
		}
		return null;
	}
	
	public int getProductSaleStockVoAvailableQuantity(long dc){
		ProductSaleStockVo pssv = getProductSaleStockVo(dc);
		return null == pssv ? 0 : pssv.getAvailableQuantity();
	}
	
}
