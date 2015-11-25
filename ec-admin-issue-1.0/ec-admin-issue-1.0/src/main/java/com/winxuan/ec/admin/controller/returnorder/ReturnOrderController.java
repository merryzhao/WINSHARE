/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.returnorder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.returnorder.ReturnOrderItem;
import com.winxuan.ec.model.returnorder.ReturnOrderPackage;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageItem;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageLog;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageRelate;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageRemark;
import com.winxuan.ec.model.returnorder.ReturnOrderTag;
import com.winxuan.ec.model.returnorder.ReturnOrderTrack;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.service.product.ProductSaleService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.returnorder.ReturnOrderService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author liyang
 * @version 1.0,2011-9-14
 */
@Controller
@RequestMapping("/returnorder")
public class ReturnOrderController {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(ReturnOrderController.class);

	private static final Long RETURN_ORDER_RESPONSIBLEORHOLDER = 27000l;
	private static final Long RETURN_ORDER_REASON = 30000l;
	private static final Long RETURN_ORDER_PICKUP = 28000l;
    //退货订单、标签、原包非整退
    private static final Long RETURN_ORDER_TAG_570002 = 570002L;
    //退货订单、标签、货到付款退货
    private static final Long RETURN_ORDER_TAG_570003 = 570003L;
    //退货订单、是否应该退款
    private static final Long IS_SHOULD_REFUND = 580000L;
    //退货订单、是否应该退款、不应该退款
    private static final Long IS_SHOULD_REFUND_580001 = 580001L;
    //退货订单、是否应该退款、待业务确定
    private static final Long IS_SHOULD_REFUND_580002 = 580002L;
    //退货订单、是否应该退款、应该退款
    private static final Long IS_SHOULD_REFUND_580003 = 580003L;
    //联盟
  	private static final Long CODE_PARENT_LIANMENG = 700L;
  	//文轩、代理
  	private static final Long CODE_PARENT_DAILI = 1001L;
  	
  	private static final Long CODE_PACKAGE_STATUS_600121 = 600121L;
  	private static final Long CODE_PACKAGE_STATUS_600122 = 600122L;
  	private static final Long CODE_PACKAGE_STATUS_600123 = 600123L;
  	private static final Long CODE_PACKAGE_STATUS_600124 = 600124L;
  	//包件录入
  	private static final Long CODE_PACKAGE_STATUS_600141 = 600141L;
  	//关联订单
  	private static final Long CODE_PACKAGE_STATUS_600142 = 600142L;
  	//改变处理状态
  	private static final Long CODE_PACKAGE_STATUS_600143 = 600143L;
  	//创建退货单
  	private static final Long CODE_PACKAGE_STATUS_600144 = 600144L;
  	
  	
	private static final String SUCCEED = "succeed";
	private static final String RETURNORDER_JSON  = "/returnorder/returnorder";
	
	private static final String PRODUCT = "product";
    private static final String BARCODE = "barcode";
    private static final String OWNCODE = "owncode";
    private static final String MERCHID = "merchid";
    private static final String TRANSFERRESULT = "transferResult";
    private static final String START_DELIVERY_TIME = "startDeliveryTime";
    private static final String END_DELIVERY_TIME = "endDeliveryTime";
    private static final String CONSIGNEE = "consignee";
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;

	@Autowired
	CodeService codeService;

	@Autowired
	PresentService presentService;
	
	@Autowired
	ChannelService channelService;

	@Autowired
	ReturnOrderService returnOrderService;
	
	@Autowired
	ProductSaleService productSaleService;
	
	private  Set<String> excelSet=new HashSet<String>();
	private Set<String> packageExcelSet = new HashSet<String>();

