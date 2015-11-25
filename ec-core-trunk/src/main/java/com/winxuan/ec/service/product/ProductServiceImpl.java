package com.winxuan.ec.service.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.McCategoryDao;
import com.winxuan.ec.dao.ProductChannelApplyDao;
import com.winxuan.ec.dao.ProductDao;
import com.winxuan.ec.dao.ProductMetaDao;
import com.winxuan.ec.dao.ProductSaleBundleDao;
import com.winxuan.ec.dao.ProductSaleIncorrectWarehouseDao;
import com.winxuan.ec.dao.ProductSalePerformanceDao;
import com.winxuan.ec.exception.CategoryException;
import com.winxuan.ec.exception.ProductException;
import com.winxuan.ec.exception.ProductSaleManageGradeException;
import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.exception.StockDocumentsException;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcRule;
import com.winxuan.ec.model.documents.StockDocuments;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductAuthor;
import com.winxuan.ec.model.product.ProductBooking;
import com.winxuan.ec.model.product.ProductBookingLog;
import com.winxuan.ec.model.product.ProductCategoryStatus;
import com.winxuan.ec.model.product.ProductChannelApply;
import com.winxuan.ec.model.product.ProductDescription;
import com.winxuan.ec.model.product.ProductExtend;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.model.product.ProductImageZoomTask;
import com.winxuan.ec.model.product.ProductManageGrade;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductMetaEnum;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleBundle;
import com.winxuan.ec.model.product.ProductSaleIncorrectWarehouse;
import com.winxuan.ec.model.product.ProductSaleLog;
import com.winxuan.ec.model.product.ProductSalePerformance;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.model.product.ProductSaleStockLog;
import com.winxuan.ec.model.product.ProductTransient;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.documents.DocumentsService;
import com.winxuan.ec.service.util.ProductComplexUtils;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.web.pojo.ProductSearch;
import com.winxuan.framework.cache.method.MethodCache;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.services.pss.enums.EnumStockDocumentsType;

/*
 * @(#)ProductServiceImpl.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

/**
 * 商品
 * 
 * @author HideHai
 * @version 1.0,2011-7-14
 */
