/*
 * @(#)FragmentController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.cm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.cm.CmsConfig;
import com.winxuan.ec.model.cm.Fragment;
import com.winxuan.ec.service.cm.CmService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author cast911
 * @description:
 * @lastupdateTime:2012-10-15下午07:58:31 -_-!
 */
@Controller
@RequestMapping(value = "/fragment")
public class CmsConfigController {

	//private static final Log LOG = LogFactory.getLog(CmsConfigController.class);

	@Autowired
	private CmService cmService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView list(FragmentForm fragmentForm,
			@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("fragment/list");
		Map<String, Object> parameters = fragmentForm.generateQueryMap();
		mav.addObject("fragmentlist", cmService.find(parameters, pagination));
		mav.addObject("pagination", pagination);
		return mav;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView update() {

		return null;
	}

	@RequestMapping(value = "/config/list/{fragmentid}", method = { RequestMethod.GET })
	public ModelAndView fragmentConfigList(@PathVariable("fragmentid") Long id) {
		Fragment fragment = cmService.getFragment(id);
		ModelAndView mav = new ModelAndView("fragment/config/list");
		mav.addObject("cmsConfigList", fragment.getCmsCofig());
		mav.addObject("fragment", fragment);
		return mav;
	}

	@RequestMapping(value = "/cmsconfig/{id}", method = { RequestMethod.GET })
	public ModelAndView getFragmentConfig(
			@PathVariable("id") Long id,
			@RequestParam(value = "fragmentId", required = true) String fragmentId) {
		CmsConfig cmsConfig = cmService.getCmsConfig(id);
		ModelAndView mav = new ModelAndView("fragment/config/update");
		mav.addObject("cmsConfig", cmsConfig);
		return mav;
	}


	@RequestMapping(value = "/updateconfig/{id}", method = { RequestMethod.POST })
	public ModelAndView tempUpdateBanner(@PathVariable("id") Long id,
			CmsConfigForm cmsConfigForm) {
		CmsConfig cmsConfig = cmService.getCmsConfig(id);
		ModelAndView mav = new ModelAndView("fragment/updateconfig");
		if (cmsConfig != null) {
			cmsConfig.setValue(cmsConfigForm.getValue());
		}
		Fragment fragment = cmsConfig.getFragment();
		fragment.addCmsConfig(cmsConfig);
		cmService.updateFragment(fragment);
		mav.addObject("cmsconfig", cmsConfig);
		mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/cmsconfig/update", method = { RequestMethod.POST })
	public ModelAndView getFragmentConfig(CmsConfigForm cmsConfigForm) {
		Fragment fragment = cmService
				.getFragment(cmsConfigForm.getFragmentId());
		CmsConfig cmsConfig = null;
		if (cmsConfigForm.getId() != null) {
			cmsConfig = cmService.getCmsConfig(cmsConfigForm.getId());
		} else {
			cmsConfig = new CmsConfig();
		}

		cmsConfig.setDescription(cmsConfigForm.getDescription());
		cmsConfig.setFormat(cmsConfigForm.getFormat());
		cmsConfig.setFragment(fragment);
		cmsConfig.setKey(cmsConfigForm.getKey());
		cmsConfig.setValue(cmsConfigForm.getValue());
		cmsConfig.setValueType(cmsConfigForm.getValueType());
		cmsConfig.setAvailable(true);
		fragment.addCmsConfig(cmsConfig);
		cmService.updateFragment(fragment);

		ModelAndView mav = new ModelAndView("fragment/config/update");
		mav.addObject("cmsConfig", cmsConfig);
		return mav;
	}

//	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
//	public ModelAndView getFragment(@PathVariable("id") Long id) {
//
//		return null;
//	}

//	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
//	public ModelAndView delFragment(@PathVariable("id") Long id) {
//
//		return null;
//	}

}
