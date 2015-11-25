package com.winxuan.ec.dao;

import com.winxuan.ec.model.customer.CustomerPayee;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-7
 */
public interface CustomerPayeeDao {
	
	@Query("from CustomerPayee c where c.id=?")
	CustomerPayee get(Long id);
	
	@SaveOrUpdate
	void saveOrUpdate(CustomerPayee customerPayee);
}
