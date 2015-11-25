/*
 * @(#)SnippetsController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.cm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.cm.Content;
import com.winxuan.ec.model.cm.Fragment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.service.cm.CmService;
import com.winxuan.ec.service.product.ProductSaleRankService;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-26
 */
@Controller
@RequestMapping(value = "/fragment")
public class FragmentController {

	private static final String JSON_TEMPLATE_PRODUCT = "/fragment/product";
	private static final String JSON_TEMPLATE_NEWS = "/fragment/news";
	private static final String JSON_TEMPLATE_LINK = "/fragment/link";
	private static final String JSON_TEMPLATE_TEXT = "/fragment/text";
	private static final String JSON_TEMPLATE_RANDOM = JSON_TEMPLATE_PRODUCT;
	private static final Fragment BLANK_FRAGMENT = new Fragment();
	private static final List<Content> BLANK_ELEMENTS = new ArrayList<Content>();
	private static final Log LOG = LogFactory.getLog(FragmentController.class);
	

	@Autowired
	private CmService cmService;
	
	@Autowired
	private ProductSaleRankService productSaleRankService;
	
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public ModelAndView config(){
		ModelAndView mav = new ModelAndView("/fragment/config");
		mav.addObject("configlist",this.cmService.findCmsConfig());
		return mav;
	}
	
	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView render(@PathVariable("id") Long id,
			@RequestParam(required = false) String format) {
		Fragment fragment = cmService.getFragment(id);
		if (fragment == null) {
			LOG.warn("cant't found fragment, id:" + id);
		}
		return this.setView(fragment, format);
	}

	@RequestMapping(value = "/{page}/{index}", method = RequestMethod.GET)
	public ModelAndView render(@PathVariable("page") String page,
			@PathVariable("index") Long index,
			@RequestParam(required = false) String format) {
		Fragment fragment = cmService.getFragmentByContext(new Fragment(page,
				index));
		if (fragment == null) {
			LOG.warn("cant't found fragment, index:" + index + " page" + page);
		}
		return this.setView(fragment, format);
	}

	private ModelAndView setView(Fragment fragment, String format) {
		Map<String, Object> model = new HashMap<String, Object>();
		String viewName = null;
		if (fragment != null) {
			List<Content> contentList = cmService.findContent(fragment);
			if(Fragment.TYPE_PRODUCT == fragment.getType() && CollectionUtils.isNotEmpty(contentList)){
				for(Content content : contentList){
					ProductSale productSale = (ProductSale)content;
					productSale.getProductTransient().setAvgRank(productSaleRankService.getProductRankAverage(productSale));
					productSale.getProductTransient().setRankCount(productSaleRankService.findRankCount(productSale));
				}
			}
			if(CollectionUtils.isEmpty(contentList)){
				contentList = BLANK_ELEMENTS;
			}
			
			viewName = fragment.getJspFile();
			if ("json".equals(format) || "jsonp".equals(format)) {
				short type = fragment.getType();
				if (type == Fragment.TYPE_PRODUCT) {
					viewName = JSON_TEMPLATE_PRODUCT;
				} else if (type == Fragment.TYPE_NEWS) {
					viewName = JSON_TEMPLATE_NEWS;
				} else if (type == Fragment.TYPE_LINK) {
					viewName = JSON_TEMPLATE_LINK;
				} else if (type == Fragment.TYPE_TEXT) {
					viewName = JSON_TEMPLATE_TEXT;
				}else if (type == Fragment.TYPE_RANDOM){
					viewName = JSON_TEMPLATE_RANDOM;
				}
				model.put("config", cmService.getFragmentConfig(fragment).toString());
			}else{
				model.put("config", cmService.getFragmentConfig(fragment));
			}
			

			model.put("fragment", fragment);
			model.put("contentList", contentList);
			model.put(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
			//img 渲染尺寸
			model.put("size",ImageSize.getImageSize(fragment.getImageType()));
		} else {
			model.put("fragment", BLANK_FRAGMENT);
			model.put("contentList", BLANK_ELEMENTS);
			model.put(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		model.put("serverTime", new Date());
		return new ModelAndView(viewName, model);
	}

}
