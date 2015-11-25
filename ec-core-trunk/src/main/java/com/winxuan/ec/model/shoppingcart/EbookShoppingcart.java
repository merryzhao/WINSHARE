/*
 * @(#)Shoppingcart.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.shoppingcart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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

import org.hibernate.annotations.GenericGenerator;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-16 下午4:55:13  --!
 * @description:
 ********************************
 */
@Entity
@Table(name = "ebook_shoppingcart")
public class EbookShoppingcart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9005878062928327613L;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ebookShoppingCart", targetEntity = EbookShoppingcartItem.class)
	@OrderBy("id desc")
	private Set<EbookShoppingcartItem> itemList;

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

	public Set<EbookShoppingcartItem> getItemList() {
		return itemList;
	}

	public void setItemList(Set<EbookShoppingcartItem> itemList) {
		this.itemList = itemList;
	}

	
	public boolean isEmpty() {
		return itemList == null || itemList.isEmpty();
	}
	
	/**
	 * 移除购物车商品
	 * 
	 * @param item
	 * @return
	 */
	public boolean remove(EbookShoppingcartItem item) {
		return isEmpty() ? false : itemList.remove(item);
	}

	public boolean hasItem(EbookShoppingcartItem item) {
		return isEmpty() ? false : itemList.contains(item);
	}

	/**
	 * 根据productSale得到shoppingcartItem
	 * 
	 * @param productSale
	 * @return
	 */
	public EbookShoppingcartItem getItem(ProductSale productSale) {
		if (!isEmpty()) {
			for (EbookShoppingcartItem item : itemList) {
				if (item.getProductSale().getId().equals(productSale.getId())) {
					return item;
				}
			}
		}
		return null;
	}
	
	/**
	 * 总价
	 * @return
	 */
	public BigDecimal getCountPrice(){
		BigDecimal result = new BigDecimal(MagicNumber.ZERO);
		if (!isEmpty()) {
			for (EbookShoppingcartItem item : itemList) {
				result =result.add(item.getProductSale().getEffectivePrice());
			}
		}
		return result;
	}
	
	/**
	 * 计算总数
	 * 
	 * @return
	 */
	public int getCount() {
		int count = 0;
		if (!isEmpty()) {
			for (EbookShoppingcartItem item : itemList) {
				count += item.getQuantity();
			}
		}
		return count;
	}
	
	/**
	 * 购物车商品数量
	 * @return
	 */
	public int getProductCount(){
		if (!isEmpty()) {
			return this.getItemList().size();
		}
		return MagicNumber.ZERO;
		
	}
	

	/**
	 * 移除购物车商品
	 * 
	 * @param productSale
	 * @return
	 */
	public boolean remove(ProductSale productSale) {
		EbookShoppingcartItem item = getItem(productSale);
		if (item != null) {
			return this.remove(item);
		}
		return false;
	}

	public void add(EbookShoppingcartItem item) {
		if (itemList == null) {
			itemList = new HashSet<EbookShoppingcartItem>();
		}
		item.setEbookShoppingCart(this);
		itemList.add(item);
	}
	
	
	public void add(Set<EbookShoppingcartItem> items) {
		if(items == null || items.size() == 0){
			return;
		}
		Iterator<EbookShoppingcartItem> it = items.iterator();
		while(it.hasNext()){
			add(it.next());
		}
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
		BigDecimal result = new BigDecimal(MagicNumber.ZERO);
		if (!isEmpty()) {
			for (EbookShoppingcartItem item : itemList) {
				result =result.add(item.getProductSale().getEffectivePrice());
			}
		}
		return result;
	}
	
	
	
}
