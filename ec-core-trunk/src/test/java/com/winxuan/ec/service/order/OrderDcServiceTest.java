//package com.winxuan.ec.service.order;
//
//import java.util.Set;
//
//import junit.framework.Assert;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.junit.Test;
//
//import com.winxuan.ec.exception.BusinessException;
//import com.winxuan.ec.exception.OrderDcMatchException;
//import com.winxuan.ec.model.area.Area;
//import com.winxuan.ec.model.code.Code;
//import com.winxuan.ec.model.order.Order;
//import com.winxuan.ec.model.order.OrderDistributionCenter;
//import com.winxuan.ec.model.order.OrderItem;
//import com.winxuan.ec.model.order.OrderItemStock;
//import com.winxuan.ec.model.product.ProductSale;
//import com.winxuan.ec.model.product.ProductSaleStock;
//import com.winxuan.ec.service.BaseTest;
//
///**
// * @author HideHai
// *
// */
//public class OrderDcServiceTest extends BaseTest{
//	
//	@Test
//	public void orderQuantityFillRateTest(){
//		Order order = services.orderService.get("20131021965193");
//		services.orderDcService.orderQuantityFillRate(order);
//	}
//	
//	@Test
//	public void saveOrderItemStockTest() throws BusinessException{
//		Order order = services.orderService.get("20131022965693");
//		Set<OrderItem> items = order.getItemList();
//		for(OrderItem orderItem : items){
//			ProductSale productSale = orderItem.getProductSale();
//			services.orderDcService.saveOrderItemStock(order);
//			OrderItemStock orderItemStock = orderItem.getOrderItemStock();
//			Assert.assertEquals(productSale.getStockQuantity(), orderItemStock.getStock());
//			String json = orderItemStock.getStockInfo();
//			JSONArray jsonArray = JSONArray.fromObject(json);
//			for(int i =0 ; i < jsonArray.size();i++){
//				JSONObject jsonObject =  (JSONObject) jsonArray.get(i);
//				Long id =  jsonObject.getLong("dc");
//				int stock = jsonObject.getInt("stock");
//				int virtual = jsonObject.getInt("virtual");
//				int sale = jsonObject.getInt("sale");
//				Code code = services.codeService.get(id);
//				ProductSaleStock saleStock = productSale.getStockByDc(code);
//				Assert.assertEquals(stock, saleStock.getStock());
//				Assert.assertEquals(virtual, saleStock.getVirtual());
//				Assert.assertEquals(sale, saleStock.getSales());
//			}
//		}
//	}
//	
//	@Test
//	public void createDcBjTest(){
//		Order order = services.orderService.get("20130929911195");
//		order.setDistributionCenter(null);
//		Area bjArea = new Area(152L);
//		order.getConsignee().setProvince(bjArea);
//		try {
//			OrderDistributionCenter center = services.orderDcService.createDc(order);
//			Assert.assertEquals(center.getDcOriginal().getId(), Code.DC_D818);
//		} catch (OrderDcMatchException e) {
//			Assert.fail(e.getMessage());
//		}
//	}
//	
//	@Test
//	public void createDcTjTest(){
//		Order order = services.orderService.get("20130929911195");
//		order.setDistributionCenter(null);
//		Area bjArea = new Area(177L);
//		order.getConsignee().setProvince(bjArea);
//		try {
//			OrderDistributionCenter center = services.orderDcService.createDc(order);
//			Assert.assertEquals(center.getDcOriginal().getId(), Code.DC_D818);
//		} catch (OrderDcMatchException e) {
//			Assert.fail(e.getMessage());
//		}
//	}
//	
//	@Test
//	public void createDcOtherTest(){
//		Order order = services.orderService.get("20130929911195");
//		order.setDistributionCenter(null);
//		Area bjArea = new Area(175L);
//		order.getConsignee().setProvince(bjArea);
//		try {
//			OrderDistributionCenter center = services.orderDcService.createDc(order);
//			Assert.assertEquals(center.getDcOriginal().getId(), Code.DC_8A17);
//		} catch (OrderDcMatchException e) {
//			Assert.fail(e.getMessage());
//		}
//	}
//	/**
//	 * 测试。未指定DC时，系统计算DC
//	 * 有仓库完全满足:4509741208
//	 * 有仓库完全满足且存在库存相等:20100101376446
//	 * 无仓库完全满足:20100101376455
//	 * 无仓库完全满足且存在库存相等：20100101376449
//	 */
//	@Test
//	public void testGetDc(){
//		Order order = services.orderService.get("20100101376455");
//		order.setDistributionCenter(null);
//		Area bjArea = new Area(174L);
//		order.getConsignee().setProvince(bjArea);
//		try {
//			OrderDistributionCenter center = services.orderDcService.createDc(order);
//			Code code = center.getDcDest();
//			Assert.assertEquals(center.getDcOriginal().getId(), Code.DC_8A17);
//		} catch (OrderDcMatchException e) {
//			Assert.fail(e.getMessage());
//		}
//		
//	}
//	
//	/**
//	 * 非文轩商品 ， 或虚拟订单
//	 * 非文轩商品:20110120321234
//	 * 虚拟订单:20111220001959  20130922532262
//	 */
//	@Test
//	public void testGetDcByMall(){
//		Order order = services.orderService.get("20111220001959");
//		order.setDistributionCenter(null);
//		Area bjArea = new Area(174L);
//		order.getConsignee().setProvince(bjArea);
//		try {
//			OrderDistributionCenter center = services.orderDcService.createDc(order);
//			Code code = center.getDcDest();
//			Assert.assertEquals(center.getDcOriginal().getId(), Code.DC_MALL);
//		} catch (OrderDcMatchException e) {
//			Assert.fail(e.getMessage());
//		}
//		
//	}
//
//	/**
//	 * 当当COD ,西南+重庆 成都发货 其余的由北京发货
//	 * 20130922532262 
//	 */
//	@Test
//	public void testGetDcDDCOD(){
//		Order order = services.orderService.get("20130922532262");
//		order.setDistributionCenter(null);
//		 Area bjArea = new Area(174L); // 上海
//		//Area bjArea = new Area(175L);//四川
//		order.getConsignee().setProvince(bjArea);
//		try {
//			OrderDistributionCenter center = services.orderDcService.createDc(order);
//			Code code = center.getDcDest();
//			Assert.assertEquals(center.getDcOriginal().getId(), Code.DC_D819);
//		} catch (OrderDcMatchException e) {
//			Assert.fail(e.getMessage());
//		}
//		
//	}
//	
//	/**
//	 * 创建DC计算集货明细
//	 * 20100101376450
//	 * 
//	 */
//	@Test
//	public void createOrderDc(){
//		Order order = services.orderService.get("20100101376450");
//		order.setDistributionCenter(null);
//		Area bjArea = new Area(174L);
//		order.getConsignee().setProvince(bjArea);
//		try {
//		OrderDistributionCenter center = services.orderDcService.createDc(order);
//		} catch (OrderDcMatchException e) {
//			Assert.fail(e.getMessage());
//		}
//	}
//	
//}

