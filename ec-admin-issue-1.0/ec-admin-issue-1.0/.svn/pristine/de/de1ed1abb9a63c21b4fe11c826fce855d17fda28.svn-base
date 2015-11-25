/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.promotion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.PromotionException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionOrderRule;
import com.winxuan.ec.model.promotion.PromotionRegisterRule;
import com.winxuan.ec.model.promotion.PromotionTag;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.promotion.PromotionService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.DateUtils;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.StringUtils;

/**
 * 商品
 * 
 * @author HideHai
 * @version 1.0,2011-7-13
 */
@Controller
@RequestMapping("/promotion")
public class PromotionController {
	private static final String AJAXVERIFYRESULTURL = "/promotion/verify_result";
	private static final String PASSVERIFYCOUNT = "passVerifyCount";
	private static final String TOTALVERIFYCOUNT = "totalVerifyCount";
	private static final String TYPESTR = "type";
	private static final Long PROMOTIONSTATUS = 29000L;
	private static final String IMGPATH = "/home/www/upload/promotion/";
	private static final String IMGURLPATH = "http://static.winxuancdn.com/upload/promotion/";
	private static final int FILEREAD = 1024;
	private static final int IMGSUFFIXLENGTH = 4;
	private static final int IMGSUFFIXNAMELENGTHS = 3;
	private static final String ERRORMESSAGE = "errorMessage";
	private static final String RESULT_MESSAGE = "message";
	private static final String PROMOTION_RESULT = "/promotion/invoke_parent";
	private static final String UNVERIFYCOUNTS = "needVerifyPromotionCount";
	private static final String IMGPREFIX = "img";
	private static final String FLAG = "flag";
	private static final String RANK = "rank";
	@Autowired
	ProductService productService;
	@Autowired
	CodeService codeService;
	@Autowired
	ChannelService channelService;
	@Autowired
	ShopService shopService;
	@Autowired
	PromotionService promotionService;
	@Autowired
	PresentService presentService;
	@Autowired
	CategoryService categoryService;

