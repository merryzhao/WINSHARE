/*
 * @(#)MrProductFreezeServiceImpl.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.replenishment;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MrCumulativeReceiveDao;
import com.winxuan.ec.dao.MrProductFreezeDao;
import com.winxuan.ec.dao.ProductDao;
import com.winxuan.ec.exception.ReplenishmentArtificialLimitException;
import com.winxuan.ec.exception.ReplenishmentRestrictLimitException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrCumulativeReceive;
import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.StringUtils;

/**
 * description
 * 
 * @author yangxinyi
 * @version 1.0,2013-8-26
 */
@Service("mrProductFreezeService")
@Transactional(rollbackFor = Exception.class)
public class MrProductFreezeServiceImpl implements MrProductFreezeService,
		Serializable {

	private static final Logger LOG = LoggerFactory
			.getLogger(MrProductFreezeServiceImpl.class);

	private static final long serialVersionUID = -861275187550654114L;

	private static final String MANUAL_REASON = "人工冻结补货";

	private static final String RESTRICT_REASON = "永久限制补货";

	private static final Long DEFAULT_DC = Code.DC_8A17;

	@InjectDao
	private MrProductFreezeDao mrProductFreezeDao;

	@InjectDao
	private ProductDao productDao;
	
	@InjectDao
	private MrCumulativeReceiveDao mrCumulativeReceiveDao;

	private JdbcTemplate jdbcTemplate;

	@Override
	public void save(MrProductFreeze mrProductFreeze) {
		mrProductFreezeDao.save(mrProductFreeze);
	}

	@Override
	public void updateFreezeFlag() {
		// 冻结和下传SAP的商品，在已收货或冻结到期时都要解冻
		/**
		 * 针对系统自动补货和人工申请补货导致冻结的商品
		 * 改动地方：人工申请补货导致冻结的商品遵循同样的解冻条件，注意SQL语句的优先级
		 * 后修改解冻逻辑，根据每日凌晨的库存增加量来判断商品是否应该解冻
		 * 如果将两个条件写到同一个SQL语句中，则执行效率较慢，因此拆开写
		 */
//		String sqlStr = "select mpf.id from mr_product_freeze mpf"
//				+ " left join product_sale_stock pss on mpf.productsale = pss.productsale and mpf.dc = pss.dc"
//				+ " where mpf.flag != 1"
//				+ " and (mpf.endtime < curdate() or (pss.stock > pss.sales and pss.stock-pss.sales > mpf.availablequantity "
//				+ "and pss.stock-pss.sales-mpf.availablequantity > mpf.quantity*0.5 and (mpf.type = 510001 or mpf.type = 510004)))";
		
//		String sqlStr = "select mpf.id from mr_product_freeze mpf "
//			+ "inner join mr_product_cumulative_receive mpcr on mpcr.productsale = mpf.productsale "
//			+ "where mpcr.dc = mpf.dc and mpf.flag != 1 and mpf.type in (510001,510004) " 
//			+ "and (mpcr.receive > mpf.quantity * 0.6)";
//		List<Long> freezeProductIdList = jdbcTemplate.queryForList(sqlStr, 
//				Long.class);
//		String sql = "select mpf.id from mr_product_freeze mpf where mpf.endtime < curdate() and flag != 1";
//		List<Long> overDueFreezedProducts = jdbcTemplate.queryForList(sql, Long.class);
//		freezeProductIdList.addAll(overDueFreezedProducts);
//		for (Long freezeProductId : freezeProductIdList) {
//			updateProductFreeze(freezeProductId);
//		}
	}
	
	public List<Long> getFreezed(){
		String sqlStr = "select mpf.id from mr_product_freeze mpf "
			+ "inner join mr_product_cumulative_receive mpcr on mpcr.productsale = mpf.productsale "
			+ "where mpcr.dc = mpf.dc and mpf.flag != 1 and mpf.type in (510001,510004) " 
			+ "and (mpcr.receive > mpf.quantity * 0.6)";
		List<Long> freezeProductIdList = jdbcTemplate.queryForList(sqlStr, 
				Long.class);
		String sql = "select mpf.id from mr_product_freeze mpf where mpf.endtime < curdate() and flag != 1";
		List<Long> overDueFreezedProducts = jdbcTemplate.queryForList(sql, Long.class);
		freezeProductIdList.addAll(overDueFreezedProducts);
		return freezeProductIdList;
	}
	
	@Override
	public void updateProductFreeze(Long productFreezeId){
		MrProductFreeze freezeProduct = mrProductFreezeDao.get(productFreezeId);
		try{
			ProductSale productSale = freezeProduct.getProductSale();
			Code dc = freezeProduct.getDc();
			freezeProduct.setFlag(MrProductFreeze.FLAG_UNFREEZED);
			mrProductFreezeDao.update(freezeProduct);
			LOG.info("productsale为" + freezeProduct.getProductSale().getId() + "的商品通过解冻测试");
			/**
			 * 被解冻的商品，其在mr_product_cumulative_receive表中的收获量应该归为0
			 */
			MrCumulativeReceive mrCumulativeReceive = mrCumulativeReceiveDao.findByProductSaleAndDc(productSale, dc);
			mrCumulativeReceive.setReceive(0);
			Date updatetime = new Date();
			mrCumulativeReceive.setUpdateTime(updatetime);
			mrCumulativeReceiveDao.update(mrCumulativeReceive);
			LOG.info("productsale为" + freezeProduct.getProductSale().getId() + "的商品通过更新收货数据测试");
		}catch(Exception e){
			LOG.info("productsale为" + freezeProduct.getProductSale().getId() + "的商品在解冻过程中出现异常");
		}
	}

	@Override
	public List<MrProductFreeze> findFreezedProducts() {
		List<MrProductFreeze> results = new ArrayList<MrProductFreeze>();
		List<MrProductFreeze> bjFreezes = findFreezedProductsBj(Code.DC_D818); // 北京分仓D818特殊处理
		results.addAll(bjFreezes);

		/**
		 * 系统自动补货以及人工上传补货申请的商品在审核后都应该下传到SAP
		 * 1)系统自动补货
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", Code.MR_FREEZE_SYSTEM);
		parameters.put("dc", Code.DC_8A17);
		List<MrProductFreeze> qtSystemFreezes = findFreezedProducts(parameters, null);
		results.addAll(qtSystemFreezes);
		
		/**
		 * 2)人工上传补货申请
		 */
		parameters.put("type", Code.MR_FREEZE_ARTIFICIAL);
		parameters.put("dc", Code.DC_8A17);
		List<MrProductFreeze> qtArtificialFreezes = findFreezedProducts(parameters, null);
		results.addAll(qtArtificialFreezes);
		
		/**
		 * 添加华东仓补货数据下传
		 */
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("type", Code.MR_FREEZE_SYSTEM);
		paras.put("dc", Code.DC_D819);
		List<MrProductFreeze> hdSystemFreezes = findFreezedProducts(paras, null);
		results.addAll(hdSystemFreezes);
		
		paras.put("type", Code.MR_FREEZE_ARTIFICIAL);
		paras.put("dc", Code.DC_D819);
		List<MrProductFreeze> hdArtificialFreezes = findFreezedProducts(paras, null);
		results.addAll(hdArtificialFreezes);
		return results;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<MrProductFreeze> findFreezedProducts(
			Map<String, Object> parameters, Pagination pagination) {
		return this.mrProductFreezeDao.findFreezedProducts(parameters,
				pagination);
	}

	// 北京仓库同一供应商补货总码洋大于2000才下传，北京分仓上线后确定CODE
	private List<MrProductFreeze> findFreezedProductsBj(Long dcBeijing) {
		List<MrProductFreeze> results = new ArrayList<MrProductFreeze>();
		String bjSql = "select p.vendor from mr_product_freeze mpf"
				+ " left join product_sale ps on mpf.productsale=ps.id"
				+ " left join product p on p.id = ps.product"
				+ " where mpf.dc = "
				+ dcBeijing
				+ " and mpf.type="
				+ Code.MR_FREEZE_SYSTEM
				+ " or mpf.type="
				+ Code.MR_FREEZE_ARTIFICIAL
				+ " and mpf.flag = 0"
				+ " group by p.vendor having sum(mpf.quantity*p.listprice) >= 2000";
		List<String> vendors = jdbcTemplate.queryForList(bjSql, String.class);

		for (String vendor : vendors) {
			/**
			 * 对同一个供应商，需要同时考虑到系统自动补货和人工申请补货两种情况
			 * 1)系统自动补货
			 */
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("type", Code.MR_FREEZE_SYSTEM);
			parameters.put("vendor", vendor);
			parameters.put("dc", dcBeijing);
			results.addAll(mrProductFreezeDao.findFreezedProducts(parameters,
					null));
			/**
			 * 2)人工上传补货申请
			 */
			parameters.put("type", Code.MR_FREEZE_ARTIFICIAL);
			parameters.put("vendor", vendor);
			parameters.put("dc", dcBeijing);
			results.addAll(mrProductFreezeDao.findFreezedProducts(parameters,
					null));
		}
		return results;
	}

	@Override
	public List<MrProductFreeze> fetchData(InputStream in) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		List<MrProductFreeze> list = new ArrayList<MrProductFreeze>();
		int rows = sheet.getPhysicalNumberOfRows();

		/**
		 * 向限制补货表添加一个ec编码时，在冻结表会生成一条默认dc为110003的记录
		 */

		for (int i = 1; i <= rows; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}

			HSSFCell cell = row.getCell(0);
			if (null != cell) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				Long productSaleId = Long.parseLong(cell.toString().trim());
				ProductSale productSale = this.productDao
						.getProductSale(productSaleId);
				if (null == productSale) {
					continue;
				}
				// 是否已经限制补货
				boolean isFreezeRestrict = this.mrProductFreezeDao
						.isExistByProductSaleAndType(productSale, new Code(
								Code.MR_FREEZE_RESTRICT));

				// 是否已经手动冻结补货
				boolean isFreezeManual = this.mrProductFreezeDao
						.isExistByProductSaleAndType(productSale, new Code(
								Code.MR_FREEZE_MANUAL));

				// 是否已经系统自动冻结
				boolean isFreezeSystem = this.mrProductFreezeDao
						.isExistByProductSaleAndType(productSale, new Code(
								Code.MR_FREEZE_SYSTEM));
				
				//是否已经人工上传补货申请审核后冻结
				boolean isFreezeArtificial = this.mrProductFreezeDao
						.isExistByProductSaleAndType(productSale, new Code(
								Code.MR_FREEZE_ARTIFICIAL));

				/**
				 * 限制补货表、人工冻结表和系统冻结表中的商品不能重复 
				 * 如果商品当前不在限制补货表，则考虑是否在人工冻结表、系统自动冻结表或者人工上传补货申请冻结表中
				 */
				if (!isFreezeRestrict) {

					/**
					 * 如果商品已经在人工冻结表、系统自动冻结表或者人工上传补货申请冻结表中，则更新数据
					 */
					if (isFreezeManual) {
						MrProductFreeze oldMannualFreeze = this.mrProductFreezeDao.
										findFreezeByProductSaleAndType(productSale, new Code(Code.MR_FREEZE_MANUAL));
						oldMannualFreeze.setId(oldMannualFreeze.getId());
						oldMannualFreeze.setProductSale(productSale);
						oldMannualFreeze.setStartTime(new Date());
						oldMannualFreeze.setEndTime(postponeTenYear());
						oldMannualFreeze.setFlag(0);
						oldMannualFreeze.setReason(RESTRICT_REASON);
						oldMannualFreeze.setType(new Code(Code.MR_FREEZE_RESTRICT));
						oldMannualFreeze.setDc(new Code(DEFAULT_DC));
						this.mrProductFreezeDao.update(oldMannualFreeze);
					} else if (isFreezeSystem) {
						MrProductFreeze oldSystemFreeze = this.mrProductFreezeDao
										.findFreezeByProductSaleAndType(productSale, new Code(Code.MR_FREEZE_SYSTEM));
						oldSystemFreeze.setId(oldSystemFreeze.getId());
						oldSystemFreeze.setProductSale(productSale);
						oldSystemFreeze.setStartTime(new Date());
						oldSystemFreeze.setEndTime(postponeTenYear());
						oldSystemFreeze.setFlag(0);
						oldSystemFreeze.setReason(RESTRICT_REASON);
						oldSystemFreeze.setType(new Code(Code.MR_FREEZE_RESTRICT));
						oldSystemFreeze.setDc(new Code(DEFAULT_DC));
						this.mrProductFreezeDao.update(oldSystemFreeze);
					}else if(isFreezeArtificial){
						MrProductFreeze oldArtificialFreeze = this.mrProductFreezeDao
										.findFreezeByProductSaleAndType(productSale, new Code(Code.MR_FREEZE_ARTIFICIAL));
						oldArtificialFreeze.setId(oldArtificialFreeze.getId());
						oldArtificialFreeze.setProductSale(productSale);
						oldArtificialFreeze.setStartTime(new Date());
						oldArtificialFreeze.setEndTime(postponeTenYear());
						oldArtificialFreeze.setFlag(0);
						oldArtificialFreeze.setReason(RESTRICT_REASON);
						oldArtificialFreeze.setType(new Code(Code.MR_FREEZE_RESTRICT));
						oldArtificialFreeze.setDc(new Code(DEFAULT_DC));
						this.mrProductFreezeDao.update(oldArtificialFreeze);
					} else {
							MrProductFreeze restrictFreeze = new MrProductFreeze();
							restrictFreeze.setProductSale(productSale);
							restrictFreeze.setCreateTime(new Date());
							restrictFreeze.setFlag(0);
							restrictFreeze.setStartTime(new Date());
							restrictFreeze.setEndTime(postponeTenYear());
							restrictFreeze.setReason(RESTRICT_REASON);
							restrictFreeze.setType(new Code(
									Code.MR_FREEZE_RESTRICT));
							restrictFreeze.setDc(new Code(DEFAULT_DC));
							this.mrProductFreezeDao.save(restrictFreeze);
							list.add(restrictFreeze);
					}
				} else {
					continue;
				}
			}
		}
		return list;
	}

	/**
	 * 向后顺延10年
	 * 
	 * @return Date
	 */
	private Date postponeTenYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, 10);
		return calendar.getTime();
	}

	/**
	 * 向后顺延45天
	 * 后根据需求，将冻结时间修改为30天
	 * 
	 * @return Date
	 */
	private Date postponeThirtyDays() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 30);
		return calendar.getTime();
	}

	@Override
	// 保存Excel表中的SAP编码和冻结结束时间，并将该人工冻结记录添加到测试数据库中的mr_product_freeze表中，其它的字段设为默认值。
	public List<MrProductFreeze> fetchArtificialFreezeData(InputStream in)
			throws IOException, ReplenishmentArtificialLimitException {

		// 读取拟上传的Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		List<MrProductFreeze> list = new ArrayList<MrProductFreeze>();
		int rows = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i <= rows; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			HSSFCell saleIdCell = row.getCell(0);
			HSSFCell endTimeCell = row.getCell(1);
			HSSFCell dcCell = row.getCell(2);

			if (null != saleIdCell) {
				saleIdCell.setCellType(Cell.CELL_TYPE_STRING);
				String productSaleId = saleIdCell.toString().trim();

				// 增加“库位”字段，要求上传的Excel文件有值不为空的dc字段
				if (dcCell == null) {
					throw new ReplenishmentArtificialLimitException("ID为"
							+ productSaleId + "的商品dc字段为空");
				}
				// 从Excel表中获取dc字段的值
				dcCell.setCellType(Cell.CELL_TYPE_STRING);
				Code dc = new Code(Long.parseLong(dcCell.toString().trim()));

				if (!StringUtils.isNullOrEmpty(productSaleId)) {
					Date endTime = endTimeCell.getDateCellValue();
					GregorianCalendar today = new GregorianCalendar();
					/**
					 * 没有冻结结束时间或者冻结结束时间小于当前时间的，将冻结结束时间顺延45天
					 * 后根据需求改为：将冻结结束时间顺延30天
					 */
					if (null == endTime || endTime.before(today.getTime())) {
						endTime = postponeThirtyDays();
					}
					ProductSale productSale = this.productDao
							.getProductSale(Long.parseLong(productSaleId));
					if (null == productSale) {
						continue;
					}
					// 是否已经限制补货
					boolean isFreezeRestrict = this.mrProductFreezeDao
							.isExistByProductSaleAndType(productSale, new Code(
									Code.MR_FREEZE_RESTRICT));

					// 是否已经手动冻结补货
					boolean isFreezeManual = this.mrProductFreezeDao
							.isExistByProductSaleAndType(productSale, new Code(
									Code.MR_FREEZE_MANUAL), dc);
					// 是否是系统自动冻结
					boolean isFreezeSystem = this.mrProductFreezeDao
							.isExistByProductSaleAndType(productSale, new Code(
									Code.MR_FREEZE_SYSTEM), dc);
					//是否已经人工上传补货申请审核后冻结
					boolean isFreezeArtificial = this.mrProductFreezeDao
							.isExistByProductSaleAndType(productSale, new Code(
									Code.MR_FREEZE_ARTIFICIAL),dc);
					
					if (!isFreezeRestrict) {
						// 如果已经是手动冻结了就修改
						if (isFreezeManual) {
							MrProductFreeze mrProductFreeze = this.mrProductFreezeDao
									.findFreezeByProductSaleAndType(
											productSale, new Code(
													Code.MR_FREEZE_MANUAL), dc);
							mrProductFreeze.setStartTime(new Date());
							mrProductFreeze.setEndTime(endTime);
							mrProductFreeze.setFlag(0);
							mrProductFreeze.setReason(MANUAL_REASON);
							mrProductFreeze.setType(new Code(
									Code.MR_FREEZE_MANUAL));

							// 将填入人工冻结表的商品的库位dc设置为从Excel表中读取的数据
							mrProductFreeze.setDc(dc);
							this.mrProductFreezeDao.update(mrProductFreeze);
						} else if (isFreezeSystem) {
							MrProductFreeze mrProductFreeze = this.mrProductFreezeDao
									.findFreezeByProductSaleAndType(
											productSale, new Code(
													Code.MR_FREEZE_SYSTEM), dc);
							mrProductFreeze.setStartTime(new Date());
							mrProductFreeze.setEndTime(endTime);
							mrProductFreeze.setFlag(0);
							mrProductFreeze.setReason(MANUAL_REASON);
							mrProductFreeze.setType(new Code(
									Code.MR_FREEZE_MANUAL));
							mrProductFreeze.setDc(dc);
							this.mrProductFreezeDao.update(mrProductFreeze);
						} else if(isFreezeArtificial){
							MrProductFreeze mrProductFreeze = this.mrProductFreezeDao
									.findFreezeByProductSaleAndType(
											productSale, new Code(
													Code.MR_FREEZE_ARTIFICIAL), dc);
							mrProductFreeze.setStartTime(new Date());
							mrProductFreeze.setEndTime(endTime);
							mrProductFreeze.setFlag(0);
							mrProductFreeze.setReason(MANUAL_REASON);
							mrProductFreeze.setType(new Code(
									Code.MR_FREEZE_MANUAL));
							mrProductFreeze.setDc(dc);
							this.mrProductFreezeDao.update(mrProductFreeze);
						}
						else {
							MrProductFreeze artificialFreeze = new MrProductFreeze();
							artificialFreeze.setProductSale(productSale);
							artificialFreeze.setCreateTime(new Date());
							artificialFreeze.setFlag(0);
							artificialFreeze.setStartTime(new Date());
							artificialFreeze.setEndTime(endTime);
							artificialFreeze.setReason(MANUAL_REASON);
							// 人工冻结
							artificialFreeze.setType(new Code(
									Code.MR_FREEZE_MANUAL));
							// dc值设置为从excel表里取到的数据
							artificialFreeze.setDc(dc);
							list.add(artificialFreeze);
						}
					} else {
						continue;
					}
				}
			} else {
				continue;
			}
		}
		return list;
	}

	@Override
	public void saveAll(List<MrProductFreeze> datas) {
		if (null != datas) {
			for (MrProductFreeze mrProductFreeze : datas) {
				LOG.info("mrprouctfreeze save start:"
						+ mrProductFreeze.getProductSale().getId());
				this.save(mrProductFreeze);
			}
		}
	}

	@Override
	public void saveOrUpdateFreeze(MrProductFreeze mrProductFreeze) {
		this.mrProductFreezeDao.saveOrUpdateFreeze(mrProductFreeze);
	}

	@Override
	public void update(MrProductFreeze mrProductFreeze) {
		this.mrProductFreezeDao.update(mrProductFreeze);
	}

	@Override
	public MrProductFreeze get(Long id) {
		return this.mrProductFreezeDao.get(id);
	}

	@Override
	public void delete(MrProductFreeze mrProductFreeze) {
		this.mrProductFreezeDao.delete(mrProductFreeze);
	}

	/**
	 * mrProductFreeze类必须传入type、reason
	 * 
	 * @throws ReplenishmentArtificialLimitException
	 */
	@Override
	public void saveOrUpdateArtificial(MrProductFreeze mrProductFreeze)
			throws ReplenishmentArtificialLimitException {
		ProductSale productSale = mrProductFreeze.getProductSale();
		Code dc = mrProductFreeze.getDc();
		GregorianCalendar today = new GregorianCalendar();

		/**
		* 如果是人工补货，先判断是否有冻结结束时间 如果没有，则以当前时间为准往后推45天作为冻结结束时间
	    * 如果有，但是小于系统当前时间，则顺延45天作为冻结结束时间
	    * 后根据需求，将45天改为30天
		*/
		if (mrProductFreeze.getEndTime() == null
				|| mrProductFreeze.getEndTime().before(today.getTime())) {
				mrProductFreeze.setEndTime(postponeThirtyDays());
		} else {
				mrProductFreeze.setEndTime(mrProductFreeze.getEndTime());
		}
		
		// 判断添加的记录是否有dc字段为空的情况
		if (mrProductFreeze.getDc() == null) {
				throw new ReplenishmentArtificialLimitException("ID为"
					+ mrProductFreeze.getId() + "的商品dc字段为空");
		} else {
				mrProductFreeze.setDc(mrProductFreeze.getDc());
		}

		// 是否已经存在限制补货
		boolean isFreezeRestrict = this.mrProductFreezeDao
				.isExistByProductSaleAndType(productSale, new Code(
						Code.MR_FREEZE_RESTRICT));

		// 是否已经存在人工冻结补货
		boolean isFreezeManual = this.mrProductFreezeDao
				.isExistByProductSaleAndType(productSale, new Code(
						Code.MR_FREEZE_MANUAL), dc);

		// 是否已经存在系统冻结补货
		boolean isFreezeSystem = this.mrProductFreezeDao
				.isExistByProductSaleAndType(productSale, new Code(
						Code.MR_FREEZE_SYSTEM), dc);
			
		//是否已经人工上传补货申请审核后冻结
		boolean isFreezeArtificial = this.mrProductFreezeDao
				.isExistByProductSaleAndType(productSale, new Code(
						Code.MR_FREEZE_ARTIFICIAL),dc);

		if (!isFreezeRestrict) {
			// 如果已经是手动冻结了就修改
			if (isFreezeManual) {
				MrProductFreeze oldManualFreeze = this.mrProductFreezeDao
						.findFreezeByProductSaleAndType(
								productSale, new Code(
										Code.MR_FREEZE_MANUAL), dc);
				oldManualFreeze.setStartTime(new Date());
				oldManualFreeze.setEndTime(mrProductFreeze.getEndTime());
				oldManualFreeze.setFlag(0);
				oldManualFreeze.setReason(MANUAL_REASON);
				oldManualFreeze.setType(new Code(
						Code.MR_FREEZE_MANUAL));

				// 将填入人工冻结表的商品的库位dc设置为从Excel表中读取的数据
				mrProductFreeze.setDc(dc);
				this.mrProductFreezeDao.update(oldManualFreeze);
			} else if (isFreezeSystem) {
				MrProductFreeze oldSystemFreeze = this.mrProductFreezeDao
						.findFreezeByProductSaleAndType(
								productSale, new Code(
										Code.MR_FREEZE_SYSTEM), dc);
				oldSystemFreeze.setStartTime(new Date());
				oldSystemFreeze.setEndTime(mrProductFreeze.getEndTime());
				oldSystemFreeze.setFlag(0);
				oldSystemFreeze.setReason(MANUAL_REASON);
				oldSystemFreeze.setType(new Code(
						Code.MR_FREEZE_MANUAL));
				mrProductFreeze.setDc(dc);
				this.mrProductFreezeDao.update(mrProductFreeze);
			} else if(isFreezeArtificial){
				MrProductFreeze oldArtificialFreeze = this.mrProductFreezeDao
						.findFreezeByProductSaleAndType(
								productSale, new Code(
										Code.MR_FREEZE_ARTIFICIAL), dc);
				oldArtificialFreeze.setStartTime(new Date());
				oldArtificialFreeze.setEndTime(mrProductFreeze.getEndTime());
				oldArtificialFreeze.setFlag(0);
				oldArtificialFreeze.setReason(MANUAL_REASON);
				oldArtificialFreeze.setType(new Code(
						Code.MR_FREEZE_MANUAL));
				mrProductFreeze.setDc(dc);
				this.mrProductFreezeDao.update(oldArtificialFreeze);
			}else {
				MrProductFreeze manualFreeze = new MrProductFreeze();
				manualFreeze.setProductSale(productSale);
				manualFreeze.setCreateTime(new Date());
				manualFreeze.setFlag(0);
				manualFreeze.setStartTime(new Date());
				manualFreeze.setEndTime(mrProductFreeze.getEndTime());
				manualFreeze.setReason(MANUAL_REASON);
					// 人工冻结
				manualFreeze.setType(new Code(
							Code.MR_FREEZE_MANUAL));
				manualFreeze.setDc(dc);
				this.mrProductFreezeDao.save(manualFreeze);
			}
		} else {
			   throw new ReplenishmentArtificialLimitException("ID为"
					+ mrProductFreeze.getId() + "的商品已在限制补货表中，" +
							"人工冻结补货不能覆盖限制补货冻结");
		}
	}

	@Override
	public void saveOrUpdateRestrict(MrProductFreeze mrProductFreeze)
			throws ReplenishmentRestrictLimitException {
		// TODO Auto-generated method stub

		ProductSale productSale = mrProductFreeze.getProductSale();
		// 是否已经限制补货
		boolean isFreezeRestrict = this.mrProductFreezeDao
				.isExistByProductSaleAndType(productSale, new Code(
						Code.MR_FREEZE_RESTRICT));
		// 是否已经人工冻结补货
		boolean isFreezeManual = this.mrProductFreezeDao
				.isExistByProductSaleAndType(productSale, new Code(
						Code.MR_FREEZE_MANUAL));
		// 是否是系统自动冻结
		boolean isFreezeSystem = this.mrProductFreezeDao
				.isExistByProductSaleAndType(productSale, new Code(
						Code.MR_FREEZE_SYSTEM));
		//是否已经人工上传补货申请审核后冻结
		boolean isFreezeArtificial = this.mrProductFreezeDao
				.isExistByProductSaleAndType(productSale, new Code(
						Code.MR_FREEZE_ARTIFICIAL));
		/**
		 * 向限制补货表添加一个ec编码时，在冻结表会生成一条默认dc为110003的记录
		 * 限制补货表、人工冻结表和系统冻结表中的商品不能重复
		 */
		if (!isFreezeRestrict) {
			/**
			 * 如果商品当前不在限制补货表，则考虑是否在人工冻结表或者系统自动冻结表中
			 */
			if (isFreezeManual) {
				MrProductFreeze oldMannualFreeze = this.mrProductFreezeDao
								.findFreezeByProductSaleAndType(productSale, new Code(Code.MR_FREEZE_MANUAL));
				oldMannualFreeze.setId(oldMannualFreeze.getId());
				oldMannualFreeze.setProductSale(productSale);
				oldMannualFreeze.setStartTime(new Date());
				oldMannualFreeze.setEndTime(postponeTenYear());
				oldMannualFreeze.setFlag(0);
				oldMannualFreeze.setReason(RESTRICT_REASON);
				oldMannualFreeze.setType(new Code(Code.MR_FREEZE_RESTRICT));
				oldMannualFreeze.setDc(new Code(DEFAULT_DC));
				this.mrProductFreezeDao.update(oldMannualFreeze);
			} else if (isFreezeSystem) {
				MrProductFreeze oldSystemFreeze = this.mrProductFreezeDao.
								findFreezeByProductSaleAndType(productSale, new Code(Code.MR_FREEZE_SYSTEM));
				oldSystemFreeze.setId(oldSystemFreeze.getId());
				oldSystemFreeze.setProductSale(productSale);
				oldSystemFreeze.setStartTime(new Date());
				oldSystemFreeze.setEndTime(postponeTenYear());
				oldSystemFreeze.setFlag(0);
				oldSystemFreeze.setReason(RESTRICT_REASON);
				oldSystemFreeze.setType(new Code(Code.MR_FREEZE_RESTRICT));
				oldSystemFreeze.setDc(new Code(DEFAULT_DC));
				this.mrProductFreezeDao.update(oldSystemFreeze);
			}else if (isFreezeArtificial){
				MrProductFreeze oldArtificialFreeze = this.mrProductFreezeDao.
				findFreezeByProductSaleAndType(productSale, new Code(Code.MR_FREEZE_ARTIFICIAL));
				oldArtificialFreeze.setId(oldArtificialFreeze.getId());
				oldArtificialFreeze.setProductSale(productSale);
				oldArtificialFreeze.setStartTime(new Date());
				oldArtificialFreeze.setEndTime(postponeTenYear());
				oldArtificialFreeze.setFlag(0);
				oldArtificialFreeze.setReason(RESTRICT_REASON);
				oldArtificialFreeze.setType(new Code(Code.MR_FREEZE_RESTRICT));
				oldArtificialFreeze.setDc(new Code(DEFAULT_DC));
								this.mrProductFreezeDao.update(oldArtificialFreeze);
			} else {
					MrProductFreeze restrictFreeze = new MrProductFreeze();
					restrictFreeze.setProductSale(productSale);
					restrictFreeze.setCreateTime(new Date());
					restrictFreeze.setFlag(0);
					restrictFreeze.setStartTime(new Date());
					restrictFreeze.setEndTime(postponeTenYear());
					restrictFreeze.setReason(RESTRICT_REASON);
					restrictFreeze.setType(new Code(Code.MR_FREEZE_RESTRICT));
					restrictFreeze.setDc(new Code(DEFAULT_DC));
					this.mrProductFreezeDao.save(restrictFreeze);
			}
		} else{
			throw new ReplenishmentRestrictLimitException("ID为"
					+ mrProductFreeze.getId() + "的商品已在限制补货表中！");
		}
	}

	@Override
	public boolean existSingleLimitFreezedProduct(ProductSale productSale) {
		// TODO Auto-generated method stub
		boolean limitFreezedOrNot = this.mrProductFreezeDao.
				isExistByProductSaleAndType(productSale, new Code(Code.MR_FREEZE_RESTRICT));
		return limitFreezedOrNot;
	}
	
	@Override
	public MrProductFreeze findSingleLimitFreezedProduct(ProductSale productSale){
		MrProductFreeze mrProductFreeze = this.mrProductFreezeDao.
				findFreezeByProductSaleAndType(productSale, new Code(Code.MR_FREEZE_RESTRICT));
		return mrProductFreeze;
	}
}
