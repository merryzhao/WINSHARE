/*
 * @(#)NotifyController
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerNotify;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.customer.CustomerServiceImpl;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author huangyixiang
 * @version 1.0,2011-10-24
 */
@Controller
@RequestMapping(value = "/customer/notify")
public class NotifyController {

	private static final String STATUS = "status";
	private static final short STATUS_SUCESS = 1;
	private static final short STATUS_FAILED = 2;
	private static final short STATUS_PARAM = 3;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET )
	public ModelAndView add(@MyInject Customer customer,
			@RequestParam(value = "p") Long productSaleId,
			@RequestParam(value = "type") Long type){
		ModelAndView modelAndView = new ModelAndView("/customer/notify/add");
		
		ProductSale productSale = productService.getProductSale(productSaleId);
		Code typeCode = codeService.get(type);
		if(productSale == null || typeCode == null){
			modelAndView.addObject(STATUS, STATUS_PARAM);
			return modelAndView;
		}
		CustomerNotify notify = customerService.getNotify(customer, productSale,typeCode);
		if(notify != null){
			modelAndView.addObject(STATUS, STATUS_FAILED);
			return modelAndView;
		}
		CustomerNotify customerNotify = new CustomerNotify();
		customerNotify.setCustomer(customer);
		customerNotify.setProductSale(productSale);
		customerNotify.setAddTime(new Date());
		customerNotify.setAddStatus(productSale.getSaleStatus());
		customerNotify.setAddListPrice(productSale.getProduct().getListPrice());
		customerNotify.setAddSellPrice(productSale.getSalePrice());
		customerNotify.setSort(productSale.getProduct().getSort());
		customerNotify.setType(typeCode);
		customerNotify.setNotified(false);
		customerService.addNotify(customerNotify);
		modelAndView.addObject(STATUS, STATUS_SUCESS);
		return modelAndView;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST )
	public ModelAndView addNofity(@MyInject Customer customer,
			@RequestParam(value = "p") Long productSaleId,
			@RequestParam(value = "expectedprice") BigDecimal expectedprice,
			@RequestParam(value = "email") String email,String phone,
			@RequestParam(value = "type") Long type){
		ModelAndView modelAndView = new ModelAndView("/customer/notify/add");
		
		ProductSale productSale = productService.getProductSale(productSaleId);
		Code typeCode = codeService.get(type);
		if(productSale == null || typeCode == null||expectedprice==null){
			modelAndView.addObject(STATUS, STATUS_PARAM);
			return modelAndView;
		}
		CustomerNotify notify = customerService.getNotify(customer, productSale,typeCode);
		//重复点击，视为更新
		if(notify != null){
			notify.setExpectedprice(expectedprice);
			notify.setAddTime(new Date());
			customerService.addNotify(notify);
			modelAndView.addObject(STATUS, STATUS_FAILED);
			return modelAndView;
		}
		CustomerNotify customerNotify = new CustomerNotify();
		customerNotify.setCustomer(customer);
		customerNotify.setProductSale(productSale);
		customerNotify.setAddTime(new Date());
		customerNotify.setAddStatus(productSale.getSaleStatus());
		customerNotify.setAddListPrice(productSale.getProduct().getListPrice());
		customerNotify.setAddSellPrice(productSale.getSalePrice());
		customerNotify.setSort(productSale.getProduct().getSort());
		customerNotify.setType(typeCode);
		customerNotify.setNotified(false);
		customerNotify.setExpectedprice(expectedprice);
		customerService.addNotify(customerNotify);
		modelAndView.addObject(STATUS, STATUS_SUCESS);
		return modelAndView;
	}

	@RequestMapping(value = "/arrival", method = RequestMethod.GET )
	public ModelAndView listArrival(@MyInject Customer customer,
			@RequestParam(value = "hasStock" , required = false) Boolean hasStock,
			@RequestParam(value = "sort" , required = false) Long sortId,
			@RequestParam(value = "category" , required = false) Long categoryId,
			@RequestParam(value = "addTime" , required = false) Integer addTimeId,
			@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/notify/arrival");
		
		//Category category = categoryId == null ? null : categoryService.get(categoryId); 暂时不做
		Category category = null;
		Code sort = (sortId == null || sortId == -1) ? null : codeService.get(sortId);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customer", customer);
		parameters.put("flagArrival", hasStock);
		parameters.put("sort", sort);
		parameters.put("category", category);
		if(addTimeId != null){
			setAddTime(parameters,addTimeId); 
		}
		
		List<CustomerNotify> list = customerService.findArrival(parameters, (short)0, pagination);
		
		Code type = codeService.get(Code.ARRIVAL);
		Map<Integer,Integer> countDate = customerService.countNotifyByDate(customer,type);
		modelAndView.addObject("countDate",countDate);
		modelAndView.addObject("arrivalList", list);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.DELETE )
	public ModelAndView remove(@MyInject Customer customer,
			@RequestParam(value = "p") Long[] productSaleIds,
			@RequestParam(value = "type") Long type){ 
		ModelAndView modelAndView = new ModelAndView("/customer/notify/delete");
		short status = STATUS_SUCESS;
		Code typeCode = codeService.get(type);
		if(typeCode == null){
			status = STATUS_PARAM;
		}
		for(Long productSaleId : productSaleIds){
			ProductSale productSale = productService.getProductSale(productSaleId);
			if(!customerService.removeNotify(customer, productSale , typeCode)){
				status = STATUS_FAILED;
			}
		}
		modelAndView.addObject(STATUS, status);
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.PUT )
	public ModelAndView update(@MyInject Customer customer,
			@RequestParam(value = "p") Long productSaleId){ 
		ModelAndView modelAndView = new ModelAndView("/customer/notify/update");
		short status = STATUS_SUCESS;
		ProductSale productSale = productService.getProductSale(productSaleId);
		CustomerNotify customerNotify = customerService.getNotify(customer, productSale,codeService.get(Code.ARRIVAL));
		if(customerNotify.isNotified()){
			customerService.updateNotify(customerNotify, false);
		}
		modelAndView.addObject(STATUS, status);
		return modelAndView;
	}
	
	@RequestMapping(value = "/priceReduce", method = RequestMethod.GET )
	public ModelAndView listPriceReduce(@MyInject Customer customer,
			@RequestParam(value = "reduced" , required = false) Boolean reduced,
			@RequestParam(value = "sort" , required = false) Long sortId,
			@RequestParam(value = "addTime" , required = false) Integer addTimeId,
			@RequestParam(value = "order" , required = false) Short order,
			@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/notify/price_reduce");
		final short orderMax = 4; 
		final short orderMin = 0;
		if(order == null ||   order > orderMax || order < orderMin){
			order = (short)0;
		}
		
		Code sort = (sortId == null || sortId == -1) ? null : codeService.get(sortId);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customer", customer);
		parameters.put("flagPriceReduce", reduced);
		if(reduced != null && reduced.equals(Boolean.TRUE)){
			parameters.put("flagArrival", Boolean.TRUE);
		}
		parameters.put("sort", sort);
		if(addTimeId != null){
			setAddTime(parameters,addTimeId); 
		}
		List<CustomerNotify> list = customerService.findPriceReduce(parameters, order, pagination);
		
		Code type = codeService.get(Code.PRICE_REDUCE);
		Map<Integer,Integer> countDate = customerService.countNotifyByDate(customer,type);
		modelAndView.addObject("countDate",countDate);
		modelAndView.addObject("priceReduceList", list);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	
	private void setAddTime(Map<String, Object> parameters,Integer addTimeId){
		final int threeMonth = -3;
		final int sixMonth = -6;
		Date addTime;
		Calendar c = Calendar.getInstance();
		String key = "addTimeMin";
		if(addTimeId.equals(CustomerServiceImpl.ONE_MONTH)){
			c.add(Calendar.MONTH, -1);
		} 
		else if(addTimeId.equals(CustomerServiceImpl.THREE_MONTH)){
			c.add(Calendar.MONTH, threeMonth);
		}
		else if(addTimeId.equals(CustomerServiceImpl.SIX_MONTH)){
			c.add(Calendar.MONTH, sixMonth);
		}
		else if(addTimeId.equals(CustomerServiceImpl.ONE_YEAR)){
			c.add(Calendar.YEAR, -1);
		}
		else if(addTimeId.equals(CustomerServiceImpl.ONE_YEAR_AGO)){
			c.add(Calendar.YEAR, -1);
			key = "addTimeMax";
		}
		else {
			return;
		}
		addTime = c.getTime();
		parameters.put(key, addTime);
	}
}
