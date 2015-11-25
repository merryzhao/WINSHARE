/*
 * @(#)DefaultStockModel.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.forecast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.winxuan.ec.model.product.ProductSale;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.CustomerDoubleExponentialSmoothingModel;
//import net.sourceforge.openforecast.models.CustomerTripleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.SimpleExponentialSmoothingModel;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2013年8月19日
 */
@Component
public class DefaultStockForecast implements StockForecast {

	@Override
	public int getByAverage(List<Integer> list, int calcCycle) {

		// ls用于存放从第一个历史销售数据不为0的周期开始到当前周期的所有历史销售数据

		List<Integer> ls = new ArrayList<Integer>();

		if (calcCycle <= 0) {
			throw new IllegalArgumentException("calcCycle must greater than 0");
		}
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException(
					"list must not null or not empty");
		}

		double count = 0;
		double average = 0;

		// 如果从当前周期向前推8个周期，存在历史销售数据为0的周期，从第一个历史销售数据不为0的周期开始取数，进行预测
		for (Integer value : list) {
			if (value == 0) {
				continue;
			}
			if (value != 0) {
				for (int i = list.indexOf(value); i < list.size(); i++) {
					ls.add(list.get(i));
				}
				list = ls;
				break;
			}
		}

		for (Integer value : list) {
			count = count + value;
		}
		average = count / list.size();
		return (int) Math.ceil(average * calcCycle);
	}	


	@Override
	/**
	 * 对于非特定MC类中的N类商品，采用一次、二次指数平滑方法进行计算，要求平滑系数alpha以0.1的速度在[0.0,0.5]的闭区间内递增
	 * 这种情况下，相当于每种商品需要计算15次
	 * 三次指数平滑方法暂时不用
	 */
	public int getByExponentialSmoothing(List<Data> list, int calcCycle) {
		
		list=this.dataProprocessing(list, calcCycle);
		if(this.getBySpecialSituation(list, calcCycle)!=0){
			return this.getBySpecialSituation(list, calcCycle);
		}
			
		DataSet observedData = this.dataPreparation(list, calcCycle);
		
		Map<String, DataSet> map = new HashMap<String, DataSet>();
		Map<Integer, String> mseMap = new HashMap<Integer, String>();
		
		double alpha;
		int index;
		for (alpha = 0.0, index =0; alpha <= 0.5 && index<=5; alpha = alpha + 0.1,index =index+1) {

			DataSet simpleData = new DataSet();
			simpleData.setTimeVariable("x");
			DataSet doubleData = new DataSet();
			doubleData.setTimeVariable("x");

			DataPoint dp = new Observation(list.get(0).getValue());
			dp.setIndependentValue("x", 0);
			
			for (int i = 0; i < list.size() + calcCycle; i++) {
				DataPoint dpt = new Observation(0);
				dpt.setIndependentValue("x", i);
				simpleData.add(dpt);
				doubleData.add(dpt);
			}

			SimpleExponentialSmoothingModel simpleModel = new SimpleExponentialSmoothingModel(
					alpha);
			simpleModel.init(observedData);

			map.put("s_" + index, simpleModel.forecast(simpleData));
			mseMap.put(calculateMse(observedData, simpleData), "s_" + index);

			CustomerDoubleExponentialSmoothingModel doubleModel = new CustomerDoubleExponentialSmoothingModel(
					alpha);
			doubleModel.init(simpleData);
			map.put("d_" + index, doubleModel.forecast(doubleData));
			mseMap.put(calculateMse(observedData, doubleData), "d_" + index);
		}
		
		List<Integer> keyList = new ArrayList<Integer>(mseMap.keySet());
		Collections.sort(keyList);
		DataSet ds = map.get(mseMap.get(keyList.get(0)));

		double forecast = 0;
		Iterator<DataPoint> it = ds.iterator();
		String timeVariable = ds.getTimeVariable();
		while (it.hasNext()) {
			DataPoint dp = it.next();
			double dpTimeValue = dp.getIndependentValue(timeVariable);
			if (dpTimeValue >= list.size()) {
				forecast = forecast + dp.getDependentValue();
			}
		}
		return (int) Math.ceil(forecast);
	}
	
	
	@Override
	/**
	 * 对于非特定MC类中的A+、A、B+、B、B-、C+类商品，采用一次指数平滑进行计算，要求平滑指数 的取值以0.1等速在0.0~1.0的闭区间内增加
	 * 这种情况下，相当于每种商品需要计算10次
	 */
	public int getBySimpleExponentialSmoothing(List<Data> list, int calcCycle){
		
		
		list=this.dataProprocessing(list, calcCycle);
		if(this.getBySpecialSituation(list, calcCycle)!=0){
			return this.getBySpecialSituation(list, calcCycle);
		}
		DataSet observedData=this.dataPreparation(list,calcCycle);
		
		
		Map<String, DataSet> map = new HashMap<String, DataSet>();
		Map<Integer, String> mseMap = new HashMap<Integer, String>();
		
		double alpha;
		int index;
		for(alpha=0.0, index=0;alpha<=1.0 && index<=10;alpha=alpha+0.1,index =index+1){
			
			DataSet simpleData = new DataSet();
			simpleData.setTimeVariable("x");
			DataPoint dp = new Observation(list.get(0).getValue());
			dp.setIndependentValue("x", 0);
			
			for (int i = 0; i < list.size() + calcCycle; i++) {
				DataPoint dpt = new Observation(0);
				dpt.setIndependentValue("x", i);
				simpleData.add(dpt);
			}
			SimpleExponentialSmoothingModel simpleModel = new SimpleExponentialSmoothingModel(
					alpha);
			simpleModel.init(observedData);

			map.put("s_" + index, simpleModel.forecast(simpleData));
			mseMap.put(calculateMse(observedData, simpleData), "s_" + index);
		}
		List<Integer> keyList = new ArrayList<Integer>(mseMap.keySet());
		Collections.sort(keyList);
		DataSet ds = map.get(mseMap.get(keyList.get(0)));

		double forecast = 0;
		Iterator<DataPoint> it = ds.iterator();
		String timeVariable = ds.getTimeVariable();
		while (it.hasNext()) {
			DataPoint dp = it.next();
			double dpTimeValue = dp.getIndependentValue(timeVariable);
			if (dpTimeValue >= list.size()) {
				forecast = forecast + dp.getDependentValue();
			}
		}
		return (int) Math.ceil(forecast);
	}

	private double getDependentValueByIndex(DataSet dataset, String name,
			double indexValue) {
		Iterator<DataPoint> it = dataset.iterator();
		while (it.hasNext()) {
			DataPoint dp = it.next();
			if (dp.getIndependentValue(name) == indexValue) {
				return dp.getDependentValue();
			}  
		}
		return 0.0;
	}

	private int calculateMse(DataSet observedData, DataSet forecastDataPoints) {
		int sumMse = 0;
		Iterator<DataPoint> it = observedData.iterator();
		String timeVariable = observedData.getTimeVariable();
		while (it.hasNext()) {
			DataPoint dp = it.next();
			double dpTimeValue = dp.getIndependentValue(timeVariable);
			double value = dp.getDependentValue();
			double mse = Math.pow(
					(value - getDependentValueByIndex(forecastDataPoints,
							timeVariable, dpTimeValue)), 2.0);
			sumMse = (int) (sumMse + mse);
		}
		return sumMse;

	}

	@Override
	public int getSafeStockAmount(int quantity, BigDecimal boundTop,
			BigDecimal boundBottom, BigDecimal ratio, int defaultQuantity,
			BigDecimal listprice) {
		// TODO Auto-generated method stub
		if (listprice.compareTo(boundTop) <= 0) {
			return quantity;
		} else if (listprice.compareTo(boundTop) >= 1
				&& listprice.compareTo(boundBottom) <= 0) {
			return (int) Math.ceil(((quantity) * ratio.doubleValue()));
		}
		return defaultQuantity;
	}


	@Override
	/**对输入的历史发货数据进行预处理
	 * 如果从当前周期向前推8个周期，存在历史销售数据为0的周期，从第一个历史销售数据不为0的周期开始取数，作为指数平滑方法的输入数据
	 */
	public List<Data> dataProprocessing(List<Data> list, int calcCycle) {
		// TODO Auto-generated method stub
		
		// ls用于存放从第一个历史销售数据不为0的周期开始到当前周期的所有历史销售数据，数据格式---周期：发货数量

		List<Data> ls = new ArrayList<Data>();

		if (calcCycle <= 0) {
			throw new IllegalArgumentException("calcCycle must greater than 0");
		}
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException(
					"list must not null and not empty");
		}

		// 如果从当前周期向前推8个周期，存在历史销售数据为0的周期，从第一个历史销售数据不为0的周期开始取数，进行预测

		for (Data data : list) {

			if (data.getValue() == 0) {
				continue;
			}
			if (data.getValue() != 0) {
				for (int i = list.indexOf(data); i < list.size(); i++) {
					ls.add(list.get(i));
				}
				list = ls;
				for (int j = 0; j < list.size(); j++) {
					list.get(j).setCycle(j);
				}
				break;
			}
		}
		return list;
	}


	@Override
	/**
	 * 对采用指数平滑方法进行计算的几种经常出现的特殊数据情况进行特殊处理
	 */
	public int getBySpecialSituation(List<Data> list, int calcCycle) {
		
		list =this.dataProprocessing(list, calcCycle);
		
		// TODO Auto-generated method stub
		//第一种特殊情况：如果输入参数为只有一个数据点的列表，形如
		//（（x=0.0,dependentValue=11.0））则选择一次指数平滑法进行预测，相当于用的简单算术平行法
		//第二种特殊情况：如果输入参数为只有两个连续数据点的列表，形如
		//((x=0.0,dependentValue=5),(x=1.0,dependentValue=3))则选择简单算术平行法进行预测
		//第三种特殊情况可能出现的输入参数为
		//((x=0.0,dependentValue=5),(x=1.0,dependentValue=0))，按照补货模型的启动机制，这种情况是不可能出现的
		if(list.size()==1 || list.size()==2){
			
			List<Integer> listTemp=new ArrayList<Integer>();
			
			for(Data data : list){
				Integer temp=(int) data.getValue();
				listTemp.add(temp);
			}
			return this.getByAverage(listTemp, calcCycle);
		}
		
		//第四种特殊情况：如果输入参数列表中的各个元素值均相等，
		//形如((x=0.0,dependentValue=5),(x=1.0,dependentValue=5),(x=2.0,dependentValue=5))
		//则指数平滑法无法根据最小的MSE值来选择对应的最优结果，因此在这种情况下，选择简单算术平行法进行预测。
		
		else if(list.size()>2)
		{
			
			//第五种特殊情况：如果输入参数列表的长度小于等于5，且只有第一个周期和最后一个周期存在不为0的数值，
			//则直接用第一个周期的真实销售数据乘以在途周期得到安全库存，乘以补货周期得到预测销售数据
			//形如((x=0.0,dependentValue=1),(x=1.0,dependentValue=0),(x=2.0,dependentValue=5))或
			//((x=0.0,dependentValue=1),(x=1.0,dependentValue=0),(x=2.0,dependentValue=0),(x=3.0,dependentValue=2))
			
			if(list.size()>=3 && list.size()<=5){
				for(int i=1; i<list.size()-1;i++){
					if(list.get(0).getValue()!=0 && list.get(i).getValue()==0 && list.get(list.size()-1).getValue()!=0){
						return (int) Math.ceil(list.get(0).getValue()*calcCycle);
					}
				}
			}
			
			List<Integer> listTemp=new ArrayList<Integer>();
			boolean flag=true;
			
			//验证输入参数列表中的各个元素值是否相等
			for(int i=0;i<list.size();i++){
				Integer tempValue= (int) list.get(list.size()-1).getValue();
				if(list.get(i).getValue()!=tempValue){
					flag=false;
				}
				listTemp.add((int) list.get(i).getValue());
			}
			
			if(flag){
				return this.getByAverage(listTemp, calcCycle);
			}
		}
		return 0; 
	}
	
	private DataSet dataPreparation(List<Data> list, int calcCycle){
		
		list=this.dataProprocessing(list, calcCycle);
		
		DataSet observedData = new DataSet();
		observedData.setTimeVariable("x");
		
		for(int i=0;i<list.size();i++){
		
			DataPoint dp=new Observation(list.get(i).getValue());
			dp.setIndependentValue(observedData.getTimeVariable(), list.get(i).getCycle());
			observedData.add(dp);
		}
		return observedData;
	}
	
	@Override
	public int getByExponentialSmoothingNew(List<Data> list, int calcCycle, String grade){
		if(grade.equals(ProductSale.PRODUCT_SALE_GRADE_N)){
			return getByExponentialSmoothing(list, calcCycle);
		}
		else{
			return getBySimpleExponentialSmoothing(list, calcCycle);
		} 
	}
}
