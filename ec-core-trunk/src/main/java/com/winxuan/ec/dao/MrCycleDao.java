package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.replenishment.MrCycle;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author yangxinyi
 * 
 */
public interface MrCycleDao {

	@Save
	void save(MrCycle mrCycle);

	@Get
	MrCycle get(Long id);

	@Delete
	void delete(MrCycle mrCycle);

	@Update
	void update(MrCycle mrCycle);

	@SaveOrUpdate
	void saveOrUpdate(MrCycle mrCycle);

	@Query("select mc from MrCycle mc LEFT JOIN mc.mcCategory where mc.mcCategory.name is not null")
	@Conditions({ @Condition("mc.mcCategory.id = :mcCategory") })
	@OrderBys({ @OrderBy("mc.id desc") })
	List<MrCycle> findMrCycles(@ParameterMap Map<String, Object> parameters, @Page Pagination pagination);

	@Query("from MrCycle mc where mc.mcCategory.id = ? and mc.dc.id = ?")
	MrCycle findMrCycle(String mccategory, Long dcId);
}
