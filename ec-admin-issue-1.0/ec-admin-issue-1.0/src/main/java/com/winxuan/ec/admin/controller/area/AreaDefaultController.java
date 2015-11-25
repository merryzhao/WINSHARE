package com.winxuan.ec.admin.controller.area;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.service.area.AreaService;

/**
 * @author liou
 * @version 2013-8-8:下午5:58:43
 * 
 */
@Controller
@RequestMapping("/default")
public class AreaDefaultController {

	@Autowired
	private AreaService areaService;

	/**
	 * 指定默认区域跳转
	 * 
	 * @param defaultForm
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView appointDefaultArea(AreaDefaultForm defaultForm) {
		ModelAndView andView = new ModelAndView(
				"/area/default/areadefault_update");
		andView.addObject("defaultForm", defaultForm);
		return andView;
	}

	/**
	 * 统计没有默认区域的数量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/districtcount", method = RequestMethod.GET)
	public ModelAndView getNoDefTownDistrictCount() {
		ModelAndView andView = new ModelAndView(
				"/area/default/areadefault_count");
		long districtCount = areaService.getNoDefTownDistrictCount();
		andView.addObject("districtcount", districtCount);
		return andView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update() {
		return new ModelAndView("/area/default/areadefault_update");
	}

	/**
	 * 修改默认区域如果有默认区域修改如果没有添加
	 * 
	 * @param town
	 * @return
	 */
	@RequestMapping(value = "/saveupdate", method = RequestMethod.POST)
	public ModelAndView saveUpdate(Area town, AreaDefaultForm defaultForm) {
		ModelAndView modelAndView = new ModelAndView(
				"/area/default/areadefault_update");
		areaService.addDistrictDefTown(town);
		defaultForm.setTown(town.getId());
		defaultForm.setDistrictId(town.getParent().getId());
		modelAndView.addObject("success", true).addObject("defaultForm",
				defaultForm);
		return modelAndView;
	}

	/**
	 * 查询所有没有默认区域的国家省市区区域
	 * 
	 * @return
	 */
	@RequestMapping(value = "/nodeftownlist", method = RequestMethod.GET)
	public ModelAndView getNoDefTown() {
		ModelAndView modelAndView = new ModelAndView(
				"/area/default/areadefault_list");
		List<Map<String, Object>> defaultArea = areaService
				.getNoDefTownDistrictList();
		modelAndView.addObject("defaultArea", defaultArea);
		return modelAndView;
	}

	/**
	 * 根据区县查看默认乡镇
	 * 
	 * @param district
	 * @return
	 */
	@RequestMapping(value = "/town", method = RequestMethod.POST)
	public ModelAndView getTown(Area district) {
		String town = null;
		Area areaTown = areaService.getDefTownByDistrict(district);
		ModelAndView andView = new ModelAndView(
				"/area/default/areadefault_town");
		if (areaTown != null) {
			town = areaTown.getName();
		}
		andView.addObject("areaTown", town);
		return andView;
	}

	@RequestMapping(value = "/town", method = RequestMethod.GET)
	public ModelAndView getTown() {
		return new ModelAndView("/area/default/areadefault_town");
	}
}
