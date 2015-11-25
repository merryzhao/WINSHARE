package com.winxuan.ec.admin.controller.version;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.version.VersionInfo;
import com.winxuan.ec.service.version.VersionService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author zhousl
 *
 * 2013-4-3
 */
@Controller
@RequestMapping(value="/version")
public class VersionController {

	Logger log = LoggerFactory.getLogger(VersionController.class);
	
	@Autowired
	private VersionService versionService;
	
	@RequestMapping(value="/goList", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView goList(@MyInject Pagination pagination){
		ModelAndView mav = new ModelAndView("/version/list");
		List<VersionInfo> versionList = versionService.find(null, pagination,(short)0);
		mav.addObject("versionList", versionList);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value="/goUpdate", method=RequestMethod.GET)
	public ModelAndView goUpdate(@RequestParam(value="id")Long id){
		ModelAndView mav = new ModelAndView("/version/update");
		VersionInfo versionInfo = versionService.get(id);
		mav.addObject("versionInfo", versionInfo);
		return mav;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ModelAndView update(@MyInject Employee employee,
			@RequestParam Long id,
			@RequestParam String version,
			@RequestParam int system,
			@RequestParam String updateAddress,
			@RequestParam String updateInfo,
			@RequestParam boolean latest){
		ModelAndView mav = new ModelAndView("/version/update");
		VersionInfo versionInfo = versionService.get(id);
		if(!checkRequestParams(version, updateAddress, updateInfo, mav) || !checkVersionRepeat(id, version, system, mav)){
			mav.addObject("version", versionInfo);
			return mav;
		}
		if(!checkLatestRepeat(id, system, latest, mav)){
			mav.addObject("version", versionInfo);
			return mav;
		}
		versionInfo.setVersion(version);
		versionInfo.setSystem(system);
		versionInfo.setUpdateAddress(updateAddress);
		versionInfo.setUpdateInfo(updateInfo);
		versionInfo.setLatest(latest);
		versionInfo.setUpdateTime(new Date());
		versionInfo.setUpdator(employee);
		versionService.update(versionInfo);
		log.info(String.format("update VersionInfo,id:%s,version:%s,system:%s,updateAddress:%s,updateInfo:%s,updator:%s,updateTime:%s,latest:%s", 
				versionInfo.getId(), versionInfo.getVersion(), versionInfo.getSystem(),
				versionInfo.getUpdateAddress(), versionInfo.getUpdateInfo(),
				versionInfo.getUpdator().getId(), versionInfo.getUpdateTime(), versionInfo.isLatest()));
		mav.addObject("version", versionService.get(id));
		mav.addObject(ControllerConstant.MESSAGE_KEY, "修改成功");
		return mav;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView add(){
		ModelAndView mav = new ModelAndView("/version/_add");
		return mav;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView add(@MyInject Employee employee,
			@RequestParam String version,
			@RequestParam int system,
			@RequestParam String updateAddress,
			@RequestParam String updateInfo,
			@MyInject Pagination pagination){
		ModelAndView mav = new ModelAndView("/version/list");
		if(!checkRequestParams(version, updateAddress, updateInfo, mav) || !checkVersionRepeat(null, version, system, mav)){
			mav.addObject("system", system);
			mav.addObject("updateAddress", updateAddress);
			mav.addObject("updateInfo", updateInfo);
			mav.addObject("version", version);
			mav.setViewName("/version/_add");
			return mav;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("system", system);
		List<VersionInfo> versionList = versionService.find(params, null,(short)0);
		if(CollectionUtils.isNotEmpty(versionList)){
			for(VersionInfo v : versionList){
				if(v.isLatest() && v.getSystem() == system){
					v.setLatest(false);
					versionService.update(v);
				}
			}
		}
		VersionInfo versionInfo = new VersionInfo();
		versionInfo.setVersion(version);
		versionInfo.setSystem(system);
		versionInfo.setUpdateAddress(updateAddress);
		versionInfo.setUpdateInfo(updateInfo);
		versionInfo.setCreateTime(new Date());
		versionInfo.setCreator(employee);
		versionInfo.setLatest(true);
		versionService.save(versionInfo);
		log.info(String.format("add VersionInfo,version:%s,system:%s,updateAddress:%s,updateInfo:%s,creator:%s,creatorTime:%s,latest:%s", 
				versionInfo.getVersion(), versionInfo.getSystem(),
				versionInfo.getUpdateAddress(), versionInfo.getUpdateInfo(),
				versionInfo.getCreator().getId(), versionInfo.getCreateTime(), versionInfo.isLatest()));
		mav.addObject("versionList", versionService.find(null, pagination,(short)0));
		mav.addObject("pagination", pagination);
		mav.addObject(ControllerConstant.MESSAGE_KEY, "发布成功");
		return mav;
	}
	
	private boolean checkVersionRepeat(Long id, String version, int system, ModelAndView mav){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("version", version);
		params.put("system", system);
		List<VersionInfo> list = versionService.find(params, null,(short)0);
		if(CollectionUtils.isNotEmpty(list)){
			if(id == null){
				mav.addObject(ControllerConstant.MESSAGE_KEY, "当前系统下已经存在此版本号");
				return false;
			}else{
				for(VersionInfo vi : list){
					if(!id.equals(vi.getId())){
						mav.addObject(ControllerConstant.MESSAGE_KEY, "当前系统下已经存在此版本号");
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean checkLatestRepeat(Long id, int system, boolean latest, ModelAndView mav){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("latest", latest);
		params.put("system", system);
		List<VersionInfo> list = versionService.find(params, null,(short)0);
		if(CollectionUtils.isNotEmpty(list)){
			if(latest){
				for(VersionInfo vi : list){
					if(!id.equals(vi.getId())){
						mav.addObject(ControllerConstant.MESSAGE_KEY, "最新版本只能有一条");
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean checkRequestParams(String version, String updateAddress, String updateInfo, ModelAndView mav){
		if(StringUtils.isBlank(version)){
			mav.addObject(ControllerConstant.MESSAGE_KEY, "版本号不能为空");
			return false;
		}
		if(StringUtils.isBlank(updateAddress)){
			mav.addObject(ControllerConstant.MESSAGE_KEY, "更新地址不能为空");
			return false;
		}
		if(StringUtils.isBlank(updateInfo)){
			mav.addObject(ControllerConstant.MESSAGE_KEY, "更新内容不能为空");
			return false;
		}
		return true;
	}
}
