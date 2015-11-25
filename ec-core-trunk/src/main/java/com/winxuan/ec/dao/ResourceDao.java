package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.authority.Resource;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 资源操作
 * @author sunflower
 *
 */
public interface ResourceDao {

	@Save
	void save(Resource resource);

	@Get
	Resource get(Long id);

	@Update
	void update(Resource resource);
	
	@Merge
	void merge(Resource resource);

	@Delete
	void delete(Resource resource);

	@Query("from Resource r where r.code = ? ")
	Resource find(String code);

	@Query("select distinct r from Resource r left join r.resourceGroups rg order by r.code ")
	@Conditions({ @Condition("rg.id = :groupId")})
	List<Resource> find(@Parameter("groupId") Long groupId, @Page Pagination pagination);

	@Query("from Resource r")
	List<Resource> findAll();
}
