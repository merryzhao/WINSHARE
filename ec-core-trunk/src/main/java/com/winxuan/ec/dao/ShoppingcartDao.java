package com.winxuan.ec.dao;


import java.util.List;

import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
/**
* description
* 
* @author renshiyong
* @version 1.0,2011-7-19
 */

public interface ShoppingcartDao {

	@Get
	Shoppingcart get(String id);
	
	@Get
	ShoppingcartItem getItem(Long id);
	
	@Delete
	void deleteItem(ShoppingcartItem item);
	
	@SaveOrUpdate
	void saveOrUpdateItem(ShoppingcartItem item);
	
	@SaveOrUpdate
	void saveOrUpdate(Shoppingcart shoppingcart);
	
	@Save
	void save(Shoppingcart shoppingcart);
	
	@Query(sqlQuery=true,value="delete from shoppingcart_item where id =?",executeUpdate=true)
	int deleteItem(Long id);
	
	@Query("from Shoppingcart sc where sc.customer.id =? order by sc.createTime desc")
	List<Shoppingcart> findShoppingcartByCustomer(Long id,@MaxResults int maxResults);
	
	
}
