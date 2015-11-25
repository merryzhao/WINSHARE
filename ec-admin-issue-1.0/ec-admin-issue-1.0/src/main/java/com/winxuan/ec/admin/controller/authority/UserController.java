package com.winxuan.ec.admin.controller.authority;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.AuthorityException;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.authority.user.UserService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.util.security.MD5Custom;


/**
 * 权限管理-用户管理
 * @author sunflower
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	
	private static final String USER_CREATE_VIEW = "/authority/user/view";
	private static final String USER_DETAIL_VIEW = "/authority/user/detail";
	private static final String USER_CREATE = "/authority/user/create";
	private static final String USER_MODIFY_VIEW = "/authority/user/update";
	private static final String USER_RESET_VIEW = "/authority/user/reset";
	private static final String RETURN_URL = "returnUrl";
	
	private static final String VALIDATE_MESSAGE_CREATE = "create";
	private static final String VALIDATE_MESSAGE_UPDATE = "update";
	private static final String VALIDATE_MESSAGE_RESET = "reset";
	private static final String VALIDATE_MESSAGE_DETAIL = "detail";
	
	
	
	@Autowired
	UserService userService;

	/**
	 * 用户创建界面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="", method = RequestMethod.GET)
	public ModelAndView view(ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView(USER_CREATE_VIEW);
		CreateUserForm createUserForm = new CreateUserForm();
		modelMap.addAttribute("createUserForm", createUserForm);
		return modelAndView;
	}
	
	/**
	 * 用户创建
	 * @param createUserForm
	 * @param result
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView userCreate(
			@Valid CreateUserForm createUserForm, BindingResult result,
			@MyInject Employee operator) {
		
		ModelAndView modelAndView = new ModelAndView();

		if (result.hasErrors()) {
			modelAndView.setViewName(USER_CREATE_VIEW);
		} else if (createUserForm.getName().isEmpty()) {
			modelAndView.setViewName(USER_CREATE_VIEW);
			result.rejectValue("name", VALIDATE_MESSAGE_CREATE);
		} else if (createUserForm.getPassword().isEmpty()) {
			modelAndView.setViewName(USER_CREATE_VIEW);
			result.rejectValue("password", VALIDATE_MESSAGE_CREATE);
		} else if (createUserForm.getEmail().isEmpty()) {
			modelAndView.setViewName(USER_CREATE_VIEW);
			result.rejectValue("email", VALIDATE_MESSAGE_CREATE);
		} else if (createUserForm.getPassword().length() < MagicNumber.SIX) {
			modelAndView.setViewName(USER_CREATE_VIEW);
			result.rejectValue("password", null, "密码的长度必须大于等于" + MagicNumber.SIX);
		}  else {
			Employee employee = new Employee();
			employee.setName(createUserForm.getName());
			employee.setPassword(createUserForm.getPassword());
			employee.setEmail(createUserForm.getEmail());
			modelAndView.setViewName(USER_CREATE);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "用户创建成功");
			modelAndView.addObject(RETURN_URL, "/user");
			try {
				userService.addUser(employee, operator);
			} catch (AuthorityException e) {
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
			}
			
		}
		modelAndView.addObject("createUserForm", createUserForm);
		return modelAndView;
	}
	
	/**
	 * 用户密码修改页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public ModelAndView goUpdate(ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView(USER_MODIFY_VIEW);
		ModifyUserForm modifyUserForm = new ModifyUserForm();
		modelMap.addAttribute("modifyUserForm", modifyUserForm);
		return modelAndView;
	}
	
	
	/**
	 * 用户密码修改
	 * @param modifyUserForm
	 * @param result
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(
			@Valid ModifyUserForm modifyUserForm, BindingResult result,
			@MyInject Employee operator) {
		
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName(USER_MODIFY_VIEW);
		} else if (modifyUserForm.getOldPassword().isEmpty()) {
			modelAndView.setViewName(USER_MODIFY_VIEW);
			result.rejectValue("oldPassword", VALIDATE_MESSAGE_UPDATE);
		} else if (modifyUserForm.getNewPassword().isEmpty()) {
			modelAndView.setViewName(USER_MODIFY_VIEW);
			result.rejectValue("newPassword", VALIDATE_MESSAGE_UPDATE);
		} else if (modifyUserForm.getConfirmNewPassword().isEmpty()) {
			modelAndView.setViewName(USER_MODIFY_VIEW);
			result.rejectValue("confirmNewPassword", VALIDATE_MESSAGE_UPDATE);
		} else if (modifyUserForm.getNewPassword().length() < MagicNumber.SIX) {
			modelAndView.setViewName(USER_MODIFY_VIEW);
			result.rejectValue("newPassword", null, "密码的长度必须大于等于" + MagicNumber.SIX);
		} else if (!modifyUserForm.getNewPassword().equals(
				modifyUserForm.getConfirmNewPassword())) {
			// 业务错误
			modelAndView.setViewName(USER_MODIFY_VIEW);
			result.rejectValue("confirmNewPassword", null, "两次密码不相等");
		} else if (!operator.getPassword().equals(
				MD5Custom.encrypt(modifyUserForm.getOldPassword()))) {
			// 密码错误
			modelAndView.setViewName(USER_MODIFY_VIEW);
			result.rejectValue("oldPassword", null, "原始密码错误");
		} else {
			modelAndView.setViewName(USER_CREATE);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "用户密码修改成功");
			modelAndView.addObject(RETURN_URL, "/user/update");
			operator.setPassword(modifyUserForm.getNewPassword());
			userService.modifyPassword(operator);
		}
		modelAndView.addObject("modifyUserForm", modifyUserForm);
		return modelAndView;
	}
	
	/**
	 * 用户密码重置页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/reset", method = RequestMethod.GET)
	public ModelAndView goReset(ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView(USER_RESET_VIEW);
		ResetUserForm resetUserForm = new ResetUserForm();
		modelMap.addAttribute("resetUserForm", resetUserForm);
		return modelAndView;
	}
	
	
	/**
	 * 用户密码重置
	 * @param resetUserForm
	 * @param result
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/reset", method = RequestMethod.POST)
	public ModelAndView reset(
			@Valid ResetUserForm resetUserForm, BindingResult result,
			@MyInject Employee operator) {
		
		ModelAndView modelAndView = new ModelAndView();

		if (result.hasErrors()) {
			modelAndView.setViewName(USER_RESET_VIEW);
		} else if (resetUserForm.getName().isEmpty()) {
			modelAndView.setViewName(USER_RESET_VIEW);
			result.rejectValue("name", VALIDATE_MESSAGE_RESET);
		} else if (resetUserForm.getPassword().isEmpty()) {
			modelAndView.setViewName(USER_RESET_VIEW);
			result.rejectValue("password", VALIDATE_MESSAGE_RESET);
		} else {
			Employee employee = new Employee();
			employee.setName(resetUserForm.getName());
			employee.setPassword(resetUserForm.getPassword());
			modelAndView.setViewName(USER_CREATE);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "用户重置密码成功");
			modelAndView.addObject(RETURN_URL, "/user/reset");
			try {
				userService.resetPassword(employee, operator);
			} catch (AuthorityException e) {
				modelAndView.addObject("message", e.getMessage());
			}
		}
		modelAndView.addObject("resetUserForm", resetUserForm);
		return modelAndView;
	}
	
	/**
	 * 用户资料页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/detail", method = RequestMethod.GET)
	public ModelAndView detail(ModelMap modelMap,@MyInject Employee employee) {
		ModelAndView modelAndView = new ModelAndView(USER_DETAIL_VIEW);
		modelAndView.addObject("userDetailForm", employee);
		return modelAndView;
	}
	
	/**
	 * 用户资料修改
	 * @param userDetailForm
	 * @param result
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/detail", method = RequestMethod.POST)
	public ModelAndView modifyDetail(
			@Valid UserDetailForm userDetailForm, BindingResult result,
			@MyInject Employee employee) {
		
		ModelAndView modelAndView = new ModelAndView();

		if (result.hasErrors()) {
			modelAndView.setViewName(USER_DETAIL_VIEW);
		}else if (userDetailForm.getRealName().isEmpty()) {
			modelAndView.setViewName(USER_DETAIL_VIEW);
			result.rejectValue("realName", VALIDATE_MESSAGE_DETAIL);
		} else if (userDetailForm.getEmail().isEmpty()) {
			modelAndView.setViewName(USER_DETAIL_VIEW);
			result.rejectValue("email", VALIDATE_MESSAGE_DETAIL);
		}  else if (userDetailForm.getPhone().isEmpty()) {
			modelAndView.setViewName(USER_DETAIL_VIEW);
			result.rejectValue("phone", VALIDATE_MESSAGE_DETAIL);
		} else if (userDetailForm.getMobile().isEmpty()) {
			modelAndView.setViewName(USER_DETAIL_VIEW);
			result.rejectValue("mobile", VALIDATE_MESSAGE_DETAIL);
		} else {
			employee.setRealName(userDetailForm.getRealName());
			employee.setPhone(userDetailForm.getPhone());
			employee.setEmail(userDetailForm.getEmail());
			employee.setMobile(userDetailForm.getMobile());
			modelAndView.setViewName(USER_CREATE);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "用户资料修改成功");
			modelAndView.addObject(RETURN_URL, "/user/detail");
			try {
				userService.modifyUser(employee, employee);
			} catch (AuthorityException e) {
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
			}
			
		}
		modelAndView.addObject("userDetailForm", userDetailForm);
		return modelAndView;
	}
}
