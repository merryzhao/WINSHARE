/**
 * 
 */
package com.winxuan.ec.service.order;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;

import com.winxuan.ec.dao.OrderDao;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderInitException;
import com.winxuan.ec.exception.OrderStockException;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.BaseTest;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * @author john
 *
 */
@Component("abstractSequanceIdentifierGeneratorTest")
public class AbstractSequanceIdentifierGeneratorTest extends BaseTest{

	/**
	 * 模拟并发任务，向_order表中插入多个订单数据
	 * @throws PresentCardException  
	 * @throws PresentException 
	 * @throws CustomerAccountException 
	 * @throws OrderStockException 
	 * @throws OrderInitException 
	 */
	@InjectDao
	OrderDao orderDao;
	
	public void create() throws OrderInitException, OrderStockException, CustomerAccountException, PresentException, PresentCardException { 
		
		Date date1 = new Date();
		Date date2 = new Date();
		Date date3 = new Date();
		Order order = new Order();
		order.setDeliveryType(new DeliveryType(DeliveryType.EMS));
		order.setPayType(new Code(Code.ORDER_PAY_TYPE_FIRST_PAY));
		order.setStorageType(new Code(Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_SELLER));
		order.setSupplyType(new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL));
		order.setShop(new Shop(1L));
		order.setVirtual(false);
		order.setCreator(new User(1681407839L));
		order.setListPrice(new BigDecimal(150));
		order.setSalePrice(new BigDecimal(100));
		order.setSaveMoney(new BigDecimal(50));
		order.setDeliveryFee(new BigDecimal(0));
		order.setAdvanceMoney(new BigDecimal(0));
		order.setCustomer(new Customer(1681407839L));
		order.setCreateTime(date1);
		order.setProcessStatus(new Code(Code.ORDER_PROCESS_STATUS_PICKING));
		order.setPaymentStatus(new Code(Code.ORDER_PAY_STATUS_COMPLETED));
		order.setChannel(new Channel(104L));
		order.setLastProcessTime(date2);
		order.setTransferResult(new Code(Code.EC2ERP_STATUS_NEW));
		order.setDeliveryCode("7006476466");
		order.setDeliveryTime(date3);
		order.setDeliveryListPrice(new BigDecimal(150));
		order.setDeliverySalePrice(new BigDecimal(100));
		order.setVersion(1);
		order.setPurchaseQuantity(5);
		order.setPurchaseKind(1);
		order.setDeliveryQuantity(5);
		order.setDeliveryKind(1);
		order.setUnite(true);
		
 		orderDao.save(order);
	}
	
	@Test
	@Rollback(false)
	public void testSequalize() throws Exception{
		this.create();
	}
}
