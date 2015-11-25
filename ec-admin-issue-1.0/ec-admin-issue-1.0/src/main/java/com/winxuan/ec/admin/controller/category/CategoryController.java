/*
 * @(#)CategoryController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.category;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.CategoryException;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.category.McCategory;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.category.McCategoryService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * description
 * 
 * @author heyadong update cast911
 * @version 1.0,2011-7-28
 * 
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
	
	private static final  String CATEGORY = "category";
	private static final String CATEGORY_RESULT = "/category/result";
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private McCategoryService mcCategoryService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(value = "id", required = false) Long productId,
			@RequestParam(value = "sid", required = false) Long productSaleId) {
		String page = null == productId ? "/category/robot_list" : "/category/robot_list_product";
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("productId", productId);
		mav.addObject("productSaleId", productSaleId);
		return mav;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView getCategory(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("/category/info");
		mav.addObject(CATEGORY, categoryService.get(id));
		return mav;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ModelAndView removeCategory(@PathVariable("id") Long id)
			throws CategoryException {
		ModelAndView modelAndView = new ModelAndView(CATEGORY_RESULT);
		Category category = categoryService.get(id);
		if (!category.getCode().startsWith(Category.MALL.toString())) {
			throw new CategoryException(category,
					String.format("只有百货下的分类才能删除!"));
		}
		categoryService.delete(category);
		modelAndView.addObject("result", MagicNumber.ONE);
		return modelAndView;
	}

	/**
	 * 更新sort,主要针对百货那种.. -_!
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/updatesort", method = RequestMethod.POST)
	public ModelAndView updateSort(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "sort", required = false) Integer sort) {
		ModelAndView modelAndView = new ModelAndView(CATEGORY_RESULT);
		Category category =categoryService.get(id);
		category.setIndex(sort);
		categoryService.update(category);
		modelAndView.addObject("result", MagicNumber.ONE);		
		return modelAndView;
	}

	@RequestMapping(value = "/tree", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView getCategorys(
			@RequestParam(value = "id", required = false) Long parentID) {
		ModelAndView mav = new ModelAndView("/category/robot_tree");
		mav.addObject("categorys",
				parentID == null ? categoryService.findNoParent()
						: categoryService.get(parentID).getChildren());
		return mav;
	}

	@RequestMapping(value = "/{id}/tree_product", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView getCategorysByProduct(@PathVariable("id") Long id,
			@RequestParam(value = "id", required = false) Long parentID) {
		ModelAndView mav = new ModelAndView("/category/robot_tree_product");
		Collection<Category> categorys = parentID == null ? categoryService.findNoParent():categoryService.get(parentID).getChildren();
		List<Category> list = productService.get(id).getCategories();
		for(Category category : list){
			String code = category.getCode().trim().replace('.',':');
			if(code.indexOf(':')!=-1){
				String[]  categoryLevel = code.split(":");
				for(int i=0;i<categoryLevel.length;i++){
					String pid = categoryLevel[i];
					for(Category cate : categorys){
						if(pid.trim().equals(cate.getId().toString())){
							cate.setDefaultChecked(true);
							break;
						}
					}
				}
			}else{
				String categoryParentId = category.getCode();
				for(Category cate : categorys){
					if(categoryParentId.trim().equals(cate.getId().toString())){
						cate.setDefaultChecked(true);
						break;
					}
				}
			}
		}
		mav.addObject("categorys",categorys);
		return mav;
	}
	
	
	@RequestMapping(value = "/rename", method = RequestMethod.POST)
	public ModelAndView rename(@RequestParam("id") Long id,
			@RequestParam("name") String name) {
		ModelAndView mav = new ModelAndView("/category/info");
		Category category = categoryService.get(id);
		if (category != null) {
			if (name.indexOf("*") > 0) {
				String[] names = StringUtils.split(name, "*");
				if (names.length > MagicNumber.ONE) {
					category.setName(names[0]);
					category.setAlias(names[1]);
				} else {
					category.setName(name);
					category.setAlias(name);
				}
			} else {
				category.setName(name);
				category.setAlias(name);
			}
			categoryService.update(category);
			mav.addObject(CATEGORY, category);
		}
		return mav;
	}

	@RequestMapping(value = "/reparent", method = RequestMethod.POST)
	public void reparent(@RequestParam("id") Long id,
			@RequestParam("parentId") Long parentId) {
		Category parent = categoryService.get(parentId);
		Category category = categoryService.get(id);
		if (parent != null && category != null) {
			category.setParent(parent);
			categoryService.update(category);
		}
	}

	@RequestMapping(value = "/available", method = RequestMethod.POST)
	public ModelAndView updateAvailable(@RequestParam("id") Long id,
			@RequestParam("available") Boolean available) {
		ModelAndView mav = new ModelAndView(CATEGORY_RESULT);
		Category category = categoryService.get(id);
		if (category != null && category.getParent() != null) {
			recursionAvailable(category, available);
			categoryService.update(category);
		}
		mav.addObject("state", "1");
		return mav;
	}

	/**
	 * 相同父节点对换.
	 * 	 * 
	 * @param id
	 * @param targetid
	 * @return
	 */
	@RequestMapping(value = "/renode", method = RequestMethod.POST)
	public ModelAndView renode(@RequestParam("id") Long id,
			@RequestParam("targetid") Long targetid) {
		ModelAndView mav = new ModelAndView(CATEGORY_RESULT);
		Category category = categoryService.get(id);
		Category targetCategory = categoryService.get(targetid);
		int tempIndex = category.getIndex();
		int targetIndex = targetCategory.getIndex();
		if(tempIndex == targetIndex){
			targetIndex++;
		}
		category.setIndex(targetIndex);
		targetCategory.setIndex(tempIndex);
		
		categoryService.update(category);
		categoryService.update(targetCategory);
		mav.addObject("state", "1");
		return mav;
	}

	private void recursionAvailable(Category category, boolean available) {
		category.setAvailable(available);
		Set<Category> children = category.getChildren();
		if (children == null || children.isEmpty()) {
			return;
		}
		for (Category child : children) {
			recursionAvailable(child, available);
		}

	}

	@RequestMapping(value = "new", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam("parentId") Long parentId) {
		ModelAndView mav = new ModelAndView(CATEGORY_RESULT);
		Category parent = categoryService.get(parentId);
		if (parent != null) {
			Category category = new Category();
			category.setName("新建节点");
			category.setAlias("新建节点");
			category.setParent(parent);
			category.setAvailable(false);
			categoryService.create(parent, category);
			category.setCode(category.createCode());
			categoryService.update(category);

		}
		return mav.addObject("state", "1");
	}

	@RequestMapping(value = "{id}/productMeta", method = RequestMethod.GET)
	public ModelAndView metaList(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("category/productmeta_list");
		Category category = categoryService.get(id);
		Category root = category.root();
		Integer rootId = Integer.valueOf(root.getId().toString());
		Set<ProductMeta> productMetaList = category.getProductMetaList();
		List<ProductMeta> productMetaAvailable = productService
				.findProductMeta(true);
		Iterator<ProductMeta> iterator = productMetaAvailable.iterator();
		while (iterator.hasNext()) {
			if (!rootId.equals(iterator.next().getCategory())) {
				iterator.remove();
			}
		}
		productMetaAvailable.removeAll(productMetaList);
		mav.addObject("productMetaAvailable", productMetaAvailable);
		mav.addObject("productMetaList", productMetaList);
		mav.addObject(CATEGORY, category);
		return mav;
	}

	@RequestMapping(value = "/{id}/productMeta", method = RequestMethod.POST)
	public ModelAndView updateProductMeta(
			@PathVariable("id") Long id,
			@RequestParam(value = "item", required = false) List<String> productMetaId,
			@RequestParam(value = "mccategories", required = false) List<String> mccategories) {
		ModelAndView mav = new ModelAndView("category/result");
		Set<ProductMeta> productMetaList = new HashSet<ProductMeta>();
		if (productMetaId != null) {
			for (int i = 0; i < productMetaId.size(); i++) {
				productMetaList.add(productService.getProductMeta(NumberUtils
						.createLong(productMetaId.get(i))));
			}
		}
		Category category = categoryService.get(id);
		if (category != null) {
			Set<McCategory> mcs = category.getMcCategory();
			mcs.clear();
			McCategory mc = null;
			if (mccategories != null && !mccategories.isEmpty()) {
				for (String mcId : mccategories) {
					mc = mcCategoryService.get(mcId);
					if (mc != null) {
						mcs.add(mc);
					}
				}
			}
			category.setProductMetaList(productMetaList);
			categoryService.update(category);
			mav.addObject(CATEGORY, category);
		}
		return mav;
	}
}
