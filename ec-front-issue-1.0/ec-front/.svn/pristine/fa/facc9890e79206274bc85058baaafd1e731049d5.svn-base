/*
 * @(#)FavoriteController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerFavorite;
import com.winxuan.ec.model.customer.CustomerFavoriteTag;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-1
 */
@Controller
@RequestMapping(value = "/customer/favorite")
public class FavoriteController {

	private static final String STATUS = "status";
	private static final short STATUS_SUCESS = 1;
	private static final short STATUS_FAILED = 2;
//	public static final int RECOMMEND_TAG_SIZE = 3;
	
	private static final String MODIFY = "/customer/favorite/modify";

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CodeService codeService;

	@Autowired
	private ProductService productService;
	

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView view(
			@MyInject Customer customer,
			@RequestParam(value = "sort", required = false) Long sortId,
			@RequestParam(value = "tag", required = false) Long tagId,
			@RequestParam(value = "order", required = false) Short index,
			@MyInject Pagination pagination) {
		ModelAndView modelAndView = new ModelAndView("/customer/favorite/view");
		
		//spring bug：如果order为null，会用default值，为"",会转换成null。
		if(null == index || index >2 || index < 0){
			index = (short)0;
		}
		
		Code sort = (sortId == null) ? null : codeService.get(sortId);
		List<CustomerFavorite> favoriteList;
		final long noTag = -2;
		
		if(tagId != null && tagId == noTag){
			favoriteList = customerService.findFavorite(customer, sort, null, true, index, pagination);
		}
		else {
			CustomerFavoriteTag tag = (tagId == null || tagId == -1) ? null : customerService.getFavoriteTag(tagId);
			favoriteList = customerService.findFavorite(customer, sort, tag, null, index, pagination);
		}
		
		Map<CustomerFavoriteTag, Integer> tags = customerService.findFavoriteTag(customer);
		
		modelAndView.addObject("favoriteList", favoriteList);
		modelAndView.addObject("pagination", pagination);
		modelAndView.addObject("tags", tags);
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@MyInject Customer customer,
			@RequestParam(value = "p") Long[] productSaleIdArray) {
		boolean single = productSaleIdArray.length == 1;
		ModelAndView modelAndView = new ModelAndView(
				MODIFY);
		for (Long productSaleId : productSaleIdArray) {
			ProductSale productSale = productService
					.getProductSale(productSaleId);
			short status = customerService.addToFavorite(customer, productSale) ? STATUS_SUCESS
					: STATUS_FAILED;
			if (single) {
				modelAndView.addObject(STATUS, status);
				modelAndView.addObject("performance", productSale.getPerformance());
				CustomerFavorite favorite = customerService.getFavorite(
						customer, productSale);
				String tagNames = favorite.getTagNames();
				if (!StringUtils.isBlank(tagNames)) {
					modelAndView.addObject("tags", tagNames.split("\\s+"));
				}
				Set<String> recommend = customerService
						.findRecommendFavoriteTagNames(customer, productSale,
								CustomerFavoriteTag.FAVORITE_TAG_RECOMMEND_QUANTITY);
				if (recommend != null && !recommend.isEmpty()) {
					modelAndView.addObject("recommends", recommend);
				}
			}
		}
		if (!single) {
			modelAndView.addObject(STATUS, STATUS_SUCESS);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/addAllFromCart", method = RequestMethod.GET)
	public ModelAndView addAllFromCart(@MyInject Customer customer,
			@MyInject Shoppingcart shoppingcart) {
		
		Set<ShoppingcartItem> set = shoppingcart.getItemList();
		if(!set.isEmpty()){
			Long[] productSaleIdArray = new Long[set.size()];
			int i = 0;
			for(ShoppingcartItem shoppingcartItem : set){
				productSaleIdArray[i] = shoppingcartItem.getProductSaleId();
				i++;
			}
			add(customer, productSaleIdArray);
		}
		
		ModelAndView modelAndView = new ModelAndView("redirect:/customer/favorite");
		return modelAndView;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@MyInject Customer customer,
			@RequestParam(value = "p") Long productSaleId) {
		ModelAndView modelAndView = new ModelAndView(
				MODIFY);
		ProductSale productSale = productService.getProductSale(productSaleId);
		short status = customerService
				.removeFromFavorite(customer, productSale) ? STATUS_SUCESS
				: STATUS_FAILED;
		modelAndView.addObject(STATUS, status);
		return modelAndView;
	}
	
	@RequestMapping(value = "/removeBatch", method = RequestMethod.DELETE)
	public ModelAndView remove(@MyInject Customer customer,
			@RequestParam(value = "p") Long[] productSaleIds) {
		ModelAndView modelAndView = new ModelAndView(MODIFY);
		short status = STATUS_SUCESS;
		for(Long productSaleId : productSaleIds){
			ProductSale productSale = productService.getProductSale(productSaleId);
			if(!customerService.removeFromFavorite(customer, productSale)){
				status = STATUS_FAILED;
			}
		}
		modelAndView.addObject(STATUS, status);
		return modelAndView;
	}

	@RequestMapping(value = "/recommend", method = RequestMethod.GET)
	public ModelAndView recommend(
			@MyInject Customer customer,
			@RequestParam(value = "p") Long productSaleId,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size) {
		ModelAndView modelAndView = new ModelAndView(
				"/customer/favorite/recommend");
		ProductSale productSale = productService.getProductSale(productSaleId);
		modelAndView.addObject("recommend", customerService
				.findRecommendFavoriteTagNames(customer, productSale, size));
		return modelAndView;
	}
	/**
	 * 给收藏商品添加标签
	 * @param customer
	 * @param productSaleId
	 * @param tag
	 * @return
	 */
	@RequestMapping(value = "/tag", method = RequestMethod.GET)
	public ModelAndView tag(@MyInject Customer customer,
			@RequestParam(value = "p") Long productSaleId,
			@RequestParam(value = "tag") String tag) {
		ModelAndView modelAndView = new ModelAndView(
				MODIFY);
		ProductSale productSale = productService.getProductSale(productSaleId);
		CustomerFavorite favorite = customerService.getFavorite(customer,
				productSale);
		if (favorite == null) {
			modelAndView.addObject(STATUS, STATUS_FAILED);
		} else {
			customerService.addTagToFavorite(favorite, tag);
			modelAndView.addObject(STATUS, STATUS_SUCESS);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/tag", method = RequestMethod.DELETE)
	public ModelAndView deleteTag(@MyInject Customer customer,
			@RequestParam(value = "tag") Long tagId){
		ModelAndView modelAndView = new ModelAndView("/customer/tag/delete");
		boolean status = customerService.deleteFavoriteTag(customer , tagId);
		if(status){
			modelAndView.addObject(STATUS, STATUS_SUCESS);
		}
		else {
			modelAndView.addObject(STATUS, STATUS_FAILED);
		}
		return modelAndView;
	}
	@RequestMapping(value = "/tag", method = RequestMethod.POST)
	public ModelAndView addTag(@MyInject Customer customer,
			@RequestParam(value = "tagName") String tagName){
		ModelAndView modelAndView = new ModelAndView("/customer/tag/add");
		short status = STATUS_FAILED;
		if(!com.winxuan.framework.util.StringUtils.isNullOrEmpty(tagName)){
			boolean isSuccess = customerService.createFavoriteTag(customer, tagName);
			if(isSuccess){
				status = STATUS_SUCESS;
				Long tagId = customerService.getFavoriteTagByTagName(customer, tagName).getId();
				modelAndView.addObject("tagId", tagId);
			}
		}
		modelAndView.addObject(STATUS, status);
		return modelAndView;
	}
	@RequestMapping(value = "/tag", method = RequestMethod.PUT )
	public ModelAndView editTag(@MyInject Customer customer,
			@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "tagId") Long tagId){
		ModelAndView modelAndView = new ModelAndView("/customer/tag/edit");
		short status = STATUS_FAILED;
		
		if(!com.winxuan.framework.util.StringUtils.isNullOrEmpty(tagName)){
			boolean isSuccess = customerService.updateFavoriteTag(customer, tagId, tagName);
			if(isSuccess){
				status = STATUS_SUCESS;
			}
		}
		modelAndView.addObject(STATUS, status);
		return modelAndView;
	}
}