	/**
	 * 跳到新建画面
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView tonew() {
		ModelAndView mav = new ModelAndView(
		"/returnorder/returnorder_new_setup");
		return mav;
	}

	/**
	 * 跳到新建画面
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newReturnOrder(@RequestParam("id") String orderId,
			@RequestParam(required = false,value="expressid") String expressid,
			@RequestParam(required = false,value="packageid") Long packageid) {
		ModelAndView mav = new ModelAndView(
		"/returnorder/returnorder_new_setup");
		// 获取订单
		Order order = orderService.get(orderId);
		//订单状态
		if(order!=null){
			//当订单状态为已发货或者交易完成时，返回新建退换货数据
			Long status = order.getProcessStatus().getId();
			if(order.isDeliveried() || status.equals(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL)
					|| status.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL) ){
				// 返回
				mav.addObject("order", order);
				initForm(mav);
				if(!excelSet.isEmpty()){//判断是否上传了excel
					dealProductReturn(order);//增加exel上传处理功能
				}
				//已有退货单
				Map<String, Object> maps = new HashMap<String, Object>();
//				maps.put("type", Code.RETURN_ORDER_TYPE_RETURN);
				maps.put("orderId", orderId);
				List<ReturnOrder> returnOrders = returnOrderService.find(maps, null);
				mav.addObject("returnOrders", returnOrders);
				excelSet.clear();//每次执行完清空set
				
				mav.addObject("expressid", expressid = expressid==null?"":expressid);
				mav.addObject("packageid", packageid = packageid==null?0:packageid);
				ReturnOrderPackage returnpackage = returnOrderService.getPackage(packageid);
				Set<ReturnOrderPackageItem> packageItemList = null;
				if(returnpackage != null){					
					packageItemList = returnpackage.getReturnOrderPackageItemList();
				}
				mav.addObject("packageItemList", packageItemList);
				return mav;
			}
			mav.addObject("error",1);
			mav.addObject("orderId", orderId);
			return mav;
		}
		mav.addObject("error",0);
		mav.addObject("orderId", orderId);
		return mav;
	}
	
	private void initForm(ModelAndView mav){
	 // 退换货类型
        Code type = codeService.get(Code.RETURN_ORDER_TYPE);
        // 承担方和责任方
        Code responsibleOrholder = codeService
        .get(RETURN_ORDER_RESPONSIBLEORHOLDER);
        // 退换货原因
        Code reason = codeService.get(RETURN_ORDER_REASON);
        // 退换货方式
        Code pickup = codeService.get(RETURN_ORDER_PICKUP);
        // 礼券面值
        List<BigDecimal> presentValues = presentService.findValue();
        //发货DC
        List<Code> targetDcs = codeService.getAllDc();
        // 返回
        mav.addObject("types", type);
        mav.addObject("responsibleOrholders", responsibleOrholder);
        mav.addObject("reasons", reason);
        mav.addObject("pickups", pickup);
        mav.addObject("presentValues", presentValues);
        mav.addObject("targetDcs", targetDcs);
	}

	/**
	 * 跳转到新建第二步
	 * 
	 * @param returnOrderNewForm
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/tonext", method = RequestMethod.POST)
	public ModelAndView newToNext(@Valid ReturnOrderNewForm returnOrderNewForm,
			BindingResult result) {
		ModelAndView mav = new ModelAndView(
		"/returnorder/returnorder_new_confirm");
		mav.addObject("rONForm", returnOrderNewForm);
		mav.addObject("order",
				orderService.get(returnOrderNewForm.getOriginalorder()));
		mav.addObject("type", codeService.get(returnOrderNewForm.getType()));
		mav.addObject("responsible",
				codeService.get(returnOrderNewForm.getResponsible()));
		mav.addObject("holder", codeService.get(returnOrderNewForm.getHolder()));
		mav.addObject("reason", codeService.get(returnOrderNewForm.getReason()));
		mav.addObject("pickup", codeService.get(returnOrderNewForm.getPickup()));
		return mav;
	}

	/**
	 * 新建
	 * 
	 * @param returnOrderNewForm
	 * @param result
	 * @param creator
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView newReturnOrder(
			@Valid ReturnOrderNewForm returnOrderNewForm, BindingResult result,
			@MyInject Employee creator) {
		ModelAndView mav = new ModelAndView(
		"/returnorder/returnorder_new_succeed");
		// 获取订单
		Order originalOrder = orderService.get(returnOrderNewForm
				.getOriginalorder());
		// 创建退换货对象
		ReturnOrder returnOrder = new ReturnOrder();
		// 设置值
		returnOrder.setType(codeService.get(returnOrderNewForm.getType()));
		returnOrder.setResponsible(codeService.get(returnOrderNewForm
				.getResponsible()));
		returnOrder.setHolder(codeService.get(returnOrderNewForm.getHolder()));
		returnOrder.setPickup(codeService.get(returnOrderNewForm.getPickup()));
		returnOrder.setReason(codeService.get(returnOrderNewForm.getReason()));
		returnOrder.setRefundDeliveryFee(returnOrderNewForm
				.getRefunddeliveryfee());
		returnOrder.setRefundCompensating(returnOrderNewForm
				.getRefundcompensating());
		returnOrder.setRefundCouponValue(returnOrderNewForm
				.getRefundcouponvalue());
		returnOrder.setRemark(returnOrderNewForm.getRemark());
		
		//商品退货金额
		BigDecimal  returnAmount = new BigDecimal(0);
		// 如果退换货是补偿类型则不设置itemList;

		Long orderStatus = originalOrder.getProcessStatus().getId();
		if(orderStatus.equals(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL) || 
				orderStatus.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL)){
			if (!(Code.RETURN_ORDER_TYPE_COMPENSATE.equals(returnOrderNewForm.getType()) ||
					Code.RETURN_ORDER_TYPE_COMPENSATE_BOOK.equals(returnOrderNewForm.getType())	)) {
				mav.addObject("errorMessage","退货取消/缺货取消订单只能创建补偿订单!");
				return mav;
			}
		}
		int i = 0;
		try {
    		for (Long id : returnOrderNewForm.getItem()) {
    			for (OrderItem item : originalOrder.getItemList()) {
    				if (id.longValue() == item.getId().longValue()) {
    					//退货数量
    					Integer quantity  = returnOrderNewForm.getItemcount()[i];
    					//设置商品暂存库位，非必填
    					String location = returnOrderNewForm.getLocations()[i];
    					returnOrder.addItem(item,quantity,location);
    					//更新包件已处理数量
    					ReturnOrderPackage returnOrderPackage = returnOrderService.getPackage(returnOrderNewForm.getPackageid());
    					if(returnOrderPackage != null){
    						Set<ReturnOrderPackageItem> itemSet = returnOrderPackage.getReturnOrderPackageItemList();
    						for(ReturnOrderPackageItem packageitem : itemSet){
    							Long packageProductid = Long.parseLong(packageitem.getEccode());
    							if(item.getProductSale().getId().equals(packageProductid)){
    								packageitem.setDealQuantity(packageitem.getDealQuantity()+quantity);
    								returnOrderService.update(packageitem);
    							}
    						}
    					}
    					//商品实洋
    					BigDecimal  amount = item.getBalancePrice();
    					//商品实洋乘以退货数量
    					amount = amount.multiply(new BigDecimal(quantity));
    					//退货金额相加
    					returnAmount = returnAmount.add(amount);
    				}
    			}
    			i++;
    		}
    		//付退运费
    		BigDecimal deliveryfee = returnOrderNewForm.getRefunddeliveryfee();
    		//加上付退运费
    		returnAmount = returnAmount.add(deliveryfee);
    		//加入退货金额
    		returnOrder.setRefundGoodsValue(returnAmount);
    		//针对退货\换货，判断是否应该退款(原包非整退-不应该；货到付款<直销，代理，联盟>退货-待业务确定；不应该退款的优先级大于待定)
    		returnOrder.setShouldrefund(new Code(IS_SHOULD_REFUND_580003));
    		if(returnOrderNewForm.getType().equals(Code.RETURN_ORDER_TYPE_RETURN) || returnOrderNewForm.getType().equals(Code.RETURN_ORDER_TYPE_REPLACE)){
    			if(originalOrder.getPayType().getId().equals(Code.ORDER_PAY_TYPE_FIRST_DELIVERY)
    					&&(originalOrder.getChannel().getParent().getId().equals(CODE_PARENT_LIANMENG)
    							|| originalOrder.getChannel().getParent().getId().equals(CODE_PARENT_DAILI))){
    				returnOrder.setShouldrefund(new Code(IS_SHOULD_REFUND_580002));
    			}else if(returnOrderNewForm.getOriginalreturned().equals(RETURN_ORDER_TAG_570002)){ 				
    				returnOrder.setShouldrefund(new Code(IS_SHOULD_REFUND_580001));
    			}
    		}
    		if(StringUtils.isNotBlank(returnOrderNewForm.getExpressid())){    			
    			returnOrder.setExpressid(returnOrderNewForm.getExpressid());
    		}
    		mav.addObject("returnOrder", returnOrder);
    		//临时设置订单出货DC，在创建销退单时，设置退货单仓参数；
    		originalOrder.getDistributionCenter().setStrategy(new Code(returnOrderNewForm.getTargetDc()));
			returnOrderService.create(returnOrder, originalOrder, creator);
			//修改对应包件状态
			if(returnOrderNewForm.getPackageid()!=null){				
				changePackageStatus(originalOrder.getId(), returnOrderNewForm.getPackageid(), returnOrder.getId(), creator);
			}
			
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			mav.addObject("success", false);
			return mav;
		}
		mav.addObject("success", true);
		return mav;
	}

	/**
	 * 详细
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_detail");
		// 获取退换货订单
		ReturnOrder returnOrder = returnOrderService.get(id);
		// 退换货跟踪类型
		Code returnOrderTrackType = codeService
		.get(Code.RETURN_ORDER_TRACK_TYPE);
		// 承担方和责任方
		Code responsibleOrholder = codeService
		.get(RETURN_ORDER_RESPONSIBLEORHOLDER);
		// 退换货原因
		Code reason = codeService.get(RETURN_ORDER_REASON);
		// 退换货方式
		Code pickup = codeService.get(RETURN_ORDER_PICKUP);
		//退货订单标签
		List<ReturnOrderTag> returnOrderTagList = returnOrderService.findReturnOrderTagByReturnOrderId(id);
		// 礼券面值
		List<BigDecimal> presentValues = presentService.findValue();
		// 返回
		mav.addObject("returnOrder", returnOrder);
		mav.addObject("returnOrderTrackTypes", returnOrderTrackType);
		mav.addObject("responsibleOrholders", responsibleOrholder);
		mav.addObject("reasons", reason);
		mav.addObject("pickups", pickup);
		mav.addObject("returnOrderTagList", returnOrderTagList);
		mav.addObject("presentValues", presentValues);
		return mav;
	}

	/**
	 * 跳转到查询页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView goToList() {
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_query");
		// 退换货类型
		Code types = codeService.get(Code.RETURN_ORDER_TYPE);
		// 退换货状态
		Code status = codeService.get(Code.RETURN_ORDER_STATUS);
		// dc选择
		List<Code> dcList = codeService.findByParent(Code.DELIVERY_CENTER);
		// 是否应该退款
		Code refund = codeService.get(IS_SHOULD_REFUND);
		// 返回
		mav.addObject("types", types);
		mav.addObject("status", status);
		mav.addObject("dcList",dcList);
		mav.addObject("isrefund",refund);
		return mav;
	}

	/**
	 * 查询页面
	 * 
	 * @param rOQForm
	 * @param result
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView list(@Valid ReturnOrderQueryForm rOQForm,
			BindingResult result, @MyInject Pagination pagination) {
		// map
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 加载查询参数
		parameters.put(rOQForm.getIdType(), rOQForm.getIdS());
		parameters.put(CONSIGNEE, rOQForm.getConsigneeOrNull());
		parameters.put("creator", rOQForm.getCreatorOrNull());
		parameters.put("type", rOQForm.getType());
		parameters.put("status", rOQForm.getStatus());
		parameters.put(rOQForm.getStartTimeType(), rOQForm.getStartDateTime());
		parameters.put(rOQForm.getEndTimeType(), rOQForm.getEndDateTime());
		parameters.put("returnDc", rOQForm.getReturnDc());
		parameters.put("receiveDc", rOQForm.getReceiveDc());
		parameters.put("isrefund", rOQForm.getIsrefund());
		parameters.put("channelId", rOQForm.getChannel());
		// 查询结果列表
		List<ReturnOrder> returnOrders = returnOrderService.find(parameters,
				pagination);
		// 退换货类型
		Code types = codeService.get(Code.RETURN_ORDER_TYPE);
		// 退换货状态
		Code status = codeService.get(Code.RETURN_ORDER_STATUS);
		List<Code> dcList = codeService.getAllDc();
		// 是否应该退款
		Code refund = codeService.get(IS_SHOULD_REFUND);
		// 返回
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_query");
		mav.addObject("types", types);
		mav.addObject("status", status);
		mav.addObject("dcList",dcList);
		mav.addObject("isrefund",refund);
		mav.addObject("returnOrders", returnOrders);
		mav.addObject("pagination", pagination);
		mav.addObject("form", rOQForm);
		return mav;
	}

	/**
	 * 审核退换货
	 * 
	 * @param returnOrderIds
	 *            需要审核的退换货id集
	 * @param type
	 *            true通过审核，false 审核不通过
	 * @param pagination
	 *            分页
	 * @param auditor
	 *            操作人
	 * @return
	 * @throws ReturnOrderException 
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public ModelAndView verify(
			@RequestParam("returnOrderIds") Long[] returnOrderIds,
			@RequestParam(value="cause", defaultValue="") String cause,
			@RequestParam("type") boolean type,
			@MyInject Pagination pagination, @MyInject Employee auditor) throws ReturnOrderException {
		// 成功审核数量
		int succeed = 0;
		// 审核失败数量
		int fail = 0;
		// 审核失败的记录
		String message = "";
		// 构建map
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("returnOrderId", returnOrderIds);
		// 需要审核的退换货数据集
		List<ReturnOrder> returnOrders = returnOrderService.find(parameters,
				pagination);
		// 循环审核
		for (ReturnOrder returnOrder : returnOrders) {
			try {
				if (!returnOrder.getStatus().getId()
						.equals(Code.RETURN_ORDER_STATUS_NEW)) {
					throw new RuntimeException("退换货订单状态错误 ："+returnOrder.getStatus().getName()
							+" 不是 已提交");
				}
				if (type) {
					// 审核通过
					returnOrderService.audit(returnOrder, auditor);
				} else {
					// 审核不通过
				    returnOrder.setCause(cause);
					returnOrderService.cancel(returnOrder, auditor);
				}
				succeed++;
			} catch (RuntimeException e) {
				fail++;
				message += "(" + returnOrder.getId() + ")"+e.getMessage()+"\n";
			} catch (ReturnOrderException e1) {
				fail++;
				message += e1.getMessage()+"\n";
			}
		}
		// 返回
		ModelAndView mav = new ModelAndView(RETURNORDER_JSON);
		mav.addObject(SUCCEED, succeed);
		mav.addObject("fail", fail);
		mav.addObject("message", message);
		return mav;
	}

	/**
	 * 标记已退款
	 * 
	 * @param returnOrderIds
	 * @return
	 */
	@RequestMapping(value = "/refund", method = RequestMethod.POST)
	public ModelAndView refund(
			@RequestParam("returnOrderIds") Long[] returnOrderIds,
			@MyInject Pagination pagination, @MyInject Employee operator) {
		// 成功退款数量
		int succeed = 0;
		// 退款失败数量
		int fail = 0;
		// 退款失败的记录
		String message = "";
		// 构建map
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("returnOrderId", returnOrderIds);
		// 需要退款的退换货数据集
		List<ReturnOrder> returnOrders = returnOrderService.find(parameters,
				pagination);
		// 循环退款
		for (ReturnOrder returnOrder : returnOrders) {
			try {
				returnOrderService.refund(returnOrder, operator);
				succeed++;
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				fail++;
				message += "(" + returnOrder.getId() + ")";
			}
		}
		ModelAndView mav = new ModelAndView(RETURNORDER_JSON);
		mav.addObject(SUCCEED, succeed);
		mav.addObject("fail", fail);
		mav.addObject("message", message);
		return mav;
	}
	/**
	 * 下传中启
	 * 
	 * @param returnOrderIds
	 * @return
	 */
	@RequestMapping(value = "/downloadZQ", method = RequestMethod.POST)
	public ModelAndView downloadZQ(
			@RequestParam("returnOrderIds") Long returnOrderIds, @MyInject Employee operator) {
		// 需要下传中启的退换货
		ReturnOrder returnOrder = returnOrderService.get(returnOrderIds);
		ModelAndView mav = new ModelAndView(RETURNORDER_JSON);
		try {
			returnOrderService.startReturn(returnOrder, operator);
			mav.addObject(SUCCEED, true);
		} catch (Exception e) {
			mav.addObject(SUCCEED, false);
		}
		return mav;
	}
	/**
	 * 回传EC
	 * 
	 * @param returnOrderIds
	 * @return
	 */
	@RequestMapping(value = "/passBackEC", method = RequestMethod.POST)
	public ModelAndView passBackEC(
			@RequestParam("returnOrderIds") Long returnOrderIds,
			@RequestParam("returnOrderItem") Long[] returnOrderItemId,
			@RequestParam("realQuantity") Integer[] realQuantitys,
			@MyInject Pagination pagination, @MyInject Employee operator) {
		// 需要回传EC的退换货
		ReturnOrder returnOrder = returnOrderService.get(returnOrderIds);
		Set<ReturnOrderItem> items =  returnOrder.getItemList();
		// 设置实际退货数量
		for (int i = 0; i < returnOrderItemId.length; i++) {
			for (ReturnOrderItem returnOrderItem : items) {
				//如果参数ID 与 原数据ID 相等，则 将对应的实际退货数量赋值给此记录
				if(returnOrderItemId[i].equals(returnOrderItem.getId())){
					returnOrderItem.setRealQuantity(realQuantitys[i]);
				}
			}
		}
		ModelAndView mav = new ModelAndView(RETURNORDER_JSON);
		try {
			returnOrderService.completeReturn(returnOrder, false, operator);
			mav.addObject(SUCCEED, true);
		} catch (Exception e) {
			mav.addObject(SUCCEED, false);
		}
		return mav;
	}	

