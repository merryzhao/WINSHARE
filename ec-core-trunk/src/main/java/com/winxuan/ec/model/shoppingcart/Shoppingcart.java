/*
 * @(#)Shoppingcart.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.promotion.PromotionPrice;
import com.winxuan.framework.util.BigDecimalUtils;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
@Entity
@Table(name = "shoppingcart")
public class Shoppingcart {

	@Id
	@GeneratedValue(generator = "categoryGenerator")
	@GenericGenerator(name = "categoryGenerator", strategy = "assigned")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "usetime")
	private Date useTime;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingcart", targetEntity = ShoppingcartItem.class)
	@OrderBy("id desc")
	private Set<ShoppingcartItem> itemList;
	
	@Transient
	private List<ShoppingcartProm> proms;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Set<ShoppingcartItem> getItemList() {
		return itemList;
	}

	public void setItemList(Set<ShoppingcartItem> itemList) {
		this.itemList = itemList;
	}

	public List<ShoppingcartProm> getProms() {
		return proms;
	}

	public void setProms(List<ShoppingcartProm> proms) {
		this.proms = proms;
	}

	/**
	 * 根据订单拆分逻辑拆分订单：按卖家和商品供应类型
	 * 
	 * @return
	 */
	public Map<ShoppingcartSeparator, List<ShoppingcartItem>> getGroupItems() {
		if (isEmpty()) {
			return null;
		}
		Map<ShoppingcartSeparator, List<ShoppingcartItem>> result = new LinkedHashMap<ShoppingcartSeparator, List<ShoppingcartItem>>();
		for (ShoppingcartItem item : itemList) {
			ShoppingcartSeparator separator = new ShoppingcartSeparator(
					item.getShop(), item.getSupplyType());
			List<ShoppingcartItem> items = result.get(separator);
			boolean newSeparator = false;
			if (items == null) {
				items = new ArrayList<ShoppingcartItem>();
				newSeparator = true;
			}
			items.add(item);
			if (newSeparator) {
				result.put(separator, items);
			}
		}
		return result;
	}

	/**
	 * 得到拆分的销售价格
	 * 
	 * @return
	 */
	public Map<ShoppingcartSeparator, BigDecimal> getGroupSalePrice() {
		if (isEmpty()) {
			return null;
		}
		Map<ShoppingcartSeparator, List<ShoppingcartItem>> groupItems = getGroupItems();
		if (groupItems != null && !groupItems.isEmpty()) {
			Map<ShoppingcartSeparator, BigDecimal> groupSalePrice = new LinkedHashMap<ShoppingcartSeparator, BigDecimal>();
			for (Map.Entry<ShoppingcartSeparator, List<ShoppingcartItem>> entry : groupItems
					.entrySet()) {
				List<ShoppingcartItem> items = entry.getValue();
				BigDecimal itemPrice = BigDecimal.ZERO;
				for (ShoppingcartItem item : items) {
					itemPrice = itemPrice.add(item.getTotalSalePrice());
				}
				groupSalePrice.put(entry.getKey(), itemPrice);
			}
			return groupSalePrice;
		}
		return null;
	}

	/**
	 * 得到拆分的码洋价格
	 * 
	 * @return
	 */
	public Map<ShoppingcartSeparator, BigDecimal> getGroupListPrice() {
		if (isEmpty()) {
			return null;
		}
		Map<ShoppingcartSeparator, List<ShoppingcartItem>> groupItems = getGroupItems();
		if (groupItems != null && !groupItems.isEmpty()) {
			Map<ShoppingcartSeparator, BigDecimal> groupListPrice = new LinkedHashMap<ShoppingcartSeparator, BigDecimal>();
			for (Map.Entry<ShoppingcartSeparator, List<ShoppingcartItem>> entry : groupItems
					.entrySet()) {
				List<ShoppingcartItem> items = entry.getValue();
				BigDecimal itemPrice = BigDecimal.ZERO;
				for (ShoppingcartItem item : items) {
					itemPrice = itemPrice.add(item.getTotalListPrice());
				}
				groupListPrice.put(entry.getKey(), itemPrice);
			}
			return groupListPrice;
		}
		return null;
	}
	
	/**
	 * 分组获得购物车的促销信息
	 * @return
	 */
	public Map<ShoppingcartSeparator, List<ShoppingcartProm>> getGroupProms() {
		if (isEmpty() || CollectionUtils.isEmpty(proms)) {
			return null;
		}
		Map<ShoppingcartSeparator, List<ShoppingcartProm>> result = new LinkedHashMap<ShoppingcartSeparator, List<ShoppingcartProm>>();
		for (ShoppingcartProm prom : proms) {
			ShoppingcartSeparator separator = new ShoppingcartSeparator(
					new Shop(prom.getShopId()), new Code(prom.getSupplyTypeCode()));
			List<ShoppingcartProm> items = result.get(separator);
			boolean newSeparator = false;
			if (items == null) {
				items = new ArrayList<ShoppingcartProm>();
				newSeparator = true;
			}
			items.add(prom);
			if (newSeparator) {
				result.put(separator, items);
			}
		}
		return result;
	}
	
	/**
	 * 获取购物车的优惠信息
	 */
	
	public BigDecimal getSaveMoney(){
		BigDecimal saveMoney=BigDecimalUtils.ZERO;
		if(!isEmpty()){
			if(proms!=null){
				for(ShoppingcartProm prom:proms){
					if(prom!=null){
						PromotionPrice price=prom.getPromotionPrice();
						if(price!=null && Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT.equals(price.getPromType())){
							saveMoney=saveMoney.add(prom.getPromotionPrice().getSaveMoney());
						}
						if(price!=null && Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT.equals(price.getPromType())){
							saveMoney=saveMoney.add(prom.getPromotionPrice().getSaveMoney());
						}
					}
				}
			}
		}
		return saveMoney;
	}
	/**
	 * 移除购物车商品
	 * 
	 * @param item
	 * @return
	 */
	public boolean remove(ShoppingcartItem item) {
		return isEmpty() ? false : itemList.remove(item);
	}

	public boolean hasItem(ShoppingcartItem item) {
		return isEmpty() ? false : itemList.contains(item);
	}

	/**
	 * 根据productSale得到shoppingcartItem
	 * 
	 * @param productSale
	 * @return
	 */
	public ShoppingcartItem getItem(ProductSale productSale) {
		if (!isEmpty()) {
			for (ShoppingcartItem item : itemList) {
				if (item.getProductSale().getId().equals(productSale.getId())) {
					return item;
				}
			}
		}
		return null;
	}

	/**
	 * 移除购物车商品
	 * 
	 * @param productSale
	 * @return
	 */
	public boolean remove(ProductSale productSale) {
		ShoppingcartItem item = getItem(productSale);
		if (item != null) {
			return remove(item);
		}
		return false;
	}

	public void add(ShoppingcartItem item) {
		if (itemList == null) {
			itemList = new HashSet<ShoppingcartItem>();
		}
		item.setShoppingcart(this);
		itemList.add(item);
	}
	public void add(Set<ShoppingcartItem> items) {
		if(items == null || items.size() == 0){
			return;
		}
		Iterator<ShoppingcartItem> it = items.iterator();
		while(it.hasNext()){
			add(it.next());
		}
	}
	
	/**
	 * 添加一个购物车级别的优惠活动信息
	 * @param prom
	 */
	public void add(ShoppingcartProm prom) {
		if (proms == null) {
			proms = new ArrayList<ShoppingcartProm>();
		}
		proms.add(prom);
	}

	/**
	 * 计算总数
	 * 
	 * @return
	 */
	public int getCount() {
		int count = 0;
		if (!isEmpty()) {
			for (ShoppingcartItem item : itemList) {
				count += item.getQuantity();
			}
		}
		return count;
	}
	
	/**
	 * 计算总品种
	 * 
	 * @return
	 */
	public int getKind() {
		return isEmpty() ? 0 : itemList.size();
	}

	public BigDecimal getSalePrice() {
		BigDecimal salePrice = BigDecimalUtils.ZERO;
		if (!isEmpty()) {
			for (ShoppingcartItem item : itemList) {
				salePrice = salePrice.add(item.getTotalSalePrice());
			}
		}
		return salePrice;
	}

	public BigDecimal getListPrice() {
		BigDecimal listPrice = BigDecimalUtils.ZERO;
		if (!isEmpty()) {
			for (ShoppingcartItem item : itemList) {
				listPrice = listPrice.add(item.getTotalListPrice());
			}
		}
		return listPrice;
	}
	
	public boolean isEmpty() {
		return itemList == null || itemList.isEmpty();
	}

	public boolean isSplited() {
		if (isEmpty()) {
			return false;
		}
		Shop shop = null;
		Code supplyType = null;
		for (ShoppingcartItem item : itemList) {
			if (shop == null) {
				shop = item.getShop();
				supplyType = item.getSupplyType();
				continue;
			}
			if (!shop.equals(item.getShop())
					|| !supplyType.equals(item.getSupplyType())) {
				return true;
			}
		}
		return false;
	}
}
