package com.winxuan.ec.service.customer;

import com.winxuan.ec.model.user.Customer;

/**
 * @author  周斯礼
 * @version 2012-11-23
 */

public interface CustomerPayPasswordService {

	String getCustomerPayMobile(Customer customer);
	
	boolean hasPayPassword(Customer customer);
}


