package com.winxuan.ec.service.replenishment;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.replenishment.MrDeliveryRecord;
import com.winxuan.ec.model.replenishment.MrDeliveryRecordCurday;
import com.winxuan.ec.model.replenishment.MrDeliveryRecordTemp;
import com.winxuan.ec.support.forecast.Data;

/**
 * 
 * @author yangxinyi
 *
 */
public interface MrDeliveryRecordService {
	
	/**
	 * 统计时间跨度
	 */
	int INTERVAL_DAY = 15;
	
	/**
	 * 获取模型参数
	 */
	String PARAM_SQL = "SELECT sum(if(mdr.type = " + Code.MR_TUANGOU + ", mdr.deliveryquantity*mr.weights, mdr.deliveryquantity)) FROM mr_delivery_record mdr"
			+ " left join product_sale_grade psg on psg.productsale = mdr.productsale"
			+ " left join mr_supply mr on mr.grade = psg.grade and mr.dc = mdr.dc"
//			+ " left join mr_supply mc on mc.grade = 'N' and mc.dc = mdr.dc"
			+ " where mdr.productsale = ? and mdr.dc = ?"
			+ " and mdr.deliverydate >= date_sub(curdate(), interval ? day) and mdr.deliverydate < date_sub(curdate(), interval ? day)";
	
	MrDeliveryRecord get(Long id);

	List<MrDeliveryRecord> find(Map<String, Object> parameters);
		
	/**
	 * 每日发货记录定时更新
	 */
	void saveMrDeliveryRecord(String startDate, String endDate);

	/**
	 * 查找某时间段内需要更新的数据
	 * startDate, endDate格式为：yyyy-MM-dd
	 * 
	 */
	int findNewDeliveryRecord(String startDate, String endDate);
	
	/**
	 * 查找当天已写入mr_delivery_record的发货记录
	 * 过滤掉已冻结的商品，同时覆盖已存在的mrProductItem
	 */
	List<MrDeliveryRecordCurday> findCurdateDeliveryRecords();

	/**
	 * 按时间段统计的发货总数，老模型计算数据
	 * @param productSale
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Integer> getProductQuantityOld(Long productSale, Long dc, int saleCycle);
	
	/**
	 * 按时间段统计的发货总数，新模型计算数据
	 * @param productSale
	 * @param saleCycle
	 * @return
	 */
	List<Data> getProductQuantityNew(Long productSale, Long dc, int saleCycle);
	
	void newMrDeliveryRecord();
	
	void saveSingleDeliveryRecord(MrDeliveryRecordTemp mrDeliveryRecordTemp);
	
//	void calculateCurdateDeliveryRecords();
//	
//	void calculateProductItem(MrDeliveryRecordCurday mrDeliveryRecordCurday);
	
}