@Service("productService")
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService, Serializable {

    /**
     * 订单缺货时商品库存数小于该值时会立即下架
     */
    private static final int OFF_SHELF_QUANTITY_BY_OUT_OF_STOCK = 10;

    private static final long serialVersionUID = 8823435720661067500L;

    private static final String CATEGORY_CODE = "categoryCode";
    
    private static final Short VIRTUAL=1;//虚拟套装

    private static final Log LOG = LogFactory.getLog(ProductServiceImpl.class);

    @Autowired
    private CodeService codeService;
    @InjectDao
    private ProductDao productDao;

    @InjectDao
    private ProductMetaDao productMetaDao;

    @InjectDao
    private ProductChannelApplyDao productChannelApplyDao;

    @InjectDao
    private ProductSalePerformanceDao productSalePerformanceDao;

    @InjectDao
    private McCategoryDao mcCategoryDao;

    @InjectDao
    private ProductSaleBundleDao productSaleBundleDao;

    private ChannelProductService channelProductService;

    @InjectDao
    private ProductSaleIncorrectWarehouseDao incorrectWarehouseDao;

    @Autowired
    private ProductSaleStockService productSaleStockService;

    @Autowired
    private DcService dcService;
    
	@Resource(name="documentsService")
	private DocumentsService documentsService;
    
    

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Product get(Long id) {
        return productDao.get(id);
    }

    public void installCategory(Product product, Category category) {
        product.setCategory(category);
        productDao.update(product);

    }

    public void installSalePrice(ProductSale productSale, BigDecimal salePrice, User operator) {
        saveProductSaleLog(productSale, salePrice, null, operator);
        productSale.setSalePrice(salePrice);
        productDao.update(productSale);
    }

    public void update(Product product) {
        productDao.update(product);

    }

    public void update(ProductSale productSale) {
    	productSale.setUpdateTime(new Date());
        productDao.update(productSale);
    }

    public void updateProductSaleInfo(ProductSale productSale, User operator) throws ProductException {
        update(productSale);
        //complexForChildrenStop(productSale, operator);
        Set<ProductSale> complexMasterList = productSale.getComplexMasterList();
        if (!CollectionUtils.isEmpty(complexMasterList)&&productSale.getBooking()!=null) {
        	for(ProductSale complexMaster : complexMasterList) {
        		this.updateComplexProduct(complexMaster);
        	}
        }
    }

    /**
     * 停用套装书
     * 
     * @param ps
     */
    public void complexStop(User operator, ProductSale... ps) {
        for (ProductSale productSale : ps) {
            updateProductStatus(productSale, codeService.get(Code.PRODUCT_SALE_STATUS_EC_STOP), operator);
        }
    }

    @Override
    public void removeProductExtend(ProductExtend extend) {
        productDao.deleteExtend(extend);
    }

    @Override
    public void removeProductDescription(ProductDescription description) {
        productDao.deleteDescription(description);
    }

    @Override
    public void removeProductImage(ProductImage image) {
        productDao.deleteImage(image);
    }
    
    
    

    public void updateProductQuantity(ProductSale productSale, int updateStockQuantity, int updateSaleQuantity) {
        if (updateStockQuantity == 0 && updateSaleQuantity == 0) {
            return;
        }
        Product product = productSale.getProduct();
        if (!product.isComplex() && !product.isVirtual()) {
            int stockQuantity = productSale.getStockQuantity() + updateStockQuantity;
            int saleQuantity = productSale.getSaleQuantity() + updateSaleQuantity;
            stockQuantity = stockQuantity < 0 ? 0 : stockQuantity;
            saleQuantity = saleQuantity < 0 ? 0 : saleQuantity;
            productSale.setStockQuantity(stockQuantity);
            productSale.setSaleQuantity(saleQuantity);
            Long currentSaleStatusId = productSale.getSaleStatus().getId();
            if (!canBeOnShelf(productSale) && currentSaleStatusId.equals(Code.PRODUCT_SALE_STATUS_ONSHELF)) {
                updateProductStatus(productSale, new Code(Code.PRODUCT_SALE_STATUS_OFFSHELF), new Employee(
                        Employee.SYSTEM));
            } else if (canBeOnShelf(productSale) && currentSaleStatusId.equals(Code.PRODUCT_SALE_STATUS_OFFSHELF)) {
                updateProductStatus(productSale, new Code(Code.PRODUCT_SALE_STATUS_ONSHELF), new Employee(
                        Employee.SYSTEM));
            } else {
                productDao.update(productSale);
            }
            updateChannelStock(productSale.getId(), productSale.getAvalibleQuantity());
        }
//        ProductSale complexMaster = productSale.getComplexMaster();
        Set<ProductSale> complexMasterList = productSale.getComplexMasterList();
        if (!CollectionUtils.isEmpty(complexMasterList)) {
        	for(ProductSale complexMaster : complexMasterList) {
	            try {
	                setupComplexQuantity(complexMaster);
	            } catch (ProductSaleStockException e) {
	                // 这里没法往上抛自定义异常了,涉及修改的地方太多.
	                throw new RuntimeException(e);
	            }
	            productDao.update(complexMaster);
	            saveProductSaleLog(complexMaster, complexMaster.getSalePrice(), complexMaster.getSaleStatus(),
	                    new Employee(Employee.SYSTEM));
	            updateChannelStock(complexMaster.getId(), complexMaster.getAvalibleQuantity());
        	}
        }
    }

    private void updateChannelStock(Long productSaleId, int stock) {
        try {
            if (channelProductService != null) {
                channelProductService.updateStock(productSaleId, stock);
            } else {
                LOG.info("channelProductService is null");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateProductSaleStatusByOutOfStock(ProductSale productSale) {
        if (productSale.getSaleStatus().getId().equals(Code.PRODUCT_SALE_STATUS_ONSHELF)
                && productSale.getStockQuantity() < OFF_SHELF_QUANTITY_BY_OUT_OF_STOCK) {
            updateProductStatus(productSale, new Code(Code.PRODUCT_SALE_STATUS_OFFSHELF), new Employee(Employee.SYSTEM));
            saveIncorrectWarehouse(productSale);
            updateChannelStock(productSale.getId(), 0);
        }
    }

    /**
     * 保存商品的不准确库存记录
     * 
     * @param productSale
     */
    private void saveIncorrectWarehouse(ProductSale productSale) {
        ProductSaleIncorrectWarehouse warehouse = new ProductSaleIncorrectWarehouse();
        warehouse.setProductSale(productSale);
        warehouse.setCreateTime(new Date());
        warehouse.setStock(productSale.getStockQuantity());
        warehouse.setChange(false);
        incorrectWarehouseDao.save(warehouse);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductSale get(Product product, Shop shop) {
        if (shop == null || shop.getId() == null || product == null || product.getId() == null) {
            return null;
        }
        return productDao.get(shop, product, null);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductSale get(Shop shop, String outterId) {
        if (shop == null || shop.getId() == null || StringUtils.isBlank(outterId)) {
            return null;
        }
        return productDao.get(shop, null, outterId);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ProductSale> getProductList(Shop shop, String outerId) {
        return productDao.get(shop, outerId);
    }

    public void save(Product product) {
        product.setMerchId(productDao.getMaxProductId() + 1);
        productDao.save(product);
        saveProductCategoryStatus(product);
    }

    public void save(ProductSale productSale) {
        productDao.save(productSale);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ProductMeta> findProductMeta(boolean onlyAvailable) {
        return onlyAvailable ? productMetaDao.findAvailableProductMeta() : productMetaDao.findAllProductMeta();
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductMeta getProductMeta(Long id) {
        return productMetaDao.getProductMeta(id);
    }

    public void createProductMeta(ProductMeta productMeta) {
        productMetaDao.saveProductMeta(productMeta);
    }

    public void updateProductMeta(ProductMeta productMeta) {
        productMetaDao.updateProductMeta(productMeta);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductMetaEnum getProductMetaEnum(Long id) {
        return productMetaDao.getProductMetaEnum(id);
    }

    public void addProductMetaEnum(ProductMeta productMeta, ProductMetaEnum productMetaEnum) {
        productMeta.addEnum(productMetaEnum);
        productMetaDao.saveProductMetaEnum(productMetaEnum);
    }

    public void updateProductMetaEnum(ProductMetaEnum productMetaEnum) {
        productMetaDao.updateProductMetaEnum(productMetaEnum);
    }

    public void deleteProductMetaEnum(ProductMeta productMeta, ProductMetaEnum productMetaEnum) {
        productMeta.removeEnum(productMetaEnum);
        productMetaDao.deleteProductMetaEnum(productMetaEnum);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ProductSale> findProductSale(Map<String, Object> parameters, Pagination pagination) {
        return productDao.find(parameters, pagination);
    }

    public List<ProductSale> findProductSaleComplex(Map<String, Object> parameters, Pagination pagination) {
        return productDao.findComplex(parameters, pagination);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductSale getProductSale(Long id) {
        return productDao.getProductSale(id);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductBooking getProductBooking(Long id) {
        return productDao.getProductBooking(id);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ProductMeta> findProductMeta(Map<String, Object> parameters, Pagination pagination, short orderIndex) {
        return productMetaDao.findProductMeta(parameters, pagination, orderIndex);
    }

    @Override
    public List<ProductChannelApply> findProductChannelApply(Map<String, Object> params, Pagination pagination) {
        if (pagination == null) {
            return productChannelApplyDao.find(params);
        }
        return productChannelApplyDao.find(params, pagination);
    }

    @Override
    public void auditProductChannelApply(ProductChannelApply channelApply, Code state) {
        // 保留方法 以后有可能会加入业务需求
        channelApply.setState(state);
        productChannelApplyDao.update(channelApply);
    }

    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int getTotalLikeByProductSaleId(ProductSale productSale) {
        ProductSalePerformance productSalePerformance = productSale.getPerformance();
        return productSalePerformance == null ? 0 : productSalePerformance.getTotalDigging();
    }

    @Override
    public boolean isIsbnExist(String isbn) {
        return productDao.getCountByIsbn(isbn) > 0 ? true : false;
    }

    @Override
    public void setupBooing(ProductSale productSale, ProductBooking booking, boolean forSale) throws ProductException {

        ProductSaleStock pss = productSale.getStockByDc(booking.getDc());
        boolean isComplex = productSale.getProduct().isComplex();
        
        if (pss.getActualAvalibleQuantity() >= pss.getVirtualAvalibleQuantity()&&!isComplex) {
            String msg = String.format("虚拟库存可用量和实物库存量一致的时候会自动转为正常商品.stock:%s",pss);
            throw new ProductSaleStockException(productSale,msg);
        }

        if (pss.getActualAvalibleQuantity() >= booking.getStockQuantity() && !isComplex) {
            throw new ProductException(productSale, "该商品已经有相等数量的实物库存.设置的库存:" + booking.getStockQuantity());
        }

        productSale.setBooking(booking);
        booking.setProductSale(productSale);
        Long saleStatus = forSale ? Code.PRODUCT_SALE_STATUS_ONSHELF : Code.PRODUCT_SALE_STATUS_OFFSHELF;
        productSale.setSaleStatus(new Code(saleStatus));
        productSale.setSupplyType(new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING));
        booking.setIgnore(booking.getStockQuantity());
        if (booking.getId() == null) {
            ProductBookingLog log = new ProductBookingLog();
            log.setProductSale(productSale);
            log.setDescription(booking.getDescription());
            log.setEmployee(booking.getEmployee());
            log.setEndDate(booking.getEndDate());
            log.setStartDate(booking.getStartDate());
            log.setDc(booking.getDc());
            log.setStockQuantity(booking.getStockQuantity());
            log.setCreateDate(booking.getCreateDate());
            productSale.addBookingLog(log);
        }
        productDao.saveOrUpdate(booking);

        Set<ProductSale> complexMasters = productSale.getComplexMasterList();
    	if(!CollectionUtils.isEmpty(complexMasters)) {
    		for(ProductSale complexMaster : complexMasters) {
    			if (complexMaster != null && complexMaster.getBooking() == null) {
    	            ProductBooking masterBooking = booking.copySelf();
    	            masterBooking.setProductSale(complexMaster);
    	            complexMaster.setBooking(masterBooking);
    	            productDao.saveOrUpdate(masterBooking);
    	            update(complexMaster);
    	        } else if (complexMaster != null && complexMaster.getBooking() != null) {
    	            computeMasterBookingDate(complexMaster);
    	        }	
    		}
    	}
    	
    	//商品供应类型发生变化同步到渠道商品 
    	this.updateChannelSupplyType(productSale.getId());
    }

    private void computeMasterBookingDate(ProductSale complexMaster) {
        ProductComplexUtils utils = new ProductComplexUtils();
        Date endDate = utils.findMaxData(complexMaster.getComplexItemList());
        Date startDate = utils.findMinData(complexMaster.getComplexItemList());
        ProductBooking masterBooking = complexMaster.getBooking();
        masterBooking.setStartDate(startDate);
        masterBooking.setEndDate(endDate);
        productDao.saveOrUpdate(masterBooking);
    }

    @Override
    public List<Product> findProduct(Map<String, Object> parameters, Pagination pagination) {
        return productDao.findProduct(parameters, pagination);
    }

    @Override
    public void saveProduct(ProductSale productSale) {
        Product product = productSale.getProduct();
        product.setMerchId(productDao.getMaxProductId() + 1);
        productDao.save(product);
        saveProductCategoryStatus(product);
        if (!CollectionUtils.isEmpty(product.getExtendList())) {
            for (ProductExtend productExtend : product.getExtendList()) {
                productDao.save(productExtend);
            }
        }
        if (!CollectionUtils.isEmpty(product.getDescriptionList())) {
            for (ProductDescription productDescription : product.getDescriptionList()) {
                productDao.save(productDescription);
            }
        }
        productDao.save(productSale);
        savePerformance(productSale);
    }

    /**
     * 保存ProductSalePerformance
     * 
     * @param productSale
     */
    private void savePerformance(ProductSale productSale) {
        ProductSalePerformance performance = new ProductSalePerformance();
        Date now = new Date();
        performance.setFirstOnShelfTime(now);
        performance.setLastOnShelfTime(now);
        performance.setProductSale(productSale);
        productSalePerformanceDao.saveOrupdate(performance);
    }

    @Override
    public List<ProductSale> findPromotion(Category category, int size, boolean effect) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        if (category != null) {
            String code = category == null ? null : category.getCode();
            parameter.put(CATEGORY_CODE, code);
        }
        parameter.put("promotionPrice", true);
        Date now = new Date();
        short orderBy = 0;
        if (effect) {
            parameter.put("promotionStartTime", now);
            parameter.put("promotionEndTime", now);
            orderBy = MagicNumber.THREE;
        } else {
            parameter.put("promotionStartBegin", now);
            orderBy = MagicNumber.TWO;
        }
        if (orderBy == 0) {
            ProductSearch productSearch = new ProductSearch();
            productSearch.setPromotionPrice(true);
            return findProductSaleByPerformerce(productSearch, orderBy, size);
        }
        return productDao.findProductSale(parameter, orderBy, size);
    }

    @Override
    public List<Object> isbnMate(Object[] barcodes) {
        return productDao.isbnMate(barcodes);
    }

    @Override
    public void save(ProductImage productImage) {
        productDao.save(productImage);
    }

    @Override
    public void update(ProductImage productImage) {
        productDao.update(productImage);
    }

    @Override
    public void createComplexProduct(ProductSale complex, List<ProductSale> items) throws ProductException {
        complex.getProduct().setMerchId(productDao.getMaxProductId() + 1);
        for (ProductSale item : items) {
            checkComplexItem( item);
            complex.addComplexItem(item);
        }
        checkComplexProduct(complex);
        setupComplexProduct(complex, items);
        save(complex);
        /*for (ProductSale item : items) {
            item.setComplexMaster(complex);
            update(item);
        }*/
        savePerformance(complex);
        saveProductCategoryStatus(complex.getProduct());
        this.saveComlexStock(complex);
        this.setupComplexBooking(complex); 

    }

    /**
     * 设置预售商品信息
     * 
     * @param complexSale
     * @throws ProductException
     */
    private void setupComplexBooking(ProductSale complexSale) throws ProductException {
        if (!Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(complexSale.getSupplyType().getId())) {
            return;
        }
        ProductBooking booking = complexSale.getBooking();
        if (booking == null) {
            booking = new ProductBooking();
        }
        Employee employee = booking.getEmployee();
        if (employee == null) {
            employee = complexSale.getProductTransient().getEmployee();
        }
        ProductComplexUtils utils = new ProductComplexUtils();
        booking.setStartDate(utils.findMinData(complexSale.getComplexItemList()));
        booking.setEndDate(utils.findMaxData(complexSale.getComplexItemList()));
        booking.setCreateDate(new Date());
        Code dc  = complexSale.getProductTransient().getDefaultComplexDc();
        ProductSaleStock stock =complexSale.getStockByDc(dc);
        booking.setDc(stock.getDc());
        booking.setEmployee(employee);
        booking.setProductSale(complexSale);
        booking.setStockQuantity(stock.getVirtualAvalibleQuantity());
        booking.setIgnore(booking.getIgnore());
        booking.setDescription(booking.getDescription());
        setupBooing(complexSale, booking, Code.PRODUCT_SALE_STATUS_ONSHELF.equals(complexSale.getSaleStatus()));
    }

    /**
     * 在套装书持久化完成后调用
     * 
     * @param complex
     * @throws ProductSaleStockException
     */
    private void saveComlexStock(ProductSale complex) throws ProductSaleStockException {
        Code dc = complex.getProductSaleStocks().iterator().next().getDc();
        int stockQuantity = productSaleStockService.computeComplexQuantity(dc, complex.getComplexItemList());
        if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(complex.getSupplyType().getId())) {
            productSaleStockService.updateVirtualQuantity(dc, complex, stockQuantity);
        } else {
            productSaleStockService.updateActualQuantity(dc, complex, stockQuantity);
        }
    }

    @Override
    public void createComplexProduct(ProductSale complex, List<ProductSale> items, List<Code> dc, Employee employee)
            throws ProductException {
        if (dc == null) {
            throw new ProductException(complex, "没有指定dc信息");
        }
        ProductTransient productTransient = new ProductTransient();
        productTransient.setComplexDcList(dc);
        productTransient.setEmployee(employee);
        complex.setProductTransient(productTransient);
        this.createComplexProduct(complex, items);
    }

    /**
     * 设置套装书基本信息
     * 
     * @param complex
     * @param items
     */
    private void setupComplexSupplyType(ProductSale complex) {
        Set<ProductSale> items = complex.getComplexItemList();
        // 设置供应类型
        for (ProductSale productSale : items) {
            if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(productSale.getSupplyType().getId())) {
                complex.setSupplyType(productSale.getSupplyType());
                break;
            }
        }

    }

    private void saveProductCategoryStatus(Product product) {
        ProductCategoryStatus status = new ProductCategoryStatus(product, ProductCategoryStatus.STATUS_HAND, new Date(
                System.currentTimeMillis() + MagicNumber.ONE_DAY * MagicNumber.THIRTY), true);
        productDao.save(status);
    }

    @Override
    public void addToComplexProduct(ProductSale complex, ProductSale item) throws ProductException {
//        checkComplexItem(complex, item);
        complex.addComplexItem(item);
        checkComplexProduct(complex);
        setupComplexProduct(complex, complex.getComplexItemList());
        update(complex);
        update(item);
    }

    @Override
    public void removeFromComplexProduct(ProductSale complex, ProductSale item) throws ProductException {
        complex.removeComplexItem(item);
        checkComplexProduct(complex);
        setupComplexProduct(complex, complex.getComplexItemList());
        update(complex);
        update(item);
    }

    private void checkComplexItem(ProductSale item) {
        if (item.getProduct().isComplex()) {
            throw new RuntimeException(String.format("%s 已是一个套装商品 ," + "套装商品不能为另一个一个套装商品的子商品!", item));
        }
    }

    private void checkComplexProduct(ProductSale complex) throws ProductException {
        Set<ProductSale> items = complex.getComplexItemList();
        if (items == null || items.size() < 2) {
            throw new ProductException(complex, "套装商品至少由两种商品组成");
        }

        if (CollectionUtils.isEmpty(complex.getDefaultTransient().getDefaultComplexDcList(complex))) {
            throw new ProductException(complex, "没有指定dc信息");
        }

        if (complex.getDefaultTransient().getComplexDcList().size() > ProductTransient.DEFAULT_COMPLEX_QUANTITY) {
            throw new ProductException(complex, "╮(╯▽╰)╭,目前业务只能指定单个DC");
        }
    }

    /**
     * 设置套装书在各dc的信息
     * 
     * @param complex
     */
    private void setupComplexQuantityDc(ProductSale complex) {
        boolean isBooking = Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(complex.getSupplyType().getId());
        Set<ProductSale> complexItemList = complex.getComplexItemList();
        List<Code> dc = complex.getProductTransient().getComplexDcList();
        for (Code code : dc) {
            DcRule dcRule = dcService.findByAvailableDc(code);
            Integer avalibleQuantity = productSaleStockService.computeComplexQuantity(code, complexItemList);
            if (avalibleQuantity != null) {
                int stockQuantity = avalibleQuantity + ProductSale.COMPLEX_BASE_STOCK;
                ProductSaleStock productSaleStock = complex.getStockByDc(dcRule.getLocation());
                if (productSaleStock == null) {
                    productSaleStock = new ProductSaleStock();
                } else {
                    productSaleStock.setProductSale(complex);
                }
                productSaleStock.setDc(dcRule.getLocation());
                if (isBooking) {
                    productSaleStock.setVirtual(stockQuantity < 0 ? 0 : stockQuantity);
                } else {
                    productSaleStock.setStock(stockQuantity < 0 ? 0 : stockQuantity);
                }
                productSaleStock.setProductSale(complex);
                complex.addStock(productSaleStock);
            }
        }
    }

    private void setupComplexQuantity(ProductSale complex) throws ProductSaleStockException {
        this.setupComplexSupplyType(complex);
        this.setupComplexQuantityDc(complex);
        Code dc = complex.getProductTransient().getDefaultComplexDc();
        Set<ProductSale> complexProductSales = complex.getComplexItemList();
        int stockQuantity = productSaleStockService.computeComplexQuantity(dc, complexProductSales);
        complex.setStockQuantity(stockQuantity < 0 ? 0 : stockQuantity);
        Long saleStatus = this.getSaleStatus(stockQuantity, complex);
        complex.setSaleStatus(new Code(saleStatus));
    }

    /**
     * 获取套装书上下架状态
     * 
     * @param stockQuantity
     * @param complex
     * @return
     */
    private Long getSaleStatus(int stockQuantity, ProductSale complex) {
        if (stockQuantity <= 0) {
            return Code.PRODUCT_SALE_STATUS_EC_STOP;
        }

        if (!canBeOnShelf(complex)) {
            return Code.PRODUCT_SALE_STATUS_EC_STOP;
        }

        if (complex.isStopped()) {
            return Code.PRODUCT_SALE_STATUS_EC_STOP;

        }
        return Code.PRODUCT_SALE_STATUS_ONSHELF;
    }

    private void setupComplexProduct(ProductSale complex, Collection<ProductSale> items)
            throws ProductSaleStockException {
        ProductSale masterItem = setupComplexProductSalePrice(complex, items);
        Product product = complex.getProduct();
        complex.setOuterId(null);
        Date now = new Date();
        complex.copy(masterItem);
        product.setComplex(VIRTUAL);
        product.setCreateTime(now);
        product.setUpdateTime(now);
        complex.setUpdateTime(now);
        setupComplexQuantity(complex);
    }

    private ProductSale setupComplexProductSalePrice(ProductSale complex, Collection<ProductSale> items) {
        BigDecimal listPrice = BigDecimal.ZERO;
        BigDecimal salePrice = BigDecimal.ZERO;
        BigDecimal basicPrice = BigDecimal.ZERO;
        ProductSale masterItem = null;
        for (ProductSale item : items) {
            if (masterItem == null) {
                masterItem = item;
            }
            listPrice = listPrice.add(item.getListPrice());
            salePrice = salePrice.add(item.getSalePrice());
            basicPrice = basicPrice.add(item.getBasicPrice());
        }
        if (complex.getId() != null) {
            saveProductSaleLog(complex, salePrice, null, new Employee(Employee.SYSTEM));
        }
        Product product = complex.getProduct();
        product.setListPrice(listPrice);
        complex.setSalePrice(salePrice);
        complex.setBasicPrice(basicPrice);
        return masterItem;
    }

    @Deprecated
    @Override
    public void updatePrice(ProductSale productSale, User operator, BigDecimal price) {
        saveProductSaleLog(productSale, price, null, operator);
        productSale.setSalePrice(price);
        productDao.update(productSale);
        Set<ProductSale> complexMasterList = productSale.getComplexMasterList();
        if (!CollectionUtils.isEmpty(complexMasterList)) {
        	for(ProductSale complex : complexMasterList) {
        		if (complex != null) {
                    setupComplexProductSalePrice(complex, complex.getComplexItemList());
                    productDao.update(complex);
                    channelProductService.update(complex.getId());
                }
        	}
        }
        channelProductService.update(productSale.getId());
    }

    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ProductSaleLog> findProductSaleLog(ProductSale productSale, Pagination pagination) {
        return productDao.find(productSale, pagination);
    }

    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean canBeOnShelf(ProductSale productSale) {
        Product product = productSale.getProduct();
        String manageCategory = product.getManageCategory();
        String workCategory = product.getWorkCategory();
        if (productSale.isDeleted()) {
            return false;
        }
        if (product.isComplex()) {
            Set<ProductSale> items = productSale.getComplexItemList();
            if (!CollectionUtils.isEmpty(items)) {
                for (ProductSale item : items) {
                    if (!item.canSale()) {
                        return false;
                    }
                }
            }
        }

        if ("02".equals(workCategory) || "03".equals(workCategory) || "02".equals(manageCategory)
                || "04".equals(manageCategory)) {
            return false;
        }
        boolean quantityAvalible = productSale.getAvalibleQuantity() > 0;
        Shop shop = productSale.getShop();
        if (!shop.getId().equals(Shop.WINXUAN_SHOP)) {
        	//判断百货卖家，是否激活，是否屏蔽搜索引擎(issue #5854)
        	if(shop.getBusinessScope().contains("G")){
        		if(!shop.getState().getId().equals(Code.SHOP_STATE_PASS) || !shop.getState().getId().equals(Code.SHOP_STATE_SEARCH_FAIL)){        			
        			return false;
        		}
        	}
            return quantityAvalible;
        } else {
            if (productSale.isPreSale()) {
                ProductBooking pb = productSale.getBooking();
                Date now = new Date();
                if (pb == null || pb.getStartDate().after(now) || pb.getEndDate().before(now)) {
                    return false;
                }
                return quantityAvalible;
            }

            // 判断当前商品是否有活动, 如果有活动, 则不判断折扣小于2的下架情况(issue #1884)
            if (null != productSale.getPromotionStartTime() && null != productSale.getPromotionEndTime()
                    && productSale.getPromotionStartTime().compareTo(new Date()) <= 0
                    && productSale.getPromotionEndTime().compareTo(new Date()) >= 0) {
                return true;
            }

            boolean priceMatched = productSale.getDiscount().compareTo(ProductSale.MIN_DISCOUNT) >= 0
                    && productSale.getSalePrice().compareTo(BigDecimal.ZERO) > 0;
            if (quantityAvalible && priceMatched) {
                boolean hasImage = product.isHasImage();
                if (!hasImage) {
                    Date productionDate = product.getProductionDate();
                    Calendar now = Calendar.getInstance();
                    now.add(Calendar.MONTH, -MagicNumber.SIX);
                    Date halfYearAgo = now.getTime();
                    now.add(Calendar.MONTH, -MagicNumber.SIX);
                    Date aYearAgo = now.getTime();
                    Long sortId = product.getSort().getId();
                    boolean productionDateMatched = productionDate == null
                            || (sortId.equals(Code.PRODUCT_SORT_BOOK) && productionDate.before(aYearAgo))
                            || (sortId.equals(Code.PRODUCT_SORT_VIDEO) && productionDate.before(halfYearAgo));
                    if (!productionDateMatched) {
                        return false;
                    }
                }
                String mcCategory = product.getMcCategory();
                return !StringUtils.isBlank(mcCategory) && !mcCategoryDao.isExistedFromOffShelf(mcCategory);
            }
        }
        return false;
    }

    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void onShelfOrOffShelf(ProductSale productSale) {
        Long saleStatusId = productSale.getSaleStatus().getId();
        boolean canBeOnshelf = canBeOnShelf(productSale);
        if (canBeOnshelf && saleStatusId.equals(Code.PRODUCT_SALE_STATUS_OFFSHELF)) {
            updateProductStatus(productSale, new Code(Code.PRODUCT_SALE_STATUS_ONSHELF), new Employee(
                    Employee.SYSTEM));
        }
        if (!canBeOnshelf && saleStatusId.equals(Code.PRODUCT_SALE_STATUS_ONSHELF)) {
            updateProductStatus(productSale, new Code(Code.PRODUCT_SALE_STATUS_OFFSHELF), new Employee(
                    Employee.SYSTEM));
        }
    }

    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Code getComplexSupplyType(Set<ProductSale> complexItemList) {
        for (ProductSale productSale : complexItemList) {
            if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(productSale.getSupplyType().getId())) {
                return new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING);
            }
        }
        return new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL);
    }

    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ProductSale> findProductSaleByCategoryAndSell(Category category) {
        String code = category.getCode();
        final int availableQuantity = 3;
        final short orderBy = 2;
        final int size = 6;

        ProductSearch productSearch = new ProductSearch();
        productSearch.setCode(code);
        productSearch.setAvailableQuantity(availableQuantity);
        productSearch.setHasPicture(true);
        return findProductSaleByPerformerce(productSearch, orderBy, size);
    }
    
    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @MethodCache(idleSeconds = MethodCache.SECONDS_OF_DAY)
    public List<ProductSale> findProductSaleByCategoryAndSell(Category category,ProductSale productSale,int size) {
        String code = category.getCode();
        final int availableQuantity = 3;
        final short orderBy = 2;
        ProductSearch productSearch = new ProductSearch();
      productSearch.setCode(code);
      productSearch.setAvailableQuantity(availableQuantity);
      productSearch.setHasPicture(true);
      List<ProductSale> hotSaleList=findProductSaleByPerformerce(productSearch, orderBy, size);
      if(hotSaleList.contains(productSale))
      {
    		hotSaleList.remove(productSale);
      }
      else if(hotSaleList.size()>12)
      {
    	  hotSaleList.remove(size-1);
      }
      
      return hotSaleList;
  }

	@Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ProductSale> findProductSaleByLastest(Category category) {
        String code = category.getCode();
        final int availableQuantity = 50;
        final short orderBy = 5;
        final int size = 6;
        final long onShelfDate = 30L;
        final long publishDate = 90L;

        ProductSearch productSearch = new ProductSearch();
        productSearch.setCode(code);
        productSearch.setAvailableQuantity(availableQuantity);
        productSearch.setHasPicture(true);
        productSearch.setOnShelfDate(onShelfDate);
        productSearch.setPublishDate(publishDate);
        return findProductSaleByPerformerce(productSearch, orderBy, size);
    }

    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ProductSale> findProductSaleByBestSellers(Category category, Long storageType, int size) {
        final int availableQuantity = 10;
        final short orderBy = 0;

        ProductSearch productSearch = new ProductSearch();
        productSearch.setCode(category.getCode());
        productSearch.setAvailableQuantity(availableQuantity);
        productSearch.setHasPicture(true);
        productSearch.setStorageType(storageType);

        return findProductSaleByPerformerce(productSearch, orderBy, size);
    }

    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @MethodCache(idleSeconds = MethodCache.SECONDS_OF_DAY)
    public List<ProductSale> findProductSaleByPerformerce(ProductSearch productSearch, short orderBy, int size) {
        String code = productSearch.getCode();
        Long storageType = productSearch.getStorageType();
        /* Integer availableQuantity = productSearch.getAvailableQuantity();
         * Long onShelfDate = productSearch.getOnShelfDate(); Long publishDate =
         * productSearch.getPublishDate(); */
        Boolean hasPicture = productSearch.getHasPicture();
        Boolean promotionPrice = productSearch.getPromotionPrice();
        Boolean complex = productSearch.getComplex();
        BigDecimal discount = productSearch.getDiscount();
        Boolean useDistinct = false;

        Map<String, Object> params = new HashMap<String, Object>();
        if (code != null) {
            String[] categorys = code.split("\\.");
            String param = "category" + categorys.length;
            params.put(param, Integer.parseInt(categorys[categorys.length - 1]));
            if (categorys.length > MagicNumber.TWO) {
                useDistinct = true;
            }
        }
        /* if(availableQuantity != null){ params.put("availableQuantity",
         * availableQuantity); } if(onShelfDate != null){
         * params.put("onShelfDate", onShelfDate); } if(publishDate != null){
         * params.put("publishDate", publishDate); } */
        if (hasPicture != null) {
            params.put("hasPicture", hasPicture);
        }
       
        if (promotionPrice != null) {
            params.put("promotionPrice", promotionPrice);
        }
        if (complex != null) {
            params.put("complex", complex?(short)1:(short)0);
        }
        if (discount != null) {
            params.put("discount", discount);
        }
        if (null != storageType) {
            params.put("storageType", storageType);
        }
        if (!useDistinct) {
            final float loadFactor = .75f;
            int newSize = size * MagicNumber.FIVE;
            List<ProductSale> productSalesDuplicate = productDao.findProductSaleByPerformerceWithNoDistinct(params,
                    orderBy, newSize);
            LinkedHashSet<ProductSale> productSales = new LinkedHashSet<ProductSale>((int) (size / loadFactor) + 1);
            for (ProductSale productSale : productSalesDuplicate) {
                if (productSales.size() >= size) {
                    break;
                }
                productSales.add(productSale);
            }
            return new ArrayList<ProductSale>(productSales);
        }

        return productDao.findProductSaleByPerformerce(params, orderBy, size);
    }

    public void setChannelProductService(ChannelProductService channelProductService) {
        this.channelProductService = channelProductService;
    }

    @Override
    public void updateProductStatus(ProductSale productSale, Code status, User operator) {
        if (Code.PRODUCT_SALE_STATUS_ONSHELF.equals(status.getId()) && !canBeOnShelf(productSale)) {
            throw new RuntimeException("该商品不能上架销售！");
        }
        saveProductSaleLog(productSale, null, status, operator);
        productSale.setSaleStatus(status);
        productSale.setUpdateTime(new Date());
        productDao.update(productSale);
//        channelProductService.update(productSale.getId());
    }

    /**
     * 记录商品修改日志
     * 
     * @param productSale
     * @param newPrice
     * @param newStatus
     * @param operator
     */
    private void saveProductSaleLog(ProductSale productSale, BigDecimal newPrice, Code newStatus, User operator) {
        ProductSaleLog log = new ProductSaleLog(productSale, newPrice == null ? productSale.getSalePrice() : newPrice,
                newStatus == null ? productSale.getSaleStatus() : newStatus, operator);
        if (null != productSale.getRemark() && !"".equals(productSale.getRemark())) {
            log.setRemark(productSale.getRemark());
        }
        productDao.save(log);
    }

    @Override
    public void updateCategory(Product product) {
        productDao.update(product);
        productDao.updateCategoryStatus(product.getId());

    }

    @Override
    public void saveManageGrade(ProductManageGrade grade) throws ProductSaleManageGradeException {
        if (!checkManageGrade(grade)) {
            throw new ProductSaleManageGradeException(grade, "非J级商品不能重复设置管理分级");
        }
        grade.getProductSale().getManageGrades().add(grade);
        productDao.save(grade);
    }

    private boolean checkManageGrade(ProductManageGrade grade) {
        ProductSale ps = grade.getProductSale();
        if (Code.PRODUCT_SALE_MANAGEGRADE_Z.equals(grade.getGrade().getId()) && ps.hasManageGrade(grade.getGrade())) {
            return false;
        }
        return true;
    }

    @Override
    public void updateManageGrade(ProductManageGrade grade) {
        productDao.update(grade);
    }

    @Override
    public ProductManageGrade getManageGrade(Long id) {
        return productDao.getProductManageGrade(id);
    }

    @Override
    public void deleteManageGrade(ProductManageGrade grade) {
        productDao.deleteManageGrade(grade);
    }

    @Override
    public int transferProductByCategory(Category category, Category targetCategory) throws CategoryException {
        try {
            checkTargetCategory(category, targetCategory);
            removeConflictCategory(category, targetCategory);
            int result = productDao.transferCategory4Product(targetCategory.getId(), category.getId());
            return result;
        } catch (ConstraintViolationException e) {
            throw new CategoryException(targetCategory, String.format("\r\n移动分类异常，" + "引起此异常的可能原因是分类下商品与已有分类冲突\r\n %s",
                    e.getMessage()));
        }
    }

    @Override
    public int transferPeoductByCategory(String[] productIds, Category category, Category targetCategory)
            throws CategoryException {
        try {
            List<String> productId = new ArrayList<String>();
            checkTargetCategory(category, targetCategory);
            for (String p : productIds) { // 判断商品是否属于起始分类
                ProductSale productSale = getProductSale((Long.valueOf(p)));
                Product product = productSale.getProduct();
                List<Category> categories = product.getCategories();
                boolean hasCate = false;
                String categoryName = "";
                for (Category c : categories) {
                    categoryName = categoryName + c.getName() + "|";
                    if (c.getId().equals(category.getId())) {
                        hasCate = true;
                        break;
                    }
                }
                if (!hasCate) {
                    throw new CategoryException(category, String.format("商品:[%s] 的分类不属于此分类,商品所属分类为:[%s]",
                            productSale.getName(), categoryName));
                }
                productId.add(product.getId().toString());
            }
            removeConflictCategory(category, targetCategory);
            int result = productDao.transferCategoryWithProduct(targetCategory.getId(), category.getId(), productId);
            return result;
        } catch (ConstraintViolationException e) {
            throw new CategoryException(targetCategory, String.format("\r\n移动分类异常，" + "引起此异常的可能原因是分类下商品与已有分类冲突\r\n %s",
                    e.getMessage()));
        }
    }

    private void checkTargetCategory(Category category, Category targetCategory) throws CategoryException {
        if (targetCategory.hasChildren()) {
            throw new CategoryException(targetCategory, String.format("目标分类不是末级分类，不能移动商品到此分类!"));
        }
        if (targetCategory.getId().equals(category.getId())) {
            throw new CategoryException(targetCategory, String.format("分类没有改变!"));
        }
    }

    /**
     * 移动分类下的商品前排除分类冲突的情况 冲突时会移除目标分类下的商品分类.
     * 
     * @param category
     * @param targetCategory
     */
    private void removeConflictCategory(Category category, Category targetCategory) {
        List<Map<String, Integer>> productIdsList = productDao.findConflictCategory(targetCategory.getId(),
                category.getId());
        for (Map<String, Integer> row : productIdsList) {
            int s = row.get("product");
            productDao.removeProductCategory(targetCategory.getId(), s);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean existsMessageIgnoreProduct(List<Long> productSales) {
        return productDao.existsMessageIgnoreProduct(productSales);
    }

    @Override
    public List<ProductSale> findByCategory(Map<String, Object> parameters, Pagination pagination) {
        return productDao.findByCategory(parameters, pagination);
    }

    @Override
    public void cancelPresaleProduct(ProductSale productSale, Employee employee) throws ProductException {
        //this.cancelPresaleProduct(productSale);
        if (productSale.getProduct().isComplex()) {
            throw new ProductException(productSale, String.format("不能直接取消套装书的预售!"));
        }

        ProductBooking booking = this.getProductBooking(productSale.getId());
        productSale.setSupplyType(new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL));
       // productSaleStockService.updateVirtualQuantity(booking.getDc(), productSale, 0);
		StockDocuments stockDocumentsVo = new StockDocuments();
		DateFormat format = new SimpleDateFormat("yyyymmddhhmmss");
		stockDocumentsVo.setType(EnumStockDocumentsType.PRESELL_STOCK);
		stockDocumentsVo.setDc(booking.getDc().getId());
		stockDocumentsVo.setCredence(format.format(new Date()).toString());
		stockDocumentsVo.setProductSale(productSale.getId());
		stockDocumentsVo.setStock(0);
		try {
			documentsService.documentProcessing(stockDocumentsVo);
		} catch (StockDocumentsException e) {
			new StockDocumentsException(stockDocumentsVo,String.format("不能直接取消套装书的预售!"));
		}

        ProductBookingLog log = new ProductBookingLog();
        log.setProductSale(productSale);
        log.setDescription("删除");
        log.setStockQuantity(productSale.getStockQuantity());
        log.setCreateDate(new Date());
        log.setStartDate(booking.getStartDate());
        log.setEndDate(booking.getEndDate());
        log.setDc(booking.getDc());
        log.setEmployee(employee);
        productSale.addBookingLog(log);
        productDao.removeProductBooking(productSale.getBooking());
        productDao.update(productSale);

        //预售商品为套装书的子商品
        Set<ProductSale> complexMasters = productSale.getComplexMasterList();
    	if(!CollectionUtils.isEmpty(complexMasters)) {
    		for(ProductSale complexMaster : complexMasters) {
    			Code code = getComplexSupplyType(complexMaster.getComplexItemList());
    	        //删除非预售商品的booking信息,并且booking不为空
    	        if (Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL.equals(code.getId()) && complexMaster.getBooking() != null) {
    	            productDao.removeProductBooking(complexMaster.getBooking());
    	            complexMaster.setBooking(null);
    	        } else if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(code.getId()) && complexMaster.getBooking() != null) {
    	            computeMasterBookingDate(complexMaster);
    	        }
    		}
    	}

    	//商品供应类型发生变化同步到渠道商品 
    	this.updateChannelSupplyType(productSale.getId());
    }

    @Override
    public List<ProductSale> getshoppingCombinations(Long id) {
        List<ProductSaleBundle> productSaleBundles = productSaleBundleDao.find(id);
        if (productSaleBundles != null && productSaleBundles.size() > 0) {
            List<ProductSale> productSales = new ArrayList<ProductSale>();
            for (int i = 0; i < productSaleBundles.size() && i < MagicNumber.THREE; i++) {
                ProductSaleBundle productSaleBundle = productSaleBundles.get(i);
                productSales.add(productSaleBundle.getProductSale());
            }
            return productSales;
        }
        // TODO 系统推荐购买组合
        return null;
    }

    public void update(ProductImageZoomTask task) {
        productDao.update(task);
    }

    public void save(ProductImageZoomTask task) {
        productDao.save(task);
    }

    public List<ProductImageZoomTask> findProductImageZoomTask(Pagination page) {
        return productDao.findProductImageZoomTask(page);
    }

    @Override
    public void save(ProductSaleStockLog productSaleStockLog) {
        this.productDao.save(productSaleStockLog);
    }

    @Override
    public void updateComplexProduct(ProductSale complex) throws ProductException {
        if (complex != null) {
            checkComplexProduct(complex);
            setupComplexProduct(complex, complex.getComplexItemList());
            update(complex);
            setupComplexBooking(complex);
        }
    }

	@Override
	public ProductMeta findProductMeta(String name, Integer category) {
		return productMetaDao.findProductMeta(name, category);
	}
	
	@Override
	public void recoverProductSaleStatus(ProductSale productSale, User operator) {
		Code code =  codeService.get(Code.PRODUCT_SALE_STATUS_ONSHELF);
        if (!canBeOnShelf(productSale)) {
            code = codeService.get(Code.PRODUCT_SALE_STATUS_OFFSHELF);
        }
        updateProductStatus(productSale, code, operator);
	}

	
	private void updateChannelSupplyType(Long productSaleId) {
        try {
            if (channelProductService != null) {
                channelProductService.update(productSaleId);
            } else {
                LOG.info("channelProductService is null");
            }
        } catch (Exception e) {
            LOG.error("updating productsale's supplytpe error ： " + e.getMessage(), e);
        }
    }

	@Override
	public List<ProductAuthor> findProductAuthors(Product product) {
		// TODO Auto-generated method stub
		return productDao.findProductAuthors(product);
	}
}