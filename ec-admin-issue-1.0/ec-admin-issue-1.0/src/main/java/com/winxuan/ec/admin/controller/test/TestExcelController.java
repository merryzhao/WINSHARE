/*
 * @(#)TestExcelController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.customer.CustomerAddress;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.util.ImageService;

/**
 * 测试Excel数据导出
 * @author  HideHai
 * @version 1.0,2011-8-22
 */
@Controller
@RequestMapping(value="/test/excel")
public class TestExcelController {
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public ModelAndView findTestData(){
		ModelAndView modelAndView = new ModelAndView("/test/test");
		Customer customer = customerService.get(1l);
		List<CustomerAddress> addresses = customer.getAddressList();
		CustomerAddress address  = new CustomerAddress();
		address.setId(0L);
		address.setAddress("测试添加");
		addresses.add(address);
		modelAndView.addObject("name", "HideHai");
		modelAndView.addObject("addresses", addresses);
		return modelAndView;
	}
	
	@RequestMapping(value="/batchzoom", method=RequestMethod.GET)
	public ModelAndView testBatchZoom(@RequestParam("src")String srcDir){
	    ModelAndView view = new ModelAndView("test/testBatchImage");
	    Map<String, String> error = imageService.batchZoomImage(srcDir);
	    view.addObject("errorMap", error);
	    return view;
	}
	
	@RequestMapping(value="/zoom", method=RequestMethod.GET)
	public ModelAndView testZoom(@RequestParam("src")String srcDir){
	    ModelAndView view = new ModelAndView("test/testBatchImage");
	    try {
	        Product product = new Product();
	        product.setId(Long.parseLong("1200105266"));
            imageService.zoomFile(ImageIO.read(new File(srcDir)), product);
            view.addObject("msg", "操作成功");
        }
        catch (IOException e) {
            view.addObject("msg", "操作失败:" + e.getMessage());
            view.addObject("errorMap", e);
        }
	    return view;
	}

}

