/*
 * @(#)Code.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.controller;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winxuan.ec.model.verifycode.VerifyCode;
import com.winxuan.ec.service.verifycode.VerifyCodeImageFactory;
import com.winxuan.ec.service.verifycode.VerifyCodeService;

/**
 * description
 * 
 * @author huangyixiang
 * @version 2011-8-12
 */
@Controller
@RequestMapping(value = "/verifyCode")
public class VerifyCodeController {
    
	@Autowired
	private VerifyCodeService verifyCodeService;
	
	@Autowired
	@Qualifier(value="letterVerifyCodeImageFactory")
	private VerifyCodeImageFactory verifyCodeImageFactory;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public void get(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 首先设置页面不缓
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		
		VerifyCode supportVerifyCode = verifyCodeImageFactory.createImage();
		verifyCodeService.generateVerifyCodeCookie(supportVerifyCode.getVerifyCode());
		// 输出图形到页
		ImageIO.write(supportVerifyCode.getImage(), "png", response.getOutputStream());
	}


}
