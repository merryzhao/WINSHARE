package com.winxuan.ec.service.order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.UnionOrderDao;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
/**
 * 
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
@Service("unionOrderService")
@Transactional(rollbackFor = Exception.class)
public class UnionOrderServiceImpl implements UnionOrderService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7955691614303727655L;
	
	@InjectDao
	UnionOrderDao unionOrderDao; 
	
	public void save(UnionOrder unionOrder) {
		unionOrderDao.save(unionOrder);
		
	}
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<UnionOrder> find(Map<String, Object> parameters,Short sort, Pagination pagination) {
		return unionOrderDao.find(parameters,sort,pagination);
	}
	@Override
	public UnionOrder getByOrderId(String orderId) {
		return unionOrderDao.getByOrderId(orderId);
	}
	

}
