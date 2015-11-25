package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.replenishment.MrMcType;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author yangxinyi
 * 
 */
public interface MrMcTypeDao {

	@Save
	void save(MrMcType mrMcType);

	@Get
	MrMcType get(Long id);

	@Query("from MrMcType mmt")
	@Conditions({ @Condition("mmt.type.id = :mcType"),
			@Condition("mmt.mcCategory.id = :mcCategory") })
	List<MrMcType> find(@ParameterMap Map<String, Object> parameters);

	@Query("SELECT mr FROM MrMcType mr LEFT JOIN mr.mcCategory where mr.mcCategory.name is not null")
	List<MrMcType> find(@Page Pagination pagination);

	@Query("from MrMcType mmt where mmt.mcCategory.id = ?")
	MrMcType findByMcCategory(String mcCategory);

}
