package com.winxuan.ec.service.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProductDao;
import com.winxuan.ec.dao.ProductSaleDao;
import com.winxuan.ec.dao.ProductSaleIncorrectStockDao;
import com.winxuan.ec.dao.StockLockRuleDao;
import com.winxuan.ec.dao.StockRuleDao;
import com.winxuan.ec.enums.StockOriginEnum;
import com.winxuan.ec.exception.ProductException;
import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.exception.StockDocumentsException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.documents.StockDocuments;
import com.winxuan.ec.model.order.CollectionItem;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.ComplexStock;
import com.winxuan.ec.model.product.MessageStock;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleIncorrectStock;
import com.winxuan.ec.model.product.ProductSaleLog;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.model.product.ProductSaleStockLog;
import com.winxuan.ec.model.product.StockLockRule;
import com.winxuan.ec.model.product.StockRule;
import com.winxuan.ec.model.product.StockSales;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.config.SystemConfigService;
import com.winxuan.ec.service.documents.DocumentsService;
import com.winxuan.ec.support.util.DateUtils;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.services.pss.enums.EnumStockDocumentsType;
import com.winxuan.services.pss.model.vo.ProductSaleStockVo;

/**
 * 
 * @author: HideHai
 * @date: 2013-7-25
 */
@Service("productSaleStockService")
@Transactional(rollbackFor = Exception.class)
public class ProductSaleStockServiceImpl implements ProductSaleStockService, Serializable {
	private static final long serialVersionUID = 3822502934852817046L;
	private static final float F_100 = 100.00F;

	private static final Log LOG = LogFactory.getLog(ProductSaleStockServiceImpl.class);

	@InjectDao
	private ProductDao productDao;

	@InjectDao
	private ProductSaleIncorrectStockDao productSaleIncorrectStockDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private StockRuleService stockRuleService;

	@Autowired(required = false)
	private ChannelProductService channelProductService;

	@Autowired
	private StockSalesService stockSalesService;

	@Autowired
	private StockLockRuleService stockLockRuleService;

	@Autowired
	private SystemConfigService systemConfigService;

	@Autowired
	private ChannelService channelService;

	@InjectDao
	private StockRuleDao stockRuleDao;

	@InjectDao
	private ProductSaleDao productSaleDao;

	@InjectDao
	private StockLockRuleDao stockLockRuleDao;
	
	@Autowired
	private DocumentsService documentsService;

	@Override
	public void updateQuantity(Code dc, ProductSale productSale, int updateStockQuantity, int saleStockQuantity)
			throws ProductSaleStockException {
		if (!Shop.WINXUAN_SHOP.equals(productSale.getShop().getId())) {
			throw new ProductSaleStockException(productSale, "不是文轩商品");
		}
		if (MagicNumber.ZERO == updateStockQuantity && MagicNumber.ZERO == saleStockQuantity) {
			return;
		}
		Product product = productSale.getProduct();
		if (product.isVirtual()) {
			throw new ProductSaleStockException(productSale, "不是实物商品");
		}
		if (product.isComplex()) {
			throw new ProductSaleStockException(productSale, "套装书不能直接更新库存，系统自动计算库存");
		}
		ProductSaleStock pss = productSale.getStockByDc(dc);
		if (null == pss) {
			pss = new ProductSaleStock(productSale, dc);
			productSale.addStock(pss);
		}
		pss.updateStock(updateStockQuantity);
		pss.updateSales(saleStockQuantity);
		this.stockRuleService.computeStock(productSale);
		productSale.setUpdateTime(new Date());
		this.productService.update(productSale);
		this.updateChannelStock(productSale.getId(), productSale.getAvalibleQuantity());
		this.updateComplexQuantity(dc, productSale);
	}

