/*
 * @(#)Test.java *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.orderinvoice;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderInvoiceException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.order.OrderInvoiceService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.AcceptHashMap;
import com.winxuan.framework.util.StringUtils;

/**
 * 
 * 
 * @author chenlong
 * @version 1.0,2011-8-3
 */
@Controller
@RequestMapping("/orderinvoice")
public class OrderInvoiceController {
	public static final String ORDER_INVOIEC_FORMNAME = "orderInvoiceCreateForm";
	public static final String TITLE_TYPE = "titleTypes";
	public static final String ORDERIDS = "orderIds";
	public static final int MAXNUMBER = 100;
	public static final String BLANK = "  ";
	public static final String ORDERIDSTR = "orderIdsStr";
	public static final String ORDERIDNAME = "orderId";
	public static final String BOOK = "图书";
	public static final String MERCHANDISE = "百货";
	private static final String MSG = "msg";
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderInvoiceService orderInvoiceService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private CodeService codeService;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView list(OrderInvoiveSearchForm searchForm,
			@MyInject Pagination pagination) {
		ModelAndView modelAndView = new ModelAndView("orderinvoice/list");
		AcceptHashMap<String, Object> param = new AcceptHashMap<String, Object>();
		if (ORDERIDNAME.equals(searchForm.getSearchType())) {
			param.acceptIf(
					"orderIds",
					searchForm.getInformation().split(
							IOUtils.LINE_SEPARATOR_WINDOWS),
					!StringUtils.isNullOrEmpty(searchForm.getInformation()));
		} else if ("invoiceId".equals(searchForm.getSearchType())) {
			param.acceptIf(
					"invoiceIds",
					searchForm.getInformation().split(
							IOUtils.LINE_SEPARATOR_WINDOWS),
					!StringUtils.isNullOrEmpty(searchForm.getInformation()));
		} else if ("outerId".equals(searchForm.getSearchType())) {
			param.acceptIf(
					"outerIds",
					searchForm.getInformation().split(
							IOUtils.LINE_SEPARATOR_WINDOWS),
					!StringUtils.isNullOrEmpty(searchForm.getInformation()));
		} else {
			param.acceptIf("customerName", searchForm.getInformation(),
					!StringUtils.isNullOrEmpty(searchForm.getInformation()));
		}
		param.acceptIf("startDate", searchForm.getStarttime(),
				searchForm.getStarttime() != null);
		param.acceptIf("endDate", searchForm.getEndtime(),
				searchForm.getEndtime() != null);
		String timeType = searchForm.getTime();
		if (!StringUtils.isNullOrEmpty(timeType)) {
			Date startDate = null;
			Date endDate = new Date();
			if ("oneday".equals(timeType)) {
				startDate = DateUtils.addDays(endDate, -1);
			} else if ("oneweek".equals(timeType)) {
				startDate = DateUtils.addWeeks(endDate, -1);
			} else if ("onemonth".equals(timeType)) {
				startDate = DateUtils.addMonths(endDate, -1);
			}
			param.accept("startDate", startDate);
			param.accept("endDate", endDate);
		}
		List<Object[]> list = orderInvoiceService.findOrderInvoice(param,
				pagination);
		List<OrderInvoiceDTO> listInvoice = new ArrayList<OrderInvoiceDTO>();
		List<Order> listOrder = new ArrayList<Order>();
		for (int i = 0; i < list.size(); i++) {
			Object o = list.get(i);
			if (o instanceof Order) {
				Order order = (Order) o;
				if (!listOrder.contains(order)) {
					Set<OrderInvoice> set = order.getInvoiceList();
					if (set.size() != 0) {
						for (OrderInvoice oi : set) {
							OrderInvoiceDTO dto = new OrderInvoiceDTO();
							dto.setOrder(order);
							dto.setOrderInvoice(oi);
							listInvoice.add(dto);
						}
					} else {
						OrderInvoiceDTO dto = new OrderInvoiceDTO();
						dto.setOrder(order);
						dto.setOrderInvoice(null);
						listInvoice.add(dto);
					}
					listOrder.add(order);
				}
				pagination.setPageCount(listInvoice.size());
			} else {
				OrderInvoiceDTO dto = new OrderInvoiceDTO();
				Object[] obj = list.get(i);
				dto.setOrder((Order) obj[0]);
				dto.setOrderInvoice((OrderInvoice) obj[1]);
				listInvoice.add(dto);
			}
		}
		modelAndView.addObject("pagination", pagination);
		modelAndView.addObject("listInvoice", listInvoice);
		if (searchForm.getCondition() != null) {
			List<String> condition = searchForm.getCondition();
			for (int i = 0; i < condition.size(); i++) {
				modelAndView.addObject(condition.get(i), condition.get(i));
			}
		}
		return modelAndView;
	}

