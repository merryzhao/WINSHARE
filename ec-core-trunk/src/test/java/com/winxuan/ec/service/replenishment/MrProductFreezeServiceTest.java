/*
 * @(#)MrProductFreezeServiceTest.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.replenishment;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.ec.service.BaseTest;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-8-26
 */
public class MrProductFreezeServiceTest extends BaseTest{
	
//	@Test
	public void testSave() {
		MrProductFreeze newFreeze = new MrProductFreeze();
		newFreeze.setCreateTime(new Date());
		newFreeze.setDc(new Code(Code.DC_D803));
		Date startTime = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(startTime);
		cal.add(Calendar.DAY_OF_MONTH, 45);
		newFreeze.setStartTime(startTime);
		newFreeze.setEndTime(cal.getTime());
		newFreeze.setProductSale(new ProductSale(10399049L));
		newFreeze.setReason("系统智能补货");
		newFreeze.setType(new Code(Code.MR_FREEZE_SYSTEM));
		services.mrProductFreezeService.save(newFreeze);
	}
	
//	@Test
//	@Rollback(false)
	public void testUpdateFreezeFlag() {
		services.mrProductFreezeService.updateFreezeFlag();
	}
	
//	@Test
	public void testFindFreezedProductsOld() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", Code.MR_FREEZE_SYSTEM);
		parameters.put("dc", Code.DC_D818);
		parameters.put("vendor", "0002200059");
		List<MrProductFreeze> results = services.mrProductFreezeService.findFreezedProducts(parameters, null);
		assertEquals(1, results.size());
	}
	
	@Test
	public void testFindFreezedProductsNew() {
		List<MrProductFreeze> results = services.mrProductFreezeService.findFreezedProducts();
		assertEquals(1, results.size());
	}
	
}
