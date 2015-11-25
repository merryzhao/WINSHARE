package com.winxuan.ec.dao;

import com.winxuan.ec.model.product.DciLog;
import com.winxuan.framework.dynamicdao.annotation.Save;
/**
 * 
 * @author cl
 *
 */
public interface DciLogDao {
	@Save
	void save(DciLog dciLog);

}
