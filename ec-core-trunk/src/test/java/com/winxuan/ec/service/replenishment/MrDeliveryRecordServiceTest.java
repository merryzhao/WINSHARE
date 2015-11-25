package com.winxuan.ec.service.replenishment;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrDeliveryRecord;
import com.winxuan.ec.model.replenishment.MrDeliveryRecordCurday;
import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.ec.model.replenishment.MrProductItem;
import com.winxuan.ec.service.BaseTest;
import com.winxuan.ec.support.forecast.Data;
import com.winxuan.ec.support.forecast.DefaultStockForecast;
import com.winxuan.ec.support.forecast.StockForecast;
import com.winxuan.framework.util.DateUtils;

/**
 * mrDeliveryRecordServiceTest
 * 
 * @author yangxinyi
 * 
 */
public class MrDeliveryRecordServiceTest extends BaseTest {
	String startDate = "";
	String endDate = "";
	DateFormat dateFormat = new SimpleDateFormat(
			DateUtils.SHORT_DATE_FORMAT_STR);
	StockForecast stockForecast = new DefaultStockForecast();

	@Before
	public void setUp() {
		// TODO Auto-generated method stub
		Date date = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1500);
		startDate = dateFormat.format(cal.getTime());
		endDate = dateFormat.format(date);
	}

	// @Test
	public void testModelOld() {
		List<Integer> params = services.mrDeliveryRecordService
				.getProductQuantityOld(11581220L, 110002L, 8);
		logger.info(params);
		int saleStock = stockForecast.getByAverage(params, 45);
		assertEquals(0, saleStock);
	}

//	@Test
	public void testModelNew() {
//		Long[] ids = { 10025796L, 10037018L, 10265993L, 10316596L, 10316597L,
//				10328766L, 10375741L, 10395605L, 10396402L, 10008441L,
//				10011383L, 10025478L, 10246815L, 10264582L, 10270481L,
//				10297153L, 10356794L, 10374093L, 10015262L, 10236431L,
//				10246419L, 10291752L, 10295601L, 10361715L, 10394888L,
//				10291662L, 10398382L, 10095996L, 10242208L, 10325829L,
//				10341336L, 10365733L, 10380408L, 10389427L };
		Long[] ids ={10009509L};
		for (Long id : ids) {
			
			List<Data> params = services.mrDeliveryRecordService
					.getProductQuantityNew(id, 110002L, 8);
			int safeStock = stockForecast.getByExponentialSmoothingNew(params,
					45 / 7, "N");
			logger.info(params);
			assertEquals(6, safeStock);
		}
	}

	// @Test
	public void testFindCurdayDeliveryRecords() {
		List<MrDeliveryRecordCurday> records = services.mrDeliveryRecordService
				.findCurdateDeliveryRecords();
		assertEquals(1, records.size());
	}

