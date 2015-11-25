package com.winxuan.ec.service.customer;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CustomerComplainReplyDao;
import com.winxuan.ec.model.customer.CustomerComplainReply;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
/**
 * 用户投诉
 * @author cast911 玄玖东
 *
 */
@Service("customerComplainReplyService")
@Transactional(rollbackFor = Exception.class)
public class CustomerComplainReplyServiceImpl implements  CustomerComplainReplyService{

	@InjectDao
	private CustomerComplainReplyDao complainReplyInfoDao;
	public CustomerComplainReplyDao getComplainReplyInfoDao() {
		return complainReplyInfoDao;
	}
	public void setComplainReplyInfoDao(CustomerComplainReplyDao complainReplyInfoDao) {
		this.complainReplyInfoDao = complainReplyInfoDao;
	}
	@Override
	public List<CustomerComplainReply> find(Map<String, Object> parameters, Pagination pagination) {
		return this.complainReplyInfoDao.find(parameters,pagination);
	}
	@Override
	public CustomerComplainReply get(Long id) {
		return this.getComplainReplyInfoDao().get(id);
	}
	@Override
	public void save(CustomerComplainReply complainReplyInfo) {
		this.complainReplyInfoDao.save(complainReplyInfo);
		
	}
	@Override
	public void update(CustomerComplainReply complainReplyInfo) {
		this.complainReplyInfoDao.update(complainReplyInfo);
		
	}
	@Override
	public void delete(CustomerComplainReply complainReplyInfo) {
		this.complainReplyInfoDao.delete(complainReplyInfo);
		
	}
	
	

	
	

}
