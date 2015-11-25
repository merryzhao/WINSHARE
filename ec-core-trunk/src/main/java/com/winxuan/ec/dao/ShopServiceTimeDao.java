package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.shop.ShopServiceTime;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
/**
 * 
 * @author sunflower
 *
 */
public interface ShopServiceTimeDao {

	@Query("from ShopServiceTime s where s.shop.id = ?")
	List<ShopServiceTime> find(Long id);

	@Save
	void save(ShopServiceTime shopServiceTime);

	@Update
	void update(ShopServiceTime shopServiceTime);

}