	/**
	 * 新建退换货跟踪
	 * 
	 * @param id
	 * @param trackType
	 * @param trackRemark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/newTrack", method = RequestMethod.POST)
	public ModelAndView newTrack(@RequestParam("id") Long id,
			@RequestParam("trackType") Long trackType,
			@RequestParam("trackRemark") String trackRemark,
			@MyInject Employee operator) {
		// 获取退换货订单
		ReturnOrder returnOrder = returnOrderService.get(id);
		// 新建跟踪
		ReturnOrderTrack track = new ReturnOrderTrack();
		track.setReturnOrder(returnOrder);
		track.setType(codeService.get(trackType));
		track.setContent(trackRemark);
		track.setCreateTime(new Date());
		track.setOperator(operator);
		// 保存数据
		returnOrderService.addTrack(returnOrder, track);
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_reack");
		mav.addObject("returnOrder", returnOrder);
		return mav;
	}

	/**
	 * 修改退换货
	 * @param name
	 * @param value
	 * @param operator
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@RequestParam("id") Long id,
			@RequestParam("name") String name,
			@RequestParam("value") String value) throws Exception{
		ModelAndView mav = new ModelAndView(RETURNORDER_JSON);
		//需要修改的对象
		ReturnOrder returnOrder =  returnOrderService.get(id);
		try {
			//如果修改退换货数量
			if("appQuantity".equals(name)){
				Set<ReturnOrderItem> items = returnOrder.getItemList();
				String[] values = value.split("-");
				if(values.length==2){
					Long itemId = Long.valueOf(values[0]);
					Integer appQuantity = Integer.valueOf(values[1]);
					for (ReturnOrderItem item : items) {
						if(item.getId().longValue()==itemId.longValue()){
							item.setAppQuantity(appQuantity);
							break;
						}
					}
				}else{
					throw new RuntimeException("data exception!");
				}
			}else{
				//修改具体的值
				setObjectValue(returnOrder, name, value);
			}
			//修改此对象
			returnOrderService.update(returnOrder);
			//修改成功
			mav.addObject(SUCCEED, true);
		} catch (Exception e) {
			mav.addObject(SUCCEED, false);
		}
		return mav;
	}
	/**
	 * 设置对象某字段的值
	 * @param object 需修改的对象
	 * @param paramName 对象字段名
	 * @param value 修改后的值
	 * @throws Exception
	 */
	private void setObjectValue(Object object, String paramName, Object value)
	throws Exception {
		Class<?> c = object.getClass();
		Field field = c.getDeclaredField(paramName);
		field.setAccessible(true);
		value = getObjectForClass(field.getType(), value);
		field.set(object, value);
	}
	/**
	 * 根据Class和value返回对应的对象
	 * @param type Class
	 * @param value 值
	 * @return
	 */
	private Object getObjectForClass(Class<?> type, Object value) {
		String className = "";
		if (type.toString().startsWith("class")) {
			className = type.getCanonicalName().replace(
					type.getPackage().getName() + ".", "");
			if("Code".equals(className)){
				return new Code(Long.valueOf(value+""));
			}else if("BigDecimal".equals(className)){
				return new BigDecimal(value+"");
			}else{
				return value;
			}
		} else {
			className = type.toString();
			if("int".equals(className)){
				return Integer.parseInt(value+"");
			}else{
				return value;
			}
		}
	}
	
