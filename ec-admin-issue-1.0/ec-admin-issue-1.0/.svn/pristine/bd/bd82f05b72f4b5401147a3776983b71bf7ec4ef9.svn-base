package com.winxuan.ec.admin.controller.groupshop;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.groupinfo.GroupShoppingInfo;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.groupinfo.GroupInfoService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;
/**
 * 
 * @author fred
 * 
 */
@Controller
@RequestMapping(value = "/groupshop")
public class GroupShopController {

	private static final String GROUP_LIST = "/groupshop/list";
	
	@Autowired
	private GroupInfoService groupInfoService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat,

				true));
	}

	private Map<String, ?> assemblyDataForBase(Pagination pagination,
			Map<String, Object> parameters) {
		Map<String, Object> map = new HashMap<String, Object>();
		pagination.setPageSize(MagicNumber.TWENTY);
		List<GroupShoppingInfo> list = groupInfoService.find(parameters,
				pagination, (short) 0);
		map.put("pagination", pagination);
		map.put("list", list);
		return map;
	}
	
	
	@RequestMapping(value = "/goList", method = RequestMethod.GET)
	public ModelAndView index(@MyInject Pagination pagination) {
		ModelAndView mode = new ModelAndView(GROUP_LIST);
		mode.addAllObjects(this.assemblyDataForBase(pagination, null));
		mode.getModelMap().remove("pagination");
		return mode;
	}

	
	
	@RequestMapping(value = "/goUpdatePass", method = RequestMethod.GET)
	public ModelAndView updatePass(@RequestParam("id") Long id,
			@MyInject Employee employee) {
		ModelAndView mode = new ModelAndView(GROUP_LIST);
		GroupShoppingInfo groupShoppingInfo = groupInfoService.get(id);
		if (groupShoppingInfo != null) {
			groupShoppingInfo.setState(new Code(Code.GROUP_SHOPPING_STATUS_PASS));
			groupShoppingInfo.setUpdateTime(new Date());
			groupShoppingInfo.setUser(employee);
			groupInfoService.update(groupShoppingInfo);
		}
		return mode;
	}

	@RequestMapping(value = "/goUpdateNullify", method = RequestMethod.GET)
	public ModelAndView updateNullify(@RequestParam("id") Long id,
			@MyInject Employee employee) {
		ModelAndView mode = new ModelAndView(GROUP_LIST);
		GroupShoppingInfo groupShoppingInfo = groupInfoService.get(id);
		if (groupShoppingInfo != null) {
			groupShoppingInfo.setState(new Code(Code.GROUP_SHOPPING_STATUS_NULLIFY));
			groupShoppingInfo.setUpdateTime(new Date());
			groupShoppingInfo.setUser(employee);
			groupInfoService.update(groupShoppingInfo);
		}
		return mode;
	}

	@RequestMapping(value = "/goDelete", method = RequestMethod.GET)
	public ModelAndView deleteExamine(@RequestParam("id") Long id) {
		ModelAndView mode = new ModelAndView("/groupshop/result");
		GroupShoppingInfo groupShoppingInfo = groupInfoService.get(id);
		groupInfoService.delete(groupShoppingInfo);
		mode.addObject

		(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);

		return mode;

	}

	/**
	 * 查看一条待审核的记录
	 */
	@RequestMapping(value = "/goDetail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam("id") Long id) {
		ModelAndView mode = new ModelAndView("/groupshop/detail");
		GroupShoppingInfo groupShoppingInfo = groupInfoService.get(id);
		mode.addObject("groupShoppingInfo", groupShoppingInfo);
		return mode;
	}

	
	/**
	 * 根据公司名称或(和)咨询时间检索待审核记录
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(
			@Valid GroupShopForm groupShopForm,
			@MyInject Pagination pagination) throws ParseException {
		ModelAndView mode = new ModelAndView(GROUP_LIST);
		Map<String, Object> parameters = groupShopForm.generateQueryMap();		
		mode.addAllObjects(assemblyDataForBase(pagination, parameters));
		mode.addObject("state", groupShopForm.getState());
		return mode;
	}

	
}
