package com.winxuan.ec.front.controller.memcache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.service.removecache.RemoveCacheService;

/**
 * description
 * 
 * @author xuan jiu dong
 * @version 2011-1-5
 */
@Controller
@RequestMapping(value = "/memcache")
public class StatusController {
	
	private static final Log LOG = LogFactory.getLog(StatusController.class);

	@Autowired
	private RemoveCacheService removeCacheService;
	
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public ModelAndView clearCache(@RequestParam(value="url",required=true) String url) throws Exception{
		ModelAndView modelAndView = new ModelAndView("/memcache/status");
		boolean result = false;
		try {
			result = removeCacheService.removeCacheByCacheUrl(url);
		} catch (Exception e) {
			LOG.error("clear the cache Exception,url:" + url, e);
			result = false;
		}
		modelAndView.addObject("message", result);
		return modelAndView;
	}
	
	

}
