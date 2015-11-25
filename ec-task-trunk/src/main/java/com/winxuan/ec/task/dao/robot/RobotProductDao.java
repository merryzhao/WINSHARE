package com.winxuan.ec.task.dao.robot;

import java.io.Serializable;
import java.util.List;

import com.winxuan.ec.task.model.robot.RobotProduct;
import com.winxuan.ec.task.model.robot.RobotProductCategoryLog;

/**
 * Robot商品dao
 * @author Heyadong
 * @version 1.0, 2012-3-28
 */
public interface RobotProductDao extends Serializable {
	
	
	/**
	 * 根据ISBN查找 RobotProduct
	 * @param barcodes
	 * @return
	 */
	public List<RobotProduct> findProductByBarcode(String[] barcodes);
	
	/**
	 * 获取未处理的商品分类变更日志
	 * @param limit 限制条数
	 * @return 
	 */
	public List<RobotProductCategoryLog> productCategoryUnprocessedLog(int limit);
	
	/**
	 * 批量修改,日志处理状态
	 * @param list 
	 * @param status 
	 * @see com.winxuan.ec.task.model.robot.RobotProductCategoryLog
	 */
	public void updateLogStatus(List<RobotProductCategoryLog> list, int status);
}
