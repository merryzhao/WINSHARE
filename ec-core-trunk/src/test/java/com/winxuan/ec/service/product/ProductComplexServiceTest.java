/*
 * @(#)ProductComplexServiceImpl.java
 *
 * Copyright 2014 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.product;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.exception.ProductException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductBooking;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductTransient;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.BaseTest;
import com.winxuan.framework.util.DateUtils;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2014-1-14
 */
public class ProductComplexServiceTest extends BaseTest{
	private static final Log LOG = LogFactory.getLog(ProductComplexServiceTest.class); 
	private static final Short VIRTUAL=1;//虚拟套装

	private ProductSale complex;
	private ProductSale item;
	private Employee employee;
	@Before
	public void setUp() {
		complex = services.productService.getProductSale(1200799605L);
		item = services.productService.getProductSale(1200799599L);
		employee = services.employeeService.get(1682143366L);
	}
	
//	@Test
	public void testFindComplexes() {
		Set<ProductSale> complexMasterList = item.getComplexMasterList();
		assertEquals(2, complexMasterList.size());
		for(ProductSale complex : complexMasterList) {
			LOG.info(complex.getId());
		}
	}
	
//	@Test
//	@Rollback(false)
	public void testCreateComplexProduct() throws ProductException {
		ProductSale complexMaster = copyProductSale(complex);
		List<ProductSale> productSales = new ArrayList<ProductSale>();
		productSales.add(services.productService.getProductSale(1200799599L));
		productSales.add(services.productService.getProductSale(1200799598L));
		List<Code> dcList = new ArrayList<Code>();
		dcList.add(services.codeService.get(Code.DC_D818));
		services.productService.createComplexProduct(complexMaster, productSales, dcList, null);
	}
	
	private ProductSale copyProductSale(ProductSale productSale) {
		Product newProduct = new Product();
		ProductSale newProductSale = new ProductSale();
		newProduct.setName("套装书测试");// 商品名
		newProduct.setManufacturer(productSale.getProduct().getManufacturer());// 出版社
		newProduct.setBarcode("0000000011111111");// 条形码
		newProduct.setAuthor("测试员");// 作者
		Date now = new Date();
		newProduct.setProductionDate(now);// 出版日期
		newProduct.setSort(productSale.getProduct().getSort());
		newProduct.setComplex(VIRTUAL);
		newProduct.setCreateTime(now);
		newProduct.setUpdateTime(now);
		newProductSale.setUpdateTime(now);
		newProductSale.setAuditStatus(new Code(Code.PRODUCT_AUDIT_STATUS_PASS));// 审核状态
		newProductSale.setStorageType(productSale.getStorageType());// 储配方式
		newProductSale.setSupplyType(productSale.getSupplyType());// 销售类型
		newProductSale.setOuterId(null);// 外部编号
		newProductSale.setSellName(newProduct.getName());// 商品销售名称
		newProductSale.setProduct(newProduct);
		return newProductSale;
	}
	
//	@Test
//	@Rollback(false)
	public void testUpdateProdcutSaleInfo() throws ProductException {
		item.setSellName("套装书Test");
		Set<ProductSale> complexMasters = item.getComplexMasterList();
    	if(!CollectionUtils.isEmpty(complexMasters)) {
    		for(ProductSale complexMaster : complexMasters) {
    			if (complexMaster != null) {
                    ProductTransient productTransient = complexMaster.getDefaultTransient();
                    productTransient.setEmployee(employee);
                    productTransient.addComplexDc(services.codeService.get(Code.DC_D818));
                }
    		}
    	}
		services.productService.updateProductSaleInfo(item, null);	
	}
	
//	@Test
//	@Rollback(false)
	public void testSetupBooking() throws ParseException, ProductException {
		ProductBooking booking = new ProductBooking();
		booking.setStartDate(DateUtils.parserStringToDate("2014-01-01",
                DateUtils.SHORT_DATE_FORMAT_STR));
        booking.setEndDate(DateUtils.parserStringToDate("2014-01-29", DateUtils.SHORT_DATE_FORMAT_STR));
        booking.setEmployee(employee);
        booking.setCreateDate(new Date());
        booking.setDc(services.codeService.get(Code.DC_D818));
        booking.setStockQuantity(40);
        item.setSupplyType(new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING));
//        services.productService.setupBooing(item, booking, false);
        services.productService.setupBooing(complex, booking, false);
	}
	
	@Test
	@Rollback(false)
	public void testCancelPresaleProduct() throws ProductException {
		services.productService.cancelPresaleProduct(item, employee);
	}
	
//	@Test
//	@Rollback(false)
	public void testUpdateProductQuantity() {
		List<Code> dcList = new ArrayList<Code>();
		dcList.add(services.codeService.get(Code.DC_D818));
		ProductTransient productTransient = new ProductTransient();
        productTransient.setComplexDcList(dcList);
        item.setProductTransient(productTransient);
        for(ProductSale masterComplex : item.getComplexMasterList()) {
        	masterComplex.setProductTransient(productTransient);
        }
		services.productService.updateProductQuantity(item, 10, 10);
	}
	
//	@Test
//	@Rollback(false)
	public void testUpdatePrice() {
		services.productService.updatePrice(item, employee, new BigDecimal(10));
	}
	
}
