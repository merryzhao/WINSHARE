package com.winxuan.ec.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winxuan.framework.loadbalance.Router;
import com.winxuan.framework.loadbalance.router.Statistics;
import com.winxuan.framework.loadbalance.router.UseInfomation;

/**
 *  系统当前运行信息Controller
 * @author Heyadong
 * @version 1.0 , 2011-12-7
 */
@Controller
@RequestMapping(value = "/system")
public class SystemController {
	
	@Autowired(required=false)
	private Router router;
	
	/**
	 * 测控信息,暂时将监控写死,目前还不确定监控参数,样式
	 * @param response
	 * @param request
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void view(ServletResponse response,ServletRequest request) throws Exception{
		if (router != null && "AdminWX".equals(request.getParameter("admin"))){
			Map<DataSource, Statistics> map = router.getDataSourceStatus().getStatus();
				for (Map.Entry<DataSource, Statistics> entry : map.entrySet()){
					StringBuilder sb = new StringBuilder();
					String url = "";
					Field urlField = entry.getKey().getClass().getDeclaredField("url");
					urlField.setAccessible(true);
					url = urlField.get(entry.getKey()).toString();
					
					Map<Method, UseInfomation> useMap = entry.getValue().getUseInfo();
					sb.append("\n  datasourceID:").append(entry.getValue().getId())
						.append("  url:").append(url)
						.append("  fail:").append(entry.getValue().getFailCount());
					for (Map.Entry<Method, UseInfomation> useEntry : useMap.entrySet()){
						sb.append("\n  Method:" + useEntry.getKey().toString())
							.append("  useCount:").append(useEntry.getValue().getCount())
							.append("  AvgTime:").append(useEntry.getValue().getAvgTime())
							.append("  MaxTime:" + useEntry.getValue().getMaxTime());
					}
						response.getWriter().println(sb.toString());
				}
		} else {
				request.getRequestDispatcher("404").forward(request, response);
		}
	}
}
