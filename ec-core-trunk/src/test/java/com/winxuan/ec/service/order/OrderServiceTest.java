package com.winxuan.ec.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.winxuan.ec.exception.OrderInitException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.BaseTest;
import com.winxuan.ec.service.BeansUtil;
import com.winxuan.ec.service.channel.ChannelBeans;
import com.winxuan.ec.service.customer.CustomerBeans;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司, v1.0 by heyadong,v2.0 by cast911
 * @lastupdateTime:2013-8-21 上午10:59:22 --!
 * @description:托管到spring,是便于外部直接获取该bean.制造订单.,可以参看各set方法
 ******************************** 
 */
@Component("orderServiceTest")
public class OrderServiceTest extends BaseTest {

    private static final Long TEST_PRODUCT_SALE_ID = 10000062L;

    //1682936950L
    private static final Long DEFAULT_USER = 1682939418L;

    private static final Log LOG = LogFactory.getLog(OrderServiceTest.class);

    private boolean isRandomCustomer = false;

    private boolean useCustomerAccount = true;

    private boolean isNeedInvoice = true;

    private int purchaseQuantity = 1;

    private List<ProductSale> purchaseList;

    private Channel channel = ChannelBeans.createChannelEC();

    /**
     * @see setTown
     */
    private Area town = null;

    @Test
    public void testCreateOrder() throws Exception {
        this.setUseCustomerAccount(false);
        this.createOrder();
    }

    public Area getTown() {
        return town;
    }

    /**
     * 设置区域信息最末级节点<br/>
     * 例: select * from area as a where a.path like "%22.23.6121.165%" order by
     * LEVEL desc limit 1
     */
    public void setTown(Area town) {
        this.town = town;
    }

    /**
     * 设置区域信息最末级节点<br/>
     * 例: select * from area as a where a.path like "%22.23.6121.165%" order by
     * LEVEL desc limit 1
     */
    public void setTown(Long id) {
        Area area = services.areaService.get(id);
        this.town = area;
    }

    public boolean isRandomCustomer() {
        return isRandomCustomer;
    }

    /**
     * 随机用户
     */
    public void setRandomCustomer(boolean isRandomCustomer) {
        this.isRandomCustomer = isRandomCustomer;
    }

    public boolean isUseCustomerAccount() {
        return useCustomerAccount;
    }

    /**
     * 使用暂存款
     */
    public void setUseCustomerAccount(boolean useCustomerAccount) {
        this.useCustomerAccount = useCustomerAccount;
    }

    public boolean isNeedInvoice() {
        return isNeedInvoice;
    }

