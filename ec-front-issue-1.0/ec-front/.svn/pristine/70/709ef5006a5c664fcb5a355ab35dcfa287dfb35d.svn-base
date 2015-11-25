package com.winxuan.ec.front.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.version.VersionInfo;
import com.winxuan.ec.service.version.VersionService;

/**
 * @author zhousl
 *
 * 2013-4-16
 */
public class AppInterceptor implements HandlerInterceptor{

	private static final int VERSION_SIZE = 2;
	
	private static final int SYSTEM_ANDROID = 1;

	private static final int SYSTEM_IOS = 2;

	private static final String SYSTEM = "system";
	
	@Autowired
	private VersionService versionService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mav) throws Exception {
		if(mav != null){
			Map<String, Object> attrList = new HashMap<String, Object>();
			String userAgent = request.getHeader("User-Agent");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("latest", true);
			List<VersionInfo> versionInfoList = versionService.find(parameters, null, (short)0);
			if(CollectionUtils.isNotEmpty(versionInfoList) && versionInfoList.size() == VERSION_SIZE){
				for(VersionInfo versionInfo : versionInfoList){
					if(versionInfo.getSystem() == SYSTEM_ANDROID){
						attrList.put("android", versionInfo);
					}else if(versionInfo.getSystem() == SYSTEM_IOS){
						attrList.put("ios", versionInfo);
					}
				}
				mav.addObject("attrList", attrList);
			}
			if(userAgent.indexOf("AppleWebKit") != -1 && userAgent.indexOf("Mobile") != -1){//移动终端
				if(userAgent.indexOf("Android") != -1){
					attrList.put(SYSTEM, SYSTEM_ANDROID);
				}else if(userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("iPad") != -1){
					attrList.put(SYSTEM, SYSTEM_IOS);
				}
			}
			mav.addObject("timestamp", new Date().getTime());
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		return true;
	}



	
}
