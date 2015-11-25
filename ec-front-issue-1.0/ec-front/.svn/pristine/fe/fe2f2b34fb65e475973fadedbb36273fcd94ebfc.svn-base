/*
 * @(#) AddressController.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.front.controller.address;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.customer.CustomerIP;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.IpUtils;
import com.winxuan.framework.util.ipseeker.IPSeeker;

/**
 * @author Min-Huang
 * @version 1.0,Mar 15, 2012
 */
@Controller
@RequestMapping("/ipaddress")
public class IpAddressController {

	/**
	 * 
	 */
	private static final String UNKNOWN = "";
	private static final String CITY = "city";
	private static final String PROVINCE = "province";
	private static final String COUNTRY = "country";
	private static final Long CHINA = 23L; 
	private static final Long XINAN = 6123L; 
	private static final Long SICHUAN = 175L; 
	private static final Long CHENGDU = 1507L; 
	private static final int AREA_TOWN = 6; 
	private static final int AREA_COUNTRY = 2; 
	/**
	 * 内蒙古、青海、海南、新疆、西藏、宁夏， 香港，澳门，台湾
	 */
	private static final List<Long> DELIVERY_AREA = Arrays.asList(168L,170L,158L,180L,178L,169L,179L,151L,176L); 
	private static final String[] COUNTRY_LIST = new String[] { "\u4e2d\u56fd",
			"\u667a\u5229", "\u6c99\u7279\u963f\u62c9\u4f2f",
			"\u585e\u6d66\u8def\u65af", "\u745e\u58eb", "\u745e\u5178",
			"\u65e5\u672c", "\u8461\u8404\u7259", "\u632a\u5a01",
			"\u5c3c\u65e5\u5229\u4e9a", "\u5357\u65af\u62c9\u592b",
			"\u5357\u975e", "\u58a8\u897f\u54e5", "\u79d8\u9c81",
			"\u5b5f\u52a0\u62c9", "\u8499\u53e4", "\u7f8e\u56fd",
			"\u65af\u91cc\u5170\u5361", "\u6cf0\u56fd",
			"\u5766\u6851\u5c3c\u4e9a", "\u8d8a\u5357", "\u82f1\u56fd",
			"\u5370\u5ea6\u5c3c\u897f\u4e9a", "\u5370\u5ea6",
			"\u610f\u5927\u5229", "\u7259\u4e70\u52a0", "\u5308\u7259\u5229",
			"\u65b0\u897f\u5170", "\u65b0\u52a0\u5761", "\u5e0c\u814a",
			"\u897f\u73ed\u7259", "\u4e4c\u62c9\u572d", "\u4e4c\u514b\u5170",
			"\u6587\u83b1", "\u59d4\u5185\u745e\u62c9", "\u571f\u8033\u5176",
			"\u9a6c\u6765\u897f\u4e9a", "\u9a6c\u5c14\u4ee3\u592b",
			"\u7f57\u9a6c\u5c3c\u4e9a", "\u4e39\u9ea6",
			"\u73bb\u5229\u7ef4\u4e9a", "\u6ce2\u5170",
			"\u6ce2\u591a\u9ece\u5404", "\u6bd4\u5229\u65f6",
			"\u4fdd\u52a0\u5229\u4e9a", "\u767d\u4fc4\u7f57\u65af",
			"\u5df4\u897f", "\u5df4\u62ff\u9a6c", "\u5df4\u57fa\u65af\u5766",
			"\u6fb3\u5927\u5229\u4e9a", "\u5965\u5730\u5229",
			"\u7231\u5c14\u5170", "\u57c3\u53ca", "\u963f\u8054\u914b",
			"\u963f\u6839\u5ef7", "\u5fb7\u56fd", "\u4fc4\u7f57\u65af",
			"\u6cd5\u56fd", "\u5362\u65fa\u8fbe", "\u5362\u68ee\u5821",
			"\u5217\u652f\u6566\u58eb\u767b", "\u9ece\u5df4\u5ae9",
			"\u8001\u631d", "\u79d1\u5a01\u7279", "\u5580\u9ea6\u9686",
			"\u6377\u514b", "\u67ec\u57d4\u5be8", "\u52a0\u62ff\u5927",
			"\u8377\u5170", "\u97e9\u56fd", "\u5173\u5c9b", "\u53e4\u5df4",
			"\u82ac\u5170", "\u83f2\u5f8b\u5bbe",
			"\u963f\u5c14\u53ca\u5229\u4e9a" };

