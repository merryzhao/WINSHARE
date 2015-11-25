/*
 * @(#)ShoppingcartController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.mall;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.cms.support.service.SupportService;
 

/**
 * description
 * 
 * @author xuan jiu dong
 * @version 1.0,2011-11-9
 */
@Controller
@RequestMapping(value = "/mall")
public class MallController {
	
	@Autowired
	private SupportService supportService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view() {
		
		ModelAndView modelAndView =null;
		//增加cms页面支持判断	add by ztx 2015-01-28
		if (supportService.havePage("mall")) {
			modelAndView = new ModelAndView("/mall/cmsindex");
			modelAndView.addObject("page", "mall");
		} else {
			modelAndView = new ModelAndView("/mall/index");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/{page}", method=RequestMethod.GET)
	public ModelAndView mallProductsList(@PathVariable("page") String page){
		ModelAndView view = new ModelAndView("/mall/list_index");
		view.addObject("page", page);
		return view;
	}
}
