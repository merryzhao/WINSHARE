/*
 * @(#)ProductSaleDisableRecordServiceTest.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.product;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSaleDisableRecord;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.BaseTest;

/**
 * description
 * 
 * @author yangxinyi
 * @version 1.0,2013-12-17
 */
public class ProductSaleDisableRecordServiceTest extends BaseTest {

	private static final Log LOG = LogFactory
			.getLog(ProductSaleDisableRecordServiceTest.class);

	ProductSaleDisableRecord record = new ProductSaleDisableRecord();
	Long recordId = 12L;
	Employee operator;

	@Before
	public void setUp() {
		// TODO Auto-generated method stub
		record.setId(recordId);
		operator = new Employee(1682143366L);
	}
	
//	@Test
	public void testGet() {
		ProductSaleDisableRecord record = services.productSaleDisableRecordService.get(recordId);
		assertEquals(recordId, record.getId());
		assertEquals(10, record.getItemList().size());
	}

//	@Test
	public void testFindByUserAndStatus() {
		assertEquals(
				0,
				services.productSaleDisableRecordService.findByUserAndStatus(
						operator, new Code(Code.PRODUCT_STOP_STATUS_UPLOAD))
						.size());
	}

//	@Test
	public void testFindItems() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productSaleDisableRecordId", 9L);
//		params.put("status", Code.PRODUCT_STOP_STATUS_UPLOAD);
		params.put("synced", true);
		assertEquals(0,
				services.productSaleDisableRecordService
						.findItems(params, null).size());
	}

//	@Test
//	@Rollback(false)
	public void testAsyncStop() {
		List<ProductSaleDisableRecord> uploadRecords = services.productSaleDisableRecordService
				.findByUserAndStatus(operator, new Code(
						Code.PRODUCT_STOP_STATUS_UPLOAD));
		for (ProductSaleDisableRecord uploadRecord : uploadRecords) {
			services.productSaleDisableRecordService.asyncProductSaleStop(
					uploadRecord, operator);
		}
		assertEquals(
				uploadRecords.size(),
				services.productSaleDisableRecordService.findByUserAndStatus(
						operator, new Code(Code.PRODUCT_STOP_STATUS_UPLOAD))
						.size());
	}
	
//	@Test
//	@Rollback(false)
	public void testAsyncRecover() {
		List<ProductSaleDisableRecord> uploadRecords = services.productSaleDisableRecordService
				.findByUserAndStatus(operator, new Code(
						Code.PRODUCT_STOP_STATUS_STOP));
		for (ProductSaleDisableRecord uploadRecord : uploadRecords) {
			LOG.info("开始恢复：" + uploadRecord.getId());
			services.productSaleDisableRecordService.asyncProductSaleRecover(
					uploadRecord, operator);
		}
		assertEquals(
				uploadRecords.size(),
				services.productSaleDisableRecordService.findByUserAndStatus(
						operator, new Code(Code.PRODUCT_STOP_STATUS_ALL_RELEASE))
						.size());
	}

//	@Test
//	@Rollback(false)
	public void testRecoverItemsByRecord() {
		assertEquals(0, services.productSaleDisableRecordService.recoverItemsByRecord(record));
	}
	
	@Test
	public void testExistStopedItem() {
		record.setId(15L);
		assertEquals(true, services.productSaleDisableRecordService.existStopedItem(record));
	}
	
}
