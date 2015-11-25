/*
 * @(#)CustomerAddressController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.CustomerAddressException;
import com.winxuan.ec.model.customer.CustomerAddress;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.util.StringUtils;

/**
 * 用户地址
 * @author  HideHai
 * @version 1.0,2011-8-9
 */
@Controller
@RequestMapping(value="/customer/address")
public class AddressController {
	private static final Log LOG = LogFactory.getLog(AddressController.class);
	private static final String ADDRESS_KEY="address";
	private static final Long CHINA = 23L;
	
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AreaService areaService;

	/**
	 * 获取用户常用地址
	 * @param customer
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(@MyInject Customer customer){
		ModelAndView modelAndView = new ModelAndView("/customer/address/list");
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		if(customer!=null){
			List<CustomerAddress> addresses = customer.getAddressList();
			if(addresses!=null){
				modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
				modelAndView.addObject("addresses", addresses);
			}
		}
		return modelAndView;
	}
	
	/**
	 * 获取用户常用地址
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="/{addressId}",method=RequestMethod.GET)
	public ModelAndView view(@MyInject Customer customer,@PathVariable Long addressId){
		ModelAndView modelAndView = new ModelAndView("/customer/address/view");
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		if(customer!=null){
			CustomerAddress address = customerService.getCustomerAddress(addressId);
			if(address!=null && address.getCustomer().equals(customer)){
				modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
				modelAndView.addObject(ADDRESS_KEY, address);
			}
		}
		return modelAndView;
	}

	/**
	 * 添加常用地址
	 * @param customer
	 * @param addressForm
	 * @param result
	 * @return
	 * @throws CustomerAddressException 
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView add(@MyInject Customer customer,@Valid AddressForm addressForm,BindingResult result) throws CustomerAddressException{
		ModelAndView modelAndView = new ModelAndView("/customer/address/add");
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		if(!result.hasErrors()){
			CustomerAddress customerAddress = new CustomerAddress();
			customerAddress.setConsignee(addressForm.getConsignee());
			customerAddress.setCountry(addressForm.getCountry());
			customerAddress.setProvince(addressForm.getProvince());
			customerAddress.setCity(addressForm.getCity());
			customerAddress.setAddress(addressForm.getAddress());
			customerAddress.setDistrict(addressForm.getDistrict());
			bindTown(customer, addressForm, customerAddress);
			customerAddress.setPhone(addressForm.getPhone());
			customerAddress.setMobile(addressForm.getMobile());
			customerAddress.setZipCode(addressForm.getZipCode());
			customerAddress.setEmail(addressForm.getEmail());
			customerAddress.setCustomer(customer);
			if(StringUtils.isNullOrEmpty(addressForm.getPhone()) 
					&& StringUtils.isNullOrEmpty(addressForm.getMobile())){
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "电话号码/手机必须选择一项!");
				return modelAndView;
			}
			customerService.saveAddress(customerAddress);
			if(addressForm.isUsual()){
				customerService.updateAddressUsual(customerAddress,addressForm.isUsual());
			}
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			modelAndView.addObject(ADDRESS_KEY, customerAddress);
		}else{
			List<ObjectError> errors =  result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for(ObjectError error : errors){
				log.info(error.getDefaultMessage());
				sb.append(error.getDefaultMessage());
				sb.append("\r\n");
			}
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, sb.toString());
		}
		return modelAndView;
	}

	/**
	 * @param customer
	 * @param addressForm
	 * @param customerAddress
	 * @throws CustomerAddressException
	 */
	private void bindTown(Customer customer, AddressForm addressForm,
			CustomerAddress customerAddress) throws CustomerAddressException {
		if(addressForm.getTown().getId().longValue() == -1 && !CHINA.equals(addressForm.getCountry().getId())){
			customerAddress.setTown(areaService.getDefTownByDistrict(addressForm.getDistrict()));
		} else if(addressForm.getTown().getId().longValue() != -1){
			customerAddress.setTown(addressForm.getTown());
		} else{
			LOG.error("添加收货地址时乡镇信息为空");
			throw new CustomerAddressException(customer, "乡镇信息为空");
		}
	}

	/**
	 * 更新地址
	 * @param customer
	 * @param customerAddress
	 * @return
	 * @throws CustomerAddressException 
	 */
	@RequestMapping(method=RequestMethod.PUT)
	public ModelAndView update(@MyInject Customer customer,@Valid AddressForm addressForm,BindingResult result) throws CustomerAddressException{
		ModelAndView modelAndView = new ModelAndView("/customer/address/update");
		CustomerAddress address;
		if(addressForm.getId()!= null && !result.hasErrors()){
			address = customerService.getCustomerAddress(addressForm.getId());
			address.setConsignee(addressForm.getConsignee());
			address.setAddress(addressForm.getAddress());
			address.setEmail(addressForm.getEmail());
			address.setZipCode(addressForm.getZipCode());
			address.setMobile(addressForm.getMobile());
			address.setPhone(addressForm.getPhone());
			address.setCountry(areaService.get(addressForm.getCountry().getId()));
			address.setProvince(areaService.get(addressForm.getProvince().getId()));
			address.setCity(areaService.get(addressForm.getCity().getId()));
			address.setDistrict(areaService.get(addressForm.getDistrict().getId()));
			bindTown(customer, addressForm, address);
			customerService.updateAddress(address);
			if(addressForm.isUsual()){
				customerService.updateAddressUsual(address,addressForm.isUsual());
			}
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			modelAndView.addObject(ADDRESS_KEY, address);			
		}else{
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/usual",method=RequestMethod.PUT)
	public ModelAndView updateUsual(@MyInject Customer customer,@RequestParam Long id){
		ModelAndView modelAndView = new ModelAndView("/customer/address/usual");
		CustomerAddress address = customerService.getCustomerAddress(id, customer);
		
		if(address == null){
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		else {
			customerService.updateAddressUsual(address,true);
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		}
		return modelAndView;
	}

	/**
	 *  删除常用地址
	 * @param customer
	 * @param addressId
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ModelAndView delete(@MyInject Customer customer, @PathVariable Long id){
		ModelAndView modelAndView = new ModelAndView("/customer/address/delete");
		CustomerAddress customerAddress = customerService.getCustomerAddress(id);
		
		if(customerAddress.getCustomer().equals(customer)){
			customerService.deleteAddress(customerAddress);			
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			modelAndView.addObject(ADDRESS_KEY, customerAddress);			
		}else{
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return modelAndView;
	}
	
	
	
}