	/**
	 * 
	 * @param orderInvoiceCreateForm
	 * @return
	 * @throws ParseException
	 * @throws OrderStatusException
	 * @throws CustomerAccountException
	 * @throws OrderInvoiceException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView createInvoice(
			@Valid OrderInvoiceCreateForm orderInvoiceCreateForm,
			@MyInject Employee operator) throws ParseException,
			OrderStatusException, CustomerAccountException,
			OrderInvoiceException {
		final Long codeId = 15002L;
		Order order = orderService.get(orderInvoiceCreateForm.getOrderId());
		OrderInvoice orderInvoice = new OrderInvoice();

		orderInvoice.setDc(order.getDistributionCenter().getDcDest());// 设置实际出货仓库
		orderInvoice.setContent(order.isExistMerchandiseItem() ? MERCHANDISE
				: BOOK);// 设置发票内容
		orderInvoice.setAddress(orderInvoiceCreateForm.getAddress());// 设置详细地址
		Area city = new Area();
		city.setId(orderInvoiceCreateForm.getCity());
		orderInvoice.setCity(city);// 设置城市
		Area country = new Area();
		country.setId(orderInvoiceCreateForm.getCountry());
		orderInvoice.setCountry(country);// 设置国家
		Area province = new Area();
		province.setId(orderInvoiceCreateForm.getProvince());
		orderInvoice.setProvince(province);// 设置省
		Area district = new Area();
		district.setId(orderInvoiceCreateForm.getDistrict());
		orderInvoice.setDistrict(district);// 设置地区
		Area town = new Area();
		town.setId(orderInvoiceCreateForm.getTown());
		orderInvoice.setTown(town);// 设置乡镇
		orderInvoice.setCreateTime(new Date());// 设置开票时间
		orderInvoice.setEmail(orderInvoiceCreateForm.getEmail());
		orderInvoice.setMobile(orderInvoiceCreateForm.getMobile());
		orderInvoice.setMoney(orderInvoiceService.calcInvoiceMoney(order,
				orderInvoiceCreateForm.getMoney()));
		orderInvoice.setQuantity(orderInvoiceCreateForm.getQuantity());
		orderInvoice.setConsignee(orderInvoiceCreateForm.getConsignee());
		orderInvoice.setTitle(orderInvoiceCreateForm.getTitle());
		orderInvoice.setZipCode(orderInvoiceCreateForm.getZipCode());
		// 发票类型
		Code titleType = new Code();
		titleType.setId(orderInvoiceCreateForm.getTitleType());
		orderInvoice.setTitleType(titleType);

		orderInvoice.setMode(new Code(codeId));// 设置开票方式
		orderInvoice
				.setDeliveryOption(order.getConsignee().getDeliveryOption());
		orderInvoice.setMode(new Code(codeId));// 设置开票方式
		orderInvoice.setDeliveryTime(order.getDeliveryTime());// 发货时间
		orderInvoice
				.setDeliveryOption(order.getConsignee().getDeliveryOption());
		orderInvoice.setOperator(operator);
		if (order.getConsignee().getInvoiceType() != null) {
			orderInvoice.setType(order.getConsignee().getInvoiceType());
		}
		orderInvoice.setOrder(order);
		orderInvoiceService.calcInvoiceMoney(order, orderInvoice.getMoney());
		order.addInvoice(orderInvoice);
		orderInvoiceService.createOrderInvoice(orderInvoice);
		ModelAndView mav = new ModelAndView("/orderinvoice/success");
		mav.addObject(ORDERIDNAME, order.getId());
		mav.addObject("orderInvoiceId", orderInvoice.getId());
		return mav;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * 
	 * description初始化搜索界面
	 * 
	 * @param pagination
	 * @return
	 * 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView initlist() {
		ModelAndView mav = new ModelAndView("orderinvoice/list");
		return mav;
	}

	/**
	 * 跳转到补开发票页面
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "invoice/{id}", method = RequestMethod.GET)
	public ModelAndView newOrderInvoice(@PathVariable("id") String orderId) {
		final Long codeId = 3459L;
		Order order = orderService.get(orderId);
		ModelAndView mav = new ModelAndView("/orderinvoice/_invoice");
		mav.addObject(ORDERIDNAME, orderId);
		OrderInvoiceCreateForm orderInvoiceCreateForm = new OrderInvoiceCreateForm();
		orderInvoiceCreateForm
				.setContent(order.isExistMerchandiseItem() ? MERCHANDISE : BOOK);
		orderInvoiceCreateForm.setCountry(order.getConsignee().getCountry()
				.getId());
		orderInvoiceCreateForm.setProvince(order.getConsignee().getProvince()
				.getId());
		orderInvoiceCreateForm.setCity(order.getConsignee().getCity().getId());
		orderInvoiceCreateForm.setDistrict(order.getConsignee().getDistrict()
				.getId());
		orderInvoiceCreateForm
				.setConsignee(order.getConsignee().getConsignee());
		orderInvoiceCreateForm.setTown(order.getConsignee().getTown().getId());
		orderInvoiceCreateForm.setOrderId(orderId);
		Set<Code> titleTypes = codeService.get(codeId).getChildren();
		mav.addObject(TITLE_TYPE, titleTypes);
		// 默认是order.deliveryQuantity，如果未发货，取order.purchaseQuantity
		if (!order.isDeliveried()) {
			orderInvoiceCreateForm.setQuantity(order.getPurchaseQuantity());
		} else {
			orderInvoiceCreateForm.setQuantity(order.getDeliveryQuantity());
		}
		// 如果手机号码为空 则设置成办公电话
		if (order.getConsignee().getMobile() != null) {
			orderInvoiceCreateForm.setMobile(order.getConsignee().getMobile());
		} else {
			orderInvoiceCreateForm.setMobile(order.getConsignee().getPhone());
		}
		OrderConsignee consignee = order.getConsignee();
		String title = "";
		if (consignee.getInvoiceTitleType() != null) {
			title = consignee.getInvoiceTitleType().getId() == Code.INVOICE_TITLE_TYPE_PERSONAL ? consignee
					.getConsignee() : consignee.getInvoiceTitle();
		}
		Long titleTypeId = consignee.getInvoiceTitleType() == null
				|| consignee.getInvoiceTitleType().getId() == Code.INVOICE_TITLE_TYPE_PERSONAL ? Code.INVOICE_TITLE_TYPE_PERSONAL
				: Code.INVOICE_TITLE_TYPE_COMPANY;
		orderInvoiceCreateForm.setTitle(title);
		orderInvoiceCreateForm.setEmail(order.getConsignee().getEmail());
		orderInvoiceCreateForm.setZipCode(order.getConsignee().getZipCode());
		orderInvoiceCreateForm.setAddress(order.getConsignee().getAddress());
		orderInvoiceCreateForm.setMoney(order.getInvoiceMoney());
		mav.addObject("titleTypeId", titleTypeId);
		mav.addObject(ORDER_INVOIEC_FORMNAME, orderInvoiceCreateForm);
		return mav;
	}

	@RequestMapping(value = "/goSure", method = RequestMethod.POST)
	public ModelAndView confirmOrderInvoice(
			@Valid OrderInvoiceCreateForm orderInvoiceCreateForm,
			BindingResult result) {
		final Long codeId = 3459L;
		ModelAndView mav = new ModelAndView("/orderinvoice/sure_invoice");
		if (result.hasErrors()) {

			Set<Code> titleTypes = codeService.get(codeId).getChildren();
			mav.addObject(TITLE_TYPE, titleTypes);
			mav.addObject("orderId", orderInvoiceCreateForm.getOrderId());
			mav.setViewName("/orderinvoice/_invoice");
			return mav;
		}
		mav.addObject(ORDER_INVOIEC_FORMNAME, orderInvoiceCreateForm);
		String country = areaService.get(orderInvoiceCreateForm.getCountry())
				.getName();
		String province = areaService.get(orderInvoiceCreateForm.getProvince())
				.getName();
		String city = areaService.get(orderInvoiceCreateForm.getCity())
				.getName();
		String district = areaService.get(orderInvoiceCreateForm.getDistrict())
				.getName();
		String town = areaService.get(orderInvoiceCreateForm.getTown())
				.getName();

		mav.addObject("area", country + BLANK + province + BLANK + city + BLANK
				+ district + BLANK + town);
		mav.addObject("titleType",
				codeService.get(orderInvoiceCreateForm.getTitleType())
						.getName());
		return mav;
	}

	@RequestMapping(value = "/orderinvoice_list", method = RequestMethod.GET)
	public ModelAndView listOrderInvoice(@RequestParam("orderid") String orderid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String[] a = new String[1];
		a[0] = orderid;
		parameters.put("orderIds", a);
		Pagination pagination = new Pagination();
		pagination.setPageCount(MAXNUMBER);
		List<Object[]> list = orderInvoiceService.findOrderInvoice(parameters,
				pagination);
		List<OrderInvoiceDTO> listInvoice = new ArrayList<OrderInvoiceDTO>();
		List<Order> listOrder = new ArrayList<Order>();
		for (int i = 0; i < list.size(); i++) {
			Object o = list.get(i);
			if (o instanceof Order) {
				Order order = (Order) o;
				if (!listOrder.contains(order)) {
					Set<OrderInvoice> set = order.getInvoiceList();
					for (OrderInvoice oi : set) {
						OrderInvoiceDTO dto = new OrderInvoiceDTO();
						dto.setOrder(order);
						dto.setOrderInvoice(oi);
						listInvoice.add(dto);
					}
					listOrder.add(order);
				}
				pagination.setPageCount(listInvoice.size());
			} else {
				OrderInvoiceDTO dto = new OrderInvoiceDTO();
				Object[] obj = list.get(i);
				dto.setOrder((Order) obj[0]);
				dto.setOrderInvoice((OrderInvoice) obj[1]);
				listInvoice.add(dto);
			}
		}
		ModelAndView mav = new ModelAndView("/orderinvoice/orderinvoice_list");
		mav.addObject("listInvoice", listInvoice);
		mav.addObject("list", list);
		return mav;
	}

	public Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 用户输入发票订单号，返回对应的发票信息 一个发票订单号对应一条发票信息
	 * 
	 * @param orderInvoiceId
	 */
	@RequestMapping(value = "/updateOrderInvoiceList")
	public ModelAndView listUpdateOrderInvoice(
			OrderInvoiveSearchForm searchForm, @MyInject Pagination pagination) {
		ModelAndView modelAndView = new ModelAndView(
				"orderinvoice/orderinvoice_update");
		modelAndView.addObject("searchForm", searchForm);
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<OrderInvoiceDTO> listInvoice = new ArrayList<OrderInvoiceDTO>();
		parameters.put("invoiceId", searchForm.getInformation());
		List<OrderInvoice> orderInvoiceList = this.orderInvoiceService.find(
				parameters, pagination);
		for (int i = 0; i < orderInvoiceList.size(); i++) {
			OrderInvoice orderInvoice = orderInvoiceList.get(i);
			OrderInvoiceDTO tempDto = new OrderInvoiceDTO();
			tempDto.setOrderInvoice(orderInvoice);
			tempDto.setOrder(orderInvoice.getOrder());
			listInvoice.add(tempDto);
		}
		modelAndView.addObject("pagination", pagination);
		modelAndView.addObject("listInvoice", listInvoice);
		return modelAndView;
	}

