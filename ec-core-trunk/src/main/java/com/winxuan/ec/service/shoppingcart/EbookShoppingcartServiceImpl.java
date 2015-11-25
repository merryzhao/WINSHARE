/*
 * @(#)ShoppingcartService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.shoppingcart;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.EbookShoppingcartDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shoppingcart.EbookShoppingcart;
import com.winxuan.ec.model.shoppingcart.EbookShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.web.resolver.model.Track;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.util.RandomCodeUtils;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-16 上午11:23:01 --!
 * @description:应该把两种购物车抽象出接口,这样Service方法只需实现相同的接口.but,时间有限.
 ******************************** 
 */

@Service("ebookShoppingcartService")
@Transactional(rollbackFor = Exception.class)
public class EbookShoppingcartServiceImpl implements EbookShoppingcartService {

	/**
	 * 电子书不存在库存
	 */
	private static final int ONE = 1;

	@InjectDao
	private EbookShoppingcartDao ebookShoppingcartDao;

	@Override
	public EbookShoppingcart get(String id) {
		EbookShoppingcart ebookShoppingcart = this.ebookShoppingcartDao.get(id);
		return this.updateUseTime(ebookShoppingcart);
	}

	@Override
	public boolean add(EbookShoppingcart ebookShoppingcart,
			ProductSale productSale) {
		boolean isNew = ebookShoppingcartDao.get(ebookShoppingcart.getId()) == null;
		if (isNew) {
			ebookShoppingcartDao.save(ebookShoppingcart);
		}
		if (!productSale.getSaleStatus().getId()
				.equals(Code.PRODUCT_SALE_STATUS_ONSHELF)) {
			return false;
		}
		
		if(!Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(productSale.getStorageType().getId())){
			return false;
		}

		EbookShoppingcartItem item = ebookShoppingcart.getItem(productSale);
		if (item == null) {
			item = new EbookShoppingcartItem();
			item.setProductSale(productSale);
			item.setQuantity(ONE);
			ebookShoppingcart.add(item);
		}
		ebookShoppingcartDao.saveOrUpdateItem(item);
		return true;
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public EbookShoppingcartItem getItem(Long id) {
		return this.ebookShoppingcartDao.getItem(id);
	}

	@Override
	public boolean removeProduct(EbookShoppingcart ebookShoppingcart,
			ProductSale productSale) {
		boolean result;
		EbookShoppingcartItem item = ebookShoppingcart.getItem(productSale);
		if(item==null){
			return false;
		}
		this.ebookShoppingcartDao.deleteItem(item.getId());
		result = ebookShoppingcart.remove(productSale);
		return result;
	}

	@Override
	public EbookShoppingcart findShoppingcartByCustomer(Customer customer) {
		List<EbookShoppingcart> result = this.ebookShoppingcartDao
				.findShoppingcartByCustomer(customer.getId(), MagicNumber.ONE);
		EbookShoppingcart shoppingcart = null;
		if (CollectionUtils.isNotEmpty(result)) {
			shoppingcart = result.get(0);
			shoppingcart = this.updateUseTime(shoppingcart);
		} else {
			shoppingcart = new EbookShoppingcart();
			shoppingcart.setId(RandomCodeUtils.create(Track.COOKIE_VALUE_MODE,
					Track.COOKIE_VALUE_LENGTH, true));
			shoppingcart.setCustomer(customer);
			shoppingcart.setCreateTime(new Date());
			shoppingcart.setUseTime(new Date());
			this.ebookShoppingcartDao.save(shoppingcart);
		}
		return shoppingcart;
	}
	
	
	@Override
	public void save(EbookShoppingcart ebookShoppingcart) {
	   this.ebookShoppingcartDao.save(ebookShoppingcart);
	}

	/**
	 * 更新购物车使用时间
	 * 
	 * @param shoppingcart
	 */
	private EbookShoppingcart updateUseTime(EbookShoppingcart ebookShoppingcart) {
		if (ebookShoppingcart != null) {
			ebookShoppingcart.setUseTime(new Date());
			ebookShoppingcartDao.saveOrUpdate(ebookShoppingcart);
		}
		return ebookShoppingcart;
	}

	@Override
	public void clear(Customer customer) {
		EbookShoppingcart ebookShoppingcart  = this.findShoppingcartByCustomer(customer);
		Set<EbookShoppingcartItem> ebookShoppingcartItem =ebookShoppingcart.getItemList();
		if(CollectionUtils.isEmpty(ebookShoppingcartItem)){
			return;
		}
		for (EbookShoppingcartItem item : ebookShoppingcartItem) {
			this.ebookShoppingcartDao.deleteItem(item.getId());
		}
		ebookShoppingcart.setItemList(null);
	}

	@Override
	public boolean removeProudct(Customer customer, ProductSale productSale) {
		EbookShoppingcart ebookShoppingcart = this.findShoppingcartByCustomer(customer);
		return this.removeProduct(ebookShoppingcart, productSale);
	}
}
