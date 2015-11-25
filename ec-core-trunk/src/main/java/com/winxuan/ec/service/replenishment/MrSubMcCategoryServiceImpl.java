/**
 * 
 */
package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MrSubMcCategoryDao;
import com.winxuan.ec.model.replenishment.MrSubMcCategory;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author monica
 * MC二次分类
 */
@Service("mrSubMcCategoryService")
@Transactional(rollbackFor = Exception.class)
public class MrSubMcCategoryServiceImpl implements MrSubMcCategoryService, Serializable{
	
	private static final long serialVersionUID = -2837672944546450485L;

	@InjectDao
	public MrSubMcCategoryDao mrSubMcCategoryDao;

	@Override
	public MrSubMcCategory getMrSubMcCategory(Integer id) {
		// TODO Auto-generated method stub
		return mrSubMcCategoryDao.getMrSubMcCategory(id);
	}

	@Override
	public MrSubMcCategory getByMcCategory(String mcCategory) {
		// TODO Auto-generated method stub
		return mrSubMcCategoryDao.getByMcCategory(mcCategory);
	}

	@Override
	public List<MrSubMcCategory> getBySubMcCategory(String subMcCategory) {
		// TODO Auto-generated method stub
		return mrSubMcCategoryDao.getBySubMcCategory(subMcCategory);
	}

	/**
	 * 返回所有的MC二次分类
	 */
	@Override
	public List<MrSubMcCategory> getByPage(Pagination pagination) {
		// TODO Auto-generated method stub
		return mrSubMcCategoryDao.find(pagination);
	}
	
	/**
	 * 根据指定的参数查找MC二次分类
	 */
	@Override
	public List<MrSubMcCategory> findMrSubMcCategorys(Map<String, Object> parameters, Pagination pagination){
		if(parameters.get("mcCategory") != null && !parameters.get("mcCategory").toString().isEmpty()){
			return mrSubMcCategoryDao.findMrSubMcCategorys(parameters, pagination);
		}
		else{
			return mrSubMcCategoryDao.find(pagination);
		}
	}

	/**
	 * 上传MC二次分类
	 */
	@Override
	public void saveSubMcCategoryData(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		// 跳过第一行的头信息
		for (int i = 1; i <= rows; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null || row.getCell(1) == null) {
				continue;
			}
			String mcCategoryId = fetchString(row.getCell(0));
			MrSubMcCategory mrSubMcCategory = mrSubMcCategoryDao.getByMcCategory(mcCategoryId);
			/**
			 * 考察MC二次分类是否已经存在，如果是，则更新；反之，则保存
			 */
			if(mrSubMcCategory == null) {
				mrSubMcCategory = new MrSubMcCategory();
				mrSubMcCategory.setMcCategory(mcCategoryId);
				mrSubMcCategory.setSubMcCategory(fetchString(row.getCell(1)));
				mrSubMcCategoryDao.save(mrSubMcCategory);
			}
			else{
				mrSubMcCategory.setSubMcCategory(fetchString(row.getCell(1)));
				mrSubMcCategoryDao.update(mrSubMcCategory);
			}
		}
	}

	private String fetchString(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.toString().trim();
	}

	@Override
	public List<String> getAllSubMcCategory() {
		// TODO Auto-generated method stub
		return mrSubMcCategoryDao.getAllSubMcCategory();
	}

	@Override
	public List<String> getMcCategorys(String subMcCategory) {
		// TODO Auto-generated method stub
		return mrSubMcCategoryDao.getMcCategorys(subMcCategory);
	}

	@Override
	public void delete(MrSubMcCategory mrSubMcCategory) {
		// TODO Auto-generated method stub
		this.mrSubMcCategoryDao.delete(mrSubMcCategory);
	}
}
