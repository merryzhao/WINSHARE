/*
 * @(#)MrSupplyService.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.replenishment;

import java.util.List;

import com.winxuan.ec.model.replenishment.MrSupply;

/**
 * description
 * 
 * @author wangbiao
 * @version 1.0 date 2013-8-23 上午11:15:36
 */

public interface MrSupplyService {
	
	void save(MrSupply mrSupply);

	void delete(MrSupply mrSupply);

	void update(MrSupply mrSupply);

	MrSupply get(Long id);

	List<MrSupply> getAll();
	
	long isExistedByGrade(String grade, Long dc);
	
	String findGradeById(Long id);
	
	Long findDCById(Long id);
}
