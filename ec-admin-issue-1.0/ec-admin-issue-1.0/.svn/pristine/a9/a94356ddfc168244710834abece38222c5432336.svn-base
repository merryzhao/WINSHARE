/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.seller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductChannelApply;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.Seller;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.seller.SellerService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.DateUtils;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * 
 * @author rsy
 * @version 1.0,2011-9-27
 */
@Controller
@RequestMapping("/seller")
public class SellerController {
	private static final String IMGPATH = "/home/www/upload/seller/";
	private static final int FILEREAD = 1024;
	private static final int IMGSUFFIXLENGTH = 4;
	private static final String IMGPREFIX = "img";
	private static final String SHOPFORM = "shopForm";
	private static final String NAMEERROR = "用户名已经存在";
	@Autowired
	SellerService sellerService;
	@Autowired
	ShopService shopService;
	@Autowired
	CodeService codeService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	/**
	 * 跳转到卖家新建商品管理查询页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/productManage", method = RequestMethod.GET)
	public ModelAndView toProductManage() {
		// 返回
		ModelAndView mav = new ModelAndView("/seller/seller_new_product_manage");
		// 数据准备
		getDateProductManagePrepare(mav);
		return mav;
	}

	/**
	 * 跳转到卖家渠道销售审批查询页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/salesAudit", method = RequestMethod.GET)
	public ModelAndView toSalesAudit() {
		// 返回
		ModelAndView mav = new ModelAndView(
				"/seller/seller_channel_sales_audit");
		// 数据准备
		getDateChannelSalesAudit(mav);
		return mav;
	}

	/**
	 * 查询卖家新建商品管理
	 * 
	 * @param pSForm
	 * @param result
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/productManage", method = RequestMethod.POST)
	public ModelAndView productManage(@Valid SellerProductSales pSForm,
			BindingResult result, @MyInject Pagination pagination) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(pSForm.getProductCode(), pSForm.getCodes());// 商品编码及商品自编码
		parameters.put("shopId", pSForm.getShop());// 店铺ID
		parameters.put("businessScope", pSForm.getClassifiedManage());// 经营分类String
		parameters.put("auditStatus", pSForm.getAuditStatus());// 审核状态
		parameters.put("saleStatus", pSForm.getProductUpDown());// 上下架
		List<ProductSale> productSales = productService.findProductSale(
				parameters, pagination);
		// 返回
		ModelAndView mav = new ModelAndView("/seller/seller_new_product_manage");
		// 返回查询结果
		mav.addObject("productSales", productSales);
		mav.addObject("pagination", pagination);
		mav.addObject("pSForm", pSForm);
		// 数据准备
		getDateProductManagePrepare(mav);
		return mav;
	}

	/**
	 * 卖家渠道销售审批查询
	 * 
	 * @param pSForm
	 * @param result
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/salesAudit", method = RequestMethod.POST)
	public ModelAndView salesAudit(@Valid SellerProductSales pSForm,
			BindingResult result, @MyInject Pagination pagination) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(pSForm.getProductCode(), pSForm.getCodes());// 商品编码及商品自编码
		parameters.put("shopId", pSForm.getShop());// 店铺ID
		parameters.put("typeId", pSForm.getApplyType());// 申请类型
		parameters.put("stateId", pSForm.getStatus());// 状态
		parameters.put("createStartDate", pSForm.getStartDate());// 申请起始时间
		parameters.put("createEndDate", pSForm.getEndDate());// 申请截止时间
		List<ProductChannelApply> productChannelApplies = productService
				.findProductChannelApply(parameters, pagination);
		// 返回
		ModelAndView mav = new ModelAndView(
				"/seller/seller_channel_sales_audit");
		// 返回查询结果
		mav.addObject("pCAs", productChannelApplies);
		mav.addObject("pagination", pagination);
		mav.addObject("pSForm", pSForm);
		// 数据准备
		getDateChannelSalesAudit(mav);
		return mav;
	}

	/**
	 * 卖家新建商品管理审核
	 * 
	 * @param ids
	 * @param audit
	 * @return
	 */
	@RequestMapping(value = "/productManageAudit", method = RequestMethod.POST)
	public ModelAndView productManageAudit(@RequestParam("ids") Long[] ids,
			@RequestParam("audit") Boolean audit) {
		// 成功审核数量
		int succeed = 0;
		// 审核失败数量
		int fail = 0;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productSaleIds", ids);
		Pagination pagination = new Pagination();
		pagination.setPageSize(MagicNumber.PAGE_SIZE_200);
		List<ProductSale> productSales = productService.findProductSale(
				parameters, pagination);
		for (ProductSale productSale : productSales) {
			if (!productSale.getAuditStatus().getId()
					.equals(Code.PRODUCT_AUDIT_STATUS_CREATE)) {
				fail++;
				continue;
			}
			if (audit) {
				productSale.setAuditStatus(new Code(
						Code.PRODUCT_AUDIT_STATUS_PASS));
			} else {
				productSale.setAuditStatus(new Code(
						Code.PRODUCT_AUDIT_STATUS_FAIL));
			}
			try {
				productService.update(productSale);
				succeed++;
			} catch (Exception e) {
				fail++;
			}
		}
		ModelAndView mav = new ModelAndView("seller/seller");
		mav.addObject("succeed", succeed);
		mav.addObject("fail", fail);
		return mav;
	}