	private static final String[] PROVINCE_LIST = new String[] {
			"\u5317\u4eac\u5e02", "\u6cb3\u5317\u7701", "\u5185\u8499\u53e4",
			"\u5c71\u897f\u7701", "\u5929\u6d25\u5e02",
			"\u9ed1\u9f99\u6c5f\u7701", "\u5409\u6797\u7701",
			"\u8fbd\u5b81\u7701", "\u5b89\u5fbd\u7701", "\u798f\u5efa\u7701",
			"\u6c5f\u82cf\u7701", "\u6c5f\u897f\u7701", "\u5c71\u4e1c\u7701",
			"\u4e0a\u6d77\u5e02", "\u6d59\u6c5f\u7701", "\u5e7f\u4e1c\u7701",
			"\u5e7f\u897f", "\u6d77\u5357\u7701", "\u6cb3\u5357\u7701",
			"\u6e56\u5317\u7701", "\u6e56\u5357\u7701", "\u8d35\u5dde\u7701",
			"\u56db\u5ddd\u7701", "\u897f\u85cf", "\u4e91\u5357\u7701",
			"\u91cd\u5e86\u5e02", "\u7518\u8083\u7701", "\u5b81\u590f",
			"\u9752\u6d77\u7701", "\u9655\u897f\u7701", "\u65b0\u7586",
			"\u6fb3\u95e8", "\u53f0\u6e7e", "\u9999\u6e2f" };

	private static final Logger LOG = LoggerFactory
			.getLogger(IpAddressController.class);
	
	private static final String CUSTOMER_COOKIE = "c";
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AreaService areaService;

	@RequestMapping
	public ModelAndView ipAddress(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("ipaddress/view");
		try {
			String ip = getIpAddr(request);
			LOG.info("DEBUG IPAddress:" + ip);
			Map<String, String> map = parse(ip);
			mav.addObject(COUNTRY, map.get(COUNTRY));
			mav.addObject(PROVINCE, map.get(PROVINCE));
			mav.addObject(CITY, map.get(CITY));
		} catch (Exception e) {
			mav.addObject(COUNTRY, UNKNOWN);
			mav.addObject(PROVINCE, UNKNOWN);
			mav.addObject(CITY, UNKNOWN);
			LOG.error(e.getMessage(), e);
		}
		return mav;
	}

	private Map<String, String> parse(String ip) {
		Map<String, String> map = new HashMap<String, String>();
		IPSeeker seeker = IPSeeker.getInstance();
		String countryAddress = seeker.getCountry(ip);
		String country = parseCountry(countryAddress);
		String province;
		map.put(COUNTRY, country);
		if (COUNTRY_LIST[0].equals(country)) {
			province = parseProvince(countryAddress);
			map.put(PROVINCE, province);
			map.put(CITY, parseCity(countryAddress, province));
		} else {
			map.put(PROVINCE, UNKNOWN);
			map.put(CITY, UNKNOWN);
		}

		return map;
	}

	/**
	 * @param address
	 * @return
	 */

	private String parseCity(String address, String province) {
		String city = UNKNOWN;
		if (!StringUtils.isBlank(address)) {
			int start = address.indexOf(province);
			if (start > -1) {
				if (province.indexOf("市") > -1) {
					int pos = address.indexOf("区");
					if (pos > -1) {
						city = address.substring(province.length(), pos + 1);
					}
				} else {
					int pos = address.indexOf("市");
					if (pos > -1) {
						city = address.substring(province.length(), pos + 1);
					}
				}
			}
		}
		return city;
	}

	/**
	 * @param address
	 * @return
	 */
	private String parseProvince(String address) {
		if (!StringUtils.isBlank(address)) {
			for (String province : PROVINCE_LIST) {
				if (address.indexOf(province) > -1) {
					return province;
				}
			}
		}
		return UNKNOWN;
	}

