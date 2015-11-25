package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import com.winxuan.ec.model.replenishment.MrMcType;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author yangxinyi
 *
 */
public interface MrMcTypeService {
	
	void save(MrMcType mrMcType);
	
	void saveAll(List<MrMcType> mrMcTypeList);

	MrMcType get(Long id);
	
	/**
	 * 获取所有数据
	 * @return
	 */
	List<MrMcType> getAll();
	
	/**
	 * 分页获取数据
	 * @param pagination
	 * @return
	 */
	List<MrMcType> getByPage(Pagination pagination);
	
	/**
	 * 导入的EXCEL表必须是2003版本的
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	void saveData(InputStream inputStream) throws IOException;
	
	/**
	 * 根据mccategory查找对应mrmctype
	 * 
	 */
	MrMcType findByMcCategory(String mcCategory);
	
	/**
	 * 根据MC分类获取定位表数据
	 * @param mccategory
	 * @param listprice
	 * @return
	 */
	int getSafeStockAmount(String mccategory, BigDecimal listprice);
}
