/*
 * @(#)PaymentController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.impl.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.front.controller.Constant;
import com.winxuan.ec.front.service.ip.IpService;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.customer.CustomerPayPasswordService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.util.BigDecimalUtils;
import com.winxuan.framework.util.web.CookieUtils;

/**
 * 支付方式
 * 
 * @author HideHai
 * @version 1.0,2011-8-8
 */
@Controller
@RequestMapping(value = "/customer/payment")
public class PaymentController {
	
	private static final Long BANK_TRANSFER = 4L;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private CustomerPayPasswordService customerPayPasswordService;
    @Autowired
    private IpService ipService;
    @Autowired
    private ShoppingcartService shoppingcartService;
    

    /**
     * 根据区域和配送方式获取订单支持的支付方式
     * 
     * @param deliveryId
     * @param areaId
     * @return
     */
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public ModelAndView checkout(@MyInject Customer customer,
                                 @RequestParam(value = "deliveryTypeId", required = true) Long deliveryTypeId,
                                 @RequestParam(value = "areaId", required = true) Long areaId,
                                 @RequestParam(value = "shopId", required = true) Long shopId,
                                 HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView("/customer/payment/checkout");

        modelAndView.addObject("useAccount", false);
        Area area = areaService.get(areaId);
        DeliveryType deliveryType = deliveryService.getDeliveryType(deliveryTypeId);
        if (null != area && null != deliveryType) {
        	List<Payment> payments = paymentService.find(area, deliveryType);
        	//订单中任意商品复本量≥10，支付方式取消“银行转账”选项
        	Shoppingcart sc = shoppingcartService.findShoppingcartByCustomer(customer);
        	for (ShoppingcartItem item : sc.getItemList()) {
        		if(item.getQuantity() >= 10){
        			List<Payment> payments2 = new ArrayList<Payment>();
        			for (Payment payment : payments) {
        				if(!payment.getId().equals(BANK_TRANSFER)){
        					payments2.add(payment);
        				}
					}
        			payments = payments2;
        			break;
        		}
			}
            CustomerAccount account = customer.getAccount();
            modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
            if (account != null && account.getBalance().compareTo(BigDecimalUtils.ZERO) > 0) {
                modelAndView.addObject("useAccount", true);
                modelAndView.addObject("balance", account.getBalance());
            }
            if (payments != null && !payments.isEmpty()) {
                if (!shopId.equals(Shop.WINXUAN_SHOP)) {
                    Payment codPay = paymentService.get(Payment.COD);
                    if (codPay != null) {
                        payments.remove(codPay);
                    }
                }
            }
            Map<ProductSale, Integer> productMap = this.productMap();
            boolean isFilter = false;
            if (MapUtils.isNotEmpty(productMap)) {
                isFilter = paymentService.isFilter(productMap);
            } else {
                isFilter = paymentService.isFilter(customer);
            }

            if (isFilter) {
                payments.remove(paymentService.get(Payment.COD));
                modelAndView.addObject("isFilter", true);
            }
            modelAndView.addObject("payment", payments);
        } else {
            modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
        }
        return modelAndView;
    }

    private Map<ProductSale, Integer> productMap() {
        Cookie cookie = CookieUtils.getCookie(Constant.COOKIE_CLIENT_NAME);
        Shoppingcart shoppingcart = shoppingcartService.get(cookie.getValue());
        Set<ShoppingcartItem> shoppingCartItem = shoppingcart.getItemList();
        Map<ProductSale, Integer> productMap = new HashMap<ProductSale, Integer>();
        for (ShoppingcartItem item : shoppingCartItem) {
            productMap.put(item.getProductSale(), item.getQuantity());
        }
        return productMap;

    }

    /**
     * 安全性支付相关信息
     * 
     * @param customer
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView checkout(@MyInject Customer customer, HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView("/customer/payment/info");
        boolean hasPayPasspword = customerPayPasswordService.hasPayPassword(customer);
        int emailStatus = 0;
        if (!ipService.isChinaIp(httpServletRequest.getRemoteAddr())) {

            EmailValidator validator = new EmailValidator();
            String email = customer.getPayEmail();
            if (StringUtils.isBlank(email) || !validator.isValid(email, null)) {
                email = null;
                emailStatus = MagicNumber.NEGATIVE_ONE;
                //				emailMsg = "您还未设置邮箱，请先设置邮箱";
            } else {
                emailStatus = MagicNumber.ONE;
                //				emailMsg = "您已经设置过支付邮箱";
            }
            modelAndView.addObject("payEmail", email);
        } else {
            emailStatus = 0;
            //			emailMsg = "国内用户请使用手机获取验证码";
        }
        modelAndView.addObject("hasPayPassword", hasPayPasspword);
        modelAndView.addObject("payEmailStatus", emailStatus);

        return modelAndView;
    }

}