	/**
	 * 卖家渠道销售审批
	 * 
	 * @param ids
	 * @param audit
	 * @return
	 */
	@RequestMapping(value = "/channelSalesAudit", method = RequestMethod.POST)
	public ModelAndView channelSalesAudit(@RequestParam("ids") Long[] ids,
			@RequestParam("audit") Boolean audit) {
		// 成功审核数量
		int succeed = 0;
		// 审核失败数量
		int fail = 0;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productSaleIds", ids);
		List<ProductChannelApply> productChannelApplies = productService
				.findProductChannelApply(parameters, null);
		for (ProductChannelApply productChannelApply : productChannelApplies) {
			if (!productChannelApply.getState().getId()
					.equals(Code.PRODUCT_AUDIT_STATUS_CREATE)) {
				fail++;
				continue;
			}
			try {
				if (audit) {
					productService.auditProductChannelApply(
							productChannelApply, new Code(
									Code.PRODUCT_AUDIT_STATUS_PASS));
				} else {
					productService.auditProductChannelApply(
							productChannelApply, new Code(
									Code.PRODUCT_AUDIT_STATUS_FAIL));
				}
				succeed++;
			} catch (Exception e) {
				fail++;
			}
		}
		ModelAndView mav = new ModelAndView("seller/seller");
		mav.addObject("succeed", succeed);
		mav.addObject("fail", fail);
		return mav;
	}

	/**
	 * 跳转到卖家信息管理画面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ModelAndView query(@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/seller/seller_manage");
		Code code = codeService.get(Code.SHOP_STATE);
		mav.addObject("shopStates", code.getChildren());
		mav.addObject(pagination);
		return mav;
	}

	/**
	 * 查询卖家信息
	 * 
	 * @param sellerQueryForm
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView list(SellerQueryForm sellerQueryForm,
			@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/seller/seller_manage");
		Code code = codeService.get(Code.SHOP_STATE);
		mav.addObject("shopStates", code.getChildren());
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params = sellerQueryForm.getParamsMap();
		} catch (ParseException e) {
			return mav;
		}
		List<Seller> sellers = sellerService.findSeller(params, pagination);
		mav.addObject("sellers", sellers);
		mav.addObject(pagination);
		return mav;
	}

	/**
	 * 卖家新建商品管理查询数据准备
	 * 
	 * @param mav
	 */
	private void getDateProductManagePrepare(ModelAndView mav) {
		// 店铺数据
		List<Shop> shops = shopService.find(null, null);
		// 经营分类
		Category categorys = categoryService.get(Category.ALL_CATEGORY);
		// 审核状态
		Code productAudits = codeService.get(Code.PRODUCT_AUDIT_STATUS);
		// 商品状态
		Code produactStatus = codeService.get(Code.PRODUCT_SALE_STATUS);
		mav.addObject("shops", shops);
		mav.addObject("categorys", categorys);
		mav.addObject("productAudits", productAudits);
		mav.addObject("produactStatus", produactStatus);
	}

