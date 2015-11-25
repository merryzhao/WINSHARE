package com.winxuan.ec.dao;
import java.util.List;
import java.util.Map;
import com.winxuan.ec.model.shop.ShopServiceNo;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;
/**
 * 
 * @author df.rsy
 *
 */
public interface ShopServiceNoDao {
	@Get
	ShopServiceNo get(Long id);

 	@SaveOrUpdate
	void saveOrupdate(ShopServiceNo shopServiceNo);
 	
 	@Save
	void save(ShopServiceNo shopServiceNo);
   	
 	@Delete
	void delete(ShopServiceNo shopServiceNo);

	@Query("from ShopServiceNo s")
	@Conditions({
		  @Condition("s.id = :id"),
		  @Condition("s.shop.id = :shopId"),
 		  @Condition("s.type.id = :type"),
 		  @Condition("s.serviceNo = :serviceNo")
	})
	List<ShopServiceNo> find(@ParameterMap Map<String, Object> parameters,@Page Pagination pagination);
	
}
