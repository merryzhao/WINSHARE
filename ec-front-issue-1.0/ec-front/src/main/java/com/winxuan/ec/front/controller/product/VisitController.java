/*
 * @(#)VisitController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.product;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.front.controller.shop.ServiceInfo;
import com.winxuan.ec.model.activity.ActivityShow;
import com.winxuan.ec.model.booktop.BookTopCategory;
import com.winxuan.ec.model.booktop.BookTopProductSale;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.cm.Content;
import com.winxuan.ec.model.cm.Fragment;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.product.DciData;
import com.winxuan.ec.model.product.DciLog;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductDescription;
import com.winxuan.ec.model.product.ProductExtend;
import com.winxuan.ec.model.product.ProductJiuyue;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionTag;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopCategory;
import com.winxuan.ec.model.shop.ShopRank;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.activity.ActivityShowService;
import com.winxuan.ec.service.booktop.BookTopService;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.cm.CmService;
import com.winxuan.ec.service.customer.CustomerCommentService;
import com.winxuan.ec.service.customer.CustomerQuestionService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.product.DciDataService;
import com.winxuan.ec.service.product.DciLogService;
import com.winxuan.ec.service.product.ProductRecommendationService;
import com.winxuan.ec.service.product.ProductSaleRankRateBean;
import com.winxuan.ec.service.product.ProductSaleRankService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.productMerge.ProductJiuyueService;
import com.winxuan.ec.service.promotion.PromotionService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.web.enumerator.RecommendMode;
import com.winxuan.framework.pagination.FrontConsultPaginationConvertor;
import com.winxuan.framework.pagination.Pagination;


/**
 * 代码太多了,我实在看不下去了,粘出去了一些.
 * 
 * @author liuan
 * @version 1.0,2011-9-29
 */
@Controller
@RequestMapping(value = "/product")
public class VisitController {

	private static final Log LOG = LogFactory.getLog(VisitController.class);

	private static final String PRODUCT = "product";
	private static final String PRODUCT_SALE = "productSale";
	private static final String COMMENT_COUNT = "commentCount";
	private static final String QUESTION_COUNT = "questionCount";
	private static final String RANK_COUNT = "rankCount";
	private static final String PAGINATION = "pagination";
	private static final String CUSTOMERQUESTIONS = "customerQuestions";
	private static final String CUSTOMER_COMMENTS = "customerComments";
	private static final String HTML_REGEX ="<(.[^>]*)>";
	private static final List<String> ESCAPE_SEQUENCE = Arrays.asList("&nbsp;");
	/**
	 * 人气标签
	 */
	private static final String BOOK_TOP_URL = "http://www.winxuan.com/topic/subject/201405/ads/pro_rq.png";
	
	private static final String STR_TEN = "10";
	private static final int BOOK_TOP_SIZE = 100;
	private static final int CATEGORY_TOP_LV = 2;
	private static final int CATEGORY_LOW_LV = 1;
	private static final int PAGE = 1;
	private static final int RANK_BAD_LEVEN = 2;
	private static final int RANK_PRAISE_LEVEN = 5;
	private static final String BAD_CUSTOMER_COMMENTS = "badCustomerComment";
	private static final String MIDDLER_CUSTOMER_COMMENTS = "middlerCustomerComment";
	private static final String PRAISE_CUSTOMER_COMMENTS = "praiseCustomerComment";
	/**
	 * 商品种类杂志
	 */
	private static final Long PRODUCT_SORT_MOOK = 2002L;
	
	
	private static final String NOT_FOUND_URL = "/shop/view_notFound";

	private Channel tmallChannel = new Channel(Channel.TAOBAO_WINSHARE);
	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerCommentService customerCommentService;

	@Autowired
	private CustomerQuestionService customerQuestionService;

	@Autowired
	private ProductSaleRankService productSaleRankService;
	
	@Autowired
	private BookTopService bookTopService;