//	 @Test
	public void testFind() throws ParseException {
		Long productSaleId = 1200229781L;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productSale", productSaleId);
		params.put("channel", 104L);
		params.put("deliveryDate", dateFormat.parse("2013-09-08"));
		List<MrDeliveryRecord> records = services.mrDeliveryRecordService
				.find(params);
		assertEquals(productSaleId, records.get(0).getProductSale().getId());
	}

	 @Test
	 @Rollback(false)
	public void testDeliveryRecordJob() {
		/*
		 * List<Map<String, Object>> mapList =
		 * services.mrDeliveryRecordService.findCurdateDeliveryRecords();
		 * for(Map<String, Object> mapObj : mapList) { Object mdrObj =
		 * mapObj.get("productsale"); if (mdrObj instanceof MrDeliveryRecord) {
		 * MrDeliveryRecord mrDeliveryRecord = (MrDeliveryRecord) mdrObj;
		 * logger.info("mdr id " + mrDeliveryRecord.getId()); } }
		 */
		List<MrDeliveryRecordCurday> records = new ArrayList<MrDeliveryRecordCurday>();
		records = services.mrDeliveryRecordService.findCurdateDeliveryRecords();

//		addTestRecords(records);

		StockForecast forecast = new DefaultStockForecast();
		for (MrDeliveryRecordCurday record : records) {
			
			 /**
			  * 检查程序效率
			  */
			long beginTime = System.currentTimeMillis();
			
			/**
			 * records中包含了处于限制补货表，且dc为D818的商品
			 * 不允许这部分商品进入补货模型的计算
			 */
			Long productSaleId = record.getProductSale();			
			MrProductFreeze mrProductFreeze = services.mrProductFreezeService
							.findSingleLimitFreezedProduct(new ProductSale(productSaleId));
			if(mrProductFreeze!=null && record.getDc().longValue() == 110004){
				continue;
			}
			long endTime = System.currentTimeMillis();
			
			int forecastSale = 0;
			int safeStock = 0;
			Long modelCode = record.getMcModel();
			if (Code.MR_MC_SPECIFIC_2.equals(record.getMcType())) {
				modelCode = Code.MR_FIX;
			} else if (Code.MR_MC_SPECIFIC_1.equals(record.getMcType())) {
				modelCode = Code.MR_MODE_OLD;
			}
			if (Code.MR_FIX.equals(modelCode)) {
				safeStock = forecast.getSafeStockAmount(
						record.getFixQuantity(), record.getFixBoundTop(),
						record.getFixBoundBottom(), record.getFixRatio(),
						record.getFixDefaultQuantity(), record.getListPrice());
				if (safeStock > record.getAvailableQuantity()) {
					forecastSale = safeStock;
				}
			} else if (Code.MR_MODE_OLD.equals(modelCode)) {
				List<Integer> params = services.mrDeliveryRecordService
						.getProductQuantityOld(record.getProductSale(),
								record.getDc(), record.getSaleCycle());

				safeStock = forecast.getByAverage(params,
						(int) Math.ceil(record.getTransitCycle() / 7));
				if (safeStock > record.getAvailableQuantity()) {
					forecastSale = forecast.getByAverage(params,
							record.getReplenishmentCycle());
					modelCode = Code.MR_MODE_OLD;
				}
			} else {
				/**
				 * 没有销售分级的商品，默认其销售分级为N
				 */
				if(record.getGrade() == null){
					record.setGrade(ProductSale.PRODUCT_SALE_GRADE_N);
				}
				String grade =record.getGrade();
				
				List<Data> params = services.mrDeliveryRecordService
						.getProductQuantityNew(record.getProductSale(),
								record.getDc(), record.getSaleCycle());
				safeStock = forecast.getByExponentialSmoothingNew(params,
						(int) Math.ceil(record.getTransitCycle() / 7),grade);
				if (safeStock > record.getAvailableQuantity()) {
					forecastSale = forecast.getByExponentialSmoothingNew(params,
							record.getReplenishmentCycle(),grade);
					modelCode = Code.MR_MODE_NEW;
				}
			}
			if (forecastSale > record.getAvailableQuantity()) {
				MrProductItem mrProductItem = new MrProductItem();
				mrProductItem.setId(record.getMrProductItem());
				mrProductItem.setProductSale(new ProductSale(record
						.getProductSale()));
				mrProductItem.setCreateTime(new Date());
				mrProductItem.setGrade(record.getGrade());
				mrProductItem.setAvailableQuantity(record
						.getAvailableQuantity());
				mrProductItem.setSafeQuantity(safeStock);
				mrProductItem.setForecastQuantity(forecastSale);
				mrProductItem.setModel(new Code(modelCode));
				mrProductItem.setDc(new Code(record.getDc()));
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				String sDate = dateFormat.format(new Date());
				mrProductItem.setNum(sDate);
				mrProductItem.setType(new Code(Code.MR_REPLENISH_TYPE_SYSTEM));
				mrProductItem.setReplenishmentCycle(record
						.getReplenishmentCycle());
				mrProductItem.setReplenishmentQuantity(forecastSale
						- record.getAvailableQuantity());
				services.mrProductItemService.saveOrUpdate(mrProductItem);
			}
		}		
	}

	private void addTestRecords(List<MrDeliveryRecordCurday> records) {
		records.clear();
		MrDeliveryRecordCurday mrDeliveryRecordCurday1 = new MrDeliveryRecordCurday();
//		mrDeliveryRecordCurday1.setMrProductItem(5673L);
		mrDeliveryRecordCurday1.setProductSale(10002620L);
		mrDeliveryRecordCurday1.setListPrice(new BigDecimal(8.00));
		mrDeliveryRecordCurday1.setAvailableQuantity(3);
		mrDeliveryRecordCurday1.setGrade("A+");
		mrDeliveryRecordCurday1.setMcModel(Code.MR_MODE_NEW);
		mrDeliveryRecordCurday1.setMcCategory("MC150206");
		mrDeliveryRecordCurday1.setReplenishmentCycle(14);
		mrDeliveryRecordCurday1.setSaleCycle(8);
		mrDeliveryRecordCurday1.setTransitCycle(45);
//		mrDeliveryRecordCurday1.setMcType(Code.MR_MC_NON_SPECIFIC);
		mrDeliveryRecordCurday1.setDc(110003L);
		records.add(mrDeliveryRecordCurday1);

		MrDeliveryRecordCurday mrDeliveryRecordCurday2 = new MrDeliveryRecordCurday();
		mrDeliveryRecordCurday2.setProductSale(10780441L);
		mrDeliveryRecordCurday2.setGrade("N");
		mrDeliveryRecordCurday2.setMcModel(Code.MR_MODE_NEW);
		mrDeliveryRecordCurday2.setAvailableQuantity(0);
		mrDeliveryRecordCurday2.setMcType(490001L);
		mrDeliveryRecordCurday2.setReplenishmentCycle(14);
		mrDeliveryRecordCurday2.setSaleCycle(8);
		mrDeliveryRecordCurday2.setTransitCycle(45);
		mrDeliveryRecordCurday2.setMcCategory("MC130101");
		mrDeliveryRecordCurday2.setDc(110003L);
		records.add(mrDeliveryRecordCurday2);

		// //测试数据---非特定MC类中的N类商品（新模型）

		 MrDeliveryRecordCurday mrDeliveryRecordCurday3=new MrDeliveryRecordCurday();
		 mrDeliveryRecordCurday3.setProductSale(10000065L);
		 mrDeliveryRecordCurday3.setAvailableQuantity(4);
//		 mrDeliveryRecordCurday3.setDeliveryQuantity(20);
		 mrDeliveryRecordCurday3.setMcType(Code.MR_MC_NON_SPECIFIC);
		 mrDeliveryRecordCurday3.setReplenishmentCycle(18);
		 mrDeliveryRecordCurday3.setSaleCycle(8);
		 mrDeliveryRecordCurday3.setMcModel(Code.MR_MODE_NEW);
		 mrDeliveryRecordCurday3.setTransitCycle(45);
		 records.add(mrDeliveryRecordCurday3);
	}

	// @Test
	public void testGet() {
		MrDeliveryRecord record = services.mrDeliveryRecordService.get(2L);
		assertEquals(1, record.getDeliveryQuantity());
	}

	// @Test
	public void testFindNewDeliveryRecord() {
		logger.info(startDate + "--->" + endDate);
		int result = services.mrDeliveryRecordService.findNewDeliveryRecord(
				startDate, endDate);
		assertEquals(11, result);
	}

	// @Test
	// @Rollback(false)
	public void testSaveMrDeliveryRecord() {
		startDate = "2013-07-26";
		endDate = "2013-09-11";
		logger.info(startDate + "--->" + endDate);
		services.mrDeliveryRecordService.saveMrDeliveryRecord(startDate,
				endDate);
	}

	/**
	 * 测试定时任务
	 */
	// @Test
	// @Rollback(false)
	public void testJob() {
		String startDate = "";
		String endDate = "";
		DateFormat dateFormat = new SimpleDateFormat(
				DateUtils.SHORT_DATE_FORMAT_STR);
		Date date = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		// cal.add(Calendar.DAY_OF_MONTH, -1060); // 测试数据在2010-09-19
		for (int i = 0; i < MrDeliveryRecordService.INTERVAL_DAY; i++) {
			endDate = dateFormat.format(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, -1);
			startDate = dateFormat.format(cal.getTime());
			int updateNum = services.mrDeliveryRecordService
					.findNewDeliveryRecord(startDate, endDate);
			if (updateNum > 0) {
				logger.info("update data in [" + startDate + "->" + endDate
						+ "]:" + updateNum);
				services.mrDeliveryRecordService.saveMrDeliveryRecord(
						startDate, endDate);
			}
		}
	}

	/*
	 * @Test public void testInsertSql() { int record =
	 * services.mrDeliveryRecordService.insertMrDeliveryRecord();
	 * assertEquals("1", String.valueOf(record)); }
	 * 
	 * @Test public void testUpdateSql() { int record =
	 * services.mrDeliveryRecordService.updateMrDeliveryRecord();
	 * assertEquals("1", String.valueOf(record)); }
	 */
}