	@RequestMapping(value="/batchnew", method = RequestMethod.GET)
	public ModelAndView batchNew(){
	    ModelAndView view = new ModelAndView("/returnorder/returnorder_batch_new");
	    initForm(view);
	    return view;
	}
	
	
	@RequestMapping(value="/findorder", method = RequestMethod.POST)
	public ModelAndView findOrder(@RequestParam("")Map<String, Object> map){
	    Map<String, Object> param = new HashMap<String, Object>();
	    Map<String, Object> pro = new HashMap<String, Object>();
	    if(map.containsKey(PRODUCT)){
	        param.put(PRODUCT, Long.parseLong(map.get(PRODUCT).toString()));
	        pro.put("productSaleId", Long.parseLong(map.get(PRODUCT).toString()));
	    }
	    if(map.containsKey(BARCODE)){
	        param.put(BARCODE, map.get(BARCODE).toString());
	        pro.put("productBarcode", map.get(BARCODE).toString());
	    }
	    if(map.containsKey(OWNCODE)){
	        param.put(OWNCODE, map.get(OWNCODE).toString());
	        pro.put("outerId", map.get(OWNCODE).toString());
	    }
	    if(map.containsKey(MERCHID)){
	        param.put(MERCHID, Long.parseLong(map.get(MERCHID).toString()));
	        pro.put("prodcutMerchId", Long.parseLong(map.get(MERCHID).toString()));
	    }
	    if(map.containsKey(TRANSFERRESULT)){
	    	Long transferResult = Long.parseLong(map.get(TRANSFERRESULT).toString());
	        param.put(TRANSFERRESULT, transferResult);
	        if(transferResult.equals(Code.EC2ERP_STATUS_NEW)){
	        	param.put("storageType", new Long[]{Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM,Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_PLATFORM});
	        }
	    }
	    param.put("parentChannel", new Long[]{Channel.CHANNEL_GROUP_BUY,Channel.CHANNEL_GROUP_BUY_EC});
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    if(map.containsKey(START_DELIVERY_TIME)){
	        try {
                param.put(START_DELIVERY_TIME, sdf.parse(map.get(START_DELIVERY_TIME).toString()));
            } catch (ParseException e) {
                map.get(START_DELIVERY_TIME);
            }
	    }
	    if(map.containsKey(END_DELIVERY_TIME)){
	        try {
	            param.put(END_DELIVERY_TIME, sdf.parse(map.get(END_DELIVERY_TIME).toString()));
	        } catch (Exception e) {
	            map.get(END_DELIVERY_TIME);
	        }
	    }
	    
	    if(map.containsKey(CONSIGNEE)){
	        param.put(CONSIGNEE, map.get(CONSIGNEE));
	    }
	    if(map.containsKey("name")){
	        param.put("name", map.get("name"));
	    }
	    //查询商品
	    List<ProductSale> ps = new ArrayList<ProductSale>();
	    
	    if(pro.size() > 0){
	        ps = productService.findProductSale(pro, null);
	    }
	    //查询订单
	    List<Order> orders = orderService.find(param, Short.parseShort("5"), null);
	    ModelAndView view = new ModelAndView("/returnorder/batchorder");
	    view.addObject("orders", orders);
	    view.addObject("ps", ps.size() > 0 ? ps.get(0) : null);
	    return view;
	}
	/**
	 * 处理上传的excel数据,该excel中的商品是针对某个特定的订单（即：要进行退货操作的订单）
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productReturnUpload", method = RequestMethod.POST)
	public ModelAndView dealFileData(@RequestParam(required = false, value = "fileName") MultipartFile file,HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_new_setup");
		if (file != null) {
	        // 文件获取流，流获取workbook,
	        Workbook excel = null;
            try {
                excel = Workbook.getWorkbook(file.getInputStream());
                // 获取第一页
                Sheet sheet = excel.getSheet(MagicNumber.ZERO);
                for (int i = MagicNumber.ONE; i < sheet.getRows(); i++) {
                	String productECCode = sheet.getCell(MagicNumber.ZERO, i).getContents().trim();
                	String excelAppNum = sheet.getCell(MagicNumber.ONE, i).getContents().trim();
                	excelSet.add(productECCode+":"+excelAppNum);
                }
                mav.addObject("message", 1);
            } catch (Exception e1) {
                throw new RuntimeException("excel无法读取");
            }
        }
		return mav;
	}
	/**
	 * 匹配excel数据和系统订单数据
	 * @param order
	 */
	public void dealProductReturn(Order order){
		Set<OrderItem> oiSet=order.getItemList();
        for(OrderItem oi:oiSet){
        	for(String e:excelSet){
        		if(oi.getProductSale().getId().equals(new Long(e.split(":")[0]))){
        			int deliveryNum=oi.getDeliveryQuantity();//发货数量
        			int eNum=Integer.parseInt(e.split(":")[1]);//excel申请数量
        			int returnNum=oi.getReturnQuantity();//已退货数量
        			int canReturnNum=0;//可退货数量
        			canReturnNum=deliveryNum-returnNum;
        			int pageReturnNum=0;//页面退换货数量
        			if(canReturnNum>eNum){//如果可退数量大于申请数量,则用申请数量作为页面的退换货数量，否则就用可退数量作为页面的退换货数量
        				pageReturnNum=eNum;
        			}else{
        				pageReturnNum=canReturnNum;
        			}
        			ProductSale ps=oi.getProductSale();
        			ps.setRemark(pageReturnNum+"");//将退换货数量放在remark字段中方便页面获取
        			oi.setProductSale(ps);
        		}
        		
        	}
        }
	}
	
