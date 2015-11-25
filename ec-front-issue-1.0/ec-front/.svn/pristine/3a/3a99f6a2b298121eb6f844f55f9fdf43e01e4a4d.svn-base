/*
 * @(#)ProductController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.product.ProductRecommendationService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.web.enumerator.RecommendMode;

/**
 * description
 * @author  huangyixiang
 * @version 2011-11-30
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductRecommendationService productRecommendationService;

	/**
	 * 畅销榜
	 * @param id
	 * @param pagination
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/bestSellers", method = RequestMethod.GET)
	public ModelAndView getBestSellers(@RequestParam Long category,@RequestParam(required = false) Long type) {
		ModelAndView modelAndView = new ModelAndView("/product/bestSellers");
		Category c = categoryService.get(category);
		
		
		int size = 15;
		if(Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(type)){
			size = 9;
		}
		List<ProductSale> retList = null;
		String[] categorys = c.getCode().split("\\.");
		if(Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(type)){
			if(categorys.length > MagicNumber.TWO){
				c = categoryService.get(Long.valueOf(categorys[1]));
			}
			 retList = productService.findProductSaleByBestSellers(c,type,size);
			 if(c != null){
				 int resultSize = 0;
					if(retList != null){
						resultSize = retList.size();
					}
					if(resultSize < 9){
						categorys = c.getCode().split("\\.");
						c = categoryService.get(Long.valueOf(categorys[0]));
						List<ProductSale> appendList = productService.findProductSaleByBestSellers(c,type,size-resultSize);
						if(retList == null){
							retList = appendList;
						}
						else{
							retList.addAll(appendList);
						}
					}
			 }
		}else{
			retList = productService.findProductSaleByBestSellers(c,type,size);
		}
	  
		modelAndView.addObject("list",retList);
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/recommend", method = RequestMethod.GET)
	public ModelAndView recommend(@RequestParam Long pid,
			@RequestParam(required = false,defaultValue = "6") Integer count,
			@RequestParam short mode) {
		ModelAndView modelAndView = new ModelAndView("/product/recommend");
		ProductSale productSale = productService.getProductSale(pid);
		short status = 1;
		String message = "" ;
		if(productSale == null){
			status = 2;
			message = "没有这个商品";
		}
		RecommendMode recommendMode = null;
		if(RecommendMode.BUY.getMode() == mode){
			recommendMode = RecommendMode.BUY;
		}
		else if(RecommendMode.VISIT.getMode() == mode){
			recommendMode = RecommendMode.VISIT;
		}
		else {
			status = MagicNumber.THREE;
			message = "mode错误";
		}
		modelAndView.addObject("status",status);
		modelAndView.addObject("message",message);
		modelAndView.addObject("list",productRecommendationService.findRecommendByProductSale(productSale, recommendMode, count));
		return modelAndView;
	}
	

	
}