	@Autowired
	private ProductRecommendationService productRecommendationService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ActivityShowService activityShowService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ServiceInfo serviceInfo;

	@Autowired
	private PromotionService promotionService;

    @Autowired
    private CmService cmService;
	
    @Autowired
    private ProductJiuyueService productJiuyueService;
    
    @Autowired
	private OrderService orderService;
    @Autowired
    private DciDataService dciDataService;
    @Autowired
    private DciLogService dciLogService;
    

	@Value("${redirect9yue}")
    private Boolean redirect9yue;

	@RequestMapping(value = "/product/stock", method = RequestMethod.GET)
	public ModelAndView updateProductStock(HttpServletRequest request) {
		String id = request.getParameter("id");
		String stock = request.getParameter("stock");
		LOG.info(id + ":" + stock);

		return new ModelAndView("/product/stock");
	}

	/**
	 * ajax获取咨询数据 百货底层页用
	 * 
	 * @param id
	 * @param pagination
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	@RequestMapping(value = "/questlist/{id}", method = RequestMethod.POST)
	public ModelAndView getQuestion(@PathVariable("id") Long id,
			@MyInject Pagination pagination, HttpServletResponse response)
			throws IOException, CloneNotSupportedException {

		// pagination = new Pagination();
		ProductSale productSale = productService.getProductSale(id);
		if (productSale == null || !productSale.isShow()) {
			response.sendError(HttpStatus.SC_NOT_FOUND);
			return null;
		}

		pagination.setConvertor(new FrontConsultPaginationConvertor());
		ModelAndView mav = this.view(null, id, pagination, response);
		List<CustomerQuestion> customerQuestions = customerQuestionService
				.findQuestionsByProductSale(productSale, pagination,
						Short.valueOf("0"));
		mav = this.addPageInfoAjax(mav, pagination);
		if (customerQuestions.size() > 0) {
			mav.addObject(CUSTOMERQUESTIONS, customerQuestions);
			mav.addObject("renderer", "question");
		}
		return mav;
	}

	/**
	 * ajax获取评论数据 百货底层页用
	 * 
	 * @param id
	 * @param pagination
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	@RequestMapping(value = "/commentlist/{id}", method = RequestMethod.POST)
	public ModelAndView getComment(@PathVariable("id") Long id,
			@MyInject Pagination pagination, HttpServletResponse response)
			throws IOException, CloneNotSupportedException {
		ProductSale productSale = productService.getProductSale(id);
		if (productSale == null || !productSale.isShow()) {
			response.sendError(HttpStatus.SC_NOT_FOUND);
			LOG.error(productSale);
			return null;
		}

		pagination.setConvertor(new FrontConsultPaginationConvertor());
		ModelAndView mav = this.view(null, id, pagination, response);
		List<CustomerComment> customerComments = customerCommentService
				.findByProductSale(productSale, pagination);
		mav = this.addPageInfoAjax(mav, pagination);
		if (customerComments.size() > 0) {
			mav.addObject(CUSTOMER_COMMENTS, customerComments);
			mav.addObject("renderer", "comment");
		}
		return mav;
	}

	private ModelAndView addPageInfoAjax(ModelAndView mav, Pagination pagination) {
		mav.addObject(PAGINATION, pagination);
		mav.addObject("pageHtmlString", pagination.toString());
		mav.setViewName("/shop/indexCustomerQuestions");
		return mav;

	}

	/**
	 * 百货,影像,图书,底层页面跳转.
	 * 
	 * --cast911
	 * 
	 * @param customer
	 * @param id
	 * @param pagination
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView view(@MyInject Customer customer,
			@PathVariable("id") Long id, @MyInject Pagination pagination,
			HttpServletResponse response) throws IOException, CloneNotSupportedException {
		ProductSale productSale = productService.getProductSale(id);
		if (productSale == null) {
			//定向到首页
//			response.sendError(HttpStatus.SC_NOT_FOUND);
			return new ModelAndView("redirect:http://www.winxuan.com");
			
		}
		if(customer != null){
			productSale.setPurchasedQuantity(2);
		}
		productSale.setPurchasedQuantityAll(0);
		if (!productSale.isShow()) {
			response.sendError(HttpStatus.SC_GONE);
			return null;
		}
        if (redirect9yue && Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(productSale.getStorageType().getId())) {
            ProductJiuyue jiuyue = productJiuyueService.fetchProductJiuyue(productSale);
            return new ModelAndView(new RedirectView("http://www.9yue.com/productdetail/" + jiuyue.getJiuyueProductid() + ".html"));
        }
		ModelAndView view = new ModelAndView();
		view.addObject(PRODUCT, productSale.getProduct());
		view.addObject(PRODUCT_SALE, productSale);
		view.addObject("customer", customer);
		this.setProductPromotion(view, productSale);
		view.addAllObjects(assemblyDataForBase(productSale));
		
		if(Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(productSale.getStorageType().getId())){
			ProductJiuyue jiuyue = productJiuyueService.fetchProductJiuyue(productSale);
			DciData dciData = dciDataService.get(jiuyue.getJiuyueBookid());
			if(dciData != null){
				view.addObject("dciData", dciData);
			}
			view.addObject("supportIphone", isSupportIphone(productSale.getProduct()));
			view = this.bookViewName(productSale, view);
			
		}else if (productSale.getProduct().getSort().getId()
				.equals(Code.PRODUCT_SORT_BOOK)) {
			// 图书
			view = this.paperbookViewName(productSale, view);
		} else if (productSale.getProduct().getSort().getId()
				.equals(Code.PRODUCT_SORT_VIDEO)) {
			// 音像
			view.addAllObjects(assemblyDataForRecommend(productSale));
			view.setViewName("/product/video");
		} else if (productSale.getProduct().getSort().getId()
				.equals(Code.PRODUCT_SORT_MERCHANDISE)) {
			// 百货
			if (Shop.WINXUAN_SHOP.equals(productSale.getShop().getId())) {
				return new ModelAndView(NOT_FOUND_URL);
			}
			view.addAllObjects(assemblyDataForMall(productSale));
			serviceInfo.setServiceInfo(productSale.getShop(), view);
			view.setViewName("/product/mall");
		}
		view.addAllObjects(assemblyCommentsAndQuestionsForBase(productSale));
		return view;
	}

	/**
	 * @param productSale
	 * @return
	 */
	private boolean isSupportIphone(Product product) {
		Set<ProductExtend> productExtends = product.getExtendList();
		if(CollectionUtils.isNotEmpty(productExtends)){
			for(ProductExtend extend : productExtends){
				//是否支持iphone阅读
				if("1".equals(extend.getValue()) 
						&& extend.getProductMeta().getId().longValue() == 114L){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 设置促销信息 
	 * 促销标签(限时抢>*折封顶>满额减>专题活动标签>免运费)
	 * 免运费信息不展示
	 * @param view
	 * @param productSale
	 * @return
	 */
	private ModelAndView setProductPromotion(ModelAndView view,
			ProductSale productSale) {
		List<Promotion> promotions = promotionService
				.findEffectivePromotion(productSale);
		String tagurl = "";
		for (int i = 0; i < promotions.size(); i++) {
			if(StringUtils.isEmpty(tagurl)){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("type", promotions.get(i).getType());
				params.put("available", true);
				List<PromotionTag> promotionTags = promotionService.findPromotionTag(params, new Pagination());
				if(CollectionUtils.isNotEmpty(promotionTags)){
					tagurl = promotionTags.get(0).getUrl();
				}
			}
			if (Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE.equals(promotions.get(i).getType().getId())) {
				promotions.remove(promotions.get(i));
			}
		}
		/*if(StringUtils.isEmpty(tagurl)){
			tagurl = subjectService.isSubjectProduct(productSale.getId().toString());
		}*/
		if(StringUtils.isBlank(tagurl)&&
			bookTopService.isBookTop(BookTopProductSale.TIME_TYPE_WEEK,
			Category.BOOK, BOOK_TOP_SIZE, productSale.getId().toString())){
			tagurl = BOOK_TOP_URL;
		}
		view.addObject("tagurl", tagurl);
		if (!CollectionUtils.isEmpty(promotions)) {
			view.addObject("promotions", promotions);
		}
		return view;
	}

	/**
	 * 纸质书页面
	 * 
	 * @param productSale
	 * @param view
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private ModelAndView paperbookViewName(ProductSale productSale, ModelAndView view) {
		Category category = productSale.getProduct().getCategory();
		Category mook = categoryService.get(PRODUCT_SORT_MOOK);
		if (mook.checkParent(category)
				&& !productSale.getShop().getId().equals(Shop.WINXUAN_SHOP)) {
			view.addAllObjects(this.assemblyDataForMall(productSale));
			serviceInfo.setServiceInfo(productSale.getShop(), view);
			view.setViewName("/product/mook/index");
			return view;
		}
		view.addAllObjects(fillTmallComments(productSale));
		
		getDescWithOutHtmlTag(productSale.getProduct().getDescriptionByMeta(ProductMeta.INTRODUCTION),view);
		view.setViewName("/product/paperbookview");

		return view;
	}

	private void getDescWithOutHtmlTag(ProductDescription  productDescription, ModelAndView view) {
		if(productDescription == null || productDescription.getContent() == null){
			view.addObject("descWithOutHtmlTag", "");
		}else{
			String str = productDescription.getContent().replaceAll(HTML_REGEX, "");
			for(String s : ESCAPE_SEQUENCE){
				str = str.replace(s, "");
			}
			view.addObject("descWithOutHtmlTag", str);
		}
		
	}
	

	/**
	 * 杂志底层页面
	 * 
	 * @param productSale
	 * @param view
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private ModelAndView bookViewName(ProductSale productSale, ModelAndView view) {
		view.addAllObjects(assemblyDataForRecommend(productSale));
		Category category = productSale.getProduct().getCategory();
		Category mook = categoryService.get(PRODUCT_SORT_MOOK);
		if (mook.checkParent(category)
				&& !productSale.getShop().getId().equals(Shop.WINXUAN_SHOP)) {
			view.addAllObjects(this.assemblyDataForMall(productSale));
			serviceInfo.setServiceInfo(productSale.getShop(), view);
			view.setViewName("/product/mook/index");
			return view;
		}
		view.addAllObjects(fillTmallComments(productSale));
		view.setViewName("/product/view");

		return view;
	}

	/**
	 * @param productSale
	 * @return
	 */
	private Map<String, ?> fillTmallComments(ProductSale productSale) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Pagination pagination = new Pagination(MagicNumber.TEN);
		modelMap.put("tmallComments", customerCommentService.findByProductSale(
				productSale, pagination, tmallChannel));
		modelMap.put("tmallCommentCount",pagination.getCount());
		return modelMap;
	}

	/**
	 * 百货独有
	 * 
	 * @param productSale
	 * @return
	 */
	private Map<String, ?> assemblyDataForMall(ProductSale productSale) {
		Shop shop = productSale.getShop();
		Set<ShopCategory> scList = productSale.getShopCategoryList();
		Map<String, Object> map = new HashMap<String, Object>();
		// 店铺评分
		shop.setScorelist(new ArrayList<Integer>());
		map.put("shop", shop);
		if (scList.size() > 0) {
			// 同类产品
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("shop", shop.getId());
			parameters.put("shopCategoryId", scList.iterator().next().getId());
			map.put("similarProductSale", shopService
					.findProductSaleByCategory(parameters, MagicNumber.FIVE));

		}
		
		ShopRank shopRank = shopService.convertToShopRank(shop);
		map.put("shop", shop);
		map.put("shopRankCount", shopRank.getRankCount());
		map.put("shopRankSum", shopRank.getSumRank());
		map.put("shopRankAvgrank", shopRank.getAverageRank());
		map.put("shopCategorys", shop.getUseShopCategory());
		// 目前所有的使用统一的温馨提示
		List<ActivityShow> activityShows = activityShowService.find();
		map.put("activityShows", activityShows);
		return map;
	}

	/**
	 * 组装图书, 音像, 百货都使用的数据
	 * 
	 * @param productSale
	 * @return
	 */
	private Map<String, ?> assemblyDataForBase(ProductSale productSale) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 提取评论集合

		Pagination commentPage = new Pagination(Integer.parseInt(STR_TEN));
		
		map.put(COMMENT_COUNT, commentPage.getCount());
		Pagination questPage = new Pagination(Integer.parseInt(STR_TEN));
		map.put(QUESTION_COUNT, questPage.getCount());
		// 评分人数
		long rankCount = productSaleRankService.findRankCount(productSale);
		map.put(RANK_COUNT, rankCount);
		
		//商品平均分
		map.put("aveRank", productSaleRankService.getProductRankAverage(productSale));
		
		//评分详情
		ProductSaleRankRateBean rankRateBean = productSaleRankService.getProductRankInfos(productSale);
		map.put("rankRate", rankRateBean);
		
		// 浏览当前商品的人还购买过的商品
		List<ProductSale> alsoBoughtList = productRecommendationService
				.findRecommendByProductSale(productSale, RecommendMode.BUY,
						Integer.parseInt("20"));
		if (alsoBoughtList.size() >= MagicNumber.THREE) {
			map.put("alsoBoughtList", alsoBoughtList);
		}
		return map;
	}

	private Map<String, ?> assemblyCommentsAndQuestionsForBase(ProductSale productSale) throws CloneNotSupportedException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<CustomerComment> customerComments = customerCommentService.findByProductSale(productSale, null);
		int limitSize = Integer.valueOf(STR_TEN);
		List<List<CustomerComment>> customerCommentList = new ArrayList<List<CustomerComment>>();
		List<List<CustomerComment>> badCommentList = new ArrayList<List<CustomerComment>>();
		List<List<CustomerComment>> middlerCommentList = new ArrayList<List<CustomerComment>>();
		List<List<CustomerComment>> praiseCommentList = new ArrayList<List<CustomerComment>>();
		if(customerComments.size() > 0){
			List<CustomerComment> list = new ArrayList<CustomerComment>();
			for(CustomerComment cusComment : customerComments){
				CustomerComment customerComment = cusComment.filterFromCustomer();
				list = customerComment.getQuoteCommentList(customerComment.getQuoteComment());
				if(customerCommentList.size()<=limitSize){
					customerCommentList.add(list);
				}
				//(差 中 好) 评论
				if(badCommentList.size()<=limitSize&&cusComment.getRank().getRank() <= RANK_BAD_LEVEN){
					badCommentList.add(list);
				}
				if(middlerCommentList.size()<=limitSize&& RANK_BAD_LEVEN < cusComment.getRank().getRank()
						&& cusComment.getRank().getRank() < RANK_PRAISE_LEVEN){
					middlerCommentList.add(list);
				}
				if(praiseCommentList.size()<=limitSize&&cusComment.getRank().getRank() == RANK_PRAISE_LEVEN){
					praiseCommentList.add(list);
				}
			}
		}
		
		map.put(CUSTOMER_COMMENTS, customerCommentList);
		map.put(BAD_CUSTOMER_COMMENTS, badCommentList);
		map.put(MIDDLER_CUSTOMER_COMMENTS, middlerCommentList);
		map.put(PRAISE_CUSTOMER_COMMENTS, praiseCommentList);
		map.put(COMMENT_COUNT,customerComments.size());
		// 提取咨询集合
		Pagination questPage = new Pagination(Integer.parseInt(STR_TEN));
		List<CustomerQuestion> customerQuestions = customerQuestionService
				.findQuestionsByProductSale(productSale, questPage,
						Short.valueOf("0"));
		if(!CollectionUtils.isEmpty(customerQuestions)){
			map.put(CUSTOMERQUESTIONS, customerQuestions);
			map.put(QUESTION_COUNT, questPage.getCount());
		}
		return map;
	}
	
	/**
	 * 搭配购买
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}/assemblelist",method=RequestMethod.GET)
	public ModelAndView hotSale(@PathVariable(value="id") Long id){
		ModelAndView view = new ModelAndView("/product/assemble");
		ProductSale productSale = productService.getProductSale(id);
		Category category = productSale.getProduct().getCategory();
		if(Code.PRODUCT_SALE_STATUS_ONSHELF.equals(productSale.getSaleStatus().getId()))
		{
		List<ProductSale> hotSaleList = productService.findProductSaleByCategoryAndSell(category,productSale,13);
        view.addObject("hotSaleList", hotSaleList);
		}
		return view;
	}
	
	/**
	 * 同类热销
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/hotsalelist", method = RequestMethod.GET)
	public ModelAndView hotSaleList(String code) {
		ModelAndView view = new ModelAndView("/product/hotSaleList");
		Category cate = new Category();
		cate.setCode(code);
		// 热销商品
		List<ProductSale> hotSaleList = productService
				.findProductSaleByCategoryAndSell(cate);
		view.addObject("hotSaleList", hotSaleList);
		return view;
	}
	
	/**
	 * 组装图书, 音像中才有的数据
	 * 
	 * @param productSale
	 * @return
	 */
	private Map<String, ?> assemblyDataForRecommend(ProductSale productSale) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 浏览当前商品的人还浏览过的商品
		List<ProductSale> alsoViewedList = productRecommendationService
				.findRecommendByProductSale(productSale, RecommendMode.VISIT,
						Integer.parseInt("5"));
		map.put("alsoViewedList", alsoViewedList);
		return map;
	}

