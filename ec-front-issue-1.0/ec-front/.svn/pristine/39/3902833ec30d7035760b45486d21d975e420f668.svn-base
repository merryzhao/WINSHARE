/*
 * @(#)ShoppingcartController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.shop;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopColumn;
import com.winxuan.ec.model.shop.ShopColumnItem;
import com.winxuan.ec.model.shop.ShopRank;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerQuestionService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.service.society.KeenessService;
import com.winxuan.ec.service.verifycode.VerifyCodeService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.FrontConsultPaginationConvertor;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.web.WebContext;

/**
 * description
 * 
 * @author chenlong edit xuan jiu dong
 * @version 1.0,2011-11-9
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopController {

	private static final String NOT_FOUND_URL = "/shop/view_notFound";

	private static final Log LOG = LogFactory.getLog(ShopController.class);

	private static final Long[] SALE_STATUSES = new Long[] {
			Code.PRODUCT_SALE_STATUS_ONSHELF, Code.PRODUCT_SALE_STATUS_OFFSHELF };

	@Autowired
	private ShopService shopService;

	@Autowired
	private CustomerQuestionService customerQuestionService;

	@Autowired
	private VerifyCodeService verifyCodeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private KeenessService keenessService;

	private List<ProductSale> productSaleList(Shop shop, Pagination pagination) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("shops", shop);
		parameters.put("hasShopCategory", 1);
		parameters.put("saleStatuses", SALE_STATUSES);
		return this.productService
				.findProductSale(parameters, new Pagination());
	}

	@RequestMapping(value = "/commentlist/{id}", method = RequestMethod.POST)
	public ModelAndView getComment(@PathVariable("id") Long id,
			@MyInject Pagination pagination) {
		Shop shop = shopService.get(id);
		if (!shopIsExist(shop)) {
			return new ModelAndView(NOT_FOUND_URL);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("shopId", shop.getId());
		List<CustomerComment> customerCommentList = shopService
				.findAllShopComment(parameters, pagination);
		pagination.setConvertor(new FrontConsultPaginationConvertor());
		ModelAndView mav = this.setViewInfo(id, pagination);
		if (customerCommentList.size() > 0) {
			mav.addObject("customerComments", customerCommentList);
			mav.addObject("renderer", "comment");
		}
		mav.setViewName("/shop/indexCustomerQuestions");
		return mav;

	}

	@RequestMapping(value = "/questlist/{id}", method = RequestMethod.POST)
	public ModelAndView getQuestion(@PathVariable("id") Long id,
			@MyInject Pagination pagination) {

		// pagination = new Pagination();
		Shop shop = shopService.get(id);
		if (!shopIsExist(shop)) {
			return new ModelAndView(NOT_FOUND_URL);
		}

		List<CustomerQuestion> customerQuestionList = customerQuestionService
				.findQuestionsByShop(shop, pagination, (short) 0);
		pagination.setConvertor(new FrontConsultPaginationConvertor());
		ModelAndView mav = this.setViewInfo(id, pagination);
		if (customerQuestionList.size() > 0) {
			mav.addObject("customerQuestions", customerQuestionList);
			mav.addObject("renderer", "question");
		}
		mav.setViewName("/shop/indexCustomerQuestions");
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") Long id,
			@MyInject Pagination pagination) {
		if (id == MagicNumber.ONE) {
			// shop 1,滚.
			return new ModelAndView(NOT_FOUND_URL);
		}
		if (!shopIsExist(shopService.get(id))) {
			return new ModelAndView(NOT_FOUND_URL);
		}
		ModelAndView mav = this.setViewInfo(id, pagination);
		mav.setViewName("/shop/index");
		return mav;
	}

	private ModelAndView setViewInfo(Long id, Pagination pagination) {
		ModelAndView mav = new ModelAndView("/shop/index");

		Shop shop = shopService.get(id);
		LOG.info("shopId -----------------------" + id);
		if (id == MagicNumber.ONE) {

			return null;
		}
		Long questionCount = customerQuestionService
				.findQuestionCountByShop(shop);
		Long commentCount = shopService.getCommentCount(shop);
		// 改为原生
		List<Integer> result = shopService.getShopRank(shop);
		ShopRank shopRank = shopService.convertToShopRank(shop);
		BigInteger shopRankCount = (BigInteger) shopRank.getRankCount();
		shop.setScorelist(result);
		mav.addObject("shop", shop);
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setServiceInfo(shop, mav);
		mav.addObject("shopRankCount", shopRank.getRankCount());
		mav.addObject("shopRankSum", shopRank.getSumRank());
		mav.addObject("shopRankAvgrank", shopRank.getAverageRank());
		mav.addObject("pagination", pagination);
		mav.addObject("productList", productSaleList(shop, pagination));
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("shop", shop.getId());
		parameters.put("saleStatuses", SALE_STATUSES);
		mav.addObject("productListSize",
				shopService.shopProductCount(parameters));
		mav.addObject("status", true);
		mav.addObject("shopColumns", shop.getShopColumn());
		mav.addObject("pageHtmlString", pagination.toString());
		mav.addObject("questionCount", questionCount);
		mav.addObject("commentCount", commentCount);
		mav.addObject("shopCategorys", shop.getUseShopCategory());
		mav.addAllObjects(assemblyDataForRecommend(shop));
		mav.addAllObjects(assemblyDataForRankCount(shop, shopRankCount));
		return mav;
	}

	/**
	 * 组装五个星星 需要极度重构
	 * 
	 * @param shop
	 * @return
	 */
	private Map<String, ?> assemblyDataForRankCount(Shop shop,
			BigInteger shopRankCount) {
		Map<String, Object> mav = new HashMap<String, Object>();
		for (int i = 1; i <= MagicNumber.FIVE; i++) {
			ShopRank shopRank = shopService.convertToShopRank(shop,
					i);

			Double sumrank = null;
			BigInteger shareStar = null;
			Double avgrank = null;
			if (shopRank != null) {
				if (shopRank.getSumRank() != null) {
					sumrank = new BigDecimal(shopRank.getRankCount() + "")
							.doubleValue();
					shareStar = new BigInteger(shopRank.getRankCount() + "");
					avgrank = sumrank / shopRankCount.doubleValue();
				} else {
					sumrank = 0.0;
					avgrank = 0.0;
					shareStar = BigInteger.ZERO;
				}

			}
			mav.put("rankCountStar" + i, avgrank);
			mav.put("shareStar" + i, shareStar);
		}
		return mav;
	}

	/**
	 * 组装各种陈列数据
	 * 
	 * @param shop
	 * @return
	 */
	private Map<String, ?> assemblyDataForRecommend(Shop shop) {
		Map<String, Object> mav = new HashMap<String, Object>();
		Set<ShopColumn> shopColumnList = shop.getShopColumn();
		if (shopColumnList != null) {
			for (ShopColumn shopColumn : shopColumnList) {
				if (shopColumn.getType().getId()
						.equals(Code.SHOP_COLUMN_TYPE_PROMOTION)) {
					List<ProductSale> shopPromotions = shop
							.getShopColumnByNum(shopColumn);
					if (shopPromotions.size() > 0 && shopPromotions != null) {
						mav.put("shopPromotion", shopPromotions);
					}
				} else if (shopColumn.getType().getId()
						.equals(Code.SHOP_COLUMN_TYPE_IMG)) {
					Set<ShopColumnItem> shopTopAdvert = shopColumn
							.getShopColumnItems();
					if (shopTopAdvert.size() > 0 && shopTopAdvert != null) {
						mav.put("shoptopadvert", shopTopAdvert);
					}
				} else if (shopColumn.getType().getId()
						.equals(Code.SHOP_COLUMN_TYPE_HOTSALE)) {
					List<ProductSale> shopHots = shop
							.getShopColumnByNum(shopColumn);
					if (shopHots.size() > 0 && shopHots != null) {
						mav.put("shopHots", shopHots);
					}
				}
			}
		}
		return mav;
	}

	@RequestMapping(value = "/{id}/question", method = RequestMethod.POST)
	public ModelAndView question(@PathVariable("id") Long id,
			@RequestParam String verifyCode,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "content", required = true) String content,
			@MyInject Customer customer,
			@CookieValue(value = "verify_number") String verifyNumber) {
		Shop shop = shopService.get(id);
		if (!shopIsExist(shop)) {
			return new ModelAndView(NOT_FOUND_URL);
		}
		if (!verifyCodeService.verify(verifyCode, verifyNumber)) {
			ModelAndView modelAndView = new ModelAndView(
					"/product/create_failure");
			modelAndView.addObject("status", false);
			modelAndView.addObject("message", "验证码错误!");
			return modelAndView;
		}
		CustomerQuestion customerQuestion = new CustomerQuestion();

		if (customer != null) {
			customerQuestion.setCustomer(customer);
		}
		customerQuestion.setTitle(StringEscapeUtils.escapeHtml(keenessService
				.replaceRich(title)));
		customerQuestion.setContent(StringEscapeUtils.escapeHtml(keenessService
				.replaceRich(content)));
		customerQuestion.setAskTime(new Date());
		customerQuestion.setShop(shop);
		customerQuestionService.save(customerQuestion);

		ModelAndView modelAndView = this.setViewInfo(id, new Pagination());
		modelAndView.setViewName("/product/customerQuestions");
		return modelAndView;
	}

	private boolean shopIsExist(Shop shop) {
		boolean result = false;
		if (null != shop) {
			result = true;
		}
		if (shop.getId() == MagicNumber.ONE) {
			LOG.warn("-.-! shop=1 events happened set return=false,ipaddress:"+WebContext.currentRequest().getRemoteAddr());			
			result = false;
		}
		return result;
	}

	@RequestMapping(value = "/{id}/questions", method = RequestMethod.GET)
	public ModelAndView questions(@PathVariable("id") Long id,
			@MyInject Customer customer) {
		Pagination pagination = new Pagination();
		Shop shop = shopService.get(id);
		if (!shopIsExist(shop)) {
			return new ModelAndView(NOT_FOUND_URL);
		}
		ModelAndView modelAndView = this.setViewInfo(id, pagination);
		modelAndView.setViewName("/product/shop_questions");
		return modelAndView;
	}

	/**
	 * new method name:getProductSale param:Shop param:shopCateGory
	 * param:pagination
	 * 
	 * value中的为cateGoryId param中的id为shopId
	 */
	@RequestMapping(value = "{id}/category/{catagoryId}", method = RequestMethod.GET)
	public ModelAndView getProductSale(@PathVariable("id") Long id,
			@PathVariable("catagoryId") Long catagoryId,
			@MyInject Pagination pagination) {
		pagination.setPageSize(MagicNumber.TWENTY);
		Shop shop = shopService.get(id);
		if (!shopIsExist(shop)) {
			return new ModelAndView(NOT_FOUND_URL);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("shop", shop.getId());
		parameters.put("saleStatuses", SALE_STATUSES);
		if (catagoryId > 0) {
			parameters.put("shopCategoryId", catagoryId);
		}
		List<ProductSale> list = shopService.findProductSaleByCategory(
				parameters, pagination);
		ModelAndView modelAndView = new ModelAndView("/shop/all_product");
		modelAndView.addObject("list", list);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
}
