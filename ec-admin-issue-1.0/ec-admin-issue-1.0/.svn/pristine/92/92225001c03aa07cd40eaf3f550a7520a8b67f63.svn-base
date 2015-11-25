package com.winxuan.ec.admin.controller.authority;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.ResourceException;
import com.winxuan.ec.exception.ResourceGroupException;
import com.winxuan.ec.model.authority.Resource;
import com.winxuan.ec.model.authority.ResourceGroup;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.authority.resource.ResourceService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;


/**
 * 权限管理-资源管理
 * @author sunflower
 *
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
	
	private static final int MAX_PAGE_SIZE = 20;
	private static final String INDEX = ",";
	private static final String RESOURCE_CREATE_VIEW = "/authority/resource/add";
	private static final String RESOURCE = "resource";
	
	@Autowired
	ResourceService resourceService;
	
	
	/**
	 * 资源查询界面
	 * @return
	 */
	@RequestMapping(value="", method = RequestMethod.GET)
	public ModelAndView view(@MyInject Pagination pagination,
			@RequestParam(value = "groupId", required = false) Long groupId) {
		
		ModelAndView modelAndView = new ModelAndView("/authority/resource/view");
		
		List<ResourceGroup> resourceGroups = resourceService.queryResourceGroup();
		
		pagination.setPageSize(MAX_PAGE_SIZE);
		ResourceGroup resourceGroup = new ResourceGroup();
		resourceGroup.setId(groupId);
		List<Resource> resources = resourceService.queryResources(groupId, pagination);
	
		modelAndView.addObject("resources", resources);
		modelAndView.addObject("resourceGroups", resourceGroups);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	

	/**
	 * 添加资源组
	 * @param groupCode
	 * @param groupValue
	 * @param groupDes
	 * @return
	 */
	@RequestMapping(value="/addGroup", method = RequestMethod.POST)
	public ModelAndView addGroup(
			@RequestParam(value = "groupCode", required = true) String groupCode,
			@RequestParam(value = "groupValue", required = false) String groupValue,
			@RequestParam(value = "groupDes", required = true) String groupDes
			) {
		
		ModelAndView modelAndView = new ModelAndView("/authority/resource/addGroup");
		ResourceGroup resourceGroup = new ResourceGroup();
		resourceGroup.setCode(groupCode);
		resourceGroup.setValue(groupValue);
		resourceGroup.setDescription(groupDes);
		try {
			resourceService.addResourceGroup(resourceGroup);
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			Long groupId = resourceService.queryResourceGroupByCode(groupCode).getId();
			modelAndView.addObject("groupId", groupId);
		} catch (ResourceGroupException e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
		}
		return modelAndView;
	}
	
	/**
	 * 删除资源组
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value="/delGroup", method = RequestMethod.POST)
	public ModelAndView delGroup(@RequestParam(value = "groupId", required = true) Long groupId
			) {
		ModelAndView modelAndView = new ModelAndView("/authority/resource/delGroup");
		ResourceGroup resourceGroup = resourceService.queryResourceGroupById(groupId);
		resourceService.deleteResourceGroup(resourceGroup);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		modelAndView.addObject("groupId", groupId);
		return modelAndView;
	}
	
	/**
	 * 资源资源组关系维护
	 * @param groupIds
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value="/relation", method = RequestMethod.POST)
	public ModelAndView relation(
			@RequestParam(value = "groupIds", required = true) String groupIds,
			@RequestParam(value = "resourceId", required = true) Long resourceId
			) {
		ModelAndView modelAndView = new ModelAndView("/authority/resource/relation");
		String[] groupIdArray = groupIds.trim().split(INDEX);
		List<ResourceGroup> resourceGroups = new ArrayList<ResourceGroup>();
		for(String groupId : groupIdArray){
			ResourceGroup resourceGroup = resourceService.queryResourceGroupById(Long.parseLong(groupId));
			resourceGroups.add(resourceGroup);
		}
		Resource resource = resourceService.queryResourceById(resourceId);
		resourceService.addGroupsToResource(resourceGroups, resource);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
	
	/**
	 * 根据资源ID删除资源
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value="/delResource", method = RequestMethod.POST)
	public ModelAndView delResource(
			@RequestParam(value = "resourceId", required = true) Long resourceId
			) {
		ModelAndView modelAndView = new ModelAndView("/authority/resource/delResource");
		Resource resource = resourceService.queryResourceById(resourceId);
		resourceService.deleteResource(resource);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		modelAndView.addObject("resourceId", resourceId);
		return modelAndView;
	}
	
	/**
	 * 资源创建界面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public ModelAndView add(ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView(RESOURCE_CREATE_VIEW);
		CreateResourceForm createResourceForm = new CreateResourceForm();
		modelMap.addAttribute("createResourceForm", createResourceForm);
		return modelAndView;
	}
	
	/**
	 * 资源创建
	 * @param createResourceForm
	 * @param result
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView resourceCreate(
			@Valid CreateResourceForm createResourceForm, BindingResult result,
			@MyInject Employee operator) {
		
		ModelAndView modelAndView = new ModelAndView();

		if (result.hasErrors()) {
			modelAndView.setViewName(RESOURCE_CREATE_VIEW);
		} else if (createResourceForm.getCode().isEmpty()) {
			modelAndView.setViewName(RESOURCE_CREATE_VIEW);
			result.rejectValue("code", RESOURCE);
		} else if (createResourceForm.getValue().isEmpty()) {
			modelAndView.setViewName(RESOURCE_CREATE_VIEW);
			result.rejectValue("value", RESOURCE);
		} else {
			Resource resource = new Resource();
			resource.setCode(createResourceForm.getCode());
			resource.setValue(createResourceForm.getValue());
			resource.setDescription(createResourceForm.getDescription());
			modelAndView.setViewName("/authority/resource/create");
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "资源创建成功");
			modelAndView.addObject("returnUrl", "/resource/add");
			try {
				resourceService.addResource(resource);
			} catch (ResourceException e) {
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
			}
		}
		modelAndView.addObject("createResourceForm", createResourceForm);
		return modelAndView;
	}
	
	/**
	 * 资源修改初始化数据
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value="/editResource", method = RequestMethod.GET)
	public ModelAndView initalEditResource(@RequestParam(value = "resourceId", required = true) Long resourceId) {
		ModelAndView modelAndView = new ModelAndView("/authority/resource/editResource");
		Resource resource = resourceService.queryResourceById(resourceId);
		modelAndView.addObject("resource", resource);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
	
	
	/**
	 * 资源修改
	 * @param resourceId
	 * @param code
	 * @param value
	 * @param description
	 * @return
	 */
	@RequestMapping(value="/editResource", method = RequestMethod.POST)
	public ModelAndView editResource(
			@RequestParam(value = "resourceId", required = true) String resourceId,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "value", required = true) String value,
			@RequestParam(value = "description", required = false) String description
			) {
		ModelAndView modelAndView = new ModelAndView("/authority/resource/editResource");
		Resource resource = resourceService.queryResourceById(Long.parseLong(resourceId));
		resource.setCode(code);
		resource.setValue(value);
		resource.setDescription(description);
		resourceService.modifyResource(resource);
		modelAndView.addObject("resource", resource);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
}
