/*
 * @(#)NewsController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.cm.News;
import com.winxuan.ec.service.news.NewsService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.web.resolver.annotation.PaginationDefinition;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author xuan jiu dong
 * @version 2011-12-30
 */
@Controller
@RequestMapping(value = "/news")
public class NewsController {

	@Autowired
	NewsService newsService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listNews(@MyInject Pagination pagination) {
		ModelAndView modelAndView = new ModelAndView("/news/news");
		List<News> list = newsService.find((short) MagicNumber.ZERO,(short) MagicNumber.ZERO, pagination);
		modelAndView.addObject("list", list);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
	public ModelAndView delNews(@PathVariable("id") Long id,
			@PaginationDefinition(size = MagicNumber.TEN) Pagination pagination) {
		ModelAndView modelAndView = new ModelAndView("/fragment/news/saveOrUpdate");
		this.newsService.delNews(id);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
}
