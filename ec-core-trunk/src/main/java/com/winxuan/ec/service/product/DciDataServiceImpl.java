package com.winxuan.ec.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.DciDataDao;
import com.winxuan.ec.model.product.DciData;
import com.winxuan.framework.dynamicdao.InjectDao;
/**
 * 
 * @author cl
 *
 */
@Service("dciDataService")
@Transactional(rollbackFor = Exception.class)
public class DciDataServiceImpl implements DciDataService {
	
	@InjectDao
	DciDataDao dciDataDao;

	@Override
	public DciData get(Long id) {
		return dciDataDao.get(id);
	}

}
