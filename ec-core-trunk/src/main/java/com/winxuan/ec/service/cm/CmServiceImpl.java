/*
 * @(#)CmServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.cm;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CmDao;
import com.winxuan.ec.model.cm.CmsConfig;
import com.winxuan.ec.model.cm.CmsConstant;
import com.winxuan.ec.model.cm.Content;
import com.winxuan.ec.model.cm.Element;
import com.winxuan.ec.model.cm.Fragment;
import com.winxuan.ec.model.cm.Link;
import com.winxuan.ec.model.cm.News;
import com.winxuan.ec.model.cm.Text;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.fragment.Rule;
import com.winxuan.ec.support.web.pojo.ProductSearch;
import com.winxuan.ec.util.ReflectOperation;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.web.CookieUtils;
import com.winxuan.framework.util.web.WebContext;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-26
 */
@Service("cmService")
@Transactional(rollbackFor = Exception.class)
public class CmServiceImpl implements CmService {

	public static final String COOKIE_RANDOM_ROCOMMEND = "random_recommend";
	
	public static final String COOKIE_PATH = "/";	
	
	public static final long COOKIE_EXPIRE_TIME = 20 * 60 * 1000;
	
	public static final String COOKIE_DOMAIN = ".winxuan.com";
	
	public static final String RANDOM_GENERATE_TYPE = "1";
	
	private static final Log LOG = LogFactory.getLog(CmServiceImpl.class);

	@InjectDao
	private CmDao cmDao;

	@Autowired
	private ProductService productService;

	@Override
	public Fragment getFragment(Long id) {
		return cmDao.getFragment(id);
	}

	@Override
	public void updateFragment(Fragment fragment) {
		cmDao.updateFragment(fragment);
	}
	
	/**
	 * 取出bookId作为cookie值
	 * @param resultContents
	 * @return
	 */
	private StringBuilder getNewCookieValue(List<Content> resultContents){
		StringBuilder cookieValue = new StringBuilder();
		Iterator<Content> it = resultContents.iterator();
		while(it.hasNext()){
			Content currentContent = it.next();
			cookieValue.append(currentContent.getId()).append("&");
		}
		return cookieValue;
	}
	
	@Override
	public List<Content> getRandomContentFromAllContent(List<Content> allContentsList, int size){			
		Cookie cookie = CookieUtils.getCookie(COOKIE_RANDOM_ROCOMMEND);
		List<Long> readBookIdList = getReadBookIdListFromCookie(cookie);
		
		Collections.shuffle(allContentsList);
		List<Content> resultContentList = this.getRandom(readBookIdList, allContentsList, size);
		
		StringBuilder cookieValue = new StringBuilder();
		if(readBookIdList.size() + resultContentList.size() < allContentsList.size() && readBookIdList.size() > 0){
			//已读 + 新的  少于 总的，非新一轮 并且 不是首次推荐
			cookieValue.append(cookie.getValue());
		}
				
		cookieValue.append(getNewCookieValue(resultContentList));
		
		writeRandomRecomendCookie(cookieValue.toString());
		return resultContentList;
	}

	private List<Content> getRandom(List<Long> readBookIdList,
			List<Content> allContentsList, int targetSize) {
		List<Content> resultList = new ArrayList<Content>();
		List<Long> selectedContentIdList = new ArrayList<Long>();
		for(Content c : allContentsList){
			if(readBookIdList.contains(c.getId())){
				continue;
			}
			resultList.add(c);
			selectedContentIdList.add((Long) c.getId());
			if(resultList.size() == targetSize){
				break;
			}
		}
		if(resultList.size() < targetSize){
			resultList.addAll(this.getRandom(selectedContentIdList, allContentsList, targetSize - resultList.size()));
		}
		return resultList;
	}

	private List<Long> getReadBookIdListFromCookie(Cookie cookie) {
		if(cookie == null){
			return new ArrayList<Long>();
		}
		String[] currentStringArray = cookie.getValue().split("&");
		List<Long> usedIndexList = new ArrayList<Long>();
		for(int i = 0; i < currentStringArray.length; i++){
			usedIndexList.add(Long.parseLong(currentStringArray[i]));
		}
		return usedIndexList;
	}

