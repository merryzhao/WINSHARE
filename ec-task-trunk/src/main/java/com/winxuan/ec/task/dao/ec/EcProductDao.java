package com.winxuan.ec.task.dao.ec;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.winxuan.ec.task.model.ec.EcProductCategoryStatus;
import com.winxuan.ec.task.model.robot.RobotProductCategoryLog;

/**
 * Ec商品分类同步.
 * 
 * 主要实现功能:
 * 1.将EC-Core新商品,作为抓取任务,添加到robot系统.
 * 2.分类替换,  将MC分类的商品替换为 robot分类(即:卓越)
 * 
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
public interface EcProductDao extends Serializable {
	/**
	 * 获取新商品
	 * 返回只包含参数
	 * {@link EcProductCategoryStatus#getProduct()}
	 * {@link EcProductCategoryStatus#getIsbn()}
	 * @param max 最大个数
	 * @return list
	 */
	List<EcProductCategoryStatus> getNewProducts(int max);
	
	/**
	 * 批量更新商品状态
	 * isNew = 0
	 * @param list 
	 */
	void updateIsNew(List<EcProductCategoryStatus> list);
	
	
	
	/**
	 * 获取MC关系的商品
	 * 主要用于: 将MC分类商品 转换会 卓越分类商品.
	 * 
	 * 隐含条件:
	 * 1.图书
	 * 2.任务已经添加到robot的(isnew = 0).
	 * 3.当前关系为MC分类 status = 2;
	 * 
	 * @param minDate 时间限制  (EcProductCategoryStatus.maxdate < minDate)
	 * @param pageNo 页数. 
	 * @param pageSize 大小
	 * @return list
	 */
	List<EcProductCategoryStatus> getMcProducts(Date minDate, int start, int limit);
	
	/**
	 * 批量删除商品与分类关系
	 * @param products Ec-Core商品id
	 */
	void deleteProductRelation(List<Long> products);
	
	/**
	 * 批量更新商品 分类状态
	 * @param status 分类状态 {@link EcProductCategoryStatus#getStatus()}}
	 * @param products Ec-core商品ID
	 */
	void updateProductStatus(int status, List<Long>  products);
	
	/**
	 * 同步商品分类
     * 
	 * @param productId EC-商品ID
	 * @param robotIds robot库中的ID,   (在EC中, robot_category.robot_id) 
	 */
	void syncProductCategory(long productId, List<Long> robotIds);
	
	
	/**
	 * 重置商品状态日志。
	 * @param logs robot商品状态日志
	 * @see com.winxuan.ec.task.model.robot.RobotProductCategoryLog
	 */
	void resetEcProductStatus(List<RobotProductCategoryLog> logs);
	
	/**
	 * 同步商品描述信息
	 * @param limit 同步个数限制
	 * @return 0 或 小于limit则,所有数据同步完成 
	 */
	int syncProductDescription(int limit);
	
	/**
	 * 同步商品责任人信息,limit同syncProductDescription
	 */
	int syncResponsibilityInfo(int limit); 
}
