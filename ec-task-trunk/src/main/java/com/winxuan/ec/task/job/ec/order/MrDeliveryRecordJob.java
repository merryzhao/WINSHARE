package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrCumulativeReceiveTemp;
import com.winxuan.ec.model.replenishment.MrDeliveryRecordCurday;
import com.winxuan.ec.model.replenishment.MrProductItem;
import com.winxuan.ec.service.replenishment.MrCumulativeReceiveService;
import com.winxuan.ec.service.replenishment.MrDeliveryRecordService;
import com.winxuan.ec.service.replenishment.MrProductFreezeService;
import com.winxuan.ec.service.replenishment.MrProductItemService;
import com.winxuan.ec.support.forecast.Data;
import com.winxuan.ec.support.forecast.DefaultStockForecast;
import com.winxuan.ec.support.forecast.StockForecast;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;

/**
 * 智能补货-统计每日发货记录
 * 
 * @author yangxinyi
 * 
 */

@Component("mrDeliveryRecordJob")
public class MrDeliveryRecordJob implements Serializable, TaskAware, TaskConfigure {

	private static final long serialVersionUID = 5686377638626913378L;
	
	private static final Log LOG = LogFactory.getLog(MrDeliveryRecordJob.class);
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private MrDeliveryRecordService mrDeliveryRecordService;
	
	@Autowired
	private MrProductFreezeService mrProductFreezeService;
	
	@Autowired
	private MrProductItemService mrProductItemService;
	
	@Autowired
	private MrCumulativeReceiveService mrCumulativeReceiveService;
	
	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public int getNotifyLevel() {
		return TaskConfigure.LEVEL_ALL;
	}

	@Override
	public void doNotify(String... msg) {
		notifyService.notify(this, msg[0]);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "mrDeliveryRecordJob";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "智能补货-统计每日发货记录";
	}

