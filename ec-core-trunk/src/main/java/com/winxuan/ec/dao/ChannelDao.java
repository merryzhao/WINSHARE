/*
 * @(#)CategoryDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.channel.ChannelUploadHistory;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author chenlong
 * @version 1.0,2011-7-18
 */
public interface ChannelDao {

	@Get
	Channel get(Long id);

	@Save
	void save(Channel channel);

	@Update
	void update(Channel channel);

	@Query("from Channel c where c.parent.id =?")
	List<Channel> findChildren(Long id);

	@Query("from Channel c")
	@Conditions({ @Condition("c.parent.id=:parentId"),
			@Condition("c.usingApi=:usingApi"),
			@Condition("c.available=:available") })
	List<Channel> find(@ParameterMap Map<String, Object> parameters);
	
	@Save
	void saveUploadHistory(ChannelUploadHistory uploadHistory);

	@Query("from ChannelUploadHistory c")
	@OrderBys(
	        @OrderBy("c.uploadtime desc")
	)
	List<ChannelUploadHistory> queryUploadHistory( 
	        @Page Pagination pagination,
            @Order short orderIndex);
	
	@Query("from ChannelUploadHistory c WHERE c.filename = ?")
	ChannelUploadHistory findHistoryFile(String filename);
	
	@Query("from Channel c WHERE c.parent.id in (105,1007)")
	List<Channel> findSupplyChannel();
}
