package com.winxuan.ec.service.union;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.UnionDao;
import com.winxuan.ec.model.union.Union;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;
/**
 * @author zhoujun
 * @version 1.0,2011-9-7
 */

@Service("unionService")
@Transactional(rollbackFor = Exception.class)
public class UnionServiceImpl implements UnionService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -13791107722467772L;
	@InjectDao
	private UnionDao unionDao;
	
	@Override
	public void save(Union union) {
		unionDao.save(union);	
	}

	@Override
	public void update(Union union) {
		unionDao.update(union);	
	}
	
	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Union get(Long id){
		return unionDao.get(id);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Union> find(Map<String,Object> parmeter) {
		return unionDao.find(parmeter);
	}

}
