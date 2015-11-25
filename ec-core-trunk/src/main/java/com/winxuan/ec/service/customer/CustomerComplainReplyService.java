package com.winxuan.ec.service.customer;

import java.util.List;
import java.util.Map;


import com.winxuan.ec.model.customer.CustomerComplainReply;
import com.winxuan.framework.pagination.Pagination;
/**
 * 用户投诉
 * @author cast911 玄玖东
 *
 */
public interface CustomerComplainReplyService {

	List<CustomerComplainReply> find(Map<String, Object> parameters, Pagination pagination);
	
	CustomerComplainReply get(Long id);
	
	void save(CustomerComplainReply complainReplyInfo);
	
	void update(CustomerComplainReply complainReplyInfo);
	
	void delete(CustomerComplainReply complainReplyInfo);
	
}
