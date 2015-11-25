/*
 * @(#)StockModel.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.forecast;

import java.math.BigDecimal;
import java.util.List;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2013年8月19日
 */
public interface StockForecast {

	/**
	 * 对输入的历史发货数据进行预处理
	 * 如果从当前周期向前推8个周期，存在历史销售数据为0的周期，从第一个历史销售数据不为0的周期开始取数，进行预测
	 */
	List<Data> dataProprocessing(List<Data> list, int calcCycle);
	
	
	/**
	 * 算术平行算法
	 * @param list
	 * @param calcCycle
	 * @return
	 */
	int getByAverage(List<Integer> list, int calcCycle);
	
	
	/**
	 * 指数平滑算法
	 * @param list
	 * @param calcCycle
	 * @return
	 */
	int getByExponentialSmoothing(List<Data> list, int calcCycle);
	
	
	/**
	 * 定位表算法
	 * @param list
	 * @param calcCycle
	 * @return
	 */
	int getSafeStockAmount(int quantity, BigDecimal boundTop, BigDecimal boundBottom,
			BigDecimal ratio, int defaultQuantity, BigDecimal listprice);
	
	/**
	 * 对采用指数平滑方法进行计算的几种经常出现的特殊数据情况进行特殊处理
	 */
	int getBySpecialSituation(List<Data> list, int calcCycle);
	
	/**
	 * 对采用指数平滑方法进行计算的几种经常出现的特殊数据情况进行特殊处理
	 */
	int getBySimpleExponentialSmoothing(List<Data> list, int calcCycle);
	
	int getByExponentialSmoothingNew(List<Data> list, int calcCycle, String grade);
}
