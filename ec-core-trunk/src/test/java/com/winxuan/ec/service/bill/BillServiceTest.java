package com.winxuan.ec.service.bill;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.bill.BillItemSap;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.service.BaseTest;

/**
 * mrDeliveryRecordServiceTest
 * 
 * @author yangxinyi
 * 
 */
public class BillServiceTest extends BaseTest {

	Bill bill;
	
	@Before
	public void setUp() {
		// TODO Auto-generated method stub
		bill = services.billService.get(10000019L);
	}

//	@Test
	public void testQueryByStatus() {
		Long[] statusCodes = {Code.BILL_CONFIRMED};
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("statusCodes", statusCodes);
		List<Bill> bills = services.billService.queryByStatus(maps, null, (short)0);
		logger.info("bill size is :" + bills.size());		
		assertEquals("10000019", bills.get(0).getId().toString());
	}
	
//	@Test
//	@Rollback(false)
	public void testUpdateBillStatusInvoice() {
		services.billService.updateBillStatusBySapInvoice(bill);
	}
	
//	@Test
	public void testFindSapBillItems() {
		List<BillItemSap> sapList = services.billService.findSapBillItems(bill);
		for(BillItemSap billItemSap : sapList) {
			logger.info("order saping : " + billItemSap.getOuterOrder());
		}
	}
	
	@Test
	@Rollback(false)
	public void updateBillSapStatus() {
		services.billService.updateBillItemSapStatus(bill);
	}
	
}