	private void writeRandomRecomendCookie(String newCookieValue) {
		Cookie cookie;
		cookie = new Cookie(COOKIE_RANDOM_ROCOMMEND, newCookieValue);
		cookie.setPath(COOKIE_PATH);
		cookie.setDomain(COOKIE_DOMAIN);
		cookie.setMaxAge(Integer.MAX_VALUE);
		CookieUtils.writeCookie(cookie);
	}
	
	@Override
	public List<Content> findContent(Fragment fragment) {
		if (fragment == null) {
			return null;
		}
		short rule = fragment.getRule();
		if (rule == Fragment.RULE_MANUAL) {
			return getManualContentList(fragment);
		} 
		return getAutoContentList(fragment);
	}
	
	/**
	 * 此段还可以继续做代码重构
	 * @param fragment
	 * @return
	 */
	private List<Content> getAutoContentList(Fragment fragment){
		if (fragment.getType() == Fragment.TYPE_PRODUCT) {
			List<ProductSale> productSaleList = null;
			if (fragment.getRule() == Fragment.RULE_AUTO_PROMOTION
					|| fragment.getRule() == Fragment.RULE_AUTO_PROMOTION_WAIT) {
				productSaleList = findPromotion(fragment);
			} else if (Rule.rules.get(fragment.getRule()) != null) {
				Rule r = Rule.rules.get(fragment.getRule());
				if (fragment.getCategory() != null) {
					r.setCode(fragment.getCategory().getCode());
				}
				r.setSize(fragment.getQuantity());
				productSaleList = findTop(r);

			}

			if (productSaleList != null && !productSaleList.isEmpty()) {
				List<Content> contentList = new ArrayList<Content>(
						productSaleList.size());
				for (ProductSale productSale : productSaleList) {
					productSale.setImageUrl(productSale.getProduct()
							.getImageUrlByType(
									(short) fragment.getImageType()));
					contentList.add(productSale);
				}
				return contentList;
			}
		}
		return null;
	}
	
	/**
	 * 获取手动的设置的内容，包括两部分：随机获取 与 非随机 获取
	 * @param fragment
	 * @return
	 */
	private List<Content> getManualContentList(Fragment fragment){		
		if(fragment.getType() != Fragment.TYPE_RANDOM){
			//非随机推荐，直接返回即可
			return fragment.getContentList();
		}
		return getRandomOrAllContentsList(fragment);				
	}
	
	/**
	 * 返回部分随机content 或者 全部content
	 * 如果url参数random == 1, 则返回部分随机content, 否则返回全部content
	 * 全部content的返回用于随机产生的库的cms维护，而部分随机只是用于取数据
	 * 
	 * 时间紧迫后期可优化
	 * 
	 * @param fragment
	 * @return
	 */
	private List<Content> getRandomOrAllContentsList(Fragment fragment){
		String randType = WebContext.currentRequest().getParameter("random");
		List<Content> allContentList = fragment.getAllOrPartContentList(true);
		if(RANDOM_GENERATE_TYPE.equals(randType) && allContentList.size() > fragment.getQuantity()){
			//random=1,则返回几条随机数据
			return getRandomContentFromAllContent(allContentList, fragment.getQuantity());
		}
		return allContentList;
	}

	private List<ProductSale> findTop(Rule rule) {
		ProductSearch productSearch = new ProductSearch();
		String code = rule.getCode();
		/*
		 * Long onShelfDate = rule.getOnShelfDate(); Long publishDate =
		 * rule.getPublishDate(); Integer quantity = rule.getQuantity();
		 */
		Boolean hasImage = rule.getHasImage();
		short orderBy = rule.getOrder().getOrder();
		Boolean complex = rule.getComplex();
		BigDecimal discount = rule.getDiscount();
		int size = rule.getSize();

		if (!StringUtils.isBlank(code)) {
			productSearch.setCode(code);
		}
		/*
		 * if(onShelfDate != null){ params.put("onShelfDate", onShelfDate); }
		 * if(publishDate != null){ params.put("publishDate", publishDate); }
		 * if(quantity != null){ params.put("availableQuantity", quantity); }
		 */
		if (hasImage != null) {
			productSearch.setHasPicture(hasImage);
		}
		if (complex != null) {
			productSearch.setComplex(complex);
		}
		if (discount != null) {
			productSearch.setDiscount(discount);
		}

		return productService.findProductSaleByPerformerce(productSearch,
				orderBy, size);
	}

