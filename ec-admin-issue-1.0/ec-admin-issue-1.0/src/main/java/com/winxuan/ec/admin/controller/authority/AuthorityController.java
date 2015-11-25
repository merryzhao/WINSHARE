package com.winxuan.ec.admin.controller.authority;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.authority.ResourceGroup;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.authority.resource.ResourceService;
import com.winxuan.ec.service.authority.user.UserService;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 权限设置
 * @author sunflower
 *
 */
@Controller
@RequestMapping("/authority")
public class AuthorityController {
	
	private static final int MAX_PAGE_SIZE = 20;
	private static final String INDEX = ",";
	
	@Autowired
	ResourceService resourceService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmployeeService employeeService;

	/**
	 * 权限设置页面
	 * @param pagination
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value="authority", method = RequestMethod.GET)
	public ModelAndView authority(@MyInject Pagination pagination,
			@RequestParam(value = "groupId", required = false) Long groupId) {
		
		ModelAndView modelAndView = new ModelAndView("/authority/setting/view");
		
		List<ResourceGroup> resourceGroups = resourceService.queryResourceGroup();
		
		pagination.setPageSize(MAX_PAGE_SIZE);
		List<Employee> employees = userService.queryUsers(pagination, groupId);
		
		modelAndView.addObject("employees", employees);
		modelAndView.addObject("resourceGroups", resourceGroups);
		modelAndView.addObject("pagination", pagination);
		
		return modelAndView;
	}
	
	/**
	 * 根据用户名进行查询
	 * @param pagination
	 * @param userName
	 * @return
	 */
	@RequestMapping(value="queryByCondition", method = RequestMethod.POST)
	public ModelAndView queryByCondition(@MyInject Pagination pagination,
			@RequestParam(value = "userName", required = false) String userName) {
		
		ModelAndView modelAndView = new ModelAndView("/authority/setting/view");
		
		if("".equals(userName)) {
			
			userName=null;
		}
		
		pagination.setPageSize(MAX_PAGE_SIZE);
		List<Employee> employees = employeeService.queryEmployeesByquery(pagination, userName);
		List<ResourceGroup> resourceGroups = resourceService.queryResourceGroup();
		
		modelAndView.addObject("employees", employees);
		modelAndView.addObject("pagination", pagination);
		modelAndView.addObject("resourceGroups", resourceGroups);
		
		return modelAndView;
	}
	
	/**
	 * 模糊查询
	 * @param name
	 */
	@RequestMapping(value="queryByName", method = RequestMethod.GET)
	public ModelAndView queryByName(HttpServletResponse response,@RequestParam(value = "name", required = false) String name) {
		
		ModelAndView modelAndView = new ModelAndView("/authority/setting/queryByName");
		
		List<String> employees = employeeService.queryByName(name);
		

		modelAndView.addObject("employeesList", employees);
		modelAndView.addObject("aabb", "abcd");
		
		return modelAndView;
	}
	

	/**
	 * 用户权限设置
	 * @param groupIds
	 * @param employeeId
	 * @return
	 */
	@RequestMapping(value="/relation", method = RequestMethod.POST)
	public ModelAndView relation(
			@RequestParam(value = "groupIds", required = true) String groupIds,
			@RequestParam(value = "employeeId", required = true) Long employeeId
			) {
		ModelAndView modelAndView = new ModelAndView("/authority/setting/relation");
		String[] groupIdArray = groupIds.trim().split(INDEX);
		List<ResourceGroup> resourceGroups = new ArrayList<ResourceGroup>();
		for(String groupId : groupIdArray){
			ResourceGroup resourceGroup = resourceService.queryResourceGroupById(Long.parseLong(groupId));
			resourceGroups.add(resourceGroup);
		}
		Employee employee = userService.queryUserById(employeeId);
		userService.addGroupsToEmployee(resourceGroups, employee);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
	
	/**
	 * 用户启用禁用
	 * @param employeeId
	 * @return
	 */
	@RequestMapping(value="/available", method = RequestMethod.POST)
	public ModelAndView available(
			@RequestParam(value = "employeeId", required = true) Long employeeId
			) {
		ModelAndView modelAndView = new ModelAndView("/authority/setting/available");

		Employee employee = userService.queryUserById(employeeId);
		
		employee = userService.enableOrDisable(employee);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		modelAndView.addObject("employeeId", employeeId);
		modelAndView.addObject("available", employee.isAvailable());
		return modelAndView;
	}
	
}