	@Override
	public void start() {
		//先根据商品的累积收获量处理每日的解冻商品
		
		List<MrCumulativeReceiveTemp> cumulativeReceiveTempList = mrCumulativeReceiveService.getCumulativeReceiveTempList();
		for(MrCumulativeReceiveTemp mrCumulativeReceiveTemp : cumulativeReceiveTempList){
			mrCumulativeReceiveService.updateCumulativeReceive(mrCumulativeReceiveTemp);
		}
		LOG.info("处于冻结期间的商品成功获取累积收货数据");
		//解冻冻结时间小于系统当前时间和累积收货数量大于补货数量60%的商品
		List<Long> freezedIdList = mrProductFreezeService.getFreezed();
		for(Long freezedId : freezedIdList){
			mrProductFreezeService.updateProductFreeze(freezedId);
		}
		LOG.info("按照新的逻辑成功解冻对应的冻结商品");
		 
		DateFormat numDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String sDate = numDateFormat.format(date);
		
		//更新每日发货数据表
		mrDeliveryRecordService.newMrDeliveryRecord();
			
		//获取当日发货数据，存入recordcurdaylist
		List<MrDeliveryRecordCurday> recordCurdayList = mrDeliveryRecordService.findCurdateDeliveryRecords();
		LOG.info("the mrdeliveryrecord size is :" + recordCurdayList.size());
		
		/**
		 * 调用DefaultStockForecast类，该类包含了用于补货计算的三个模型：老模型、新模型与定位表
		 * 定位表：特定MC类2、非特定MC类中销售分级为D的商品
		 * 老模型：特定MC类1、非特定MC类中销售分级为C、C-的商品
		 * 新模型：非特定MC类中销售分级为A+、A、B+、B、B-、C+、N的商品
		 * 其中，新上架、暂没计算销售分级的商品均标识为新品N
		 * 
		 */
		StockForecast forecast = new DefaultStockForecast();		
		for(MrDeliveryRecordCurday record : recordCurdayList) {		
			try{
				/**
				 * recordCurdayList中包含了处于限制补货表，且dc为D818的商品
				 * 不允许这部分商品进入补货模型的计算
				 * dc为D803的商品也不允许进入补货模型的计算
				 */
				Long productSaleId = record.getProductSale();
				boolean limitFreezedOrNot = mrProductFreezeService.
						existSingleLimitFreezedProduct(new ProductSale(productSaleId));
				if(limitFreezedOrNot || record.getDc().longValue() == 110001){
					continue;
				}
				/**
				 * 记录正式参与补货模型的数据记录
				 */
				LOG.info("productSaleId为:" + productSaleId + ",dc为:" + record.getDc().longValue()
						+"的商品正式参与补货模型的计算");
			
				int forecastSale = 0;
				int safeStock = 0;
				Long modelCode = record.getMcModel();
			
				//对于属于特定MC类1或特定MC类2的商品而言，计算模型与销售分级无关，因此可通过MC类进行调用
				if (Code.MR_MC_SPECIFIC_2.equals(record.getMcType())) {
					modelCode = Code.MR_FIX;
				} else if(Code.MR_MC_SPECIFIC_1.equals(record.getMcType())) {
					modelCode = Code.MR_MODE_OLD;
				}
				/**
				 * 定位表：直接根据商品MC类确定安全库存和目标库存的数量
				 * 如果安全库存大于现有库存量，则需要补货，补货数量为安全库存（定位表中即等于目标库存）-现有库存量
				 * 预测销售数量即为目标库存量
				 */
				if (Code.MR_FIX.equals(modelCode)) {
					safeStock = forecast.getSafeStockAmount(record.getFixQuantity(), record.getFixBoundTop(), record.getFixBoundBottom(), 
							record.getFixRatio(), record.getFixDefaultQuantity(), record.getListPrice());
					if(safeStock > record.getAvailableQuantity()) {
							forecastSale = safeStock;
					}
					
				/**
				 * 老模型：根据过去8个周期的历史发货数据的平均值来确定安全库存和目标库存
				 * 1）根据在途周期(transitCycle)计算安全库存，如果安全库存大于现有库存量，则：
				 * 2）根据补货周期(replenishmentCycle)计算目标库存，也即预测销售量。
				 */
				} else if (Code.MR_MODE_OLD.equals(modelCode)) {
					List<Integer> params = mrDeliveryRecordService.getProductQuantityOld(record.getProductSale(), record.getDc(), record.getSaleCycle());
					safeStock = forecast.getByAverage(params, (int) Math.ceil(record.getTransitCycle()/7)); 
					if (safeStock > record.getAvailableQuantity()) {
							forecastSale = forecast.getByAverage(params, record.getReplenishmentCycle());
							modelCode = Code.MR_MODE_OLD;
				}					
				/**
				 * 新模型：调用指数平滑法计算安全库存和目标库存，过程同老模型
				 */
				} else {
				/**
				 * 没有销售分级的商品，默认其销售分级为N
				 */
					if(record.getGrade() == null){
							record.setGrade(ProductSale.PRODUCT_SALE_GRADE_N);
					}
					List<Data> params = mrDeliveryRecordService.getProductQuantityNew(record.getProductSale(), record.getDc(), record.getSaleCycle());
					safeStock = forecast.getByExponentialSmoothingNew(params, (int) Math.ceil(record.getTransitCycle()/7), record.getGrade());  
					if(safeStock > record.getAvailableQuantity()) {
						forecastSale = forecast.getByExponentialSmoothingNew(params, record.getReplenishmentCycle(), record.getGrade());
						modelCode = Code.MR_MODE_NEW;
					}
				}
				/**
				 * 将需要补货的商品写入mr_product_item表
				 * 则补货数量为：预测销售值-可用库存量
				 */
				if(forecastSale > record.getAvailableQuantity()) {
					MrProductItem mrProductItem = new MrProductItem();
					mrProductItem.setId(record.getMrProductItem());
					mrProductItem.setProductSale(new ProductSale(record.getProductSale()));
					mrProductItem.setGrade(record.getGrade());
					mrProductItem.setCreateTime(new Date());
					mrProductItem.setAvailableQuantity(record.getAvailableQuantity());
					mrProductItem.setSafeQuantity(safeStock);
					mrProductItem.setForecastQuantity(forecastSale);
					mrProductItem.setModel(new Code(modelCode));  
					mrProductItem.setDc(new Code(record.getDc()));
					mrProductItem.setNum(sDate);
					/**
					 * 设置补货商品的type为531001
					 */
					mrProductItem.setType(new Code(Code.MR_REPLENISH_TYPE_SYSTEM));
					mrProductItem.setReplenishmentCycle(record.getReplenishmentCycle());
					mrProductItem.setReplenishmentQuantity(forecastSale - record.getAvailableQuantity());
					mrProductItemService.saveOrUpdate(mrProductItem);
				} 		
			}catch(Exception e){
					LOG.error("replenishment error productSaleId " + record.getProductSale() + ":" + e);
			}
		}	
	}
}
