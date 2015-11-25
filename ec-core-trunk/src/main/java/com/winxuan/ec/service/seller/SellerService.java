/*
 * @(#)SellerService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.seller;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.user.Seller;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.validator.AuthenticationProvider;

/**
 * 卖家接口
 * @author  HideHai
 * @version 1.0,2011-8-9
 */
public interface SellerService extends AuthenticationProvider{

	/**
	 * 通过编号获取卖家
	 * @param id
	 * @return
	 */
	Seller get(Long id);
	
	/**
	 * 根据用户名和密码登录
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	Seller login(String name, String password);
	
	/**
	 * 通过名称获取卖家
	 * 
	 * @param name
	 *            用户名称
	 * @return
	 */
	Seller getByName(String name);
	
	/**
	 * 检查用户名是否存在
	 * 
	 * @param name
	 * @return
	 */
	boolean nameIdExisted(String name);
	
	/**
	 * 修改卖家账户信息
	 * @param seller
	 */
	void update(Seller seller);
	
	/**
	 * 获取卖家(用于seller查询)
	 * @return
	 */
	List<Seller> findSeller(Map<String,Object> params,Pagination pagination);
	
	/**
	 * 获取卖家
	 * @return
	 */
	List<Seller> find();
}

