package com.winxuan.ec.task.job.search;

import java.io.IOException;
import java.io.Serializable;

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
 * 全量更新
 * @author caoyouwen
 *
 */
@Component("searchUpdate")
public class SearchUpdate implements Serializable, TaskAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7623451286374401130L;

	private static final Log LOG = LogFactory.getLog(SearchUpdate.class);
	
	private static String spellCheckForCleanUrl;
	private static String searchForCleanUrl;
	@Value("${solrServerUrl}")
	private  String solrServerUrl;

	@Autowired
	private SearchDao searchDao;
	@PostConstruct
	private void init() {
		spellCheckForCleanUrl = "http://"
				+ solrServerUrl
				+ "/spellcheck/select?qt=%2Fdataimport&clean=true&commit=true&command=full-import";
		searchForCleanUrl = "http://"
				+ solrServerUrl
				+ "/search/select?qt=%2Fdataimport&clean=true&commit=true&command=full-import";
	}

	@Override
	public String getName() {
		return "searchUpdate";
	}

	
	@Override
	public String getDescription() {
		return "全量更新";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_SEARCH;
		
	}

	/**
	 * 每小时更新一次
	 * 根据时间戳来获取（新增,更新,删除）的商品数据,并导入search_update表
	 * 
	 */
	@Override
	public void start(){
			try {
			    searchDao.updateQuery();
			    request(spellCheckForCleanUrl);
			    request(searchForCleanUrl);
			} catch(Exception e){
				LOG.info("insert_sql 参数错误！"+e);
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
}
