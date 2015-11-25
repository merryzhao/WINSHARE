/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.order;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.admin.controller.excel.ExcelOrderPayListForm;
import com.winxuan.ec.admin.controller.order.ExpressToAcccountForm.ExpressToAccountException;
import com.winxuan.ec.admin.controller.product.ProductSaleForm;
import com.winxuan.ec.exception.BusinessException;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.EmployeeBussinessException;
import com.winxuan.ec.exception.OrderCloneException;
import com.winxuan.ec.exception.OrderDeliveryException;
import com.winxuan.ec.exception.OrderException;
import com.winxuan.ec.exception.OrderInitException;
import com.winxuan.ec.exception.OrderPresellException;
import com.winxuan.ec.exception.OrderShipperInfoMatchException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.OrderStockException;
import com.winxuan.ec.exception.PaymentCredentialException;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.ProductException;
import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.delivery.DeliveryInfo;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderBatchPay;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderDeliverySplit;
import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.ec.model.order.OrderExtend;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPackges;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.order.OrderShipperInfo;
import com.winxuan.ec.model.order.OrderTrack;
import com.winxuan.ec.model.order.OrderUpdateLog;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.order.BatchPayService;
import com.winxuan.ec.service.order.OrderExpressService;
import com.winxuan.ec.service.order.OrderInterfaceService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.order.OrderShipperInfoService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.refund.RefundService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.order.OrderDeliveryInfo;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.util.IOUtils;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.BigDecimalUtils;
import com.winxuan.framework.util.DateUtils;
import com.winxuan.services.dip.service.WmsOrderBlockService;
import com.winxuan.services.dip.service.WmsOrderService;
import com.winxuan.services.pss.model.vo.ProductSaleStockVo;
import com.winxuan.services.support.exception.RemoteBusinessException;

