package com.winxuan.ec.front.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;



/**
 * 
 * @author youwen
 *
 */
public class MobileFilter implements Filter {
	
	private static String configPath = "";
	private static String contentPath = "";
	private static Object[] mconfig = {};
	private static Object[] exceptUrl = {};
	private static final String M_URL = "http://m.winxuan.com";
	private static final String ROOT_PATH = "/";
	private static final String MOBILE_KEY = "_mobile";
	private static final String REFUSH_KEY = "_refush";
	private static final String FRONT_KEY = "_www";
	private static final String MATCH_METHOD = "product";
	private static final String INIT_MOBILE_CONFIG = "configPath";
	private Logger log = Logger.getLogger(MobileFilter.class);

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		try{
			String userAgent = httpRequest.getHeader("User-Agent");
			userAgent = userAgent.toLowerCase();
			Map<String,Object> map = httpRequest.getParameterMap();
			//应用路径读取初始化config参数,
			if(mconfig.length==0||exceptUrl.length==0||map.containsKey(REFUSH_KEY)){
				contentPath = httpRequest.getSession().getServletContext().getRealPath("");
				this.loadConfig();
			}
			//url排除
			String requestUrl = httpRequest.getRequestURI();
			for (Object url : exceptUrl) {
				if(requestUrl.indexOf(url.toString())>=0&&!requestUrl.equals(ROOT_PATH)){
					filter.doFilter(request, httpResponse);
					return;
				}
			}
			//Agent判断,参数_www 转向www.winxuan ,_mobile 转向m.wixnuan
			if(map.containsKey(MOBILE_KEY)){
				httpResponse.sendRedirect(M_URL);
			}else if(!map.containsKey(FRONT_KEY)){
				for (Object equipment : mconfig) {
					if(userAgent.indexOf(equipment.toString())>=0){
						String[] method = httpRequest.getRequestURI().split("/");
						//必须符合/product/*格式
						if(method.length > 2&&MATCH_METHOD.equals(method[1])){
							httpResponse.sendRedirect(M_URL+httpRequest.getRequestURI());
						}else{
							httpResponse.sendRedirect(M_URL);
						}
						return;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.info(String.format("<mobile filter:type:%s \n method :%s>",
					httpRequest.getMethod(),httpRequest.getRequestURI()));
		}
		filter.doFilter(request, httpResponse);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		configPath = config.getInitParameter(INIT_MOBILE_CONFIG);
	}
	
	private void loadConfig(){
		String path = contentPath + configPath;
		log.info("load mobile filter config-path:"+path);
		File file = new File(path);
		Reader reader =null;
		String config = "";
		try {
			reader =new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) !=-1) {
				if (((char) tempchar) !='\r') {
					config += (char) tempchar;
				}
			}
			reader.close();
			readerProperties(config);
	    } catch (Exception e) {
	       log.error(String.format("<reader file:%s \n error info:%s>", path,e));
	    }
	}
	
	private void readerProperties(String file){
		JSONArray array =JSONArray.fromObject(file);
		for (Object object : array) {
			JSONObject obj = (JSONObject)object;
			if(obj.containsKey("mobile")){
				//mobile Agent
				JSONArray mobile = (JSONArray)obj.get("mobile");
				mconfig = mobile.toArray();
			}
			if(obj.containsKey("exceptUrl")){
				JSONArray url = (JSONArray)obj.get("exceptUrl");
				//exceptUrl 
				exceptUrl = url.toArray();
			}
		}
	}

}
