/*
 * @(#)MrProductFreezeDao.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.replenishment.MrModelChoose;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * @author  lijunhong
 * @version 1.0,2013-8-28
 */
public interface MrModelChooseDao {
	@Save
	void save(MrModelChoose mrModelChoose);
	
	@Get
	MrModelChoose get(Long id);
	
	@Delete
	void delete(MrModelChoose mrModelChoose);

	@Update
	void update(MrModelChoose mrModelChoose);
	
	@Query("from MrModelChoose mmc")
	@OrderBys({ @OrderBy("mmc.id desc") })
	List<MrModelChoose> findMrModelChoose();
	
	@Query("from MrModelChoose mmc where mmc.grade =? and mmc.id <> ?")
	long isExistedByGrade(String grade,Long id);

}
