package com.winxuan.ec.admin.controller.order;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.OrderInitException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryInfo;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.order.OrderUpdateLog;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.order.OrderInvoiceService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.order.OrderDeliveryInfo;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * @author  周斯礼
 * @version 2013-8-16
 */
@Controller
public class OrderEditController {

	private static final String ORDER_ID_STR = "orderId";
	
	private static final String SUCCESS_VIEW = "/order/success_result";
	
	private static final String MERCHANDISE="百货";	
	
	private static final String BOOK="图书";
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderInvoiceService orderInvoiceService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private OrderDeliveryInfo orderDeliveryInfo;
	
	/**
	 * 更新订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/edit", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response,
			@MyInject Employee operator) throws OrderInitException {
		String paramName = request.getParameter("paramName");
		String paramValue = request.getParameter("paramValue");
		String orderId = request.getParameter(ORDER_ID_STR);
		String orderPayment = request.getParameter("orderPayment");
		String payMent = request.getParameter("payMent");
		ModelAndView mav = new ModelAndView(SUCCESS_VIEW);
		mav.addObject(ControllerConstant.MESSAGE_KEY, null);
		Order order = orderService.get(orderId);
		if (("consignee-needInvoice").equals(paramName)) {
			try{
				consigneeNeedInvoice(operator, paramValue, request, mav, order);
			}catch(OrderInitException e){
				mav.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());	
				return mav;
			}
		} else if ("deliveryType".equals(paramName)) {// 修改配送方式
			Area oldDistrict = order.getConsignee().getDistrict();
			DeliveryInfo modifyDeliveryInfo = orderDeliveryInfo.isAreaSupportForEmployee(order.getConsignee().getDistrict(), Long.valueOf(paramValue),null,operator); 
			DeliveryInfo oldDeliveryInfo = orderDeliveryInfo.isAreaSupportForEmployee(oldDistrict, order.getDeliveryType().getId(),null,operator); 
			
			if(modifyDeliveryInfo == null){
				mav.addObject(ControllerConstant.MESSAGE_KEY,String.format("该区域:%s不支持此配送方式:%s 结果:修改失败!",
						oldDistrict.getName(),order.getDeliveryType().getId()));
				return mav;
			}
			if(modifyDeliveryInfo.getDeliveryFee(order.getListPrice())
					.compareTo(oldDeliveryInfo.getDeliveryFee(order.getListPrice())) != 0){
				mav.addObject(ControllerConstant.MESSAGE_KEY, "订单新旧区域运费不一致!  结果:修改失败!");
				return mav;
			}
			order.setDeliveryType(deliveryService.getDeliveryType(Long.valueOf(paramValue)));
		} else if (("consignee-deliveryOption").equals(paramName)) {// 修改送货时间要求
			order.getConsignee().setDeliveryOption(
					codeService.get(Long.valueOf(paramValue)));
		} else if ("consignee-invoiceType".equals(paramName)) {// 修改送货时间要求
			order.getConsignee().setInvoiceType(
					codeService.get(Long.valueOf(paramValue)));
		} else if (("consignee-area").equals(paramName)) {// 修改区域
			try {
				consigneeArea(request, mav, order, operator);
			} catch (NumberFormatException ne) {
				mav.addObject(ControllerConstant.MESSAGE_KEY, ne.getMessage());
				return mav;
			} catch (OrderInitException oe) {
				response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
				mav.addObject(ControllerConstant.MESSAGE_KEY, oe.getMessage());
				return mav;
			}
		} else if (("orderPayment").equals(paramName)) {//  修改支付方式
			for (OrderPayment op : order.getPaymentList()) {
				if (String.valueOf(op.getId()).equals(orderPayment)) {
					op.setPayment(paymentService.get(NumberUtils
							.createLong(payMent)));
					break;
				}
			}
		}else if("consignee-consignee".equals(paramName)){
			order.getConsignee().setConsignee(paramValue);
		}else if("consignee-phone".equals(paramName)){
			order.getConsignee().setPhone(paramValue);
		}else if("consignee-mobile".equals(paramName)){
			order.getConsignee().setMobile(paramValue);
		}else if("consignee-email".equals(paramName)){
			order.getConsignee().setEmail(paramValue);
		}else if("consignee-zipCode".equals(paramName)){
			order.getConsignee().setZipCode(paramValue);
		}else if("consignee-invoiceTitle".equals(paramName)){
			order.getConsignee().setInvoiceTitle(paramValue);
		}else if("consignee-address".equals(paramName)){
			order.getConsignee().setAddress(paramValue);
		}else if("consignee-remark".equals(paramName)){
			order.getConsignee().setRemark(paramValue);
		}else if("transfer".equals(paramName)){
		    boolean type = Boolean.valueOf(paramValue);
		    if(!order.getTransferResult().getId().equals(Code.EC2ERP_STATUS_NEW)){
    		    //设置订单下传中启
    		    if(type){
    		        order.setTransferResult(new Code(Code.EC2ERP_STATUS_NONE));
    		    } else {
    		        order.setTransferResult(new Code(Code.EC2ERP_STATUS_CANCEL));
    		    }
		    }
		}
		orderService.update(order, operator);
		return mav;
	}

	/**
	 * @param country
	 * @param province
	 * @param city
	 * @param district
	 * @param town
	 * @param mav
	 * @param order
	 * @throws NumberFormatException
	 * @throws OrderInitException 
	 */
	private void consigneeArea(HttpServletRequest request, ModelAndView mav, Order order, Employee operator)
			throws NumberFormatException, OrderInitException {
		String country = request.getParameter("country");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String town = request.getParameter("town");
		try {
			Area countryArea = areaService.get(Long.parseLong(country));
			Area proviceArea = areaService.get(Long.parseLong(province));
			Area cityArea = areaService.get(Long.parseLong(city));
			Area districtArea = areaService.get(Long.parseLong(district));
			Area townArea = areaService.get(Long.parseLong(town));
			Area oldArea = order.getConsignee().getTown();
			
			if(townArea == null){
				throw new OrderInitException(order, "修改后的乡镇不能为空   结果:修改失败!");
			}
			//如果下单前没有乡镇一级的信息，就用原来的区县信息
			if(oldArea == null){
				oldArea = order.getConsignee().getDistrict();
			}
			DeliveryInfo modifyDeliveryInfo = orderDeliveryInfo.isAreaSupportForEmployee(townArea, order.getDeliveryType().getId(),null,operator); 
			DeliveryInfo oldDeliveryInfo = orderDeliveryInfo.isAreaSupportForEmployee(oldArea, order.getDeliveryType().getId(),null,operator); 
			if(modifyDeliveryInfo == null){
				throw new OrderInitException(order, String.format("该区域:%s不支持此配送方式:%s 结果:修改失败!",
						townArea.getName(),order.getDeliveryType().getId()));
			}
			//比较新区域下的运费和旧区域的运费，如果不相等，则返回
			if(modifyDeliveryInfo.getDeliveryFee(order.getListPrice())
					.compareTo(oldDeliveryInfo.getDeliveryFee(order.getListPrice())) != 0){
				throw new OrderInitException(order, "订单新旧区域运费不一致!  结果:修改失败!");
			}
			order.getConsignee().setCountry(countryArea);
			order.getConsignee().setProvince(proviceArea);
			order.getConsignee().setCity(cityArea);
			order.getConsignee().setDistrict(districtArea);
			order.getConsignee().setTown(townArea);
		} catch (NumberFormatException ne) {
			throw ne;
		} catch (OrderInitException oe){
			throw oe;
		}
		
		
	}

