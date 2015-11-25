package com.winxuan.ec.admin.controller.dc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.dc.DcRule;
import com.winxuan.ec.model.dc.DcRuleArea;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.dc.DcService;

/**
 * @author  周斯礼
 * @version 2013-8-20
 */

@Controller
@RequestMapping("/dc")
public class DcController {

	private static final Log LOG = LogFactory.getLog(DcController.class);
	
	private static final short RESULT_SUCCESS = 1;
	
	private static final short RESULT_FAIL = 0;
	
	private static final Long AREA_CHINA = 23L;
	
	@Autowired
	private DcService dcService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView mav = new ModelAndView("/dc/list");
		mav.addObject("dcRules", dcService.find(null, null, (short)0));
		return mav;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")Long id){
		ModelAndView mav = new ModelAndView("/dc/edit");
		mav.addObject("dcRule", dcService.getDc(id));
		return mav;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public ModelAndView doEdit(DcRuleForm dcRuleForm, BindingResult result){
		ModelAndView mav = new ModelAndView("/dc/success");
		if(!result.hasErrors()){
			DcRule dcRule = dcService.getDc(dcRuleForm.getId());
			dcRule.setAddress(dcRuleForm.getAddress());
			dcRule.setAvailable(dcRuleForm.isAvailable());
			dcRule.setCountrywide(dcRuleForm.isCountrywide());
			dcRule.setDescription(dcRuleForm.getDescription());
			dcRule.setPriority(dcRuleForm.getPriority());
			dcService.updateDcRule(dcRule);
			mav.addObject(ControllerConstant.RESULT_KEY, RESULT_SUCCESS);
		}else{
			StringBuffer sb = new StringBuffer();
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error : errors){
				sb.append(error.getDefaultMessage() + ";");
			}
			LOG.debug(sb.toString());
			mav.addObject(ControllerConstant.MESSAGE_KEY, sb.toString());
			mav.addObject(ControllerConstant.RESULT_KEY, RESULT_FAIL);
		}
		return mav;
	}
	
	@RequestMapping(value="/area/index")
	public ModelAndView area(){
		ModelAndView mav = new ModelAndView("/dc/area/index");
		mav.addObject("dcRules", dcService.findByAvailableDc());
		return mav;
	}
	
	@RequestMapping(value="/area/edit")
	public ModelAndView getAreaList(@RequestParam("dcRuleId")Long dcRuleId){
		ModelAndView mav = new ModelAndView("/dc/area/edit");
		areaEdit(dcRuleId, mav);
		return mav;
	}

	private void areaEdit(Long dcRuleId, ModelAndView mav) {
		DcRule dcRule = dcService.getDc(dcRuleId);
		Set<Area> unusedProvinces = new HashSet<Area>();//该DC未配送的省
		Area china = areaService.get(AREA_CHINA);
		List<Area> areas = china.getAvailableChildren();//得到中国下面的几大片区，从而得到所有省
		if(CollectionUtils.isNotEmpty(areas)){
			Set<DcRuleArea> dcRuleAreas = dcRule.getDcAreaList();
			Set<Area> usedProvinces = new HashSet<Area>();
			Set<Area> allProvinces = new HashSet<Area>();
			if(CollectionUtils.isNotEmpty(dcRuleAreas)){
				for(DcRuleArea dcRuleArea : dcRuleAreas){
					usedProvinces.add(dcRuleArea.getArea());
				}
			}
			for(Area area : areas){
				allProvinces.addAll(area.getAvailableChildren());
			}
			if(CollectionUtils.isNotEmpty(allProvinces)){
				for(Area area : allProvinces){
					if(!usedProvinces.contains(area)){
						unusedProvinces.add(area);
					}
				}
			}
		}
		mav.addObject("dcRule", dcRule);
		mav.addObject("unusedProvinces", unusedProvinces);
	}
	
	@RequestMapping(value="/area/delete")
	public ModelAndView areaDelete(@RequestParam(value="dcRuleId")Long dcRuleId,
			@RequestParam(value="deleteAreaId", required=false)List<Long> areaIds){
		ModelAndView mav = new ModelAndView("/dc/area/edit");
		if(CollectionUtils.isNotEmpty(areaIds)){
			if(CollectionUtils.isNotEmpty(areaIds)){
				for(Long areaId : areaIds){
					DcRuleArea dcRuleArea = dcService.getDcArea(areaId);
					dcService.removeDcRuleArea(dcRuleArea);
				}
			}
		}
		areaEdit(dcRuleId, mav);
		return mav;
	}
	
	@RequestMapping(value="/area/add")
	public ModelAndView areaAdd(@RequestParam(value="dcRuleId")Long dcRuleId,
			@RequestParam(value="addAreaId", required=false)List<Long> areaIds){
		ModelAndView mav = new ModelAndView("/dc/area/edit");
		DcRule dcRule = dcService.getDc(dcRuleId);
		if(CollectionUtils.isNotEmpty(areaIds)){
			for(Long areaId : areaIds){
				if(!isExist(dcRule, areaId)){
					DcRuleArea dcRuleArea = new DcRuleArea();
					dcRuleArea.setArea(areaService.get(areaId));
					dcRuleArea.setDc(dcRule);
					dcRule.addDcArea(dcRuleArea);
				}
			}
		}
		areaEdit(dcRuleId, mav);
		return mav;
	}
	
	private boolean isExist(DcRule dcRule, Long newAreaId){
		Set<DcRuleArea> dcRuleAreas = dcRule.getDcAreaList(); 
		if(CollectionUtils.isNotEmpty(dcRuleAreas)){
			for(DcRuleArea dcRuleArea : dcRuleAreas){
				if(dcRuleArea.getArea().getId().equals(newAreaId)){
					return true;
				}
			}
		}
		return false;
	}
}


