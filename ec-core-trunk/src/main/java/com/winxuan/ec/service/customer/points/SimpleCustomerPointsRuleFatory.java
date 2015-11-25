package com.winxuan.ec.service.customer.points;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.winxuan.ec.dao.CustomerAccountDao;
import com.winxuan.ec.dao.CustomerPointsDao;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.product.ProductSalePerformance;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 用户积分规则工厂简单实现类
 * 
 * @author sunflower
 * 
 */
@Service("CustomerPointsRuleFactory")
public class SimpleCustomerPointsRuleFatory implements
		CustomerPointsRuleFactory, Serializable {

	private static final long serialVersionUID = -1706821030779898396L;

	@InjectDao
	private CustomerAccountDao customerAccountDao;

	@InjectDao
	private CustomerPointsDao customerPointsDao;

	@Override
	public CustomerPointsRule createOrderCustomerPointsRule(Customer customer,
			Order order) {
		return new OrderCustomerPointsRule(customer, order, customerAccountDao,
				customerPointsDao);
	}

	@Override
	public CustomerPointsRule createFavoriteCustomerPointsRule(Customer customer) {
		return new FavoriteCustomerPointsRule(customer, customerAccountDao,
				customerPointsDao);
	}

	@Override
	public CustomerPointsRule createCommentCustomerPointsRule(
			Customer customer, ProductSalePerformance productSalePerformance) {
		return new CommentCustomerPointsRule(customer, customerAccountDao,
				customerPointsDao, productSalePerformance);
	}

	@Override
	public CustomerPointsRule createCollectCustomerPointsRule(Customer customer) {
		return new CollectCustomerPointsRule(customer, customerAccountDao,
				customerPointsDao);
	}

	@Override
	public CustomerPointsRule createOrderConfirmCustomerPointsRule(
			Customer customer, Order order) {
		return new OrderConfirmCustomerPointsRule(customer, customerAccountDao,
				customerPointsDao, order);
	}

	@Override
	public CustomerPointsRule createActivityCustomerPointsRule(Customer customer) {
		throw new UnsupportedOperationException("not implements!!!");
	}

	@Override
	public CustomerPointsRule createShoppingcartCustomerPointsRule(
			Shoppingcart shoppingcart) {

		return new ShoppingcartCustomerPointsRule(shoppingcart);
	}

	@Override
	public CustomerPointsRule createShoppingcartItemCustomerPointsRule(
			ShoppingcartItem shoppingcartItem) {
		return new ShoppingcartItemCustomerPointsRule(shoppingcartItem);
	}

	@Override
	public CustomerPointsRule createReturnOrderCustomerPointsRule(Order order) {
		return new ReturnOrderCustomerPointsRule(order.getCustomer(),
				customerAccountDao, customerPointsDao, order);
	}

}
