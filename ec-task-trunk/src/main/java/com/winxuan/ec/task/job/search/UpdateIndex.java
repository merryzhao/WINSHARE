/*
 * @(#)UpdateIndex
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.search;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.dao.search.SearchDao;
import com.winxuan.ec.task.exception.search.SolrRequestException;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * description
 * 
 * @author huangyixiang
 * @version 1.0,2011-11-3
 */
@Component("updateIndex")
public class UpdateIndex implements Serializable, TaskAware {

	private static final long serialVersionUID = -1842568962092864156L;
	private static final Log LOG = LogFactory.getLog(UpdateIndex.class);
	
	private static final int SEARCH_INDEX_COUNT = 300000;
	// private static String solrServerUrl = "10.1.2.51:8080";
	private static String searchUrl;
	private static String searchForCleanUrl;
	private static String spellCheckUrl;
	private static String spellCheckForCleanUrl;

	@Value("${solrServerUrl}")
	private  String solrServerUrl;

	@Autowired
	private SearchDao searchDao;
	@PostConstruct
	private void init() {
		searchUrl = "http://"
				+ solrServerUrl
				+ "/search/select?qt=%2Fdataimport&clean=false&commit=true&command=delta-import";
		searchForCleanUrl = "http://"
				+ solrServerUrl
				+ "/search/select?qt=%2Fdataimport&clean=true&commit=true&command=full-import";
		spellCheckUrl = "http://"
				+ solrServerUrl
				+ "/spellcheck/select?qt=%2Fdataimport&clean=false&commit=true&command=full-import";
		spellCheckForCleanUrl = "http://"
				+ solrServerUrl
				+ "/spellcheck/select?qt=%2Fdataimport&clean=true&commit=true&command=full-import";
	}
	/**
	 * 每次启动的时候监听（每隔5分钟执行一次，最多检测30次）当前search_index表是否已经创建完成，如果完成启动索引并终止监听 索引策略：
	 * 每周一进行clean=true的索引； 每周二到周日进行clean=false的索引
	 */
	public void start() {
		Date dateStart = null;
		Date now = new Date();
		DateFormat format = new SimpleDateFormat(
				"yyyy:MM:dd");
		DateFormat parse = new SimpleDateFormat(
				"yyyy:MM:dd HH:mm:ss");
		String dateString = format.format(now)
				+ " 00:00:00";
		try {
			dateStart = parse.parse(dateString);
		} catch (ParseException e) {
			LOG.error(e.getMessage(), e);
			dateStart = new Date();
		}
		int number = 30;
		while (true) {
			number--;
			if (number <= 0) {
				LOG.info("超过了检测次数后search_index表中数据依然未准备好，请联系DBA");
				break;
			}
//		
				Date date = new Date();
				Calendar aCalendar = Calendar.getInstance();
				aCalendar.setTime(date);
				int week = aCalendar.get(Calendar.DAY_OF_WEEK);
				if (week == 2) {// 星期一
					if (searchDao.isSearchIndexFinished(dateStart)) {
					    index(true);
					    break;
					}
					else
					{
						try {
							Thread.sleep(1000 * 60 * 5);
						} catch (InterruptedException e) {
							LOG.error(e.getMessage(), e);
						}
					}
				} else {
					index(false);
					break;
				}
				
			}
//		else {
//				try {
//					Thread.sleep(1000 * 60 * 5);
//				} catch (InterruptedException e) {
//					LOG.error(e.getMessage(), e);
//				}
//			}
//		}

	}

	private void index(boolean create) {
		if (create) {
			request(spellCheckForCleanUrl);

			if (searchDao.searchIndexCount() >= SEARCH_INDEX_COUNT) {
				searchDao.updateAuthorString();
				request(searchForCleanUrl);
			} else {
				LOG.warn("search index count < " + SEARCH_INDEX_COUNT);
			}
		} else {
			if (searchDao.searchIndexCount() >= SEARCH_INDEX_COUNT) {
//				searchDao.updateAuthorString();
				request(searchUrl);
			} else {
				LOG.warn("search index count < " + SEARCH_INDEX_COUNT);
			}
		}
	}

	private void request(String url) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		for (int i = 0; i < 2; i++) {
			try {
				int status = client.executeMethod(method);
				if (status == HttpStatus.SC_OK) {
					return;
				} else {
					throw new SolrRequestException("更新索引请求失败: httpStatus = "
							+ status + "\n" + url);
				}
			} catch (HttpException e) {
				LOG.error(e.getMessage(), e);
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			} catch (SolrRequestException e) {
				LOG.error(e.getMessage(), e);
			} finally {
				method.releaseConnection();
			}

		}
	}

	@Override
	public String getName() {
		return "updateIndex";
	}

	@Override
	public String getDescription() {
		return "更新搜索索引";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_FRONT;
	}

}
