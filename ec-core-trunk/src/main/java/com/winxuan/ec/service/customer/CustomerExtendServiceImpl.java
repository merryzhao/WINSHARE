package com.winxuan.ec.service.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CustomerExtendDao;
import com.winxuan.ec.model.customer.CustomerExtend;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * @author  周斯礼
 * @version 2012-11-8
 */

@Service(value="customerExtendService")
@Transactional(rollbackFor=Exception.class)
public class CustomerExtendServiceImpl implements CustomerExtendService {

	@InjectDao
	private CustomerExtendDao customerExtendDao;

	@Override
	public List<CustomerExtend> find(Map<String, Object> parameters) {
		return customerExtendDao.find(parameters);
	}
	@Override
	public boolean payEmailExist(String payEmail){
		if(StringUtils.isBlank(payEmail)){
			return false;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("payEmail", payEmail);
		List<CustomerExtend> customerExtend = find(parameters);
		return customerExtend != null && customerExtend.size() > 0;
	}
	
	

}


