package com.winxuan.ec.service.settle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.settle.SapOrderItem;
import com.winxuan.ec.service.BaseTest;

/**
 * @description: TODO
 *
 * @createtime: 2014-2-13 下午3:09:31
 *
 * @author zenghua
 *
 * @version 1.0
 */
public class InnerSettleServiceImplTest extends BaseTest {
	
	private static DateFormat format;
	
	private static final Date START_DATE = DateUtils.addMonths(new Date(), -3);
	private static final Date END_DATE = DateUtils.addMonths(new Date(), -2);
	
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	
	@Autowired
	private InnerSettleService innerSettleService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		format = new SimpleDateFormat("yyyyMMdd");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		
	}

	/**
	 * Test method for {@link com.winxuan.ec.service.settle.InnerSettleServiceImpl#getDeliveryOrderItem(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetDeliveryOrderItem() throws Exception {
		List<SapOrderItem> deliveryOrderItems = innerSettleService.getDeliveryOrderItem(
				DateFormatUtils.format(START_DATE, DATE_PATTERN), DateFormatUtils.format(END_DATE, DATE_PATTERN));
		assertNotNull(deliveryOrderItems);
//		assertEquals(1752, deliveryOrderItems.size());
		
		
		for (SapOrderItem s : deliveryOrderItems) {
			String outerOrder = s.getOuterOrder();
//			String sub = outerOrder.substring(0, 8);
//			Date orderDate = format.parse(sub);
//			assertTrue(START_DATE.compareTo(orderDate)<0);
//			assertTrue(END_DATE.compareTo(orderDate)>0);
			assertNotNull(outerOrder);
			assertNotNull(s.getOuterItem());
			assertNotNull(s.getSapid());
		}
	}

	/**
	 * Test method for {@link com.winxuan.ec.service.settle.InnerSettleServiceImpl#getReturnOrderItem(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetReturnOrderItem() throws Exception {
		List<SapOrderItem> list = innerSettleService.getReturnOrderItem(
				DateFormatUtils.format(START_DATE, DATE_PATTERN), DateFormatUtils.format(END_DATE, DATE_PATTERN));
		assertNotNull(list);
		
		for (SapOrderItem s : list) {
			String outerOrder = s.getOuterOrder();
//			String sub = outerOrder.substring(0, 8);
//			Date orderDate = format.parse(sub);
//			
//			assertTrue(START_DATE.compareTo(orderDate)<0);
//			assertTrue(END_DATE.compareTo(orderDate)>0);
			assertNotNull(outerOrder);
			assertNotNull(s.getOuterItem());
			assertNotNull(s.getSapid());
		}
	}

	/**
	 * Test method for {@link com.winxuan.ec.service.settle.InnerSettleServiceImpl#getRejectOrderItem(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetRejectOrderItem() throws Exception {
		List<SapOrderItem> list = innerSettleService.getRejectOrderItem(
				DateFormatUtils.format(START_DATE, DATE_PATTERN), DateFormatUtils.format(END_DATE, DATE_PATTERN));
		assertNotNull(list);
		
		for (SapOrderItem s : list) {
			String outerOrder = s.getOuterOrder();
//			String sub = outerOrder.substring(1, 8);
//			String subDate = "2" + sub;
//			Date orderDate = format.parse(subDate);
//			assertTrue(START_DATE.compareTo(orderDate)<0);
//			assertTrue(END_DATE.compareTo(orderDate)>0);
			assertNotNull(outerOrder);
			assertNotNull(s.getOuterItem());
			assertNotNull(s.getSapid());
		}
	}

	/**
	 * Test method for {@link com.winxuan.ec.service.settle.InnerSettleServiceImpl#getAllOrderItem()}.
	 */
	@Test
	public void testGetAllOrderItem() {
		assertNotNull(innerSettleService.getAllOrderItem());
	}

	/**
	 * Test method for {@link com.winxuan.ec.service.settle.InnerSettleServiceImpl#sendSapOrderItem()}.
	 */
//	@Test
	public void testSendSapOrderItem() {
//		innerSettleService.sendSapOrderItem();
	}

	/**
	 * Test method for {@link com.winxuan.ec.service.settle.InnerSettleServiceImpl#createBill(com.winxuan.ec.model.settle.SapOrderItem)}.
	 */
	@Test
	public void testCreateBill() {
		SapOrderItem s = new SapOrderItem();
		s.setOuterOrder("aa");
		Bill bill = innerSettleService.createBill(s);
		assertNotNull(bill);
		assertTrue(bill.getId()>0);
		assertEquals("aa", bill.getList());
		assertEquals("aa", bill.getInvoice());
	}

}
