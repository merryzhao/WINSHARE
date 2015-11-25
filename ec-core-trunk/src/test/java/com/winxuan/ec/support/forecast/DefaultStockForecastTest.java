/*
 * @(#)DefaultStockForecastTest.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.forecast;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2013年8月21日
 */
public class DefaultStockForecastTest {

	public StockForecast stockForecast = new DefaultStockForecast();

	// 销售周期足够，不需要补零
	public List<Data> doubleList = new ArrayList<Data>();
	public List<Data> simpleList = new ArrayList<Data>();
	public List<Integer> averageList = new ArrayList<Integer>();

	// 销售周期不足，需要补零
	public List<Data> secondSimpleList = new ArrayList<Data>();
	
	//四种特殊情况
	public List<Data> specialList1 = new ArrayList<Data>();
	public List<Data> specialList2 = new ArrayList<Data>();
	public List<Data> specialList3 = new ArrayList<Data>();
	public List<Data> specialList4 = new ArrayList<Data>();
	

	@Before
	public void setUpSecondSimpleList() {

		// 商品从第4个周期开始销售，前3个周期的发货数量为0，也就是销售周期不足，需要从商品开始销售的起始点向前补充周期至数目为8
		secondSimpleList.add(new Data(0, 0));
		secondSimpleList.add(new Data(1, 0));
		secondSimpleList.add(new Data(2, 0));
		secondSimpleList.add(new Data(3, 11));
		secondSimpleList.add(new Data(4, 71));
		secondSimpleList.add(new Data(5, 40));
		secondSimpleList.add(new Data(6, 23));
		secondSimpleList.add(new Data(7, 24));

	}

	@Before
	public void setUpDoubleList() {

		doubleList.add(new Data(0, 14));
		doubleList.add(new Data(1, 15));
		doubleList.add(new Data(2, 6));
		doubleList.add(new Data(3, 21));
		doubleList.add(new Data(4, 125));
		doubleList.add(new Data(5, 19));
		doubleList.add(new Data(6, 36));
		doubleList.add(new Data(7, 11));
		doubleList.add(new Data(8, 217));
		doubleList.add(new Data(9, 11));
		doubleList.add(new Data(10, 194));
		doubleList.add(new Data(11, 2));
		doubleList.add(new Data(12, 30));
		doubleList.add(new Data(13, 81));
		doubleList.add(new Data(14, 42));
		doubleList.add(new Data(15, 235));
		doubleList.add(new Data(16, 33));
	}

	@Before
	public void setUpSimpleList() {

		simpleList.add(new Data(0, 78));
		simpleList.add(new Data(1, 49));
		simpleList.add(new Data(2, 32));
		simpleList.add(new Data(3, 59));
		simpleList.add(new Data(4, 58));
		simpleList.add(new Data(5, 50));
		simpleList.add(new Data(6, 35));
		simpleList.add(new Data(7, 48));
		simpleList.add(new Data(8, 44));
		simpleList.add(new Data(9, 65));
		simpleList.add(new Data(10, 215));
		simpleList.add(new Data(11, 72));
		simpleList.add(new Data(12, 91));
		simpleList.add(new Data(13, 14));
		simpleList.add(new Data(14, 37));
		simpleList.add(new Data(15, 16));
		simpleList.add(new Data(16, 11));
	}

	@Before
	public void setUpAverageList() {
		averageList.add(0);
		averageList.add(0);
		averageList.add(40);
		averageList.add(0);
		averageList.add(5);
	}

	@Before
	public void setUpSpecialList1() {
		specialList1.add(new Data(0, 0));
		specialList1.add(new Data(1, 0));
		specialList1.add(new Data(2, 0));
		specialList1.add(new Data(3, 10));
	}
	
	@Before
	public void setUpSpecialList2() {
		specialList2.add(new Data(0, 0));
		specialList2.add(new Data(1, 0));
		specialList2.add(new Data(2, 8));
		specialList2.add(new Data(3, 8));
		specialList2.add(new Data(4, 8));
		specialList2.add(new Data(5, 8));
		specialList2.add(new Data(6, 8));
		specialList2.add(new Data(7, 8));
	}
	
	@Before
	public void setUpSpecialList3() {
		specialList3.add(new Data(0, 0));
		specialList3.add(new Data(1, 0));
		specialList3.add(new Data(2, 0));
		specialList3.add(new Data(3, 0));
		specialList3.add(new Data(4, 0));
		specialList3.add(new Data(5, 0));
		specialList3.add(new Data(6, 5));
		specialList3.add(new Data(7, 3));
	}
	
	@Before
	public void setUpSpecialList4() {
		specialList4.add(new Data(0, 0));
		specialList4.add(new Data(1, 0));
		specialList4.add(new Data(2, 0));
		specialList4.add(new Data(3, 0));
		specialList4.add(new Data(4, 0));
		specialList4.add(new Data(5, 1));
		specialList4.add(new Data(6, 0));
		specialList4.add(new Data(7, 5));
	}
	

