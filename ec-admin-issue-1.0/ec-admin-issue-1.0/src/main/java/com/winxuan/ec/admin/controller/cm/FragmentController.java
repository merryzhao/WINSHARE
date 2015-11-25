/*
 * @(#)FragmentController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.cm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.cm.Content;
import com.winxuan.ec.model.cm.Element;
import com.winxuan.ec.model.cm.Fragment;
import com.winxuan.ec.model.cm.Link;
import com.winxuan.ec.model.cm.News;
import com.winxuan.ec.model.cm.Text;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.cm.CmService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.removecache.RemoveCacheService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.UrlUtils;
import com.winxuan.framework.cache.refresh.CacheRefreshService;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-10-27
 */
@Controller
@RequestMapping(value = "/fragment")
public class FragmentController {

	private static final Log LOG = LogFactory.getLog(FragmentController.class);

	@Autowired
	private CmService cmService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private RemoveCacheService removeCacheService;
	@Autowired
	private CacheRefreshService cacheRefreshService;

	/**
	 * 清除缓存
	 * 
	 * @param url
	 * @return boolean
	 */
	private boolean clearCache(String url) {
		boolean result = false;
		try {
			result = cacheRefreshService.refreshPageCache(url);
			if(!result){
				LOG.warn(String.format("flush cache failure url:%s",url));
			}
			
		} catch (Exception e) {
			LOG.error("clear the cache Exception,url:" + url, e);
			result = false;
		}
		return result;
	}
	
	/**
	 * 清除片段缓存
	 * @param key
	 * @return
	 */
	private boolean cleanCacheByKey(String key){
		boolean result = false;
		try {
			result = removeCacheService.removeCacheByCacheKey(key);
			if(!result){
				LOG.warn(String.format("flush fragmentcache failure key:%s",key));
			}
		} catch (Exception e) {
			LOG.error("clear the cache Exception,key:" + key, e);
			result = false;
		}
		return result;
		
		
	}

