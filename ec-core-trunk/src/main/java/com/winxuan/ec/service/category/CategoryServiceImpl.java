/*
 * @(#)CategoryServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.category;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CategoryDao;
import com.winxuan.ec.exception.CategoryException;
import com.winxuan.ec.model.category.Category;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * 
 * @author chenlong
 * @version 1.0,2011-7-18
 */

@Service("categoryService")
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {

	@InjectDao
	private CategoryDao categoryDao;
    
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Category get(Long id) {
		return categoryDao.get(id);
	}

	public void update(Category category) {
		categoryDao.update(category);
	}
	

	@Override
	public void delete(Category category) throws CategoryException {
		if(category.hasChildren()){
			throw new CategoryException(category,String.format("分类下还有子分类，不能执行删除操作!"));
		}
		long count = getProductCount(category);
		if(count>0){
			throw new CategoryException(category, String.format("分类下还有商品,数量:[%s]，不能执行删除操作!",count));
		}
		Category parent = category.getParent();
		parent.removeChild(category);
		categoryDao.update(parent);
		categoryDao.delete(category);
	}

	public void create(Category parent, Category child) {
		parent.addChild(child);
		categoryDao.save(child);
		child.createCode();
		categoryDao.update(child);
	}

	@Override
	public List<Category> findNoParent() {
		return categoryDao.findNoParentCategory();
	}


	@Override
	public List<Category> findCategoryByName(Map<String, Object> parameters) {
		return categoryDao.findCategoryByName(parameters);
	}

	@Override
	public long getProductCount(Category category) {
		return categoryDao.getProductCount(category.getId());
	}
	
	

}