	/**
	 * 根据发票订单号，在页面上直接修改发票金额，并保存
	 */
	@RequestMapping(value = "/updateOrderInvoiceMoney")
	public ModelAndView updateOrderInvoiceMoney(@RequestParam("id") String id,
			@RequestParam("money") BigDecimal money, @MyInject Employee employee) {
		ModelAndView modelAndView = new ModelAndView(
				"orderinvoice/orderinvoice_update");
		Long employeeId = employee.getId();
		if ((null != money)) {
			OrderInvoice orderInvoice = this.orderInvoiceService.get(id);
			Date date = new Date();
			orderInvoice.setMoney(money);
			orderInvoice.setCreateTime(date);
			orderInvoice.setOperator(new User(employeeId));
			this.orderInvoiceService.update(orderInvoice);
			modelAndView.addObject(MSG, "succeed");
		} else {
			modelAndView.addObject(MSG, "error");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView goUpload() {
		ModelAndView mav = new ModelAndView("orderinvoice/orderinvoice_upload");
		return mav;
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public ModelAndView upload(@RequestParam("file") MultipartFile file,
			@MyInject Employee operator) {
		ModelAndView mav = new ModelAndView("orderinvoice/orderinvoice_upload");
		List<OrderInvoice> orderInvoices = null;
		try {
			orderInvoices = dealFile(file, operator);
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());
			return mav;
		}
		mav.addObject("orderInvoices", orderInvoices);
		return mav;
	}

	/**
	 * 读取EXCEL
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private List<OrderInvoice> dealFile(MultipartFile file, Employee operator)
			throws Exception {
		List<OrderInvoice> orderInvoices = new ArrayList<OrderInvoice>();
		InputStream inputStream;
		Workbook excel;
		try {
			inputStream = file.getInputStream();
			excel = Workbook.getWorkbook(inputStream);
		} catch (Exception e1) {
			throw new Exception("excel无法读取" + e1.getMessage());
		}
		Sheet sheet = excel.getSheet(0);
		String orderId;
		Order order = null;
		Area area = null;
		Area areaTemp = null;
		List<Area> areas = new ArrayList<Area>();
		for (int i = 1; i < sheet.getRows(); i++) {
			OrderInvoiceCreateForm form = new OrderInvoiceCreateForm();
			orderId = sheet.getCell(0, i).getContents().trim();
			if (StringUtils.isNullOrEmpty(orderId)) {
				throw new Exception("上传的数据订单号不能没有！！");
			}
			order = orderService.get(orderId);
			if (order == null) {
				throw new Exception("订单号为" + orderId + "的订单不存在！");
			}
			form.setOrderId(orderId);
			if (StringUtils.isNullOrEmpty(sheet.getCell(1, i).getContents()
					.trim())) {
				throw new Exception("上传的发票抬头不能没有！！");
			}
			form.setTitle(sheet.getCell(1, i).getContents().trim());
			if (!StringUtils.isNullOrEmpty(sheet.getCell(2, i).getContents()
					.trim())) {
				form.setQuantity(Integer.parseInt(sheet.getCell(2, i)
						.getContents().trim()));
			} else {
				form.setQuantity(order.getDeliveryQuantity());
			}
			if (!StringUtils.isNullOrEmpty(sheet.getCell(3, i).getContents()
					.trim())) {
				form.setMoney(new BigDecimal(sheet.getCell(3, i).getContents()
						.trim()));
			} else {
				form.setMoney(order.getInvoiceMoney());
			}
			if (!StringUtils.isNullOrEmpty(sheet.getCell(4, i).getContents()
					.trim())) {
				form.setConsignee(sheet.getCell(4, i).getContents().trim());
			} else {
				form.setConsignee(order.getConsignee().getConsignee());
			}
			if (!StringUtils.isNullOrEmpty(sheet.getCell(5, i).getContents()
					.trim())) {
				form.setMobile(sheet.getCell(5, i).getContents().trim());
			} else {
				if (!StringUtils
						.isNullOrEmpty(order.getConsignee().getMobile())) {
					form.setMobile(order.getConsignee().getMobile());
				} else {
					form.setMobile(order.getConsignee().getPhone());
				}
			}
			// 区域
			if (!StringUtils.isNullOrEmpty(sheet.getCell(6, i).getContents()
					.trim())) {
				area = areaService
						.get(sheet.getCell(6, i).getContents().trim()).get(0);
				if (area == null) {
					throw new Exception("订单号为" + orderId + "的国家不存在！");
				}
				form.setCountry(area.getId());
			} else {
				form.setCountry(order.getConsignee().getCountry().getId());
				area = order.getConsignee().getCountry();
			}
			if (!StringUtils.isNullOrEmpty(sheet.getCell(7, i).getContents()
					.trim())) {
				areas = areaService.get(sheet.getCell(7, i).getContents()
						.trim());
				for (Area a : areas) {
					if (a.getParent().getParent().getId().equals(area.getId())) {
						areaTemp = a;
					}
				}
				if (areaTemp == null) {
					throw new Exception("订单号为" + orderId + "的省份不存在！");
				} else {
					form.setProvince(areaTemp.getId());
					area = areaTemp;
					areaTemp = null;
				}
			} else {
				form.setProvince(order.getConsignee().getProvince().getId());
				area = order.getConsignee().getProvince();
			}
			if (!StringUtils.isNullOrEmpty(sheet.getCell(8, i).getContents()
					.trim())) {
				areas = areaService.get(sheet.getCell(8, i).getContents()
						.trim());
				for (Area a : areas) {
					if (a.getParent().getId().equals(area.getId())) {
						areaTemp = a;
					}
				}
				if (areaTemp == null) {
					throw new Exception("订单号为" + orderId + "的城市不存在！");
				} else {
					form.setCity(areaTemp.getId());
					area = areaTemp;
					areaTemp = null;
				}
			} else {
				form.setCity(order.getConsignee().getCity().getId());
				area = order.getConsignee().getCity();
			}
			if (!StringUtils.isNullOrEmpty(sheet.getCell(9, i).getContents()
					.trim())) {
				areas = areaService.get(sheet.getCell(9, i).getContents()
						.trim());
				for (Area a : areas) {
					if (a.getParent().getId().equals(area.getId())) {
						areaTemp = a;
					}
				}
				if (areaTemp == null) {
					throw new Exception("订单号为" + orderId + "的区县不存在！");
				} else {
					form.setDistrict(areaTemp.getId());
					area = areaTemp;
					areaTemp = null;
				}
			} else {
				form.setDistrict(order.getConsignee().getDistrict().getId());
				area = order.getConsignee().getDistrict();
			}
			if (!StringUtils.isNullOrEmpty(sheet.getCell(10, i).getContents()
					.trim())) {
				areas = areaService.get(sheet.getCell(10, i).getContents()
						.trim());
				for (Area a : areas) {
					if (a.getParent().getId().equals(area.getId())) {
						areaTemp = a;
					}
				}
				if (areaTemp == null) {
					throw new Exception("订单号为" + orderId + "的乡镇不存在！");
				} else {
					form.setTown(areaTemp.getId());
				}
			} else {
				form.setTown(order.getConsignee().getTown().getId());
			}
			if (!StringUtils.isNullOrEmpty(sheet.getCell(11, i).getContents()
					.trim())) {
				form.setAddress(sheet.getCell(11, i).getContents().trim());
			} else {
				form.setAddress(order.getConsignee().getAddress());
			}
			area = null;
			areaTemp = null;
			orderInvoices.add(createOrderInvoices(order, form, operator));
		}
		return orderInvoices;
	}

	/**
	 * 创建发票信息
	 * 
	 * @param order
	 * @param form
	 * @return
	 * @throws OrderStatusException
	 */
	private OrderInvoice createOrderInvoices(Order order,
			OrderInvoiceCreateForm orderInvoiceCreateForm, Employee operator)
			throws OrderStatusException {
		final Long codeId = 15002L;
		OrderInvoice orderInvoice = new OrderInvoice();
		orderInvoice.setDc(order.getDistributionCenter().getDcDest());// 设置实际出货仓库
		orderInvoice.setContent(order.isExistMerchandiseItem() ? MERCHANDISE
				: BOOK);// 设置发票内容
		orderInvoice.setAddress(orderInvoiceCreateForm.getAddress());// 设置详细地址
		Area city = new Area();
		city.setId(orderInvoiceCreateForm.getCity());
		orderInvoice.setCity(city);// 设置城市
		Area country = new Area();
		country.setId(orderInvoiceCreateForm.getCountry());
		orderInvoice.setCountry(country);// 设置国家
		Area province = new Area();
		province.setId(orderInvoiceCreateForm.getProvince());
		orderInvoice.setProvince(province);// 设置省
		Area district = new Area();
		district.setId(orderInvoiceCreateForm.getDistrict());
		orderInvoice.setDistrict(district);// 设置地区
		Area town = new Area();
		town.setId(orderInvoiceCreateForm.getTown());
		orderInvoice.setTown(town);// 设置乡镇
		orderInvoice.setCreateTime(new Date());// 设置开票时间
		orderInvoice.setEmail(order.getConsignee().getEmail());
		orderInvoice.setMobile(orderInvoiceCreateForm.getMobile());
		orderInvoice.setMoney(orderInvoiceService.calcInvoiceMoney(order,
				orderInvoiceCreateForm.getMoney()));
		orderInvoice.setQuantity(orderInvoiceCreateForm.getQuantity());
		orderInvoice.setConsignee(orderInvoiceCreateForm.getConsignee());
		orderInvoice.setTitle(orderInvoiceCreateForm.getTitle());
		orderInvoice.setZipCode(order.getConsignee().getZipCode());
		// 发票类型
		Code titleType = new Code(3460L);
		orderInvoice.setTitleType(titleType);
		orderInvoice.setMode(new Code(codeId));// 设置开票方式
		orderInvoice
				.setDeliveryOption(order.getConsignee().getDeliveryOption());
		orderInvoice.setMode(new Code(codeId));// 设置开票方式
		orderInvoice.setDeliveryTime(order.getDeliveryTime());// 发货时间
		orderInvoice
				.setDeliveryOption(order.getConsignee().getDeliveryOption());
		orderInvoice.setOperator(operator);
		if (order.getConsignee().getInvoiceType() != null) {
			orderInvoice.setType(order.getConsignee().getInvoiceType());
		}
		orderInvoice.setOrder(order);
		orderInvoiceService.calcInvoiceMoney(order, orderInvoice.getMoney());
		order.addInvoice(orderInvoice);
		orderInvoiceService.createOrderInvoice(orderInvoice);
		return orderInvoice;
	}
}