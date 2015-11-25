/*
 * @(#)CategoryService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.category;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.CategoryException;
import com.winxuan.ec.model.category.Category;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
public interface CategoryService {

	/**
	 * 获得分类
	 * @param id 分类编号
	 * @return 存在返回对应分类，不存在返回null
	 */
	Category get(Long id);
	
	/**
	 * 更新分类
	 * @param category 分类
	 */
	void update(Category category);
	
	/**
	 * 删除分类
	 * @param category
	 */
	void delete(Category category) throws CategoryException;
	
	/**
	 * 创建分类
	 * @param parent 被创建分类的父级分类
	 * @param child 被创建的分类，仅需设置name,alias
	 */
	void create(Category parent,Category child);
	
	/**
	 * 查询父分类为空的分类
	 * 即为第一级分类
	 */
	List<Category> findNoParent();
	
	/**
	 * 根据名字查询category
	 * @param name
	 * @return
	 */
	List<Category> findCategoryByName(Map<String, Object> parameters);
	
	/**
	 * 分类下的商品数量
	 * @param category
	 * @return
	 */
	long getProductCount(Category category);
}
