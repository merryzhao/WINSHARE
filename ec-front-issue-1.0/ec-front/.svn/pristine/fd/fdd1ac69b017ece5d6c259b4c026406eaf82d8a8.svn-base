/*
 * @(#)BoughtController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerBought;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.StringUtils;

/**
 * 用户已购商品controller
 * 
 * @author liuan
 * @version 1.0,2011-9-27
 */
@Controller
@RequestMapping(value = "/customer/bought")
public class BoughtController {
	
	@Autowired
	private CodeService codeService;

	@Autowired
	private CustomerService customerService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(
			@MyInject Customer customer,
			@Valid BoughtForm boughtForm,
			@MyInject Pagination pagination) {
		ModelAndView modelAndView = new ModelAndView("/customer/bought/list");
		Code sort = boughtForm.getSort() == null ? null : new Code(boughtForm.getSort());
		Map<String,Object> parameters = new HashMap<String,Object>();
		if(!StringUtils.isNullOrEmpty(boughtForm.getProductName())){
			parameters.put("productName", "%"+boughtForm.getProductName()+"%");
		}
		parameters.put("startDate", boughtForm.getStartDate());
		parameters.put("endDate", boughtForm.getEndDate());
		List<CustomerBought> boughtList = customerService.findBought(customer,
				sort, parameters, boughtForm.getOrderBy(), pagination);
		Map<String,Object> selectedParameters = new HashMap<String,Object>();
		selectedParameters.put("sort", boughtForm.getSort());
		selectedParameters.put("orderBy", boughtForm.getOrderBy());
		selectedParameters.put("pruductName", boughtForm.getProductName());
		selectedParameters.put("startDate", boughtForm.getStartDate());
		selectedParameters.put("endDate", boughtForm.getEndDate());
		boughtForm.setTotal(boughtForm.getTotal() == null ? String.valueOf(pagination.getCount()) : boughtForm.getTotal());
		modelAndView.addObject("selectedParameters", selectedParameters);
		modelAndView.addObject("compositorList", codeService.get(Code.CUSTOMER_BOUGHT_COMPOSITOR).getAvailableChildren());		
		modelAndView.addObject("boughtList", boughtList);
		modelAndView.addObject("pagination", pagination);
		modelAndView.addObject("total", boughtForm.getTotal());
		return modelAndView;
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ModelAndView listForProduct(@MyInject Customer customer, @PathVariable("id")String id){
		ModelAndView modelAndView = new ModelAndView("/customer/bought/order_commenting");
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("orderId", id);
		List<CustomerBought> boughtList = customerService.findBought(customer,null, parameters, (short) 0, null);
		modelAndView.addObject("boughtList", boughtList);
		modelAndView.addObject("total", boughtList.size());
		return modelAndView;
	}
}