	/**
	 * 根据returnorderid执行不退款操作。
	 * @return
	 */
	@RequestMapping(value = "/notrefund", method = RequestMethod.POST)
	public ModelAndView notrefund(@RequestParam(value = "returnOrderId", required = true) Long returnOrderId ,
			@MyInject Employee operator ){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder");
		ReturnOrder returnOrder = returnOrderService.get(returnOrderId);
		if(returnOrder != null){
			returnOrderService.notRefund(returnOrder,operator);
			mav.addObject("message","不退款操作成功...");
		}
		return mav;
	}
	
	@RequestMapping(value = "/packagenew", method = RequestMethod.GET)
	public ModelAndView newPackage(@MyInject Employee creator){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_package_setup");
		List<Channel> channelList = channelService.findSupplyChannel();
		mav.addObject("supplylist", channelList);
		List<Code> codolist = codeService.findByParent(600130l);
		mav.addObject("returntypelist",codolist);
		mav.addObject("creator", creator.getDisplayName());
		return mav;
	}
	
	@RequestMapping(value = "/packageconfirm", method = RequestMethod.POST)
	public ModelAndView confirmPackage(@Valid ReturnOrderPackageForm returnOrderPackageForm, BindingResult result){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_package_confirm");
		mav.addObject("packageform", returnOrderPackageForm);
		mav.addObject("type", "1".equals(returnOrderPackageForm.getType()) ? "零售":"供货");
		List<PackageItemTempForm> itemList = new ArrayList<PackageItemTempForm>();
		for(int i = 0; i < returnOrderPackageForm.getProductSale().length; i++ ){
			PackageItemTempForm form = new PackageItemTempForm();
			Long saleid = Long.parseLong(returnOrderPackageForm.getProductSale()[i].trim());
			ProductSale productSale = productSaleService.findProductSale(saleid);
			form.setProductsale(productSale);
			form.setLocation(returnOrderPackageForm.getLocations()[i]);
			form.setQuantity(returnOrderPackageForm.getQuantity()[i]);
			itemList.add(form);
		}
		mav.addObject("packageitemlist", itemList);
		return mav;
	}
	