	/**
	 * @param operator
	 * @param paramValue
	 * @param mav
	 * @param order
	 * @throws NumberFormatException
	 * @throws OrderInitException 
	 */
	private void consigneeNeedInvoice(Employee operator, String paramValue,
			HttpServletRequest request, ModelAndView mav,Order order)
			throws NumberFormatException, OrderInitException {
		String invoiceTitleType=request.getParameter("invoiceTitleType");
		String invoiceTitle=request.getParameter("invoiceTitle");
		final String trueValue = "1";
		final String falseValue = "0";
		if (paramValue.equals(falseValue)) {
			order.getConsignee().setNeedInvoice(false);
			order.addUpdateLog(new OrderUpdateLog(operator, order,
					"是否需要发票", "需要发票", "不需要发票"));
			if(order.getInvoiceList() != null && !order.getInvoiceList().isEmpty()){
				OrderInvoice oi = order.getInvoiceList().iterator().next();
				order.getInvoiceList().remove(oi);
				orderInvoiceService.remove(oi);
			}
		} else if (paramValue.equals(trueValue)) {
			order.getConsignee().setNeedInvoice(true);
			Set<OrderInvoice> invoices = order.getInvoiceList();
			if(StringUtils.isBlank(invoiceTitle)){
				invoiceTitle = order.getConsignee().getConsignee();
			}
			if(StringUtils.isBlank(invoiceTitleType)){
				throw new OrderInitException(order, "发票类型为空,不能保存发票！");
			}
			Long invoiceTypeId = Code.INVOICE_TITLE_TYPE_COMPANY.equals(Long.valueOf(invoiceTitleType)) ? 
					Code.INVOICE_TITLE_TYPE_COMPANY : Code.INVOICE_TITLE_TYPE_PERSONAL;
			Code type = new Code(invoiceTypeId);
			
			order.getConsignee().setInvoiceTitle(invoiceTitle);
			order.getConsignee().setInvoiceTitleType(type);
			order.getConsignee().setInvoiceType(new Code(Code.INVOICE_TYPE_GENERAL));
			OrderInvoice invoice = null;
			if(invoices == null || invoices.isEmpty()){
				invoice = order.getInvoice(order.isExistMerchandiseItem()?MERCHANDISE:BOOK, new Code(Code.ORDER_INVOICE_MODE_NORMAL), order.getInvoiceMoney());
				invoice.setTransferred(true);
				invoice.setTitle(invoiceTitle);
				invoice.setTitleType(type);
				invoice.setOperator(operator);
				order.addInvoice(invoice);
			}else{
				if(invoices.size() > MagicNumber.ONE){
					throw new OrderInitException(order, "此订单已有多张发票，不支持修改发票信息的操作！");
				}
				invoice = invoices.iterator().next();
				invoice.setTitle(invoiceTitle);
				invoice.setTitleType(type);
				invoice.setOperator(operator);
			}
		}
	}
}


