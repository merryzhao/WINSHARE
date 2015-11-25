/*
orderAddProductSale * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.product;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.CategoryException;
import com.winxuan.ec.exception.ProductException;
import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.exception.StockDocumentsException;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.category.McCategory;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcRule;
import com.winxuan.ec.model.documents.StockDocuments;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductBooking;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.model.product.ProductManageGrade;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductMetaEnum;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.model.product.ProductTransient;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.category.McCategoryService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.documents.DocumentsService;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.service.util.ImageService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.util.POIUtil;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.DateUtils;
import com.winxuan.services.pcs.model.vo.CodeVo;
import com.winxuan.services.pcs.model.vo.ProductSaleVo;
import com.winxuan.services.pcs.service.ProductSaleService;
import com.winxuan.services.pss.enums.EnumStockDocumentsType;
import com.winxuan.services.pss.model.vo.ProductSaleStockVo;

/**
 * 商品
 * 
 * @author HideHai
 * @version 1.0,2011-7-13
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	private static final short VIRTUAL=1;//虚拟套装
    private Logger log = LoggerFactory.getLogger(getClass());
   
    
    @Autowired
    private ProductService productService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private McCategoryService mcCategoryService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductSaleStockService productSaleStockService;

    @Autowired
    private DcService dcService;
    
	@Resource(name="documentsService")
	private DocumentsService documentsService;
	
	@Autowired
	private ProductSaleService pSService;

    /**
     * 根据ID获取商品对象
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id, @MyInject Customer customer) {
        Product product = productService.get(id);
        ModelMap modelMap = new ModelMap();
        if (product == null) {
            modelMap.put("id", id);
            return new ModelAndView("/product/view_notFound", modelMap);
        }
        modelMap.put(ControllerConstant.PRODUCT, product);
        return new ModelAndView("/product/view", modelMap);
    }

    /**
     * 为商品信息查询画面准备数据
     * 
     * @param
     * @return
     */
    @RequestMapping(value = "/prepare", method = RequestMethod.GET)
    public ModelAndView prepareForProduct() {
        ModelMap modelMap = new ModelMap();
        modelMap.put(ControllerConstant.SALESTATUS, codeService.get(ControllerConstant.PRODUCTSALESTATUS).getChildren());
        modelMap.put("shops", shopService.find(null, null));
        modelMap.put("store", codeService.get(Code.STORAGE_AND_DELIVERY_TYPE));
        modelMap.put("productQueryForm", new ProductQueryForm());
        return new ModelAndView(ControllerConstant.LISTURL, modelMap);
    }

	/**
	 * 商品信息查询
	 * 
	 * @param
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/productList", method = RequestMethod.POST)
	public ModelAndView listProduct(@Valid ProductQueryForm productQueryForm,
			BindingResult result, @MyInject Pagination pagination,
			HttpServletRequest request) throws ParseException {
		// 如果验证通过
		if (!result.hasErrors() && productQueryForm.canQuery()) {
			// 构建map
			Map<String, Object> parameters = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(productQueryForm.getCodingContent())) {
				String[] strs = productQueryForm.getCodingContent().split(
						ControllerConstant.SPLITCHAR);
				if ("productBarcodes".equals(productQueryForm.getCoding())
						|| ControllerConstant.OUTERIDS.equals(productQueryForm
								.getCoding())) {
					List<String> strings = new ArrayList<String>();
					for (int i = 0; i < strs.length; i++) {
						if (!StringUtils.isBlank(strs[i])) {
							strings.add(strs[i].trim());
						}
					}
					parameters.put(productQueryForm.getCoding(), strings);
				} else {
					List<Long> longs = new ArrayList<Long>();
					for (int i = 0; i < strs.length; i++) {
						if (!StringUtils.isBlank(strs[i])) {
							try {
								longs.add(NumberUtils.createLong(strs[i].trim()));
							} catch (Exception e) {
								continue;
							}
						}
					}
					parameters.put(productQueryForm.getCoding(), longs);
				}
			}
			// 其他
			parameters.put(ControllerConstant.PRODUCTNAME,
					productQueryForm.getProductNameQuery());
			parameters
					.put("productAuthor", productQueryForm.getProductAuthor());
			parameters.put("productMcCategory",
					productQueryForm.getProductMcCategory());
			parameters.put("sellerName", productQueryForm.getSellerName());
			parameters.put(ControllerConstant.SALESTATUS,
					productQueryForm.getStatus());
			parameters.put("complex", productQueryForm.isBooleanComplex() == null ? productQueryForm.isBooleanComplex() : productQueryForm.kindComplex());
			parameters.put("shopId", productQueryForm.getShopId());
			parameters.put("storageType", productQueryForm.getStorageType());
			if (!StringUtils.isEmpty(productQueryForm.getManufacturer())) {
				parameters.put("manufacturer",
						"%" + productQueryForm.getManufacturer() + "%");
			}
			parameters.put("stockQuantityMin",
					productQueryForm.getStockNumberMin());
			parameters.put("stockQuantityMax",
					productQueryForm.getStockNumberMax());
			if (productQueryForm.getCategory() != null) {
				parameters.put("categoryCode",
						categoryService.get(productQueryForm.getCategory())
								.getCode());
			}
			parameters.put("hasPicture", productQueryForm.getBooleanPicture());
			parameters
					.put("supplyTypes", productQueryForm.getLongSupplyTypes());
			String productionStartDate = productQueryForm
					.getProductionStartDate();
			parameters.put(
					"productionStartDate",
					StringUtils.isEmpty(productionStartDate) ? null : DateUtils
							.parserStringToDate(productionStartDate,
									DateUtils.SHORT_DATE_FORMAT_STR));
			String productionEndDate = productQueryForm.getProductionEndDate();
			parameters.put(
					"productionEndDate",
					StringUtils.isEmpty(productionEndDate) ? null : DateUtils
							.parserStringToDate(productionEndDate,
									DateUtils.SHORT_DATE_FORMAT_STR));
			parameters.put("discountMin", productQueryForm.getDiscountMin());
			parameters.put("discountMax", productQueryForm.getDiscountMax());
			parameters.put("listpriceMin", productQueryForm.getListpriceMin());
			parameters.put("listpriceMax", productQueryForm.getListpriceMax());

			// 查询数据
			List<ProductSale> productSales = productService.findProductSale(
					parameters, pagination);
			
            // 返回
            ModelAndView mav = new ModelAndView(ControllerConstant.LISTURL);
            mav.addObject("shops", shopService.find(null, null));
            mav.addObject("store", codeService.get(Code.STORAGE_AND_DELIVERY_TYPE));
            mav.addObject(ControllerConstant.PRODUCTSALES, productSales);
            mav.addObject(ControllerConstant.PAGINATION, pagination);
            mav.addObject(ControllerConstant.SALESTATUS, codeService.get(ControllerConstant.PRODUCTSALESTATUS)
                    .getChildren());
            mav.addObject("productQueryForm", productQueryForm);
            mav.addObject("allDc", codeService.getAllDc());
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(ControllerConstant.LISTURL);
            mav.addObject(ControllerConstant.SALESTATUS, codeService.get(ControllerConstant.PRODUCTSALESTATUS)
                    .getChildren());
            return mav;
        }
    }

    /**
     * 新建订单时添加商品
     * 
     * @param
     * @return
     */
    @RequestMapping(value = "/orderAddProductSale", method = RequestMethod.POST)
    public ModelAndView orderAddProductSale(@RequestParam("search_productSaleId") String productSaleId,
                                            @RequestParam("search_outerId") String outerId,
                                            @RequestParam("search_ISBN") String isbn,
                                            @RequestParam("search_productName") String productName,
                                            @RequestParam("search_shop") Long shopId,
                                            @RequestParam(value = "search_supplyType", required = false) Long supplyType) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		if (!"".equals(productSaleId)) {
			parameters.put("productSaleId",
					NumberUtils.createLong(productSaleId));
		}

		if (!"".equals(productName)) {
			parameters.put(ControllerConstant.PRODUCTNAME, productName);
		}
		if (!"".equals(isbn)) {
			parameters.put(ControllerConstant.PRODUCTBARCODE, isbn);
		}
		if (!"".equals(outerId)) {
			parameters.put(ControllerConstant.OUTERID, outerId);
		}
		parameters.put("shopId", shopId);
		// 查询数据
		List<ProductSale> productSales = productService.findProductSale(
				parameters, new Pagination());
		List<ProductSaleForm> list = new ArrayList<ProductSaleForm>();
		for (ProductSale p : productSales) {
			try {
				this.productSaleStockService.initProductSaleStock(p);
			} catch (ProductSaleStockException e) {
				throw new RuntimeException(e.getMessage());
			}
			ProductSaleForm productSaleForm = new ProductSaleForm();
			float bd = p
					.getSalePrice()
					.divide(p.getProduct().getListPrice(), 2,
							BigDecimal.ROUND_HALF_DOWN).floatValue();
			DecimalFormat df = new DecimalFormat("0.00");
			productSaleForm.setDiscount(df.format(bd));
			productSaleForm.setNum("1");
			productSaleForm.setProductSale(p);
			productSaleForm.setSupplyType(supplyType);
			// 添加商品在各dc中的库存
			Set<ProductSaleStockVo> productSaleStocks = p.getProductSaleStockVos();
			if (org.apache.commons.collections.CollectionUtils
					.isNotEmpty(productSaleStocks)) {
				for (ProductSaleStockVo productSaleStock : productSaleStocks) {
					ProductSaleStock stock = new ProductSaleStock();
					stock.setId(productSaleStock.getId());
					stock.setDc(this.codeService.get(productSaleStock.getDc()));
					stock.setProductSale(p);
					stock.setIncorrect(productSaleStock.getIncorrect());
					stock.setSales(productSaleStock.getSales());
					stock.setStock(productSaleStock.getStock());
					stock.setVirtual(productSaleStock.getVirtual());
					if (Code.ORDER_SALE_TYPE_RAPID.equals(supplyType)) {
						if (Code.DC_8A19.equals(productSaleStock.getDc())) {
							productSaleForm.getProductSaleStocks().add(
									stock);
							break;
						} else {
							continue;
						}
					} else {
						if (!Code.DC_8A19.equals(productSaleStock.getDc())) {
							productSaleForm.getProductSaleStocks().add(
									stock);
						}
					}
				}
			}
			list.add(productSaleForm);
		}
		ModelAndView mav = new ModelAndView("/order/add_products");
		mav.addObject("list", list);
		return mav;
	}

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public ModelAndView orderAddProductSale(@RequestParam("type") String type,
                                            @RequestParam("productInfo") String productInfo) {
    	ModelAndView mav = new ModelAndView("/promotion/add_products");
		String[] productInfos = productInfo.split(ControllerConstant.SPLITCHAR);
		List<ProductSaleForm> list = new ArrayList<ProductSaleForm>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (ControllerConstant.PRODUCTID.equals(type)) {
			for (String s : productInfos) {
				map.put(ControllerConstant.PRODUCTSALEID,
						NumberUtils.createLong(s));
				for (ProductSale ps : productService.findProductSale(map,
						new Pagination(ControllerConstant.PAGESIZE))) {
					ProductSaleForm psf = new ProductSaleForm();
					psf.setProductSale(ps);
					list.add(psf);

				}
			}
		} else if ("isbn".equals(type)) {
			for (String s : productInfos) {
				map.put("productBarcode", s);
				for (ProductSale ps : productService.findProductSale(map,
						new Pagination(ControllerConstant.PAGESIZE))) {
					ProductSaleForm psf = new ProductSaleForm();
					psf.setProductSale(ps);
					list.add(psf);
				}
			}
		} else {
			for (String s : productInfos) {
				map.put("outerId", s);
				for (ProductSale ps : productService.findProductSale(map,
						new Pagination(ControllerConstant.PAGESIZE))) {
					ProductSaleForm psf = new ProductSaleForm();
					psf.setProductSale(ps);
					list.add(psf);

				}
			}
		}
		mav.addObject("list", list);
		return mav;
    }

    @RequestMapping(value = "/addProductByExcel", method = RequestMethod.POST)
    public ModelAndView addProductByExcel(@RequestParam("file") MultipartFile file) {
		InputStream inputStream;
		Workbook excel;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			inputStream = file.getInputStream();
			excel = Workbook.getWorkbook(inputStream);
		} catch (Exception e1) {
			throw new RuntimeException("excel无法读取");
		}
		Sheet sheet = excel.getSheet(0);
		List<ProductSaleForm> list = new ArrayList<ProductSaleForm>();
		for (int i = 1; i < sheet.getRows(); i++) {
			String id = sheet.getCell(0, i).getContents().trim();
			String salePrice = sheet.getCell(1, i).getContents().trim();
			if (StringUtils.isBlank(id)) {
				break;
			}
			map.put(ControllerConstant.PRODUCTSALEID,
					NumberUtils.createLong(id));
			for (ProductSale ps : productService.findProductSale(map,
					new Pagination(ControllerConstant.PAGESIZE))) {
				ProductSaleForm psf = new ProductSaleForm();
				psf.setProductSale(ps);
				psf.setDiscount(salePrice);// 此处用discount存储excel中的实在
				list.add(psf);
			}
		}
		ModelAndView mav = new ModelAndView("/promotion/add_products");
		mav.addObject("list", list);
		return mav;
    }

    @RequestMapping(value = "/{productSaleId}/detail", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(ControllerConstant.PRODUCTSALEID) Long productSaleId) {
        ProductSale productSale = productService.getProductSale(productSaleId);
        Product product = productSale.getProduct();
        List<ProductImage> largeImages = product.getImageListByType(ProductImage.TYPE_LARGE);
        List<ProductImage> mediumImages = product.getImageListByType(ProductImage.TYPE_MEDIUM);
        List<ProductImage> smallImages = product.getImageListByType(ProductImage.TYPE_SMALL);
        List<ProductImage> illustrationImages = product.getImageListByType(ProductImage.TYPE_ILLUSTRATION);
        ModelAndView mav = new ModelAndView("/product/detail");
        mav.addObject(ControllerConstant.PRODUCTSALE, productSale);
        mav.addObject("largeImages", largeImages);
        mav.addObject("mediumImages", mediumImages);
        mav.addObject("smallImages", smallImages);
        mav.addObject("illustrationImages", illustrationImages);
        mav.addObject("log", productService.findProductSaleLog(productSale, null));
        return mav;
    }

    /**
     * 跳转到枚举
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/{productMeta}/productMetaEnumList", method = RequestMethod.GET)
    public ModelAndView productMetaEnum(@PathVariable("productMeta") Long id) {
        ProductMeta productMeta = productService.getProductMeta(id);
        Set<ProductMetaEnum> productMetaEnums = productMeta.getEnumList();
        ModelAndView mav = new ModelAndView("/product/productMetaEnum");
        mav.addObject("productMetaEnums", productMetaEnums);
        mav.addObject("meta", id);
        return mav;
    }

    /**
     * 修改枚举
     * 
     * @param id
     * @param value
     * @param index
     * @param isdefault
     * @return
     */
    @RequestMapping(value = "/productMetaEnum/{productMetaEnumId}", method = RequestMethod.PUT)
    public ModelAndView productMetaEnumUpdate(@PathVariable("productMetaEnumId") Long id,
                                              @RequestParam("value") String value, @RequestParam("index") int index,
                                              @RequestParam("isdefault") boolean isdefault) {
        ProductMetaEnum productMetaEnum = productService.getProductMetaEnum(id);
        productMetaEnum.setValue(value);
        productMetaEnum.setIndex(index);
        productMetaEnum.setDefaultValue(isdefault);
        productService.updateProductMetaEnum(productMetaEnum);
        ModelAndView mav = new ModelAndView(ControllerConstant.REDIRECT_PRODUCT
                + productMetaEnum.getProductMeta().getId() + ControllerConstant.PRODUCTMETAENUMLIST);
        return mav;
    }

    /**
     * 删除枚举
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/productMetaEnum/{productMetaEnumId}/delete", method = RequestMethod.GET)
    public ModelAndView productMetaEnumDelete(@PathVariable("productMetaEnumId") Long id) {
        ProductMetaEnum productMetaEnum = productService.getProductMetaEnum(id);
        productService.deleteProductMetaEnum(productMetaEnum.getProductMeta(), productMetaEnum);
        ModelAndView mav = new ModelAndView(ControllerConstant.REDIRECT_PRODUCT
                + productMetaEnum.getProductMeta().getId() + ControllerConstant.PRODUCTMETAENUMLIST);
        return mav;
    }

    /**
     * 创建枚举
     * 
     * @param meta
     * @param value
     * @param index
     * @param isdefault
     * @return
     */
    @RequestMapping(value = "/productMetaEnum", method = RequestMethod.POST)
    public ModelAndView createProductMetaEnum(@RequestParam("meta") Long meta, @RequestParam("value") String value,
                                              @RequestParam("index") int index,
                                              @RequestParam("isdefault") boolean isdefault) {
        ProductMeta productMeta = productService.getProductMeta(meta);
        ProductMetaEnum productMetaEnum = new ProductMetaEnum();
        productMetaEnum.setProductMeta(productMeta);
        productMetaEnum.setValue(value);
        productMetaEnum.setIndex(index);
        productMetaEnum.setDefaultValue(isdefault);
        productService.addProductMetaEnum(productMeta, productMetaEnum);
        ModelAndView mav = new ModelAndView(ControllerConstant.REDIRECT_PRODUCT + meta
                + ControllerConstant.PRODUCTMETAENUMLIST);
        return mav;
    }

    @RequestMapping(value = "/importproduct", method = RequestMethod.POST)
    public ModelAndView importproduct(@RequestParam("meta") Long meta, @RequestParam("value") String value,
                                      @RequestParam("index") int index, @RequestParam("isdefault") boolean isdefault) {
        ProductMeta productMeta = productService.getProductMeta(meta);
        ProductMetaEnum productMetaEnum = new ProductMetaEnum();
        productMetaEnum.setProductMeta(productMeta);
        productMetaEnum.setValue(value);
        productMetaEnum.setIndex(index);
        productMetaEnum.setDefaultValue(isdefault);
        productService.addProductMetaEnum(productMeta, productMetaEnum);
        ModelAndView mav = new ModelAndView(ControllerConstant.REDIRECT_PRODUCT + meta
                + ControllerConstant.PRODUCTMETAENUMLIST);
        return mav;
    }

    @RequestMapping(value = "/productstop", method = RequestMethod.GET)
    public ModelAndView productStop() {
        ModelAndView mav = new ModelAndView("/product/productStop");
        return mav;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ModelAndView transferProduct(@RequestParam(value = "target", required = true, defaultValue = "category") String target,
                                        @RequestParam(required = false, value = "productIds") String productIds,
                                        @RequestParam(required = true, value = "categoryId") Long categoryId,
                                        @RequestParam(required = true, value = "targetCategoryId") Long targetCategoryId)
            throws CategoryException {
        ModelAndView modelAndView = new ModelAndView("/product/transfer");
        Category category = categoryService.get(categoryId);
        Category targetCategory = categoryService.get(targetCategoryId);
        int result = 0;
        if (productIds == null || com.winxuan.framework.util.StringUtils.isNullOrEmpty(productIds)) {
            log.info(String.format("%s to %s", categoryId, targetCategoryId));
            result = productService.transferProductByCategory(category, targetCategory);
        } else {
            log.info(String.format("%s to %s with product: %s", categoryId, targetCategoryId, productIds));
            result = productService.transferPeoductByCategory(productIds.split(","), category, targetCategory);
        }
        modelAndView.addObject("result", "1");
        modelAndView.addObject(ControllerConstant.MESSAGE, String.format("移动操作影响行数：%s", result));
        return modelAndView;
    }

    /**
     * 
     * @param codingType
     * @param codingValue
     * @param sellerName
     * @param ids
     * @param queryType
     * @param actionFlag
     * @param pagination
     * @return
     */
    @RequestMapping(value = "/findproduct", method = RequestMethod.POST)
    public ModelAndView findProduct(@RequestParam(required = false, value = "codingType") String codingType,
                                    @RequestParam(required = false, value = "codingValue") String codingValue,
                                    @RequestParam(required = false, value = "shopName") String shopName,
                                    @RequestParam(required = false, value = "ids") String ids,
                                    @RequestParam(required = false, value = "actionFlag") String actionFlag,
                                    @RequestParam(required = false, value = "remark") String remark,
                                    @MyInject Pagination pagination, @MyInject Employee operator) {
        String[] pids = null;
        if (!StringUtils.isBlank(ids)) {
            pids = ids.split(ControllerConstant.SPLITPOINT);
        }
        if ("stop".equals(actionFlag)) {// 停用商品
			ModelAndView mav = new ModelAndView("/product/product");
			Set<Long> productSaleIds = new HashSet<Long>();
			for (String id : pids) {
				productSaleIds.add(Long.valueOf(id));
				if (!StringUtils.isBlank(id)) {
					ProductSale productSale = productService
							.getProductSale(Long.valueOf(id));
					productSale.setRemark(remark);
					productService.updateProductStatus(productSale,
							codeService.get(Code.PRODUCT_SALE_STATUS_EC_STOP),
							operator);
				}
			}
			productService.updateComplexSaleStatus(productSaleIds);
			mav.addObject("saleStatus",
					codeService.get(Code.PRODUCT_SALE_STATUS_EC_STOP).getName());
			return mav;
		} else {
            ModelAndView mav = new ModelAndView("/product/productStop");
            Map<String, Object> params = new HashMap<String, Object>();
            if (!StringUtils.isBlank(codingValue)) {
                codingValue = codingValue.trim();
                String[] qids = codingValue.split(ControllerConstant.SPLITCHAR);
                List<String> stringcodes = new ArrayList<String>();
                for (int i = 0; i < qids.length; i++) {
                    if (!StringUtils.isBlank(qids[i])) {
                        stringcodes.add(qids[i].trim());
                    }
                }
                if (ControllerConstant.PRODUCTSALEID.equals(codingType)) {// 按商品ID查询
                    List<Long> longcodes = new ArrayList<Long>();
                    for (int i = 0; i < qids.length; i++) {
                        if (!StringUtils.isBlank(qids[i])) {
                            try {
                                longcodes.add(NumberUtils.createLong(qids[i].trim()));
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                    params.put(ControllerConstant.PRODUCTSALEIDS, longcodes);
                } else if ("productBarcode".equals(codingType)) {// 按条形码查询
                    params.put("productBarcodes", stringcodes);
                } else if ("outerId".equals(codingType)) {// 按外编码查询
                    params.put(ControllerConstant.OUTERIDS, stringcodes);
                } else if ("prodcutMerchId".equals(codingType)) {// 按主数据编码查询
                    List<Long> longcodes = new ArrayList<Long>();
                    for (int i = 0; i < qids.length; i++) {
                        if (!StringUtils.isBlank(qids[i])) {
                            try {
                                longcodes.add(NumberUtils.createLong(qids[i].trim()));
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                    params.put("prodcutMerchIds", longcodes);
                }
            }

            if (!StringUtils.isBlank(shopName)) {
                params.put("shopName", shopName);
            }
            params.put("auditStatus", Code.PRODUCT_AUDIT_STATUS_PASS);
            List<ProductSale> productSales = productService.findProductSale(params, pagination);
            mav.addObject(ControllerConstant.PRODUCTSALES, productSales);
            mav.addObject("pagination", pagination);
            return mav;
        }
    }

    /**
     * 删除商品
     * 
     * @param proId
     * @return
     */
    @RequestMapping(value = "/deleteproduct", method = RequestMethod.POST)
    public ModelAndView deleteProduct(@RequestParam("proId") Long proId, @MyInject Employee operator) {
        ModelAndView mav = new ModelAndView("/seller/seller");
        ProductSale productSale = productService.getProductSale(proId);
        Code code = codeService.get(Code.PRODUCT_SALE_STATUS_DELETED);
        productService.updateProductStatus(productSale, code, operator);
        mav.addObject(ControllerConstant.MESSAGE, code.getName());
        return mav;
    }

    /**
     * 根据上传的excel中的条件查询商品信息
     * 
     * @param file
     * @param pagination
     * @return
     */
    @RequestMapping(value = "/queryconfig", method = RequestMethod.POST)
    public ModelAndView queryProductMateSale(@RequestParam(required = false, value = "fileName") MultipartFile file,
                                             HttpServletRequest request, @MyInject Pagination pagination) {
        // 文件获取流，流获取workbook,
        Workbook excel = null;
        Map<String, Object> params = new HashMap<String, Object>();
        if (file != null) {
            try {
                excel = Workbook.getWorkbook(file.getInputStream());
            } catch (Exception e1) {
                throw new RuntimeException("excel无法读取");
            }
            // 获取第一页
            Sheet sheet = excel.getSheet(MagicNumber.ZERO);
            List<Long> productSaleIds = new ArrayList<Long>();
            for (int i = MagicNumber.ONE; i < sheet.getRows(); i++) {
                String productSaleId = sheet.getCell(MagicNumber.ZERO, i).getContents().trim();
                if (!StringUtils.isBlank(productSaleId)) {
                    if (!StringUtils.isBlank(productSaleId)) {
                        productSaleIds.add(Long.valueOf(productSaleId.trim()));
                    }
                } else {
                    break;
                }
            }
            if (!productSaleIds.isEmpty()) {
                params.put(ControllerConstant.PRODUCTSALEIDS, productSaleIds);
                request.getSession().setAttribute(ControllerConstant.PRODUCTSALEIDS, productSaleIds);
            }
        } else {
            params.put(ControllerConstant.PRODUCTSALEIDS,
                    (List<?>) request.getSession().getAttribute(ControllerConstant.PRODUCTSALEIDS));
        }
        params.put("auditStatus", Code.PRODUCT_AUDIT_STATUS_PASS);
        List<ProductSale> productSales = productService.findProductSale(params, pagination);

        ModelAndView mav = new ModelAndView("/product/productStop");
        mav.addObject(ControllerConstant.PRODUCTSALES, productSales);
        mav.addObject("resultSize", productSales.size());
        mav.addObject("pagination", pagination);
        return mav;
    }

    /**
     * 提交设置新品预售
     * 
     * @param saleId
     * @param stockQuantity
     * @param bookStartDate
     * @param bookEndDate
     * @param saleStatus
     * @param bookDescription
     * @return
     * @throws ProductException
     * @throws StockDocumentsException 
     */
    @RequestMapping(value = "/setBooking", method = RequestMethod.POST)
    public ModelAndView setBooking(ProductBookingForm form, @MyInject Employee employee) throws ProductException, StockDocumentsException {
		ModelAndView mav = new ModelAndView(ControllerConstant.JOSNRESULT);
		if (StringUtils.isNotBlank(form.getSaleStatus())
				&& StringUtils.isNotBlank(form.getBookStartDate())
				&& StringUtils.isNotBlank(form.getBookEndDate())
				&& StringUtils.isNumeric(form.getSaleStatus())
				&& form.getSaleId() != null && form.getDc() != null) {
			ProductSale ps = productService.getProductSale(Long.valueOf(form
					.getSaleId()));
			if (ps.getProduct().isComplex()) {
				mav.addObject(ControllerConstant.MESSAGE_KEY, "不能直接设置套装书为预售商品！");
				return mav;
			}
			try {
				productSaleStockService.initProductSaleStock(ps);
			} catch (ProductSaleStockException e) {

				throw new RuntimeException();
			}
			ProductSaleStockVo pss = ps
					.getProductSaleStockVo(new Code(form.getDc()).getId());

			if (pss != null
					&& getActualAvalibleQuantity(pss.getStock(), pss.getSales()) >= form
							.getStockQuantity()) {
				String msg = String.format("预售数量小于实物数量.stock:%s",
						getActualAvalibleQuantity(pss.getStock(), pss.getSales()));
				throw new ProductSaleStockException(ps, msg);
			}

			boolean forSale = false;
			forSale = Long.valueOf(form.getSaleStatus()).compareTo(
					Code.PRODUCT_SALE_STATUS_ONSHELF) == 0 ? true : false;
			ProductBooking booking = new ProductBooking();

			try {
				booking.setStartDate(DateUtils.parserStringToDate(
						form.getBookStartDate(),
						DateUtils.SHORT_DATE_FORMAT_STR));
				booking.setEndDate(DateUtils.parserStringToDate(
						form.getBookEndDate(), DateUtils.SHORT_DATE_FORMAT_STR));
			} catch (ParseException e) {
				mav.addObject(ControllerConstant.RESULT_KEY,
						ControllerConstant.RESULT_INTERNAL_ERROR);
				return mav;
			}
			if (forSale) {
				mav.addObject(ControllerConstant.SALESTATUS,
						codeService.get(Code.PRODUCT_SALE_STATUS_ONSHELF)
								.getName());
			} else {
				mav.addObject(ControllerConstant.SALESTATUS,
						codeService.get(Code.PRODUCT_SALE_STATUS_OFFSHELF)
								.getName());
			}
			booking.setDescription(form.getBookDescription());
			booking.setStockQuantity(form.getStockQuantity());
			booking.setIgnore(form.getIgnore());
			booking.setEmployee(employee);
			booking.setCreateDate(new Date());
			Code dc = codeService.get(form.getDc());
			booking.setDc(dc);
			ps.setSupplyType(new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING));
			productSaleStockService.updateVirtualQuantity(dc, ps,
					form.getStockQuantity());
/*			StockDocuments stockDocumentsVo = new StockDocuments();
			DateFormat format = new SimpleDateFormat("yyyymmddhhmmss");
			stockDocumentsVo.setType(EnumStockDocumentsType.PRESELL_STOCK);
			stockDocumentsVo.setDc(dc.getId());
			stockDocumentsVo.setCredence(format.format(new Date()).toString());
			stockDocumentsVo.setProductSale(ps.getId());
			stockDocumentsVo.setStock(form.getStockQuantity());
			documentsService.documentProcessing(stockDocumentsVo);*/
			for(ProductSaleStockVo stock:ps.getProductSaleStockVos()){
				if(stock.getDc()==booking.getDc().getId()){
					stock.setVirtual(form.getStockQuantity());
				}
			}
			productService.setupBooing(ps, booking, forSale);
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
			mav.addObject(ControllerConstant.STATUS,
					codeService.get(Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING)
							.getName());
			return mav;
		}
		mav.addObject(ControllerConstant.MESSAGE_KEY, "数据错误，设置失败，请检查...");
		return mav;

	}

    /**
     * 跳转到商品信息修改
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public ModelAndView goProductEdit(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("/product/product_edit");
        ProEditForm proEditForm = new ProEditForm();
        ProductSale productSale = productService.getProductSale(id);
        proEditForm.setFormValue(productSale);
        if (productSale != null) {
            mav.addObject("isBh", belongToCategory(productSale.getProduct().getCategory(), ControllerConstant.BH));
        }
        mav.addObject("proEditForm", proEditForm);
        mav.addObject(ControllerConstant.PRODUCTSALE, productSale);
        mav.addObject("storageType", codeService.get(Code.STORAGE_AND_DELIVERY_TYPE).getChildren());
        mav.addObject(ControllerConstant.SALESTATUS, codeService.get(Code.PRODUCT_SALE_STATUS).getChildren());
        mav.addObject("noEditType", Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM);
        String mcId = productSale.getProduct().getMcCategory();
        if (StringUtils.isNotBlank(mcId)) {
            McCategory mcCategory = mcCategoryService.get(mcId);

            mav.addObject("mcCategory", mcCategory);

        }
        return mav;
    }

    /**
     * 修改商品信息
     * 
     * @param proEditForm
     * @return
     * @throws ParseException
     * @throws ProductException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView productEdit(@Valid ProEditForm proEditForm, @MyInject Employee operator) throws ParseException,
            ProductException {
        ModelAndView mav = new ModelAndView();

        ProductSale productSale = productService.getProductSale(proEditForm.getId());
        if (productSale != null) {

            productSale = proEditForm.getProductSale(productSale);
            Product product = productSale.getProduct();
            if (proEditForm.getCategories() != null) {
                Category category;
                List<Category> list = product.getCategories() == null ? new ArrayList<Category>() : product
                        .getCategories();
                list.clear();
                for (Long id : proEditForm.getCategories()) {
                    category = categoryService.get(id);
                    if (category != null) {
                        list.add(category);
                    }
                }
                product.setCategories(list);
            } else {
                product.setCategories(null);
            }

            if (!productSale.getSaleStatus().getId().equals(proEditForm.getSaleStatus())) {
                productService.updateProductStatus(productSale, codeService.get(proEditForm.getSaleStatus()), operator);
				if(Code.PRODUCT_SALE_STATUS_EC_STOP.equals(proEditForm.getSaleStatus())){
					Set<Long> productSaleIds = new HashSet<Long>();
					productSaleIds.add(productSale.getId());
					productService.updateComplexSaleStatus(productSaleIds);
				}
            } else {
            	Set<ProductSale> complexMasters = productSale.getComplexMasterList();
            	if(!CollectionUtils.isEmpty(complexMasters)&&productSale.getBooking()!=null) {
            		for(ProductSale complexMaster : complexMasters) {
            			if (complexMaster != null) {
                            ProductTransient productTransient = complexMaster.getDefaultTransient();
                            productTransient.setEmployee(operator);
                            productTransient.addComplexDc(productSale.getBooking().getDc());
                        }
            		}
            	}
            	productService.updateProductSaleInfo(productSale, operator);
            }
            if (!(StringUtils.isBlank(proEditForm.getStockQuantity()) || productSale.getProduct().isComplex())
                    && productSale.getShop().getId() == Shop.WINXUAN_SHOP) {
                try {
                	/*productSaleStockService.updateVirtualQuantity(productSale
					.getBooking().getDc(), productSale, productSale
					.getBooking().getStockQuantity());*/
					StockDocuments stockDocumentsVo = new StockDocuments();
					DateFormat format = new SimpleDateFormat("yyyymmddhhmmss");
					stockDocumentsVo.setType(EnumStockDocumentsType.PRESELL_STOCK);
					stockDocumentsVo.setDc(productSale
							.getBooking().getDc().getId());
					stockDocumentsVo.setCredence(format.format(new Date()).toString());
					stockDocumentsVo.setProductSale(productSale.getId());
					stockDocumentsVo.setStock(productSale
							.getBooking().getStockQuantity());
					documentsService.documentProcessing(stockDocumentsVo);
                } catch (Exception e) {
                    mav.addObject("message", e.getMessage());
                }
            }

            mav.setViewName("redirect:/product/" + proEditForm.getId() + "/detail");
            return mav;
        }
        return null;
    }

    /**
     * 修改商品分类
     * 
     * @param proEditForm
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/updateproductcagegory", method = RequestMethod.POST)
    public ModelAndView updateProductCategory(@Valid ProCaEditForm proCaEditForm, @RequestParam(value = "pid",
                                                      required = false) String pid, @RequestParam(value = "sid",
                                                      required = false) String sid, @MyInject Employee operator)
            throws ParseException {
        ModelAndView mav = new ModelAndView();
        if (null == proCaEditForm) {
            mav.setViewName("redirect:/category/view?id=" + pid);
            return mav;
        }
        Long[] categorys = proCaEditForm.getCategories();
        categorys = this.getCategory(categorys);
        Product product = productService.get(Long.valueOf(pid));
        List<Category> list = product.getCategories() == null ? new ArrayList<Category>() : product.getCategories();
        list.clear();
        for (Long ca : categorys) {
            if (null != ca && ca != 0L) {
                Category c = categoryService.get(ca);
                if (null != c) {
                    list.add(c);
                }
            }
        }
        product.setCategories(list);
        productService.updateCategory(product);
        mav.setViewName("redirect:/product/" + sid + "/edit");
        return mav;
    }

    /**
     * 
     * @param categorys
     *            页面传入的分类，包含所选分类的全部父节点与子节点
     * @return 返回处理后的所选分类的末节点
     */
    private Long[] getCategory(Long[] categorys) {
        List<Category> list = new ArrayList<Category>();
        for (Long ca : categorys) {
            Category c = categoryService.get(ca);
            list.add(c);
        }
        for (Category cas : list) {
            Category parent = cas.getParent();
            for (int i = 0; i < categorys.length; i++) {
                Long c = categorys[i];
                if (null == parent) {
                    break;
                }
                if (c == parent.getId()) {
                    categorys[i] = 0L;
                    break;
                }
            }
        }
        return categorys;
    }

    /**
     * 停用商品
     * 
     * @param proId
     * @return
     */
    @RequestMapping(value = "/stopproduct", method = RequestMethod.POST)
    public ModelAndView productStop(@RequestParam("proId") Long proId, @MyInject Employee operator) {
        ModelAndView mav = new ModelAndView(ControllerConstant.JOSNRESULT);
        ProductSale productSale = productService.getProductSale(proId);
        productService.complexStop(operator, productSale);
        mav.addObject(ControllerConstant.SALESTATUS, productSale.getSaleStatus().getName());
        return mav;
    }

    /**
     * 取消停用商品
     * 
     * @param proId
     * @return
     */
    @RequestMapping(value = "/offproduct", method = RequestMethod.POST)
    public ModelAndView productOff(@RequestParam("proId") Long proId, @MyInject Employee operator) {
        ModelAndView mav = new ModelAndView(ControllerConstant.JOSNRESULT);
        ProductSale productSale = productService.getProductSale(proId);
        mav.addObject(ControllerConstant.STATUS, true);
        Code code = codeService.get(Code.PRODUCT_SALE_STATUS_ONSHELF);
        if (!productService.canBeOnShelf(productSale)) {
            code = codeService.get(Code.PRODUCT_SALE_STATUS_OFFSHELF);
            mav.addObject(ControllerConstant.STATUS, false);
        }
        productService.updateProductStatus(productSale, code, operator);
        mav.addObject(ControllerConstant.SALESTATUS, productSale.getSaleStatus().getName());
        return mav;
    }

    /**
     * 跳转到商品价格修改
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}/newprice", method = RequestMethod.GET)
    public ModelAndView goPriceEdit(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("/product/product_price_edit");
        ProductSale productSale = productService.getProductSale(id);
        mav.addObject("priceLog", productService.findProductSaleLog(productSale, null));
        mav.addObject(ControllerConstant.PRODUCTSALE, productSale);
        return mav;
    }

    /**
     * 修改商品价格
     * 
     * @param id
     * @param newPrice
     * @param operator
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/priceupdate", method = RequestMethod.POST)
    public ModelAndView priceEdit(@RequestParam("id") Long id, @RequestParam("newPrice") BigDecimal newPrice,
                                  @MyInject Employee operator) {
		ModelAndView mav = new ModelAndView();
		ProductSale productSale = productService.getProductSale(id);
		if (productSale != null) {
			if (productSale.getStorageType().getId().longValue() == Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM
					.longValue()) {
				log.info(productSale.getId() + "是纸质书，请到价格系统修改！");
			} else {
				productService.updatePrice(productSale, operator, newPrice);
			}
			mav.setViewName("redirect:/product/" + id + "/detail");
			return mav;
		}
		return null;
    }

	@RequestMapping(value = "/minStock", method = RequestMethod.GET)
	public ModelAndView findMinStock(@RequestParam(value = "id") Long[] ids,
			@RequestParam(value = "dc") Long dc,@RequestParam(value = "itemdc") Long[] itemdc) {
		ModelAndView mav = new ModelAndView("product/minStock");
		List<MinStock> minStockList = new ArrayList<MinStock>();
		Integer quantity=0;
		DcRule dcRule = dcService.findByAvailableDc(new Code(dc));
		if(hasNotSame(itemdc)){
			quantity=0;
			minStockList.add(new MinStock(dcRule.getLocation(), quantity));
			mav.addObject("minStockList", minStockList);
			return mav;
		}
		Set<ProductSale> productList = new HashSet<ProductSale>();
		Set<Long> productSaleIds = new HashSet<Long>();
		for (Long id : ids) {
			//productList.add(productService.getProductSale(id));
			productSaleIds.add(id);
		}
		List<ProductSaleVo> productSaleList = pSService.findProductSales(productSaleIds);
		for(ProductSaleVo v : productSaleList){
			ProductSale product = new ProductSale();
			Code code = new Code();
			product.setId(v.getId());
			code.setId(v.getSupplyType().getId());
			product.setSupplyType(code);
			productList.add(product);
		}
		
		quantity = productSaleStockService.computeComplexQuantity(
				dcRule.getLocation(), productList);
		minStockList.add(new MinStock(dcRule.getLocation(), quantity));
		mav.addObject("minStockList", minStockList);
		return mav;
	}
	
	private boolean hasNotSame(Long[] itemdc){
		for(int i=0;i<itemdc.length;i++){
			for(int j=i+1;j<itemdc.length;j++){
				if(!itemdc[i].equals(itemdc[j])){
					return true;
				}
			}
		}
		return false;
	}

    /**
     * 初始化套装书修改页面
     * 
     * @param ids
     * @param codingType
     * @return
     */
    @RequestMapping(value = "/initedit", method = RequestMethod.POST)
    public ModelAndView initComplexEdit(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("/product/complexedit");
		ProductSale productSale = productService.getProductSale(id);
		mav.addObject(ControllerConstant.PRODUCTSALES,
				productSale.getComplexItemList());
		productSale.getProductSaleStockVos();
		mav.addObject(ControllerConstant.PRODUCTSALE, productSale);
		return mav;
	}

    /**
     * 商品信息查询
     * 
     * @param
     * @return
     */
    @RequestMapping(value = "/complexlist", method = RequestMethod.POST)
    public ModelAndView findComplexProduct(@Valid ComplexQueryForm complexQueryForm, @MyInject Pagination pagination,
                                           HttpServletRequest request) throws ProductSaleStockException {
		// 构建map
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ControllerConstant.PRODUCTSALEIDS,
				complexQueryForm.getPorIds());
		parameters.put(ControllerConstant.PRODUCTNAME,
				complexQueryForm.getQueryProductName());
		parameters.put(ControllerConstant.SALESTATUS,
				complexQueryForm.getSaleStatus());
		parameters.put("startTime", complexQueryForm.getStartTime());
		parameters.put("endTime", complexQueryForm.getEndTime());
		parameters.put("complex", VIRTUAL);
		parameters.put("items", complexQueryForm.getItemIds());
		// 查询数据
		List<ProductSale> productSales = productService.findProductSaleComplex(
				parameters, pagination);
		for(ProductSale sale:productSales){
			productSaleStockService.initProductSaleStock(sale);
			for(ProductSaleStockVo vo:sale.getProductSaleStockVos()){
				Code code = codeService.get(vo.getDc());
				CodeVo codeVo = new CodeVo();
				codeVo.setName(code.getName());
				codeVo.setDescription(code.getDescription());
				vo.setDcdetail(codeVo);
			}
		}
		// 返回
		ModelAndView mav = new ModelAndView("/product/complex_product_list");
		mav.addObject(ControllerConstant.PRODUCTSALES, productSales);
		mav.addObject(ControllerConstant.PAGINATION, pagination);
		mav.addObject(ControllerConstant.SALESTATUS,
				codeService.get(ControllerConstant.PRODUCTSALESTATUS)
						.getChildren());
		mav.addObject("complexQueryForm", complexQueryForm);
		return mav;

	}

    /**
     * 判断商品是否属于某个分类
     * 
     * @param category
     * @return
     */
    private boolean belongToCategory(Category category, Long cid) {
        if (category != null) {
            if (category.getId() == cid) {
                return true;
            }
            return belongToCategory(category.getParent(), cid);
        }
        return false;
    }

    /**
     * 判断商品是否可以上架
     * 
     * @param
     * @return
     */
    @RequestMapping(value = "/canbeonshelf", method = RequestMethod.POST)
    public ModelAndView canBeOnShelf(@RequestParam("proId") String productSaleId) {
        ModelAndView mav = new ModelAndView(ControllerConstant.JOSNRESULT);
        if (!StringUtils.isBlank(productSaleId)) {
            ProductSale productSale = productService.getProductSale(Long.valueOf(productSaleId));
            if (productSale != null) {
                mav.addObject("result", productService.canBeOnShelf(productSale));
            }
        }
        return mav;
    }

    /**
     * 商品批量调价视图
     * 
     * @return
     */
    @RequestMapping(value = "/productPriceUpdate")
    public ModelAndView productPriceUpdate() {
        return new ModelAndView("/product/productPriceUpdate");
    }

    /**
     * 根据上传的excel中的商品进行调价   
     * 
     * @param file
     * @param pagination
     * @return
     */
    @RequestMapping(value = "/productPriceUpdateUpload", method = RequestMethod.POST)
    public ModelAndView productPriceUpdateUpload(@RequestParam(required = false, value = "fileName") MultipartFile file,
                                                 HttpServletRequest request) {
        // 文件获取流，流获取workbook,
        Workbook excel = null;
        Map<String, Object> params = new HashMap<String, Object>();
        Map<Long, BigDecimal> productSalePrices = new HashMap<Long, BigDecimal>();
        if (file != null) {
            try {
                excel = Workbook.getWorkbook(file.getInputStream());
            } catch (Exception e1) {
                throw new RuntimeException("excel无法读取");
            }
            // 获取第一页
            Sheet sheet = excel.getSheet(MagicNumber.ZERO);
            List<Long> productSaleIds = new ArrayList<Long>();
            for (int i = MagicNumber.ONE; i < sheet.getRows(); i++) {
                String productSaleId = sheet.getCell(MagicNumber.ZERO, i).getContents().trim();
                String productSalePrice = sheet.getCell(MagicNumber.ONE, i).getContents().trim();
                if (!StringUtils.isBlank(productSaleId) || !StringUtils.isBlank(productSalePrice)) {
                    Long psi = Long.valueOf(productSaleId.trim());
                    productSaleIds.add(psi);
                    productSalePrices.put(psi, new BigDecimal(productSalePrice));
                } else {
                    break;
                }
            }
            if (!productSaleIds.isEmpty()) {
                params.put(ControllerConstant.PRODUCTSALEIDS, productSaleIds);
            }
        }
        List<ProductSale> productSales = productService.findProductSale(params, null);
        List<ProductPriceUpdateForm> productSaleList = new ArrayList<ProductPriceUpdateForm>();
        for (int i = 0; i < productSales.size(); i++) {
			ProductSale ps = (ProductSale) productSales.get(i);
			if (ps.getShop().getId().compareTo(Long.valueOf(1)) == 0) {
				if (ps.getStorageType().getId().longValue() == Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM
						.longValue()) {
					log.info(ps.getId() + "是纸质书，请到价格系统修改！");
				} else {
					ProductPriceUpdateForm productPriceUpdateForm = new ProductPriceUpdateForm();
					productPriceUpdateForm.setId(ps.getId());
					productPriceUpdateForm.setName(ps.getProduct().getName());
					productPriceUpdateForm.setBarcode(ps.getProduct()
							.getBarcode());
					productPriceUpdateForm.setListPrice(ps.getProduct()
							.getListPrice());
					productPriceUpdateForm.setSaleprice(productSalePrices
							.get(ps.getId()));
					productSaleList.add(productPriceUpdateForm);
				}
			}
		}

        ModelAndView mav = new ModelAndView("/product/productPriceUpdate");
        mav.addObject(ControllerConstant.PRODUCTSALES, productSaleList);
        mav.addObject("resultSize", productSaleList.size());
        return mav;
    }

    @Deprecated
    @RequestMapping(value = "/productPriceUpdateing", method = RequestMethod.POST)
    public ModelAndView productPriceUpdateing(@RequestParam("productSaleId") Long[] productSaleIds,
                                              @RequestParam("saleprice") BigDecimal[] saleprices,
                                              @MyInject Employee employee) {
        ModelAndView mav = new ModelAndView("/product/productPriceUpdateResult");
        if (productSaleIds != null && saleprices != null && productSaleIds.length == saleprices.length) {
            for (int i = 0; i < productSaleIds.length; i++) {
                ProductSale productSale = productService.getProductSale(productSaleIds[i]);
                productService.updatePrice(productSale, employee, saleprices[i]);
            }
            mav.addObject(ControllerConstant.MESSAGE, "商品批量调价成功");
        } else {
            mav.addObject(ControllerConstant.MESSAGE, "商品批量调价失败");
        }
        return mav;
    }

    @RequestMapping(value = "/appendCategory")
    public void appendCategory(@RequestParam("productId") Long productId, @RequestParam("categoryId") Long categoryId) {
        Product product = productService.get(productId);
        Category category = categoryService.get(categoryId);
        if (product != null && category != null) {
            product.getCategories().add(category);
            productService.updateCategory(product);
        }
    }

    /**
     * 跳转到管理分级页面
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}/manageGrade", method = RequestMethod.GET)
    public ModelAndView goManageGradeEdit(@PathVariable("id") Long id, @RequestParam(required = false,
                                                  value = ControllerConstant.MESSAGE) String message) {
        ModelAndView mav = new ModelAndView("/product/product_manage_grade");
        ProductSale productSale = productService.getProductSale(id);
        Code manageGrade = codeService.get(Code.PRODUCT_SALE_MANAGEGRADE);
        mav.addObject("productSale", productSale);
        mav.addObject("manageGrade", manageGrade);
        mav.addObject(ControllerConstant.MESSAGE, message);
        return mav;
    }

    /**
     * 添加商品管理分级
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/saveManageGrade", method = RequestMethod.POST)
    public ModelAndView manageGradeEdit(@Valid ProductSaleManageGradeForm form, @MyInject Employee operator) {
        ModelAndView mav = new ModelAndView(ControllerConstant.REDIRECT_PRODUCT + form.getPsId() + "/manageGrade");
        ProductSale productSale = productService.getProductSale(form.getPsId());
        Code grade = codeService.get(form.getGrade());
        ProductManageGrade manageGrade = null;
        try {
            manageGrade = form.getManageGrade(productSale, grade);
            manageGrade.setOperator(operator);
        } catch (ParseException e1) {
            mav.addObject(ControllerConstant.MESSAGE, "参数错误!");
            return mav;
        }
        try {
            productService.saveManageGrade(manageGrade);
            mav.addObject(ControllerConstant.MESSAGE, "添加成功!");
        } catch (Exception e) {
            mav.addObject(ControllerConstant.MESSAGE, e.getMessage());
        }
        return mav;
    }

    /**
     * 删除商品管理分级
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}/removeManageGrade", method = RequestMethod.GET)
    public ModelAndView removeManageGrade(@PathVariable("id") Long id) {
        ProductManageGrade grade = productService.getManageGrade(id);
        ModelAndView mav = new ModelAndView(ControllerConstant.REDIRECT_PRODUCT + grade.getProductSale().getId()
                + "/manageGrade");
        productService.deleteManageGrade(grade);
        mav.addObject(ControllerConstant.MESSAGE, "删除成功");
        return mav;
    }

    @RequestMapping(value = "/cancelPresaleProduct/{id}", method = RequestMethod.POST)
    public ModelAndView cancelPresaleProduct(@PathVariable("id") Long id, @MyInject Employee employee) {
        ModelAndView mav = new ModelAndView("/product/result");
        ProductSale productSale = productService.getProductSale(id);
        try {
            productService.cancelPresaleProduct(productSale, employee);
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
            mav.addObject(ControllerConstant.MESSAGE_KEY, "修改成功.");
        } catch (ProductException e) {
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
            mav.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
        }
        return mav;
    }

    @RequestMapping(value = "/zoomImage", method = RequestMethod.GET)
    public ModelAndView zoomImage() {
        ModelAndView view = new ModelAndView("product/productImageZoom");
        return view;
    }

    @RequestMapping(value = "/zoomImage", method = RequestMethod.POST)
    public ModelAndView zoomImage(@MyInject Employee employee,
                                  @RequestParam(value = "file", required = false) MultipartFile file) {
        ModelAndView view = new ModelAndView("product/productImageZoom");
        try {
            if (file == null || file.isEmpty()) {
                view.addObject(ControllerConstant.MSG, "请选择文件!");
                return view;
            }
            List<List<String>> data = POIUtil.fetchData(file.getInputStream(), 0);
            imageService.zoomImage(employee, data);
            view.addObject(ControllerConstant.MSG, "正在转换, 请耐心等待, 结果将会放在[报表->附件]中!");
        } catch (IOException e) {
            view.addObject(ControllerConstant.MSG, e.getMessage());
        }
        return view;
    }
    
	/**
	 * 获取虚拟可用量，虚拟库存 - 占用
	 */
	public int getVirtualAvalibleQuantity(int virtual, int sales) {
		int avalible = virtual - sales;
		avalible = avalible < MagicNumber.ZERO ? MagicNumber.ZERO : avalible;
		return avalible;
	}

	/**
	 * 获取实物可用量，实物库存 - 占用
	 */
	public int getActualAvalibleQuantity(int stock, int sales) {
		int avalible = stock - sales;
		avalible = avalible < MagicNumber.ZERO ? MagicNumber.ZERO : avalible;
		return avalible;
	}
}
