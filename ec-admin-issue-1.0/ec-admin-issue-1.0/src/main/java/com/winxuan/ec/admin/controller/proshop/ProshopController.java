package com.winxuan.ec.admin.controller.proshop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.shop.ProShop;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.shop.ProShopService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author cast911
 * 
 */
@Controller
@RequestMapping("/proshop")
public class ProshopController {

	@Autowired
	ProShopService proShopService;

	@Autowired
	CategoryService categoryService;

	@RequestMapping(value = "/saveorupdate", method = RequestMethod.POST)
	public ModelAndView saveOrUpdate(ProshopForm proshopForm) {
		ModelAndView mav = new ModelAndView("/category/result");
		ProShop ps = null;
		if (proshopForm.getId() != null) {
			ps = proShopService.get(proshopForm.getId());
		}
		if (ps == null) {
			ps = new ProShop();
		}

		if (proshopForm.getCategory() != null) {
			String category = proshopForm.getCategory();
			category = StringUtils.trim(category);
			String[] str;
			if (category.indexOf(",") > 0) {
				str = proshopForm.getCategory().split(",");
			} else {
				str = new String[] { category };
			}

			ps.setCategories(null);
			Set<Category> categorys = new HashSet<Category>();
			for (String string : str) {
				Category ca = categoryService.get(Long.parseLong(string));
				if (ca != null) {
					ProShop proShop = ca.getProShop();
					if (!ps.equals(proShop)) {
						if (proShop != null) {
							mav.addObject(ControllerConstant.RESULT_KEY,
									ControllerConstant.RESULT_PARAMETER_ERROR);
							mav.addObject(
									ControllerConstant.MESSAGE_KEY,
									"分类id" + ca.getId() + ",已经映射到 "
											+ ca.getName() + ":"
											+ proShop.getUrl());
							return mav;
						}
					}
					categorys.add(ca);
					ps.setCategories(categorys);
				}

			}

		}

		ps.setAvailable(proshopForm.isAvailable());
		ps.setDescription(proshopForm.getDescription());
		ps.setIndex(proshopForm.getIndex());
		ps.setName(proshopForm.getName());
		ps.setTemplate(proshopForm.getTemplate());
		ps.setUrl(proshopForm.getUrl());
		proShopService.saveOrUpdate(ps);
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		mav.addObject(ControllerConstant.MESSAGE_KEY,"修改成功!");
		mav.addObject("proshop", ps);
		return mav;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ModelAndView delete(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("/category/result");
		proShopService.delete(proShopService.get(id));
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("/category/proshop");
		ProShop ps = proShopService.get(id);
		mav.addObject("proshop", ps);
		return mav;
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView find(@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/proshop/index_list");
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		List<ProShop> proShopList = proShopService.find(parameters, (short) 1,
				pagination);
		mav.addObject("proShopList", proShopList);
		mav.addObject("pagination", pagination);
		return mav;
	}

}
