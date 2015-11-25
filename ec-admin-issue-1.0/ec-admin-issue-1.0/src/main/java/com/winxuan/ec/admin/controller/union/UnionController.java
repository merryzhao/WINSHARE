package com.winxuan.ec.admin.controller.union;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.model.union.Union;
import com.winxuan.ec.model.union.UnionCommission;
import com.winxuan.ec.model.union.UnionCommissionLog;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.order.UnionOrderService;
import com.winxuan.ec.service.union.UnionCommissionLogService;
import com.winxuan.ec.service.union.UnionCommissionService;
import com.winxuan.ec.service.union.UnionService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
@Controller
@RequestMapping("/union")
public class UnionController {

	private static final Log LOG = LogFactory.getLog(UnionController.class);
	
	private static final int MAX_NUMBER = 10000;
	
	@Autowired
	UnionService unionService;
	
	@Autowired
	UnionOrderService unionOrderService;
	
	@Autowired
	UnionCommissionService unionCommissionService;
	
	@Autowired
	UnionCommissionLogService unionCommissionLogService;
	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public  ModelAndView list(){
		Map<String, Object> parameters = new HashMap<String, Object>(); 
		ModelAndView modelAndView  = new ModelAndView("/union/list");
		List<Union> unionList = unionService.find(parameters);
		modelAndView.addObject("unionList", unionList);
		return modelAndView;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(Union union){
		ModelAndView modelAndView  = new ModelAndView("redirect:/union");
		unionService.save(union);
        return modelAndView;
	}
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.POST)
	public ModelAndView edit(Union union,@PathVariable("id")Long id){
		ModelAndView modelAndView  = new ModelAndView("redirect:/union");
		unionService.update(union);
        return modelAndView;
	}
	
	@RequestMapping(value="{id}/delete", method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable("id")Long id){
		ModelAndView modelAndView  = new ModelAndView("/union/success_result");
		Union union = unionService.get(id);
		if(union == null)
			{return modelAndView;}
		union.setShow(false);
		unionService.update(union);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
		modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "修改成功！");
        return modelAndView;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@MyInject Pagination pagination){
		final String zero = "0";
		ModelAndView modelAndView  = new ModelAndView("/union/unionorderList");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("available", true);
		List<UnionOrder> unionOrderList = unionOrderService.find(parameters ,Short.valueOf(zero), pagination);
		modelAndView.addObject("unionOrderList", unionOrderList);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	@RequestMapping(value = "/unionOrderList", method = RequestMethod.POST)
	public ModelAndView unionOrderList(UnionOrderForm unionOrderForm,@MyInject Pagination pagination,
			@RequestParam(required = false, value = "format")String format,
			@RequestParam(value="isProduct",required = false) Boolean isProduct){
		final String zero = "0";
		ModelAndView modelAndView  = new ModelAndView("/union/unionorderList");
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters = unionOrderForm.generateQueryMap();
		} catch (ParseException e) {
			LOG.error(e.getMessage(), e);
		}
		Map<String,Object> selectedParameters = new HashMap<String,Object>();
		selectedParameters.putAll(parameters);
		if("xls".equals(format)){
			pagination.setPageSize(MAX_NUMBER);
		}
		List<UnionOrder> unionOrderList = unionOrderService.find(parameters,Short.valueOf(zero),pagination);
		modelAndView.addObject("unionOrderList", unionOrderList);
		modelAndView.addObject("selectedParameters", selectedParameters);
		modelAndView.addObject("pagination", pagination);
		if(isProduct!=null && isProduct){
			modelAndView.setViewName("/union/productList");
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value="/unionCommissionList",method = RequestMethod.POST)
	public ModelAndView unionCommissionList(UnionCommissionForm unionCommissionForm,
			@RequestParam(required = false, value = "format")String format,
			@MyInject Pagination pagination){
		final Short sort = 0;
		ModelAndView modelAndView = new ModelAndView("/union/commission");
	    Map<String,Object> parameters = unionCommissionForm.generateQueryMap();
	    Map<String,Object> parameters1 = new HashMap<String,Object>();
	    Map<String,Object> selectedParameters = new HashMap<String,Object>();
	    selectedParameters.putAll(parameters);
	    selectedParameters.putAll(unionCommissionForm.generateTimeMap());
		if (parameters.containsKey("unionId")) {
			List<Long> unionIds = new ArrayList<Long>();
			unionIds.add((Long) parameters.get("unionId"));
			parameters1.put("unionIds", unionIds);
		}      
        List<String> list = unionCommissionForm.generateDate();
		if(list != null && list.size()>0){
			parameters.put("time", list);
		}
		if("xls".equals(format)){
			pagination.setPageSize(MAX_NUMBER);
		}
	    List<UnionCommission> unionCommissionList = unionCommissionService.find(parameters, sort, pagination);
	    modelAndView.addObject("unionCommissionList",unionCommissionList);
	    modelAndView.addObject("selectedParameters",selectedParameters);
	    modelAndView.addObject("pagination",pagination);
		return modelAndView;
	}

	
	@RequestMapping(value="/{id}/editCommission",method = RequestMethod.POST)
	public ModelAndView  editCommission(@PathVariable Long id, @RequestParam("newCommission") BigDecimal newCommission,@MyInject Employee employee){
		UnionCommissionLog unionCommissionLog = new UnionCommissionLog();
		unionCommissionLog.setCreateDate(new Date());
		employee = employeeService.get(Employee.SYSTEM);
		unionCommissionLog.setOperator(employee);
		unionCommissionLog.setType(UnionCommissionLog.LOG_COMMISSION_TYPE);
		unionCommissionLog.setNewCommission(newCommission);
		UnionCommission unionCommission = unionCommissionService.get(id);
		unionCommissionLog.setOldCommission(unionCommission.getCommission());
		unionCommission.setCommission(newCommission);
		unionCommission.addUnionCommissionLog(unionCommissionLog);
		unionCommissionService.update(unionCommission);		
		ModelAndView modelAndView = new ModelAndView("/union/success_result");
		modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
		modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "更新成功！");
		return modelAndView;
	}
	
	@RequestMapping(value="/{id}/editPay",method = RequestMethod.POST)
	public ModelAndView  editPay(@PathVariable Long id, @RequestParam("isPay") boolean isPay,@MyInject Employee employee){
		UnionCommissionLog unionCommissionLog = new UnionCommissionLog();
		unionCommissionLog.setCreateDate(new Date());
		employee = employeeService.get(Employee.SYSTEM);
		unionCommissionLog.setOperator(employee);
		unionCommissionLog.setType(UnionCommissionLog.LOG_PAY_TYPE);
		unionCommissionLog.setNewPay(isPay);
		UnionCommission unionCommission = unionCommissionService.get(id);
		unionCommissionLog.setOldPay(unionCommission.isPay());
		unionCommission.setPay(isPay);
		unionCommission.addUnionCommissionLog(unionCommissionLog);
		unionCommissionService.update(unionCommission);		
		ModelAndView modelAndView = new ModelAndView("/union/success_result");
		modelAndView.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
		modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "更新成功！");
		return modelAndView;
	}
	@RequestMapping(value="/{id}/log",method = RequestMethod.GET)
	public ModelAndView log(@PathVariable Long id){
		List<UnionCommissionLog> unionCommissionLogs = unionCommissionLogService.findByUnionCommission(id);
		ModelAndView modelAndView = new ModelAndView("/union/union_commission_log");
		modelAndView.addObject("logs", unionCommissionLogs);
		return modelAndView;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
}
