package com.winxuan.ec.front.controller.cm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * categoryController
 * 
 * @author juqkai 2011-12-12
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController {

	// 图书一级目录显示个数

	private static final String ITEMS_KEY = "items";

	private static final Log LOG = LogFactory.getLog(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/{id}")
	public ModelAndView makeCategory(@PathVariable("id") Long id) {
		ModelAndView view = new ModelAndView("/category/list");
		Category cate = categoryService.get(id);
		view.addObject("category", makeMap(cate, -1));
		return view;
	}

	@RequestMapping("/{id}/{level}")
	public ModelAndView makeCategory(@PathVariable("id") Long id,
			@PathVariable("level") int level) {
		ModelAndView view = new ModelAndView("/category/list");
		Category cate = categoryService.get(id);
		view.addObject("category", makeMap(cate, level));
		return view;
	}

	private Map<String, Object> makeMap(Category cate, int level) {
		Map<String, Object> m = new HashMap<String, Object>();
		Map<String, Object> map = itemToMap(cate);
		if (cate.getChildren().size() > 0) {
			if (level > 1) {
				map.put(ITEMS_KEY, treeToList(cate.getChildren(), --level));
			} else if (level < 0) {
				map.put(ITEMS_KEY, treeToList(cate.getChildren(), -1));
			} else if (level == 1) {
				map.put(ITEMS_KEY, treeToList(cate.getChildren()));
			}
		}
		m.put(cate.getId().toString(), map);
		return m;
	}

	private Map<String, Object> itemToMap(Category cate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", cate.getName());
		map.put("title", cate.getName());
		map.put("href", cate.getId().toString());
		return map;
	}

	private List<Map<String, Object>> treeToList(Collection<Category> children) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Category ca : children) {
			if (ca.isAvailable()) {
				list.add(itemToMap(ca));
			}
		}
		return list;
	}

	private Map<String, Object> treeToList(Collection<Category> children,
			int level) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Category ca : children) {
			if (ca.isAvailable()) {
				map.putAll(makeMap(ca, level));
			}
		}
		return map;
	}

	@RequestMapping(value = "/tree/{id}", method = { RequestMethod.GET })
	public ModelAndView categoryTree(
			@PathVariable("id") Long id,
			@RequestParam(value = "jspfile", required = false, defaultValue = "catelog_tree") String jspfile,
			@RequestParam(value = "onlyEBook", required = false, defaultValue = "false") String onlyEBook,
			@RequestParam(value = "count", required = false, defaultValue = "0") Integer count) {
		ModelAndView mav = new ModelAndView("/fragment/category/" + jspfile);
		if (count == 0) {
			Category category = categoryService.get(id);
			mav.addObject("category", category);
			if("true".equals(onlyEBook))
			{
				mav.addObject("onlyEBookQueryString", "?onlyEBook="+onlyEBook);
			}
		} else {
			this.fillBookCategory(id, mav, count);
		}
		return mav;

	}

	@RequestMapping(value = "/book/nav")
	public ModelAndView bookCategoryNav() {
		ModelAndView mav = new ModelAndView("/fragment/category/book/nav");
		fillBookCategory(Category.BOOK, mav, MagicNumber.EIGHT);
		return mav;
	}
	
	@RequestMapping(value = "/book/navnew")
	public ModelAndView bookCategoryNavNew() {
		ModelAndView mav = new ModelAndView("/fragment/category/book/navnew");
		fillBookCategory(Category.BOOK, mav, MagicNumber.EIGHT);
		return mav;
	}

	@RequestMapping(value = "/mall/nav")
	public ModelAndView mallCateogryNav() {
		ModelAndView mav = new ModelAndView("/fragment/category/mall/nav");
		Category mall = categoryService.get(Category.MALL);
		
		mav.addObject("mall", mall);
		return mav;
	}

	@RequestMapping(value = "/tree/book", method = { RequestMethod.GET })
	public ModelAndView categoryTree() {
		ModelAndView mav = new ModelAndView("/fragment/category/tree");
		Category book = categoryService.get(Category.BOOK);
		mav.addObject("root", book);
		return mav;
	}

	private void fillBookCategory(Long categoryId, ModelAndView mav, int sum) {
		Category book = categoryService.get(categoryId);
		List<Category> items = new ArrayList<Category>();
		int count = 0;
		Set<Category> childs = book.getChildren();
		for (Category category : childs) {
			if (category.isAvailable() && count < sum) {
				count++;
				items.add(category);
			}
			if (count == sum) {
				break;
			}
		}
		mav.addObject(ITEMS_KEY, items);
		count = 0;
		// 更多图书,导航放四个.hover效果展示全部,奇葩,,去掉前面展示的N个
		List<Category> morebook = new ArrayList<Category>();
		for (Category category : childs) {
			if (category.isAvailable() && count < MagicNumber.FOUR
					&& category.getIndex() > MagicNumber.EIGHT) {
				count++;
				morebook.add(category);
			}
		}
		mav.addObject("morebook", morebook);
	}

	@RequestMapping(value = "/navdata")
	public ModelAndView getNavData(@RequestParam(required = false) String format) {
		ModelAndView mav = new ModelAndView("/fragment/category/navdata");
		NavAsse na = new NavAsse();
		na.setCategoryService(categoryService);
		try {
			mav.addObject("navdata", na.asseDate().toString());
			if ("json".equals(format)) {
				mav.setViewName("/category/navdata");
				mav.addObject("navdata", na.asseDate().toString());
			}
		} catch (JSONException e) {
			LOG.error(e.getMessage(), e);
		}
		return mav;
	}

	@RequestMapping(value = "/mall/navdata")
	public ModelAndView getMallNavData(
			@RequestParam(required = false) String format) throws JSONException {
		ModelAndView mav = new ModelAndView("/fragment/category/navdata");
		MallNavAsse mna = new MallNavAsse();
		mna.setCategoryService(categoryService);
		mav.addObject("navdata", mna.asseDate().toString());
		return mav;
	}

}
