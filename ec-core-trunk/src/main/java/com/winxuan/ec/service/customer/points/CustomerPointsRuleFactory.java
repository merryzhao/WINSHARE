package com.winxuan.ec.service.customer.points;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.product.ProductSalePerformance;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;


/**
 * 积分规则工厂类
 * @author sunflower
 *
 */
public interface CustomerPointsRuleFactory {

	/**
	 * 成功购物（订单状态为已确认）赠送积分
	 * @param customer
	 * @param order
	 * @return
	 */
	CustomerPointsRule createOrderCustomerPointsRule(Customer customer,Order order);
	
	/**
	 * 查询购物车中商品赠送的积分
	 * @param shoppingcart
	 * @return
	 */
	CustomerPointsRule createShoppingcartCustomerPointsRule(Shoppingcart shoppingcart);
	
	/**
	 * 查询购物车中商品赠送的积分
	 * @param shoppingcart
	 * @return
	 */
	CustomerPointsRule createShoppingcartItemCustomerPointsRule(ShoppingcartItem shoppingcartItem);
	
	/**
	 * 添加喜欢（顶）赠送积分
	 * @param customer
	 * @return
	 */
	CustomerPointsRule createFavoriteCustomerPointsRule(Customer customer);
	
	/**
	 * 添加评论赠送积分
	 * @param customer
	 * @return
	 */
	CustomerPointsRule createCommentCustomerPointsRule(Customer customer,ProductSalePerformance productSalePerformance);
	
	/**
	 * 添加收藏赠送积分
	 * @param customer
	 * @return
	 */
	CustomerPointsRule createCollectCustomerPointsRule(Customer customer);
	
	
	/**
	 * 确认收货赠送积分
	 * @param customer
	 * @return
	 */
	CustomerPointsRule createOrderConfirmCustomerPointsRule(Customer customer,Order order);
	
	
	/**
	 * 参加活动赠送积分
	 * @param customer
	 * @return
	 */
	CustomerPointsRule createActivityCustomerPointsRule(Customer customer);
	
	
	/**
	 * 退货减积分
	 * @param customer
	 * @return
	 */
	CustomerPointsRule createReturnOrderCustomerPointsRule(Order order);
	
	
}