	/**
	 * 保存returnorderPackage
	 * @param returnOrderPackageForm
	 * @param result
	 * @param creator
	 * @return
	 */
	@RequestMapping(value = "/packagenew", method = RequestMethod.POST)
	public ModelAndView createPackageInfo(@Valid ReturnOrderPackageForm returnOrderPackageForm, BindingResult result,
			@MyInject Employee creator){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_package_succeed");
		/*List<Channel> channelList = channelService.findSupplyChannel();
		mav.addObject("supplylist", channelList);
		List<Code> codolist = codeService.findByParent(600130l);
		mav.addObject("returntypelist",codolist);
		mav.addObject("creator", creator.getDisplayName());*/
		
		ReturnOrderPackage returnPackage = new ReturnOrderPackage();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfn = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		try {
			Date expressTime = "".equals(returnOrderPackageForm.getExpresstime()) ? null:sdf.parse(returnOrderPackageForm.getExpresstime());
			Date signTime = "".equals(returnOrderPackageForm.getSigntime()) ? new Date():sdfn.parse(returnOrderPackageForm.getSigntime());
			returnPackage.setExpressTime(expressTime);
			returnPackage.setsSignTime(signTime);
		} catch (ParseException e) {
			mav.addObject("msg", "时间格式转换异常，请核实！");
			return mav;
		}
		returnPackage.setType(returnOrderPackageForm.getType());
		Long returntype = Long.parseLong(returnOrderPackageForm.getReturnType());
		returnPackage.setReturntype(codeService.get(returntype));
		returnPackage.setCarrier(returnOrderPackageForm.getCarrier());
		returnPackage.setExpressid(returnOrderPackageForm.getExpressid());
		returnPackage.setCustomer(returnOrderPackageForm.getCustomer());
		returnPackage.setPhone(returnOrderPackageForm.getPhone());
		returnPackage.setAddress(returnOrderPackageForm.getAddress());
		returnPackage.setRemark(returnOrderPackageForm.getRemark());
		returnPackage.setsSignName(returnOrderPackageForm.getSignname());
		returnPackage.setStorageLocation(returnOrderPackageForm.getStoragelocation());
		returnPackage.setShouldQuantity(returnOrderPackageForm.getShouldquantity());
		returnPackage.setRealQuantity(returnOrderPackageForm.getRealquantity());
		returnPackage.setStatus(codeService.get(CODE_PACKAGE_STATUS_600121));//“已录入”状态
		returnPackage.addLog(new Code(CODE_PACKAGE_STATUS_600141), creator);
		/*if(isRelateOrder(returnOrderPackageForm.getOrderid())){
			if(checkOrder(returnOrderPackageForm.getOrderid())){
				returnPackage.setStatus(codeService.get(CODE_PACKAGE_STATUS_600122));//“已关联订单”状态
			}else{
				mav.addObject("msg","输入订单号不能创建退货单，所以不能关联包件！");
				return mav;
			}
		}else{
			returnPackage.setStatus(codeService.get(CODE_PACKAGE_STATUS_600121));//“已录入”状态
		}*/
		try {
			returnOrderService.saveReturnOrderPackage(returnPackage);
		} catch (ReturnOrderException e) {
			mav.addObject("msg", "保存包件异常，请联系管理人员！");
			LOG.error("运单号："+returnOrderPackageForm.getExpressid()+"保存失败...");
			return mav;
		}
		
		//保存商品信息
		String[] eccodes = returnOrderPackageForm.getProductSale();
		int[] quantitys = returnOrderPackageForm.getQuantity();
		String[] locations = returnOrderPackageForm.getLocations();
		for(int i = 0; i < eccodes.length; i++){
			long eccode = Long.parseLong(eccodes[i]);
			int quantity = quantitys[i];
			String location = locations[i];
			ProductSale productSale = productSaleService.findProductSale(eccode);
			ReturnOrderPackageItem item = new ReturnOrderPackageItem();
			item.setReturnOrderPackage(returnPackage);
			item.setEccode(eccodes[i]);
			item.setOuterid(productSale.getOuterId());
			item.setIsbn(productSale.getProduct().getBarcode());
			item.setName(productSale.getName());
			item.setListprice(productSale.getListPrice());
			item.setQuantity(quantity);
			item.setLocation(location);
			try {
				returnOrderService.saveReturnOrderPackageItem(item);
			} catch (ReturnOrderException e) {
				mav.addObject("msg", "保存包件详情异常，请联系管理人员！");
				LOG.error("运单号："+returnOrderPackageForm.getExpressid()+",商品编号："+eccodes[i]+"保存失败...");
				return mav;
			}
		}
		//保存包件关联信息（原订单号，供货退货订单号等）
		String orderid = returnOrderPackageForm.getOrderid();
		String returnid = returnOrderPackageForm.getReturnid();
		String typeid = returnOrderPackageForm.getType();
		if(StringUtils.isNotBlank(orderid)){
			String[] orderids = orderid.split("\r\n");
			for(String order : orderids){
				ReturnOrderPackageRelate relate = new ReturnOrderPackageRelate();
				relate.setReturnOrderPackage(returnPackage);
				relate.setRelateid(order);
				relate.setStatus(Short.valueOf("0"));
				returnPackage.addLog(new Code(CODE_PACKAGE_STATUS_600142), creator);
				try {
					returnOrderService.saveReturnOrderPackageRelate(relate);
				} catch (ReturnOrderException e) {
					mav.addObject("msg", "保存包件关联表异常，请联系管理人员！");
					LOG.error("包件号："+returnPackage.getId()+"对应的关系表保存失败！");
					return mav;
				}
			}
		}
		if("2".equals(typeid) && StringUtils.isNotBlank(returnid)){
			String[] returnids = returnid.split(",");
			for(String re : returnids){
				ReturnOrderPackageRelate relate = new ReturnOrderPackageRelate();
				relate.setReturnOrderPackage(returnPackage);
				relate.setRelateid(re);
				relate.setStatus(Short.valueOf("2"));
				try {
					returnOrderService.saveReturnOrderPackageRelate(relate);
				} catch (ReturnOrderException e) {
					mav.addObject("msg", "保存包件关联表异常，请联系管理人员！");
					LOG.error("包件号："+returnPackage.getId()+"对应的关系表保存失败！");
					return mav;
				}
			}
		}
		//mav.setViewName("redirect:/returnorder/packagenew");
		mav.addObject("returnPackage", returnPackage);
		return mav;
	}
	
