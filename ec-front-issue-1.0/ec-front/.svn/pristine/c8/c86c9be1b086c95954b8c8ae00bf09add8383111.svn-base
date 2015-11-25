package com.winxuan.ec.front.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhousl
 *
 * 2013-4-19
 */
@Controller
@RequestMapping(value="/useragent")
public class UserAgentController {

	@RequestMapping(value="", method=RequestMethod.GET)
	public ModelAndView getAgent(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/app/view");
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		if(userAgent.contains("android")){
			mav.addObject("os", "android");
		}else if(userAgent.contains("iphone") || userAgent.contains("ipad")){
			mav.addObject("os", "ios");
		}
		return mav;
	}
}