    /**
     * 是否需要发票
     */
    public void setNeedInvoice(boolean isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public List<ProductSale> getPurchaseList() {
        return purchaseList;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    /**
     * 购买数量,所有的商品使用同一数量
     */
    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Channel getChannel() {
        return channel;
    }

    /**
     * 渠道.默认为ec直销
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * 没有指定商品 则添加默认商品
     * 
     * @see OrderBeans
     * @return
     */
    public List<ProductSale> getEffectiveProductSale() {
        if (CollectionUtils.isEmpty(this.purchaseList)) {
            this.addPurchaseProduct(TEST_PRODUCT_SALE_ID);
        }
        return this.getPurchaseList();

    }

    /**
     * 购买的商品项
     */
    public void setPurchaseList(List<ProductSale> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public void addPurchaseProduct(ProductSale productSale) {
        Assert.isTrue((productSale != null), productSale.getId() + "不存在");
        if (CollectionUtils.isEmpty(this.purchaseList)) {
            this.purchaseList = new ArrayList<ProductSale>();
        }
        this.purchaseList.add(productSale);
    }

    /**
     * 添加商品
     * 
     * @param id
     */
    public void addPurchaseProduct(Long id) {
        ProductSale ps = services.productService.getProductSale(id);
        this.addPurchaseProduct(ps);
    }

    /**
     * 下单的区域信息是根据省级节点,随机找到最末级区域
     * 
     * @return
     * @throws Exception
     */
    public Order createOrder() throws Exception {

        // 暂存款 支付
        Payment payment = services.paymentService.get(Payment.ACCOUNT);

        // 用户暂存款余额
        BigDecimal customerBalance = new BigDecimal("10000");

        // 添加暂存款的阀值
        BigDecimal balanceThreshold = new BigDecimal("100");

        Customer customer = null;

        boolean flag = CollectionUtils.isNotEmpty(this.getEffectiveProductSale());
        Assert.isTrue(flag, "没有商品信息");

        if (this.isRandomCustomer) {
            customer = CustomerBeans.createCustomer();
            services.customerService.register(customer);
        } else {
            customer = services.customerService.get(DEFAULT_USER);
        }

        if (customer.getAccount().getBalance().compareTo(balanceThreshold) < 0) {
            customer.getAccount().setBalance(customerBalance);
        }
        services.customerService.update(customer);

        Order order = orderBeans.createOrder();
        order.setCreateTime(new Date());
        order.setCreator(customer);
        order.setCustomer(customer);

        OrderConsignee orderConsignee = orderBeans.createOrderConsignee(town);
        orderConsignee.setOrder(order);
        order.setConsignee(orderConsignee);
        order.setChannel(this.getChannel());

        this.createOrderItem(order);
        this.sumOrderSalePrice(order);

        order.setDeliveryFee(order.getShop().getDeliveryFee());
        
        OrderPayment orderPayment = orderBeans.createOrderPayment();
        orderPayment.setPayment(payment);
        orderPayment.setPayMoney(order.getTotalPayMoney());
        orderPayment.setOrder(order);
        orderPayment.setPayment(payment);
        order.setPaymentList(BeansUtil.createSet(orderPayment));
        
       
        
        
        if (this.isNeedInvoice) {
            this.saveInvoice(order, customer);
        }
        services.orderService.create(order);
        // 暂存款支付
        if (this.useCustomerAccount) {
            List<Order> orderList = new ArrayList<Order>();
            orderList.add(order);
            services.orderService.payByAccount(orderList, customer, customer);
        }

        LOG.info("order:" + order.getId());
        return order;
    }

    /**
     * 创建商品订单项
     * 
     * @param order
     */
    private void createOrderItem(Order order) {
        for (ProductSale productSale : this.getEffectiveProductSale()) {
            boolean flag = productSale.getAvalibleQuantity() > 0;
            Assert.isTrue(flag, productSale.getId() + ":商品可用量需要大于0");
            BigDecimal itemListPrice = productSale.getListPrice();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setShop(productSale.getShop());
            orderItem.setPurchaseQuantity(purchaseQuantity);
            orderItem.setProductSale(productSale);
            orderItem.setListPrice(itemListPrice);
            orderItem.setSalePrice(productSale.getSalePrice());
            order.addItem(orderItem);
            order.setShop(orderItem.getShop());
        }
    }


    /**
     * 保存发票信息.
     * 
     * @param order
     * @param customer
     * @throws OrderStatusException
     */
    private void saveInvoice(Order order, Customer customer) throws OrderStatusException {
        order.getConsignee().setNeedInvoice(true);
        order.getConsignee().setInvoiceTitle("发票测试" + System.currentTimeMillis());
        order.getConsignee().setInvoiceTitleType(new Code(Code.INVOICE_TITLE_TYPE_COMPANY));
        order.getConsignee().setInvoiceType(new Code(Code.INVOICE_TYPE_GENERAL));
        OrderInvoice orderInvoice = orderBeans.createOrderInvoice(order);
        orderInvoice.setStatus(new Code(Code.INVOICE_STATUS_NORMAL));
        order.addInvoice(orderInvoice);
        //services.orderInvoiceService.save(orderInvoice);
    }

    private void sumOrderSalePrice(Order order) throws OrderInitException {
        BigDecimal listPrice = BigDecimal.ZERO;
        BigDecimal salePrice = BigDecimal.ZERO;
        int purchaseQuantity = 0;
        Code storageType = null;
        boolean virtual = false;
        Shop shop = null;
        Set<OrderItem> orderItems = order.getItemList();
        if (orderItems == null || orderItems.size() == 0) {
            throw new OrderInitException(order, " 订单项为空");
        }
        for (OrderItem orderItem : orderItems) {
            ProductSale productSale = orderItem.getProductSale();
            int itemPurchaseQuantity = orderItem.getPurchaseQuantity();
            if (itemPurchaseQuantity <= 0) {
                throw new OrderInitException(order, productSale + " 订购数量小于等于0");
            }
            purchaseQuantity += itemPurchaseQuantity;
            listPrice = listPrice.add(orderItem.getTotalListPrice());
            salePrice = salePrice.add(orderItem.getTotalSalePrice());
            if (storageType == null) {
                storageType = productSale.getStorageType();
                virtual = productSale.getProduct().isVirtual();
                shop = productSale.getShop();
            } else {
                if (!storageType.equals(productSale.getStorageType())) {
                    throw new OrderInitException(order, productSale + " 储配方式不一致");
                }
                if (virtual != productSale.getProduct().isVirtual()) {
                    throw new OrderInitException(order, productSale + " 虚拟类型不一致");
                }
                if (!shop.equals(productSale.getShop())) {
                    throw new OrderInitException(order, productSale + " 所属卖家不一致");
                }
            }
        }
        order.setPurchaseQuantity(purchaseQuantity);
        order.setPurchaseKind(orderItems.size());
        order.setListPrice(listPrice);
        order.setSalePrice(salePrice);
        order.setStorageType(storageType);
    }

}
