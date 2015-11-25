/*
 * @(#)ShoppingcartService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.shoppingcart;


import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shoppingcart.EbookShoppingcart;
import com.winxuan.ec.model.shoppingcart.EbookShoppingcartItem;
import com.winxuan.ec.model.user.Customer;

/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-16 上午11:23:01  --!
 * @description:应该把两种购物车抽象出接口,这样Service方法只需实现相同的接口.but,时间有限.
 ********************************
 */
public interface EbookShoppingcartService {

	/**
	 * 得到购物车<br/>
	 * 修改shoppingcart.useTime为当前时间
	 * 
	 * @param id
	 * @return
	 */
	EbookShoppingcart get(String id);
	
	
	void save(EbookShoppingcart ebookShoppingcart);
	
	
	

	/**
	 * 电子书购物车不存在商品数量
	 * @param ebookShoppingcart
	 * @param productSale
	 * @param quantity
	 * @return
	 */
	boolean add(EbookShoppingcart ebookShoppingcart, ProductSale productSale);
	
	EbookShoppingcartItem getItem(Long id);
	
	/**
	 * 删除购物车商品
	 * @param ebookShoppingcart
	 * @param productSale
	 * @return
	 */
	boolean removeProduct(EbookShoppingcart ebookShoppingcart,ProductSale productSale);
	
	/**
	 * 删除指定用户的购物车商品
	 * @param customer
	 * @param productSale
	 * @return
	 */
	boolean removeProudct(Customer customer,ProductSale productSale);
	
	/**
	 * 根据用户获取购物车
	 * @param customer
	 * @return Shoppingcart
	 */
	EbookShoppingcart findShoppingcartByCustomer(Customer customer);
	
	/**
	 * 清除指定用户的购物车
	 * @param customer
	 */
	void clear(Customer customer);
	

}
