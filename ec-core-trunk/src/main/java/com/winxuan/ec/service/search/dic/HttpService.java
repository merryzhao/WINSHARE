package com.winxuan.ec.service.search.dic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * http请求（get）处理类
 * @author sunflower
 *
 */
@Service("httpService")
@Transactional(rollbackFor = Exception.class)
public class HttpService implements  Serializable{
	

	public static final String PROXY_IN_USE = "Y";
	public static final String PROXY_UN_USE = "N";	

	private static final long serialVersionUID = -7142486324198087847L;
	private static final Log LOG = LogFactory.getLog(HttpService.class);



	private String proxy = null;
	private String proxyHost = null;
	private String proxyPort = null;
	private String proxyProtocol = null;

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyProtocol() {
		return proxyProtocol;
	}

	public void setProxyProtocol(String proxyProtocol) {
		this.proxyProtocol = proxyProtocol;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	/**
	 * 通过代理访问指定url
	 * 
	 * @param url
	 * @param proxyHost
	 * @param proxyPort
	 * @param proxyProtocol
	 * @return
	 */
	private List<String> getResponseViaProxy(String url, String proxyHost,
			String proxyPort, String proxyProtocol, Charset charset) {

		HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort),
				proxyProtocol);

		HttpClient httpClient = new DefaultHttpClient();
		BufferedReader br = null;
		List<String> list = new ArrayList<String>();
		try {
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);

			HttpGet httpget = new HttpGet(url);

			LOG.info("executing request to " + httpget.getURI() + " via "
					+ proxy);
			HttpResponse rsp = httpClient.execute(httpget);
			HttpEntity entity = rsp.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				br = new BufferedReader(
						new InputStreamReader(instream, charset));
				String tempbf = null;
				try {
					while ((tempbf = br.readLine()) != null) {
						list.add(tempbf);
					}
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}

		} catch (ClientProtocolException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
		return list;
	}

	/**
	 * 不通过代理访问
	 * 
	 * @param url
	 * @return
	 */
	private List<String> getResponse(String url, Charset charset) {
		// 构造HttpClient的实例
		HttpClient httpClient = new DefaultHttpClient();
		BufferedReader br = null;
		List<String> list = new ArrayList<String>();
		try {
			HttpGet httpget = new HttpGet(url);

			LOG.info("executing request " + httpget.getURI());

			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				br = new BufferedReader(
						new InputStreamReader(instream, charset));
				String tempbf = null;
				try {
					while ((tempbf = br.readLine()) != null) {
						list.add(tempbf);
					}
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		} catch (ClientProtocolException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
		return list;
	}

	public List<String> fetchAsList(String url, Charset charset) {

		if (charset == null) {
			charset = Charset.forName("UTF-8");
		}
		List<String> list = new ArrayList<String>();
		if (proxy != null && proxy.toUpperCase().equals(PROXY_IN_USE)) {
			list = getResponseViaProxy(url, proxyHost, proxyPort,
					proxyProtocol, charset);
		} else {
			list = getResponse(url, charset);
		}
		return list;
	}
}
