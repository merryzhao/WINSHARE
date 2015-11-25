/*
 * @(#)NewsService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.news;

import java.util.List;

import com.winxuan.ec.model.cm.News;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  huangyixiang
 * @version 2011-12-8
 */
public interface NewsService {

	News find(Long id);
	 
	List<News> find(short type , short sort , Pagination pagination);
	
	@Query(value="DELETE FROM cm_news WHERE id = ?",sqlQuery=true,executeUpdate=true)
	void delNews(Long id);
}
