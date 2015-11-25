/*
 * @(#)CategoryService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.shop;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.shop.ProShop;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author cast911
 * @version 1.0,2012-05-23
 *
 */
public interface ProShopService {

	ProShop get(Long id);
	
	void update(ProShop proShop);
	
	void save(ProShop proShop);
	
	void saveOrUpdate(ProShop proShop);

	void delete(ProShop proShop);
	
	List<ProShop> find(Map<String, Object> parameters, Short sort,Pagination pagination);
}
