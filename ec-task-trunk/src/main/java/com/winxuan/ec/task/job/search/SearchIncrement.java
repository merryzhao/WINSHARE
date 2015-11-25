package com.winxuan.ec.task.job.search;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
 * 增量更新
 * @author caoyouwen
 *
 */
@Component("searchIncrement")
public class SearchIncrement  implements Serializable, TaskAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7946236603412542849L;
	
	private static String searchUrl;
	private static final Log LOG = LogFactory.getLog(SearchIncrement.class);
	/**
	 * 0-8点不进行增量
	 */
	private static final List<Integer> FREETIME = Arrays.asList(0,1,2,3,4,5,6,7,8);   

	@Autowired
	private SearchDao searchDao;
	
	@Value("${solrServerUrl}")
	private  String solrServerUrl;
	
	
	@PostConstruct
	private void init() {
		searchUrl = "http://"
				+ solrServerUrl
				+ "/search/select?qt=%2Fdataimport&clean=false&commit=true&command=delta-import";
	}
	

	@Override
	public String getName() {
		return "searchIncrement";
	}

	@Override
	public String getDescription() {
		return "增量更新";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_SEARCH;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		if(!FREETIME.contains(new Date().getHours())){
			searchDao.incrementQuery();
			LOG.info("请求增量:"+searchUrl);
			request(searchUrl);
		}
	}
	
	private void request(String url) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		for (int i = 0; i < 2; i++) {
			try {
				int status = client.executeMethod(method);
				if (status == HttpStatus.SC_OK) {
					LOG.info("增量完成.");
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
