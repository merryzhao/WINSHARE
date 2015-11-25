package com.winxuan.ec.task.service.erp;

import com.winxuan.ec.task.model.erp.ErpProductStock;


/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-8-6 下午4:58:08  --!
 * @description:
 ********************************
 */
public interface ErpProductStockService {
	/**
	 * 对文轩网的商品进行增量更新 基于主数据
	 * 
	 * @throws Exception
	 */
	void incrementalUpdateProduct(ErpProductStock erpProductStock) throws Exception;
}
