/*
 * @(#)AreaController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.area;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.delivery.DeliveryInfo;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.support.interceptor.MyInject;

/**
 * 区域管理后台控制类
 *
 * @author wumaojie
 * @version 1.0,2011-8-12
 */
@Controller
@RequestMapping("/area")
public class AreaController{
	
	private static final Long ALL_ID = 22L;
	
	@Autowired
	private AreaService areaService;  
	
	@Autowired
	private DeliveryService deliveryService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index() {
		//返回
		ModelAndView mav = new ModelAndView("/area/area_index");
		return mav;
	}
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView categorylist(@RequestParam(required=false,value="id")Long id) {
		if(id==null){
			id=ALL_ID;
		}
		Area area = areaService.get(id);
		ModelAndView mav = new ModelAndView("/area/tree");
		mav.addObject("area",area);
		return mav;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView view(@PathVariable("id")Long id) {
		//获取根节点
		Area areaCountry = areaService.get(Area.CHINA);
		//返回
		ModelAndView mav = new ModelAndView("/area/area_list");
		mav.addObject("areaCountry",areaCountry);
		if(id!=null&&id.intValue()!=0){
			Area areaProvince = areaService.get(id);	
			mav.addObject("areaProvince",areaProvince);
		}else{
			mav.addObject("areaProvince",null);
		}
		return mav;
	}
	
	@RequestMapping(value="/{id}/deliveryinfo", method=RequestMethod.GET)
	public ModelAndView viewDeliveryinfo(@PathVariable("id")Long id, @MyInject Employee employee) {
		//获取地区
		Area area = areaService.get(id);
        //获取配送方式列表
		List<DeliveryInfo> deliveryInfos =  deliveryService.findDeliveryInfoForEmployee(area, null, null, employee);
		//返回
		ModelAndView mav = new ModelAndView("/area/area_deliveryinfo");
		mav.addObject("deliveryInfos",deliveryInfos);
		return mav;
	}
}
