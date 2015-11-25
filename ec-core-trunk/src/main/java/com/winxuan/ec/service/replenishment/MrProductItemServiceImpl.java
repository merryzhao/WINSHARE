/*
 * @(#)MrProductItemServiceImpl.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MrProductFreezeDao;
import com.winxuan.ec.dao.MrProductItemDao;
import com.winxuan.ec.dao.MrSubMcCategoryDao;
import com.winxuan.ec.dao.ProductSaleDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.ec.model.replenishment.MrProductItem;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.support.principal.PrincipalHolder;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * 
 * @author yangxinyi
 * @version 1.0,2013-8-22
 */
@Service("mrProductItemService")
@Transactional(rollbackFor = Exception.class)
public class MrProductItemServiceImpl implements Serializable,
		MrProductItemService {

	private static final Log LOG = LogFactory
			.getLog(MrProductItemServiceImpl.class);

	private static final long serialVersionUID = -1177141530202958300L;

	private static final String SYSTEM_REASON = "补货系统自动冻结该商品的补货";

	private static final String ARTIFICIAL_REASON = "人工上传补货申请审核后冻结该商品的补货";

	@InjectDao
	private MrProductItemDao mrProductItemDao;

	@InjectDao
	private MrProductFreezeDao mrProductFreezeDao;
	
	@InjectDao
	private MrSubMcCategoryDao mrSubMcCategoryDao;
	
	@Autowired
	private ProductSaleStockService productSaleStockService;

	@InjectDao
	private ProductSaleDao productSaleDao;

	private JdbcTemplate jdbcTemplate;

	@Override
	public void save(MrProductItem mrProductItem) {
		mrProductItemDao.save(mrProductItem);
	}

	@Override
	public void saveOrUpdate(MrProductItem mrProductItem) {
		mrProductItemDao.saveOrUpdate(mrProductItem);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MrProductItem> findProductItems(Map<String, Object> parameters,
			Short sort, Pagination pagination) {
		List<MrProductItem> items = this.mrProductItemDao.findMrProductItemsByType(parameters, sort,
				pagination);
		return items;
	}

	@Override
	public void update(MrProductItem mrProductItem) {
		this.mrProductItemDao.update(mrProductItem);
	}

	@Override
	public void updateCheckStatus(Long id, User operator) {
		// 下传冻结表
		PrincipalHolder.set(operator);
		MrProductItem mrProductItem = this.mrProductItemDao.get(id);
		ProductSale productSale = mrProductItem.getProductSale();
		Long time = System.currentTimeMillis();
		// 冻结往后随延45天，后根据需求将45天改为30天
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.DAY_OF_MONTH, 30);
		/**
		 * 取待补货商品的补货类型，以便设置冻结类型,覆盖机制的修改——不同类型
		 */
		Code type = mrProductItem.getType();
		
		// 是否已经手动冻结补货
		boolean isFreezeManual = this.mrProductFreezeDao
				.isExistByProductSaleAndTypeNew(productSale, new Code(
						Code.MR_FREEZE_MANUAL), mrProductItem.getDc());
		// 是否是系统自动冻结
		boolean isFreezeSystem = this.mrProductFreezeDao
				.isExistByProductSaleAndTypeNew(productSale, new Code(
						Code.MR_FREEZE_SYSTEM), mrProductItem.getDc());
		//是否已经人工上传补货申请审核后冻结
		boolean isFreezeArtificial = this.mrProductFreezeDao
				.isExistByProductSaleAndTypeNew(productSale, new Code(
						Code.MR_FREEZE_ARTIFICIAL),mrProductItem.getDc());
		if (null != mrProductItem) {
			if(isFreezeManual){
				MrProductFreeze mrProductFreeze = this.mrProductFreezeDao
				.findFreezeByProductSaleAndTypeNew(
						productSale, new Code(
								Code.MR_FREEZE_MANUAL), mrProductItem.getDc());
				mrProductFreeze.setProductSale(productSale);
				mrProductFreeze.setAvailableQuantity(mrProductItem.getAvailableQuantity());
				mrProductFreeze.setQuantity(mrProductItem.getReplenishmentQuantity());
				mrProductFreeze.setStartTime(new Date());
				mrProductFreeze.setCreateTime(new Date()); 
				mrProductFreeze.setEndTime(calendar.getTime());
				mrProductFreeze.setFlag(0);
				if (type.getId().equals(Code.MR_REPLENISH_TYPE_SYSTEM)) {
					mrProductFreeze.setType(new Code(Code.MR_FREEZE_SYSTEM));
					mrProductFreeze.setReason(SYSTEM_REASON);
				} else {
					mrProductFreeze.setType(new Code(Code.MR_FREEZE_ARTIFICIAL));
					mrProductFreeze.setReason(ARTIFICIAL_REASON);
				}
				mrProductFreeze.setDc(mrProductItem.getDc());
				this.mrProductFreezeDao.update(mrProductFreeze);
			}
			else if (isFreezeSystem){
				MrProductFreeze mrProductFreeze = this.mrProductFreezeDao
				.findFreezeByProductSaleAndTypeNew(productSale, new Code(Code.MR_FREEZE_SYSTEM), mrProductItem.getDc());
				mrProductFreeze.setProductSale(productSale);
				mrProductFreeze.setAvailableQuantity(mrProductItem.getAvailableQuantity());
				mrProductFreeze.setQuantity(mrProductItem.getReplenishmentQuantity());
				mrProductFreeze.setStartTime(new Date());
				mrProductFreeze.setCreateTime(new Date()); 
				mrProductFreeze.setEndTime(calendar.getTime());
				mrProductFreeze.setFlag(0);
				if (type.getId().equals(Code.MR_REPLENISH_TYPE_SYSTEM)) {
					mrProductFreeze.setType(new Code(Code.MR_FREEZE_SYSTEM));
					mrProductFreeze.setReason(SYSTEM_REASON);
				} else {
					mrProductFreeze.setType(new Code(Code.MR_FREEZE_ARTIFICIAL));
					mrProductFreeze.setReason(ARTIFICIAL_REASON);
				}
				mrProductFreeze.setDc(mrProductItem.getDc());
				this.mrProductFreezeDao.update(mrProductFreeze);
			}
			else if (isFreezeArtificial){
				MrProductFreeze mrProductFreeze = this.mrProductFreezeDao
				.findFreezeByProductSaleAndTypeNew(
						productSale, new Code(
								Code.MR_FREEZE_ARTIFICIAL), mrProductItem.getDc());
				mrProductFreeze.setProductSale(productSale);
				mrProductFreeze.setAvailableQuantity(mrProductItem.getAvailableQuantity());
				mrProductFreeze.setQuantity(mrProductItem.getReplenishmentQuantity());
				mrProductFreeze.setStartTime(new Date());
				mrProductFreeze.setCreateTime(new Date()); 
				mrProductFreeze.setEndTime(calendar.getTime());
				mrProductFreeze.setFlag(0);
				if (type.getId().equals(Code.MR_REPLENISH_TYPE_SYSTEM)) {
					mrProductFreeze.setType(new Code(Code.MR_FREEZE_SYSTEM));
					mrProductFreeze.setReason(SYSTEM_REASON);
				} else {
					mrProductFreeze.setType(new Code(Code.MR_FREEZE_ARTIFICIAL));
					mrProductFreeze.setReason(ARTIFICIAL_REASON);
				}
				mrProductFreeze.setDc(mrProductItem.getDc());
				this.mrProductFreezeDao.update(mrProductFreeze);
			}
			else{
				MrProductFreeze freeze = new MrProductFreeze();
				freeze.setProductSale(productSale);
				freeze.setAvailableQuantity(mrProductItem.getAvailableQuantity());
				freeze.setQuantity(mrProductItem.getReplenishmentQuantity());
				freeze.setStartTime(new Date());
				freeze.setCreateTime(new Date()); 
				freeze.setEndTime(calendar.getTime());
				freeze.setFlag(0);
				if (type.getId().equals(Code.MR_REPLENISH_TYPE_SYSTEM)) {
					freeze.setType(new Code(Code.MR_FREEZE_SYSTEM));
					freeze.setReason(SYSTEM_REASON);
				} else {
					freeze.setType(new Code(Code.MR_FREEZE_ARTIFICIAL));
					freeze.setReason(ARTIFICIAL_REASON);
				}
				freeze.setDc(mrProductItem.getDc());
				this.mrProductFreezeDao.save(freeze);
			}
		}

		// 更新审核状态
		mrProductItem.setCheck(true);
		mrProductItem.setAuditTime(new Date());
		this.mrProductItemDao.update(mrProductItem);
	}

	@Override
	public void delete(MrProductItem mrProductItem) {
		this.mrProductItemDao.delete(mrProductItem);
	}

	@Override
	public MrProductItem get(Long id) {
		return this.mrProductItemDao.get(id);
	}

	@Override
	public void exportProductItem(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> parameters,
			Pagination pagination) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("补货审核表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("记录ID");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("商品自编码");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("发货仓库");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("MC分类");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("补货数量");
		cell.setCellStyle(style);
		
		List<MrProductItem> items = new ArrayList<MrProductItem>();

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		if(parameters.get("subMcCategory") != null){
			String subMcCategory = parameters.get("subMcCategory").toString().replace("%", "");
			List<String> mcCategorys = this.mrSubMcCategoryDao.getMcCategorys(subMcCategory);
			if (mcCategorys.size() != 0 && !mcCategorys.isEmpty()){
				parameters.put("mcCategorys", mcCategorys);
				items = findProductItems(parameters, (short) 0, pagination);
			}
		}
		else{
			items = findProductItems(parameters, (short) 0, pagination);
		}
		for (int i = 0; i < items.size(); i++) {
			MrProductItem item = items.get(i);
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(item.getId());
			row.createCell(1).setCellValue(item.getProductSale().getOuterId());
			row.createCell(2).setCellValue(item.getDc().getName());
			row.createCell(3).setCellValue(
					item.getProductSale().getProduct().getMcCategory());
			row.createCell(4).setCellValue(item.getReplenishmentQuantity());
		}

		OutputStream out = null;
		// 第六步，将文件存到指定位置
		try {
			LOG.info("parameters:" + parameters);
			out = response.getOutputStream();
			response.setContentType("application/msexcel");// 定义输出类型
			String fileName = "补货审核表";
			// 为不同的浏览器，对文件名进行不同的编码转换
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("GBK"), "ISO8859-1")
					+ ".xls");
			wb.write(out);

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	@Override
	public List<MrProductItem> fetchData(InputStream inputStream)
			throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = workbook.getSheetAt(0);
		List<MrProductItem> list = new ArrayList<MrProductItem>();
		int rows = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i <= rows; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			long itemId = fetchLong(row.getCell(0));
			if (itemId == 0 || StringUtils.isBlank(itemId + "")) {
				throw new IOException("上传的补货审核的记录ID不能为空！");
			}
			int replenishmentQuantity = fetchInteger(row.getCell(4));
			MrProductItem mrProductItem = this.mrProductItemDao.get(itemId);
			if (null != mrProductItem) {
				mrProductItem.setReplenishmentQuantity(replenishmentQuantity);
				list.add(mrProductItem);
			} else {
				throw new IOException("上传的补货审核的记录ID的补货记录不存在！");
			}

		}
		return list;
	}

	@Override
	public void updateAll(List<MrProductItem> items) {
		for (MrProductItem mrProductItem : items) {
			this.mrProductItemDao.update(mrProductItem);
		}

	}

	private long fetchLong(HSSFCell cell) {
		String param = fetchString(cell);
		return StringUtils.isBlank(param) ? 0 : Long.parseLong(param);
	}

	private int fetchInteger(HSSFCell cell) {
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

	/**
	 * 手工批量上传补货申请，没有对应的id，直接将补货记录插入到mr_product_item表中
	 */
	@Override
	public List<MrProductItem> fetchArtificialApplication(
			InputStream inputStream) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = workbook.getSheetAt(0);
		List<MrProductItem> list = new ArrayList<MrProductItem>();
		int rows = sheet.getPhysicalNumberOfRows();
		int availableQuantity = 0;
		for (int i = 1; i <= rows; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				try {
					long productSaleId = fetchLong(row.getCell(0));
					long dc = fetchLong(row.getCell(1));
					int replenishmentQuantity = fetchInteger(row.getCell(2));
					String num = fetchString(row.getCell(3));
					ProductSale productSale = this.productSaleDao
							.findProductSale(productSaleId);
					/**
					 * 如果商品在限制补货表中，则不能采用人工上传的方式申请补货
					 * 若是其它类型的冻结，则可以上传
					 */
					boolean isFreezeRestrict = this.mrProductFreezeDao
							.isExistByProductSaleAndType(productSale, new Code(
									Code.MR_FREEZE_RESTRICT));
					if (isFreezeRestrict) {
						continue;
					}
					MrProductItem mrProductItem = new MrProductItem();
					/**
					 * 获取商品的可用量
					 */
					availableQuantity = productSaleStockService.getAvalibleQuantity(productSale, new Code(dc));
					mrProductItem.setAvailableQuantity(availableQuantity);
					mrProductItem.setProductSale(productSale);
					mrProductItem.setNum(num);
					mrProductItem
							.setReplenishmentQuantity(replenishmentQuantity);
					mrProductItem.setDc(new Code(dc));

					/**
					 * 获取商品的销售分级 如果商品不存在销售分级，则grade设置为空
					 */
					String grade = getProductGrade(productSaleId);
					if (getProductGrade(productSaleId) != null) {
						mrProductItem.setGrade(grade);
					} else {
						mrProductItem.setGrade(null);
					}
					mrProductItem.setCheck(false);
					mrProductItem.setModel(null);
					mrProductItem.setReplenishmentCycle(0);
					mrProductItem.setSafeQuantity(0);
					/**
					 * 设置创建时间
					 */
					Long time = System.currentTimeMillis();
					mrProductItem.setCreateTime(new Date(time));
					mrProductItem.setForecastQuantity(0);
					mrProductItem.setType(new Code(
							Code.MR_REPLENISH_TYPE_MANUAL));
					list.add(mrProductItem);
				} catch (DataIntegrityViolationException e) {
					e.getMessage();
					continue;
				}
			}
		}
		return list;
	}

	@Override
	public void saveAll(List<MrProductItem> items) {
		// TODO Auto-generated method stub
		for (MrProductItem mrProductItem : items) {
			this.updateOrSaveProductItem(mrProductItem);
			// this.mrProductItemDao.save(mrProductItem);
		}
	}

