/*
 * @(#)MrSupplyDao.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.replenishment.MrSupply;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description 团购权重数据访问接口
 * 
 * @author wangbiao
 * @version 1.0 date 2013-8-23 上午10:52:27
 */

public interface MrSupplyDao {

	@Save
	void save(MrSupply mrSupply);
	
	@Delete
	void delete(MrSupply mrSupply);

	@Update
	void update(MrSupply mrSupply);
	
	@Get
	MrSupply get(Long id);
	
	@Query("from MrSupply ms")
	@OrderBys({ @OrderBy("ms.id asc") })
	List<MrSupply> findMrSupplies();
	
	@Query("from MrSupply ms where ms.grade =? and ms.dc.id=?")
	long isExistedByGrade(String grade, Long dc);
	
	@Query("from MrSupply ms where ms.grade = ?")
	MrSupply find(String grade);

	@Query(value = "select grade from mr_supply ms where id=? ", sqlQuery = true)
	String findGradeById(Long id);
	
	@Query(value = "select dc from mr_supply ms where id=? ", sqlQuery = true)
	Long findDCById(Long id);

}
