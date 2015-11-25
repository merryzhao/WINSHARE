package com.winxuan.ec.admin.controller.removecache;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.service.removecache.RemoveCacheService;
import com.winxuan.framework.cache.refresh.CacheRefreshService;


/**
 * 缓存清理
 * @author sunflower
 *
 */
@Controller
@RequestMapping("/removeCache")
public class RemoveCacheController {

	private static final  String DELETE_CACHE_SUCCESS = "缓存删除成功";
	private static final  String DELETE_CACHE_FAIL = "缓存删除失败";
	private static final Log LOG = LogFactory.getLog(RemoveCacheController.class);
	
	@Autowired
	RemoveCacheService removeCacheService;
	
	@Autowired
	CacheRefreshService cacheRefreshService;
	
	@RequestMapping(value = "/flushAll", method = RequestMethod.GET)
	public ModelAndView flushAll(){
		ModelAndView modelAndView = new ModelAndView("/removecache/remove");
		try {
			removeCacheService.flushAll();
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, DELETE_CACHE_SUCCESS);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_INTERNAL_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, DELETE_CACHE_FAIL);
		}
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = "/removeUrl", method = RequestMethod.GET)
	public ModelAndView removeCacheByCacheUrl(@RequestParam(value="url",required=true) String url) {
		
		ModelAndView modelAndView = new ModelAndView("/removecache/remove");
		try {
			//removeCacheService.removeCacheByCacheUrl(url);
			cacheRefreshService.refreshPageCache(url);
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, DELETE_CACHE_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_INTERNAL_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, DELETE_CACHE_FAIL);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/removeKey", method = RequestMethod.GET)
	public ModelAndView removeCacheByCacheKey(@RequestParam(value="key",required=true) String key) {

		ModelAndView modelAndView = new ModelAndView("/removecache/remove");
		try {
			removeCacheService.removeCacheByCacheKey(key);
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, DELETE_CACHE_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_INTERNAL_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, DELETE_CACHE_FAIL);
		}
		return modelAndView;
	}
}
