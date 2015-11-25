package com.winxuan.ec.dao;


import java.util.List;

import com.winxuan.ec.model.shoppingcart.EbookShoppingcart;
import com.winxuan.ec.model.shoppingcart.EbookShoppingcartItem;
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

public interface EbookShoppingcartDao {

	@Get
	EbookShoppingcart get(String id);
	
	@Get
	EbookShoppingcartItem getItem(Long id);
	
	@Delete
	void deleteItem(EbookShoppingcartItem item);
	
	@SaveOrUpdate
	void saveOrUpdateItem(EbookShoppingcartItem item);
	
	@SaveOrUpdate
	void saveOrUpdate(EbookShoppingcart ebookShoppingcart);
	
	@Save
	void save(EbookShoppingcart ebookShoppingcart);
	
	@Query(sqlQuery=true,value="delete from ebook_shoppingcart_item where id =?",executeUpdate=true)
	int deleteItem(Long id);
	
	@Query("from EbookShoppingcart sc where sc.customer.id =? order by sc.createTime desc")
	List<EbookShoppingcart> findShoppingcartByCustomer(Long id,@MaxResults int maxResults);
	
	
}
