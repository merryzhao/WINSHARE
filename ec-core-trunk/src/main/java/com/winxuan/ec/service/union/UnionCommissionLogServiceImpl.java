package com.winxuan.ec.service.union;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.UnionCommissionLogDao;
import com.winxuan.ec.model.union.UnionCommissionLog;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * @author zhoujun
 * @version 1.0,2011-9-26
 */
@Service("unionCommissionLogService")
@Transactional(rollbackFor = Exception.class)
public class UnionCommissionLogServiceImpl implements UnionCommissionLogService{
	@InjectDao
	UnionCommissionLogDao unionCommissionLogDao;

	@Override
	public List<UnionCommissionLog> findByUnionCommission(Long id) {
		return unionCommissionLogDao.findByUnionCommission(id); 
	} 
}
