/*
 * @(#)MrProductFreezeServiceImpl.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.replenishment;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MrModelChooseDao;
import com.winxuan.ec.model.replenishment.MrModelChoose;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * description
 * @author  lijunhong
 * @version 1.0,2013-8-28
 */
@Service("mrModelChooseService")
@Transactional(rollbackFor = Exception.class)
public class MrModelChooseServiceImpl implements MrModelChooseService, Serializable{

	private static final long serialVersionUID = -861275187550654114L;
	
	@InjectDao
	MrModelChooseDao mrModelChooseDao;

	@Override
	public void save(MrModelChoose mrModelChoose) {
		mrModelChooseDao.save(mrModelChoose);
	}

	@Override
	public void delete(MrModelChoose mrModelChoose) {
		this.mrModelChooseDao.delete(mrModelChoose);
	}

	@Override
	public void update(MrModelChoose mrModelChoose) {
		this.mrModelChooseDao.update(mrModelChoose);
	}

	@Override
	public MrModelChoose get(Long id) {
		return this.mrModelChooseDao.get(id);
	}

	@Override
	public List<MrModelChoose> getAll() {
		return this.mrModelChooseDao.findMrModelChoose();
	}
	@Override
	public long isExistedByGrade(MrModelChoose mrModelChoose) {
		Long modelId = mrModelChoose.getId();
		if(null == mrModelChoose.getId() || "".equals(mrModelChoose.getId())){
			modelId = 0L;
		}
		return this.mrModelChooseDao.isExistedByGrade(mrModelChoose.getGrade(),modelId);
	}

	
}
