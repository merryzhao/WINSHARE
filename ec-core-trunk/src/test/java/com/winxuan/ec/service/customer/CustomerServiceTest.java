package com.winxuan.ec.service.customer;

import org.junit.Assert;
import org.junit.Test;

import com.winxuan.ec.exception.RegisterEmailDuplicateException;
import com.winxuan.ec.exception.RegisterNameDuplicateException;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.BaseTest;
import com.winxuan.framework.util.BigDecimalUtils;

/**
 * 顾客测试用例
 * @author Heyadong
 *
 */
public class CustomerServiceTest extends BaseTest{
	
	/**
	 * 测试 用户注册.成功
	 * @throws RegisterEmailDuplicateException 
	 * @throws RegisterNameDuplicateException 
	 */
	@Test
	public void testRegisterCaseSuccess() throws RegisterNameDuplicateException, RegisterEmailDuplicateException{
		Customer customer = CustomerBeans.createCustomer();
		Assert.assertNull(customer.getId());
		services.customerService.register(customer);
		Assert.assertNotNull(customer.getId()); 
		Assert.assertNotNull(customer.getAccount().getId());
		customer = services.customerService.get(customer.getAccount().getId());
		Assert.assertEquals(customer.getAccount().getBalance(), BigDecimalUtils.ZERO);
	}
	
	
	/**
	 * 用户注册 失败  原因,用户名重复
	 * @throws RegisterEmailDuplicateException
	 * @throws RegisterNameDuplicateException 
	 */
	@Test(expected=RegisterNameDuplicateException.class)
	public void testRegisterCaseRegisterNameDuplicateException() throws RegisterEmailDuplicateException, RegisterNameDuplicateException{
		testRegisterCaseSuccess();
		Customer customer = CustomerBeans.createCustomer();
		customer.setEmail("1" + customer.getEmail());
		services.customerService.register(customer);
		
	}
	
	/**
	 * 用户注册失败  原因, 电子邮件重复
	 * @throws RegisterNameDuplicateException 
	 * @throws RegisterEmailDuplicateException 
	 */	
	@Test(expected=RegisterEmailDuplicateException.class)
	public void testRegisterCaseRegisterEmailDuplicateException() throws RegisterNameDuplicateException, RegisterEmailDuplicateException{
		testRegisterCaseSuccess();
		Customer customer = CustomerBeans.createCustomer();
		customer.setName("Ab" + customer.getName());
		services.customerService.register(customer);
	}
}
