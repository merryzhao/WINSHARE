/**
 * 
 */
package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.replenishment.MrSubMcCategory;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author monica
 * MC二次分类
 */
public interface MrSubMcCategoryService {

	/**
	 * 根据id获取对应的MC二次分类
	 * @param id
	 * @return
	 */
	MrSubMcCategory getMrSubMcCategory(Integer id);
	
	/**
	 * 根据MC分类获取对应的MC二次分类
	 * @param mcCategory
	 * @return
	 */
	MrSubMcCategory getByMcCategory(String mcCategory);
	
	/**
	 * 根据MC二次分类获取对应的MC分类列表
	 * @param subMcCategory
	 * @return
	 */
	List<MrSubMcCategory> getBySubMcCategory(String subMcCategory);
	
	/**
	 * 获取所有的MC二次分类
	 * @param pagination
	 * @return
	 */
	List<MrSubMcCategory> getByPage(Pagination pagination);
	
	/**
	 * 上传MC二次分类
	 * @param is
	 * @throws IOException
	 */
	void saveSubMcCategoryData(InputStream is) throws IOException;
	
	List<String> getAllSubMcCategory();
	
	/**
	 * 根据MC二次分类获取对应的MC分类列表
	 */
	List<String> getMcCategorys(String subMcCategory);
	
	/**
	 * 删除给定id的MC二次分类
	 */
	void delete(MrSubMcCategory mrSubMcCategory);
	
	/**
	 * 查找MC二次分类列表
	 */
	List<MrSubMcCategory> findMrSubMcCategorys(Map<String, Object> parameters, Pagination pagination);
}
