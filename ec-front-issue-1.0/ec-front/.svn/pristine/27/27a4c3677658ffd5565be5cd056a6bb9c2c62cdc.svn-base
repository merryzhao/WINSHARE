/*
 * @(#)BookContoller.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.cms.support.service.SupportService;
 
/**
 * 图书频道
 * @author  HideHai
 * @version 1.0,2011-11-10
 */
@Controller
public class BookController {
	
	@Autowired
	private SupportService supportService;
	
	@RequestMapping(value="/book",method = RequestMethod.GET)
	ModelAndView view(){
		ModelAndView modelAndView = new ModelAndView("/book/index");
		return modelAndView;
	}
	
	@RequestMapping(value="/{page}",method = RequestMethod.GET)
	ModelAndView bookShopView(@PathVariable("page") String page){
		
		ModelAndView modelAndView =null;
		
		if (supportService.havePage(page)) {
			modelAndView = new ModelAndView("/book/bookshop/newindex");
		} else {
			modelAndView = new ModelAndView("/book/bookshop/index");
		}

		modelAndView.addObject("page", page);
		return modelAndView;
	}

}

