/*
 * @(#)CategoryDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.category.Category;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author chenlong
 * @version 1.0,2011-7-18
 */
public interface CategoryDao {

	@Get
	Category get(Long id);

	@Update
	void update(Category category);

	@Save
	void save(Category category);
	
	@Delete
	void delete(Category category);

	@Query("from Category where parent is null")
	List<Category> findNoParentCategory();
 
	@Query("from Category c")
	@Conditions({
		     @Condition("c.name =:name"),
			 @Condition("c.available = :available") 
			 })
	List<Category> findCategoryByName(
			@ParameterMap Map<String, Object> parameters);
	
	@Query(value="SELECT COUNT(*) from product_category WHERE category =?",sqlQuery=true)
	long getProductCount(Long categoryid);
}
