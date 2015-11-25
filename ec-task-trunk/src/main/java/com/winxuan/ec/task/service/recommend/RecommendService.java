package com.winxuan.ec.task.service.recommend;

import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

/**
 * 
 * @author Administrator
 *
 */
public interface RecommendService {


	/**
	 * 初始化基础数据
	 */
	void initBaseData();

	/**
	 * 将基础数据放入文件
	 * 
	 * @throws IOException
	 */
	void saveBaseDataToFile() throws IOException;
	
	
	/**
	 * 获取商品ID的最佳拍档
	 * @return
	 */
	List<RecommendedItem> findRecommendListByCommodityId(long commodityId,short mode) ;

	/**
	 * 根据itemId获取最佳拍档
	 * 
	 * @param itemId
	 * @return
	 * @throws IOException,TasteException
	 */
	List<RecommendedItem> getMostSimilarItems(long itemId)
			throws IOException,TasteException;
	
	/**
	 * 根据userid获取商品
	 * @param userid
	 * @return
	 * @throws IOException
	 * @throws TasteException
	 */
	List<RecommendedItem> getItemsByUserId(long userId) throws IOException, TasteException;

	/**
	 * 根据itemBase规则进行运算
	 * 
	 * @throws IOException
	 */
	void doCalculate() throws IOException,TasteException;

	/**
	 * 将结果文件保存入数据库
	 */
	void saveResult();
	
	/**
	 * 删除只有一个商品的订单和一个商品的访问
	 */
	void deleteOnlyOneUserId();

}