	/**
	 * //---->最佳搭配2014-5-12
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/combbuy", method = RequestMethod.GET)
	public ModelAndView combBuy(String code) {
		ModelAndView view = new ModelAndView("/product/hotSaleList");
		Category cate = new Category();
		cate.setCode(code);
		// 热销商品
		List<ProductSale> hotSaleList = productService
				.findProductSaleByCategoryAndSell(cate);
		view.addObject("hotSaleList", hotSaleList);
		return view;
	}
	

	/**
	 * 同类7日热销(周),新书热销(月)
	 * 分类到2级分类，默认分类图书
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/weekhotsalelist", method = RequestMethod.GET)
	public ModelAndView weekSaleList(String code) {
		ModelAndView view = new ModelAndView("/product/topSaleList");
		String[] categorys = code.split("\\.");
		Long categoryId = Category.BOOK;
		Long caregoryId2 = null;
		if(categorys.length>CATEGORY_TOP_LV){
			categoryId = Long.valueOf(categorys[CATEGORY_TOP_LV]);
			caregoryId2 = Long.valueOf(categorys[CATEGORY_TOP_LV-1]);
		}else if(categorys.length==CATEGORY_TOP_LV){
			categoryId = Long.valueOf(categorys[CATEGORY_TOP_LV-1]);
		}else if(categorys.length==CATEGORY_LOW_LV){
			categoryId = Long.valueOf(categorys[CATEGORY_LOW_LV-1]);
		}
		// 7日热销商品
		List<BookTopProductSale> weekSaleBookTop = bookTopService
				.queryTopProducts(BookTopCategory.TOP_TYPE_SALE,
						categoryId, BookTopProductSale.TIME_TYPE_WEEK, PAGE);
		// 新书热销商品
		List<BookTopProductSale> newSaleBookTop = bookTopService
				.queryTopProducts(BookTopCategory.TOP_TYPE_NEW, 
						categoryId, BookTopProductSale.TOP_TYPE_ONE_MONTH, PAGE);
		if(CollectionUtils.isEmpty(weekSaleBookTop)&&caregoryId2!=null) {
			weekSaleBookTop= bookTopService
					.queryTopProducts(BookTopCategory.TOP_TYPE_SALE,
							caregoryId2, BookTopProductSale.TIME_TYPE_WEEK, PAGE);
		}
		if(CollectionUtils.isEmpty(newSaleBookTop)&&caregoryId2!=null) {
			newSaleBookTop = bookTopService
					.queryTopProducts(BookTopCategory.TOP_TYPE_NEW, 
							caregoryId2, BookTopProductSale.TOP_TYPE_ONE_MONTH, PAGE);
		}
		view.addObject("weekSaleBookTop", weekSaleBookTop);
		view.addObject("newSaleBookTop", newSaleBookTop);
		return view;
	}
	

	@RequestMapping(value = "/{id}/performance", method = RequestMethod.GET)
	public ModelAndView performan(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("/product/performance");
		ProductSale productSale = productService.getProductSale(id);
		modelAndView.addObject("performance", productSale.getPerformance());
		return modelAndView;
	}
	
	@RequestMapping(value = "/{id}/shoppingCombination", method = RequestMethod.GET)
	public ModelAndView shoppingCombination(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("/product/shoppingCombination");
		ProductSale productSale = productService.getProductSale(id);
		List<ProductSale> productSales = productService.getshoppingCombinations(id);
		modelAndView.addObject("productSale", productSale);
		modelAndView.addObject("shoppingCombinations", productSales);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
	@RequestMapping(value = "/limitbuy", method = RequestMethod.GET)
	public ModelAndView limitBuy()
	{
		List<Content> contentFive = new ArrayList<Content>();
		ModelAndView modelAndView = new ModelAndView("/fragment/product/product_55");
		Fragment fragment = cmService.getFragment(55L);
		if (fragment != null) {
			List<Content> contentList = cmService.findContent(fragment);
			if(Fragment.TYPE_PRODUCT == fragment.getType() && CollectionUtils.isNotEmpty(contentList)){
				int size=contentList.size();
				for(int i=0;i<size;i++){
					contentFive.add(contentList.get(i));
					if(i==4)
					{
						break;
					}
				}
			}	
			modelAndView.addObject("limitBuy", contentFive);
		}
		return modelAndView;
		
	}
	@RequestMapping(value = "/dciLog")
	public String saveDciLog(Long bookId, Integer src, Model model){
		
		DciData dciData = dciDataService.get(bookId);
		DciLog dciLog = new DciLog();
		dciLog.setBookId(bookId);
		dciLog.setTime(new Date());
		dciLog.setSrc(src);
		Date randomDate = randomDate("2014-12-01", "2014-12-26");
		dciLog.setFakeTime(randomDate);
		dciLogService.save(dciLog);
		if(DciLog.SRC_ONCLICK == src){
			return "redirect:" + dciData.getUrl(); 
		}else{
			model.addAttribute("result", "success");
			return "dci/result";
		}
		
	}

	 private static Date randomDate(String beginDate, String endDate) {  
	        try {  
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	            Date start = format.parse(beginDate);// 构造开始日期  
	            Date end = format.parse(endDate);// 构造结束日期  
	            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。  
	            if (start.getTime() >= end.getTime()) {  
	                return null;  
	            }  
	            long date = random(start.getTime(), end.getTime());  
	  
	            return new Date(date);  
	        } catch (Exception e) {  
	        	LOG.error(e.getMessage(), e) ;
	        }  
	        return null;  
	    }  
	  
	    private static long random(long begin, long end) {  
	        long rtn = begin + (long) (Math.random() * (end - begin));  
	        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值  
	        if (rtn == begin || rtn == end) {  
	            return random(begin, end);  
	        }  
	        return rtn;  
	    }  

}
