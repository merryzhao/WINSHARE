package com.winxuan.ec.service.customer.points;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 用户查询购物车积分
 * @author sunflower
 *
 */
public class ShoppingcartCustomerPointsRule extends CustomerPointsRule {
	
	static final BigDecimal POINTS_PER_YUAN = new BigDecimal(MagicNumber.TEN);
	static final BigDecimal SILVER_POINTS_PER_YUAN = new BigDecimal(MagicNumber.TEN*MagicNumber.SILVER_POINTS_WEIGHT);
	static final BigDecimal GOLD_POINTS_PER_YUAN = new BigDecimal(MagicNumber.TEN*MagicNumber.GOLD_POINTS_WEIGHT);
	
	Shoppingcart shoppingcart;

	public ShoppingcartCustomerPointsRule(Shoppingcart shoppingcart) {
		super();
		this.shoppingcart = shoppingcart;
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
		Set<ShoppingcartItem> shoppingcartItems = shoppingcart.getItemList();
		Iterator<ShoppingcartItem> it = shoppingcartItems.iterator();
		int points = 0;
		while(it.hasNext()){
			ShoppingcartItem shoppingcartItem = it.next();
			ProductSale productSale = shoppingcartItem.getProductSale();
			Product product = productSale.getProduct();
			points += ShoppingcartItemCustomerPointsRule.generatePoints(shoppingcartItem.getSalePrice(),product,shoppingcart.getCustomer(),shoppingcartItem.getQuantity());
		}
		return points;
	}

}
