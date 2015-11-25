package com.winxuan.ec.task.service.ec.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.task.dao.ec.EcProductWareHouseDao;
import com.winxuan.ec.task.model.ec.EcProductWareHouse;
import com.winxuan.ec.task.service.ec.EcProductWareHouseService;
/**
 * description
 * @author  lean bian
 * @version 1.0,2013-09-26
 */
@Service("ecProductWareHouseService")
@Transactional(rollbackFor=Exception.class)
public class EcProductWareHouseServiceImpl implements EcProductWareHouseService{

	private static final long serialVersionUID = 2808415021149544394L;
	
	private static final Log LOG = LogFactory.getLog(EcProductWareHouseServiceImpl.class);
	
	@Autowired
	private ProductSaleStockService productSaleService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private EcProductWareHouseDao ecProductWareHouseDao;

	@Override
	public List<EcProductWareHouse> getChangedWarehouseList(int limit){
		return ecProductWareHouseDao.getChangedWarehouseList(limit);
	}
	
	@Override
	public void changeStockAndStateOneByOne(EcProductWareHouse changedEcProductWareHouseItem){
		long getPsBegin = System.currentTimeMillis(); 
		List<ProductSale> psList = productService.getProductList(new Shop(Shop.WINXUAN_SHOP), changedEcProductWareHouseItem.getOuterId());
		long getPsEnd = System.currentTimeMillis(); 
		LOG.info("根据outerid获取商品列表的时间为：" + (getPsEnd - getPsBegin) + "毫秒");
		try{
			if(!CollectionUtils.isEmpty(psList)){
				for(ProductSale ps : psList){
					syncOneNoneComlexItem(changedEcProductWareHouseItem, ps);					
				}
			}
		}catch(ProductSaleStockException e){
			LOG.info(e.getMessage());
		}
		
	}

	private void syncOneNoneComlexItem(EcProductWareHouse currentEcProductWareHouse, ProductSale ps) throws ProductSaleStockException {
		if(!ps.getProduct().isComplex()){
			long getDcBegin = System.currentTimeMillis(); 
			Code dc = codeService.getDcCodeByDcName(currentEcProductWareHouse.getLocation());
			long updateQuantityBegin = System.currentTimeMillis(); 
			productSaleService.updateQuantityByWms(dc, ps, currentEcProductWareHouse.getStock());
			long updateQuantityEnd = System.currentTimeMillis(); 
			ecProductWareHouseDao.updateChanged(currentEcProductWareHouse.getId());
			long updateStateEnd = System.currentTimeMillis(); 
			LOG.info("拿到dc用以更新库存的时间为：" + (updateQuantityBegin - getDcBegin) + "毫秒");
			LOG.info("更新库存的时间为：" + (updateQuantityEnd - updateQuantityBegin) + "毫秒");
			LOG.info("更新状态的时间为：" + (updateStateEnd - updateQuantityEnd) + "毫秒");
		}
	}
}
