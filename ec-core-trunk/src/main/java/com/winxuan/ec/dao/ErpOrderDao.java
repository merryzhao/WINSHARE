package com.winxuan.ec.dao;

import com.winxuan.ec.model.erp.EcErpOrder;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 
 * @author yj
 * 
 */
public interface ErpOrderDao {

	@Save
	void save(EcErpOrder erpOrder);

	@Query("from EcErpOrder e where e.order = ? and e.state = ?")
	EcErpOrder get(String order,String state);

	@Update
	void update(EcErpOrder ecErpOrder);
}
