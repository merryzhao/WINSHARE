package com.winxuan.ec.task.dao.recommend;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.jdbc.support.rowset.SqlRowSet;
/**
 * 
 * @author huangyixiang
 *
 */
public interface RecommendDao {

	/**
	 * 将开始日期以后的数据保存为最佳拍档的基础数据
	 */
	void saveBaseDataByDate();
	
	/**
	 * 获取最佳拍档基础数据中最大的订单ID
	 * @return
	 */
	long getMaxOrderId();
	
	
	
	SqlRowSet findBaseData(int start, int count);
	
	
	/**
	 * 获取需要计算最佳拍档的商品ID
	 * @param start
	 * @param count
	 * @return
	 */
	List<Long> findNeedCalculateCommodityIdList(int start, int count);
	
	
	/**
	 * 清空最佳拍档的结果
	 */
	void emptyResult();

	/**
	 * 根据商品ID获取最佳拍档列表
	 * 
	 * @param commodityId
	 * @return
	 */
	List<RecommendedItem> findRecommendListByCommodityId(long commodityId,short mode);

	/**
	 * 保存文件中的数据至结果表
	 */
	void saveResultByFile(String filePath);
	
	long getMaxTagId();
	
	long getMaxId();
	
	long getIdAfterId(long id);
	
	long getItemById(long id);
	
	void updateRecommendid();
	
	void updateRecommendation();
	
	void updateMode();
	
	void deleteOnlyOneUserId();
}
