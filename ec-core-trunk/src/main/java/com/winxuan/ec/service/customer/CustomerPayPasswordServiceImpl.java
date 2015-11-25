package com.winxuan.ec.service.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.customer.CustomerExtend;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.util.StringUtils;

/**
 * @author  周斯礼
 * @version 2012-11-23
 */

@Service("customerPayPasswordService")
@Transactional(rollbackFor = Exception.class)
public class CustomerPayPasswordServiceImpl implements CustomerPayPasswordService{

	@Override
	public String getCustomerPayMobile(Customer customer) {
		CustomerExtend customerExtend = customer.getCustomerExtend();
		String payMobile = null;
		if(customerExtend != null){
			payMobile = customerExtend.getPayMobile();
			if(org.apache.commons.lang.StringUtils.isBlank(payMobile)){
				return null;
			}
			StringBuffer sb = new StringBuffer();
			sb.append(payMobile.substring(MagicNumber.ZERO, MagicNumber.FOUR)).append("***").append(payMobile.substring(MagicNumber.SEVEN));
			payMobile = sb.toString();
		}
		return payMobile;
	}

	@Override
	public boolean hasPayPassword(Customer customer) {
		CustomerExtend customerExtend = customer.getCustomerExtend();
		String paypassword = null;
		if(customerExtend != null){
			paypassword = customerExtend.getPayPassword();
			if(!StringUtils.isNullOrEmpty(paypassword)){
				return true;
			}
		}
		return false;
	}

}


