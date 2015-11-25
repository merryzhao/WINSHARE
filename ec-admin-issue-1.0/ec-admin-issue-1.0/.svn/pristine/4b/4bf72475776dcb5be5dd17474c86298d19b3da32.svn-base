/*
 * @(#)CMSController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.cms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winxuan.framework.util.web.WebContext;

/**
 * description
 * @author  huangyixiang
 * @version 2011-10-28
 */
@Controller
@RequestMapping(value="/cms")
public class CMSController {
	
	private static String scripts = "";
	private static final String[] SCRIPT = {
			"http://static.winxuancdn.com/js/maintain.js"
	};
	
	static {
		for(String script : SCRIPT){
			scripts += "<script type=\"text/javascript\" src=\"" + script + "\"></script>";
		}
		scripts += "</body>";
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public void view(@RequestParam String url) throws IOException{
		HttpServletResponse response = WebContext.currentResponse();
		if(!url.startsWith("http://")){
			response.sendError(HttpStatus.SC_BAD_REQUEST);
			return;
		}
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		int status = client.executeMethod(method);
		
		if(status != HttpStatus.SC_OK){
			response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		String html = method.getResponseBodyAsString();
		method.releaseConnection();
		html = html.replaceFirst("<head>", "<head><base href ='"+ url +"'/>");
		html = html.replaceFirst("</body>", scripts);
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		PrintWriter p = response.getWriter();
		p.write(html);
		p.flush();
		p.close();
	}
		

}
