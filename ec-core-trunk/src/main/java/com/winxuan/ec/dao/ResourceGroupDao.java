package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.authority.ResourceGroup;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Evict;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 资源组操作
 * @author sunflower
 *
 */
public interface ResourceGroupDao {

	@Save
	void save(ResourceGroup resourceGroup);

	@Query("from ResourceGroup r where r.code = ? ")
	ResourceGroup find(String code);

	@Update
	void update(ResourceGroup resourceGroup);
	
	@Merge
	void merge(ResourceGroup resourceGroup);

	@Delete
	void delete(ResourceGroup resourceGroup);
	
	@Evict
	void evict(ResourceGroup resourceGroup);

	@Query("from ResourceGroup r")
	List<ResourceGroup> findAll();

	@Get
	ResourceGroup get(Long id);

}
