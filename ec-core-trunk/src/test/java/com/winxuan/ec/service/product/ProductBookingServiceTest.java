package com.winxuan.ec.service.product;

import java.util.Date;
import java.util.Set;

import org.apache.commons.jexl.junit.Asserter;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.winxuan.ec.exception.ProductException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductBooking;
import com.winxuan.ec.model.product.ProductBookingLog;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.BaseTest;


/**
 * 
 * @author: HideHai 
 * @date: 2013-10-24
 */
public class ProductBookingServiceTest extends BaseTest{
	
	
	@Test
	public void cancelPresaleProductTest() throws ProductException{
		ProductSale productSale = services.productService.getProductSale(11058392L);
		Code dcOriginal = productSale.getBooking().getDc();
		Employee employee = services.employeeService.get(Employee.SYSTEM);
		services.productService.cancelPresaleProduct(productSale, employee);
		Set<ProductBookingLog> bookingLogs = productSale.getBookingLogs();
		for(ProductBookingLog log : bookingLogs){
			if(log.getDc() == null ||
					!log.getDc().getId().equals(dcOriginal.getId())){
				Asserter.fail("DC为空或不一致");
			}
		}
	}
	
	@Test
	public void setupBooingTest() throws ProductException{
		ProductSale productSale = services.productService.getProductSale(1200688265L);
		ProductBooking booking = new ProductBooking();
		booking.setArrivaldate(DateUtils.addDays(new Date(), 10));
		booking.setCreateDate(new Date());
		booking.setDc(services.codeService.get(110003L));
		booking.setDescription("测试");
		booking.setEmployee(services.employeeService.get(Employee.SYSTEM));
		booking.setEndDate(DateUtils.addDays(new Date(), 10));
		booking.setProductSale(productSale);
		booking.setStockQuantity(200);
		booking.setIgnore(100);
		
		services.productService.setupBooing(productSale, booking, false);
		Code dcOriginal = productSale.getBooking().getDc();
		Set<ProductBookingLog> bookingLogs = productSale.getBookingLogs();
		for(ProductBookingLog log : bookingLogs){
			if(log.getDc() == null ||
					!log.getDc().getId().equals(dcOriginal.getId())){
				Asserter.fail("DC为空或不一致");
			}
		}
	}

}

