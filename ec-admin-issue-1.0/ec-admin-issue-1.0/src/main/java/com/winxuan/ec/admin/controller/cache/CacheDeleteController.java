package com.winxuan.ec.admin.controller.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.framework.cache.CacheManager;


/**
 * 
 * @author jiangyunjun
 *
 */
@Controller
@RequestMapping("/cache")
public class CacheDeleteController {
	
	private static final String RESLUT_VIEW = "/cache/result";

	private static final String SUCCESS = "success";

	private static final String MESSAGE = "message";

	private static final Log LOG = LogFactory.getLog(CacheDeleteController.class);
	
	@Autowired
	@Qualifier("objectCacheManager")
	private CacheManager objectCacheManager;
	
	@Autowired
	@Qualifier("cacheManager")
	private CacheManager fragmentCacheManager;
	
	@RequestMapping("/deleteCache")
	public void toDeleteCache(){
	}

	@RequestMapping("/deleteObjectCache")
	public ModelAndView deleteObjectCache(String objectCacheKeyPrefix, long minId, long maxId) {
		ModelAndView mav = new ModelAndView(RESLUT_VIEW);
		if(minId > maxId){
			mav.addObject(MESSAGE, "minId必须小于maxId");
			return mav;
		}
		if(StringUtils.isEmpty(objectCacheKeyPrefix)){
			mav.addObject(MESSAGE, "类名不能为空");
			return mav;
		}
		for(long indexId = minId ; indexId <= maxId ; indexId++){
			String realOjbectCacheKey = generateCacheKey(objectCacheKeyPrefix, indexId);
			try {
				objectCacheManager.remove(realOjbectCacheKey);
			} catch (Exception e) {
				LOG.error("deleting object cache " + realOjbectCacheKey + " error", e);
			}
		}
		mav.addObject(MESSAGE, SUCCESS);
		return mav;
	}
	
	@RequestMapping("/deleteFragmentCache")
	public ModelAndView deleteFragmentCache(String fragmentKey) {
		ModelAndView mav = new ModelAndView(RESLUT_VIEW);
		if(StringUtils.isEmpty(fragmentKey)){
			mav.addObject(MESSAGE, "片段缓存key不能为空");
			return mav;
		}
		try {
			fragmentCacheManager.remove(fragmentKey);
		} catch (Exception e) {
			LOG.error("deleting fragment cache " + fragmentKey + " error!", e);
		}
		mav.addObject(MESSAGE, SUCCESS);
		return mav;
	}

	private String generateCacheKey(String objectCacheKeyPrefix, long indexId) {
		return objectCacheKeyPrefix + ":0:" + indexId;
	}

	public void setObjectCacheManager(CacheManager objectCacheManager) {
		this.objectCacheManager = objectCacheManager;
	}

	public void setFragmentCacheManager(CacheManager fragmentCacheManager) {
		this.fragmentCacheManager = fragmentCacheManager;
	}
}