	/**
	 * 为商品信息查询画面准备数据
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/prepare", method = RequestMethod.GET)
	public ModelAndView prepareForPromotion() {
		ModelAndView mav = new ModelAndView("/promotion/_new");
		Code c = codeService.get(Code.PROMOTION_TYPE);
		List<Code> list = c.getAvailableChildren();
		List<Shop> shops = shopService.findAll();
		mav.addObject("sellerList", shops);
		mav.addObject("types", list);
		return mav;
	}

	/**
	 * 进入促销活动编辑的页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView();
		Promotion promotion = promotionService.get(id);
		Long type = promotion.getType().getId();
		List<Shop> allShops = shopService.findAll();
		mav.addObject("sellerList", allShops);
		mav.addObject("promotion", promotion);
		// 本次活动中的卖家
		String[] shopIds = promotion.getShopIds();
		if(shopIds != null && shopIds.length > 0){
			List<Shop> shops = new ArrayList<Shop>();
			for(int i=0;i<shopIds.length;i++){
				shops.add(shopService.get(Long.valueOf(shopIds[i])));
			}
			mav.addObject("sellers", shops);
		}
		if (Code.PROMOTION_TYPE_PRODUCT_AMOUNT.equals(type)) {
			mav.setViewName("/promotion/single_productedit");
		} else if (Code.PROMOTION_TYPE_CATEGORY_AMOUNT.equals(type)) {
			mav.setViewName("/promotion/category_priceedit");
		} else if (Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT.equals(type)) {
			mav.setViewName("/promotion/edit_product");
		} else if (Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT.equals(type)) {
			mav.setViewName("/promotion/promotion_price_update");
		} else if (Code.PROMOTION_TYPE_ORDER_SEND_PRESENT.equals(type)) {
			mav.setViewName("/promotion/present_send_edit");
		} else if (Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE.equals(type)) {
			mav.setViewName("/promotion/edit_deliveryfee");
			PromotionOrderRule deliveryfeeOrderRule = new PromotionOrderRule();
			ArrayList<Area> oldAreas = new ArrayList<Area>();
			for (PromotionOrderRule promotionOrderRule : promotion.getOrderRules()) {
				oldAreas.add(promotionOrderRule.getArea());
				deliveryfeeOrderRule = promotionOrderRule;
			}
			mav.addObject("oldAreas", oldAreas);
			mav.addObject("deliveryfeeOrderRule", deliveryfeeOrderRule);
		}else if (Code.PROMOTION_TYPE_PRODUCT_SAVE_DELIVERYFEE.equals(type)){
			mav.setViewName("/promotion/show_product_deliveryfee");
		}else if (Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT.equals(type)){
			mav.setViewName("/promotion/specifyproduct_preferential_update");
		}else if(Code.PROMOTION_TYPE_REGISTER_SEND_PRESENT.equals(type)){
			mav.setViewName("/promotion/register_present_send_edit");
		}
		return mav;
	}

	/**
	 * 查找商品
	 * 
	 * @param numbers
	 * @param numberType
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/findProductOrGift", method = RequestMethod.POST)
	public ModelAndView findProductOrGift(@Parameter("numbers") String numbers,
			@Parameter("numberType") int numberType,
			@Parameter("type") int type, @Parameter("isAdd") int isAdd) {
		ModelAndView mav = new ModelAndView();
		if (isAdd == 1) {
			mav.setViewName("/promotion/product_table");
			if (type == 1) {
				mav.setViewName("/promotion/gift_table");
			}
		} else {
			mav.setViewName("/promotion/product_table_edit");
			if (type == 1) {
				mav.setViewName("/promotion/gift_table_edit");
			}
		}
		String[] ids = numbers.trim().split(",");
		LinkedHashSet<String> idSet = new LinkedHashSet<String>();
		for (int i = 0; i < ids.length; i++) {
			if (!"".equals(ids[i])) {
				idSet.add(ids[i].trim());
			}
		}
		switch (numberType) {
		// 商品编号
		case 0:
			ArrayList<ProductSale> productSales = new ArrayList<ProductSale>();
			for (String id : idSet) {
				ProductSale productSale = productService.getProductSale(Long
						.valueOf(id));
				if (productSale != null) {
					productSales.add(productSale);
				}
			}
			mav.addObject("productSales", productSales);
			break;
		// ISBN
		case 1:
			ArrayList<ProductSale> productSales2 = new ArrayList<ProductSale>();
			for (String id : idSet) {
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("productBarcode", id);
				Pagination pagination = new Pagination();
				pagination.setPageSize(MagicNumber.PAGE_SIZE_200);
				List<ProductSale> proList = productService
						.findProductSale(parameters,pagination);
				if (!proList.isEmpty()) {
					for (ProductSale productSale : proList) {
						productSales2.add(productSale);
					}
				}
			}
			mav.addObject("productSales", productSales2);
			break;
		// 自编码
		case 2:
			ArrayList<ProductSale> productSales3 = new ArrayList<ProductSale>();
			for (String id : idSet) {
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("outerId", id);
				Pagination pagination = new Pagination();
				pagination.setPageSize(MagicNumber.PAGE_SIZE_200);
				List<ProductSale> proList = productService
						.findProductSale(parameters,pagination);
				if (!proList.isEmpty()) {
					for (ProductSale productSale : proList) {
						productSales3.add(productSale);
					}
				}
			}
			mav.addObject("productSales", productSales3);
			break;
		default:
			break;
		}
		return mav;
	}

	/**
	 * 跳转到促销活动查询页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView queryForPromotion() {
		ModelAndView mav = getPrepareMav("/promotion/promotion_query");
		mav.addObject(UNVERIFYCOUNTS, promotionService.getNeedVerifyCount());
		return mav;
	}

	private ModelAndView getPrepareMav(String mvnName) {
		ModelAndView mav = new ModelAndView(mvnName);
		// 促销活动类型
		Code promotionType = codeService.get(Code.PROMOTION_TYPE);
		// 促销活动状态
		Code promotionStatus = codeService.get(PROMOTIONSTATUS);
		// 返回参数
		mav.addObject("promotionType", promotionType);
		mav.addObject("promotionStatus", promotionStatus);
		return mav;
	}

	/**
	 * 促销活动数据查询
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView promotionList(
			@Valid PromotionQueryForm promotionQueryForm, BindingResult result,
			@MyInject Pagination pagination) {
		ModelAndView mav = getPrepareMav("/promotion/promotion_query");
		// 参数准备
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pId", promotionQueryForm.getPromotionId());
		parameters.put("pTitle", promotionQueryForm.getPromotionTitle());
		parameters.put("pTypeId", promotionQueryForm.getPromotionType());
		parameters.put("pStatusId", promotionQueryForm.getPromotionStatus());
		try {
			parameters.put("pStartDateBegin",
				 getDate(promotionQueryForm.getPromotionStartdate(),true));
			parameters.put("pEndDateOver",
					getDate(promotionQueryForm.getPromotionEnddate(),false));
		} catch (ParseException e) {
			return mav;
		}
		// 查询数据
		List<Promotion> promotions = promotionService.find(parameters,pagination);
		mav.addObject("promotions", promotions);
		mav.addObject("pagination", pagination);
		mav.addObject(UNVERIFYCOUNTS, promotionService.getNeedVerifyCount());
		return mav;
	}

	public Date getDate(String date,boolean flag) throws ParseException {
		if (StringUtils.isNullOrEmpty(date)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newdate;
		if(flag){
			newdate = DateUtils.getEarlyInTheDay(sdf.parse(date));
		}else{
			newdate = DateUtils.getLateInTheDay(sdf.parse(date));
		}
		
		return newdate;

	}

	/**
	 * 停用单个促销活动
	 * 
	 * @param promotionId
	 * @param type
	 * @param operator
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public ModelAndView stop(@RequestParam("promotionId") Long promotionId,
			@MyInject Employee operator) throws PromotionException {
		ModelAndView mav = new ModelAndView(AJAXVERIFYRESULTURL);
		// 获取促销活动
		Promotion promotion = promotionService.get(promotionId);
		try {
			promotionService.stop(promotion, operator);
			mav.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
			// 异常返回失败提示
		} catch (PromotionException e) {
			mav.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return mav;
	}

	/**
	 * 跳转到审核促销活动页面
	 * 
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/unVerifyPromotion", method = RequestMethod.GET)
	public ModelAndView findNeedVerify(@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/promotion/verify");
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pStatusId", new Long[] { Code.PROMOTION_STATUS_CREATE });
		List<Promotion> unVerifyPromotions = promotionService.find(parameters,pagination);
		mav.addObject("unVerifyPromotions", unVerifyPromotions);
		mav.addObject(pagination);
		mav.addObject(UNVERIFYCOUNTS, promotionService.getNeedVerifyCount());
		return mav;
	}

	/**
	 * 批量审核促销活动
	 * 
	 * @param promotionIds
	 * @param type
	 * @param operator
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/verifyBatch", method = RequestMethod.POST)
	public ModelAndView verify(@RequestParam("item") String promotionIds,
			@RequestParam(TYPESTR) int type, @MyInject Employee operator)
			throws PromotionException {
		ModelAndView mav = new ModelAndView(AJAXVERIFYRESULTURL);
		String[] ids = promotionIds.trim().split(",");
		int count = 0;
		// 循环审核
		if (null != promotionIds) {
			boolean isPass;
			Promotion promotion ;
			for (int i = 0; i < ids.length; i++) {
				promotion = promotionService
						.get(Long.valueOf(ids[i]));
				if (type == 1) {
					isPass = true;
				}else if (type == 0) {
					isPass = false;
				}else{
					mav.addObject(ERRORMESSAGE,"审核类型不能为空！");
					return mav;
				}
				try {
					promotionService.audit(promotion, isPass, operator);
					mav.addObject(ControllerConstant.RESULT_KEY,
							ControllerConstant.RESULT_SUCCESS);
				} catch (PromotionException e) {
					mav.addObject(ControllerConstant.RESULT_KEY,
							ControllerConstant.RESULT_INTERNAL_ERROR);
					mav.addObject(ERRORMESSAGE, e.getMessage());
					count++;
				}
			}

		}
		mav.addObject(TOTALVERIFYCOUNT, ids.length);
		mav.addObject(PASSVERIFYCOUNT, ids.length - count);
		return mav;
	}

	/**
	 * 审核单个促销活动
	 * 
	 * @param promotionId
	 * @param type
	 * @param operator
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public ModelAndView verify(@RequestParam("promotionId") Long promotionId,
			@RequestParam(TYPESTR) int type, @MyInject Employee operator)
			throws PromotionException {
		ModelAndView mav = new ModelAndView(AJAXVERIFYRESULTURL);
		if (null != promotionId) {
			boolean isPass;
			Promotion promotion = promotionService.get(promotionId);
			if (type == 1) {
				isPass = true;
				mav.addObject(ControllerConstant.RESULT_KEY,
						MagicNumber.ONE);
			}else if (type == 0) {
				mav.addObject(ControllerConstant.RESULT_KEY,
						MagicNumber.TWO);
				isPass = false;
			}else{
				mav.addObject(ERRORMESSAGE,"审核类型不能为空！");
				return mav;
			}
			promotion.setAssessor(operator);
			promotion.setAssessTime(new Date());			
			try {
				promotionService.audit(promotion, isPass, operator);
				mav.addObject("assessTime", new SimpleDateFormat(DateUtils.SHORT_DATE_FORMAT_STR2).format(promotion.getAssessTime()));
				if(!StringUtils.isNullOrEmpty(promotion.getAssessor().getRealName())){
					mav.addObject("assessor", promotion.getAssessor().getRealName());
				}else{
					mav.addObject("assessor", promotion.getAssessor().getName());
				}
			} catch (PromotionException e) {
				mav.addObject(ControllerConstant.RESULT_KEY,
						ControllerConstant.RESULT_PARAMETER_ERROR);
				mav.addObject(ERRORMESSAGE, e.getMessage());
			}
		}
		return mav;
	}

	/**
	 * 提交买商品送商品活动
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public ModelAndView saveProduct(
			PromotionProductForm promotionProductForm,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request, @MyInject Employee operator)
			throws PromotionException {
		ModelAndView mav = new ModelAndView();
		Promotion promotion = promotionProductForm.getPromotion(null,productService, operator);
		uploadImg(promotion, request, img);
		try {
			mav.setViewName(PROMOTION_RESULT);
			promotionService.save(promotion, operator);
			return mav;
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
			return mav;
		}
	}

	/**
	 * 提交修改买商品送商品活动
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/productUpdate", method = RequestMethod.POST)
	public ModelAndView updateProduct(
			HttpServletRequest request,
			PromotionProductForm promotionProductForm,
			@MyInject Employee operator,
			@RequestParam(required = false, value = "localfile") MultipartFile img)
			throws PromotionException {
		ModelAndView mav = new ModelAndView();
		Promotion oldPromotion = promotionService.get(promotionProductForm
				.getId());
		Promotion promotion = promotionProductForm.getPromotion(oldPromotion,
				productService,operator);
		uploadImg(oldPromotion, request, img);
		try {
			mav.setViewName(PROMOTION_RESULT);
			promotionService.update(promotion, operator);
			return mav;
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
			return mav;
		}
	}

	/**
	 * 提交单品优惠活动
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/saveSingleProduct", method = RequestMethod.POST)
	public ModelAndView saveSingleProduct(
			SingleProductForm singleProductForm,
			@MyInject Employee operator,
			@RequestParam("localfile") MultipartFile img,
			HttpServletRequest request) throws PromotionException {
		ModelAndView mav = new ModelAndView(PROMOTION_RESULT);
		Promotion p = singleProductForm.getPromotion(null,productService, operator);
		uploadImg(p, request, img);
		try {
			promotionService.save(p, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}

	/**
	 * 修改单品优惠活动
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/updateSingleProduct", method = RequestMethod.POST)
	public ModelAndView updateSingleProduct(
			SingleProductForm singleProductForm,
			@RequestParam("id") Long id,
			@MyInject Employee operator,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request) throws PromotionException {
		ModelAndView mav = new ModelAndView(PROMOTION_RESULT);
		Promotion oldPromotion = promotionService.get(id);
		Promotion p = singleProductForm.getPromotion(oldPromotion,productService, operator);
		uploadImg(oldPromotion, request, img);
		try {
			promotionService.update(p, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}

	/**
	 * 提交类别价格优惠活动
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/saveCategoryPrice", method = RequestMethod.POST)
	public ModelAndView saveCategoryPrice(
			CategoryPriceForm categoryPriceForm,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			@MyInject Employee operator, HttpServletRequest request)
			throws PromotionException {
		ModelAndView mav = new ModelAndView(PROMOTION_RESULT);
		Promotion p = categoryPriceForm.getPromotion(null, operator,categoryService);
		uploadImg(p, request, img);
		try {
			promotionService.save(p, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}

	/**
	 * 修改类别价格优惠活动
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/updateCategoryPrice", method = RequestMethod.POST)
	public ModelAndView updateCategoryPrice(
			CategoryPriceForm categoryPriceForm,
			@RequestParam("id") Long id,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			@MyInject Employee operator, HttpServletRequest request)
			throws PromotionException {
		ModelAndView mav = new ModelAndView(PROMOTION_RESULT);
		Promotion oldPromotion = promotionService.get(id);
		Promotion p = categoryPriceForm.getPromotion(oldPromotion, operator,categoryService);
		uploadImg(oldPromotion, request, img);
		try {
			promotionService.update(p, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}

	/**
	 * 提交运费减免活动
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/saveDeliveryFee", method = RequestMethod.POST)
	public ModelAndView saveDeliveryFee(
			PromotionDeliveryFeeForm promotionDeliveryFeeForm,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request, @MyInject Employee operator) {
		ModelAndView mav = new ModelAndView();
		Promotion promotion = promotionDeliveryFeeForm.getPromotion(null,
				operator);
		uploadImg(promotion, request, img);
		try {
			mav.setViewName(PROMOTION_RESULT);
			promotionService.save(promotion, operator);
			return mav;
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
			return mav;
		}

	}

	/**
	 * 提交修改运费减免活动
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/deliveryFeeUpdate", method = RequestMethod.POST)
	public ModelAndView updateDeliveryFee(
			HttpServletRequest request,
			PromotionDeliveryFeeForm promotionDeliveryFeeForm,
			@MyInject Employee operator,
			@RequestParam(required = false, value = "localfile") MultipartFile img) {
		ModelAndView mav = new ModelAndView();
		Promotion oldPromotion = promotionService.get(promotionDeliveryFeeForm.getId());
		Promotion promotion = promotionDeliveryFeeForm.getPromotion(oldPromotion, operator);
		uploadImg(oldPromotion, request, img);
		try {
			mav.setViewName(PROMOTION_RESULT);
			promotionService.update(promotion, operator);
			return mav;
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
			return mav;
		}

	}

	/**
	 * 提交订单满省活动
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/orderPrice", method = RequestMethod.POST)
	public ModelAndView saveOrderPrice(
			PromotionOrderPriceForm priceForm,
			@MyInject Employee operator,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request) throws PromotionException {
		ModelAndView mav = new ModelAndView();
		Promotion promotion = priceForm.getPromotion(null, img,operator);
		// 保存图片
		uploadImg(promotion, request, img);
		try {
			mav.setViewName(PROMOTION_RESULT);
			promotionService.save(promotion, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}
	
	/**
	 * 提交订单满省活动修改
	 * 
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/orderPriceUpdate", method = RequestMethod.POST)
	public ModelAndView updateOrderPrice(
			PromotionOrderPriceForm priceForm,
			@MyInject Employee operator,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request) throws PromotionException {
		ModelAndView mav = new ModelAndView(PROMOTION_RESULT);
		Promotion promotion = priceForm.getPromotion(promotionService.get(priceForm.getPromotionId()), img, operator);
		// 保存图片
		uploadImg(promotion, request, img);
		try {
			promotionService.update(promotion, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}

	/**
	 * 查询订单送礼卷活动规则
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/findruleslist", method = RequestMethod.POST)
	public ModelAndView findOrderRulesList(@Parameter("id") Long id) {
		ModelAndView mav = new ModelAndView("orderpresent/orderrulesbeans");
		List<PromotionOrderRule> promotionOrderRules = toList(promotionService
				.get(id).getOrderRules());
		List<OrderRulesBean> orderRulesBeans = new ArrayList<OrderRulesBean>();
		getOrderRulesBeans(promotionOrderRules, orderRulesBeans);
		mav.addObject("orderRulesBeans", orderRulesBeans);
		return mav;
	}
	
	/**
	 * 查询注册送礼卷活动规则
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/findregruleslist", method = RequestMethod.POST)
	public ModelAndView findRegRulesList(@Parameter("id") Long id) {
		ModelAndView mav = new ModelAndView("/promotion/regrulesbean");
		PromotionRegisterRule registerRule = getRegisterRule(promotionService
				.get(id).getRegisterRules());
		RegisterRulesBean registerRulesBean = getRegisterRulesBean(registerRule);
		if(registerRulesBean == null){
			mav.addObject("message", ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		mav.addObject("registerRulesBean", registerRulesBean);
		return mav;
	}

	/**
	 * 保存或修改订单送礼卷活动
	 * 
	 * @return
	 * @throws ParseException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/presentfororder", method = RequestMethod.POST)
	public ModelAndView saveOrUpdatePresentForOrder(
			@Valid PresentSendForm presentSendForm,
			@MyInject Employee operator,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request) throws ParseException,
			IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(PROMOTION_RESULT);
		Promotion promotion = null;
		String type = request.getParameter("optionType");
		// 判断是更新还是新增操作
		if (type != null) {
			if ("update".equals(type)) {// 如果是更新
				Promotion oldPromotion = promotionService.get(presentSendForm.getPromotionId());
 			    //上传图片
				uploadImg(oldPromotion, request, img);
				promotion = presentSendForm.getPromotion(oldPromotion, operator);
			}
		} else {// 新增
			promotion = presentSendForm.getPromotion(null, operator);
			// 广告图片
			uploadImg(promotion, request, img);
		}
		// 活动规则数据
		promotion.setOrderRules(getJsonData(promotion,presentSendForm.getPromotionData1()));
		try {
			if (type != null) {
				if ("update".equals(type)) {// 更新数据
					promotionService.update(promotion, operator);
				}
			} else {// 保存
				promotionService.save(promotion, operator);
			}
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}
	
	
	/**
	 * 提交指定(部分)商品满省活动	add by ztx
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/saveSpecifyProductPreferential", method = RequestMethod.POST)
	public ModelAndView saveSpecifyProductPreferential(
			SpecifyProductPreferentialForm productForm,
			@MyInject Employee operator,
			@RequestParam("localfile") MultipartFile img,
			HttpServletRequest request) throws PromotionException {
		
		ModelAndView mav = new ModelAndView(PROMOTION_RESULT);
		Promotion p = productForm.getPromotion(null,productService, operator);
		uploadImg(p, request, img);
		try {
			promotionService.save(p, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}

	/**
	 * 修改指定(部分)商品满省活动	add by ztx
	 * @return
	 * @throws PromotionException
	 */
	@RequestMapping(value = "/updateSpecifyProductPreferential", method = RequestMethod.POST)
	public ModelAndView updateSpecifyProductPreferential(
			SpecifyProductPreferentialForm productForm,
			@RequestParam("id") Long id,
			@MyInject Employee operator,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request) throws PromotionException {
		ModelAndView mav = new ModelAndView(PROMOTION_RESULT);
		Promotion oldPromotion = promotionService.get(id);
		Promotion p = productForm.getPromotion(oldPromotion, productService, operator);
		uploadImg(oldPromotion, request, img);
		try {
			promotionService.update(p, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}


	public Set<PromotionOrderRule> getJsonData(Promotion promotion,
			String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Set<PromotionOrderRule> orderRules = new HashSet<PromotionOrderRule>();
		for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			JSONArray j = JSONArray.fromObject(jsonObject.get(key));
			for (int i = 0; i < j.size(); i++) {
				PromotionOrderRule orderRule = new PromotionOrderRule();
				orderRule.setMinAmount(new BigDecimal(key));
				orderRule.setPresentBatch(presentService.getBatch(Long
						.valueOf((String) JSONObject.fromObject(j.get(i)).get(
								"id"))));
				orderRule.setAmount(orderRule.getPresentBatch().getValue());
				orderRule.setPresentNum(Integer.valueOf((String) JSONObject
						.fromObject(j.get(i)).get("count")));
				orderRule.setPromotion(promotion);
				orderRules.add(orderRule);
			}
		}
		return orderRules;
	}

	/**
	 * 得到礼券赠送规则列表
	 * 
	 * @param orderRules
	 * @param orderRulesBeans
	 */
	private void getOrderRulesBeans(List<PromotionOrderRule> orderRules,List<OrderRulesBean> orderRulesBeans) {
		if (orderRules == null || orderRules.isEmpty()) {
			return;
		}
		List<OrderPresnetBean> orderPresnetBeans = new ArrayList<OrderPresnetBean>();// 礼券赠送数量和赠送批次的列表
		OrderRulesBean orderRulesBean = new OrderRulesBean(); // 礼券赠送规则
		orderRulesBean.setMinAmount(orderRules.get(0).getMinAmount()); // 把订单应满足的金额设置到规则中
		for (PromotionOrderRule orderRule : orderRules) { // 查找相同金额的礼券赠送规则
			if (orderRule.getMinAmount().equals(orderRulesBean.getMinAmount())) { // 如果金额相同
				orderRulesBean.setManner(orderRule.getPromotion().getManner()
						.getId());
				OrderPresnetBean orderPresnetBean = new OrderPresnetBean(); //
				orderPresnetBean.setPresentBatch(orderRule.getPresentBatch()); // 设置赠送的批次
				orderPresnetBean.setPresentNum(orderRule.getPresentNum()); // 设置赠送的数量
				orderPresnetBeans.add(orderPresnetBean);
			}
		}
		orderRulesBean.setOrderPresnetBeans(orderPresnetBeans);
		orderRulesBeans.add(orderRulesBean);
		getOrderRulesBeans(
				removeFromList(orderRules, orderRulesBean.getMinAmount()),
				orderRulesBeans);
	}
	
	private RegisterRulesBean getRegisterRulesBean(PromotionRegisterRule registerRule) {
		if (registerRule == null) {
			return null;
		}
		RegisterRulesBean registerRulesBean = new RegisterRulesBean(); // 礼券赠送规则
		RegisterPresentBean registerPresentBean = new RegisterPresentBean();// 礼券赠送数量和赠送批次的列表
		registerPresentBean.setPresentBatch(registerRule.getPresentBatch()); // 设置赠送的批次
		registerPresentBean.setPresentNum(registerRule.getPresentNum()); // 设置赠送的数量
		registerRulesBean.setRegisterPresentBean(registerPresentBean);
		return registerRulesBean;
	}

	private List<PromotionOrderRule> toList(Set<PromotionOrderRule> orderRules) {
		List<PromotionOrderRule> promotionOrderRules = new ArrayList<PromotionOrderRule>();
		if (orderRules != null) {
			for (PromotionOrderRule orderRule : orderRules) {
				if (orderRule.getPresentNum() != null
						&& orderRule.getPresentBatch() != null) {
					promotionOrderRules.add(orderRule);
				}
			}
		}
		return promotionOrderRules;
	}
	
	private PromotionRegisterRule getRegisterRule(Set<PromotionRegisterRule> registerRuleSet) {
		if (CollectionUtils.isNotEmpty(registerRuleSet)) {
			for (PromotionRegisterRule registerRule : registerRuleSet) {
				if (registerRule.getPresentNum() != null
						&& registerRule.getPresentBatch() != null) {
					return registerRule;
				}
			}
		}
		return null;
	}

	private List<PromotionOrderRule> removeFromList(
			List<PromotionOrderRule> orderRules, BigDecimal minAmount) {
		List<PromotionOrderRule> promotionOrderRules = new ArrayList<PromotionOrderRule>();
		if (orderRules != null) {
			for (PromotionOrderRule orderRule : orderRules) {
				if (!orderRule.getMinAmount().equals(minAmount)) {
					promotionOrderRules.add(orderRule);
				}
			}
		}
		return promotionOrderRules;
	}
	/**
	 * 上传图片
	 * @param promotion
	 * @param request
	 * @param img
	 */
	private void uploadImg(Promotion promotion, HttpServletRequest request,
			MultipartFile img) {
		if (img != null && !StringUtils.isNullOrEmpty(img.getOriginalFilename())) {
			String imgName = generateImageName(img.getOriginalFilename());
			boolean isSuccess = fileUp(img, imgName);
			if(isSuccess){
				promotion.setAdvertImage(IMGURLPATH+imgName);
			}
		}
	}
	private String generateImageName(String name) {
		CharSequence a = name.subSequence(name.length() - IMGSUFFIXLENGTH,
				name.length());
		java.util.Date nowDate = new java.util.Date();
		String imgName = IMGPREFIX + java.lang.Long.toString(nowDate.getTime());
		return imgName + a;
	}

	private boolean fileUp(MultipartFile img, String name) {
		CharSequence suffix = name.subSequence(name.length() - IMGSUFFIXNAMELENGTHS,
				name.length());
		if(!"jpg".equals(suffix)&&!"bmp".equals(suffix)&&!"gif".equals(suffix)&&!"png".equals(suffix)){
			return false;
		}
		
		File file = new File(IMGPATH);
		if (!file.exists()) {
			file.mkdir();
		}
		File imagefile = new File(IMGPATH+ name);
		return copy(img, imagefile);
	}

	private boolean copy(MultipartFile src, File dst) {
		boolean boolFile = true;
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(src.getInputStream(), FILEREAD);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						FILEREAD);
				byte[] buffer = new byte[FILEREAD];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} catch (FileNotFoundException e) {
				boolFile = false;
			} catch (IOException e) {
				boolFile = false;
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (IOException e) {
			boolFile = false;
		}
		return boolFile;
	}
	
