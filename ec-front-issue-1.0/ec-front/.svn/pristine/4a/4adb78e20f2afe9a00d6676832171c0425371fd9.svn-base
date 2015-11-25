/*
 * @(#)TestExcelController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 测试Excel数据导出
 * @author  HideHai
 * @version 1.0,2011-8-22
 */
@Controller
@RequestMapping(value="/test/excel")
public class TestExcelController {
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public ModelAndView findTestData(){
		ModelAndView modelAndView = new ModelAndView("/test/test");
		modelAndView.addObject("name", "HideHai");
		return modelAndView;
	}

}