/**
 * 商品
 * 
 * @author HideHai
 * @version 1.0,2011-7-13
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	private static final Log LOG = LogFactory.getLog(OrderController.class);
	private static final String MESSAGE_SAVE_SUCCESS = "保存成功!";
	private static final String MESSAGE_SUCCESS = "操作成create功!";
	private static final Long ORDER_TRACK_TYPE = 1358L;
	private static final short AUDIT_RESULT_SUCCESS = 1;
	private static final short AUDIT_RESULT_FAIL = 0;
	// 为了保证号码为02885383268-6206和028-85383268-6206的订单通过审核，将PHONE_LENGTH_1调整为3
	private static final int PHONE_LENGTH_1 = 3;
	private static final int PHONE_LENGTH_2 = 14;
	private static final int MOBIL_LENGTH = 11;
	private static final Long CHANNEL_FENXIAO = 5L;
	private static final Object NOTDATA = 0;
	private static final String ORDER_ID_STR = "orderId";
	private static final String SEPARATOR = "-";
	private static final String CURRENTAUDITREMAIN = "currentCount";
	private static final String PREAUDITREMAIN = "preCount";
	private static final String PASSAUDIT = "passCount";
	private static final String UNAUDITCOUNT = "unAuditCount";
	private static final String ORDER_SHIPPER_COUNTS = "orderShipperCounts";
	private static final String ORDERSTR = "order";
	private static final String INDEX = ",";
	private static final String CHANGE = "change";
	private static final String LISTPAYMENT = "listPayment";
	private static final String MSG = "msg";
	private static final String SUCCESS_VIEW = "/order/success_result";
	private static final String BR = "<br />";
	private static final String MESSAGE = "message";
	private static final String RESULT_SUCCESS_STRING = "success";
	private static final String BOOK = "图书";
	private static final String MERCHANDISE = "百货";
	private String ajaxResult = "/order/result";
	private String resultChildren = "children";
	private AuditUnpassReason auditUnpassReason;

	@Autowired
	private PresentService presentService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PresentCardService presentCardService;
	@Autowired
	private OrderExpressService orderExpressService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private RefundService refundService;
	@Autowired
	private OrderDeliveryInfo orderDeliveryInfo;
	@Autowired
	private DcService dcService;
	@Autowired
	private BatchPayService batchPayService;
	@Autowired
	private OrderInterfaceService orderInterfaceService;
	@Autowired
	private WmsOrderBlockService wmsOrderBlockService;
	@Autowired
	private WmsOrderService wmsOrderService;
	@Autowired
	private OrderShipperInfoService orderShipperInfoService;

	private Long deliveryOptionNumber = Code.DELIVERY_OPTION;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView list(OrderForm orderForm, @RequestParam(value = "sortId", required = false) Short sortId,
			@MyInject Pagination pagination) throws UnsupportedEncodingException {
		final String four = "4";
		Map<String, Object> parameters = null;
		try {
			parameters = orderForm.generateQueryMap(channelService);
		} catch (ParseException e) {
			LOG.error(e.getMessage(), e);
		} catch (IllegalArgumentException e1) {
			ModelAndView mv = new ModelAndView("redirect:/order/search?message="
					+ URLEncoder.encode(e1.getMessage(), "UTF-8"));
			return mv;
		}
		List<Order> orderList = orderService
				.find(parameters, sortId == null ? Short.valueOf(four) : sortId, pagination);
		ModelMap modelMap = new ModelMap();
		modelMap.put("orderList", orderList);
		modelMap.put("selectRows", orderForm.getSelectRow());
		modelMap.put("pagination", pagination);
		modelMap.put(UNAUDITCOUNT, orderService.getCountOfUnaudited());
		modelMap.put(ORDER_SHIPPER_COUNTS, this.orderShipperInfoService.countNeedAuditShipperInfo());
		return new ModelAndView("/order/list", modelMap);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(value = "message", required = false) String message) {
		List<Channel> channels = channelService.findChildren(Channel.ROOT);
		ModelMap modelMap = new ModelMap();
		modelMap.put("message", message);
		modelMap.put("processStatus", codeService.get(Code.ORDER_PROCESS_STATUS).getAvailableChildren());
		modelMap.put("paymentStatus", codeService.get(Code.ORDER_PAY_STATUS).getAvailableChildren());
		modelMap.put("dcList", codeService.getAllDc());
		modelMap.put("outOfStockOptions", codeService.get(Code.OUT_OF_STOCK_OPTION).getAvailableChildren());
		modelMap.put("paymentTypes", codeService.get(Code.PAYMENT_TYPE).getAvailableChildren());
		modelMap.put("gradeTypes", Customer.ALL_GRADE_TYPE);
		modelMap.put("saleTypes", codeService.get(Code.PRODUCT_SALE_SUPPLY_TYPE).getAvailableChildren());
		modelMap.put("sortTypes", codeService.get(Code.PRODUCT_SORT).getAvailableChildren());
		modelMap.put("storageTypes", codeService.get(Code.STORAGE_AND_DELIVERY_TYPE).getAvailableChildren());
		modelMap.put("shops", shopService.find(null, null));
		modelMap.put("deliveryTypes", deliveryService.findDeliveryType(true));
		modelMap.put("channel", channels);
		modelMap.put(UNAUDITCOUNT, orderService.getCountOfUnaudited());
		modelMap.put(ORDER_SHIPPER_COUNTS, this.orderShipperInfoService.countNeedAuditShipperInfo());
		modelMap.put("deliveryCompanys", deliveryService.findDeliveryCompany(true));
		return new ModelAndView("/order/search", modelMap);
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public ModelAndView cancel(@RequestParam("id") String id, @RequestParam("processStatus") Long processStatus,
			@MyInject Employee employee) throws OrderStatusException, CustomerAccountException, PresentCardException,
			PresentException, ReturnOrderException {
		Order order = orderService.get(id);
		Code code = codeService.get(processStatus);
		orderService.cancel(order, code, employee);
		ModelAndView mav = new ModelAndView("redirect:/order/" + id);
		return mav;
	}

	@RequestMapping(value = "/createOrderTrackPrepare", method = RequestMethod.GET)
	public ModelAndView viewOrderTract() {
		Code parentCode = codeService.get(ORDER_TRACK_TYPE);
		ModelAndView mav = new ModelAndView("/order/create_order_track");
		mav.addObject("listCode", parentCode.getChildren());
		return mav;
	}

	@RequestMapping(value = "/orderTrack", method = RequestMethod.POST)
	public ModelAndView create(@Valid OrderTrackForm orderTrackForm, BindingResult result, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView("/order/ordertrack");
		Date time = new Date();
		if (!result.hasErrors()) {
			OrderTrack orderTrack = new OrderTrack();
			orderTrack.setOrder(orderService.get(orderTrackForm.getOrderid()));
			orderTrack.setCreateTime(time);
			orderTrack.setEmployee(employee);
			orderTrack.setContent(orderTrackForm.getContent());
			orderTrack.setType(codeService.get(Long.parseLong(orderTrackForm.getTypeId())));
			orderService.addTrack(orderTrack);
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
			mav.addObject("time", sdf.format(time));
			mav.addObject("employee", "admin");
			mav.addObject("type", codeService.get(Long.parseLong(orderTrackForm.getTypeId())).getName());
		}

		return mav;
	}

	@RequestMapping(value = "area/{id}", method = RequestMethod.POST)
	public ModelAndView viewArea(@PathVariable("id") Long id) throws OrderStatusException, CustomerAccountException {
		ModelAndView mv = new ModelAndView(ajaxResult);
		ModelMap modelMap = new ModelMap();
		Area area = areaService.get(id);
		modelMap.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		modelMap.put(ControllerConstant.MESSAGE_KEY, "");
		modelMap.put(resultChildren, area.getAvailableChildren());
		mv.addAllObjects(modelMap);
		return mv;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView viewOrderDetail(@PathVariable("id") String orderId,
			@RequestParam(value = "view", defaultValue = "detail", required = false) String viewName,
			@MyInject Pagination pagination) {
		Order order = orderService.get(orderId);
		final String zero = "0";

		List<ProductSale> productSaleList = new ArrayList<ProductSale>();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ORDER_ID_STR, orderId);
		List<OrderTrack> orderTrackList = null;

		ModelAndView mav = new ModelAndView();
		if ("detail".equals(viewName)) {
			orderTrackList = orderService.findTrack(parameters, Short.parseShort(zero), pagination);
			if (orderTrackList != null) {
				mav.addObject("trackSize", orderTrackList.size());// 订单跟踪记录条数
			} else {
				mav.addObject("trackSize", NOTDATA);
			}
			if (order.getUpdateLogList() != null) {
				mav.addObject("orderLogList", order.getUpdateLogList().size());// 订单更新日志记录条数
			} else {
				mav.addObject("orderLogList", NOTDATA);
			}
			if (order.getStatusLogList() != null) {
				mav.addObject("orderStatusList", order.getStatusLogList().size());// 订单状态日志记录条数
			} else {
				mav.addObject("orderStatusList", NOTDATA);//
			}
			for (OrderItem orderItem : order.getItemList()) {
				productSaleList.add(orderItem.getProductSale());
			}
			List<DeliveryCompany> deliveryCompanies = deliveryService.findDeliveryCompany(true);
			if (!deliveryCompanies.isEmpty()) {
				mav.addObject("deliveryCompanies", deliveryCompanies);
			}
			mav.addObject("returnOrderCount", MagicNumber.ZERO);
			if (order.getReturnOrderList() != null) {
				mav.addObject("returnOrderCount", order.getReturnOrderList().size());
			}
			/*
			 * if (order.isDangDangCOD()) { mav.addObject("dangdangWarhouse",
			 * OrderExtend.getDangDangCodWarehouse(order)); }
			 */
			if (order.isCODOrder()) {//
				mav.addObject("codWarhouse", OrderExtend.getCodWarehouseData(order));
			}
			mav.addObject("dcs", codeService.findByParent(Code.DELIVERY_CENTER));
			mav.setViewName("/order/order_detail");
			mav.addObject("productSaleList", productSaleList);

		} else if ("log".equals(viewName)) {
			mav.setViewName("/order/order_log");
		} else if ("status".equals(viewName)) {
			mav.setViewName("/order/order_status");
		} else if ("delivery".equals(viewName)) {
			BigDecimal totalListPrice = new BigDecimal(zero);
			BigDecimal totalSalePrice = new BigDecimal(zero);
			for (OrderItem oi : order.getItemList()) {
				totalListPrice = totalListPrice.add(oi.getListPrice()
						.multiply(new BigDecimal(oi.getDeliveryQuantity())));
				totalSalePrice = totalSalePrice.add(oi.getSalePrice()
						.multiply(new BigDecimal(oi.getDeliveryQuantity())));
			}
			List<OrderDeliverySplit> splits = orderService.findDeliverySplit(order);
			mav.addObject("totalListPrice", totalListPrice.toString());
			mav.addObject("totalSalePrice", totalSalePrice.toString());
			mav.addObject("listInvoice", order.getInvoiceList());
			mav.addObject("splits", splits);
			mav.setViewName("/order/order_delivery");
		} else if ("logistics".equals(viewName)) {
			orderService.fetchLogisticsInfo(order);
			mav.setViewName("/order/order_logistics");

		} else if ("track".equals(viewName)) {
			Code code = codeService.get(Code.ORDER_TRACKING_STATUS);
			orderTrackList = orderService.findTrack(parameters, Short.parseShort(zero), pagination);
			mav.setViewName("/order/ordertrack_list");
			mav.addObject("orderTrackList", orderTrackList);
			mav.addObject("code", code);
		} else if ("customerAccount".equals(viewName)) {
			mav.setViewName("/order/customer_account");
		} else if ("accountLog".equals(viewName)) {
			Map<String, Object> presnetCardParam = new HashMap<String, Object>();
			presnetCardParam.put(ORDER_ID_STR, orderId);
			mav.addObject("presentLogs", presentService.findPresentLogByOrderId(orderId));
			mav.addObject("presentCardDealLogList", presentCardService.findDealLogList(presnetCardParam, null));
			mav.addObject("orderSalePrice", order.getSalePrice());
			mav.addObject("accountDetailList",
					customerService.findAccountDetail(order.getCustomer().getAccount(), order));
			mav.addObject("refundCredentialList", refundService.findByOrder(order.getId()));
			mav.setViewName("/order/account_log");
		} else if ("erpStatus".equals(viewName)) {
			Map<String, Object> presnetCardParam = new HashMap<String, Object>();
			presnetCardParam.put(ORDER_ID_STR, orderId);
			if (Code.DC_8A17.equals(order.getDistributionCenter().getDcDest().getId())) {
				mav.addObject("erpStatus", wmsOrderService.getOrderInfoStatus(order.getId()));
			} else {
				mav.addObject("erpStatus", orderInterfaceService.getStatusByInterface(order));
			}
			mav.setViewName("/order/order_erp_status");
		}

		mav.addObject("isLogistics", order.isAllowedQueryLogistics());
		mav.addObject(ORDERSTR, order);
		mav.addObject(ORDER_ID_STR, order.getId());
		mav.addObject(UNAUDITCOUNT, orderService.getCountOfUnaudited());
		mav.addObject(ORDER_SHIPPER_COUNTS, this.orderShipperInfoService.countNeedAuditShipperInfo());
		mav.addObject("SalePriceByItems", order.getSalePriceByItems().toString());
		return mav;
	}

	@RequestMapping(value = "/deliveryOptionCode", method = RequestMethod.POST)
	public ModelAndView listDeliveryOptionCode() {
		ModelAndView mav = new ModelAndView(ajaxResult);
		Code deliveryOptionCode = codeService.get(deliveryOptionNumber);
		ModelMap map = new ModelMap();
		map.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		map.put(ControllerConstant.MESSAGE_KEY, MESSAGE_SAVE_SUCCESS);
		mav.addObject(CHANGE, true);
		map.put(resultChildren, deliveryOptionCode.getChildren());
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/findDeliveryType", method = RequestMethod.POST)
	public ModelAndView listDeliveryTypee() {
		ModelAndView mav = new ModelAndView(ajaxResult);
		List<DeliveryType> listDeliveryType = deliveryService.findDeliveryType(true);
		ModelMap map = new ModelMap();
		map.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		map.put(ControllerConstant.MESSAGE_KEY, MESSAGE_SAVE_SUCCESS);
		mav.addObject(CHANGE, true);
		map.put(resultChildren, listDeliveryType);
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/findDeliveryTypeByArea/{areaId}", method = RequestMethod.POST)
	public ModelAndView listDeliveryType(@PathVariable("areaId") Long areaId, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView(ajaxResult);
		List<DeliveryInfo> deliveryInfos = deliveryService.findDeliveryInfoForEmployee(areaService.get(areaId), null,
				null, employee);
		List<DeliveryType> listDeliveryType = new ArrayList<DeliveryType>();
		if (CollectionUtils.isNotEmpty(deliveryInfos)) {
			for (DeliveryInfo deliveryInfo : deliveryInfos) {
				listDeliveryType.add(deliveryInfo.getDeliveryType());
			}
		}
		ModelMap map = new ModelMap();
		map.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		map.put(ControllerConstant.MESSAGE_KEY, MESSAGE_SAVE_SUCCESS);
		mav.addObject(CHANGE, true);
		map.put(resultChildren, listDeliveryType);
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/findPaymentList/{id}", method = RequestMethod.POST)
	public ModelAndView listPayment(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView(ajaxResult);
		List<Payment> payments = paymentService.findCanChangePaymentList(paymentService.get(id));
		if (payments != null) {
			for (int i = 0; i < payments.size(); i++) {
				if ("货到付款".equals(payments.get(i).getName().trim())) {
					payments.remove(i);
					break;
				}
			}
			ModelMap map = new ModelMap();
			map.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			map.put(ControllerConstant.MESSAGE_KEY, MESSAGE_SAVE_SUCCESS);
			map.put(resultChildren, payments);
			mav.addAllObjects(map);
			mav.addObject(CHANGE, true);
		} else {
			mav.addObject(CHANGE, false);
		}
		return mav;
	}

	@RequestMapping(value = "/findInvoiceTypeList", method = RequestMethod.POST)
	public ModelAndView listInvoiceType() {
		ModelAndView mav = new ModelAndView(ajaxResult);
		Set<Code> codes = codeService.get(Code.INVOICE_TYPE).getChildren();
		ModelMap map = new ModelMap();
		map.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		map.put(ControllerConstant.MESSAGE_KEY, MESSAGE_SAVE_SUCCESS);
		mav.addObject(CHANGE, true);
		map.put(resultChildren, codes);
		mav.addAllObjects(map);
		return mav;
	}

	/**
	 * 查询需要审核的订单列表
	 */
	@RequestMapping(value = "/unAuditOrders", method = RequestMethod.GET)
	public ModelAndView findNeedAudit(@MyInject Pagination pagination) {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("processStatus", new Long[] { Code.ORDER_PROCESS_STATUS_NEW });
		parameters.put("payType", new Long[] { Code.ORDER_PAY_TYPE_FIRST_DELIVERY });
		short sort = (short) 4;
		List<Order> unAuditOrders = orderService.find(parameters, sort, pagination);
		ModelAndView mav = new ModelAndView("/order/auditBatch_list");
		mav.addObject(CURRENTAUDITREMAIN, Long.valueOf(unAuditOrders.size()));
		mav.addObject("unAuditOrders", unAuditOrders);
		mav.addObject("pagination", pagination);
		mav.addObject(UNAUDITCOUNT, orderService.getCountOfUnaudited());
		mav.addObject(ORDER_SHIPPER_COUNTS, this.orderShipperInfoService.countNeedAuditShipperInfo());
		return mav;
	}

	@RequestMapping(value = "/{orderId}/audit", method = RequestMethod.GET)
	public ModelAndView audit(@PathVariable(ORDER_ID_STR) String orderId,
			@RequestParam(value = "flag", defaultValue = "0", required = false) Integer flag,
			@MyInject Employee employee) throws OrderStockException {
		// 放入错误信息
		auditUnpassReason = new AuditUnpassReason();
		Set<String> orderIds = new HashSet<String>();
		orderIds.add(orderId);
		HashMap<String, String> reason = new HashMap<String, String>();
		reason.put(orderId, "");
		auditUnpassReason.setOrderIds(orderIds);
		auditUnpassReason.setReasons(reason);
		boolean isAudit = false;
		short currentAuditRemain = 0;
		short passAuditCount = 0;
		try {
			isAudit = auditOrder(orderId, employee);
		} catch (OrderStatusException e) {
			createReason(auditUnpassReason, orderId, " 订单不处于已提交状态或者处于已提交状态但不是先货后款类型 ");
			isAudit = false;
		}
		if (flag == 1) {
			ModelAndView mav = new ModelAndView(SUCCESS_VIEW);
			ModelMap map = new ModelMap();
			if (isAudit) {
				map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
			} else {
				map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			}
			mav.addAllObjects(map);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/order/audit_result");
			ModelMap modelMap = new ModelMap();
			modelMap.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
			modelMap.put(PREAUDITREMAIN, 1);
			if (isAudit) {
				currentAuditRemain = AUDIT_RESULT_FAIL;
				passAuditCount = AUDIT_RESULT_SUCCESS;
			} else {
				currentAuditRemain = AUDIT_RESULT_SUCCESS;
				passAuditCount = AUDIT_RESULT_FAIL;
			}
			modelMap.put(CURRENTAUDITREMAIN, currentAuditRemain);
			modelMap.put(PASSAUDIT, passAuditCount);
			String r = auditUnpassReason.getReasons().get(orderId);
			if (StringUtils.isNotBlank(r)) {
				String finalReason = orderId + ":" + r;
				modelMap.put("finalReason", finalReason);
			}

			mav.addAllObjects(modelMap);
			return mav;
		}
	}

	@RequestMapping(value = "/{orderId}/nullify", method = RequestMethod.GET)
	public ModelAndView nullify(@PathVariable(ORDER_ID_STR) String orderId, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView(SUCCESS_VIEW);
		ModelMap map = new ModelMap();
		if (!StringUtils.isBlank(orderId)) {
			Order order = orderService.get(orderId);
			try {
				orderService.nullify(order, employee);
				map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
			} catch (OrderStatusException e) {
				LOG.error(e.getBusinessObject().toString() + "can not be nullify!", e);
				map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			}
		} else {
			map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
		}
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/{orderId}/erpIntercept", method = RequestMethod.GET)
	public ModelAndView erpIntercept(@PathVariable(ORDER_ID_STR) String orderId, @MyInject Employee employee) {
		ModelAndView modelAndView = new ModelAndView(SUCCESS_VIEW);
		if (!StringUtils.isBlank(orderId)) {
			Order order = orderService.get(orderId);
			try {
				erpIntercept(order, employee);
				modelAndView.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
			} catch (Exception e) {
				modelAndView.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			}
		} else {
			modelAndView.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
		}
		return modelAndView;
	}

	/**
	 * 拦截订单
	 * 
	 * @param order
	 * @param employee
	 * @throws RemoteBusinessException
	 */
	private void erpIntercept(Order order, Employee employee) throws OrderStatusException, RemoteBusinessException {
		if (order == null) {
			throw new OrderStatusException(order, new Code[] { new Code(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT) });
		}
		if (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT)) {
			return;
		}
		if (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_PICKING)
				&& order.getTransferResult().getId().equals(Code.EC2ERP_STATUS_NEW)) {
			if (Code.DC_8A17.equals(order.getDistributionCenter().getDcDest().getId())) {
				wmsOrderBlockService.blockOrder(order.getId());
				order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT), employee);
				orderService.update(order, employee);
			}
		}
	}

	@RequestMapping(value = "/{orderId}/reorder", method = RequestMethod.GET)
	public ModelAndView reorder(@PathVariable(ORDER_ID_STR) String orderId, @MyInject Employee employee) {
		ModelAndView modelAndView = new ModelAndView(SUCCESS_VIEW);
		if (!StringUtils.isBlank(orderId)) {
			Order order = orderService.get(orderId);
			try {
				orderService.extendOrder(order, employee);
				modelAndView.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
			} catch (OrderStatusException e) {
				LOG.error(e.getBusinessObject().toString() + "can not be erpCancel!", e);
				modelAndView.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			}
		} else {
			modelAndView.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/{orderId}/picking", method = RequestMethod.GET)
	public ModelAndView picking(@PathVariable(ORDER_ID_STR) String orderId, @MyInject Employee employee) {
		ModelAndView view = new ModelAndView(SUCCESS_VIEW);
		if (!StringUtils.isBlank(orderId)) {
			try {
				orderService.picking(orderId, employee);
				view.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				view.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			}
		} else {
			view.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
		}
		return view;
	}

	private boolean auditOrder(String orderId, Employee employee) throws OrderStatusException, OrderStockException {
		String customerMobile;
		String customerPhone;
		String consignee;
		String address;
		Area district;
		OrderConsignee orderConsignee;
		Order order = orderService.get(orderId);
		orderConsignee = order.getConsignee();
		customerMobile = orderConsignee.getMobile();
		String phone = "";
		if (orderConsignee.getPhone() != null) {
			customerPhone = orderConsignee.getPhone();
			String[] phones = customerPhone.split(SEPARATOR);

			if (phones.length > 1) {
				phone = phones[1];
			} else {
				phone = customerPhone;
			}
		}
		consignee = orderConsignee.getConsignee();
		address = orderConsignee.getAddress();
		district = orderConsignee.getDistrict();
		if (isChannelOrder(order)) { // 如果是渠道订单
			if ((null != phone || null != customerMobile) && (null != district)) {
				orderService.audit(order, employee);
				return true;
			} else {
				createReason(auditUnpassReason, orderId, " 买家电话为空 ");
				return false;
			}
		} else { // 如果不是渠道订单
			if ((PHONE_LENGTH_1 <= phone.length() && PHONE_LENGTH_2 >= phone.length() || (MOBIL_LENGTH == customerMobile
					.length())) && (consignee != null) && (address != null) && (district != null)) {
				auditUnpassReason.getOrderIds().add(orderId);
				orderService.audit(order, employee);
				return true;
			} else {
				createReason(auditUnpassReason, orderId, " 买家电话位数不正确 ");
				return false;
			}
		}
	}

	@RequestMapping(value = "/{orderId}/pay", method = RequestMethod.GET)
	public ModelAndView payByPaymentCredential(@PathVariable(ORDER_ID_STR) String orderId) {
		Order order = orderService.get(orderId);
		ModelAndView mav = new ModelAndView();
		mav.addObject(ORDERSTR, order);
		List<Payment> listPayment = paymentService.find(true, false);
		List<Payment> newListPayment = new ArrayList<Payment>();
		for (Payment payment : listPayment) {
			if (isSupportThisPayment(order, payment)) {
				newListPayment.add(payment);
			}
		}
		mav.addObject(LISTPAYMENT, newListPayment);
		// 普通订单登记到款
		OrderPayForm orderPayForm = new OrderPayForm();
		mav.addObject("orderPayForm", orderPayForm);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		mav.addObject("today", sdf.format(date));
		mav.addObject("requidPayMoney", order.getRequidPayMoney());
		mav.setViewName("/order/pay");
		return mav;
	}

	private boolean isSupportThisPayment(Order order, Payment payment) {
		Code type = payment.getType();
		return type.getId().equals(Code.PAYMENT_TYPE_CHANNEL) || type.getId().equals(Code.PAYMENT_TYPE_OFFLINE)
				|| type.getId().equals(Code.PAYMENT_TYPE_ONLINE)
				|| (Payment.CASH.equals(payment.getId()) && DeliveryType.DIY.equals(order.getDeliveryType().getId()));
	}

	@RequestMapping(value = "/completedorder/{orderId}", method = RequestMethod.GET)
	public ModelAndView completedOrder(@PathVariable(ORDER_ID_STR) String orderId, @MyInject Employee operator) {
		Order order = orderService.get(orderId);
		ModelAndView mav = new ModelAndView();
		String message = new String();
		orderService.updateForConfirmGotGoods(order, operator);
		message = "确认收货成功!";
		mav.addObject(ControllerConstant.MESSAGE_KEY, message);
		mav.setViewName("/order/success_result");
		return mav;
	}

	@RequestMapping(value = "/getBalanceAndName", method = RequestMethod.GET)
	public ModelAndView getCustomerAndBlance(@RequestParam(ORDER_ID_STR) String id) {
		ModelAndView mav = new ModelAndView("/order/order_pay");
		Order order = orderService.get(id);
		if (order == null) {
			mav.addObject("errors", "订单号不存在");
			return mav;
		}
		String message;
		if (!order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_NEW)
				|| (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_NEW) && !order.getPayType()
						.getId().equals(Code.ORDER_PAY_TYPE_FIRST_PAY))) {
			message = "statefalse!";
		} else {
			message = RESULT_SUCCESS_STRING;
		}
		ModelMap map = new ModelMap();
		map.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		map.put(ControllerConstant.MESSAGE_KEY, message);
		map.put("customer", order.getCustomer().getName());
		map.put("balance", order.getCustomer().getAccount().getBalance());
		map.put("requidPayMoney", order.getRequidPayMoney());
		map.put("paymentId", order.getUnpaidPayment().getPayment().getId());
		mav.addAllObjects(map);
		return mav;
	}

	private List<Payment> filterPayments() {
		List<Payment> listPayment = paymentService.find(true, false);
		List<Payment> newListPayment = new ArrayList<Payment>();
		Code type = null;
		for (Payment payment : listPayment) {
			type = payment.getType();
			if (type.getId().equals(Code.PAYMENT_TYPE_CHANNEL) || type.getId().equals(Code.PAYMENT_TYPE_OFFLINE)
					|| type.getId().equals(Code.PAYMENT_TYPE_ONLINE)) {
				newListPayment.add(payment);
			}
		}
		return newListPayment;
	}

	@RequestMapping(value = "/gotoPay", method = RequestMethod.GET)
	public ModelAndView gotoPay() {
		ModelAndView mav = new ModelAndView();
		List<Payment> newListPayment = filterPayments();
		mav.addObject(LISTPAYMENT, newListPayment);
		// 普通订单登记到款
		OrderPayForm orderPayForm = new OrderPayForm();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		mav.addObject("today", sdf.format(date));
		mav.addObject("orderPayForm", orderPayForm);
		mav.setViewName("/order/order_pay");
		return mav;
	}

	@RequestMapping(value = "/gotoBatchPay", method = RequestMethod.GET)
	public ModelAndView gotoBatchPay() {
		ModelAndView mav = new ModelAndView("/order/order_batch_pay");
		List<Payment> newListPayment = filterPayments();
		mav.addObject(LISTPAYMENT, newListPayment);
		return mav;
	}

	@RequestMapping(value = "/gotoBatchPayList", method = RequestMethod.POST)
	public ModelAndView batchPay(@RequestParam(value = "file") MultipartFile file) {
		ModelAndView mav = new ModelAndView("/order/order_pay_list");
		Workbook excel = null;
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
			excel = Workbook.getWorkbook(inputStream);

			Sheet sheet = excel.getSheet(0);
			List<ExcelOrderPayListForm> payList = new ArrayList<ExcelOrderPayListForm>(sheet.getRows());
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);
				ExcelOrderPayListForm payForm = new ExcelOrderPayListForm();
				payForm.setOrderId(row[MagicNumber.ZERO].getContents().trim());
				payForm.setPlatform(row[MagicNumber.ONE].getContents().trim());
				payForm.setCredential(row[MagicNumber.TWO].getContents().trim());
				payForm.setMoney(new BigDecimal(row[MagicNumber.THREE].getContents().trim()));
				payForm.setAccount(row[MagicNumber.FOUR].getContents().trim());
				payForm.setPayer(row[MagicNumber.FIVE].getContents().trim());
				payForm.setAddress(row[MagicNumber.SIX].getContents().trim());
				String date = row[MagicNumber.SEVEN].getContents().trim().replaceAll("\\D+", "-");
				if (date.charAt(date.length() - 1) == '-') {
					date = date.substring(0, date.length() - 1);
				}
				payForm.setPayTime(date);
				payForm.setRemark(row[MagicNumber.EIGHT].getContents().trim());
				Order order = orderService.get(payForm.getOrderId());
				if (null != order) {
					payForm.setRequidPayMoney(order.getRequidPayMoney());
				}
				payList.add(payForm);
			}
			mav.addObject("paylist", payList);
			mav.addObject(LISTPAYMENT, filterPayments());
		} catch (IOException e) {
			mav.addObject(ControllerConstant.MESSAGE_KEY, "文件上传错误..");
		} catch (BiffException e) {
			mav.addObject(ControllerConstant.MESSAGE_KEY, "Excel 文件格式错误.请将Excel文件转换成2003..");
		} catch (Exception e) {
			mav.addObject(ControllerConstant.MESSAGE_KEY, "请检查Excel文件内部格式,重试.或 联系管理员");
		} finally {
			if (excel != null) {
				excel.close();
			}
			IOUtils.close(inputStream);
		}
		return mav;
	}

	@RequestMapping(value = "/batchPay", method = RequestMethod.POST)
	public ModelAndView batchPay(OrderBatchPayForm batch, @MyInject Employee employee) throws ReturnOrderException {
		ModelAndView mav = new ModelAndView("/order/order_batch_pay_result");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer errorMsg = new StringBuffer();
		for (ExcelOrderPayListForm pay : batch.getList()) {
			Order o = orderService.get(pay.getOrderId());
			if (o == null) {
				errorMsg.append(String.format("%s订单不存在  <br />", pay.getOrderId()));
				continue;
			} else if (!Code.ORDER_PROCESS_STATUS_NEW.equals(o.getProcessStatus().getId())) {
				errorMsg.append(String.format("%s订单状态不正确 <br />", pay.getOrderId()));
				continue;
			}
			try {
				Order order = orderService.get(pay.getOrderId());
				List<Order> orderList = new ArrayList<Order>();
				orderList.add(order);
				PaymentCredential paymentCredential = new PaymentCredential();
				if (!"".equals(pay.getPayTime()) && pay.getPayTime() != null) {
					paymentCredential.setPayTime(sdf.parse(pay.getPayTime()));
				}
				paymentCredential.setPayment(paymentService.get(batch.getPaymentTypeId()));
				paymentCredential.setCustomer(order.getCustomer());
				paymentCredential.setMoney(pay.getMoney());
				paymentCredential.setOuterId(pay.getCredential());
				paymentCredential.setPayer(pay.getPayer());
				paymentCredential.setRemark(pay.getRemark());
				paymentCredential.setOperator(employee);
				orderService.pay(orderList, paymentCredential, employee);
			} catch (ParseException e) {
				errorMsg.append(String.format("%s订单支付日期格式错误.<br />", pay.getOrderId()));
				continue;
			} catch (OrderStatusException e) {
				errorMsg.append(String.format("%s订单支付状态错误 <br />", pay.getOrderId()));
				continue;
			} catch (OrderStockException e) {
				errorMsg.append(String.format("%s订单支库存异常 %s <br />", pay.getOrderId(), e.getMessage()));
				continue;
			} catch (CustomerAccountException e) {
				errorMsg.append(String.format("%s订单.帐户异常  %s <br />", pay.getOrderId(), e.getMessage()));
				continue;
			} catch (PaymentCredentialException e) {
				errorMsg.append(String.format("%s订单.凭证异常  %s <br />", pay.getOrderId(), e.getMessage()));
				continue;
			}
		}
		mav.addObject(ControllerConstant.MESSAGE_KEY, errorMsg.length() == 0 ? MESSAGE_SUCCESS : errorMsg.toString()
				+ "请对以上订单进行处理");
		return mav;
	}

	@RequestMapping(value = "/{orderId}/pay", method = RequestMethod.POST)
	public ModelAndView payByPaymentCredential(@PathVariable(ORDER_ID_STR) String orderId,
			@Valid OrderPayForm orderPayForm, BindingResult result, @MyInject Employee employee) throws ParseException,
			OrderStatusException, CustomerAccountException, OrderStockException, PaymentCredentialException,
			ReturnOrderException {
		ModelAndView mav = new ModelAndView("/order/result");
		if (!result.hasErrors()) {
			Order order = orderService.get(orderId);
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
			PaymentCredential paymentCredential = new PaymentCredential();
			if (!"".equals(orderPayForm.getPayTime()) && orderPayForm.getPayTime() != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				paymentCredential.setPayTime(format.parse(orderPayForm.getPayTime()));
			}
			paymentCredential.setPayment(paymentService.get(orderPayForm.getPayment()));
			paymentCredential.setCustomer(order.getCustomer());
			paymentCredential.setMoney(NumberUtils.createBigDecimal(orderPayForm.getMoney()));
			paymentCredential.setOuterId(orderPayForm.getOuterId());
			paymentCredential.setPayer(orderPayForm.getPayer());
			paymentCredential.setRemark(orderPayForm.getRemark());
			paymentCredential.setOperator(employee);
			orderService.pay(orderList, paymentCredential, employee);
			mav.addObject(ORDER_ID_STR, orderId);
			mav.addObject("money", orderPayForm.getMoney());
		} else {
			List<Payment> listPayment = paymentService.find(Code.PAYMENT_TYPE_ONLINE);
			List<Payment> newListPayment = new ArrayList<Payment>();
			for (Payment payment : listPayment) {
				if (payment.isAvailable()) {
					newListPayment.add(payment);
				}
			}
			mav.addObject(LISTPAYMENT, newListPayment);
			mav.addObject("orderPayForm", orderPayForm);
			mav.setViewName("/order/order_pay");
		}
		return mav;
	}

	/**
	 * 子渠道
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/channel/{id}", method = RequestMethod.GET)
	public ModelAndView viewChannel(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("/order/channel");
		List<Channel> channels = channelService.findChildren(id);
		mav.addObject("channel", channels);
		return mav;
	}

	/**
	 * 跳转到订单跟踪查询页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/gotrack", method = RequestMethod.GET)
	public ModelAndView viewTrack() {
		Code code = codeService.get(Code.ORDER_TRACKING_STATUS);
		ModelAndView mav = new ModelAndView("/order/order_track");
		mav.addObject("types", code);
		mav.addObject(UNAUDITCOUNT, orderService.getCountOfUnaudited());
		mav.addObject(ORDER_SHIPPER_COUNTS, this.orderShipperInfoService.countNeedAuditShipperInfo());
		return mav;
	}

	/**
	 * 订单跟踪查询页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/track", method = RequestMethod.POST)
	public ModelAndView viewOrderTrack(@Valid OrderQueryTrackForm orderQueryTrackForm, BindingResult result,
			@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/order/order_track_table");
		if (!result.hasErrors()) {
			// 构建map
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(ORDER_ID_STR, orderQueryTrackForm.getOrderId());
			parameters.put("employee", orderQueryTrackForm.getEmployee());
			parameters.put("name", orderQueryTrackForm.getName());
			parameters.put("type", orderQueryTrackForm.getTypes());
			try {
				if (orderQueryTrackForm.getStartTrackDate() != null) {
					parameters.put("startTrackDate",
							DateUtils.getEarlyInTheDay(orderQueryTrackForm.getStartTrackDate()));
				}
				if (orderQueryTrackForm.getEndTrackDate() != null) {
					parameters.put("endTrackDate", DateUtils.getLateInTheDay(orderQueryTrackForm.getEndTrackDate()));
				}
				if (orderQueryTrackForm.getStartOrderDate() != null) {
					parameters.put("startOrderDate",
							DateUtils.getEarlyInTheDay(orderQueryTrackForm.getStartOrderDate()));
				}
				if (orderQueryTrackForm.getEndOrderDate() != null) {
					parameters.put("endOrderDate", DateUtils.getLateInTheDay(orderQueryTrackForm.getEndOrderDate()));
				}
				if (orderQueryTrackForm.getStartDeliveryDate() != null) {
					parameters.put("startDeliveryDate2",
							DateUtils.getEarlyInTheDay(orderQueryTrackForm.getStartDeliveryDate()));
				}
				if (orderQueryTrackForm.getEndDeliveryDate() != null) {
					parameters.put("endDeliveryDate",
							DateUtils.getLateInTheDay(orderQueryTrackForm.getEndDeliveryDate()));
				}
			} catch (ParseException pe) {
				return mav;
			}
			List<OrderTrack> orderTracks = orderService.findTrack(parameters, (short) 0, pagination);
			mav.addObject("orderTracks", orderTracks);
		}
		mav.addObject("types", codeService.get(Code.ORDER_TRACKING_STATUS));
		mav.addObject("pagination", pagination);
		return mav;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 判断是否为渠道订单
	 * 
	 * @param order
	 * @return
	 */
	private boolean isChannelOrder(Order order) {
		Long id = order.getChannel().getParent().getId();
		return CHANNEL_FENXIAO.compareTo(id) == 0;
	}

	/**
	 * 处理ajax请求
	 * 
	 * @param orderIds
	 * @param employee
	 * @return
	 * @throws OrderStatusException
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public ModelAndView audit(@RequestParam("item") String orderIds, @MyInject Employee employee) {
		// 放入错误信息
		StringBuilder sb = new StringBuilder();
		auditUnpassReason = new AuditUnpassReason();
		Set<String> orderIdList = new HashSet<String>();
		HashMap<String, String> reasons = new HashMap<String, String>();
		auditUnpassReason.setReasons(reasons);
		auditUnpassReason.setOrderIds(orderIdList);
		String[] ids = orderIds.trim().split(INDEX);
		long orderSize = 0L;
		long passCount = 0L;
		// 循环验证审核订单
		if (null != orderIds) {
			for (int i = 0; i < ids.length; i++) {
				try {
					if (auditOrder(ids[i], employee)) {
						passCount++;
					}
				} catch (Exception e) {
					createReason(auditUnpassReason, ids, i, " 关联数据出错 ");
					continue;
				}
			}
			orderSize = ids.length;
		}
		ModelAndView mav = new ModelAndView("/order/audit_result");
		ModelMap modelMap = new ModelMap();
		modelMap.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
		modelMap.put(PREAUDITREMAIN, orderSize);
		modelMap.put(CURRENTAUDITREMAIN, orderSize - passCount);
		modelMap.put(PASSAUDIT, passCount);
		for (String id : orderIdList) {
			String r = auditUnpassReason.getReasons().get(id);
			if (StringUtils.isNotBlank(r)) {
				sb.append(id + ":" + r + "<br/>");
			}
		}
		if (StringUtils.isNotBlank(sb.toString())) {
			mav.addObject("finalReason", sb.toString());
		}
		mav.addAllObjects(modelMap);
		return mav;
	}

	private void createReason(AuditUnpassReason auditUnpassReason, String[] ids, int i, String message) {
		String reason2 = auditUnpassReason.getReasons().get(ids[i]);
		if (reason2 == null) {
			reason2 = message;
		} else {
			reason2 += message;
		}

		auditUnpassReason.getOrderIds().add(ids[i]);
		auditUnpassReason.getReasons().put(ids[i], reason2);
	}

	private void createReason(AuditUnpassReason auditUnpassReason, String orderId, String message) {
		String reason2 = auditUnpassReason.getReasons().get(orderId);
		if (reason2 == null) {
			reason2 = message;
		} else {
			reason2 += message;
		}

		auditUnpassReason.getOrderIds().add(orderId);
		auditUnpassReason.getReasons().put(orderId, reason2);
	}

	@RequestMapping(value = "/orderAddProduct", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam("file") MultipartFile file) {
		InputStream inputStream;
		org.apache.poi.ss.usermodel.Workbook excel;
		// try {
		// inputStream = file.getInputStream();
		// excel = new XSSFWorkbook(inputStream);
		// } catch (Exception e1) {
		// throw new RuntimeException("excel无法读取");
		// }
		try {
			inputStream = file.getInputStream();
			excel = new XSSFWorkbook(inputStream);
		} catch (Exception ex) {
			LOG.info("EXCEL version lower than 2007");
			try {
				inputStream = file.getInputStream();
				excel = new HSSFWorkbook(inputStream);
			} catch (Exception e) {
				throw new RuntimeException("excel无法读取");
			}
		}
		org.apache.poi.ss.usermodel.Sheet sheet = excel.getSheetAt(0);
		List<ProductSaleForm> list = new ArrayList<ProductSaleForm>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		Long supplyType = null;
		try {
			Row row = sheet.getRow(1);
			org.apache.poi.ss.usermodel.Cell cell = row.getCell(4);
			supplyType = Math.round(cell.getNumericCellValue());
		} catch (Exception e) {
			throw new RuntimeException("无法获取供应类型");
		}
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			Row row = sheet.getRow(i);
			String outerId = Math.round(row.getCell(0).getNumericCellValue()) + "";
			Long id = Math.round(row.getCell(1).getNumericCellValue());
			double discount = row.getCell(2).getNumericCellValue();
			String num = Math.round(row.getCell(3).getNumericCellValue()) + "";
			if ("".equals(id) && "".equals(outerId)) {
				break;
			}
			ProductSale ps = new ProductSale();
			if (id.longValue() != 0) {
				ps = productService.getProductSale(id);
			} else if (!"".equals(outerId)) {
				parameters.put("outerId", outerId);
				List<ProductSale> productSales = productService.findProductSale(parameters, null);
				ps = productSales.isEmpty() ? null : productSales.get(0);
			}
			ProductSaleForm productSaleForm = new ProductSaleForm();
			if (ps != null) {
				ps.setSalePrice(BigDecimalUtils.format(ps.getListPrice().multiply(new BigDecimal(discount))));
				Set<ProductSaleStockVo> productSaleStocks = ps.getProductSaleStockVos();
				if (org.apache.commons.collections.CollectionUtils.isNotEmpty(productSaleStocks)) {
					for (ProductSaleStockVo productSaleStock : productSaleStocks) {
						ProductSaleStock stock = new ProductSaleStock();
						stock.setId(productSaleStock.getId());
						stock.getDc().setId(productSaleStock.getDc());
						stock.getProductSale().setId(productSaleStock.getProductSale());
						stock.setIncorrect(productSaleStock.getIncorrect());
						stock.setSales(productSaleStock.getSales());
						stock.setStock(productSaleStock.getStock());
						stock.setVirtual(productSaleStock.getVirtual());
						if (Code.ORDER_SALE_TYPE_RAPID.equals(supplyType)) {
							if (Code.DC_8A19.equals(productSaleStock.getDc())) {
								productSaleForm.getProductSaleStocks().add(stock);
								break;
							} else {
								continue;
							}
						} else {
							if (!Code.DC_8A19.equals(productSaleStock.getDc())) {
								productSaleForm.getProductSaleStocks().add(stock);
							}
						}
					}
				}
			}
			productSaleForm.setDiscount(discount + "");
			productSaleForm.setNum(num);
			productSaleForm.setProductSale(ps);
			list.add(productSaleForm);
		}
		ModelAndView mav = new ModelAndView("/order/add_products");
		mav.addObject("list", list);
		return mav;
	}

	@RequestMapping(value = "/delivery", method = RequestMethod.POST)
	public ModelAndView delivery(HttpServletRequest request, @MyInject Employee operator) throws OrderStatusException,
			OrderDeliveryException, CustomerAccountException, PresentCardException, PresentException,
			ReturnOrderException {
		String orderId = request.getParameter(ORDER_ID_STR);
		String deliveryCompanyId = request.getParameter("deliveryCompanyId");
		String proIds = request.getParameter("proIds");
		String proQuantities = request.getParameter("proQuantities");
		String orderDeliveryCode = request.getParameter("orderDeliveryCode");
		// 得到商品id数组
		String[] productIds = null;
		if (null != proIds) {
			productIds = proIds.trim().split(INDEX);
		}
		// 得到商品数量数组
		String[] productQuantities = null;
		if (null != proQuantities) {
			productQuantities = proQuantities.trim().split(INDEX);
		}
		// 得到订单
		Order order = orderService.get(orderId);
		// 得到订单商品项
		Set<OrderItem> orderItems = order.getItemList();
		for (OrderItem orderItem : orderItems) {
			// 得到商品
			Product product = orderItem.getProductSale().getProduct();
			String proId = product.getId().toString();
			// 商品数量和id列表大小一样 表示正常
			if (productIds.length == productQuantities.length) {
				for (int i = 0; i < productIds.length; i++) {
					String proEditId = productIds[i];
					// 找到对应的商品,设置发货数量
					if (proId.equals(proEditId)) {
						orderItem.setDeliveryQuantity(Integer.parseInt(productQuantities[i]));
					}
				}
			}
		}
		DeliveryCompany deliveryCompany = deliveryService.getDeliveryCompany(Long.valueOf(deliveryCompanyId));
		// 调用发货方法
		orderService.delivery(order, deliveryCompany, orderDeliveryCode, operator);
		ModelAndView mav = new ModelAndView("/order/delivery");
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
		mav.addAllObjects(modelMap);
		return mav;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView gotoNew() {
		// 发货时间选择
		Code codeDeliveryTime = codeService.get(Code.DELIVERY_OPTION);
		// 发票类型
		Code codeInvoiceType = codeService.get(Code.INVOICE_TYPE);
		// 发票抬头类型
		Code codeInvoiceTitleType = codeService.get(Code.INVOICE_TITLE_TYPE);
		// 渠道
		List<Channel> channels = channelService.findChildren(Channel.ROOT);
		ModelAndView mav = new ModelAndView("/order/_new");
		mav.addObject("codeDeliveryTime", codeDeliveryTime);
		mav.addObject("codeInvoiceType", codeInvoiceType);
		mav.addObject("codeInvoiceTitleType", codeInvoiceTitleType);
		mav.addObject("channels", channels);
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<Code> list = new ArrayList<Code>();
		// 已生成
		list.add(new Code(Code.SHOP_STATE_CREATE));
		// 已激活
		list.add(new Code(Code.SHOP_STATE_PASS));
		// 已搜索引擎屏蔽
		list.add(new Code(Code.SHOP_STATE_SEARCH_FAIL));
		// 完全冻结
		list.add(new Code(Code.SHOP_STATE_FAIL));
		parameters.put("state", list);
		List<Shop> listShop = shopService.find(parameters, null);
		mav.addObject("listShop", listShop);
		mav.addObject(UNAUDITCOUNT, orderService.getCountOfUnaudited());
		mav.addObject(ORDER_SHIPPER_COUNTS, this.orderShipperInfoService.countNeedAuditShipperInfo());
		return mav;
	}

	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public ModelAndView area(@RequestParam("id") Long id, @MyInject Employee employee,
			@RequestParam("free") String free, @RequestParam("areaId") String areaId,
			@RequestParam("deliveryTypeId") Long deliveryTypeId, @RequestParam("address") String address)
			throws UnsupportedEncodingException {
		// 获取地区
		Area area = areaService.get(id);
		ModelAndView mav = new ModelAndView("/order/new");
		// address = new String(address.getBytes("ISO-8859-1"), "UTF-8");
		// 区域变化时
		if (deliveryTypeId == null) {
			// 获取配送方式列表
			// List<DeliveryInfo> deliveryInfos =
			// deliveryService.findDeliveryInfo(area, null, new
			// String(address.getBytes("ISO-8859-1"), "UTF-8"));

			List<DeliveryInfo> deliveryInfos = deliveryService.findDeliveryInfoForEmployee(area, null, address,
					employee);
			// 返回
			ModelMap modelMap = new ModelMap();
			modelMap.put("deliveryType", deliveryInfos);
			modelMap.put("isSupportcod", area.isSupportCod());
			mav.addAllObjects(modelMap);
		} else {
			// 配送方式
			DeliveryType deliveryType = deliveryService.getDeliveryType(deliveryTypeId);
			List<Payment> payments = paymentService.find(area, deliveryType);
			ModelMap modelMap = new ModelMap();
			modelMap.put("payment", payments);
			mav.addAllObjects(modelMap);
			if (!"-1".equals(areaId)) {
				if (StringUtils.isBlank(free)) {
					mav.addObject(
							"free",
							orderDeliveryInfo.isAreaSupportForEmployee(areaService.get(Long.valueOf(areaId)),
									deliveryTypeId, address, employee).getDeliveryFee(BigDecimal.ZERO));
				} else {
					mav.addObject(
							"free",
							orderDeliveryInfo.isAreaSupportForEmployee(areaService.get(Long.valueOf(areaId)),
									deliveryTypeId, address, employee).getDeliveryFee(new BigDecimal(free)));
				}
			}
		}
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createOrder(@Valid OrderNewForm orderNewForm, @MyInject Employee employee)
			throws CustomerAccountException, OrderException, PresentException, PresentCardException, ProductException,
			EmployeeBussinessException {
		ModelAndView mav = new ModelAndView("");
		DeliveryInfo deliveryInfo = orderDeliveryInfo.isAreaSupportForEmployee(areaService.get(orderNewForm.getTown()),
				orderNewForm.getDeliveryTypeId(), orderNewForm.getAddress(), employee);
		if (deliveryInfo == null) {
			throw new EmployeeBussinessException("null", "您的账号没有提交该种配送方式订单的权限！");
		}

		// new Order
		Customer customer = null;
		Order order = new Order();

		// 支付信息
		Payment payment = null;
		Set<OrderPayment> orderPayments = new LinkedHashSet<OrderPayment>();
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setCreateTime(new Date());
		orderPayment.setOrder(order);

		// 设置是否查检库存
		order.setCheckStock(orderNewForm.isCheckStock());
		order.setTransferResult(new Code(orderNewForm.getTransferResultId()));
		/**
		 * 如果手动指定了DC，则创建一个物流 transferResultId == -1：自动分配DC
		 */
		if (orderNewForm.getDc().longValue() != -1L) {
			OrderDistributionCenter distributionCenter = new OrderDistributionCenter();
			distributionCenter.setOrder(order);
			order.setDistributionCenter(distributionCenter);
			distributionCenter.setDcOriginal(new Code(orderNewForm.getDc()));
			distributionCenter.setDcDest(new Code(orderNewForm.getDc()));
			// 物流系统中的收货人（客户代码）
			distributionCenter.setRemark(orderNewForm.getDccustomer());
		}
		// 设置订单项
		order.setItemList(new LinkedHashSet<OrderItem>());
		Long[] productSale = orderNewForm.getProductSale();
		if (productSale == null) {
			throw new ProductException(null, "订单中商品不能为空");
		}
		BigDecimal[] listPrice = orderNewForm.getListPrice();
		BigDecimal[] salePrice = orderNewForm.getSalePrice();
		int[] purchaseQuantity = orderNewForm.getPurchaseQuantity();
		Set<OrderItem> item = order.getItemList();
		Shop shop = productService.getProductSale(productSale[0]).getShop();
		for (int i = 0; i < productSale.length; i++) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setPurchaseQuantity(purchaseQuantity[i]);
			orderItem.setListPrice(listPrice[i]);
			orderItem.setSalePrice(salePrice[i]);
			orderItem.setProductSale(productService.getProductSale(productSale[i]));
			orderItem.setShop(shop);
			item.add(orderItem);
		}
		// 设置卖家
		order.setShop(shop);
		// 设置总实洋和码洋
		order.setListPrice(orderNewForm.getAllListPrice());
		order.setSalePrice(orderNewForm.getAllSalePrice());
		// 设置下单人
		order.setCreator(employee);
		// ****发货信息******
		// 设置收货信息
		OrderConsignee consignee = new OrderConsignee();
		order.setConsignee(consignee);
		consignee.setOrder(order);
		// 设置客户账号
		customer = customerService.getByName(orderNewForm.getCustomerName());
		order.setCustomer(customer);
		// 电子邮件
		consignee.setEmail(orderNewForm.getEmail());
		// 邮编
		consignee.setZipCode(orderNewForm.getZipcode());
		// 设置配送方式
		order.setDeliveryType(deliveryService.getDeliveryType(orderNewForm.getDeliveryTypeId()));
		// 设置运费
		order.setDeliveryFee(orderNewForm.getDeliveryfee());
		order.setSplit(true);
		// 支付方式编号
		payment = paymentService.get(orderNewForm.getPaymentType());
		orderPayment.setPayment(payment);
		// 设置渠道
		order.setChannel(channelService.get(orderNewForm.getChannel()));
		// 收货人
		consignee.setConsignee(orderNewForm.getConsignee());
		// 电话
		consignee.setPhone(orderNewForm.getPhone());
		// 手机
		consignee.setMobile(orderNewForm.getMobile());
		// 备注
		consignee.setRemark(orderNewForm.getRemark());
		// 国家，省，市，区
		Area area = areaService.get(orderNewForm.getTown());
		consignee.setCountry(area.getParent().getParent().getParent().getParent().getParent());
		consignee.setProvince(area.getParent().getParent().getParent());
		consignee.setCity(area.getParent().getParent());
		consignee.setDistrict(area.getParent());
		consignee.setTown(area);
		// 地址
		consignee.setAddress(orderNewForm.getAddress());
		// ******配送信息******
		// 送货时间
		consignee.setDeliveryOption(codeService.get(orderNewForm.getDeliverytime()));
		// *****来源及支付信息*****;
		// 总实洋
		BigDecimal allSalePrice = new BigDecimal(0);
		for (OrderItem orderItem : order.getItemList()) {
			allSalePrice = allSalePrice.add(orderItem.getSalePrice().multiply(
					new BigDecimal(orderItem.getPurchaseQuantity())));
		}
		BigDecimal allMoney = allSalePrice.add(order.getDeliveryFee());
		if (payment.getId().equals(Payment.CHANNEL)) {
			PaymentCredential credential = new PaymentCredential();
			credential.setCustomer(customer);
			credential.setMoney(allMoney);
			credential.setOperator(employee);
			credential.setOrderPaymentList(orderPayments);
			credential.setOuterId((employee.getId() + String.valueOf(System.currentTimeMillis())));
			credential.setPayer(employee.getName());
			credential.setPayment(payment);
			credential.setPayTime(new Date());
			orderPayment.setCredential(credential);
			orderPayment.setPay(true);
		}
		// 货到付款过滤
		if (Payment.COD.equals(payment.getId())) {
			this.isFilter(order);
		}
		orderPayment.setPayMoney(allMoney);
		// 加入list
		orderPayments.add(orderPayment);
		// 加入order
		order.setPaymentList(orderPayments);
		order.setOuterId(orderNewForm.getOuterOrderId());
		// ****配送信息****
		// 是否需要发票
		consignee.setNeedInvoice(orderNewForm.isInvoice());
		// 发票抬头
		if (orderNewForm.isInvoice()) {
			if (orderNewForm.getInvoiceTitle().intValue() == Code.INVOICE_TITLE_TYPE_COMPANY) {
				consignee.setInvoiceTitle(orderNewForm.getCompanyName());
			} else if (orderNewForm.getInvoiceTitle().intValue() == Code.INVOICE_TITLE_TYPE_PERSONAL) {
				consignee.setInvoiceTitle(consignee.getConsignee());
			}
		}
		// 发票抬头类型
		if (orderNewForm.isInvoice()) {
			consignee.setInvoiceTitleType(codeService.get(orderNewForm.getInvoiceTitle()));
			OrderInvoice invoice = order.getInvoice(order.isExistMerchandiseItem() ? MERCHANDISE : BOOK, new Code(
					Code.ORDER_INVOICE_MODE_NORMAL), orderNewForm.getAllSalePrice());
			invoice.setTransferred(true);
			invoice.setOperator(employee);
			order.addInvoice(invoice);
		}
		if (!order.isCheckStock()) {
			order.getConsignee().setRemark(order.getConsignee().getRemark() + ".该订单不查检库存.");
		}
		// 设置订单销售类型
		if (null != orderNewForm.getOrderSaleType()) {
			order.setSupplyType(new Code(orderNewForm.getOrderSaleType()));
		}
		// 下单
		orderService.create(order);
		Set<Order> orderlist = order.getParentOrder().getOrderList();
		if (!CollectionUtils.isEmpty(orderlist) && orderlist.size() > 1) {
			mav.setViewName("/order/order_split_list");
			mav.addObject("orderList", orderlist);
		} else {
			mav.setViewName("redirect:/order/" + orderlist.iterator().next().getId());
		}
		return mav;
	}

	public void isFilter(Order order) throws OrderInitException {
		Set<OrderItem> item = order.getItemList();
		Map<ProductSale, Integer> productMap = new HashMap<ProductSale, Integer>();
		for (OrderItem orderItem : item) {
			productMap.put(orderItem.getProductSale(), orderItem.getPurchaseQuantity());
		}
		boolean isFilter = paymentService.isFilter(productMap);
		if (isFilter) {
			throw new OrderInitException(order, "订单超过指定码洋,商品册数超过指定阀值,不支持货到付款.具体咨询技术中心");
		}
	}

	/**
	 * 订单退换货
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/{orderId}/returnorder", method = RequestMethod.GET)
	public ModelAndView returnorder(@PathVariable(ORDER_ID_STR) String orderId) {
		// 获取订单
		Order order = orderService.get(orderId);
		// 获取退换货列表
		Set<ReturnOrder> returnOrders = order.getReturnOrderList();
		ModelAndView mav = new ModelAndView("/order/order_returnorder_tab");
		mav.addObject("returnOrders", returnOrders);
		mav.addObject(ORDER_ID_STR, orderId);
		return mav;
	}

	/*
	 * 判断区域是否存在某种配送方式 isAreaSupp(Area, Long,
	 * String)已迁至com.winxuan.ec.support.order.OrderDliveryInfo.java
	 */

	/**
	 * 暂存款支付页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/advanceaccountPayment")
	public ModelAndView advanceaccountPayment() {
		ModelAndView mav = new ModelAndView("/order/advanceaccountPayment");
		AdvanceaccountPaymentFrom advanceaccountPaymentFrom = new AdvanceaccountPaymentFrom();
		mav.addObject("advanceaccountPaymentFrom", advanceaccountPaymentFrom);
		return mav;
	}

	@RequestMapping(value = "/getOrderInfo", method = RequestMethod.POST)
	public ModelAndView getOrderInfo(@Parameter("id") String id) {
		ModelAndView mav = new ModelAndView("/order/advanceaccount_payment");
		Order order = orderService.get(id);
		ModelMap map = new ModelMap();
		if (order != null) {
			map.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			map.put(ORDER_ID_STR, order.getId());
			map.put("customerName", order.getCustomer().getName());
			map.put("balance", order.getCustomer().getAccount().getBalance());
			map.put("requidPayMoney", order.getRequidPayMoney());
			map.put("orderStatus", order.getProcessStatus().getName());
			map.put("payType", order.getPayType().getName());
			if (order.getRequidPayMoney().compareTo(BigDecimal.ZERO) == 0
					|| order.getCustomer().getAccount().getBalance().compareTo(order.getRequidPayMoney()) < 0
					|| order.getProcessStatus().getId().compareTo(Code.ORDER_PROCESS_STATUS_NEW) != 0
					|| order.getPayType().getId().compareTo(Code.ORDER_PAY_TYPE_FIRST_PAY) != 0) {
				map.put("flag", ControllerConstant.RESULT_INTERNAL_ERROR);
			}
		} else {
			map.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_INTERNAL_ERROR);
		}
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/confirmAdvanceaccountPayment", method = RequestMethod.POST)
	public ModelAndView confirmAdvanceaccountPayment(@Valid AdvanceaccountPaymentFrom advanceaccountPaymentFrom) {
		ModelAndView mav = new ModelAndView("/order/advanceaccountPayment_result");
		Order order = orderService.get(advanceaccountPaymentFrom.getOrderId());
		String message = "失败!";
		if (order != null) {
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
			try {
				mav.addObject(ORDER_ID_STR, order.getId());
				mav.addObject("payMoney", order.getRequidPayMoney());
				orderService.pay(orderList, order.getCustomer().getAccount(), order.getCustomer().getAccount()
						.getBalance(), order.getCreator());
				message = "成功!";
			} catch (OrderStatusException e) {
				LOG.error(e.getMessage(), e);
				message = "失败，订单状态不符合支付条件";
			} catch (OrderStockException e) {
				LOG.error(e.getMessage(), e);
				message = "失败，库存不够";
			} catch (CustomerAccountException e) {
				LOG.error(e.getMessage(), e);
				message = "失败，用户账户异常";
			}
		}
		mav.addObject("message", message);
		return mav;
	}

	@RequestMapping(value = "/outer/{outerId}", method = RequestMethod.GET)
	public ModelAndView isExistOuterId(@PathVariable("outerId") String outerId) {
		ModelAndView mav = new ModelAndView(ajaxResult);
		mav.addObject(ControllerConstant.RESULT_KEY,
				orderService.isExistOuterId(outerId) ? ControllerConstant.RESULT_WARNING
						: ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/expressToAccount", method = RequestMethod.GET)
	public ModelAndView expressToAccount() {
		ModelAndView view = new ModelAndView("/order/express_toaccount");
		view.addObject("express", new ExpressToAcccountForm());
		view.addObject("deliveryTypes", deliveryService.findDeliveryCompany(true));
		if (orderExpressService.canExpressToAccount()) {
			view.addObject(MSG, "正在执行对账操作, 请稍后再试!");
		}
		return view;
	}

	/**
	 * 快递对账
	 * 
	 * @param resp
	 * @param file
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/expressToAccount", method = RequestMethod.POST)
	public ModelAndView expressToAccount(@MyInject Employee employee,
			@RequestParam(value = "file", required = false) MultipartFile file, ExpressToAcccountForm form) {
		ModelAndView view = new ModelAndView("/order/express_toaccount");
		view.addObject("express", form);

		view.addObject("deliveryTypes", deliveryService.findDeliveryCompany(true));
		if (file == null || file.isEmpty()) {
			view.addObject(MSG, "请选择文件!");
			return view;
		}
		if (orderExpressService.canExpressToAccount()) {
			view.addObject(MSG, "正在执行对账操作, 请稍后再试!");
			return view;
		}
		try {
			form.canAvailable();
		} catch (ExpressToAccountException e) {
			view.addObject(MSG, e.getMessage());
			return view;
		}

		try {
			Map<String, List<String>> delivery = fetchData(file.getInputStream(), form.getCodeCol() - 1);
			List<String> head = fetchHead(file.getInputStream());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("startDeliveryTime", form.getStartDate());
			param.put("endDeliveryTime", DateUtils.getLateInTheDay(form.getEndDate()));
			param.put("deliveryCompany", form.getDelivery());

			orderExpressService.expressToAccount(employee, delivery, param, head, file.getOriginalFilename());
		} catch (Exception e) {
			view.addObject(MSG, e.getMessage());
			return view;
		}
		view.addObject(MSG, "文件解析成功, 正在进行后台处理, 过段时间请到\"报表管理->附件管理\"查询/下载处理结果!");
		return view;
	}

	/**
	 * 提取数据
	 * 
	 * @param is
	 * @param col
	 * @return
	 * @throws IOException
	 */
	private Map<String, List<String>> fetchData(InputStream is, int col) throws IOException {
		if (col < 0) {
			col = 0;
		}
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		Map<String, List<String>> delivery = new HashMap<String, List<String>>();
		for (int i = 0; i < rows; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<String> item = new ArrayList<String>();
			for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
				item.add(fetchVal(row.getCell(j)));
			}
			// 过滤第一行的头信息
			if (i == 0) {
				continue;
			}
			if (row.getPhysicalNumberOfCells() > col) {
				String deliverycode = fetchVal(row.getCell(col));
				delivery.put(deliverycode, item);
			}
		}
		return delivery;
	}

	/**
	 * 提取头信息
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private List<String> fetchHead(InputStream is) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(0);

		HSSFRow row = sheet.getRow(0);
		if (row == null) {
			return new ArrayList<String>();
		}
		List<String> item = new ArrayList<String>();
		for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
			item.add(fetchVal(row.getCell(j)));
		}
		return item;
	}

	private String fetchVal(HSSFCell cell) {
		String val = "";
		if (cell == null) {
			return val;
		}
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(cell)) {
			double d = cell.getNumericCellValue();
			Date date = HSSFDateUtil.getJavaDate(d);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			val = sdf.format(date);
		} else {
			cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
			val = cell.toString().trim();
		}
		return val;
	}

	/**
	 * 复制订单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/copyOrder", method = RequestMethod.GET)
	public ModelAndView copyOrder() {
		ModelAndView view = new ModelAndView("/order/order_clone");
		return view;
	}

	/**
	 * 批量制订单
	 * 
	 * @param employee
	 * @param file
	 * @param col
	 * @return
	 */
	@RequestMapping(value = "/batchCopyOrder", method = RequestMethod.POST)
	public ModelAndView batchCopyOrder(@MyInject Employee employee,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "col", defaultValue = "1") Integer col) {
		ModelAndView view = new ModelAndView("/order/order_clone");
		try {
			Map<String, List<String>> orders = fetchData(file.getInputStream(), col - 1);
			List<String> orderIds = new ArrayList<String>();
			orderIds.addAll(orders.keySet());
			StringBuilder message = new StringBuilder();
			if (orderIds.size() > MagicNumber.THOUSAND) {
				view.addObject(RESULT_SUCCESS_STRING, true);
				view.addObject(MSG, "对不起！操作失败！单次最多只能上传300条订单！");
				return view;
			}

			for (String orderId : orderIds) {
				try {
					orderService.copyOrder(employee, orderId);
				} catch (OrderCloneException e) {
					if (!StringUtils.isBlank(message.toString())) {
						message.append(BR);
					}
					message.append(orderId + ":" + e.getMessage());
				}
			}
			view.addObject(RESULT_SUCCESS_STRING, true);
			view.addObject(MSG, message.toString());
		} catch (Exception e) {
			view.addObject(RESULT_SUCCESS_STRING, false);
			view.addObject(MSG, "文件格式不能解析/有错.");
		}
		return view;
	}

	/**
	 * 复制订单
	 * 
	 * @param employee
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/{orderId}/copyOrder", method = RequestMethod.GET)
	public ModelAndView copyOrder(@PathVariable(ORDER_ID_STR) String orderId, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView(SUCCESS_VIEW);
		ModelMap map = new ModelMap();

		if (!StringUtils.isBlank(orderId)) {
			try {
				Order newOrder = orderService.copyOrder(employee, orderId);
				map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
				map.put(MESSAGE, newOrder.getId());
			} catch (OrderCloneException e) {
				LOG.error(e.getMessage(), e);
				map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			}
		} else {
			map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
		}
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/saveExtends", method = RequestMethod.POST)
	public ModelAndView saveExtends(@RequestParam("order") String orderId, @RequestParam("meta") Integer meta,
			@RequestParam("value") String value) {
		ModelAndView mav = new ModelAndView("/app/result");
		Order order = orderService.get(orderId);
		Long channel = order.getChannel().getId();
		if ((channel == 8091L || channel == 8093L || channel == 8085L) && meta == 1L) {// 卓越供货渠道
			OrderPackges entity = orderService.getOrderPackges(orderId);
			if (entity != null) {
				entity.setValue(value);
				// entity.setTs(new Date());
			} else {
				entity = new OrderPackges(orderId, order.getChannel().getId(), new Date(), value);
			}
			orderService.saveOrderPackage(entity);
		}
		List<OrderExtend> orderExtends = order.getOrderExtends();
		for (OrderExtend oe : orderExtends) {
			if (meta.equals(oe.getMeta())) {
				oe.setValue(value);
				orderService.saveOrderExtend(oe);
				return mav;
			}
		}
		OrderExtend oe = new OrderExtend();
		oe.setOrder(order);
		oe.setMeta(meta);
		oe.setValue(value);
		orderExtends.add(oe);
		orderService.saveOrderExtend(oe);
		return mav;
	}

	@RequestMapping(value = "/cancelPresell/{orderId}")
	public ModelAndView cancelPresell(@PathVariable String orderId, @MyInject Employee employee) {
		Order order = orderService.get(orderId);
		ModelAndView mav = new ModelAndView("/order/success_result");
		String message = null;
		if (order == null) {
			message = "订单号不存在!";
		}
		try {
			orderService.cancelPresell(order, employee);
			message = "解除预售成功!";
		} catch (OrderPresellException e) {
			message = e.getMessage();
		}
		mav.addObject(ControllerConstant.MESSAGE_KEY, message);
		return mav;
	}

	@RequestMapping(value = "/changedc", method = RequestMethod.POST)
	public ModelAndView orderDcUpdate(HttpServletRequest request, @MyInject Employee employee) throws BusinessException {
		ModelAndView mav = new ModelAndView("/order/changedc");
		String orderId = request.getParameter("orderId");
		if (StringUtils.isBlank(orderId)) {
			mav.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "订单号不能为空");
			return mav;
		}
		String dcId = request.getParameter("dcId");
		Order order = orderService.get(orderId);
		if (!order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_NEW)
				&& !order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)) {
			mav.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "订单处理状态为\t" + order.getTransferResult().getName()
					+ "\t已不能更改DC");
			return mav;
		}
		Code dc = null;
		try {
			dc = codeService.get(Long.parseLong(dcId));
		} catch (NumberFormatException e) {
			mav.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "dc信息有误");
			return mav;
		}
		if (dc == null || order == null) {
			LOG.debug("订单或DC为空");
			mav.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "订单号或DC不能为空");
			return mav;
		}
		try {
			if (dcService.updateOrderDcOriginal(order, dc, employee)) {
				mav.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
				mav.addObject("dc", dc);
				mav.addObject("order", order);
			} else {
				mav.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			}
		} catch (ProductSaleStockException e) {
			LOG.debug(e.getMessage());
			mav.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			mav.addObject(ControllerConstant.MESSAGE_KEY, dc.getName() + "库位不存在,无法更新库存");
		}
		return mav;
	}

	@RequestMapping(value = "/updatesupplytype", method = RequestMethod.GET)
	public ModelAndView updateSupplyType(@RequestParam("orderId") String orderId, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView("/order/result");
		Order order = orderService.get(orderId);
		order.setSupplyType(new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL));
		orderService.update(order, employee);
		return mav;
	}

	@RequestMapping(value = "/unionPayQuery", method = RequestMethod.GET)
	public ModelAndView unionPayQuery() {
		ModelAndView mav = new ModelAndView("/order/unionpay/query");
		return mav;
	}

	@RequestMapping(value = "/unionPayQuery", method = RequestMethod.POST)
	public ModelAndView unionPayQuery(String orderId) {
		ModelAndView mav = new ModelAndView("/order/unionpay/query");
		OrderBatchPay orderBatchPay = batchPayService.getByOrderId(orderId);
		Map<String, String> resp = new HashMap<String, String>();
		if (orderBatchPay == null) {
			// 未推送交易的订单，返回订单未支付
			resp.put("12", "respCode");
		} else {
			BatchPay batchPay = batchPayService.get(orderBatchPay.getBatchPayId());
			String respString = HttpUtil.query(batchPay);
			HttpUtil.verifyResponse(respString, resp);
			for (Map.Entry<String, String> resps : resp.entrySet()) {
				LOG.info(String.format("%s:%s", resps.getKey(), resps.getValue()));
			}
			mav.addObject("batchPay", batchPay);
		}
		mav.addObject("orderId", orderId);
		mav.addObject("query", resp);
		return mav;
	}

	/**
	 * 查询需要审核承运商的订单列表
	 */
	@RequestMapping(value = "/auditOrderShipper", method = RequestMethod.GET)
	public ModelAndView auditOrderShipper(@MyInject Pagination pagination) {
		List<OrderShipperInfo> orderShipperInfos = this.orderShipperInfoService.find(pagination);
		Map<String, String> errorMessage = Maps.newHashMap();
		List<String> orderIds = Lists.newArrayList();
		for (OrderShipperInfo orderShipperInfo : orderShipperInfos) {
			orderIds.add(orderShipperInfo.getOrderId());
			errorMessage.put(orderShipperInfo.getOrderId(), orderShipperInfo.getErrorMessage());
		}
		List<Order> orders = null;
		if (CollectionUtils.isNotEmpty(orderIds)) {
			Map<String, Object> parameters = Maps.newHashMap();
			parameters.put("orderId", orderIds);
			orders = this.orderService.find(parameters, (short) 4, pagination);
		}
		List<DeliveryCompany> deliveryCompanys = this.deliveryService.findDeliveryCompany(Boolean.TRUE);
		ModelAndView mav = new ModelAndView("/order/audit_shipper_list");
		mav.addObject("pagination", pagination);
		mav.addObject("errorMessage", errorMessage);
		mav.addObject("orders", orders);
		mav.addObject("deliveryCompanys", deliveryCompanys);
		mav.addObject(ORDER_SHIPPER_COUNTS, pagination.getCount());
		mav.addObject(UNAUDITCOUNT, orderService.getCountOfUnaudited());
		return mav;
	}

	@RequestMapping(value = "/{orderId}/auditOrderShipper", method = RequestMethod.GET)
	public ModelAndView auditOrderShipper(@PathVariable(ORDER_ID_STR) String orderId,
			@RequestParam(value = "flag", defaultValue = "0", required = false) Integer flag,
			@RequestParam(required = true) Long deliveryCompanyId, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView(SUCCESS_VIEW);
		ModelMap map = new ModelMap();
		Order order = this.orderService.get(orderId);
		// 原始的承运商信息
		DeliveryCompany company = order.getDeliveryCompany();
		String originalCompanyName = null != company ? company.getCompany() : null;
		DeliveryCompany deliveryCompany = this.deliveryService.getDeliveryCompany(deliveryCompanyId);
		OrderUpdateLog log = new OrderUpdateLog(employee, order, "承运商审核", originalCompanyName,
				null != deliveryCompany ? deliveryCompany.getCompany() : null);
		// 记录日志
		order.addUpdateLog(log);
		order.setDeliveryCompany(deliveryCompany);
		try {
			this.orderShipperInfoService.matchingOrderShipperInfo(order);
			map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_SUCCESS);
			map.put(ControllerConstant.MESSAGE_KEY, "承运商审核成功！");
		} catch (OrderShipperInfoMatchException e) {
			OrderShipperInfo orderShipperInfo = this.orderShipperInfoService.getOrderShipperInfo(order.getId());
			orderShipperInfo.setErrorMessage(e.getMessage());
			orderShipperInfo.setStatus(OrderShipperInfo.ERROR_STATUS);
			this.orderShipperInfoService.update(orderShipperInfo);
			map.put(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			map.put(ControllerConstant.MESSAGE_KEY, "调用DMS报错，错误信息：" + e.getMessage() + "请重新选择！");
		}
		mav.addAllObjects(map);
		return mav;
	}
}