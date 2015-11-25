package com.winxuan.ec.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.DciLogDao;
import com.winxuan.ec.model.product.DciLog;
import com.winxuan.framework.dynamicdao.InjectDao;
/**
 * 
 * @author cl
 *
 */
@Service("dciLogService")
@Transactional(rollbackFor = Exception.class)
public class DciLogServiceImpl implements DciLogService {

	@InjectDao
	DciLogDao dciLogDao;
	@Override
	public void save(DciLog dciLog) {
		dciLogDao.save(dciLog);

	}

}
