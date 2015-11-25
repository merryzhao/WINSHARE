package com.winxuan.ec.front.controller.customer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.front.config.HttpUtil;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.interceptor.MyInject;


/*
 * @(#)CustomerController.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

/**
 * 用户
 * 
 * @author HideHai
 * @version 1.0,2011-7-15
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerDigitalController {



	Logger log = LoggerFactory.getLogger(getClass());

	

	/**
	 * 用户电子书书架
	 */
	@RequestMapping(value = "/digital/shelf", method = RequestMethod.GET)
	public ModelAndView digitalShelf(@MyInject Customer customer) {
		ModelAndView modelAndView = new ModelAndView("/customer/digital/bookShelf");
		Long userId=customer.getId();
		modelAndView.addObject("userId",userId);
		modelAndView.addObject("token", HttpUtil.checkValidate(userId));
		return modelAndView;
	}
	/**
	 * 用户电子书订单
	 */
	@RequestMapping(value = "/digital/order", method = RequestMethod.GET)
	public ModelAndView getCurrentCustom(@MyInject Customer customer) {
		ModelAndView modelAndView = new ModelAndView("/customer/digital/orderList");
		
		return modelAndView;
	}
	/**
	 * 电子书书籍兑换券
	 */
	@RequestMapping(value = "/digital/bookexchange", method = RequestMethod.GET)
	public ModelAndView bookexchange(@MyInject Customer customer) {
		ModelAndView modelAndView = new ModelAndView("/customer/digital/bookexchange");
		Long userId=customer.getId();
		modelAndView.addObject("userId",userId);
		modelAndView.addObject("token", HttpUtil.checkValidate(userId));
		return modelAndView;
	}
}
