package com.winxuan.ec.dao;

import org.junit.Test;

import com.winxuan.ec.service.BaseTest;
import com.winxuan.framework.dynamicdao.InjectDao;
/**
 * 
 * @author chenlei
 * 2014-12-17
 * 数据来源 10.100.15.172
 */
public class OrderDaoTest extends BaseTest{
	
	@InjectDao
	OrderDao orderDao;
	@Test
	public void testGetPurchaseQuantityToday(){
		long l = orderDao.getPurchaseQuantityToday(1687630281L,10427407L);
//		assertEquals(1, l);
	}
	

}
