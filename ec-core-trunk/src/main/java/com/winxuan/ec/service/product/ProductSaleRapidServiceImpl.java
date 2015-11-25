/**
 * 
 */
package com.winxuan.ec.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProductSaleRapidDao;
import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRapid;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * @author zhousl
 *
 * 2013-9-3
 */
@Service(value="productSaleRapidService")
@Transactional(rollbackFor=Exception.class)
public class ProductSaleRapidServiceImpl implements ProductSaleRapidService{

	@InjectDao
	private ProductSaleRapidDao productSaleRapidDao;
	
	@Autowired
	private ProductSaleStockService productSaleStockService;

	@Override
	public void save(ProductSaleRapid productSaleRapid) throws ProductSaleStockException {
		productSaleRapidDao.save(productSaleRapid);
		productSaleStockService.updateVirtualQuantity(new Code(Code.DC_8A19), productSaleRapid.getProductSale(), productSaleRapid.getAmount());
	}

	@Override
	public void update(ProductSaleRapid productSaleRapid) throws ProductSaleStockException {
		productSaleRapidDao.update(productSaleRapid);
		productSaleStockService.updateVirtualQuantity(new Code(Code.DC_8A19), productSaleRapid.getProductSale(), productSaleRapid.getAmount());
	}

	@Override
	public ProductSaleRapid getByProductSale(ProductSale productSale) {
		return productSaleRapidDao.getByProductSale(productSale);
	}

}
