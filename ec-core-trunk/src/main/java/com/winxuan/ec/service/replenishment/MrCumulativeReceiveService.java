/**
 * 
 */
package com.winxuan.ec.service.replenishment;

import java.util.List;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrCumulativeReceiveTemp;

/**
 * @author monica
 *
 */
public interface MrCumulativeReceiveService {

	/**
	 * 每天凌晨定时对mr_product_cumulative_receive表中的数据进行修改
	 */
	void updateCumulativeReceive(MrCumulativeReceiveTemp mrCumulativeReceiveTemp);
	
	List<MrCumulativeReceiveTemp> getCumulativeReceiveTempList();
	
	boolean isExistInReceive(ProductSale productSale, Code dc);
}
