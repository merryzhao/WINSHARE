/*
 * @(#)NewsDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.cm.News;
import com.winxuan.framework.dynamicdao.annotation.Get;
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
 * @author  huangyixiang
 * @version 2011-12-8
 */
public interface NewsDao {
	
	@Get
	News get(Long id);
	
	@Query("from News n")
	@Conditions({ @Condition("n.type = :type") })
	@OrderBys({ @OrderBy("n.createTime desc") })
	List<News> find(@ParameterMap Map<String, Object> parameters,
			@Order Short sort, @Page Pagination pagination);
	
	@Query(value="DELETE FROM cm_news WHERE id = ?",sqlQuery=true,executeUpdate=true)
	void delNews(Long id);
}
