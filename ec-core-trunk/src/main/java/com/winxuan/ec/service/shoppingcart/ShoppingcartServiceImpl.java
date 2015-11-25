package com.winxuan.ec.service.shoppingcart;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ShoppingcartDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.web.resolver.model.Track;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.util.RandomCodeUtils;

/**
 * description
 * 
 * @author renshiyong
 * @version 1.0,2011-7-15
 */

@Service("shoppingcartService")
@Transactional(rollbackFor = Exception.class)
public class ShoppingcartServiceImpl implements ShoppingcartService {

	@InjectDao
	private ShoppingcartDao shoppingcartDao;

	public Shoppingcart get(String id) {
		Shoppingcart shoppingcart = shoppingcartDao.get(id);

		return this.updateUseTime(shoppingcart);
	}

	/**
	 * 更新购物车使用时间
	 * 
	 * @param shoppingcart
	 */
	private Shoppingcart updateUseTime(Shoppingcart shoppingcart) {
		if (shoppingcart != null) {
			shoppingcart.setUseTime(new Date());
			shoppingcartDao.saveOrUpdate(shoppingcart);
		}
		return shoppingcart;
	}

	public boolean add(Shoppingcart shoppingcart, ProductSale productSale,
			int quantity) {
		boolean isNew = shoppingcartDao.get(shoppingcart.getId()) == null;
		if (isNew) {
			shoppingcartDao.save(shoppingcart);
		}
		boolean result = true;
		if (quantity <= 0) {
			shoppingcartDao.deleteItem(shoppingcart.getItem(productSale));
			result = shoppingcart.remove(productSale);
		} else {
			if (!productSale.getSaleStatus().getId()
					.equals(Code.PRODUCT_SALE_STATUS_ONSHELF)) {
				result = false;
			} else {
				ShoppingcartItem item = shoppingcart.getItem(productSale);
				if (item == null) {
					item = new ShoppingcartItem();
					item.setProductSale(productSale);
					shoppingcart.add(item);
				}
				if (quantity > productSale.getCanPurchaseQuantity()) {
					result = false;
					item.setQuantity(productSale.getCanPurchaseQuantity());
				} else {
					item.setQuantity(quantity);
				}
				shoppingcartDao.saveOrUpdateItem(item);
			}
		}
		return result;
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ShoppingcartItem getItem(Long id) {
		return shoppingcartDao.getItem(id);
	}

	@Override
	public boolean removeProduct(Shoppingcart shoppingcart,
			ProductSale productSale) {
		boolean result;
		this.shoppingcartDao.deleteItem(shoppingcart.getItem(productSale)
				.getId());
		result = shoppingcart.remove(productSale);
		return result;
	}

	@Override
	public Shoppingcart findShoppingcartByCustomer(Customer customer) {
		List<Shoppingcart> result = this.shoppingcartDao
				.findShoppingcartByCustomer(customer.getId(), MagicNumber.ONE);
		Shoppingcart shoppingcart = null;
		if (result != null && result.size() > MagicNumber.ZERO) {
			shoppingcart = result.get(0);
			shoppingcart = this.updateUseTime(shoppingcart);
		} else {
			shoppingcart = new Shoppingcart();
			shoppingcart.setId(RandomCodeUtils.create(Track.COOKIE_VALUE_MODE,
					Track.COOKIE_VALUE_LENGTH, true));
			shoppingcart.setCustomer(customer);
			shoppingcart.setCreateTime(new Date());
			shoppingcart.setUseTime(new Date());
			this.shoppingcartDao.save(shoppingcart);
		}
		return shoppingcart;
	}

}
