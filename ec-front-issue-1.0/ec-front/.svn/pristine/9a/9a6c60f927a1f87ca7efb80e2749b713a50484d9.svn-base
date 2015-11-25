package com.winxuan.ec.front.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.winxuan.framework.util.security.MD5Utils;




/**
 *
 * @author not attributable
 * @version 1.0
 */
public  class HttpUtil {
	
	 private static final Log LOG = LogFactory.getLog(HttpUtil.class);
	 private static final String UPDATE_PASSWORD_9YUE = "http://www.9yue.com/updatePwd.html";
	 private static final String COOKIE_9YUE = "http://www.9yue.com/getCookie.html";
		
	public static boolean updatePwdYue(Long userId,String password,String passwordConfirm)
	{
		Map<String,String> params = new HashMap<String, String>();
		params.put("winId", userId.toString());
		params.put("password", password);
		params.put("passwordConfirm", passwordConfirm);
		String result = doPostHttpRequest(UPDATE_PASSWORD_9YUE,params);
		try {
			JSONObject root = new JSONObject(result);
			if("0".equals(root.get("result")))
			{
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return false;
	}
	/**
	 * 种cookie
	 * @param url
	 * @param params
	 * @return
	 */
	public static void doCookie()
	{
		Map<String,String> params = new HashMap<String, String>();
		String result = doPostHttpRequest(COOKIE_9YUE,params);
		JSONObject root;
		try {
			root = new JSONObject(result);
			String id = root.get("id").toString();
			if(!"".equals(id))
			{
				Long userId = Long.valueOf(id);
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
		}
		
	
		
		
	}
	
	
	
	@SuppressWarnings("finally")
	public static String doPostHttpRequest(String url,Map<String, String> params) {
		String results = "";
		
		
		HttpClient httpclient = new DefaultHttpClient();
		try {
			//获得HttpPost对象
			HttpPost post = new HttpPost(url);
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
			for (Entry<String, String> en : params.entrySet()) {
				//建立一个NameValuePair数组，用于存储欲传送的参数  
				paramsList.add(new BasicNameValuePair(en.getKey(), en.getValue()));
			}
			post.setEntity(new UrlEncodedFormEntity(paramsList, HTTP.UTF_8));
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 200);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 200);
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity(); // 获取响应实体   
			if (null != entity) {
				results = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); // Consume response content   
			}
		} catch (Exception e) {
			LOG.warn("Can't httpclient error " + e.getMessage());
		} finally {
			httpclient.getConnectionManager().shutdown(); // 关闭连接,释放资源   
			return results;
		}
	}
	public static String checkValidate(Long userID){
		   String key="ewithf#$ewitr3495u032rujfsdg3@#";
		   return MD5Utils.encryptWithKey(userID.toString()+key);
		}
}
