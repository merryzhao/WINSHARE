package com.winxuan.ec.task.service.erp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcRule;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.dao.erp.ErpDao;
import com.winxuan.ec.task.model.erp.ErpProductStock;
import com.winxuan.ec.task.service.erp.ErpProductStockService;
import com.winxuan.framework.pagination.Pagination;


/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-8-6 下午4:58:46  --!
 * @description:
 ********************************
 */
@Service("erpProductStockService")
@Transactional(rollbackFor = Exception.class)
public class ErpProductStockServiceImpl implements ErpProductStockService {


	private static final Log LOG = LogFactory.getLog(ErpOrderServiceImpl.class);
	@Autowired
	private ErpDao erpDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private DcService dcService;

	@Autowired
	private ProductSaleStockService productSaleStockService;

	@Override
	public void incrementalUpdateProduct(ErpProductStock erpProductStock) throws Exception {
			long merchId = erpProductStock.getMerchId();
			ProductSale product = this.getProductSale(merchId);
			if (product == null) {
				return;
			}
			DcRule dc = this.matchDcByErp(erpProductStock);
			if (dc == null) {
				return;
			}
			Code dcInfo = dc.getLocation();
			int stock = erpProductStock.getStock();
			productSaleStockService.updateQuantityByWms(dcInfo, product, stock);
			erpDao.updateErpProductStockState(merchId);
			String msg = "psId:%s,merchId:%s,stcok:%s,dcInfo:%s,updateSuccess!";
			LOG.info(String.format(msg,product.getId(), merchId,stock,dcInfo));
	}
	/**
	 * 根据erp过来的dc信息匹配ec系统的dc信息
	 * 
	 * @param erpProductStock
	 * @return
	 */
	private DcRule matchDcByErp(ErpProductStock erpProductStock) {
		List<DcRule> dcList = dcService.findByAvailableDc();
		String erpDc = erpProductStock.getDc();
		DcRule result = null;
		for (DcRule dcRule : dcList) {
			if (dcRule.getLocation().getName().equalsIgnoreCase(erpDc)) {
				result = dcRule;
				break;
			}
		}
		if (result == null) {
			String msg = "dc match failure,erpDc:%s";
			LOG.warn(String.format(msg, erpDc));
		}
		return result;
	}

	/**
	 * 根据merchId 获取ProductSale
	 * 
	 * @param merchId
	 * @return
	 */
	private ProductSale getProductSale(long merchId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("prodcutMerchId", merchId);
		parameters.put("notStorageType", Code.STORAGE_AND_DELIVERY_TYPE_EBOOK);
		List<ProductSale> result = productService.findProductSale(parameters,
				new Pagination());
		if (CollectionUtils.isNotEmpty(result)) {
			if (result.size() > MagicNumber.ONE) {
				String msg = "merchId:(%s) mapping productSale have many rows";
				LOG.warn(String.format(msg, merchId));
			} else {
				return result.get(MagicNumber.ZERO);
			}
		}
		return null;

	}
}
