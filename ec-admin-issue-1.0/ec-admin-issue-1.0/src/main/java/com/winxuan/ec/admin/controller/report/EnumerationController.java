/*
 * @(#)EnumerationController.java
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
import com.winxuan.ec.model.report.Enumeration;
import com.winxuan.ec.service.report.ReportService;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-8-23
 */
@Controller
@RequestMapping("/report/enumeration")
public class EnumerationController {

	@Autowired
	ReportService reportService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newDataSource(@Valid EnumerationForm enumerationForm,
			BindingResult result) {
		ModelAndView mav = new ModelAndView("/report/enumeration_create");
		List<DataSource> dataSources = reportService.findDataSourceOnlyAvailable();
		mav.addObject("dataSources", dataSources);
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@Valid EnumerationForm enumerationForm,
			BindingResult result) {
		Enumeration enumeration = new Enumeration();
		enumeration.setAvailable(true);
		enumeration.setName(enumerationForm.getName());
		enumeration.setSql(enumerationForm.getSql());
		enumeration.setDataSource(reportService.getDataSourceById(enumerationForm.getDataSourceId()));
		reportService.createEnumeration(enumeration);
		ModelAndView mav = new ModelAndView("/report/success");
		return mav;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("/report/enumeration_list");
		List<Enumeration> enumerations = reportService.findEnumerationOnlyAvailable();
		mav.addObject("enumerations", enumerations);
		return mav;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("enumerationId") Long enumerationId) {
		Enumeration enumeration = reportService.getEnumerationById(enumerationId);
		EnumerationForm enumerationForm = new EnumerationForm();
		enumerationForm.setId(enumerationId);
		enumerationForm.setName(enumeration.getName());
		enumerationForm.setSql(enumeration.getSql());
		enumerationForm.setDataSourceId(enumeration.getDataSource().getId());
		ModelAndView mav = new ModelAndView("/report/enumeration_edit");
		List<DataSource> dataSources = reportService.findDataSourceOnlyAvailable();
		mav.addObject("dataSources", dataSources);
		mav.addObject("enumerationForm", enumerationForm);
		return mav;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@Valid EnumerationForm enumerationForm,
			BindingResult result) {
		Enumeration enumeration = new Enumeration(); 
		enumeration.setAvailable(true);
		enumeration.setId(enumerationForm.getId());
		enumeration.setName(enumerationForm.getName());
		enumeration.setSql(enumerationForm.getSql());
		enumeration.setDataSource(reportService.getDataSourceById(enumerationForm.getDataSourceId()));
		reportService.updateEnumeration(enumeration);
		ModelAndView mav = new ModelAndView("/report/success");
		return mav;
	}
	

	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("enumerationId") Long enumerationId) {
		Enumeration enumeration = reportService.getEnumerationById(enumerationId);
		enumeration.setAvailable(false);
		reportService.updateEnumeration(enumeration);
		ModelAndView mav = new ModelAndView("redirect:/report/enumeration/list");
		return mav;
	}
}