	@Test
	public void testDouble() {
		double double1 = stockForecast.getByExponentialSmoothing(doubleList, 1);
		double double2 = stockForecast.getByExponentialSmoothing(doubleList, 3);
		assertThat(double1, closeTo(93.0, 1.0));
		assertThat(double2, closeTo(272.0, 1.0));
	}

	@Test
	public void testSimple() {
		double simple1 = stockForecast.getByExponentialSmoothing(simpleList, 1);
		double simple2 = stockForecast.getByExponentialSmoothing(simpleList, 3);
		assertThat(simple1, closeTo(57.0, 1.0));
		assertThat(simple2, closeTo(171.0, 1.0));
	}

	@Test
	public void testSecondSimple() {
		double secondsimple1 = stockForecast.getByExponentialSmoothing(
				secondSimpleList, 1);
		double secondsimple2 = stockForecast.getByExponentialSmoothing(
				secondSimpleList, 3);
		assertThat(secondsimple1, closeTo(27.0, 1.0));
		assertThat(secondsimple2, closeTo(83.0, 1.0));
	}

	@Test
	public void averageTest() {
		double average1 = stockForecast.getByAverage(averageList, 1);
		double average2 = stockForecast.getByAverage(averageList, 6);
		assertThat(average1, closeTo(15.0, 1.0));
		assertThat(average2, closeTo(90.0, 1.0));
	}

	@Test
	public void testSpecialList1() {
		double special1 = stockForecast.getByExponentialSmoothing(specialList1,
				1);
		double special2 = stockForecast.getByExponentialSmoothing(specialList1,
				6);
		assertThat(special1, closeTo(10, 1.0));
		assertThat(special2, closeTo(60, 1.0));
	}
	
	@Test
	public void testSpecialList2() {
		double special1 = stockForecast.getByExponentialSmoothing(specialList2,
				1);
		double special2 = stockForecast.getByExponentialSmoothing(specialList2,
				6);
		assertThat(special1, closeTo(8, 1.0));
		assertThat(special2, closeTo(48, 1.0));
	}
	
	@Test
	public void testSpecialList3() {
		double special1 = stockForecast.getByExponentialSmoothing(specialList3,
				1);
		double special2 = stockForecast.getByExponentialSmoothing(specialList3,
				6);
		assertThat(special1, closeTo(4, 1.0));
		assertThat(special2, closeTo(24, 1.0));
	}
	
	@Test
	public void testSpecialList4() {
		double special1 = stockForecast.getByExponentialSmoothing(specialList4,
				1);
		double special2 = stockForecast.getByExponentialSmoothing(specialList4,
				6);
		assertThat(special1, closeTo(1, 1.0));
		assertThat(special2, closeTo(6, 1.0));
	}

	@Test
	public void getSafeStockAmountTest() {

		double result1 = stockForecast.getSafeStockAmount(10, new BigDecimal(
				"100"), new BigDecimal("500"), new BigDecimal("0.5"), 2,
				new BigDecimal("50"));
		double result2 = stockForecast.getSafeStockAmount(10, new BigDecimal(
				"100"), new BigDecimal("500"), new BigDecimal("0.5"), 2,
				new BigDecimal("200"));
		double result3 = stockForecast.getSafeStockAmount(10, new BigDecimal(
				"100"), new BigDecimal("500"), new BigDecimal("0.5"), 2,
				new BigDecimal("600"));

		double result4 = stockForecast.getSafeStockAmount(5, new BigDecimal(
				"100"), new BigDecimal("0"), new BigDecimal("0"), 2,
				new BigDecimal("50"));
		double result5 = stockForecast.getSafeStockAmount(5, new BigDecimal(
				"100"), new BigDecimal("0"), new BigDecimal("0"), 2,
				new BigDecimal("250"));

		assertThat(result1, closeTo(10.0, 0.0));
		assertThat(result2, closeTo(5.0, 0.0));
		assertThat(result3, closeTo(2.0, 0.0));
		assertThat(result4, closeTo(5.0, 0.0));
		assertThat(result5, closeTo(2.0, 0.0));
	}
	
	@Test
	public void dataProprocessingTest(){
		List<Data> list = stockForecast.dataProprocessing(secondSimpleList, 2);
	}
	
	@Test
	public void getBySimpleExponentialSmoothingTest(){
		double simpeSmoothing =stockForecast.getBySimpleExponentialSmoothing(simpleList, 1);
		assertThat(simpeSmoothing, closeTo(57.0, 1.0));
	}
	
	@Test
	public void getByExponentialSmoothingNewTest(){
		double prediction1 = stockForecast.getByExponentialSmoothingNew(doubleList, 1, "A");
		double prediction2 = stockForecast.getByExponentialSmoothingNew(doubleList, 1, "N");
		assertThat(prediction1, closeTo(82,1.0));
		assertThat(prediction2, closeTo(93,1.0));
	}
}
