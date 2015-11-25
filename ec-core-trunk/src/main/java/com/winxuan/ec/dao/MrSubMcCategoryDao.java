/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.replenishment.MrSubMcCategory;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author monica
 * MC二次分类
 */
public interface MrSubMcCategoryDao {

	@Save
	void save(MrSubMcCategory mrSubMcCategory);
	
	@Delete
	void delete(MrSubMcCategory mrSubMcCategory);

	@Update
	void update(MrSubMcCategory mrSubMcCategory);
	
	@Query("SELECT msmc from MrSubMcCategory msmc")
	List<MrSubMcCategory> find(@Page Pagination pagination);
	
	/**
	 * 根据id获取MC二次分类
	 * @param id
	 * @return
	 */
	@Get
	MrSubMcCategory getMrSubMcCategory(Integer id);
	
	/**
	 * 根据MC分类获取对应的MC二次分类
	 * @param mcCategory
	 * @return
	 */
	@Query("from MrSubMcCategory msmc where msmc.mcCategory = ?")
	MrSubMcCategory getByMcCategory(String mcCategory);
	
	/**
	 * 根据MC二次分类获取对应的MC分类列表
	 * @param subMcCategory
	 * @return
	 */
	@Query("from MrSubMcCategory msmc where msmc.subMcCategory = ?")
	List<MrSubMcCategory> getBySubMcCategory(String subMcCategory);
	
	/**
	 * 获取所有的MC二次分类
	 */
	@Query("select distinct(subMcCategory) from MrSubMcCategory order by subMcCategory asc")
	List<String> getAllSubMcCategory();
	
	/**
	 * 根据MC二次分类获取对应的MC分类列表
	 */
	@Query("select distinct(mcCategory) from MrSubMcCategory where subMcCategory = ?")
	List<String> getMcCategorys(String subMcCategory);
	
	
	@Query("from MrSubMcCategory msmc where msmc.mcCategory like :mcCategory")
	List<MrSubMcCategory> findMrSubMcCategorys(@ParameterMap Map<String, Object> parameters, @Page Pagination pagination);
}
