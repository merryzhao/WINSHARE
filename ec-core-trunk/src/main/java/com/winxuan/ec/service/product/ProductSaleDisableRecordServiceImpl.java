package com.winxuan.ec.service.product;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProductSaleDisableRecordDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleDisableItem;
import com.winxuan.ec.model.product.ProductSaleDisableRecord;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.pagination.Pagination;

/**
 * @description: TODO
 * 
 * @createtime: 2013-12-10 下午5:35:36
 * 
 * @author zenghua
 * 
 * @version 1.0
 */
@Service("productSaleDisableRecordService")
@Transactional(rollbackFor = Exception.class)
public class ProductSaleDisableRecordServiceImpl implements
		ProductSaleDisableRecordService {

	private static final Log LOG = LogFactory
			.getLog(ProductSaleDisableRecordServiceImpl.class);

	@InjectDao
	private ProductSaleDisableRecordDao productSaleDisableRecordDao;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public ProductSaleDisableRecord get(Long id) {
		return productSaleDisableRecordDao.get(id);
	}
	
	@Override
	public ProductSaleDisableItem getItem(Long itemId) {
		return productSaleDisableRecordDao.getItem(itemId);
	}
	
	@Override
	public void save(ProductSaleDisableRecord record) {
		productSaleDisableRecordDao.save(record);
	}
	
	@Override
	public void saveItem(ProductSaleDisableItem item) {
		productSaleDisableRecordDao.saveItem(item);
	}
	
	@Override
	public void update(ProductSaleDisableRecord record) {
		productSaleDisableRecordDao.update(record);
	}
	
	@Override
	public void updateItem(ProductSaleDisableItem item) {
		productSaleDisableRecordDao.updateItem(item);
	}

	@Override
	public List<ProductSaleDisableRecord> find(@ParameterMap Map<String,Object> params, @Page Pagination pagination, @Order short index) {
		return productSaleDisableRecordDao.find(params, pagination, index);
	}
	
	@Override
	public List<ProductSaleDisableItem> findItems(@ParameterMap Map<String,Object> params, @Page Pagination pagination) {
		return productSaleDisableRecordDao.findItems(params, pagination);
	}
	
	@Override
	public int recoverItemsByRecord(ProductSaleDisableRecord record) {
		return productSaleDisableRecordDao.recoverItemsByRecord(record.getId());
	}
	
	@Override
	public List<ProductSaleDisableRecord> findByUserAndStatus(Employee operator, Code status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status.getId());
		params.put("userId", operator.getId());
		return productSaleDisableRecordDao.find(params, null, (short)0);
	}
	
	@Override
	public ProductSaleDisableRecord upload(List<ProductSale> productSales, final Employee operator, String remark) {
		ProductSaleDisableRecord record = new ProductSaleDisableRecord();
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setUploader(operator);
		record.setComment(remark);
		record.setStatus(new Code(Code.PRODUCT_STOP_STATUS_UPLOAD));
		productSaleDisableRecordDao.save(record);
		
		for (ProductSale ps : productSales) {
			ProductSaleDisableItem item = new ProductSaleDisableItem();
			item.setProductSale(ps);
			item.setProductSaleDisableRecord(record);
			item.setStatus(new Code(Code.PRODUCT_STOP_STATUS_UPLOAD));
			productSaleDisableRecordDao.saveItem(item);
		}
		return record;
	}

	@Async
	@Override
	public void asyncProductSaleStop(ProductSaleDisableRecord productSaleDisableRecord, final Employee operator) {
		productSaleDisableRecord = productSaleDisableRecordDao.get(productSaleDisableRecord.getId());
		for(ProductSaleDisableItem productSaleDisableItem : productSaleDisableRecord.getItemList()) {
			productService.updateProductStatus(productSaleDisableItem.getProductSale(), new Code(Code.PRODUCT_SALE_STATUS_EC_STOP), operator);
			productSaleDisableItem.setStatus(new Code(Code.PRODUCT_STOP_STATUS_STOP));
			productSaleDisableItem.setSynced(false);
			productSaleDisableRecordDao.saveItem(productSaleDisableItem);
			LOG.info("异步更新商品停用状态：" + productSaleDisableItem.getProductSale().getId());
		}
		productSaleDisableRecord.setStatus(new Code(Code.PRODUCT_STOP_STATUS_STOP));
		productSaleDisableRecordDao.save(productSaleDisableRecord);
		LOG.info("异步更新商品停用状态结束：" + productSaleDisableRecord.getId());
	}
	
	@Async
	@Override
	public void asyncProductSaleRecover(ProductSaleDisableRecord productSaleDisableRecord, final Employee operator) {
		productSaleDisableRecord = productSaleDisableRecordDao.get(productSaleDisableRecord.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productSaleDisableRecordId", productSaleDisableRecord.getId());
		params.put("status", Code.PRODUCT_STOP_STATUS_STOP);
		for(ProductSaleDisableItem productSaleDisableItem : productSaleDisableRecordDao.findItems(params, null)) {
			productService.recoverProductSaleStatus(productSaleDisableItem.getProductSale(), operator);
			productSaleDisableItem.setStatus(new Code(Code.PRODUCT_STOP_STATUS_ALL_RELEASE));
			productSaleDisableItem.setSynced(false);
			productSaleDisableRecordDao.saveItem(productSaleDisableItem);
			LOG.info("异步释放商品停用状态：" + productSaleDisableItem.getProductSale().getId());
		}
		productSaleDisableRecord.setStatus(new Code(Code.PRODUCT_STOP_STATUS_ALL_RELEASE));
		productSaleDisableRecordDao.update(productSaleDisableRecord);
		
		LOG.info("异步释放商品停用状态结束：" + productSaleDisableRecord.getId());
	}

	@Override
	public boolean existStopedItem(ProductSaleDisableRecord record) {
		return productSaleDisableRecordDao.existStopedItem(record.getId());
	}
	
}
