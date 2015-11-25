/*
 * @(#)CmDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.cm.CmsConfig;
import com.winxuan.ec.model.cm.Element;
import com.winxuan.ec.model.cm.Fragment;
import com.winxuan.ec.model.cm.Link;
import com.winxuan.ec.model.cm.News;
import com.winxuan.ec.model.cm.Text;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
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
 * @author liuan
 * @version 1.0,2011-10-26
 */
public interface CmDao {

	@Get
	Fragment getFragment(Long id);

	@Get
	Element getElement(Long id);

	@Delete
	void deleteElement(Element element);

	@Get
	News getNews(Long id);

	@Get
	Link getLink(Long id);

	@Get
	Text getText(Long id);
	
	@Get
	CmsConfig getCmsConfig(Long id);

	@Save
	void saveFragment(Fragment fragment);

	@Update
	void updateFragment(Fragment fragment);

	@SaveOrUpdate
	void saveOrUpdateNews(News news);

	@SaveOrUpdate
	void saveOrUpdateLink(Link link);

	@SaveOrUpdate
	void saveOrUpdateText(Text text);

	@Query(value = "DELETE FROM cm_element WHERE fragment = ?", sqlQuery = true, executeUpdate = true)
	void clearFragment(Long id);

	@Query("from Fragment as f where f.page=? and f.index =?")
	Fragment getFragmentByContext(String page, long index);

	@Query("from Fragment as f where f.page=? ")
	@OrderBys({ @OrderBy("f.index asc") })
	List<Fragment> getFragmentsByContext(String page, @Order Short sort);

	@Query("from Fragment f")
	@Conditions({ @Condition("f.name =:name"), @Condition("f.type = :type"),
			@Condition("f.rule = :rule"), @Condition("f.page = :page"),
			@Condition("f.imageType = :imageType"),
			@Condition("f.index = :index"),
			@Condition("f.id = :id")
	})
	@OrderBys({
		@OrderBy("f.id desc"),
		@OrderBy("f.id asc")
		
	})
	List<Fragment> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination,@Order Short sort);
	@Query("from CmsConfig f")
	List<CmsConfig> findCmsConfig();

}
