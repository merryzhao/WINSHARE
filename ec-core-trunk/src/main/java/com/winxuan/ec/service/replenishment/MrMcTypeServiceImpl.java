package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MrMcTypeDao;
import com.winxuan.ec.model.category.McCategory;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.replenishment.MrMcType;
import com.winxuan.ec.support.forecast.DefaultStockForecast;
import com.winxuan.ec.support.forecast.StockForecast;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author yangxinyi
 * 
 */
@Service("mrMcTypeService")
@Transactional(rollbackFor = Exception.class)
public class MrMcTypeServiceImpl implements MrMcTypeService, Serializable {

	private static final long serialVersionUID = 6486980993911187853L;
	@InjectDao
	MrMcTypeDao mrMcTypeDao;

	@Override
	public void save(MrMcType mrMcType) {
		// TODO Auto-generated method stub
		mrMcTypeDao.save(mrMcType);
	}

	@Override
	public void saveAll(List<MrMcType> mrMcTypeList) {
		// TODO Auto-generated method stub
		for (MrMcType mrMcType : mrMcTypeList) {
			mrMcTypeDao.save(mrMcType);
		}
	}

	/**
	 * 提取数据
	 * 
	 * @param is
	 * @param col
	 * @return
	 * @throws IOException
	 */
	@Override
	@Rollback(false)
	public void saveData(InputStream is) throws IOException {
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
			MrMcType mrMcType = mrMcTypeDao.findByMcCategory(mcCategoryId);
			if(mrMcType == null) {
				mrMcType = new MrMcType();
				McCategory mcCategory = new McCategory();
				mcCategory.setId(mcCategoryId);
				mrMcType.setMcCategory(mcCategory);
			}
			mrMcType.setType(new Code(Long.valueOf(fetchString(row.getCell(1)))));
			mrMcType.setQuantity(fetchInteger(row.getCell(2)));
			mrMcType.setBoundTop(fetchBigDecimal(row.getCell(3)));
			mrMcType.setBoundBottom(fetchBigDecimal(row.getCell(4)));
			mrMcType.setRatio(fetchBigDecimal(row.getCell(5)));
			mrMcType.setDefaultQuantity(fetchInteger(row.getCell(6)));
			mrMcTypeDao.save(mrMcType);
		}
	}

	private BigDecimal fetchBigDecimal(HSSFCell cell) {
		if (cell == null) {
			return new BigDecimal(0);
		}
		return new BigDecimal(cell.getNumericCellValue());
	}

	private Integer fetchInteger(HSSFCell cell) {
		String param = fetchString(cell);
		return StringUtils.isBlank(param) ? 0 : Integer.parseInt(param);
	}

	private String fetchString(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.toString().trim();
	}

	@Override
	public MrMcType get(Long id) {
		// TODO Auto-generated method stub
		return mrMcTypeDao.get(id);
	}

	@Override
	public List<MrMcType> getAll() {
		Pagination pagination = new Pagination(1000);
		return mrMcTypeDao.find(pagination);
	}
	
	@Override
	public List<MrMcType> getByPage(Pagination pagination) {
		return mrMcTypeDao.find(pagination);
	}

	@Override
	public int getSafeStockAmount(String mccategory, BigDecimal listprice) {
		MrMcType mrMcType = findByMcCategory(mccategory);
		StockForecast forecast = new DefaultStockForecast();
		return forecast.getSafeStockAmount(mrMcType.getQuantity(), mrMcType.getBoundTop(), 
				mrMcType.getBoundBottom(), mrMcType.getRatio(), mrMcType.getDefaultQuantity(), listprice);

	}

	@Override
	public MrMcType findByMcCategory(String mcCategory) {
		return mrMcTypeDao.findByMcCategory(mcCategory);
	}

}
