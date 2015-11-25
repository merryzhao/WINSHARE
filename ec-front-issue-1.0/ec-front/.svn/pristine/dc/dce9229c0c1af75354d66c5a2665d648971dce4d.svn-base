/*
 * @(#)ShoppingcartController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.media;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
 

/**
 * description
 * 
 * @author xuan jiu dong
 * @version 1.0,2011-11-9
 */
@Controller
@RequestMapping(value = "/media")
public class MediaController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view() {
		return  new ModelAndView("/media/index");
	}
	
	@RequestMapping(value="/{page}", method = RequestMethod.GET)
	public ModelAndView music(@PathVariable("page") String page){
		ModelAndView view = new ModelAndView("/media/music_index");
		view.addObject("page", page);
		return view;
	}
}
