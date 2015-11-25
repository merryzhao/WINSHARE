package com.winxuan.ec.service.customer.points;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

import com.winxuan.ec.dao.CustomerAccountDao;
import com.winxuan.ec.dao.CustomerPointsDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 用户订单成功赠送积分规则
 * @author sunflower
 *
 */
public class OrderCustomerPointsRule extends CustomerPointsRule {
	
	static final BigDecimal POINTS_PER_YUAN = new BigDecimal(MagicNumber.TEN);
	static final BigDecimal SILVER_POINTS_PER_YUAN = new BigDecimal(MagicNumber.TEN*MagicNumber.SILVER_POINTS_WEIGHT);
	static final BigDecimal GOLD_POINTS_PER_YUAN = new BigDecimal(MagicNumber.TEN*MagicNumber.GOLD_POINTS_WEIGHT);

	public OrderCustomerPointsRule(Customer customer, Order order, CustomerAccountDao customerAccountDao, CustomerPointsDao customerPointsDao) {
		super(customer,customerAccountDao,customerPointsDao, order);
	}

	@Override
	protected Long getCodeType() {
		return Code.CUSTOMER_POINTS_TYPE_ORDER_AWARD;
	}

	@Override
	protected String getComment() {
		
		return "成功购物赠送积分";
	}

	@Override
	public int generatePoints(Object object) {
		
		Order order = (Order)object;
		Set<OrderItem> orderItems = order.getItemList();
		Iterator<OrderItem> it = orderItems.iterator();
		int points = 0;
		//Set<OrderItem> newOrderItems = new HashSet<OrderItem>();
		while(it.hasNext()){
			OrderItem orderItem = it.next();
			ProductSale productSale = orderItem.getProductSale();
			Product product = productSale.getProduct();
			int score = generatePoints(orderItem.getBalancePrice(),product,customer);
			orderItem.setPoints(score);
			points += score*orderItem.getPurchaseQuantity();
			//newOrderItems.add(orderItem);
		}
		//order.setItemList(newOrderItems);
		object = order;
		return points;
	}

	
	private int generatePoints(BigDecimal money, Product product,Customer customer) {
		
		if(customer == null){//未登录用户
			customer = new Customer();
		}
		Long sort = product.getSort().getId();
		if(sort.compareTo(Code.PRODUCT_SORT_MERCHANDISE) == 0){//百货暂不参与积分奖励
			return 0;
		}
		int points = 0;
		short grade = customer.getGrade();
		if(grade == Customer.GRADE_TYPE_SILVER){//银卡会员
			points = (int) Math.ceil(money.multiply(SILVER_POINTS_PER_YUAN).doubleValue());
		}else if(grade == Customer.GRADE_TYPE_GOLD){//金卡会员
			points = (int) Math.ceil(money.multiply(GOLD_POINTS_PER_YUAN).doubleValue());
		}else if(grade == Customer.GRADE_TYPE_BRONZE){//普通会员
			points = (int) Math.ceil(money.multiply(POINTS_PER_YUAN).doubleValue());
		}else{
			throw new UnsupportedOperationException();
		}
		return points;
	}
	
	
	/**
	 * 获取的是发货的商品积分
	 */
	public int getPoints(){
		
		return order.getDeliveryTotalPoints()-order.getReturnTotalPoints();
		/*
		Set<OrderItem> orderItems = order.getItemList();
		Iterator<OrderItem> it = orderItems.iterator();
		int points = 0;
		while(it.hasNext()){
			OrderItem orderItem = it.next();
			points += orderItem.getPoints()*orderItem.getDeliveryQuantity();
		}
		return points;*/
	}
}
