package com.winxuan.ec.service.customer;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerExtend;

/**
 * @author  周斯礼
 * @version 2012-11-8
 */

public interface CustomerExtendService {

	List<CustomerExtend> find(Map<String, Object> parameters);
	
	boolean payEmailExist(String payEmail);
}


