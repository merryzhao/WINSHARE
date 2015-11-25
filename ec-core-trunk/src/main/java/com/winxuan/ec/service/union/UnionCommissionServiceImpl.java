package com.winxuan.ec.service.union;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.UnionCommissionDao;
import com.winxuan.ec.model.union.UnionCommission;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
/**
 * @author zhoujun
 * @version 1.0,2011-9-8
 */
@Service("unionCommissionService")
@Transactional(rollbackFor = Exception.class)
public class UnionCommissionServiceImpl implements UnionCommissionService,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9074330151453229129L;
	@InjectDao
	UnionCommissionDao unionCommissionDao;
	
	@Override
	public void save(UnionCommission unionCommission) {
		unionCommissionDao.save(unionCommission);		
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UnionCommission get(Long id) {
		return unionCommissionDao.get(id);
	}
	
	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UnionCommission get(Long id, String time) {
		return unionCommissionDao.getByUnionAndDate(id, time);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<UnionCommission> find(Map<String, Object> parameters,
			Short sort, Pagination pagination) {
		return unionCommissionDao.find(parameters, sort, pagination);
	}

	@Override
	public void update(UnionCommission unionCommission) {
		unionCommissionDao.update(unionCommission);
	}

	
}
