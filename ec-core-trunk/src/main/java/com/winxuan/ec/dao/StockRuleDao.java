package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.product.StockRule;
import com.winxuan.ec.model.product.StockRuleDc;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**   
 * @author liou
 * @version 2013-9-4:下午5:13:03
 * 
 */
public interface StockRuleDao {
	
	@Get
	StockRule get(Long id);
	
	@Save
	void save(StockRule stockRule);
	
	@Update
	void update(StockRule stockRule);
	
	@Query("from StockRule s Order By  s.id desc")
	List<StockRule> find(@Page Pagination pagination);
	
	@Query("from StockRule s where s.channel.id=? and s.supplyType.id=?")
	StockRule get(long channelId, long supplyTypeId);
	
	@Delete
	void delete(StockRuleDc ruleDc);
	
	@Query("from StockRule s where s.channel.id=? and s.supplyType.id=?")
	boolean isSupplyAndChannel(Long channelId,Long supplyTypeId);
}
