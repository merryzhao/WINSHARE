/*
 * @(#)MrSupplyServiceImpl.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.replenishment;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MrSupplyDao;
import com.winxuan.ec.model.replenishment.MrSupply;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * description
 * 
 * @author wangbiao
 * @version 1.0 date 2013-8-23 上午11:16:02
 */

@Service("mrSupplyService")
@Transactional(rollbackFor = Exception.class)
public class MrSupplyServiceImpl implements MrSupplyService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2966857774584886021L;

	@InjectDao
	private MrSupplyDao mrSupplyDao;

	@Override
	public void save(MrSupply mrSupply) {
		this.mrSupplyDao.save(mrSupply);
	}

	@Override
	public void delete(MrSupply mrSupply) {
		this.mrSupplyDao.delete(mrSupply);
	}

	@Override
	public void update(MrSupply mrSupply) {
		this.mrSupplyDao.update(mrSupply);
	}

	@Override
	public MrSupply get(Long id) {
		return this.mrSupplyDao.get(id);
	}

	@Override
	public List<MrSupply> getAll() {
		return this.mrSupplyDao.findMrSupplies();
	}

	@Override
	public long isExistedByGrade(String grade, Long dc) {
		return this.mrSupplyDao.isExistedByGrade(grade, dc);
	}

	@Override
	public String findGradeById(Long id) {
		return this.mrSupplyDao.findGradeById(id);
	}

	@Override
	public Long findDCById(Long id) {
		return this.mrSupplyDao.findDCById(id);
	}
	
}
