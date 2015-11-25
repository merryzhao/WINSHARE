/*
 * @(#)DataSourceController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.report;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.report.DataSource;
import com.winxuan.ec.service.report.ReportService;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-8-23
 */
@Controller
@RequestMapping("/report/datasource")
public class DataSourceController {
	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newDataSource(@Valid DataSourceForm dataSourceForm,
			BindingResult result) {
		ModelAndView mav = new ModelAndView("/report/datasource_create");
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@Valid DataSourceForm dataSourceForm,
			BindingResult result) {
		DataSource dataSource = new DataSource();
		dataSource.setName(dataSourceForm.getName());
		dataSource.setClassName(dataSourceForm.getClassName());
		dataSource.setUrl(dataSourceForm.getUrl());
		dataSource.setUserName(dataSourceForm.getUserName());
		dataSource.setPassword(dataSourceForm.getPassword());
		dataSource.setAvailable(true);
		reportService.createDataSource(dataSource);
		ModelAndView mav = new ModelAndView("/report/success");
		return mav;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("/report/datasource_list");
		List<DataSource> dataSources = reportService.findDataSourceOnlyAvailable();
		mav.addObject("dataSources", dataSources);
		return mav;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("dataSourceId") Long dataSourceId) {
		DataSource dataSource = reportService.getDataSourceById(dataSourceId);
		DataSourceForm dataSourceForm = new DataSourceForm();
		dataSourceForm.setName(dataSource.getName());
		dataSourceForm.setClassName(dataSource.getClassName());
		dataSourceForm.setUrl(dataSource.getUrl());
		dataSourceForm.setUserName(dataSource.getUserName());
		dataSourceForm.setPassword(dataSource.getPassword());
		dataSourceForm.setId(dataSourceId);
		ModelAndView mav = new ModelAndView("/report/datasource_edit");
		mav.addObject("dataSourceForm", dataSourceForm);
		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@Valid DataSourceForm dataSourceForm,
			BindingResult result) {
		DataSource dataSource = new DataSource();
		dataSource.setId(dataSourceForm.getId());
		dataSource.setName(dataSourceForm.getName());
		dataSource.setClassName(dataSourceForm.getClassName());
		dataSource.setUrl(dataSourceForm.getUrl());
		dataSource.setUserName(dataSourceForm.getUserName());
		dataSource.setPassword(dataSourceForm.getPassword());
		dataSource.setAvailable(true);
		reportService.updateDataSource(dataSource);
		ModelAndView mav = new ModelAndView("/report/success");
		return mav;
	}

	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("dataSourceId") Long dataSourceId) {
		DataSource dataSource = reportService.getDataSourceById(dataSourceId);
		dataSource.setAvailable(false);
		reportService.updateDataSource(dataSource);
		ModelAndView mav = new ModelAndView("redirect:/report/datasource/list");
		return mav;
	}

}
