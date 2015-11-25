package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.erp.EcErpOrderItem;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 
 * @author yj
 *
 */
public interface ErpOrderItemDao {
	
	@Save
	void save(EcErpOrderItem erpOrderItem);
	
	@Query("from EcErpOrderItem ei where ei.order=?")
	List<EcErpOrderItem> findByOrderid(String order);
}
