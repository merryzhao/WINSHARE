/*
 * @(#)PaymentServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.PaymentCredentialDao;
import com.winxuan.ec.dao.PaymentDao;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * 支付服务接口实现类
 * 
 * @author wumaojie
 * @version 1.0,2011-7-18
 */
@Service("paymentService")
@Transactional(rollbackFor = Exception.class)
public class PaymentServiceImpl implements PaymentService, Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8995683635183691056L;

    private static final BigDecimal DEFAULT_LISTPRICE_THRESHOLD = new BigDecimal(400);

    private static final int DEFALUT_PRODUCT_COUNT = 8;

    private static final Log LOG = LogFactory.getLog(PaymentServiceImpl.class);

    /**
     * 是否取消掉过滤
     * 在一定时间后,该功能会取消掉
     * 最初始的值是false.为true的时候,该功能表示已经没有使用了,但是不排除后续会开启
     * 
     */
    private static final Boolean IS_CANCEL = true;

    @InjectDao
    private PaymentDao paymentDao;

    @InjectDao
    private PaymentCredentialDao paymentCredentialDao;

    @Autowired
    private ShoppingcartService shoppingcartService;

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Payment get(Long id) {
        return paymentDao.get(id);
    }

    public void update(Payment payment) {
        paymentDao.update(payment);
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Payment> find(boolean onlyAvailable, boolean onlyShow) {
        if (onlyAvailable) {
            if (onlyShow) {
                return paymentDao.find(onlyAvailable, onlyShow);
            }
            return paymentDao.find(onlyAvailable, null);

        } else {
            if (onlyShow) {
                return paymentDao.find(null, onlyShow);
            }
            return paymentDao.find(null, null);
        }

    }

    public void save(PaymentCredential paymentCredential) {
        paymentCredentialDao.save(paymentCredential);
    }

    @Override
    public boolean checkAndSave(PaymentCredential paymentCredential) {
        Long paymentType = paymentCredential.getPayment().getType().getId();
        if ((paymentType.equals(Code.PAYMENT_TYPE_OFFLINE) || paymentType.equals(Code.PAYMENT_TYPE_ONLINE))
                && paymentCredentialDao.isExisted(paymentCredential.getPayment(), paymentCredential.getOuterId())) {
            return false;
        }
        save(paymentCredential);
        return true;
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Payment> find(Area area, DeliveryType deliveryType) {
        // 是否支持货到付款
        boolean isSupportcod = area.isSupportCod();
        long deliveryTypeId = deliveryType.getId();
        // 是否自提
        boolean isPickUpByCustomer = deliveryTypeId == DeliveryType.DIY;

        List<Payment> payments = paymentDao.find(true, true);
        // 如果不是货到付款 
        if (!isSupportcod || deliveryTypeId != DeliveryType.EXPRESS) {
            payments.remove(paymentDao.get(Payment.COD));
        }
        // 如果不是自提
        if (!isPickUpByCustomer) {
            payments.remove(paymentDao.get(Payment.CASH));
        }
        return payments;
    }

    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Payment> find(Long type) {
        return paymentDao.find(type);
    }

    public void save(Payment payment) {
        paymentDao.save(payment);
    }

    @Override
    @Read
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Payment> findCanChangePaymentList(Payment payment) {
        List<Payment> allPaymentList = paymentDao.find(Boolean.TRUE, null);
        Long typeId = payment.getType().getId();
        List<Payment> canChangePaymentList = new ArrayList<Payment>();
        for (Payment comparePayment : allPaymentList) {
            Long compareTypeId = comparePayment.getType().getId();
            if (typeId.equals(Code.PAYMENT_TYPE_COD)) {
                if (compareTypeId.equals(Code.PAYMENT_TYPE_COD)) {
                    canChangePaymentList.add(comparePayment);
                }
            } else if (typeId.equals(Code.PAYMENT_TYPE_OFFLINE) || typeId.equals(Code.PAYMENT_TYPE_ONLINE)) {
                if (compareTypeId.equals(Code.PAYMENT_TYPE_OFFLINE) || compareTypeId.equals(Code.PAYMENT_TYPE_ONLINE)) {
                    canChangePaymentList.add(comparePayment);
                }
            }
        }
        if (canChangePaymentList == null || canChangePaymentList.size() == 0) {
            return null;
        } else if (canChangePaymentList.size() == 1) {
            if (canChangePaymentList.get(0).getId().equals(payment.getId())) {
                return null;
            } else {
                return canChangePaymentList;
            }
        } else {
            return canChangePaymentList;
        }
    }

    @Override
    public boolean isFilter(Customer customer) {
        Shoppingcart shoppingcart = shoppingcartService.findShoppingcartByCustomer(customer);
        if (shoppingcart == null) {
            LOG.error("无法获取购物车 " + customer);
            return true;
        }
        Set<ShoppingcartItem> shoppingCartItem = shoppingcart.getItemList();
        Map<ProductSale, Integer> productMap = new HashMap<ProductSale, Integer>();
        for (ShoppingcartItem item : shoppingCartItem) {
            productMap.put(item.getProductSale(), item.getQuantity());
        }
        return this.isFilter(productMap);
    }

    @Override
    public boolean isFilter(Map<ProductSale, Integer> productMap) {
        if (IS_CANCEL) {
            return false;
        }
        else{
            int quantity = 0;
            BigDecimal listPriceAmount = BigDecimal.ZERO;
            Set<ProductSale> productSale = productMap.keySet();
            for (ProductSale ps : productSale) {
                BigDecimal listPrice = ps.getListPrice();
                int getQuantity = productMap.get(ps);
                BigDecimal priceCount = listPrice.multiply(new BigDecimal(getQuantity));
                listPriceAmount = listPriceAmount.add(priceCount);
                quantity += getQuantity;
            }
            boolean isPassPrice = listPriceAmount.compareTo(DEFAULT_LISTPRICE_THRESHOLD) >= 0 ? true : false;
            boolean isPassQuantity = quantity >= DEFALUT_PRODUCT_COUNT;
            return isPassQuantity || isPassPrice;
        }
    }
}
