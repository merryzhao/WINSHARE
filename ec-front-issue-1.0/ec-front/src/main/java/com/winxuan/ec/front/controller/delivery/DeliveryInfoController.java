/*
 * @(#)DeliveryController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.delivery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.delivery.DeliveryInfo;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.delivery.DeliveryService;

/**
 * 	配送方式
 * @author  HideHai
 * @version 1.0,2011-8-8
 */
@Controller
@RequestMapping(value="/deliveryinfo")
public class DeliveryInfoController {

//	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private AreaService areaService;


	/**
	 * 获取下单支持的配送方式
	 * @param areaId
	 * @param customerIp
	 * @return
	 */
	@RequestMapping(value="/area/{areaId}",method=RequestMethod.GET)
	public ModelAndView listDeliveryInfoByAreaId(@PathVariable(value="areaId") Long areaId,
			HttpServletRequest request){
		boolean canPickUpSelf = Boolean.parseBoolean(request.getParameter("canPickUpSelf"));
		ModelAndView modelAndView = new ModelAndView("/deliveryinfo/list");
		Area area = areaService.get(areaId);
		List<DeliveryInfo> deliveryInfos = deliveryService.findDeliveryInfo(area, null, canPickUpSelf);
		if(CollectionUtils.isNotEmpty(deliveryInfos)){
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, ControllerConstant.RESULT_SUCCESS);
			modelAndView.addObject("deliveryInfo", deliveryInfos);
		}else{
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return modelAndView;
	}
}