	private void updateComplexQuantity(Code dc, ProductSale productSale) {
		Set<ProductSale> complexList = productSale.getComplexMasterList();
		if (CollectionUtils.isNotEmpty(complexList)) {
			for (ProductSale complexs : complexList) {
				if (null != complexs && CollectionUtils.isNotEmpty(complexs.getComplexItemList())) {
					int stock = this.computeComplexQuantity(dc, complexs.getComplexItemList());
					Code supplyType = this.productService.getComplexSupplyType(complexs.getComplexItemList());
					complexs.setSupplyType(supplyType);
					ProductSaleStock pss = complexs.getStockByDc(dc);
					if (null == pss) {
						pss = new ProductSaleStock(complexs, dc);
						complexs.addStock(pss);
					}
					if (Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL.equals(complexs.getSupplyType().getId())) {
						pss.setStock(stock);
						pss.setVirtual(MagicNumber.ZERO);
					} else {
						if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(complexs.getSupplyType().getId())) {
							pss.setVirtual(stock);
						}
					}
					this.stockRuleService.computeStock(complexs);

					complexs.setUpdateTime(new Date());
					this.productService.update(complexs);
					this.saveProductSaleLog(complexs, complexs.getSalePrice(), complexs.getSaleStatus(), new Employee(
							Employee.SYSTEM));
					this.updateChannelStock(complexs.getId(), complexs.getAvalibleQuantity());
				}
			}
		}
	}

	private void updateChannelStock(Long productSaleId, int stock) {
		try {
			if (null != channelProductService) {
				channelProductService.updateStock(productSaleId, stock);
			} else {
				LOG.info("channelProductService is null");
			}
		} catch (Exception e) {
			LOG.error("Sending stock to channel : " + e.getMessage(), e);
		}
	}

	private void saveProductSaleLog(ProductSale productSale, BigDecimal newPrice, Code newStatus, User operator) {
		ProductSaleLog log = new ProductSaleLog(productSale, null == newPrice ? productSale.getSalePrice() : newPrice,
				null == newStatus ? productSale.getSaleStatus() : newStatus, operator);
		if (StringUtils.isNotBlank(productSale.getRemark())) {
			log.setRemark(productSale.getRemark());
		}
		this.productDao.save(log);
	}

	@Override
	@Deprecated
	public int quantityFillRate(Code dc, ProductSale productSale, int quantity) throws ProductSaleStockException {
		if (MagicNumber.ZERO == quantity) {
			throw new ProductSaleStockException(productSale, "复本量不能为零");
		}
		ProductSaleStock pss = productSale.getStockByDc(dc);
		if (null != pss) {
			if (pss.existIncorrectStock()) {
				throw new ProductSaleStockException(productSale, "不准确库存");
			}
			int avalibleQuantity = MagicNumber.ZERO;
			if (Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL.equals(productSale.getSupplyType().getId())) {
				avalibleQuantity = pss.getActualAvalibleQuantity();
			} else {
				avalibleQuantity = pss.getVirtualAvalibleQuantity();
			}
			if (avalibleQuantity <= MagicNumber.ZERO) {
				throw new ProductSaleStockException(productSale, "无可用量");
			}
			if (quantity <= avalibleQuantity) {
				return MagicNumber.TEN_THOUSAND;
			}
			float fq = Float.parseFloat(String.valueOf(quantity));
			float faq = Float.parseFloat(String.valueOf(avalibleQuantity));
			return Math.round((faq / fq) * MagicNumber.TEN_THOUSAND);
		}
		throw new ProductSaleStockException(productSale, "无DC信息");
	}

	@Override
	public int usableQuantity(Code dc, ProductSale productSale, int quantity) {
		ProductSaleStock pss = productSale.getStockByDc(dc);
		if (null != pss) {
			int result;
			int avalibleQuantity = getAvalibleQuantity(productSale, dc);
			result = quantity >= avalibleQuantity ? avalibleQuantity : quantity;
			return result;
		}
		LOG.info("DC (" + dc.getId() + ") : has no dc message!");
		return 0;
	}

	/**
	 * 根据商品供应类型获取可用量 正常商品，需要考虑不准确库存
	 * 
	 * @param productSale
	 * @return
	 */
	@Override
	public int getAvalibleQuantity(ProductSale productSale, Code dc) {
		int avalibleQuantity = MagicNumber.ZERO;
		ProductSaleStock pss = productSale.getStockByDc(dc);
		if (null != pss) {
			if (Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL.equals(productSale.getSupplyType().getId())) {
				avalibleQuantity = pss.getActualAvalibleQuantity();
				if (pss.existIncorrectStock()) {
					avalibleQuantity = MagicNumber.ZERO;
				}
			} else {
				avalibleQuantity = pss.getVirtualAvalibleQuantity();
			}
		}
		return avalibleQuantity;
	}

	@Override
	public Map<Code, Integer> quantityFillRate(ProductSale productSale, int quantity) {
		Set<ProductSaleStock> productSaleStocks = productSale.getProductSaleStocks();
		if (CollectionUtils.isNotEmpty(productSaleStocks)) {
			Map<Code, Integer> fillRate = new HashMap<Code, Integer>();
			for (ProductSaleStock pss : productSaleStocks) {
				try {
					fillRate.put(pss.getDc(), this.usableQuantity(pss.getDc(), productSale, quantity));
				} catch (Exception e) {
					LOG.info("DC (" + pss.getDc().getId() + ") : " + e.getMessage());
					fillRate.put(pss.getDc(), MagicNumber.ZERO);
					continue;
				}
			}
			return fillRate;
		}
		return null;
	}

	private void updateQuantity(Code dc, Long type, ProductSale productSale, int quantity)
			throws ProductSaleStockException {
		ProductSaleStock pss = productSale.getStockByDc(dc);
		int originalStock = null == pss ? MagicNumber.ZERO : pss.getStock();
		if (null == pss) {
			pss = new ProductSaleStock(productSale, dc);
			productSale.addStock(pss);
		}
		if (Code.PRODUCT_STOCK_TYPE_ACTUAL.equals(type)) {
			pss.setStock(quantity);
		} else {
			pss.setVirtual(quantity);
		}
		if (Code.PRODUCT_STOCK_TYPE_ACTUAL.equals(type)) {					//如果更新实物库存则处理不准确库存
			List<Map<String, Integer>> ids = this.productSaleIncorrectStockDao.findIncorrectStock(dc.getId(),
					productSale.getId());
			if (CollectionUtils.isNotEmpty(ids)) {
				ProductSaleIncorrectStock productSaleIncorrectStock = this.productSaleIncorrectStockDao.get(Long
						.valueOf(ids.get(MagicNumber.ZERO).get("id")));
				if (quantity > productSaleIncorrectStock.getStock()) {	//更新数量大于不准确库存数量，取消不准确库存
					this.productSaleIncorrectStockDao.updateIncorrectStockChanged(dc.getId(), productSale.getId());	//最近一次不准确日志
					pss.setIncorrect(0);
				}
			}
		}
		if (!productSale.getProduct().isComplex()
				&& Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(productSale.getSupplyType().getId())) {
			if (null != productSale.getBooking() && null != productSale.getBooking().getDc()
					&& dc.getId().equals(productSale.getBooking().getDc().getId()) && pss.getStock() > pss.getVirtual()) {
				try {
					productService.cancelPresaleProduct(productSale, new Employee(Employee.SYSTEM));
				} catch (ProductException e) {
					throw new ProductSaleStockException(productSale, e.getMessage());
				}
				pss.setVirtual(MagicNumber.ZERO);
			}
		}
		this.stockRuleService.computeStock(productSale);
		productSale.setUpdateTime(new Date());
		this.productService.update(productSale);
		this.saveProductSaleStockLog(productSale, dc, originalStock, quantity, pss.getSales());
		this.updateChannelStock(productSale.getId(), productSale.getAvalibleQuantity());
		this.updateComplexQuantity(dc, productSale);
	}

	@Override
	public void updateActualQuantity(Code dc, ProductSale productSale, int quantity) throws ProductSaleStockException {
		this.updateQuantity(dc, Code.PRODUCT_STOCK_TYPE_ACTUAL, productSale, quantity);
	}

	@Override
	public void updateVirtualQuantity(Code dc, ProductSale productSale, int quantity) throws ProductSaleStockException {
		this.updateQuantity(dc, Code.PRODUCT_STOCK_TYPE_VIRTUAL, productSale, quantity);
	}

	@Override
	public int computeComplexQuantity(Code dc, Set<ProductSale> complexProductSales) {
		int avalibleQuantity = MagicNumber.NEGATIVE_ONE;
		for (ProductSale ps : complexProductSales) {
			ProductSaleStock pss = ps.getStockByDc(dc);
			if (null == pss) {
				return MagicNumber.ZERO;
			}
			int quantity = MagicNumber.ZERO;
			if (Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL.equals(ps.getSupplyType().getId())) {
				quantity = pss.getActualAvalibleQuantity();
			} else {
				if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(ps.getSupplyType().getId())) {
					quantity = pss.getVirtualAvalibleQuantity();
				}
			}
			if (avalibleQuantity == MagicNumber.NEGATIVE_ONE) {
				avalibleQuantity = quantity;
			} else {
				avalibleQuantity = avalibleQuantity < quantity ? avalibleQuantity : quantity;
			}
			if (MagicNumber.ZERO >= avalibleQuantity) {
				return MagicNumber.ZERO;
			}
		}
		int stock = avalibleQuantity + ProductSale.COMPLEX_BASE_STOCK;
		stock = stock < MagicNumber.ZERO ? MagicNumber.ZERO : stock;
		return stock;
	}

	@Override
	public void updateQuantityByWms(Code dc, ProductSale productSale, int quantity) throws ProductSaleStockException {
		this.updateActualQuantity(dc, productSale, quantity);
	}

	private void saveProductSaleStockLog(ProductSale productSale, Code dc, int originalStock, int quantity, int sales) {
		ProductSaleStockLog pssl = new ProductSaleStockLog();
		pssl.setProductSale(productSale);
		pssl.setDc(dc);
		pssl.setOriginalStock(originalStock);
		pssl.setStock(quantity);
		pssl.setSales(sales);
		pssl.setUpdateTime(new Date());
		this.productService.save(pssl);
	}

	@Override
	public void saveIncorrectStock(Code dc, ProductSale productSale) {
		ProductSaleStock pss = productSale.getStockByDc(dc);
		if (null != pss && pss.getStock() < MagicNumber.TEN) {
			pss.setIncorrect(pss.getStock());
			this.saveProductSaleIncorrectStock(pss, productSale);
			this.stockRuleService.computeStock(productSale);
			this.updateChannelStock(productSale.getId(), productSale.getAvalibleQuantity());
		} else {
			LOG.info("ProductSale(" + productSale.getId() + ")没有DC (" + dc.getId() + ")信息，或者此DC下库存大于等于10");
		}
	}

	private void saveProductSaleIncorrectStock(ProductSaleStock productSaleStock, ProductSale productSale) {
		if (!this.productSaleIncorrectStockDao.existIncorrectStock(productSaleStock.getDc().getId(),
				productSale.getId())) {
			ProductSaleIncorrectStock psis = new ProductSaleIncorrectStock();
			psis.setDc(productSaleStock.getDc());
			psis.setProductSale(productSale);
			psis.setChanged(false);
			psis.setStock(productSaleStock.getStock());
			psis.setCreateTime(new Date());
			this.productSaleIncorrectStockDao.save(psis);
		}
	}

	public void setChannelProductService(ChannelProductService channelProductService) {
		this.channelProductService = channelProductService;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String parseStockInfo4Json(Set<ProductSaleStockVo> productSaleStockVos) {
		JSONArray pAllJson = new JSONArray();
		if (CollectionUtils.isNotEmpty(productSaleStockVos)) {
			JSONObject pJson = new JSONObject();
			for (ProductSaleStockVo pssv : productSaleStockVos) {
				pJson.put("dc", pssv.getDc());
				pJson.put("stock", pssv.getStock());
				pJson.put("virtual", pssv.getVirtual());
				pJson.put("sale", pssv.getSales());
				pAllJson.add(pJson);
			}
		}
		return pAllJson.toString();
	}

	@Override
	public void save(ProductSaleStock productSaleStock) {
		productDao.save(productSaleStock);
	}

	@Override
	public int getSaleStockByDc(ProductSale productSale) {
		int stock = 0;
		boolean isBooking = Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(productSale.getSupplyType().getId());
		if (isBooking) {
			for (ProductSaleStock productSaleStock : productSale.getProductSaleStocks()) {
				stock = stock + productSaleStock.getVirtualAvalibleQuantity();
			}
		} else {
			for (ProductSaleStock productSaleStock : productSale.getProductSaleStocks()) {
				stock = stock + productSaleStock.getActualAvalibleQuantity();
			}
		}
		return stock;
	}

	@Override
	public int getMaxSaleStockByDc(ProductSale productSale) {
		boolean isBooking = Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(productSale.getSupplyType().getId());
		List<Integer> stockList = new ArrayList<Integer>();
		if (isBooking) {
			for (ProductSaleStock productSaleStock : productSale.getProductSaleStocks()) {
				stockList.add(productSaleStock.getVirtualAvalibleQuantity());
			}
		} else {
			for (ProductSaleStock productSaleStock : productSale.getProductSaleStocks()) {
				stockList.add(productSaleStock.getActualAvalibleQuantity());
			}
		}
		Collections.sort(stockList);
		return stockList.size() > 0 ? stockList.get(stockList.size() - 1) : 0;
	}

	@Override
	public void subStock(ProductSale productSale, int purchaseQuantity) {

		// 判断储配方式，虚拟
		productSale = productService.getProductSale(productSale.getId());
		if (Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_PLATFORM
				.equals(productSale.getStorageType().getId())
				|| Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_SELLER.equals(productSale.getStorageType()
						.getId()) || productSale.getProduct().isVirtual()) {
			if (purchaseQuantity >= productSale.getStockQuantity()) {
				if (Code.PRODUCT_SALE_STATUS_ONSHELF.equals(productSale.getSaleStatus().getId())) {
					productSale.setSaleStatus(new Code(Code.PRODUCT_SALE_STATUS_OFFSHELF));
				}
				productSale.setStockQuantity(0);
			} else {
				productSale.setStockQuantity(productSale.getStockQuantity() - purchaseQuantity);
			}
			productService.update(productSale);
		} else {
			LOG.info(productSale.getId() + " can't called the current method");
		}

	}

	@Override
	public void addStockSales(OrderItem orderItem) {
		ProductSale productSale = orderItem.getProductSale();

		Order order = orderItem.getOrder();
		String orderId = order.getId();
		OrderDistributionCenter odc = order.getDistributionCenter();
		if (odc.getDcDest() == null) {
			LOG.info(order.getId() + " 没有匹配到仓库！");
			return;
		}
		createStockSales(odc.getDcDest(), orderId, productSale, orderItem.getPurchaseQuantity(),
				Code.STOCK_SALES_TYPE_ORDER);
		if (CollectionUtils.isNotEmpty(orderItem.getCollectionItemList())) {
			for (CollectionItem ci : orderItem.getCollectionItemList()) {
				createStockSales(ci.getFromDc(), orderId, productSale, ci.getCollectQuantity(),
						Code.STOCK_SALES_TYPE_COLLECT);
			}
		}
		updateProductSale(productSale);
	}

	private void createStockSales(Code dc, String orderId, ProductSale productSale, int sales, Long type) {
		Long productSaleId = productSale.getId();

		if (stockSalesService.get(orderId, productSaleId, dc) == null) {
			StockSales ss = new StockSales();
			ss.setDc(dc);
			ss.setOrderId(orderId);
			ss.setProductSaleId(productSaleId);
			ProductSaleStock productSaleStock = getStockByDc(productSale, dc);
			ss.setProductSaleStock(productSaleStock);
			ss.setSales(sales);
			ss.setType(new Code(type));
			stockSalesService.save(ss);

//			productSaleStock.updateSales(sales);
			StockDocuments stockDocuments = new StockDocuments(orderId, dc.getId(), productSaleId, EnumStockDocumentsType.ORDER_CREATE);
			stockDocuments.setOccupancy(sales);
			try {
				documentsService.documentProcessing(stockDocuments);
			} catch (StockDocumentsException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void subStockSales(OrderItem orderItem) {
		ProductSale productSale = orderItem.getProductSale();

		Order order = orderItem.getOrder();
		String orderId = order.getId();
		OrderDistributionCenter odc = order.getDistributionCenter();
		if (odc.getDcDest() == null) {
			return;
		}
		OrderConsignee oc = order.getConsignee();
		Long productSaleId = productSale.getId();
		StockSales stockSales = stockSalesService.get(orderId, productSaleId, odc.getDcDest());
		if (stockSales == null) {
			return;
		}

//		ProductSaleStock pss = stockSales.getProductSaleStock();
		if (Code.STOCK_SALES_STSTUS_TAKE_UP.equals(stockSales.getStatus().getId())) {
			stockSales.setStatus(new Code(Code.STOCK_SALES_STSTUS_RELEASE));
			stockSalesService.update(stockSales);
/*			
			pss.updateSales((0 - stockSales.getSales()));
			pss.updateStock((0 - orderItem.getDeliveryQuantity()));

			if (pss.getVirtual() > 0) {
				pss.updateVirtual((0 - orderItem.getDeliveryQuantity()));
			}

			if (pss.getActualAvalibleQuantity() > 0) {
				Long status = order.getProcessStatus().getId();
				if ((Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG.equals(status) && orderItem.getPurchaseQuantity() > orderItem
						.getDeliveryQuantity())
						|| (Code.OUT_OF_STOCK_OPTION_SEND.equals(oc.getOutOfStockOption().getId()) && Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL
								.equals(status))) {
					saveIncorrectStock(odc.getDcDest(), productSale);	//不准确库存记录
				}
			}
*/			
			Long status = order.getProcessStatus().getId();
			StockDocuments stockDocuments = new StockDocuments(orderId, odc.getDcDest().getId(), productSaleId);
			stockDocuments.setOccupancy(stockSales.getSales());

			if(status.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED) || status.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)){
				stockDocuments.setType(EnumStockDocumentsType.ORDER_DELIVERY);
				stockDocuments.setStock(orderItem.getDeliveryQuantity());
			}else{
				if(status.equals(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL) && Code.OUT_OF_STOCK_OPTION_SEND.equals(oc.getOutOfStockOption().getId())){
					stockDocuments.setType(EnumStockDocumentsType.STOCKOUT_CANCEL);
				}else{
					if(status.equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL)){
						stockDocuments.setType(EnumStockDocumentsType.INTERCEPT_CANCEL);
						stockDocuments.setStock(orderItem.getDeliveryQuantity());
					}else{
						stockDocuments.setType(EnumStockDocumentsType.ORDER_CANCEL);
					}
				}
			}
			try {
				documentsService.documentProcessing(stockDocuments);
			} catch (StockDocumentsException e) {
				throw new RuntimeException(e);
			}
		}

		updateProductSale(productSale);
	}

	private void updateProductSale(ProductSale productSale) {
		stockRuleService.computeStock(productSale);
		productService.update(productSale);
		updateComplexQuantity(productSale);
		updateChannelStock(productSale.getId(), productSale.getAvalibleQuantity());
	}

	@Override
	public void subStockSales(CollectionItem collectionItem) {

	}

	@Override
	public int getStockFromZq(long productSaleId, Code dc) {
		return 0;
	}

	private int getComplexStock(ProductSale complexs) {
		Set<Code> locations = new HashSet<Code>();
		if (CollectionUtils.isNotEmpty(complexs.getComplexItemList())) {
			for (ProductSale ps : complexs.getComplexItemList()) {
				for (ProductSaleStock pss : ps.getProductSaleStocks()) {
					if (pss != null) {
						locations.add(pss.getDc());
					}
				}
			}
		}
		int stock = 0;
		for (Code dc : locations) {
			List<Integer> stockList = new ArrayList<Integer>();
			for (ProductSale ps : complexs.getComplexItemList()) {
				ProductSaleStock pss = ps.getStockByDc(dc);
				if (pss == null) {
					stockList.add(0);
					break;
				} else {
					// stockList.add(pss.getVirtualAvalibleQuantity() > 0 ?
					// pss.getVirtualAvalibleQuantity():
					// pss.getActualAvalibleQuantity());
					stockList.add(getAvalibleQuantity(ps, dc));
				}
			}
			Collections.sort(stockList);
			if (CollectionUtils.isNotEmpty(stockList)) {
				int avalibleQuantity = stockList.get(0) + ProductSale.COMPLEX_BASE_STOCK;
				stock = stock + (avalibleQuantity > 0 ? avalibleQuantity : 0);
			}
		}
		return stock > 0 ? stock : 0;
	}

	private void updateComplexQuantity(ProductSale productSale) {
		Set<ProductSale> complexList = productSale.getComplexMasterList();
		if (CollectionUtils.isNotEmpty(complexList)) {
			for (ProductSale complexs : complexList) {
				if (CollectionUtils.isNotEmpty(complexs.getComplexItemList())) {
					int stock = getComplexStock(complexs);
					if (stock <= 0) {
						if (Code.PRODUCT_SALE_STATUS_ONSHELF.equals(complexs.getSaleStatus().getId())) {
							complexs.setSaleStatus(new Code(Code.PRODUCT_SALE_STATUS_OFFSHELF));
						}
						complexs.setStockQuantity(0);
					} else {
						complexs.setStockQuantity(stock);
						if (Code.PRODUCT_SALE_STATUS_OFFSHELF.equals(complexs.getSaleStatus().getId())) {
							List<Long> onSaleStatus = new ArrayList<Long>();
							for (ProductSale ps : complexs.getComplexItemList()) {
								if (Code.PRODUCT_SALE_STATUS_ONSHELF.equals(ps.getSaleStatus().getId())) {
									onSaleStatus.add(ps.getSaleStatus().getId());
								}

							}
							if (onSaleStatus.size() == complexs.getComplexItemList().size()) {
								complexs.setSaleStatus(new Code(Code.PRODUCT_SALE_STATUS_ONSHELF));
							}
						}
					}
					productService.update(complexs);
					updateChannelStock(complexs.getId(), complexs.getAvalibleQuantity());
				}
			}
		}
	}

	private ProductSaleStock getStockByDc(ProductSale productSale, Code dc) {
		ProductSaleStock pss = productSale.getStockByDc(dc);
		if (pss == null) {
			pss = new ProductSaleStock(productSale, dc);
			productSale.addStock(pss);
			productDao.save(pss);
		}
		return pss;
	}

	@Override
	public ComplexStock getStock(StockOriginEnum origin, long channelId, long productSaleId) {
		ComplexStock complexStock = getStock(channelId, productSaleId, null);
		complexStock.setOrigin(origin);
		return complexStock;
	}

	private ComplexStock getStock(long channelId, long productSaleId, Long orderCustomerId) {
		ProductSale productSale = productDao.getProductSale(productSaleId);

		ComplexStock complexStock;
		Long saleStatus = productSale.getSaleStatus().getId();
		if (Code.PRODUCT_SALE_STATUS_DELETED.equals(saleStatus) || Code.PRODUCT_SALE_STATUS_EC_STOP.equals(saleStatus)
				|| Code.PRODUCT_SALE_STATUS_SAP_STOP.equals(saleStatus)) {
			complexStock = new ComplexStock();
			complexStock.setStock(0);
			complexStock.setDcStocks(getDcStocks(productSale));
			return complexStock;
		}

		if (!systemConfigService.lockStockSwitchOpen()) {
			complexStock = new ComplexStock();
			complexStock.setStock(stockRuleService.computeStock(channelId, productSale));
			complexStock.setDcStocks(getDcStocks(productSale));
			return complexStock;
		}

		StockLockRule stockLockRule;
		if (null != orderCustomerId) {
			stockLockRule = stockLockRuleService.findEffective(channelId, productSaleId, orderCustomerId);
		} else {
			stockLockRule = stockLockRuleService.findEffective(channelId, productSaleId);
		}
		if (null != stockLockRule) {
			complexStock = getComplexStock(stockLockRule);
		} else {
			complexStock = new ComplexStock();
			complexStock.setStock(stockRuleService.computeStock(channelId, productSale));
		}

		complexStock.setDcStocks(getDcStocks(productSale));
		return complexStock;
	}

	private Map<Long, Integer> getDcStocks(ProductSale productSale) {
		Map<Long, Integer> dcStocks = new HashMap<Long, Integer>();
		Set<ProductSaleStock> productSaleStocks = productSale.getProductSaleStocks();

		if (CollectionUtils.isNotEmpty(productSaleStocks)) {
			for (ProductSaleStock pss : productSaleStocks) {
				if (Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL.equals(productSale.getSupplyType().getId())) {
					if (!pss.existIncorrectStock()) {
						dcStocks.put(pss.getDc().getId(), pss.getActualAvalibleQuantity());
					} else {
						dcStocks.put(pss.getDc().getId(), 0);
					}
				} else {
					dcStocks.put(pss.getDc().getId(), pss.getVirtualAvalibleQuantity());
				}
			}
		}
		return dcStocks;
	}

	private ComplexStock getComplexStock(StockLockRule stockLockRule) {
		ComplexStock complexStock = new ComplexStock();
		complexStock.setActivityType(stockLockRule.getLockType());
		complexStock.setActivityStock(stockLockRule.getRealLock());
		complexStock.setStock(stockLockRule.getRealLock());
		return complexStock;
	}

	@Override
	public ComplexStock getStock(StockOriginEnum origin, long channelId, long orderCustomerId, long productSaleId) {
		ComplexStock complexStock = getStock(channelId, productSaleId, orderCustomerId);
		complexStock.setOrigin(origin);
		return complexStock;
	}

	@Override
	public Map<Long, Integer> getStock(StockOriginEnum origin, long channelId, List<Long> productSaleIds, long dcId) {
		Map<Long, Integer> stocks = new HashMap<Long, Integer>();
		if (CollectionUtils.isEmpty(productSaleIds)) {
			return stocks;
		}

		StockRule stockRuleUsual = stockRuleService.getStockRule(channelId, Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL);
		StockRule stockRuleBooking = stockRuleService.getStockRule(channelId, Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING);
		if (null == stockRuleUsual && null == stockRuleBooking) {
			return stocks;
		}

		String path = (null != stockRuleUsual) ? stockRuleUsual.getChannel().getPath() : null;
		path = (null == path) ? ((null != stockRuleBooking) ? stockRuleBooking.getChannel().getPath() : null) : path;
		int usualPercent = (null != stockRuleUsual) && (null != stockRuleUsual.getStockRuleDc(dcId)) ? stockRuleUsual.getPercent() : 0;
		int bookingPercent = (null != stockRuleBooking) && (null != stockRuleBooking.getStockRuleDc(dcId)) ? stockRuleBooking.getPercent() : 0;
		List<Map<String, Object>> productSaleStocks = productSaleDao.getProductSaleStockByDc(productSaleIds, dcId);
		List<Map<String, Object>> occupancyStocks = productSaleIncorrectStockDao.getOccupancyStocks(productSaleIds);

		List<Map<String, Object>> lockedSumAvailableStocks = null;
		if (systemConfigService.lockStockSwitchOpen()) {
			lockedSumAvailableStocks = stockLockRuleDao.getLockedSumAvailableStocks(productSaleIds);
		}

		for (Long productSaleId : productSaleIds) {
			stocks.put(productSaleId, 0);
		}

		if (CollectionUtils.isNotEmpty(productSaleStocks)) {
			for (Map<String, Object> saleStock : productSaleStocks) {
				if (path.startsWith(StockRuleServiceImpl.SUPPLY_PATH)
						&& Boolean.valueOf(saleStock.get("complex").toString())) {
					continue;
				}

				Long saleStatus = Long.valueOf(saleStock.get("salestatus").toString());
				if (Boolean.valueOf(saleStock.get("stockstatus").toString())
						&& (!Code.PRODUCT_SALE_STATUS_DELETED.equals(saleStatus)
								&& !Code.PRODUCT_SALE_STATUS_EC_STOP.equals(saleStatus) && !Code.PRODUCT_SALE_STATUS_SAP_STOP.equals(saleStatus))) {
					Long productSaleId = Long.valueOf(saleStock.get("id").toString());
					int tmpStock = Integer.parseInt(saleStock.get("stock").toString());
					int sales = Integer.parseInt(saleStock.get("sales").toString());
					int occupancyStock = getOccupancyStock(occupancyStocks, productSaleId);

					tmpStock = (tmpStock > sales) ? (tmpStock - sales) : 0;
					tmpStock = (tmpStock > occupancyStock) ? (tmpStock - occupancyStock) : 0;

					if (systemConfigService.lockStockSwitchOpen()) {
						int lockedSumAvailableStock = getLockedSumAvailableStock(lockedSumAvailableStocks, productSaleId);
						tmpStock = (tmpStock > lockedSumAvailableStock) ? (tmpStock - lockedSumAvailableStock) : 0;
					}

					if (0 >= tmpStock) {
						continue;
					}

					if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(Long.valueOf(saleStock.get("supplytype").toString()))) {
						tmpStock = (int) Math.floor(tmpStock * bookingPercent / F_100);
					} else {
						tmpStock = (int) Math.floor(tmpStock * usualPercent / F_100);
					}

					tmpStock = (0 >= tmpStock) ? 0 : tmpStock;
					stocks.put(productSaleId, tmpStock);
				}
			}
		}

		return stocks;
	}

	private int getOccupancyStock(List<Map<String, Object>> occupancyStocks, Long productSaleId) {
		if (null != productSaleId && CollectionUtils.isNotEmpty(occupancyStocks)) {
			for (Map<String, Object> occupancyStock : occupancyStocks) {
				if (productSaleId.equals(Long.valueOf(occupancyStock.get("productsale").toString()))) {
					return Integer.parseInt(occupancyStock.get("num").toString());
				}
			}
		}
		return 0;
	}

	private int getLockedSumAvailableStock(List<Map<String, Object>> lockedSumAvailableStocks, Long productSaleId) {
		if (null != productSaleId && CollectionUtils.isNotEmpty(lockedSumAvailableStocks)) {
			for (Map<String, Object> lockedSumAvailableStock : lockedSumAvailableStocks) {
				if (productSaleId.equals(Long.valueOf(lockedSumAvailableStock.get("productsale").toString()))) {
					return Integer.parseInt(lockedSumAvailableStock.get("num").toString());
				}
			}
		}
		return 0;
	}

	@Override
	public int computeRealLock(StockLockRule stockLockRule) {
		ProductSale productSale = productDao.getProductSale(stockLockRule.getProductSale());
		return computeLockStock(productSale, stockLockRule);
	}

	private int computeLockStock(ProductSale productSale, StockLockRule stockLockRule) {
		if (null == stockLockRule) {
			return 0;
		}

		int availableStock = getAvailableStock(productSale);
		if (availableStock <= 0) {
			return 0;
		}

		if (DateUtils.validateTime(stockLockRule.getBeginTime(), stockLockRule.getEndTime())) {
			int realLock = 0;
			if (StockLockRule.LOCK_TYPE_QUANTITY == stockLockRule.getLockType()) {
				realLock = availableStock >= stockLockRule.getLockStock() ? stockLockRule.getLockStock()
						: availableStock;
			} else {
				if (StockLockRule.LOCK_TYPE_FACTOR == stockLockRule.getLockType()) {
					if (null != stockLockRule.getLockFactor()
							&& (stockLockRule.getLockFactor() < 0 || stockLockRule.getLockFactor() > 100)) {
						LOG.warn("库存锁定规则(" + stockLockRule.getId() + ")" + "的锁定系数不是有效的百分比,无法计算锁定库存.");
						return 0;
					}
					realLock = (int) Math.floor(availableStock * stockLockRule.getLockFactor() / F_100);
				}
			}

			return realLock;
		}

		return 0;
	}

	private int getAvailableStock(ProductSale productSale) {
		if (CollectionUtils.isEmpty(productSale.getProductSaleStocks())) {
			return 0;
		}

		int quantity = 0;
		for (ProductSaleStock pss : productSale.getProductSaleStocks()) {
			if (null != pss
					&& (Code.DC_8A17.equals(pss.getDc().getId()) || Code.DC_D818.equals(pss.getDc().getId()) || Code.DC_D819
							.equals(pss.getDc().getId()))) {
				if (Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL.equals(productSale.getSupplyType().getId())) {
					if (!pss.existIncorrectStock()) {
						quantity += pss.getActualAvalibleQuantity();
					}
				} else {
					if (Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(productSale.getSupplyType().getId())) {
						quantity += pss.getVirtualAvalibleQuantity();
					}
				}
			}
		}

		long tmpLong = productSaleIncorrectStockDao.getStockOccupancy(productSale.getId());
		int occupancy = tmpLong > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) tmpLong;

		return quantity > occupancy ? (quantity - occupancy) : 0;
	}

	@Override
	public void afreshComputeRealLockByFactor(ProductSale productSale) {
		if (!systemConfigService.lockStockSwitchOpen()) {
			return;
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productSaleId", productSale.getId());
		List<StockLockRule> slrs = stockLockRuleService.findEffective(parameters);
		if (CollectionUtils.isEmpty(slrs)) {
			return;
		}

		for (StockLockRule slr : slrs) {
			if (StockLockRule.LOCK_TYPE_FACTOR == slr.getLockType()) {
				int realLock = computeLockStock(productSale, slr);
				if (realLock > slr.getRealLock()) {
					int tmpLock = realLock - slr.getRealLock();
					slr.setRealLock(realLock);
					slr.setUpdateTime(new Date());
					stockLockRuleService.update(slr);
					stockRuleService.computeStock(productSale);
					sendChannelStock(new MessageStock(StockOriginEnum.INCREMENT, productSale.getId(), slr.getChannel()
							.getId(), tmpLock));
				}
			}
		}
	}

	@Override
	public void updateLockSales(Channel channel, ProductSale productSale, int sales) {
		if (!systemConfigService.lockStockSwitchOpen()) {
			return;
		}

		stockLockRuleService.updateSales(sales, channel.getId(), productSale.getId());
	}

	@Override
	public void updateLockSales(Channel channel, long orderCustomerId, ProductSale productSale, int sales) {
		if (!systemConfigService.lockStockSwitchOpen()) {
			return;
		}

		stockLockRuleService.updateSales(sales, channel.getId(), productSale.getId(), orderCustomerId);
	}

	@Override
	public void sendChannelStock(MessageStock messageStock) {
		try {
			if (null != channelProductService) {
				channelProductService.updateStock(messageStock);
			} else {
				LOG.info("channelProductService is null");
			}
		} catch (Exception e) {
			LOG.error("Sending stock to channel : " + e.getMessage(), e);
		}
	}

}