	/**
	 * 卖家渠道销售审批查询数据准备
	 * 
	 * @param mav
	 */
	private void getDateChannelSalesAudit(ModelAndView mav) {
		// 店铺数据
		List<Shop> shops = shopService.find(null, null);
		// 申请类型
		Code type = codeService.get(Code.PRODUCT_CHANNEL_APP_STATUS);
		// 状态
		Code state = codeService.get(Code.PRODUCT_AUDIT_STATUS);
		mav.addObject("shops", shops);
		mav.addObject("types", type);
		mav.addObject("states", state);
	}

	/**
	 * 更改shop状态
	 * 
	 * @param sellerId
	 * @param updateType
	 * @return
	 */
	@RequestMapping(value = "/updateState", method = RequestMethod.POST)
	public ModelAndView updateState(@Parameter("sellerId") Long sellerId,
			@Parameter("updateType") Integer updateType) {
		ModelAndView mav = new ModelAndView("/seller/seller_manage");
		Seller seller = sellerService.get(sellerId);
		Date nowDate;
		try {
			switch (updateType) {
			// 激活
			case MagicNumber.ONE:
				seller.getShop().setState(new Code(Code.SHOP_STATE_PASS));
				nowDate = new Date();
				seller.getShop().setActiveDate(nowDate);
				mav.addObject("activeDate", new SimpleDateFormat(
						DateUtils.SHORT_DATE_FORMAT_STR).format(nowDate));
				break;
			// 屏蔽
			case MagicNumber.TWO:
				seller.getShop()
						.setState(new Code(Code.SHOP_STATE_SEARCH_FAIL));
				break;
			// 完全冻结
			case MagicNumber.THREE:
				seller.getShop().setState(new Code(Code.SHOP_STATE_FAIL));
				break;
			// 注销
			case MagicNumber.FOUR:
				seller.getShop().setState(new Code(Code.SHOP_STATE_CANCEL));
				break;
			default:
				break;
			}
			mav.addObject(ControllerConstant.RESULT_KEY, updateType);
		} catch (Exception e) {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		sellerService.update(seller);
		return mav;
	}

	/**
	 * 跳转添加卖家画面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/_new", method = RequestMethod.GET)
	public ModelAndView newShop() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/seller/shop_add");
		ShopForm shopForm = new ShopForm();
		mav.addObject(SHOPFORM, shopForm);
		mav.addObject("nameError", null);
		mav.addObject("storage",
				codeService.get(Code.STORAGE_AND_DELIVERY_TYPE));
		return mav;
	}

	/**
	 * 跳转修改卖家画面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/_edit", method = RequestMethod.GET)
	public ModelAndView editShop(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/seller/shop_edit");
		ShopForm shopForm = new ShopForm();
		Shop shop = shopService.get(id);
		if (shop != null) {
			shopForm.setFormValue(shop);
			mav.addObject("openTime", shop.getCreateDate());
			mav.addObject("activeTime", shop.getActiveDate());
		} else {
			mav.addObject("openTime", null);
			mav.addObject("activeTime", null);
		}
		mav.addObject("pwd", shopForm.getPassWrod());
		mav.addObject("storage",
				codeService.get(Code.STORAGE_AND_DELIVERY_TYPE));
		mav.addObject(SHOPFORM, shopForm);
		mav.addObject("nameError", null);
		return mav;
	}

	/**
	 * 保存卖家
	 * 
	 * @param shopForm
	 * @param img
	 * @param request
	 * @param operator
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/_save", method = RequestMethod.POST)
	public ModelAndView saveShop(
			@Valid ShopForm shopForm,
			BindingResult result,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request, @MyInject Employee operator)
			throws ParseException {
		ModelAndView mav = new ModelAndView();
		if (isSellerEcxist(shopForm.getSeller(null).getName())) {
			mav.addObject(SHOPFORM, shopForm);
			mav.addObject("nameError", NAMEERROR);
			mav.setViewName("/seller/shop_add");
			return mav;
		}
		if (result.hasErrors()) {
			mav.addObject(SHOPFORM, shopForm);
			mav.setViewName("/seller/shop_add");
			return mav;
		}
		Seller seller = shopForm.getSeller(null);
		Shop shop = shopForm.getShop(null, operator);
		shop.setState(new Code(Code.SHOP_STATE_CREATE));
		upImg(shop, request, img);// 设置图片
		seller.setShop(shop);
		Set<Seller> sellers = new HashSet<Seller>();
		sellers.add(seller);
		shop.setSellers(sellers);
		shopService.save(shop, operator);
		mav.setViewName("redirect:/seller/query");
		return mav;
	}

	/**
	 * 修改卖家
	 * 
	 * @param shopForm
	 * @param img
	 * @param request
	 * @param operator
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "_update", method = RequestMethod.POST)
	public ModelAndView updateShop(
			@Valid ShopForm shopForm,
			BindingResult result,
			@RequestParam(required = false, value = "localfile") MultipartFile img,
			HttpServletRequest request, @MyInject Employee operator)
			throws ParseException {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			mav.addObject(SHOPFORM, shopForm);
			mav.setViewName("/seller/shop_edit");
			return mav;
		}
		Shop shop = shopService.get(shopForm.getId());
		Seller seller = null;
		if (shop != null && shop.getSellers() != null) {
			for (Seller s : shop.getSellers()) {
				if (s.isShopManager()) {
					seller = s;
					break;
				}

			}
		}
		String oldPwd = request.getParameter("pwd");
		String newPwd = shopForm.getPassWrod();
		if (!oldPwd.equals(newPwd)) {
			shopForm.setPassWrod(newPwd);
		}
		seller = shopForm.getSeller(seller);
		shop = shopForm.getShop(shop, null);
		upImg(shop, request, img);// 设置图片
		shopService.update(shop, operator);
		mav.setViewName("redirect:/seller/query");
		return mav;
	}

	/**
	 * 上传图片
	 * 
	 * @param promotion
	 * @param request
	 * @param img
	 */
	private void upImg(Shop shop, HttpServletRequest request, MultipartFile img) {
		// 如果图片不为空
		if (img != null && !StringUtils.isBlank(img.getOriginalFilename())) {
			// 获取图片名
			String imgName = generateImageName(img.getOriginalFilename());
			// 设置图片名到shop
			shop.setLogo(imgName);
			// 上传图片到服务器
			fileUp(img, imgName);
		}
	}

	private boolean fileUp(MultipartFile img, String name) {
		File file = new File(IMGPATH);
		if (!file.exists()) {
			file.mkdir();
		}
		File imagefile = new File(IMGPATH + name);
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

	private String generateImageName(String name) {
		CharSequence a = name.subSequence(name.length() - IMGSUFFIXLENGTH,
				name.length());
		java.util.Date nowDate = new java.util.Date();
		String imgName = IMGPREFIX + java.lang.Long.toString(nowDate.getTime());
		return imgName + a;
	}

	private boolean isSellerEcxist(String sellerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", sellerName);
		List<Seller> sellers = sellerService.findSeller(params, null);
		if (sellers.isEmpty()) {
			return false;
		}
		return true;
	}
}
