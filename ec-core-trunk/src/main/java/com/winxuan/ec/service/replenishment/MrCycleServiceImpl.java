package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MrCycleDao;
import com.winxuan.ec.model.category.McCategory;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.replenishment.MrCycle;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 补货周期管理
 * 
 * @author yangxinyi
 * 
 */
@Service("mrCycleService")
@Transactional(rollbackFor = Exception.class)
public class MrCycleServiceImpl implements MrCycleService, Serializable {

	private static final long serialVersionUID = -18650394733631928L;

	@InjectDao
	MrCycleDao mrCycleDao;

	@Override
	public void create(MrCycle mrcycle) {
		mrcycle.setCreateTime(new Date());
		mrCycleDao.save(mrcycle);
	}

	@Override
	public void save(MrCycle mrCycle) {
		this.mrCycleDao.save(mrCycle);
	}

	@Override
	public void delete(MrCycle mrCycle) {
		this.mrCycleDao.delete(mrCycle);
	}

	@Override
	public void update(MrCycle mrCycle) {
		this.mrCycleDao.update(mrCycle);
	}

	@Override
	public MrCycle get(Long id) {
		return this.mrCycleDao.get(id);
	}

	@Override
	public List<MrCycle> getAll(Pagination pagination) {
		return this.mrCycleDao.findMrCycles(null, pagination);
	}

	@Override
	public List<MrCycle> findByMC(String mccategory) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mcCategory", mccategory);
		return this.mrCycleDao.findMrCycles(params, null);
	}

	@Override
	public void saveData(InputStream is) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		// 过滤第一行的头信息
		for (int i = 1; i <= rows; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}

			String mcCategoryId = fetchString(row.getCell(0));
			if (StringUtils.isBlank(mcCategoryId)) {
				throw new IOException("上传的补货周期的MC分类不能为空！");
			}
			String dcStr = fetchString(row.getCell(4));
			Long dcId = null;
			if (StringUtils.isNotBlank(dcStr)) {
				dcId = Long.parseLong(dcStr);
			} else {
				throw new IOException("上传的补货周期的DC不能为空！");
			}
			MrCycle oldCycle = mrCycleDao.findMrCycle(mcCategoryId, dcId);
			if (oldCycle == null) {
				MrCycle mrCycle = new MrCycle();
				McCategory mcCategory = new McCategory();
				mcCategory.setId(mcCategoryId);
				mrCycle.setMcCategory(mcCategory);
				mrCycle.setCreateTime(new Date());
				mrCycle.setSaleCycle(fetchInteger(row.getCell(1)));
				mrCycle.setReplenishmentCycle(fetchInteger(row.getCell(2)));
				mrCycle.setTransitCycle(fetchInteger(row.getCell(3)));
				mrCycle.setDc(new Code(dcId));
				mrCycleDao.save(mrCycle);
			} else {
				oldCycle.setSaleCycle(fetchInteger(row.getCell(1)));
				oldCycle.setReplenishmentCycle(fetchInteger(row.getCell(2)));
				oldCycle.setTransitCycle(fetchInteger(row.getCell(3)));
				mrCycleDao.update(oldCycle);
			}

		}
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

}
