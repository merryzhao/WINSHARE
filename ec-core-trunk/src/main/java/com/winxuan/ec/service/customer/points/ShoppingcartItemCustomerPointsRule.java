package com.winxuan.ec.service.customer.points;

import java.math.BigDecimal;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 用户查询购物车条目积分
 * @author sunflower
 *
 */
public class ShoppingcartItemCustomerPointsRule extends CustomerPointsRule{
	
	static final BigDecimal POINTS_PER_YUAN = new BigDecimal(MagicNumber.TEN);
	static final BigDecimal SILVER_POINTS_PER_YUAN = new BigDecimal(MagicNumber.TEN*MagicNumber.SILVER_POINTS_WEIGHT);
	static final BigDecimal GOLD_POINTS_PER_YUAN = new BigDecimal(MagicNumber.TEN*MagicNumber.GOLD_POINTS_WEIGHT);
	
	ShoppingcartItem shoppingcartItem;

	public ShoppingcartItemCustomerPointsRule(ShoppingcartItem shoppingcartItem) {
		super();
		this.shoppingcartItem = shoppingcartItem;
	}

	@Override
	protected Long getCodeType() {
		return null;
	}

	@Override
	protected String getComment() {
		
		return null;
	}
	
	public int addPoints() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int generatePoints(Object object) {
		ProductSale productSale = shoppingcartItem.getProductSale();
		Product product = productSale.getProduct();
		return generatePoints(shoppingcartItem.getSalePrice(),product,shoppingcartItem.getShoppingcart().getCustomer(),shoppingcartItem.getQuantity() );
	}

	public static int generatePoints(BigDecimal money, Product product,Customer customer,int quantity) {
		
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
			points = (int) Math.ceil(money.multiply(SILVER_POINTS_PER_YUAN).doubleValue()*quantity);
		}else if(grade == Customer.GRADE_TYPE_GOLD){//金卡会员
			points = (int) Math.ceil(money.multiply(GOLD_POINTS_PER_YUAN).doubleValue()*quantity);
		}else if(grade == Customer.GRADE_TYPE_BRONZE){//普通会员
			points = (int) Math.ceil(money.multiply(POINTS_PER_YUAN).doubleValue()*quantity);
		}else{
			throw new UnsupportedOperationException();
		}
		return points;
	}

}
