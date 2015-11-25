package com.winxuan.ec.service.shop;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ShopServiceNoDao;
import com.winxuan.ec.dao.ShopServiceTimeDao;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopServiceNo;
import com.winxuan.ec.model.shop.ShopServiceTime;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author df.rsy
 *
 */
@Service("shopServiceNoService")
@Transactional(rollbackFor = Exception.class)
public class ShopServiceNoServiceImpl implements ShopServiceNoService {

	@InjectDao
	ShopServiceNoDao shopServiceNoDao;
	
	@InjectDao
	ShopServiceTimeDao shopServiceTimeDao;
	
	@Override
	public ShopServiceNo get(Long id) {
 		return shopServiceNoDao.get(id);
	}

	@Override
	public void saveOrupdate(ShopServiceNo shopServiceNo) {
		shopServiceNoDao.saveOrupdate(shopServiceNo);
	}

	@Override
	public void delete(ShopServiceNo shopServiceNo) {
		shopServiceNoDao.delete(shopServiceNo);
	}

	@Override
	public List<ShopServiceNo> find(Map<String, Object> parameters,
			Pagination pagination) {
 		return shopServiceNoDao.find(parameters, pagination);
	}

	@Override
	public void save(ShopServiceNo shopServiceNo) {
		shopServiceNoDao.save(shopServiceNo);
	}

	@Override
	public List<ShopServiceTime> find(Shop shop) {
		return shopServiceTimeDao.find(shop.getId());
	}

	@Override
	public void save(ShopServiceTime shopServiceTime) {
		shopServiceTimeDao.save(shopServiceTime);
	}

	@Override
	public void update(ShopServiceTime shopServiceTime) {
		shopServiceTimeDao.update(shopServiceTime);
	}

}