	private List<ProductSale> findPromotion(Fragment fragment) {
		List<ProductSale> productSaleList = null;
		short rule = fragment.getRule();
		if (fragment.getRule() == Fragment.RULE_AUTO_PROMOTION) {
			productSaleList = productService.findPromotion(
					fragment.getCategory(), fragment.getQuantity(), true);
		} else if (rule == Fragment.RULE_AUTO_PROMOTION_WAIT) {
			productSaleList = productService.findPromotion(
					fragment.getCategory(), fragment.getQuantity(), false);
		}
		return productSaleList;
	}

	@Override
	public Element getElement(Long id) {
		return cmDao.getElement(id);
	}

	@Override
	public void saveFragment(Fragment fragment) {
		cmDao.saveFragment(fragment);
	}

	@Override
	public void saveOrUpdateLink(Link link) {
		cmDao.saveOrUpdateLink(link);
	}

	@Override
	public void saveOrUpdateNews(News news) {
		cmDao.saveOrUpdateNews(news);
	}

	@Override
	public void saveOrUpdateText(Text text) {
		cmDao.saveOrUpdateText(text);
	}

	@Override
	public News getNews(Long id) {
		return cmDao.getNews(id);
	}

	@Override
	public Link getLink(Long id) {
		return cmDao.getLink(id);
	}

	@Override
	public Text getText(Long id) {
		return cmDao.getText(id);
	}

	@Override
	public void clearFragment(Fragment fragment) {
		cmDao.clearFragment(fragment.getId());
		fragment.setElements(null);
	}

	@Override
	public Fragment getFragmentByContext(Fragment fragment) {
		return cmDao.getFragmentByContext(fragment.getPage(),
				fragment.getIndex());
	}

	@Override
	public List<Fragment> getFragmentsByContext(Fragment fragment) {
		return cmDao.getFragmentsByContext(fragment.getPage(), (short) 0);
	}

	@Override
	public List<Fragment> find(Map<String, Object> parameters,
			Pagination pagination, Short sort) {
		return this.cmDao.find(parameters, pagination, sort);
	}

	@Override
	public List<Fragment> find(Map<String, Object> parameters,
			Pagination pagination) {
		return this.find(parameters, pagination, (short) 1);
	}

	@Override
	public Map<String, Object> getFragmentConfig(Fragment fragment) {
		Set<CmsConfig> cmsConfigs = fragment.getCmsCofig();
		Map<String, Object> restul = new HashMap<String, Object>();
		if (cmsConfigs.isEmpty() || cmsConfigs == null) {
			return restul;
		} else {
			for (CmsConfig cmsConfig : cmsConfigs) {
				if (cmsConfig.isAvailable()) {
					restul.put(cmsConfig.getKey(), this.convertValue(cmsConfig));
				}
			}
		}
		return restul;
	}

	private Object convertValue(CmsConfig cmsConfig) {
		Object result = null;
		String className = "java.lang.";
		String valueType = CmsConstant.VALUETYPE.get(cmsConfig.getValueType());
		if (valueType == null) {
			LOG.warn(cmsConfig.getValueType()
					+ " Not found,The results will be set to null");
			return result;
		}
		if ("String".equals(valueType)) {
			return cmsConfig.getValue();
		}

		try {
			if ("Date".equals(valueType)) {
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = df.parse(cmsConfig.getValue());
				result = date;
			} else {
				Class<?> c = Class.forName(className + valueType);
				result = ReflectOperation.getInstance(c, String.class,
						cmsConfig.getValue());
			}
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage(), e);
			result = null;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			result = null;
		}
		return result;
	}

	@Override
	public CmsConfig getCmsConfig(Long id) {
		return this.cmDao.getCmsConfig(id);
	}

	@Override
	public List<CmsConfig> findCmsConfig() {
		return this.cmDao.findCmsConfig();
	}

}
