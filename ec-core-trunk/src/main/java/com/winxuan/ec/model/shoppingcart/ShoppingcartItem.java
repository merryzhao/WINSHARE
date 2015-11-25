/*
 * @(#)ShoppingcartItem.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.points.CustomerPointsRule;
import com.winxuan.ec.service.customer.points.CustomerPointsRuleFactory;
import com.winxuan.ec.service.customer.points.SimpleCustomerPointsRuleFatory;
import com.winxuan.ec.support.promotion.ProductGift;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
@Entity
@Table(name = "shoppingcart_item")
public class ShoppingcartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shoppingcart")
	private Shoppingcart shoppingcart;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;

	@Column
	private int quantity;
	
	@Transient
	private List<ProductGift> gifts;
	
	@Transient
	private boolean ploy=false;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Shoppingcart getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(Shoppingcart shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Product getProduct() {
		return productSale.getProduct();
	}

	public Code getStatus() {
		return productSale.getSaleStatus();
	}

	public Long getStatusCode() {
		return getStatus().getId();
	}
	
	public Long getSupplyTypeCode(){
		return getSupplyType().getId();
	}

	public BigDecimal getListPrice() {
		return getProduct().getListPrice();
	}

	public List<ProductGift> getGifts() {
		return gifts;
	}

	public void setGifts(List<ProductGift> gifts) {
		this.gifts = gifts;
	}

	public BigDecimal getSalePrice() {
		Customer customer = shoppingcart.getCustomer();
		if (customer != null && customer.getId() != null) {
			return customer.getSalePrice(productSale);
		}
		return productSale.getEffectivePrice();
	}
	
	/**
	 * 添加一条赠品信息
	 * @param ps
	 * @param number
	 */
	public void addGift(ProductSale ps,Integer number){
		if(gifts == null){
			gifts = new ArrayList<ProductGift>();
		}
		ProductGift gift = null;
		for(ProductGift pg : gifts){
			if(pg.getGift().getId().equals(ps.getId())){
				gift = pg;
				break;
			}
		}
		if(gift == null){
			gift = new ProductGift(ps,number);
		}else{
			gift.setSendNum(gift.getSendNum()+number);
		}
		gifts.add(gift);
	}
 
	public BigDecimal getSavePrice() {
		return getListPrice().subtract(getSalePrice());
	}

	public BigDecimal getTotalListPrice() {
		return getListPrice().multiply(new BigDecimal(quantity));
	}

	public BigDecimal getTotalSalePrice() {
		return getSalePrice().multiply(new BigDecimal(quantity));
	}

	public String getName() {
		return getProduct().getName();
	}

	public String getUrl() {
		return getProduct().getUrl();
	}

	public String getImageUrl() {
		return getProduct().getImageUrlFor55px();
	}

	public BigDecimal getDiscount() {
		BigDecimal listPrice = getListPrice();
		if (listPrice.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		}
		return getSalePrice().divide(listPrice, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	public int getPoints() {
		CustomerPointsRuleFactory customerPointsRuleFactory = new SimpleCustomerPointsRuleFatory();
		CustomerPointsRule customerPointsRule = customerPointsRuleFactory.createShoppingcartItemCustomerPointsRule(this);
		return customerPointsRule.getPoints();
	}
	public String getSellName() {
		return getProductSale().getEffectiveName();
	}
	public Long getProductSaleId() {
		return getProductSale().getId();
	}

	public int getAvalibleQuantity() {
		return getProductSale().getAvalibleQuantity();
	}

	public Shop getShop() {
		return productSale.getShop();
	}

	public Code getSupplyType() {
		return productSale.getSupplyType();
	}


	public boolean isPloy() {
		return ploy;
	}

	public void setPloy(boolean ploy) {
		this.ploy = ploy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ShoppingcartItem)) {
			return false;
		}
		ShoppingcartItem other = (ShoppingcartItem) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
}
