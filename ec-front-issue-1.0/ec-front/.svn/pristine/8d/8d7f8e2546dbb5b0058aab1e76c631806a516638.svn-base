package com.winxuan.ec.front.controller.area;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.service.area.AreaService;

/**
 * @author  周斯礼
 * @version 2013-7-29
 */

@Controller
@RequestMapping("/area")
public class AreaController {
	
	private static final String LEVEL_COUNTRY = "3";

	@Autowired
	AreaService areaService;
	
	
	/**
	 * 获取乡镇一级的数据
	 * @return
	 */
	@RequestMapping
	public ModelAndView area(@RequestParam("districtId")Long districtId){
		ModelAndView mav = new ModelAndView("/area/town");
		Area area = areaService.get(districtId);
		if(area != null){
			List<Area> towns = area.getAvailableChildren();
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			mav.addObject("towns", towns);
		}else{
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return mav;
	}
	
	/**
	 * 通过国家获取所有省份
	 * @return
	 */
	@RequestMapping(value="/province",method=RequestMethod.GET)
	public ModelAndView countryFindProvince(String countryId){
		ModelAndView mav = new ModelAndView("/area/town");
		List<Area> provinces = new ArrayList<Area>();
		List<Map<String, Object>> areas = areaService.find(LEVEL_COUNTRY,"%."+countryId+".%");
		for (Map<String, Object> map : areas) {
			Long id = Long.valueOf(map.get("id").toString());
			String name = map.get("name").toString();
			Long parent = Long.valueOf(map.get("parent").toString());
			Area area = new Area();
			area.setId(id);
			area.setName(name);
			area.setParent(new Area(parent));
			provinces.add(area);
		}
		if(CollectionUtils.isNotEmpty(provinces)){
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			mav.addObject("towns", provinces);
		}else{
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return mav;
	}
}


