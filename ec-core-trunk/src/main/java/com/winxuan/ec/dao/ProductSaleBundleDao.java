package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.product.ProductSaleBundle;
import com.winxuan.ec.model.product.ProductSaleBundleLog;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 
 * @author juqkai(juqkai@gmail.com)
 */
public interface ProductSaleBundleDao {
	@Get
	ProductSaleBundle get(Long id);

	@Save
	void save(ProductSaleBundle bundleSale);

	/**
	 * 保存日志
	 * 
	 * @param log
	 */
	@Save
	void save(ProductSaleBundleLog log);

	@Update
	void update(ProductSaleBundle bundleSale);

	@Delete
	void del(ProductSaleBundle sb);

	@Query("from ProductSaleBundleLog bl")
	@Conditions({ @Condition("bl.master.id = :masterId") })
	List<ProductSaleBundleLog> listLogForMaster(
			@Parameter("masterId") Long masterId);

	@Query("from ProductSaleBundle p where p.master.id =?")
	List<ProductSaleBundle> find(Long productSaleId);
}
