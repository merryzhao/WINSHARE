/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.ordersettle;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.BillException;
import com.winxuan.ec.model.bill.Bill;
import com.winxuan.ec.model.bill.BillAccount;
import com.winxuan.ec.model.bill.BillItem;
import com.winxuan.ec.model.bill.BillReceiptRecord;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.bill.BillService;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.returnorder.ReturnOrderService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author yangxinyi
 * @version 1.0,2013-04-01
 */
@Controller
@RequestMapping("/ordersettle")
public class OrderSettleController {
	private static final String DATEFORMAT="yyyy-MM-dd";

	@Autowired
	ChannelService channelService;

	@Autowired
	ReturnOrderService returnOrderService;

	@Autowired
	OrderService orderService;

	@Autowired
	BillService billService;
	
	private DateFormat dateFormat;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView tonew() {
		ModelAndView mav = new ModelAndView("/ordersettle/ordersettle");
		mav.addObject("billAccounts", findBillAccounts());
		return mav;
	}

	private List<Channel> findChannel() {
		List<Channel> channels = channelService.get(Channel.CHANNEL_ORDER_SETTLE).getLeafChildren();
		channels.add(channelService.get(Long.valueOf(8090)));
		return channels;
	}

	private List<BillAccount> findBillAccounts() {
		Map<String, Object> maps = new HashMap<String, Object>();
		return billService.queryBillAccount(maps, null, (short) 0);
	}

	@ResponseBody
	@RequestMapping(value = "/findLastBill", method = RequestMethod.GET)
	public String findLastBill(
			@RequestParam("billAccountId") Long billAccountId,
			@MyInject Employee employee) {
		Bill bill = billService.lastBill(billAccountId);
		String result = "";
		if (bill != null
				&& (!Code.BILL_CONFIRMED.equals(bill.getStatus().getId()) && !Code.BILL_PROCESSED
						.equals(bill.getStatus().getId()))) {
			result = bill.getId().toString();
		}
		return result;
	}
	
	@RequestMapping(value = "/toCreateBill", method = RequestMethod.GET)
	public ModelAndView toCreateBill(
			@RequestParam(value = "billAccountId") Long billAccountId,
			@RequestParam(value = "channelIds") Long[] channelIds,
			@RequestParam(value = "amount") BigDecimal amount,
			@RequestParam(value = "list") String list,
			@RequestParam(value = "invoice") String invoice,
			@MyInject Employee employee) throws BillException {
		BillAccount billAccount = billService.getBillAccount(billAccountId);
		List<Channel> channels = new ArrayList<Channel>();
		for (Long channelId : channelIds) {
			channels.add(new Channel(channelId));
		}
		Bill bill = billService.allotment(invoice, list, billAccount, channels, amount,
				employee);
		return new ModelAndView("redirect:/ordersettle/toLastBill?billId="
				+ bill.getId());
	}

