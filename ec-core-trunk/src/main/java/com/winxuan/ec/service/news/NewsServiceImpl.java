/*
 * @(#)NewsServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.news;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.NewsDao;
import com.winxuan.ec.model.cm.News;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * 
 * @author huangyixiang
 * @version 2011-12-8
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@InjectDao
	private NewsDao newsDao;

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public News find(Long id) {
		return newsDao.get(id);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<News> find(short type, short sort, Pagination pagination) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", type);
		return newsDao.find(parameters, sort, pagination);
	}

	@Override
	public void delNews(Long id) {
		this.newsDao.delNews(id);

	}

}
