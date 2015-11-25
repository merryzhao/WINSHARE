package com.winxuan.ec.dao;

import com.winxuan.ec.model.product.DciData;
import com.winxuan.framework.dynamicdao.annotation.Get;

/**
 * 
 * @author cl
 *
 */
public interface DciDataDao {
	
	@Get
	DciData get(Long id);

}
