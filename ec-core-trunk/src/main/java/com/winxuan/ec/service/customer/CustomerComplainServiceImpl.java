package com.winxuan.ec.service.customer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CustomerComplainDao;
import com.winxuan.ec.model.customer.CustomerComplain;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
/**
 * 用户投诉
 * @author cast911 玄玖东
 *
 */
@Service("customerComplainService")
@Transactional(rollbackFor = Exception.class)
public class CustomerComplainServiceImpl implements  CustomerComplainService{

	@InjectDao
	private CustomerComplainDao complainInfoDao;
	public CustomerComplainDao getComplainInfoDao() {
		return complainInfoDao;
	}
	public void setComplainInfoDao(CustomerComplainDao complainInfoDao) {
		this.complainInfoDao = complainInfoDao;
	}
	@Override
	public List<CustomerComplain> find(Map<String, Object> parameters, Pagination pagination,Short sort) {
		return this.complainInfoDao.find(parameters,pagination,sort);
	}
	@Override
	public CustomerComplain get(Long id) {
		return this.getComplainInfoDao().get(id);
	}
	@Override
	public void save(CustomerComplain complainInfo) {
		this.complainInfoDao.save(complainInfo);
		
	}
	@Override
	public void update(CustomerComplain complainInfo) {
		this.complainInfoDao.update(complainInfo);
		
	}
	@Override
	public void delete(CustomerComplain complainInfo) {
		this.complainInfoDao.delete(complainInfo);
		
	}

}
