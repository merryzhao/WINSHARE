/*
 * @(#)NewsController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.cm.News;
import com.winxuan.ec.service.news.NewsService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.web.resolver.annotation.PaginationDefinition;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  huangyixiang
 * @version 2011-12-8
 */
@Controller
public class NewsController {
	
	@Autowired
	NewsService newsService;
	
	@RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable long id){
		ModelAndView modelAndView = new ModelAndView("/news/view");
		modelAndView.addObject("news", newsService.find(id));
		return modelAndView;
	}
	

	@RequestMapping(value = "/news" ,  method = RequestMethod.GET)
	public ModelAndView listNews(@PaginationDefinition(size = MagicNumber.TEN) Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/news/news");
		
		List<News> list = newsService.find((short)MagicNumber.ZERO, (short)MagicNumber.ZERO, pagination);
		
		modelAndView.addObject("list", list);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	@RequestMapping(value = "/prom" ,  method = RequestMethod.GET)
	public ModelAndView listProm(@PaginationDefinition(size = MagicNumber.TEN) Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/news/prom");
		
		List<News> list = newsService.find((short)MagicNumber.ONE, (short)MagicNumber.ZERO, pagination);
		
		modelAndView.addObject("list", list);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
}
