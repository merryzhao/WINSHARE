package com.winxuan.ec.service.customer;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.customer.CustomerComplain;
import com.winxuan.framework.pagination.Pagination;
/**
 * 用户投诉
 * @author cast911 玄玖东
 *
 */
public interface CustomerComplainService {

	List<CustomerComplain> find(Map<String, Object> parameters, Pagination pagination,Short sort);
	
	CustomerComplain get(Long id);
	
	void save(CustomerComplain complainInfo);
	
	void update(CustomerComplain complainInfo);
	
	void delete(CustomerComplain complainInfo);
}
