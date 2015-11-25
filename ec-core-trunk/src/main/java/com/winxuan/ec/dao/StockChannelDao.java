package com.winxuan.ec.dao;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.StockChannel;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * @author liou
 * @version 2013-9-11:下午7:29:20
 * 
 */
public interface StockChannelDao {

	@Get
	StockChannel get(Long id);

	@Save
	void save(StockChannel stockChannel);
	
	@Query("from StockChannel sc")
	@Conditions({
		@Condition(" sc.productSale=:productSale "),
		@Condition(" sc.channel=:channel ")
	})
	StockChannel get(@Parameter("productSale") ProductSale productSale, @Parameter("channel") Channel channel);
	
	@Update
	void update(StockChannel stockChannel);
}
