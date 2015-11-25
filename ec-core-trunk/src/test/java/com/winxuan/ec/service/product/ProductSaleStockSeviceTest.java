package com.winxuan.ec.service.product;

import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.service.BaseTest;


/**
 * 
 * @author: HideHai 
 * @date: 2013-10-24
 */
public class ProductSaleStockSeviceTest extends BaseTest{
	
	private static final Log LOG = LogFactory
			.getLog(ProductSaleStockSeviceTest.class);

	@Test
	public void quantityFillRateTest(){
		ProductSale productSale = services.productService.getProductSale(1200223478L);
		Map<Code, Integer> map = 
		services.productSaleStockService.quantityFillRate(productSale, 19);
		for(Entry<Code, Integer> entry  : map.entrySet()){
			LOG.info(String.format("%s : %s", entry.getKey().getId(),entry.getValue()));
		}
	}
	
	@Test
	public void parseStockInfo4JsonTest(){
		ProductSale productSale = services.productService.getProductSale(1200223478L);
		LOG.info(productSale.getStockQuantity());
//		String json = services.productSaleStockService.parseStockInfo4Json(productSale);
		String json = "";
		JSONArray jsonArray = JSONArray.fromObject(json);
		for(int i =0 ; i < jsonArray.size();i++){
			JSONObject jsonObject =  (JSONObject) jsonArray.get(i);
			Long id =  jsonObject.getLong("dc");
			int stock = jsonObject.getInt("stock");
			int virtual = jsonObject.getInt("virtual");
			int sale = jsonObject.getInt("sale");
			Code code = services.codeService.get(id);
			ProductSaleStock saleStock = productSale.getStockByDc(code);
			Assert.assertEquals(stock, saleStock.getStock());
			Assert.assertEquals(virtual, saleStock.getVirtual());
			Assert.assertEquals(sale, saleStock.getSales());
		}
		
	}
	

}