	/**
	 * @param country
	 * @return
	 */
	private String parseCountry(String country) {
		String name = "中国";
		for (String countryName : COUNTRY_LIST) {
			if (countryName.equals(country)) {
				return country;
			}
		}
		return name;
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getParameter("testip");
		if (StringUtils.isNotBlank(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	
	/**
	 * 通过淘宝ip库获取地址信息并记录信息
	 * customer，cookie可获取常用登陆ip信息
	 * 检查当前区域最后一级是否支持活到付款或包邮
	 * @param customer
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/ip",method=RequestMethod.GET)
	public ModelAndView ip(@MyInject Customer customer,HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("/customer/address/ip");
		String customerCookie = "";
		if(request.getCookies()!=null){
			for (Cookie cookie : request.getCookies()) {
				if(CUSTOMER_COOKIE.equals(cookie.getName())){
					customerCookie = cookie.getValue();
				}
			}
		}
		CustomerIP customerIP = getCustomerIP(customer,customerCookie);
		//new ip
		if(customerIP==null){
			try{
				customerIP = new CustomerIP(IpUtils.getClientIP(request));
				customerIP.setCustomerCookie(customerCookie);
				customerIP.setCustomer(customer);
				customerIP.setCountry(areaService.get(customerIP.getCountryname()).get(0));
				customerIP.setArea(areaService.get(customerIP.getRegion()).get(0));
				customerIP.setProvince(areaService.get(customerIP.getProvincename()).get(0));
				customerIP.setCity(areaService.get(customerIP.getCityname()).get(0));
				if(StringUtils.isBlank(customerIP.getIsp())){
					throw new Exception("无法获取真实IP地址！");
				}
			}catch (Exception e) {
				customerIP.setCustomerCookie(customerCookie);
				customerIP.setCustomer(customer);
				customerIP.setCountry(areaService.get(CHINA));
				customerIP.setArea(areaService.get(XINAN));
				customerIP.setProvince(areaService.get(SICHUAN));
				customerIP.setCity(areaService.get(CHENGDU));
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"fail");
			}
			customerService.saveorupdateCustomerIp(customerIP);
		}
		if(customerIP.getLastArea()!=null){
			supportDelivery(modelAndView, customerIP.getLastArea(), customerIP);
		}
		modelAndView.addObject("customerIP", customerIP);
		return modelAndView;
	}
	
	/**
	 * 修改常用ip地址
	 * 设置乡镇一级区县
	 * 验证区县支持38包邮或货到付款
	 * @param id
	 * @param addressForm
	 * @return
	 */
	@RequestMapping(value = "/selectArea",method=RequestMethod.POST)
	public ModelAndView selectTown(@RequestParam Long id,@RequestParam Long areaid){
		ModelAndView modelAndView = new ModelAndView("/customer/address/ip");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cid", id);
		List<CustomerIP> ips = customerService.getCustomerIPByCustomer(params);
		if(!ips.isEmpty()){
			CustomerIP customerIP = ips.get(0);
			Area area = areaService.get(areaid);
			if(area!=null){
				if(area.getLevel()==AREA_TOWN){
					customerIP.setTown(area);
					customerIP.setCounty(area.getParent());
					customerIP.setCity(area.getParent().getParent());
					customerIP.setProvince(area.getParent().getParent().getParent());
					customerIP.setArea(area.getParent().getParent().getParent().getParent());
					customerIP.setCountry(area.getParent().getParent().getParent().getParent().getParent());
					supportDelivery(modelAndView, area, customerIP);
					customerService.saveorupdateCustomerIp(customerIP);
				}else if(area.getLevel()==AREA_COUNTRY){
					customerIP.setArea(area);
					customerIP.setCountry(area.getParent());
					customerIP.setCity(null);
					customerIP.setProvince(null);
					customerIP.setCounty(null);
					customerIP.setTown(null);
					customerService.saveorupdateCustomerIp(customerIP);
				}
				modelAndView.addObject(ControllerConstant.RESULT_KEY,area.getLevel());
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"success");
				return modelAndView;
			}
		}
		modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"fail");
		return modelAndView;
	}
	
	public void supportDelivery(ModelAndView modelAndView,Area area,CustomerIP customerIP){
		//满38免邮费区域
		modelAndView.addObject("delivery",!DELIVERY_AREA.contains(area.getLevel()>=3?
				customerIP.getProvince().getId():area.getId())&&area.isMainlandChina());
		//支持货到付款
		modelAndView.addObject("support",area.isSupportCod());
	}
	
	private CustomerIP getCustomerIP(Customer customer,String customerCookie){
			CustomerIP customerIP = null;
			Map<String,Object> params = new HashMap<String,Object>();
			List<CustomerIP> ips = null;
			if(customer!=null&&customer.getId()!=null){
				params.put("customer", customer);
				ips = customerService.getCustomerIPByCustomer(params);
			}else if(StringUtils.isNotBlank(customerCookie)){
				params.put("customercookie", customerCookie);
				ips = customerService.getCustomerIPByCustomer(params);
			}
			if(CollectionUtils.isNotEmpty(ips)){
				customerIP = ips.get(0);
				customerIP.setCustomerCookie(customerCookie);
			}
			return customerIP;
	}
}