//	/**
//	 * 获取对应商品的可用量
//	 * 
//	 * @param productSaleId
//	 * @param dc
//	 * @return
//	 */
//	private Integer getQuantity(Long productSaleId, Long dc) {
//		final String queryStr = "select pss.stock-pss.sales as availablequantity from product_sale_stock pss"
//				+ " where pss.productsale = ? and dc = ? and pss.stock >= pss.sales";
//		LOG.info(queryStr);
//		try {
//			return jdbcTemplate.queryForObject(queryStr, Integer.class,
//					productSaleId, dc);
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//	}

	/**
	 * 获取对应商品的销售分级
	 * 
	 * @param productSaleId
	 * @return
	 */
	private String getProductGrade(Long productSaleId) {
		final String queryStr = "select psg.grade as grade from product_sale_grade psg"
				+ " where psg.productsale = ?";
		LOG.info(queryStr);
		try {
			return jdbcTemplate.queryForObject(queryStr, String.class,
					productSaleId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 根据productsale，dc，type和status选择对应的补货记录
	 */
	@Override
	public MrProductItem findArtificialProductItem(ProductSale productSale,
			Code dc, Code type, boolean status) {
		// TODO Auto-generated method stub
		return this.mrProductItemDao.findArtificialProductItem(productSale, dc,
				type, status);
	}

	/**
	 * 人工上传补货申请的覆盖机制：只要是没有审核的，都用后来的记录覆盖前面的记录
	 */
	private void updateOrSaveProductItem(MrProductItem mrProductItem) {

		ProductSale productSale = mrProductItem.getProductSale();
		Code dc = mrProductItem.getDc();
		Code type = mrProductItem.getType();
		boolean status = mrProductItem.isCheck();
		String grade = mrProductItem.getGrade();
		String num = mrProductItem.getNum();
		int replenishmentCycle = mrProductItem.getReplenishmentCycle();
		int safeQuantity = mrProductItem.getSafeQuantity();
		int forecastQuantity = mrProductItem.getForecastQuantity();
		int availableQuantity = mrProductItem.getAvailableQuantity();
		int replenishmentQuantity = mrProductItem.getReplenishmentQuantity();

		MrProductItem oldMrProductItem = this.findArtificialProductItem(
				productSale, dc, type, status);
		if (oldMrProductItem != null) {
			oldMrProductItem.setGrade(grade);
			oldMrProductItem.setNum(num);
			oldMrProductItem.setReplenishmentCycle(replenishmentCycle);
			oldMrProductItem.setSafeQuantity(safeQuantity);
			oldMrProductItem.setForecastQuantity(forecastQuantity);
			oldMrProductItem.setAvailableQuantity(availableQuantity);
			oldMrProductItem.setReplenishmentQuantity(replenishmentQuantity);
			oldMrProductItem.setCreateTime(new Date());
			this.mrProductItemDao.update(oldMrProductItem);
			// return oldMrProductItem;
		} else {
			this.mrProductItemDao.save(mrProductItem);
			// return mrProductItem;
		}
	}
}
