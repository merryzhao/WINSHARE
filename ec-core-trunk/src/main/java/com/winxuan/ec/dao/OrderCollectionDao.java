/**
 * 
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.OrderCollection;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-6
 */
public interface OrderCollectionDao {

	@Get
	OrderCollection get(String id);
	
	@Update
	void update(OrderCollection orderCollection);
	
	@Save
	void save(OrderCollection orderCollection);
	
	@Query("from OrderCollection oc where oc.id = ?")
	OrderCollection getCollentionByOrderId(String orderId);
	
	@SaveOrUpdate
	void saveOrUpdate(OrderCollection orderCollection);
}
