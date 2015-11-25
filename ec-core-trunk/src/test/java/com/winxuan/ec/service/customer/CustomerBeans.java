package com.winxuan.ec.service.customer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.customer.CustomerAddress;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.area.AreaBeans;
import com.winxuan.framework.util.BigDecimalUtils;

/**
 * 顾客相关实体对象的创建工具
 * @author Heyadong
 *
 */
public class CustomerBeans {
	/**
	 * 实例化一个customer , 
	 * 
	 * @see {下列参数未设置}
	 * @see {CustomerAddress}
	 * @see {CustomerAccount}
	 * @return
	 */
	public static Customer createCustomer(){
		
		Customer customer = new Customer();
		customer.setLastLoginTime(new Date());
		customer.setAvailable(true);
		customer.setAvatar("A");
		customer.setBirthday(new Date());
		customer.setChannel(new Channel(Channel.CHANNEL_EC));
		
		customer.setCity(AreaBeans.createAreaCity());
		customer.setProvince(AreaBeans.createAreaProvince());
		customer.setCountry(AreaBeans.createAreaCountry());
		
		Random random = new Random();

		customer.setDiscount(new BigDecimal(1));
		customer.setEmail(random.nextInt(Integer.MAX_VALUE)+"@winxuan.com");
	
		//已验证
		short emailActive = 1;
		customer.setEmailActive(emailActive);
		
		customer.setMobile("13888888888");
		customer.setName(customer.getEmail());
		customer.setPassword("111111");
		customer.setPhone("81919191");
		customer.setRegisterTime(new Date());
		customer.setSource(createUserSource());
		//普通会员
		short gender = 0;
		customer.setGender(gender);
		//男
		short grade = 1;
		customer.setGrade(grade);
		return customer;
	}
	
	/**
	 * 创建一个account
	 * @see {下列参数未设置}
	 * @see {Customer}
	 * @return
	 */
	public static CustomerAccount createCustomerAccount(){
		CustomerAccount account = new CustomerAccount();
		account.setBalance(BigDecimalUtils.ZERO);
		account.setFrozen(BigDecimalUtils.ZERO);
		account.setLastUseTime(new Date());
		account.setPoints(0);
		return account;
	}
	
	/**
	 * 创建客户地址
	 * 
	 * @see {下列参数未设置}
	 * @see {Payment}
	 * @return
	 */
	public static CustomerAddress createCustomerAddress(){
		CustomerAddress address = new CustomerAddress();
		address.setAddress("中国四川省文轩路");
		address.setCity(AreaBeans.createAreaCity());
		address.setDistrict(AreaBeans.createAreaDistrict());
		address.setProvince(AreaBeans.createAreaProvince());
		address.setCountry(AreaBeans.createAreaCountry());
		Code deliveryOption = new Code(Code.DELIVERY_OPTION_ANYTIME);
		address.setDeliveryOption(deliveryOption);
		address.setEmail("system@winxuan.com");
		address.setMobile("13888888888");
		address.setZipCode("610000");
		address.setUsual(true);
		return address;
	}
	
	/**
	 * 用户数据来源,EC 前台
	 * @return
	 */
	public static Code createUserSource(){
		return new Code(Code.USER_SOURCE_EC_FRONT);
	}
}