	@RequestMapping(value="/savePresentForNewCustomer", method=RequestMethod.POST)
	public ModelAndView savePresentForNewCustomer(@Valid RegisterPresentSendForm registerPresentSendForm,
			@MyInject Employee operator,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request) throws ParseException,
			IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException {
		ModelAndView mav = new ModelAndView(PROMOTION_RESULT);
		Promotion promotion = registerPresentSendForm.getPromotion(null, operator);
		// 广告图片
		uploadImg(promotion, request, img);
		// 活动规则数据
		promotion.setRegisterRules(analyzePromotionRegisterRules(promotion,registerPresentSendForm.getPromotionRuleData()));
		try {
			promotionService.save(promotion, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}
	
	public Set<PromotionRegisterRule> analyzePromotionRegisterRules(Promotion promotion,
			String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Set<PromotionRegisterRule> registerRules = new HashSet<PromotionRegisterRule>();
		for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			JSONArray j = JSONArray.fromObject(jsonObject.get(key));
			for (int i = 0; i < j.size(); i++) {
				PromotionRegisterRule registerRule = new PromotionRegisterRule();
				registerRule.setPresentBatch(presentService.getBatch(Long
						.valueOf((String) JSONObject.fromObject(j.get(i)).get(
								"id"))));
				registerRule.setPresentNum(Integer.valueOf((String) JSONObject
						.fromObject(j.get(i)).get("count")));
				registerRule.setPromotion(promotion);
				registerRules.add(registerRule);
			}
		}
		return registerRules;
	}
	
	@RequestMapping(value="/updatePresentForNewCustomer", method=RequestMethod.POST)
	public ModelAndView updatePresentForNewCustomer(@Valid RegisterPresentSendForm registerPresentSendForm,
			@MyInject Employee operator,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request) throws ParseException{
		ModelAndView mav = new ModelAndView(PROMOTION_RESULT);
		Promotion newPromotion = null;
		Promotion oldPromotion = promotionService.get(registerPresentSendForm.getPromotionId());
		uploadImg(oldPromotion, request, img);
		newPromotion = registerPresentSendForm.getPromotion(oldPromotion, operator);
		newPromotion.setRegisterRules(analyzePromotionRegisterRules(newPromotion, registerPresentSendForm.getPromotionRuleData()));
		try {
			promotionService.update(newPromotion, operator);
		} catch (PromotionException e) {
			mav.addObject(FLAG, true);
			mav.addObject(ERRORMESSAGE, e.getMessage());
		}
		return mav;
	}
	
	@RequestMapping(value = "/tag", method = RequestMethod.GET)
	public ModelAndView promotiontag(Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/promotion/promotion_tag");
		List<PromotionTag> promotionTag = promotionService.findPromotionTag(null, pagination);
		modelAndView.addObject("promotionTag",promotionTag);
		modelAndView.addObject("pagination",pagination);
		return modelAndView;
	}
	
	@RequestMapping(value = "/{id}/tag", method = RequestMethod.GET)
	public ModelAndView getpromotiontag(@PathVariable("id") Long id){
		ModelAndView modelAndView = new ModelAndView("/promotion/promotion_tag_update");
		PromotionTag promotionTag = promotionService.getPromotionTag(id);
		Code code  = codeService.get(Code.PROMOTION_TYPE);
		modelAndView.addObject("promotionTag",promotionTag);
		modelAndView.addObject("code",code.getChildren());
		return modelAndView;
	}
	
	@RequestMapping(value = "/eidt/tag", method = RequestMethod.POST)
	public ModelAndView eidt(PromotionTag promotionTag,
			@MyInject Employee operator){
		if(promotionTag.getId()!=null){
			PromotionTag promotiontag = promotionService.getPromotionTag(promotionTag.getId());
			promotiontag.setUrl(promotionTag.getUrl());
			promotiontag.setType(codeService.get(promotionTag.getType().getId()));
			promotiontag.setRank(promotionTag.getRank());
			promotiontag.setAvailable(promotionTag.isAvailable());
			promotiontag.setEmployee(operator);
			promotionService.saveorupdateTag(promotiontag);
		}else{
			promotionTag.setEmployee(operator);
			promotionTag.setCreatetime(new Date());
			promotionService.saveorupdateTag(promotionTag);
		}
		return new ModelAndView("redirect:/promotion/tag");
	}
	
	@RequestMapping(value = "/rank/tag", method = RequestMethod.POST)
	public ModelAndView tagRank(Long id,String rank,boolean available,String type){
		ModelAndView modelAndView = new ModelAndView("/promotion/rank");
		PromotionTag promotionTag = promotionService.getPromotionTag(id);
		if(RANK.equals(type)){
			promotionTag.setRank(Integer.valueOf(rank));
		}else{
			promotionTag.setAvailable(!available);
		}
		promotionService.saveorupdateTag(promotionTag);
		modelAndView.addObject(RESULT_MESSAGE, "success");
		modelAndView.addObject("promotionTag", promotionTag);
		return modelAndView;
	}
	
}