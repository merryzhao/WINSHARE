package com.winxuan.ec.admin.controller.config;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.config.SystemSwitchConfig;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.config.SystemConfigService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author liou
 * 2014-10-22上午11:08:46
 */
@Controller
@RequestMapping("/switchconfig")
public class SystemSwitchConfigController {

	@Autowired
	private SystemConfigService systemConfigService;
	
	@RequestMapping(value="/update/{id}",method = RequestMethod.POST)
	public ModelAndView get(@PathVariable("id") Long id,Boolean valueOpen){
		ModelAndView andView = new ModelAndView("redirect:/switchconfig/list");
		SystemSwitchConfig switchConfig = systemConfigService.getSwitchConfig(id);
		if(switchConfig!=null){
			switchConfig.setIsOpen(valueOpen);
			systemConfigService.updateSwitchConfig(switchConfig);
		}
		return andView;
	}
	
	@RequestMapping(value="/save",method = RequestMethod.GET)
	public ModelAndView save(){
		ModelAndView andView = new ModelAndView("/config/switchconfig_save");
		return andView;
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public ModelAndView save(SystemSwitchConfig switchConfig,Employee employee,Boolean valueOpen){
		ModelAndView andView = new ModelAndView("/config/switchconfig_save");
		if(systemConfigService.isSystemSwitchConfig(switchConfig.getType())){
			andView.addObject("isVerification", false);
		}else{
			switchConfig.setCreateTime(new Date());
			switchConfig.setUpdateTime(new Date());
			switchConfig.setUpdateOperator(employee);
			switchConfig.setIsOpen(valueOpen);
			systemConfigService.saveSwitchConfig(switchConfig);
			andView.addObject("isVerification", true);
		}
		return andView;
	}
	
	@RequestMapping(value="/list")
	public ModelAndView find(@MyInject Pagination pagination){
		ModelAndView andView = new ModelAndView("/config/switchconfig_list");
		List<SystemSwitchConfig> switchConfigs = systemConfigService.findSwitchConfig(pagination);
		andView.addObject("switchConfigs", switchConfigs);
		andView.addObject("pagination", pagination);
		return andView;
	}
}
