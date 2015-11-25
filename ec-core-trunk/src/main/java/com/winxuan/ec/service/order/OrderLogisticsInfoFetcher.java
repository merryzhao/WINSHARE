/*
 * @(#)OrderLogisticsFinder.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.order;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.order.OrderLogistics;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-26
 */

@Component("orderLogisticsInfoFetcher")
public class OrderLogisticsInfoFetcher implements Serializable {

	
	private static final String QUERY_TEMPLATE = "/query?type=%s&postid=%s&id=1";
	private static final String DEFULT_API_URL = "http://api.kuaidi100.com";
	private static final int STATUS_EMPTY = 0;
	private static final long serialVersionUID = 2425967298132734632L;

	Logger log = LoggerFactory.getLogger(OrderLogisticsInfoFetcher.class);
	
	@Value("${core.logapiurl}")
	private String apiUrl = DEFULT_API_URL;


	/**
	 * 获得物流跟踪信息
	 * 
	 * @param deliveryCompany
	 *            配送公司代码
	 * @param deliveryCode
	 *            运输单号
	 * @return
	 * @throws OrderDeliveryLogisticsException 
	 */
	public List<OrderLogistics> fetch(String deliveryCompany,
			String deliveryCode){
		String queryUrl = String.format(apiUrl + QUERY_TEMPLATE,
				deliveryCompany, deliveryCode);
		String apiResult = null;
			try {
				apiResult = invokeApi(queryUrl);
				if (StringUtils.isNotBlank(apiResult)) {
					try {
						return parse(apiResult);
					} catch (JsonParseException e) {
						log.info(e.getMessage());
					}
				}
			} catch (ClientProtocolException e) {
				log.error(e.getMessage(), e);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		return null;
	}
	


	private  String invokeApi(String queryUrl) throws ClientProtocolException,
			IOException {
		log.info("kuaidi100:"+queryUrl);
		HttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet(queryUrl);
		try {
			HttpResponse response = httpClient.execute(request);
			if (response != null
					&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<OrderLogistics> parse(String json) throws JsonParseException{
		JSONObject jsonObject = JSONObject.fromObject(json,
				OrderLogisticsUtils.config());
		if (jsonObject != null && jsonObject.getInt("status")!=STATUS_EMPTY){
			List<OrderLogistics> result = new ArrayList<OrderLogistics>();
			List<JSONObject> list = null;
			try {
				list =  (List<JSONObject>)jsonObject.getJSONArray("data");	
			} catch (Exception e) {
				log.info("Json:"+json);
				log.info("JsonObject:"+jsonObject);
				log.info(e.getMessage());
				return null;
			}
			if(CollectionUtils.isNotEmpty(list)){
				for (JSONObject log : list) {
					OrderLogistics aLogistics = new OrderLogistics();
					aLogistics.setContext(log.get("context").toString());
					aLogistics.setTime(log.get("time").toString());
					result.add(aLogistics);
				}
			}
			return result;
		}
		return null;
	}

}
