/*
 * @(#)MrProductItemTest.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.replenishment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrProductItem;
import com.winxuan.ec.service.BaseTest;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-8-22
 */
public class MrProductItemTest extends BaseTest{
	
	@Test
	public void testSave() {
		MrProductItem mrProductItem = new MrProductItem();
		mrProductItem.setProductSale(new ProductSale(11320383L));
		mrProductItem.setAuditTime(new Date());
		mrProductItem.setCreateTime(new Date());
		mrProductItem.setAvailableQuantity(10);
		mrProductItem.setCheck(true);
		mrProductItem.setForecastQuantity(20);
		mrProductItem.setModel(new Code(Code.MR_MODE_NEW));
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String sDate = dateFormat.format(new Date());
		mrProductItem.setNum(sDate);
		mrProductItem.setReplenishmentCycle(45);
		mrProductItem.setSafeQuantity(15);
		services.mrProductItemService.save(mrProductItem);
	}
	
}
