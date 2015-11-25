/*
 * @(#)ShoppingcartService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.shoppingcart;


import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
public interface ShoppingcartService {

	/**
	 * 得到购物车<br/>
	 * 修改shoppingcart.useTime为当前时间
	 * 
	 * @param id
	 * @return
	 */
	Shoppingcart get(String id);

	/**
	 * 向购物车添加商品，数量小于等于0时，则为清除该商品<br/>
	 * 设置shoppingcart.useTime为当前时间<br/>
	 * 
	 * @param shoppingcart
	 *            购物车
	 * @param productSale
	 *            銷售商品
	 * @param quantity
	 *            商品的数量
	 * @return 如果quantity小于等于productSale.avalibleQuantity，设置商品数量为quantity，返回true<br/>
	 *         否则设置数量为productSale.avalibleQuantity，返回false<br/>
	 *         如果商品已下架，不添加，返回false。
	 */
	boolean add(Shoppingcart shoppingcart, ProductSale productSale, int quantity);
	
	ShoppingcartItem getItem(Long id);
	
	boolean removeProduct(Shoppingcart shoppingcart,ProductSale productSale);
	
	/**
	 * 根据用户获取购物车
	 * @param customer
	 * @return Shoppingcart
	 */
	Shoppingcart findShoppingcartByCustomer(Customer customer);
	

}
