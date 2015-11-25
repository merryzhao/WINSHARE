/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.presentcard;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.presentcard.PresentCardUtils;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.pagination.Pagination;

/**
 * 处理礼品卡相关页面跳转
 * 
 * @author Chenlong
 * @version 1.0,2011-9-5
 */
@Controller
@RequestMapping("/presentcard")
public class PresentCardController {
	@Autowired
	PresentCardService presentCardService;

	@Autowired
	CodeService codeService;

	@Autowired
	CustomerService customerService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/initnew", method = RequestMethod.GET)
	public ModelAndView initnew() throws ParseException {
		ModelAndView mav = new ModelAndView("/presentcard/_new");
		return mav;
	}

	@RequestMapping(value = "/storePresentCard", method = RequestMethod.GET)
	public ModelAndView goStore() {
		ModelAndView mav = new ModelAndView("/presentcard/store");
		return mav;
	}

	/**
	 * 入库
	 * 
	 * @param file
	 * @param pagination
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/store", method = RequestMethod.POST)
	public ModelAndView store(
			@RequestParam(value="file",required=false) MultipartFile file,
			@Parameter("storeType") Integer storeType,
			@RequestParam(value = "presentCardIdstr", required = false) String presentCardIdstr,
			@MyInject Pagination pagination, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView("/presentcard/store");
		List<String> idList = new ArrayList<String>();
		switch (storeType) {
		case 0:
			InputStream inputStream;
			Workbook excel = null;

			try {
				inputStream = file.getInputStream();
				excel = Workbook.getWorkbook(inputStream);
			} catch (Exception e1) {
				mav.addObject("exceptionMessage", "无法读取excel,请检查文件类型是否正确");
			}
			if (excel != null) {
				Sheet sheet = excel.getSheet(0);
				for (int i = 0; i < sheet.getRows(); i++) {
					String id = sheet.getCell(0, i).getContents();
					if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
						idList.add(id);
					}
				}
			}
			break;
		case 1:
			String[] ids = presentCardIdstr.split(",");
			for (int i = 0; i < ids.length; i++) {
				if (StringUtils.isNotBlank(ids[i]) && StringUtils.isNumeric(ids[i])) {
					idList.add(ids[i]);
				}
			}
			break;

		default:
			break;
		}

		try {
			List<PresentCard> storageSuccessPresentCards = presentCardService
					.store(idList.toArray(new String[idList.size()]), employee);
			mav.addObject("storageSuccessPresentCards",
					storageSuccessPresentCards);
		} catch (PresentCardException e) {
			mav.addObject("exceptionMessage", e.getMessage());
		}

		return mav;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam("type") Long type,
			@RequestParam("quantity") int quantity, @MyInject Employee operator)
			throws ParseException {
		ModelAndView mav = new ModelAndView("/presentcard/_new");
		List<PresentCard> list = presentCardService.create(type, quantity,
				operator);
		mav.addObject("list", list);
		return mav;
	}

	/**
	 * 跳转到查询礼品卡页面
	 * 
	 * @param cardQueryForm
	 * @param pagination
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ModelAndView query() throws ParseException {
		// 礼品卡状态
		Code status = codeService.get(Code.PRESENT_CARD_STATUS);
		// 礼品卡类型
		Code type = codeService.get(Code.PRESENT_CARD_TYPE);
		// 返回
		ModelAndView mav = new ModelAndView("/presentcard/query_giftcard");
		mav.addObject("status", status.getChildren());
		mav.addObject("types", type.getChildren());
		return mav;
	}

	/**
	 * 查询礼品卡
	 * 
	 * @param presentFindForm
	 * @param pagination
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView list(@Valid PresentCardQueryForm cardQueryForm,
			@MyInject Pagination pagination) throws ParseException {
		// 构建map
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ids", cardQueryForm.getIds());
		parameters.put("type", cardQueryForm.getType());
		parameters.put("statusList", cardQueryForm.getStatus());
		parameters.put("orderId", cardQueryForm.getOrderId());
		parameters.put("denomination", cardQueryForm.getDenomination());
		parameters.put("startDate", cardQueryForm.getStartDate());
		parameters.put("endDate", cardQueryForm.getEndDate());
		// 获取礼品卡列表
		List<PresentCard> presentCards = presentCardService.find(parameters,
				pagination);
		// 礼品卡状态
		Code status = codeService.get(Code.PRESENT_CARD_STATUS);
		// 礼品卡类型
		Code type = codeService.get(Code.PRESENT_CARD_TYPE);
		// 返回
		ModelAndView mav = new ModelAndView("/presentcard/query_giftcard");
		mav.addObject("presentCards", presentCards);
		mav.addObject("cardForm", cardQueryForm);
		mav.addObject("pagination", pagination);
		mav.addObject("status", status.getChildren());
		mav.addObject("types", type.getChildren());
		return mav;
	}

	/**
	 * 跳转到密码修改页面
	 */
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public ModelAndView password() {
		PresentCardModifyForm presentCardModifyForm = new PresentCardModifyForm();
		ModelAndView mav = new ModelAndView("/presentcard/modify_password");
		mav.addObject(presentCardModifyForm);
		return mav;
	}

