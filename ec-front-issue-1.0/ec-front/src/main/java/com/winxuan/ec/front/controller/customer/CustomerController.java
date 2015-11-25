package com.winxuan.ec.front.controller.customer;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.impl.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.front.config.HttpUtil;
import com.winxuan.ec.front.controller.Constant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerExtend;
import com.winxuan.ec.model.customer.CustomerExtension;
import com.winxuan.ec.model.customer.CustomerFavorite;
import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.customer.CustomerThirdParty;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerLockService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.util.StringTool;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.StringUtils;
import com.winxuan.framework.util.security.MD5Custom;
import com.winxuan.framework.util.web.CookieUtils;
import com.winxuan.framework.validator.impl.CookieIdentityValidator;

/*
 * @(#)CustomerController.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

/**
 * 用户
 * 
 * @author HideHai
 * @version 1.0,2011-7-15
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

	private static final String UPDATE_PASSWORD = "/customer/password/_updatePassword";
	private static final String BINDING_ACCOUNT = "/customer/password/_bindingAccount";
	private static final int PAGE_SIZE = 3;
	private static final short STATUS_MODE_COMPLEX = 0;
	private static final short STATUS_MODE_SIMPLE = 1;
	private static final  List<Long> CUSTOMER_SOURCE = Arrays.asList(Code.USER_SOURCE_EC_FRONT,
			Code.USER_SOURCE_ANONYMITY,Code.USER_SOURCE_9YUE_FRONT);
	private static final  List<Long> OFF_PRODUCT = Arrays.asList(Code.PRODUCT_SALE_STATUS_OFFSHELF,
			Code.PRODUCT_SALE_STATUS_EC_STOP,Code.PRODUCT_SALE_STATUS_SAP_STOP,Code.PRODUCT_SALE_STATUS_DELETED);
	private static final String CUSTOMER = "customer";

	private static final String YMD = "yyyy-MM-dd";
	private static final short THREE = 3;
	private static final short SIX = 6;
	private static final String SPLIT = "-";

	private static final int FIFTY = 50;

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private CodeService codeService;

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CookieIdentityValidator cookieIdentityValidator;

	@Autowired
	private OrderService orderService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private PresentCardService presentCardService;
	
	@Autowired
	private CustomerLockService customerLockService;
	
	@Autowired
	private ShoppingcartService shoppingcartService;
	

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view(@MyInject Customer customer,
			@MyInject Pagination pagination) {
		ModelAndView modelAndView = new ModelAndView();
		pagination.setPageSize(PAGE_SIZE);

		// 订单信息
		List<Order> orderList = orderService
				.findByCustomer(customer, PAGE_SIZE);
		modelAndView.addObject("orderList", orderList);

		// 礼品卡余额
		BigDecimal total = presentCardService
				.getTotalBalanceByCustomer(customer);
		modelAndView.addObject("total", total);
		// 我的问答
		List<CustomerQuestion> questions = customerService.findQuestions(
				customer, pagination, Short.valueOf("0"));
		modelAndView.addObject("questionList", questions);
		// 用户收藏
		List<CustomerFavorite> favoriteList = customerService.findFavorite(
				customer, PAGE_SIZE);
		modelAndView.addObject("favoriteList", favoriteList);

		modelAndView.addObject(CUSTOMER, customer);
		modelAndView.setViewName("customer/view");
		return modelAndView;
	}

	/**
	 * 测试获取当前用户
	 * 
	 * @param customer
	 * @param customer2
	 * @return
	 */
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public ModelAndView getCurrentCustom(@MyInject Customer customer,
			Customer customer2) {
		ModelAndView modelAndView = new ModelAndView("/customer/current");
		ModelMap modelMap = new ModelMap();
		modelMap.put("customer", customer);
		short s = Short.valueOf("2");
		modelMap.put("test", s);
		if (customer != null) {
			log.info("当前用户:" + customer.getName());
		}
		modelAndView.addAllObjects(modelMap);
		return modelAndView;
	}
	
	/**
	 * 用户降价通知信息获取
	 * 
	 * @param customer
	 * @return,获取最后一个订单的数据
	 */
	@RequestMapping(value = "/customerinfostatus", method = RequestMethod.GET)
	public ModelAndView customerStatus(
			@MyInject Customer customer) {
		ModelAndView modelAndView = new ModelAndView("/customer/customerinfostatus");
		
		modelAndView.addObject("isLogin", customer != null);
		if(customer!=null){
			modelAndView.addObject("isLogin", true);
			modelAndView.addObject("email", customer.getEmail()==null?"":customer.getEmail());
			List<Order> orderlist = orderService.findByCustomer(customer, 1);
			if(orderlist!=null)
			{
				try
				{
				Order order = orderlist.get(0);
				OrderConsignee consignee = order.getConsignee();
				if(consignee!=null)
				{
				  modelAndView.addObject("phone", consignee.getMobile()==null?"":consignee.getMobile());
				}
				}
				catch (Exception e) {
					modelAndView.addObject("phone", "");
				}
				
			}
			else
			{
				modelAndView.addObject("phone", "");
			}
			
		}

		return modelAndView;
	}
	

	/**
	 * 获取用户帐户信息
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ModelAndView showStatus(
			@MyInject Customer customer,
			@CookieValue(value = "v", required = false) String visitor,
			@RequestParam(value = "mode", required = false, defaultValue = "0") short mode,
			@MyInject Shoppingcart shoppingcart) {
		ModelAndView modelAndView = new ModelAndView("/customer/status");
		modelAndView.addObject("isLogin", customer != null);
		modelAndView.addObject("visitor", visitor);
		/*是否该修改密码*/
		if(customer!=null){
			modelAndView.addObject("loginTime", customer.getLastLoginTime().getTime());
			if(customer.getUserLockState() != null){
				//用户锁定后user_lock_state 获取lastUpdateTime时间为空
				long lastUpdateTime = new Date().getTime();
				if(customer.getUserLockState().getLastUpdateTime()!=null){
					lastUpdateTime = customer.getUserLockState().getLastUpdateTime().getTime();
				}
				modelAndView.addObject("lastUpdateTime", lastUpdateTime);
			}
			modelAndView.addObject("isUpdate", customer.getLongTimeNotLogin());
		}
		if(shoppingcart != null && CollectionUtils.isNotEmpty(shoppingcart.getItemList())){
			for(ShoppingcartItem item : shoppingcart.getItemList()){
				if(item.getProductSale() != null){
					item.getProductSale().setPurchasedQuantityAll(0);
					if(customer != null){
						item.getProductSale().setPurchasedQuantity(2);
					}
				}
			}
		}
		
		if (mode == STATUS_MODE_SIMPLE) {
			modelAndView.addObject("shoppingCount", shoppingcart == null ? 0
					: shoppingcart.getCount());
		}
		if (mode == STATUS_MODE_COMPLEX) {
			modelAndView.addObject("shoppingcart", shoppingcart);
		}
		updateClientCookie(shoppingcart);
		return modelAndView;
	}
	
	
	/**
	 * 获取用户帐户信息
	 * 
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "/shoppingcart", method = RequestMethod.GET)
	public ModelAndView shoppingcart(
			@MyInject Customer customer,
			@CookieValue(value = "v", required = false) String visitor,
			@RequestParam(value = "mode", required = false, defaultValue = "0") short mode,
			@MyInject Shoppingcart shoppingcart) {
		ModelAndView modelAndView = new ModelAndView("/customer/status");
		modelAndView.addObject("isLogin", customer != null);
		modelAndView.addObject("visitor", visitor);
		/*是否该修改密码*/
		if(customer!=null){
			modelAndView.addObject("loginTime", customer.getLastLoginTime().getTime());
			if(customer.getUserLockState() != null){
				//用户锁定后user_lock_state 获取lastUpdateTime时间为空
				long lastUpdateTime = new Date().getTime();
				if(customer.getUserLockState().getLastUpdateTime()!=null){
					lastUpdateTime = customer.getUserLockState().getLastUpdateTime().getTime();
				}
				modelAndView.addObject("lastUpdateTime", lastUpdateTime);
			}
			modelAndView.addObject("isUpdate", customer.getLongTimeNotLogin());
		}
		if(shoppingcart != null && CollectionUtils.isNotEmpty(shoppingcart.getItemList())){
			//去掉下架和停用商品
			removeOffProduct(shoppingcart);
			for(ShoppingcartItem item : shoppingcart.getItemList()){
				if(item.getProductSale() != null){
					item.getProductSale().setPurchasedQuantityAll(orderService.getPurchaseQuantityToday(item.getProductSale()));
					
					if(customer != null){
						item.getProductSale().setPurchasedQuantity(orderService.getPurchaseQuantityToday(customer,item.getProductSale()));
					}
				}
			}
		}
		
		if (mode == STATUS_MODE_SIMPLE) {
			modelAndView.addObject("shoppingCount", shoppingcart == null ? 0
					: shoppingcart.getCount());
		}
		if (mode == STATUS_MODE_COMPLEX) {
			modelAndView.addObject("shoppingcart", shoppingcart);
		}
		updateClientCookie(shoppingcart);
		return modelAndView;
	}

	private void removeOffProduct(Shoppingcart shoppingcart){
		Set<ShoppingcartItem> cartItem = new HashSet<ShoppingcartItem>();
		for(ShoppingcartItem item : shoppingcart.getItemList()){
			if(!OFF_PRODUCT.contains(item.getProductSale().getSaleStatus().getId())){
				cartItem.add(item);
			}
		}
		shoppingcart.setItemList(cartItem);
	}
	
	private void updateClientCookie(Shoppingcart shoppingcart) {
		Cookie cookie = new Cookie(Constant.COOKIE_SHOPPINGCART_COUNT_NAME,
				String.valueOf(shoppingcart.getCount()));
		cookie.setMaxAge(Constant.COOKIE_CLIENT_AGE);
		cookie.setPath(Constant.COOKIE_PATH);
		cookie.setDomain(Constant.COOKIE_DOMAIN);
		CookieUtils.writeCookie(cookie);
	}


	@RequestMapping(value = "/accountDetail", method = RequestMethod.GET)
	public ModelAndView advanceAccountDetail(
			@RequestParam(required = false) String orderId,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@MyInject Customer customer, @MyInject Pagination pagination) {
		ModelAndView modelAndView = new ModelAndView("");
		if (StringUtils.isNullOrEmpty(orderId)
				&& StringUtils.isNullOrEmpty(startDate)
				&& StringUtils.isNullOrEmpty(endDate)) {
			modelAndView.setView(new RedirectView("forward:/account"));
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (!StringUtils.isNullOrEmpty(orderId)) {
			parameters.put("orderId", orderId);
		}
		if (!StringUtils.isNullOrEmpty(orderId)) {
			parameters.put("startDate", startDate);
		}
		if (!StringUtils.isNullOrEmpty(orderId)) {
			parameters.put("endDate", endDate);
		}
		customerService.findAccountDetail(parameters, pagination);
		return modelAndView;
	}

	/**
	 * 修改密码页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
	public ModelAndView updatePassword(ModelMap modelMap) {
		UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();
		modelMap.addAttribute("updatePasswordForm", updatePasswordForm);
		return new ModelAndView(UPDATE_PASSWORD);
	}

	/**
	 * 修改密码
	 * 
	 * @param updatePasswordForm
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
	public ModelAndView updatePassword(
			@Valid UpdatePasswordForm updatePasswordForm, BindingResult result,
			@MyInject Customer customer) {
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName(UPDATE_PASSWORD);
		} else if (updatePasswordForm.getOldPassword().isEmpty()) {
			modelAndView.setViewName(UPDATE_PASSWORD);
			result.rejectValue("oldPassword", null, "请输入旧密码");
		} else if (updatePasswordForm.getNewPassword().isEmpty()) {
			modelAndView.setViewName(UPDATE_PASSWORD);
			result.rejectValue("newPassword", null, "请输入新密码");
		} else if (updatePasswordForm.getNewPasswordConfirm().isEmpty()) {
			modelAndView.setViewName(UPDATE_PASSWORD);
			result.rejectValue("newPasswordConfirm", null, "请输入重复新密码");
		} else if (updatePasswordForm.getNewPassword().length() < SIX) {
			modelAndView.setViewName(UPDATE_PASSWORD);
			result.rejectValue("newPassword", null, "密码的长度必须大于等于" + SIX);
		} else if (!updatePasswordForm.getNewPassword().equals(
				updatePasswordForm.getNewPasswordConfirm())) {
			// 业务错误
			modelAndView.setViewName(UPDATE_PASSWORD);
			result.rejectValue("newPasswordConfirm", null, "两次密码不相等");
		} else if(!customer.getPassword().equals(
				MD5Custom.encrypt(updatePasswordForm.getOldPassword()))
				&& !customer.getPassword().equals(
						StringTool.md5(updatePasswordForm.getOldPassword()))) {
			//增加9月网原密码验证
			// 密码错误
			modelAndView.setViewName(UPDATE_PASSWORD);
			result.rejectValue("oldPassword", null, "原始密码错误");
		} else {
			if(Code.USER_SOURCE_9YUE_FRONT.equals(customer.getSource().getId()))
			{
				customer.setPassword(StringTool.md5(updatePasswordForm
						.getNewPassword()));
				//修改9月网密码
				HttpUtil.updatePwdYue(customer.getId(),updatePasswordForm.getNewPassword(),updatePasswordForm.getNewPasswordConfirm());
			}
			else
			{
				customer.setPassword(MD5Custom.encrypt(updatePasswordForm
						.getNewPassword()));
			}
			customerService.update(customer);
			//解锁,更新最后操作时间
			customerLockService.accountUnLock(customer, customer);
			modelAndView.setViewName("/customer/password/updatePassword");
		}
		if (updatePasswordForm != null) {
			modelAndView.addObject("updatePassword", updatePasswordForm);
		}
		return modelAndView;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat(YMD);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * 用户修改信息后,重写cookie
	 */
	private void writeNickName(String nickName) {
		Cookie cookie = cookieIdentityValidator.createVisitorCookie(nickName);
		CookieUtils.writeCookie(cookie);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView basic(@MyInject Customer customer,
			@Valid DetailForm basicForm, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();
		if (basicForm.getFlag() != 1) {
			if (basicForm.getRealName() != null) {
				customer.setRealName(basicForm.getRealName().trim());
			}
			customer.setGender(basicForm.getGender());
			customer.setBirthday(basicForm.getBirthday());
			if (basicForm.getCountry() != null) {
				customer.setCountry(areaService.get(basicForm.getCountry()));
			}
			if (basicForm.getProvince() != null) {
				customer.setProvince(areaService.get(basicForm.getProvince()));
			}
			if (basicForm.getCity() != null) {
				customer.setCity(areaService.get(basicForm.getCity()));
			}
			if (basicForm.getEmail() != null
					&& !StringUtils.isNullOrEmpty(basicForm.getEmail())
					&& !basicForm.getEmail().trim()
							.equalsIgnoreCase(customer.getEmail().trim())) {
				customer.setEmailActive((short) 0);
				customer.setEmail(basicForm.getEmail().trim());
			}
			if (basicForm.getMobile() != null) {
				customer.setMobile(basicForm.getMobile().trim());
			}
			if (basicForm.getNickName() != null) {
				String nickName = StringUtils.escapeSpecialLabel(basicForm
						.getNickName().trim());
				if (nickName == null) {
					nickName = "";
				}else if(nickName.length()>=FIFTY){
					result.rejectValue("nickName", null, "长度为1-50个字符");
				}
				
				customer.setNickName(nickName);

			}
			if (basicForm.getEmail() != null
					&& !StringUtils.isNullOrEmpty(basicForm.getEmail())
					&& !result.hasErrors()) {
				customerService.update(customer);
				customer = this.unEscapeName(customer);
				this.writeNickName(customer.getDisplayName());
			}

		}

		if (customer.getBirthday() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(YMD);
			String[] birthday = sdf.format(customer.getBirthday()).split(SPLIT);
			if (birthday.length == THREE) {
				modelAndView.addObject("year", Integer.parseInt(birthday[0]));
				modelAndView.addObject("month", Integer.parseInt(birthday[1]));
				modelAndView.addObject("day", Integer.parseInt(birthday[2]));
			}
		}
		customer = this.unEscapeName(customer);
		modelAndView.addObject(CUSTOMER, customer);
		modelAndView.setViewName("/customer/detail/detail");
		return modelAndView;
	}

	/**
	 * 还原被转义的名字
	 * 
	 * @param customer
	 * @return
	 */
	private Customer unEscapeName(Customer customer) {
		String nickName = StringUtils.unEscapeSpecialLabel(
				customer.getNickName(), true);
		customer.setNickName(nickName);
		return customer;
	}

	@RequestMapping(value = "/more", method = RequestMethod.GET)
	public ModelAndView more(@MyInject Customer customer,
			@Valid MoreForm moreForm) {
		ModelAndView modelAndView = new ModelAndView("/customer/detail/more");
		CustomerExtension customerExtension = customer.getExtension();
		if (customerExtension == null) {
			customerExtension = new CustomerExtension();
			customerExtension.setCustomer(customer);
		}

		if (moreForm.getFlag() != 1) {
			customerExtension.setLivingStatus(moreForm.getLivingStatus());
			if (moreForm.getCareerType() != null) {
				customerExtension.setCareerType(codeService.get(moreForm
						.getCareerType()));
			}
			if (moreForm.getCareer() != null) {
				customerExtension.setCareer(codeService.get(moreForm
						.getCareer()));
			}
			if (moreForm.getSalary() != null) {
				customerExtension.setSalary(codeService.get(moreForm
						.getSalary()));
			}
			if (moreForm.getInterest() != null) {
				customerExtension.setInterest(moreForm.getInterest().trim());
			}
			if (moreForm.getFavorite() != null) {
				customerExtension.setFavorite(moreForm.getFavorite().trim());
			}
			customerService.saveOrUpdateCustomerExtension(customerExtension);
		}

		modelAndView.addObject("livingStatusList",
				codeService.get(Code.CUSTOMER_LIVING_STATUS)
						.getAvailableChildren());
		modelAndView.addObject("salaryList",
				codeService.get(Code.CUSTOMER_SALARY).getAvailableChildren());
		if (customerExtension.getLivingStatus() != null) {
			String[] livingArray = customerExtension.getLivingStatus().split(
					"@");
			Map<String, String> selectedParameters = new HashMap<String, String>();
			for (String living : livingArray) {
				selectedParameters.put(living, living);
			}
			modelAndView.addObject("selectedParameters", selectedParameters);
		}
		modelAndView.addObject("customerExtension", customerExtension);
		return modelAndView;
	}

	@RequestMapping(value = "/saveAvatar", method = RequestMethod.GET)
	public ModelAndView saveAvatar(@MyInject Customer customer,
			@Valid DetailForm basicForm) {
		ModelAndView modelAndView = new ModelAndView("/customer/profile/result");
		if (basicForm.getAvatar() != null) {
			if (basicForm.getAvatar().indexOf("portrait") == -1
					|| basicForm.getAvatar().lastIndexOf(".gif") == -1) {
				modelAndView.addObject(ControllerConstant.RESULT_KEY,
						ControllerConstant.RESULT_WARNING);
			} else {
				customer.setAvatar(basicForm.getAvatar());
				customerService.update(customer);
				modelAndView.addObject(ControllerConstant.RESULT_KEY,
						ControllerConstant.RESULT_SUCCESS);
			}
		} else {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_WARNING);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/payEmail", method = RequestMethod.POST)
	public ModelAndView updatePayEmail(@MyInject Customer customer,
			@RequestParam(value = "payEmail", required = false) String payEmail){
		ModelAndView modelAndView = new ModelAndView("/customer/email/payEmailUpdate");
		int status = MagicNumber.ONE;
		String msg = "设置邮箱成功 ";
		EmailValidator emailValidator = new EmailValidator();
		if(org.apache.commons.lang.StringUtils.isBlank(payEmail)
				|| !emailValidator.isValid(payEmail, null)){
			status = MagicNumber.NEGATIVE_ONE;
			msg = "邮箱格式不正确 ";
		}
		CustomerExtend customerExtend = customer.getCustomerExtend();
		if(customerExtend == null){
			customerExtend = new CustomerExtend();
			customerExtend.setCustomer(customer);
			customer.setCustomerExtend(customerExtend);
		}
		customerExtend.setPayEmail(payEmail);
		customerService.saveOrUpdateCustomerExtend(customerExtend);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,status);
		modelAndView.addObject(ControllerConstant.MESSAGE_KEY,msg);
		return modelAndView;
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ModelAndView email(@MyInject Customer customer,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "date", required = false) String date,
			@RequestParam(value = "key", required = false) String key) {
		ModelAndView modelAndView = new ModelAndView("/customer/email/result");
		short r = ControllerConstant.RESULT_WARNING;
		if (customerService.sendEmailValidate(customer)) {
			r = ControllerConstant.RESULT_SUCCESS;
		}
		modelAndView.addObject(ControllerConstant.RESULT_KEY, r);
		return modelAndView;
	}

	@RequestMapping(value = "/emailIsExist", method = RequestMethod.GET)
	public ModelAndView emailIsExist(
			@RequestParam(value = "email", required = false) String email) {
		ModelAndView modelAndView = new ModelAndView("/customer/email/result");
		short r = ControllerConstant.RESULT_WARNING;
		if (customerService.emailIsExisted(email.trim())) {
			r = ControllerConstant.RESULT_SUCCESS;
		}
		modelAndView.addObject(ControllerConstant.RESULT_KEY, r);
		return modelAndView;
	}
	
	@RequestMapping(value="/bindingAccount",method = RequestMethod.GET)
	public ModelAndView bindingAccount(ModelMap modelMap, @MyInject Customer customer){
		ModelAndView modelAndView =  new ModelAndView(BINDING_ACCOUNT);
		CustomerThirdParty customerThirdParty = customerService.getByThirdPartyId(customer.getId());
		if(customerThirdParty!=null){
			if(customerThirdParty.getWinxuan().getEmail()!=null){
				modelAndView.addObject("login", customerThirdParty.getWinxuan().getEmail());
			}
		}else if(CUSTOMER_SOURCE.contains(customer.getSource().getId())){
			modelAndView.setView(new RedirectView("forward:/customer"));
		}
		UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();
		modelMap.addAttribute("updatePasswordForm", updatePasswordForm);
		return  modelAndView;
	}
	
	@RequestMapping(value="/bindingAccount",method = RequestMethod.PUT)
	public ModelAndView bindingAccount(@Valid UpdatePasswordForm updatePasswordForm, 
			BindingResult result,@MyInject Customer customer){
		ModelAndView modelAndView = new ModelAndView(BINDING_ACCOUNT);
		if (result.hasErrors()) {
			return modelAndView;
		} else if(updatePasswordForm.getUsername().isEmpty()){
			result.rejectValue("username",null, "请输入文轩网用户名");
		} else if(customerService.nameIsExisted(updatePasswordForm.getUsername())||
				customerService.emailIsExisted(updatePasswordForm.getUsername())){
			result.rejectValue("username",null, "“"+updatePasswordForm.getUsername()+ "”  用户名已存在");
		} else if (updatePasswordForm.getNewPassword().isEmpty()) {
			result.rejectValue("newPassword", null, "请输入新密码");
		} else if (updatePasswordForm.getNewPasswordConfirm().isEmpty()) {
			result.rejectValue("newPasswordConfirm", null, "请输入重复新密码");
		} else if (updatePasswordForm.getNewPassword().length() < SIX) {
			result.rejectValue("newPassword", null, "密码的长度必须大于等于" + SIX);
		} else if (!updatePasswordForm.getNewPassword().equals(
			updatePasswordForm.getNewPasswordConfirm())) {
			result.rejectValue("newPasswordConfirm", null, "两次密码不相等");
		}else if(!CUSTOMER_SOURCE.contains(customer.getSource().getId())){
			customer.setSource(new Code(Code.USER_SOURCE_EC_FRONT));
			customerService.bindingAccount(customer, updatePasswordForm.getUsername(),
					updatePasswordForm.getNewPassword());
			modelAndView.addObject("login",updatePasswordForm.getUsername());
		}
		return modelAndView;
	}
}