	/**
	 * 更新fragment的名字,就单纯的为了更新,不要想太多
	 * 
	 * @param fragmentId
	 * @param name
	 * @return view
	 */
	@RequestMapping(value = "/{fragmentId}/save", method = RequestMethod.POST)
	public ModelAndView updateFragment(
			@PathVariable("fragmentId") Long fragmentId,
			@RequestParam("name") String name) {
		ModelAndView modelAndView = new ModelAndView("/fragment/save");
		Fragment fragment = cmService.getFragment(fragmentId);
		if (null != fragment) {
			fragment.setName(name);
			cmService.updateFragment(fragment);
			modelAndView.addObject("fragment", fragment);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} else {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);

		}
		return modelAndView;
	}

	@RequestMapping(value = "/{fragmentId}/quantity", method = RequestMethod.POST)
	public ModelAndView updateQuantity(
			@PathVariable("fragmentId") Long fragmentId,
			@RequestParam("q") int quantity) {
		ModelAndView modelAndView = new ModelAndView("/fragment/save");
		Fragment fragment = cmService.getFragment(fragmentId);
		if (null != fragment) {
			fragment.setQuantity(quantity);
			cmService.updateFragment(fragment);
			modelAndView.addObject("fragment", fragment);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} else {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);

		}
		return modelAndView;
	}

	/**
	 * 保存模块数据
	 * 
	 * @param fragmentId
	 * @param contentId
	 * @return
	 */
	@RequestMapping(value = "/{fragmentId}", method = RequestMethod.POST)
	public ModelAndView save(
			@PathVariable("fragmentId") Long fragmentId,
			@RequestParam(value = "ruleId", required = true) Short ruleId,
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			@RequestParam(value = "cachekey", required = false) String cachekey,
			@RequestParam(value = "contentIds", required = false) String[] contentIds,
			@RequestParam("url") String url) {
		ModelAndView modelAndView = new ModelAndView("/fragment/save");
		Fragment fragment = cmService.getFragment(fragmentId);
		Set<Element> elements = new HashSet<Element>();
		if (fragment != null) {
			cmService.clearFragment(fragment);
			short fragType = fragment.getType();
			Category category = categoryId == null ? null : categoryService
					.get(categoryId);
			if (ruleId == Fragment.RULE_MANUAL && contentIds != null) {
				Element element = null;
				Content content = null;
				if(fragType == Fragment.TYPE_PRODUCT && !check(contentIds)){
					modelAndView.setViewName("/fragment/save_Failure");
					modelAndView.addObject("message", "存在相同的商品数据");
					return modelAndView;
				}
				for (int i = 0; i < contentIds.length; i++) {
					Long contentId = Long.valueOf(contentIds[i]);
					if (fragType == Fragment.TYPE_PRODUCT) {
						content = productService.getProductSale(contentId);
					} else if (fragType == Fragment.TYPE_LINK) {
						content = cmService.getLink(contentId);
					} else if (fragType == Fragment.TYPE_NEWS) {
						content = cmService.getNews(contentId);
					} else if (fragType == Fragment.TYPE_TEXT) {
						content = cmService.getText(contentId);
					}
					else if (fragType == Fragment.TYPE_RANDOM) {
						  content = productService.getProductSale(contentId);
						}
					if (content == null) {
						continue;
					}
					element = new Element();
					element.setFragment(fragment);
					element.setSort(i + 1);
					element.setContent(content);
					elements.add(element);
				}
			}
			fragment.setRule(ruleId);
			fragment.setElements(elements);
			fragment.setCategory(category);
			this.cleanCacheByKey(cachekey);
			cmService.updateFragment(fragment);
			modelAndView.addObject("url", UrlUtils.getUrlHostReg(url));
			modelAndView.addObject("fragment", fragment);
			this.clearCache(url);
			return modelAndView;
		}
		modelAndView.setViewName("/fragment/save_Failure");
		modelAndView.addObject("message", "error");
		return modelAndView;
	}

	private boolean check(String[] contentIds) {
		for(int i=0;i<contentIds.length;i++ ){
			String one = contentIds[i];
			for(int k=i+1;k<contentIds.length;k++ ){
				String other = contentIds[k];
				if(one.equals(other)){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 保存/更新新闻
	 * 
	 * @param employee
	 *            添加人
	 * @param id
	 *            新闻ID，不传入新增
	 * @param title
	 *            新闻标题
	 * @param content
	 *            新闻内容
	 * @return 视图
	 */
	@RequestMapping(value = "/news", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateNews(@MyInject Employee employee,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "content", required = true) String content) {
		ModelAndView modelAndView = new ModelAndView(
				"/fragment/news/saveOrUpdate");
		News news = null; 
		if (id == null) {
			news = this.setNews(news, employee);
		} else { 
			news = cmService.getNews(id);
			if (news == null) {
				news = this.setNews(news, employee);
			}
		}
		news.setTitle(title);
		news.setContent(content);
		news.setUpdateTime(new Date());
		cmService.saveOrUpdateNews(news);
		modelAndView.addObject("news", news);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}

	private News setNews(News news, Employee employee) {
		news = new News();
		news.setCreateTime(new Date());
		news.setCreator(employee);
		return news;
	}

	@RequestMapping(value = "/link", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateLink(@MyInject Employee employee,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "src", required = false) String src,
			@RequestParam(value = "href", required = true) String href,
			@RequestParam(value = "desc", required = false) String desc
			) {
		ModelAndView modelAndView = new ModelAndView(
				"/fragment/link/saveOrUpdate");
		Link link = null;
		if (id == null) {
			link = new Link();
		} else {
			link = cmService.getLink(id);
		}
		link.setName(name);
		link.setTitle(title);
		link.setSrc(src);
		link.setHref(href);
		link.setDescription(desc);
		link.setImg(src != null);
		cmService.saveOrUpdateLink(link);
		modelAndView.addObject("link", link);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}

	@RequestMapping(value = "/text", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateText(@MyInject Employee employee,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "content", required = true) String content) {
		ModelAndView modelAndView = new ModelAndView(
				"/fragment/text/saveOrUpdate");
		Text text = null;
		if (id == null) {
			text = new Text();
		} else {
			text = cmService.getText(id);
		}
		text.setName(name);
		text.setContent(content);
		cmService.saveOrUpdateText(text);
		modelAndView.addObject("text", text);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
   
}
