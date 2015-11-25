/*
 * @(#)CmService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.cm;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.cm.CmsConfig;
import com.winxuan.ec.model.cm.Content;
import com.winxuan.ec.model.cm.Element;
import com.winxuan.ec.model.cm.Fragment;
import com.winxuan.ec.model.cm.Link;
import com.winxuan.ec.model.cm.News;
import com.winxuan.ec.model.cm.Text;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-10-26
 */
public interface CmService {

	Fragment getFragment(Long id);
	
	void saveFragment(Fragment fragment);
	
	void updateFragment(Fragment fragment);
	
	/**
	 * 清除片段内元素
	 * @param fragment
	 */
	void clearFragment(Fragment fragment);
	
	Element getElement(Long id);
	
	News getNews(Long id);
	
	Link getLink(Long id);
	
	Text getText(Long id);
	
	CmsConfig getCmsConfig(Long id);
	
	void saveOrUpdateLink(Link link);
	
	void saveOrUpdateNews(News news);
	
	void saveOrUpdateText(Text text);
	
	
	List<Content> findContent(Fragment fragment);
	
	/**
	 * 通过传入的Page和Index参数返回带有不同集合的Fragment
	 * @param fragment
	 * @return
	 */
	Fragment getFragmentByContext(Fragment fragment);
	
	/**
	 * 通过传入的Page参数返回带有不同集合的Fragment
	 * @param fragment
	 * @return
	 */
	List<Fragment> getFragmentsByContext(Fragment fragment);
	
	/**
	 * 查询.You know
	 * @param parameters
	 * @param pagination
	 * @param sort
	 * @return
	 */
	List<Fragment> find(Map<String, Object> parameters,Pagination pagination,Short sort);
	
	
	/**
	 * 查询.You know
	 * @param parameters
	 * @param pagination
	 * @return
	 */
	List<Fragment> find(Map<String, Object> parameters,Pagination pagination);
	
	/**		
	 * 获取fragmetn 的配置文件
	 * @param fragment
	 * @return
	 */
	Map<String, Object> getFragmentConfig(Fragment fragment);
	
	List<CmsConfig> findCmsConfig();
		
	List<Content> getRandomContentFromAllContent(List<Content> allContentsList,int size);
}