	@RequestMapping(value = "/toLastBill")
	public ModelAndView toLastBill(@RequestParam(value = "billId") Long billId,
			@MyInject Pagination pagination) {
		Bill bill = billService.get(billId);
		BillAccount billAccount = bill.getBillAccount();
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("bill", bill.getId());
		List<BillItem> billItems = billService.query(maps, pagination,
				(short) 0);
//		List<Bill> unconfirmBills = findUnconfirmBills(billAccount); 
		ModelAndView mav = new ModelAndView("/ordersettle/ordersettle");
		mav.addObject("channelList", findChannel());
		mav.addObject("bill", bill);
//		mav.addObject("unconfirmBills", unconfirmBills);
		mav.addObject("billAccount", billAccount);
		mav.addObject("billAccounts", findBillAccounts());
		mav.addObject("amount", bill.getAllotment());
		mav.addObject("list", bill.getList());
		mav.addObject("invoice", bill.getInvoice());
		mav.addObject("billItemList", billItems);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	private List<Bill> findUnconfirmBills(BillAccount billAccount) {
		Long[] statusCodes = {Code.BILL_ALLOC_UNFINISHED, Code.BILL_UNCONFIRMED};
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("billAccountId", billAccount.getId());
		maps.put("statusCodes", statusCodes);
		return billService.queryByStatus(maps, null, (short)0);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addBillItems", method = RequestMethod.POST)
	public String addBillItems(@RequestParam(value = "itemIds") Long[] itemIds,
			@RequestParam(value = "billId") Long billId,
			@MyInject Employee employee, HttpServletResponse response)
			throws IOException {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for (Long itemId : itemIds) {
			orderItems.add(orderService.getOrderItem(itemId));
		}
		response.setContentType("text/html;charset=UTF-8");
		try {
			billService.appendAllocatment(orderItems, billId, employee);
		} catch (BillException e) { // TODO Auto-generated catch block
			response.getWriter().print(e.getMessage());
			return null;
		}
		response.getWriter().print("success");
		return null;
	}

	@RequestMapping(value = "/delelteBillItem", method = RequestMethod.GET)
	public ModelAndView delelteBillItem(
			@RequestParam(value = "orderItemId") Long orderItemId,
			@RequestParam(value = "billId") Long billId,
			@MyInject Employee employee) throws BillException {
		OrderItem orderItem = orderService.getOrderItem(orderItemId);
		billService.removeAllocatment(orderItem, billId, employee);
		return new ModelAndView("redirect:/ordersettle/toLastBill?billId="
				+ billId);
	}

	@RequestMapping(value = "/listOrderItems", method = RequestMethod.GET)
	public ModelAndView listOrderItems(@RequestParam("orderId") String orderId,
			@RequestParam("billId") Long billId) {
		ModelAndView mav = new ModelAndView("/ordersettle/orderitem_info");
		mav.addObject("orderId", orderId);
		mav.addObject("billId", billId);
		List<OrderItem> orderItems = billService.getOrderItems(billId, orderId);
		for (Iterator<OrderItem> it=orderItems.iterator(); it.hasNext();) {
		    if (it.next().getAvailablePrice().compareTo(BigDecimal.ZERO) == 0) {
		        it.remove();
		    }
		}
		Collections.sort(orderItems, new Comparator<OrderItem>(){
			@Override
			public int compare(OrderItem o1, OrderItem o2) {
				return o1.getAvailablePrice().compareTo(o2.getAvailablePrice()) == 1 ? -1 : 1;
			}
		});
		mav.addObject("orderItemList", orderItems);
		return mav;
	}

	@RequestMapping(value = "/lockBill", method = RequestMethod.GET)
	public ModelAndView lockBill(
			@RequestParam(value = "billId") Long billId,
			@MyInject Employee employee) throws BillException {
		Bill bill = billService.get(billId);
		billService.lock(bill, employee);
		return new ModelAndView("redirect:/ordersettle/viewBill?billId="
				+ billId);
	}

	/**
	 * 初始化日期
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat(DATEFORMAT);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value = "/listBills")
	public ModelAndView listBills(
			@RequestParam(value = "billId", required = false) Long billId,
			@RequestParam(value = "list", required = false, defaultValue = "") String list,
			@RequestParam(value = "invoice", required = false, defaultValue = "") String invoice,
			@RequestParam(value = "accountName", required = false, defaultValue = "") String accountName,
			@RequestParam(value = "startDate", required = false, defaultValue = "") Date startDate,
			@RequestParam(value = "endDate", required = false, defaultValue = "") Date endDate,
			@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/ordersettle/bill_list");
		Map<String, Object> maps = new HashMap<String, Object>();
		String startDateString = "";
		String endDateString = "";
		maps.put("bill", billId);
		if(StringUtils.isNotBlank(list)) {
			maps.put("list", "%" + list + "%");
		}
		if(StringUtils.isNotBlank(invoice)) {
			maps.put("invoice", "%" + invoice + "%");
		}
		if(StringUtils.isNotBlank(accountName)) {
			maps.put("accountName", "%" + accountName + "%");
		}
		if(startDate != null) {
			maps.put("startDate", startDate);
			startDateString = dateFormat.format(startDate);
		}
		if(endDate != null) {
			maps.put("endDate", endDate);
			endDateString = dateFormat.format(endDate);
		}
		List<Bill> bills = billService.queryBills(maps, null, (short) 0);
		mav.addObject("billId", billId);
		mav.addObject("list", list);
		mav.addObject("invoice", invoice);
		mav.addObject("accountName", accountName);
		mav.addObject("startDate", startDateString);
		mav.addObject("endDate", endDateString);
		mav.addObject("bills", bills);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value = "/viewBill")
	public ModelAndView viewBill(
			@RequestParam(value = "billId", required = false) Long billId,
			@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/ordersettle/billview");
		Map<String, Object> maps = new HashMap<String, Object>();
		if(billId == null) {
			return mav;
		}
		Bill bill = billService.get(billId);
		maps.put("bill", billId);
		List<BillItem> billItems = billService.query(maps, pagination,
				(short) 0);
		mav.addObject("bill", bill);
		mav.addObject("billId", billId);
		mav.addObject("billAccount", bill.getBillAccount());
		mav.addObject("billItemList", billItems);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value = "/confirmBill", method = RequestMethod.GET)
	public ModelAndView confirmBill(
			@RequestParam(value = "billId") Long billId,
			@MyInject Employee employee) throws BillException {
		Bill bill = billService.get(billId);
		billService.confirm(bill, employee);
		return new ModelAndView("redirect:/ordersettle/viewBill?billId="
				+ billId);
	}
	
	@RequestMapping(value = "/removeBillReceiptRecord", method = RequestMethod.GET)
	public ModelAndView removeBillReceiptRecord(
			@RequestParam(value = "billReceiptRecordId") Long recordId) {
		BillReceiptRecord record = billService.getBillReceiptRecord(recordId);
		Bill bill = record.getBill();
		billService.removeBillReceiptRecord(record);
		return new ModelAndView("redirect:/ordersettle/viewBill?billId="
				+ bill.getId());
	}
	
	@RequestMapping(value = "/saveBillReceiptRecord", method = RequestMethod.POST)
	public ModelAndView saveBillReceiptRecord(
			@RequestParam(value = "billId") Long billId,
			@RequestParam(value = "money") BigDecimal money,
			@RequestParam(value = "updatetime") Date updatetime) {
		Bill bill = billService.get(billId);
		BillReceiptRecord newRecord = new BillReceiptRecord();
		newRecord.setBill(bill);
		newRecord.setMoney(money);
		newRecord.setUpdatetime(updatetime);
		billService.saveBillReceiptRecord(newRecord);
		return new ModelAndView("redirect:/ordersettle/viewBill?billId="
				+ billId);
	}

	/*@RequestMapping(value = "/channelView", method = RequestMethod.GET)
	public ModelAndView channelView(@RequestParam(value = "channelId", required = false) Long channelId) {
		ModelAndView mav = new ModelAndView("/ordersettle/orderbillchannel");
		mav.addObject("channelList", findChannel());
		if (channelId != null) {
			BillChannel billChannel = billService.getBillChannel(channelId);
			mav.addObject("channelId", channelId);
			mav.addObject("billChannel", billChannel);
		}
		return mav;
	}

	@RequestMapping(value = "/updateTolerance", method = RequestMethod.GET)
	public ModelAndView updateTolerance(
			@RequestParam(value = "channelId") Long channelId,
			@RequestParam(value = "amount") BigDecimal amount,
			@MyInject Employee employee) throws BillException {
		Channel channel = channelService.get(channelId);
		billService.updateTolerance(channel, amount, employee);
		return new ModelAndView("redirect:/ordersettle/channelView?channelId="
				+ channelId);
	}*/

	@RequestMapping(value = "/billAccountEdit", method = RequestMethod.GET)
	public ModelAndView billAccountEdit(
			@RequestParam(value = "message", required = false) String message) {
		ModelAndView mav = new ModelAndView("/ordersettle/billaccount");
		mav.addObject("billAccounts", findBillAccounts());
		mav.addObject("channelList", findChannel());
		if (StringUtils.isNotBlank(message)) {
			if ("accountExist".equals(message)) {
				mav.addObject("message", "账户已存在，请检查");
			}
		}
		return mav;
	}

	@RequestMapping(value = "/billAccountCreate", method = RequestMethod.POST)
	public ModelAndView toCreateBill(
			@RequestParam(value = "channelIds") Long[] channelIds,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "tolerance") BigDecimal tolerance,
			@MyInject Employee employee) throws BillException {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("name", name);
		List<BillAccount> billAccounts = billService.queryBillAccount(maps,
				null, (short) 0);
		if (billAccounts.size() > 0) {
			return new ModelAndView(
					"redirect:/ordersettle/billAccountEdit?message=accountExist");
		}
		BillAccount billAccount = new BillAccount();
		billAccount.setName(name);
		billAccount.setBalance(BigDecimal.valueOf(0.0));
		billAccount.setTolerance(tolerance);
		billAccount.setLastEmployee(employee);
		List<Channel> channels = new ArrayList<Channel>();
		for (Long channelId : channelIds) {
			channels.add(new Channel(channelId));
		}
		billAccount.setChannels(channels);
		billService.saveBillAccount(billAccount);
		return new ModelAndView("redirect:/ordersettle/billAccountEdit");
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteBillAccountChannel", method = RequestMethod.GET)
	public String deleteBillAccountChannel(
			@RequestParam(value = "billAccountId") Long billAccountId,
			@RequestParam(value = "channelId") Long channelId,
			@MyInject HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		String msg = "success";
		try {
			billService.removeFromBillAccount(billAccountId, channelId);
		} catch (BillException e) {
			// TODO Auto-generated catch block
			msg = e.getMessage();
		}
		response.getWriter().print(msg);
		return null;
	}
	
	@RequestMapping(value = "/addBillAccountChannels", method = RequestMethod.POST)
	public ModelAndView addBillAccountChannels(
			@RequestParam(value = "billAccountId") Long billAccountId,
			@RequestParam(value = "channelIds") Long[] channelIds) throws BillException {
		BillAccount billAccount = billService.getBillAccount(billAccountId);
		List<Channel> channels = new ArrayList<Channel>();
		for(Long channelId : channelIds) {
			channels.add(new Channel(channelId));
		}
		billService.appendToBillAccount(billAccount, channels);
		return new ModelAndView("redirect:/ordersettle/billAccountEdit");
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateTolerance", method = RequestMethod.GET)
	public String updateTolerance(
			@RequestParam(value = "billAccountId") Long billAccountId,
			@RequestParam(value = "tolerance") BigDecimal tolerance,
			@MyInject HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		BillAccount billAccount = billService.getBillAccount(billAccountId);
		billAccount.setTolerance(tolerance);
		billService.saveBillAccount(billAccount);
		response.getWriter().print("success");
		return null;
	}
	
}