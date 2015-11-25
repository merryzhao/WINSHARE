/*
 * @(#)TrackController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.cps;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-28
 */
@Controller
@RequestMapping(value = "/track")
public class TrackController {
	private static final Log LOG =LogFactory.getLog(TrackController.class);

	@Autowired
	@Qualifier("cpsFactory")
	private CpsFactory cpsFactory;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView track(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Cps cps = cpsFactory.get(id);
			if(cps ==null){
				ModelAndView modelAndView =new ModelAndView("/cps/error");
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"不存在该联盟");
				return modelAndView;
			}
		try {	
			cps.track(request, response);
		} catch (CpsException e) {
			ModelAndView modelAndView =new ModelAndView("/cps/error");
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
			LOG.info(e.getMessage());
			return modelAndView;
		}
		return null;
	}
}
