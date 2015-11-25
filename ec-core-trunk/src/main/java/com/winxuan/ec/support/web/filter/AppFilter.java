package com.winxuan.ec.support.web.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.winxuan.ec.model.version.VersionInfo;
import com.winxuan.ec.service.version.VersionService;
import com.winxuan.framework.cache.CacheManager;
import com.winxuan.framework.util.security.MD5Utils;

/**
 * @author zhousl
 *
 * 2013-4-17
 */
public class AppFilter implements Filter{

	private static final int VERSION_SIZE = 2;
	
	private static final int SYSTEM_ANDROID = 1;

	private static final int SYSTEM_IOS = 2;

	private static final Integer CACHE_DAY = 3600 * 24;

	private static final String IOS_CACHE_KEY = "IOS_CACHE_KEY";
	
	private static final String ANDROID_CACHE_KEY = "ANDROID_CACHE_KEY";
	
	private VersionService versionService;
	
	private CacheManager cacheManager;

	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		Map<String, Object> attrList = new HashMap<String, Object>();
		if(cacheManager.get(MD5Utils.encryptWithKey(IOS_CACHE_KEY)) == null || cacheManager.get(MD5Utils.encryptWithKey(ANDROID_CACHE_KEY)) == null){
			initAppCache();
		}
		attrList.put("android", (VersionInfo)cacheManager.get(MD5Utils.encryptWithKey(ANDROID_CACHE_KEY)));
		attrList.put("ios", (VersionInfo)cacheManager.get(MD5Utils.encryptWithKey(IOS_CACHE_KEY)));
		request.setAttribute("timestamp", new Date().getTime());
		request.setAttribute("attrList", attrList);
		chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		versionService = context.getBean(VersionService.class);
		if (versionService == null) {
			throw new ServletException("versionService is not initialized");
		}
		cacheManager = context.getBean(CacheManager.class);
		if(cacheManager == null){
			throw new ServletException("cacheManager is not initialized");
		}
		initAppCache();
	}

	private void initAppCache() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("latest", true);
		List<VersionInfo> versionInfoList = versionService.find(parameters, null, (short)0);
		if(CollectionUtils.isNotEmpty(versionInfoList) && versionInfoList.size() == VERSION_SIZE){
			for(VersionInfo versionInfo : versionInfoList){
				if(versionInfo.getSystem() == SYSTEM_ANDROID){
					cacheManager.put(MD5Utils.encryptWithKey(ANDROID_CACHE_KEY), versionInfo, CACHE_DAY);
				}else if(versionInfo.getSystem() == SYSTEM_IOS){
					cacheManager.put(MD5Utils.encryptWithKey(IOS_CACHE_KEY), versionInfo, CACHE_DAY);
				}
			}
		}
	}

}