	/**
	 * 判断包件是否关联了订单
	 * @return
	 */
	public boolean isRelateOrder(String orderid){
		if(StringUtils.isNotBlank(orderid)){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查关联的订单是否可用
	 * @return
	 */
	public boolean checkOrder(String orderid){
		if(StringUtils.isNotBlank(orderid)){
			String[] orderids = orderid.split("\r\n");
			if(orderids.length > 0){
				for(String ord : orderids){
					Order order = orderService.get(ord);
					if(order != null){
						Long status = order.getProcessStatus().getId();
						if(order.isDeliveried() || status.equals(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL)
								|| status.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL) 
								|| status.equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL)){
							continue;
						}
						return false;
					}
					return false;
				}
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * 包件信息商品上传
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/returnPackageProductUpload", method=RequestMethod.POST)
	public ModelAndView dealReturnPackageUploadData(@RequestParam(required = false, value = "file") MultipartFile file,HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/returnorder/add_package_product");
		if (file != null) {
	        // 文件获取流，流获取workbook,
	        Workbook excel = null;
	        List<ReturnOrderPackageItem> packageItemList = new ArrayList<ReturnOrderPackageItem>();
            try {
                excel = Workbook.getWorkbook(file.getInputStream());
                // 获取第一页
                Sheet sheet = excel.getSheet(MagicNumber.ZERO);
                for (int i = MagicNumber.ONE; i < sheet.getRows(); i++) {
                	String productECCode = sheet.getCell(MagicNumber.ZERO, i).getContents().trim();
                	String isbn = sheet.getCell(MagicNumber.ONE, i).getContents().trim();
                	String productName = sheet.getCell(MagicNumber.TWO,i).getContents().trim();
                	String listprice = sheet.getCell(MagicNumber.THREE,i).getContents().trim();
                	String quantity = sheet.getCell(MagicNumber.FOUR,i).getContents().trim();
                	packageExcelSet.add(productECCode+":"+isbn+":"+productName+":"+listprice+":"+quantity);
                	ReturnOrderPackageItem packageItem = new ReturnOrderPackageItem();
                	packageItem.setEccode(productECCode);
                	packageItem.setIsbn(isbn);
                	packageItem.setName(productName);
                	packageItem.setListprice(new BigDecimal(listprice));
                	packageItem.setQuantity(Integer.parseInt(quantity));
                	packageItemList.add(packageItem);
                }
                mav.addObject("message", 1);
                mav.addObject("list",packageItemList);
            } catch (Exception e1) {
                throw new RuntimeException("excel读取失败，请重试！");
            }
        }
		return mav;
	}
	
	/**
	 * 新建订单时添加商品
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/addPackageProduct", method = RequestMethod.POST)
	public ModelAndView orderAddProductSale(
			@RequestParam(value="search_productSaleId", required = false) String productSaleId,
			@RequestParam(value="search_outerId", required = false) String outerId,
			@RequestParam(value="search_ISBN",required = false) String isbn,
			@RequestParam(value="search_productName",required = false) String productName) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		if (!"".equals(productSaleId)) {
			parameters.put("productSaleId",
					NumberUtils.createLong(productSaleId));
		}

		if (!"".equals(productName)) {
			parameters.put(ControllerConstant.PRODUCTNAME, productName);
		}
		if (!"".equals(isbn)) {
			parameters.put(ControllerConstant.PRODUCTBARCODE, isbn);
		}
		if (!"".equals(outerId)) {
			parameters.put(ControllerConstant.OUTERID, outerId);
		}
		// 查询数据
		List<ProductSale> productSales = productService.findProductSale(
				parameters, new Pagination());
		List<ReturnOrderPackageItem> list = new ArrayList<ReturnOrderPackageItem>();
		for (ProductSale productSale : productSales) {
			ReturnOrderPackageItem item = new ReturnOrderPackageItem();
			item.setEccode(productSale.getId().toString());
			item.setIsbn(productSale.getProduct().getBarcode());
			item.setName(productSale.getName());
			item.setListprice(productSale.getListPrice());
			list.add(item);
		}
		ModelAndView mav = new ModelAndView("/returnorder/add_package_product");
		mav.addObject("list", list);
		return mav;
	}
	
	/**
	 * 导航到包件查询页面
	 * @return
	 */
	@RequestMapping(value = "/packagequery", method = RequestMethod.GET)
	public ModelAndView gotoPackage(){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_package_query");
		List<Code> codelist = codeService.findByParent(600120l);
		List<Channel> channelList = channelService.findSupplyChannel();
		mav.addObject("typelist", channelList);
		mav.addObject("statusList", codelist);
		return mav;
	}
	
	/**
	 * 包件信息查询
	 * @param returnOrderPackageQueryForm
	 * @return
	 */
	@RequestMapping(value = "/packagequery", method = RequestMethod.POST)
	public ModelAndView queryPackage(@Valid ReturnOrderPackageQueryForm returnOrderPackageQueryForm, @MyInject Pagination pagination){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_package_query");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("expressid", returnOrderPackageQueryForm.getExpresidOrNull());
		param.put("orderid", returnOrderPackageQueryForm.getOrderidOrNull());
		param.put("status", returnOrderPackageQueryForm.getStatus());
		param.put("customer", returnOrderPackageQueryForm.getCustomerOrNull());
		param.put("phone", returnOrderPackageQueryForm.getPhoneOrNull());
		param.put("returnid", returnOrderPackageQueryForm.getReturnidOrNull());
		param.put("starttime", returnOrderPackageQueryForm.getStartDateTime());
		param.put("endtime", returnOrderPackageQueryForm.getEndDateTime());
		List<ReturnOrderPackage> packageList = returnOrderService.findReturnOrderPackage(param,pagination);
		
		List<Code> codelist = codeService.findByParent(600120l);
		List<Channel> channelList = channelService.findSupplyChannel();
		mav.addObject("statusList", codelist);
		mav.addObject("expressid_param", returnOrderPackageQueryForm.getExpressid());
		mav.addObject("orderid_param", returnOrderPackageQueryForm.getOrderid());
		mav.addObject("status_param", returnOrderPackageQueryForm.getStatus());
		mav.addObject("customer_param", returnOrderPackageQueryForm.getCustomer());
		mav.addObject("phone_param", returnOrderPackageQueryForm.getPhone());
		mav.addObject("returnid_param", returnOrderPackageQueryForm.getReturnid());
		mav.addObject("typelist", channelList);
		mav.addObject("packagelist", packageList);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	@RequestMapping(value = "/packagequery/{packageid}", method = RequestMethod.GET)
	public ModelAndView getPackageDetail(@PathVariable("packageid") Long packageid){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_package_item");
		ReturnOrderPackage returnOrderPackage = returnOrderService.getPackage(packageid);
		List<ReturnOrderPackageItem> itemList = returnOrderService.getPackageItem(packageid);
		mav.addObject("returnOrderPackage", returnOrderPackage);
		mav.addObject("packageitemlist", itemList);
		return mav;
	}
	
	@RequestMapping(value = "/dealpackage/{packageid}", method = RequestMethod.POST)
	public ModelAndView dealPackage(@PathVariable("packageid") Long packageid, 
			@RequestParam(value="status", required = true) String status,
			@MyInject Employee operator){
		ModelAndView mav = new ModelAndView(RETURNORDER_JSON);
		ReturnOrderPackage returnPackage = returnOrderService.getPackage(packageid);
		if(returnPackage != null){			
			/*if("clear".equals(status)){
				returnPackage.setStatus(codeService.get(CODE_PACKAGE_STATUS_600125));
			}*/
			if("suspend".equals(status)){
				returnPackage.setStatus(codeService.get(CODE_PACKAGE_STATUS_600123));
			}
			if("dealing".equals(status)){
				returnPackage.setStatus(codeService.get(CODE_PACKAGE_STATUS_600122));
			}
			if("done".equals(status)){
				returnPackage.setStatus(codeService.get(CODE_PACKAGE_STATUS_600124));
			}
			returnPackage.addLog(new Code(CODE_PACKAGE_STATUS_600143), operator);
			returnOrderService.update(returnPackage);
			mav.addObject(SUCCEED, true);
			return mav;
		}
		mav.addObject(SUCCEED, false);
		return mav;
	}
	
	@RequestMapping(value = "/ralatepackage/{packageid}", method = RequestMethod.POST)
	public ModelAndView relatePackage(@PathVariable("packageid") Long packageid,
			@RequestParam(value="orders", required = true) String orders,
			@MyInject Employee operator){
		ModelAndView mav = new ModelAndView(RETURNORDER_JSON);
		ReturnOrderPackage rop = returnOrderService.getPackage(packageid);
		if(rop == null){
			mav.addObject(SUCCEED, false);
			mav.addObject("message", "包件"+packageid+",不存在，请核实！！");
			return mav;
		}
		if(StringUtils.isBlank(orders)){
			mav.addObject(SUCCEED, false);
			mav.addObject("message", "订单号不能为空！！！");
			return mav;
		}
		if(!checkOrder(orders)){
			mav.addObject(SUCCEED, false);
			mav.addObject("message", "关联的订单号状态不对或者对应订单不存在，不能关联，请核实！！！");
			return mav;
		}
		
		
		ReturnOrderPackageRelate ropr= new ReturnOrderPackageRelate();
		ropr.setReturnOrderPackage(rop);
		ropr.setRelateid(orders);
		ropr.setStatus(Short.valueOf("0"));
		try {
			returnOrderService.saveReturnOrderPackageRelate(ropr);
			rop.setStatus(codeService.get(CODE_PACKAGE_STATUS_600122));//更新为“处理中”
			rop.addLog(new Code(CODE_PACKAGE_STATUS_600143), operator);
			returnOrderService.update(rop);
		} catch (ReturnOrderException e) {
			mav.addObject(SUCCEED, false);
			mav.addObject("message", "订单号"+orders+"关联包件失败，请稍后重试...");
			return mav;
		}
		
		mav.addObject(SUCCEED, true);
		return mav;
	}
	
	public void changePackageStatus(String orderid, Long packageid, Long returnorderid, Employee creator){
		
		ReturnOrderPackage rop = returnOrderService.getPackage(packageid);
		if(rop != null){
			Set<ReturnOrderPackageRelate> roprSet =rop.getReturnOrderPackageRelateList();
			//boolean temp = true;
			for(ReturnOrderPackageRelate ropr : roprSet){
				if(!Short.valueOf("2").equals(ropr.getStatus())){					
					if(ropr.getRelateid().equals(orderid)){
						ropr.setStatus(Short.valueOf("1"));
						ropr.setReturnorderid(returnorderid);
						returnOrderService.update(ropr);
						rop.addLog(new Code(CODE_PACKAGE_STATUS_600144), creator);
					}
					/*if(Short.valueOf("0").equals(ropr.getStatus())){
						temp = false;
					}*/
				}
			}
			//所有订单处理完了之后，更改包件状态为“已处理”
			/*if(temp){
				rop.setStatus(codeService.get(CODE_PACKAGE_STATUS_600124));//已处理状态
				returnOrderService.update(rop);
			}*/
		}
	}
	
	/**
	 * 给包件添加备注信息
	 * @return
	 */
	@RequestMapping(value = "/packageremark", method = RequestMethod.POST)
	public ModelAndView doRemarkPackage(
			@RequestParam(value="remark", required = true) String remark,
			@RequestParam(value="packageid", required = true) Long packageid,
			@MyInject Employee user){
		ModelAndView mav = new ModelAndView(RETURNORDER_JSON);
		ReturnOrderPackage returnOrderPackage = returnOrderService.getPackage(packageid);
		if(returnOrderPackage != null){			
			ReturnOrderPackageRemark ropr = new ReturnOrderPackageRemark();
			ropr.setReturnOrderPackage(returnOrderPackage);
			ropr.setRemark(remark);
			ropr.setRemarktime(new Date());
			ropr.setUser(user);
			returnOrderService.saveReturnOrderPackageRemark(ropr);
			mav.addObject(SUCCEED, true);
			return mav;
		}
		mav.addObject(SUCCEED, false);
		return mav;
	}
	
	/**
	 * 获取包件备注信息列表
	 * @return
	 */
	@RequestMapping(value = "/packageremark", method = RequestMethod.GET)
	public ModelAndView getRemarkPackage(@RequestParam(value="packageid", required = true) Long packageid){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_package_remark");
		List<ReturnOrderPackageRemark> remarkList = returnOrderService.findPackageRemarkListByPackageid(packageid);
		mav.addObject("remarkList", remarkList);
		return mav;
	}
	
	/**
	 * 获取包件日志信息列表
	 * @param packageid
	 * @return
	 */
	@RequestMapping(value = "/packagelog", method = RequestMethod.GET)
	public ModelAndView getLogPackage(@RequestParam(value="packageid", required = true) Long packageid){
		ModelAndView mav = new ModelAndView("/returnorder/returnorder_package_log");
		List<ReturnOrderPackageLog> logList = returnOrderService.findPackageLogListByPackageid(packageid);
		mav.addObject("logList", logList);
		return mav;
	}
	
}
