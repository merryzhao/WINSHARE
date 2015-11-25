/*
 * @(#)CategoryService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.shop;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProShopDao;
import com.winxuan.ec.model.shop.ProShop;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
/**
 * description
 * 
 * @author cast911
 * @version 1.0,2011-7-11
 */

@Service("categoryProShopService")
@Transactional(rollbackFor = Exception.class)
public class ProShopServiceImpl implements ProShopService {

	@InjectDao
	ProShopDao categoryProShopDao;
	
	
	@Override
	public ProShop get(Long id) {
		// TODO Auto-generated method stub
		return this.categoryProShopDao.get(id);
	}

	@Override
	public void update(ProShop categoryProShop) {
		this.categoryProShopDao.update(categoryProShop);
		
	}

	@Override
	public void save(ProShop categoryProShop) {
		this.categoryProShopDao.save(categoryProShop);
		
	}

	@Override
	public void saveOrUpdate(ProShop categoryProShop) {
		this.categoryProShopDao.saveOrUpdate(categoryProShop);
		
	}

	@Override
	public void delete(ProShop proShop) {
		this.categoryProShopDao.delete(proShop);
		
	}

	@Override
	public List<ProShop> find(Map<String, Object> parameters, Short sort,
			Pagination pagination) {
		return this.categoryProShopDao.find(parameters, sort, pagination);
	}

	
}
