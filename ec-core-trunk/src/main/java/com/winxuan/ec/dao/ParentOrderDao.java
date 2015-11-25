/**
 * 
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.ParentOrder;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-10-16
 */
public interface ParentOrderDao {

	@Get
	ParentOrder get(String id);
	
	@Save
	void save(ParentOrder parentOrder);
	
	@Update
	void update(ParentOrder parentOrder);
	
}
