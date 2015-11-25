/*
 * @(#)ShoppingcartItem.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.shoppingcart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductExtend;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;

/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-16 下午4:55:17  --!
 * @description:
 ********************************
 */
@Entity
@Table(name = "ebook_shoppingcart_item")
public class EbookShoppingcartItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8965348674041538416L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ebookshoppingcart")
	private EbookShoppingcart ebookShoppingCart;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;

	@Column
	private int quantity;
	
	public boolean getSupportIphone() {
		Set<ProductExtend> productExtends = productSale.getProduct().getExtendList();
		if(CollectionUtils.isNotEmpty(productExtends)){
			for(ProductExtend extend : productExtends){
				if("1".equals(extend.getValue()) && extend.getProductMeta().getId().longValue() == 114L){//是否支持epub
					return true;
				}
			}
		}
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EbookShoppingcart getEbookShoppingCart() {
		return ebookShoppingCart;
	}

	public void setEbookShoppingCart(EbookShoppingcart ebookShoppingCart) {
		this.ebookShoppingCart = ebookShoppingCart;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public BigDecimal getTotalSalePrice() {
		return getSalePrice().multiply(new BigDecimal(quantity));
	}
	public BigDecimal getSalePrice() {
		Customer customer = ebookShoppingCart.getCustomer();
		if (customer != null && customer.getId() != null) {
			return customer.getSalePrice(productSale);
		}
		return productSale.getEffectivePrice();
	}
	public String getUrl() {
		return this.getProductSale().getEBook().getUrl();
	}
  
	public Product getProduct() {
		return productSale.getProduct();
	}
	public String getName() {
		return getProduct().getName();
	}
	
	public String getSellName() {
		return  getProductSale().getEffectiveName();
	}
	public String getImageUrl() {
		return getProduct().getImageUrlFor55px();
	}
	public Long getProductSaleId() {
		return getProductSale().getId();
	}
}
