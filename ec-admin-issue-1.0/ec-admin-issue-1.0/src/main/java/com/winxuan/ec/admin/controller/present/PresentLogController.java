/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.admin.controller.present;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winxuan.ec.service.present.PresentService;

/**
 * description
 * @author liyang
 * @version 1.0,2011-8-31
 */
@Controller
@RequestMapping("/presentlog")
public class PresentLogController {

	@Autowired
	PresentService presentService;
}