	/**
	 * 修改密码
	 * 
	 * @param presentCardModifyForm
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public ModelAndView modifyPassword(
			@Valid PresentCardModifyForm presentCardModifyForm,
			BindingResult result, @MyInject Employee operator) {
		ModelAndView mav = new ModelAndView("/presentcard/modify_password");
		if (!result.hasErrors()) {
			String id = presentCardModifyForm.getId();
			String newPassword = presentCardModifyForm.getPassword();
			try {
				presentCardService.modifyPassword(id, newPassword, operator);
			} catch (PresentCardException e) {
				mav.addObject("exceptionMessage", e.getMessage());
			}
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
			return mav;
		} else {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_INTERNAL_ERROR);
			return mav;
		}
	}

	/**
	 * 判断ID是否存在
	 * 
	 * @param presentCardId
	 * @return
	 */
	@RequestMapping(value = "/verifyId", method = RequestMethod.POST)
	public ModelAndView verifyId(
			@Parameter("presentCardId") String presentCardId) {
		ModelAndView mav = new ModelAndView("/presentcard/verify_result");
		PresentCard pc = presentCardService.get(presentCardId);
		if (null == pc) {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
		} else {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		}
		return mav;
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ModelAndView getPresentCardAjax(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView("/presentcard/present_detail");
		PresentCard presentCard = presentCardService.get(id);
		ModelMap map = new ModelMap();
		if (presentCard == null) {
			map.put(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);

			return mav;
		}
		map.put(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		map.put(ControllerConstant.MESSAGE_KEY, "success");
		map.put("presentCard", presentCard);
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/cancelpage", method = RequestMethod.GET)
	public ModelAndView canelPage() {
		ModelAndView mav = new ModelAndView("/presentcard/cancel_presentcard");
		return mav;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancelPresentCard(@RequestParam("id") String id,
			@MyInject Employee employee) {
		ModelAndView mav = new ModelAndView("/presentcard/result");
		PresentCard presentCard = presentCardService.get(id);
		Long result = NumberUtils.LONG_ZERO;
		try {
			List<PresentCard> list = new ArrayList<PresentCard>();
			list.add(presentCard);
			presentCardService.logout(list, employee);
		} catch (PresentCardException e) {
			result = NumberUtils.LONG_ONE;
		}
		mav.addObject(ControllerConstant.RESULT_KEY, result);
		return mav;
	}

	@RequestMapping(value = "/delaypage", method = RequestMethod.GET)
	public ModelAndView delayPage() {
		ModelAndView mav = new ModelAndView("/presentcard/delay_presentcard");
		return mav;
	}

	@RequestMapping(value = "/delay", method = RequestMethod.GET)
	public ModelAndView delayPresentCard(@RequestParam("id") String id,
			@RequestParam("newdate") String newDate, @MyInject Employee employee) {
		Long result = NumberUtils.LONG_ZERO;
		ModelAndView mav = new ModelAndView("/presentcard/result");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			presentCardService.delay(id, sdf.parse(newDate), employee);
		} catch (PresentCardException e) {
			result = NumberUtils.LONG_ONE;
		} catch (ParseException e) {
			result = NumberUtils.LONG_ONE;
		}
		mav.addObject(ControllerConstant.RESULT_KEY, result);
		return mav;
	}

	/**
	 * 查询需要打印的卡片
	 * 
	 * @return
	 * @throws ParseException
	 * 
	 */
	@RequestMapping(value = "/printlist", method = RequestMethod.POST)
	public ModelAndView getPrintCardList(@RequestParam("count") int count)
			throws ParseException {
		Long result = NumberUtils.LONG_ZERO;
		ModelAndView mav = new ModelAndView("/presentcard/list");
		List<PresentCard> listCard;
		try {
			listCard = presentCardService.findForPrinting(count);
			mav.addObject("cardList", listCard);
			StringBuffer presentBuffer = new StringBuffer();
			for (int i = 0; i < listCard.size(); i++) {
				presentBuffer.append(listCard.get(i).getId());
				if (i != listCard.size() - 1) {
					presentBuffer.append(";");
				}
			}
			mav.addObject("printString", presentBuffer.toString());
		} catch (PresentCardException e) {
			mav.addObject("message",e.getMessage());
		}
		mav.addObject(ControllerConstant.RESULT_KEY, result);
		return mav;
	}

	/**
	 * 跳转到卡片印刷页面
	 * 
	 * @return
	 * @throws ParseException
	 * 
	 */
	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public ModelAndView goPrintCardList() throws ParseException {
		ModelAndView mav = new ModelAndView("/presentcard/print_card");
		return mav;
	}

	/**
	 * 跳转到卡片详情
	 * 
	 * @return
	 * @throws ParseException
	 * 
	 */

	@RequestMapping(value = "/cardinfo/{id}", method = RequestMethod.GET)
	public ModelAndView cardInfo(@PathVariable("id") String id)
			throws ParseException {
		ModelAndView mav = new ModelAndView("/presentcard/card_info");
		mav.addObject("card", presentCardService.get(id));
		return mav;
	}
	
	@RequestMapping(value = "/goProductNew", method = RequestMethod.GET)
	public ModelAndView goProductNew()  {
		ModelAndView mav = new ModelAndView("/presentcard/product_new");
		mav.addObject("physicalCategory", PresentCardUtils.physicalCategory);
		mav.addObject("electronicCategory", PresentCardUtils.electronicCategory);
		return mav;
	}
	
	@RequestMapping(value = "/productCreate", method = RequestMethod.POST)
	public ModelAndView productCreate(@RequestParam("category") Long category,
			@RequestParam("listPrice") BigDecimal listPrice,@RequestParam("name") String name,
			@RequestParam("stock") int stock)  {
		String barcode = "";
		if (category.equals(PresentCardUtils.electronicCategory)) {
			barcode = "1" + listPrice;
		} else if (category.equals(PresentCardUtils.physicalCategory)) {
			barcode = "2" + listPrice;
		}
		final long cityChengdu  = 1507L;
		Product product = new Product();
		product.setBarcode(barcode);
		product.setCategory(new Category(category));
		product.setListPrice(listPrice);
		product.setName(name);
		product.setSort(new Code(Code.PRODUCT_SORT_MERCHANDISE));
		product.setVirtual(true);
		product.setCreateTime(new Date());
		product.setUpdateTime(new Date());
		product.setManufacturer("文轩");
		product.setHasImage(true);
		ProductSale productSale = new ProductSale();
		productSale.setProduct(product);
		productSale.setShop(new Shop(1L));//文轩
		productSale.setSalePrice(product.getListPrice());
		productSale.setBasicPrice(product.getListPrice());
		productSale.setSellName(product.getName());
		productSale.setStockQuantity(stock);
		productSale.setUpdateTime(new Date());
		productSale.setLocation(new Area(cityChengdu));//成都
		productSale.setAuditStatus(new Code());
		productSale.setSaleStatus(new Code(Code.PRODUCT_SALE_STATUS_ONSHELF));
		productSale.setAuditStatus(new Code(Code.PRODUCT_AUDIT_STATUS_CREATE));
		productSale.setStorageType(new Code(Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_SELLER));
		productSale.setSupplyType(new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL));
		productService.saveProduct(productSale);
		ModelAndView mav = new ModelAndView();
		mav.setView( new RedirectView("/presentcard/productList"));
		return mav;
	}
	
	@RequestMapping(value = "/productList")
	public ModelAndView productList(@MyInject Pagination pagination){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("virtual", true);
		List<ProductSale> productSales = productService.findProductSale(parameters, pagination);
		ModelAndView mav = new ModelAndView("presentcard/product_list");
		mav.addObject("productSales", productSales);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value = "/orderList")
	public ModelAndView orderList(@MyInject Pagination pagination){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("virtual", true);
		List<Order> orderList = orderService.find(parameters,(short)0, pagination);
		ModelMap modelMap = new ModelMap();
		modelMap.put("orderList", orderList);
		modelMap.put("pagination", pagination);
		return new ModelAndView("presentcard/order_list", modelMap);
	}

}
