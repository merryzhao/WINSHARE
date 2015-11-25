/*
 * @(#)PaymentService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.payment;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
public interface PaymentService {

    /**
     * 得到支付方式
     * 
     * @param id
     * @return
     */
    Payment get(Long id);

    /**
     * 修改支付方式
     * 
     * @param payment
     */
    void update(Payment payment);

    /**
     * 根据支付方式类型返回支付方式集合
     * 
     * @param type
     *            支付方式类型 Payment.TYPE_[a-z][A-Z]
     * @return
     */
    List<Payment> find(Long type);

    /**
     * 查询支付方式
     * 
     * @param onlyAvailable
     *            是否只返回有效的
     * @param onlyShow
     *            是否只返回前台展示的
     * @return
     */
    List<Payment> find(boolean onlyAvailable, boolean onlyShow);

    /**
     * 保存支付凭据
     * 
     * @param paymentCredential
     *            支付凭据
     */
    void save(PaymentCredential paymentCredential);

    /**
     * 检查支付凭据是否已存在，如果不存在则保存，否则不做处理（邮局汇款、银行转账、网银支付时检查）
     * 
     * @param paymentCredential
     * @return 不存在并保存返回true，已存在返回false
     */
    boolean checkAndSave(PaymentCredential paymentCredential);

    /**
     * 根据区域和配送方式获得支付方式<br/>
     * 
     * @param area
     *            区域
     * @param deliveryType
     *            配送方式
     * @return 结果集包括所有的有效且前台展示的支付方式，其中货到付款根据区域是否支持，现金支付根据是否是自提
     */
    List<Payment> find(Area area, DeliveryType deliveryType);

    /**
     * 根据指定的规则,返回是否应该过滤掉货到付款 <br/>
     * 具体怎样删除由上层处理(api,front...) 过滤货到付款的数据具体业务规则见issue,3414
     * 
     * @param customer
     *            结算用户
     * @return true:应该去掉货到付款,false:不需要去掉货到付款
     */
    boolean isFilter(Customer customer);

    /**
     * 根据指定的规则,返回是否应该过滤掉货到付款 <br/>
     * 具体怎样删除由上层处理(api,front...) 过滤货到付款的数据具体业务规则见issue,3414
     * 
     * @param customer
     *            结算用户
     * @return true:应该去掉货到付款,false:不需要去掉货到付款
     */
    boolean isFilter(Map<ProductSale, Integer> productMap);

    /**
     * 保存支付方式
     * 
     * @param payment
     */
    void save(Payment payment);

    /**
     * 查找可修改的支付方式
     * 
     * @param payment
     * @return
     */
    List<Payment> findCanChangePaymentList(Payment payment);
}
