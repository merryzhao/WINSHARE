/*
 * @(#)LogController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.controller.quartz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-11-16
 */
@Controller
@RequestMapping(value="/log")
public class LogController {

	private static final String SELECT_SQL_LIMIT = "SELECT * FROM quartz_log WHERE infolevel='ERROR' ORDER BY stamp DESC limit ?,?";
	private static final int SIZE = 100;
	
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;

	@RequestMapping(value="/error",method = RequestMethod.GET)
	public ModelAndView error(Integer init){
		if(init == null){
			init = 0;
		}
		ModelAndView modelAndView = new ModelAndView("/log/error");
		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(SELECT_SQL_LIMIT,new Object[]{init,SIZE});
		modelAndView.addObject("list", list);
		modelAndView.addObject("nowTime", new Date());
		modelAndView.addObject("init",init);
		return modelAndView;
	}

}

