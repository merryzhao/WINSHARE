package com.winxuan.ec.service.order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderDeliverySplit;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.ParentOrder;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.service.message.SmsMessageService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.framework.sms.Constant;
import com.winxuan.framework.sms.SMSMessage;
import com.winxuan.framework.sms.SMSService;

/**
 * 订单短信服务. 短信内容包含了逻辑并且短信内容需要 备案 短信内容经过精密修改,切忽随意调整
 * 
 * @author heyadong
 * @version 1.0, 2012-6-8
 */
@Service("orderMessageService")
public class OrderMessageServiceImpl implements OrderMessageService {
	public static final Map<String, String> TAGS = new HashMap<String, String>();

	private static final Log LOG = LogFactory
			.getLog(OrderMessageServiceImpl.class);

	// 短信LOGO
	private static final String KEYWORD_LOGO = "@logo@";
	// 订单号
	private static final String KEYWORD_ORDER = "@order@";
	// 外部单号
	private static final String KEYWORD_OUTERID = "@outerid@";
	// 快递编码
	private static final String KEYWORD_DELIVERY_CODE = "@delivery_code@";
	// 快递类型
	private static final String KEYWORD_DELIVERY_TYPE = "@delivery_type@";
	// 渠道名
	private static final String KEYWORD_CHANNEL = "@channel@";
	// 缺货商品提示信息
	private static final String KEYWORD_OUTOFSTOCK_PRODUCT_INFO = "@product_info@";
	// 缺货商品总数
	private static final String KEYWORD_OUTOFSTOCK_TOTAL = "@number@";
	// 缺货商品种类数
	private static final String KEYWORD_OUTOFSTOCK_KIND = "@kind@";
	// 快递公司
	private static final String KEYWORD_DELIVERY_COMPANY = "@delivery_company@";
	// 应付金额
	private static final String KEYWORD_REQUI_PAY_MONEY = "@money@";
	// 分包订单相关参数——运单号,分包订单的运单号有多个
	private static final String KEYWORD_SPLIT_DELIVERY_CODE = "@split_delivery_code@";
	// 分包订单相关参数——快递公司,一个分包订单可能对应若干不同的快递公司
	private static final String KEYWORD_SPLIT_DELIVERY_COMPANY = "@split_delivery_company@";
	private static final String KEYWORD_SPLIT_NUMBER = "@split_delivery_number@";
	private static final String KEYWORD_PACKAGE_INFO = "@split_package_info@";
	private static final String KEYWORD_MULTI_BIN = "@split_multi_bin@";

	// 根据短信解析后.找到的短信.如果为NULL,刚该订单不发送短信
	private static final String KEYWORD_SMS_MESSAGE = "@sms_message@";

	private static final int PRODUCT_NAME_LIMIT = 6;
	private static final String PRODUCT_SUFFIX = "...";

	// 缺货种类限制
	private static final int OUTOFSTOCK_KIND_LIMIT = 2;
	static {
		TAGS.put("短信LOGO", KEYWORD_LOGO);
		TAGS.put("订单号", KEYWORD_ORDER);
		TAGS.put("外部单号", KEYWORD_OUTERID);
		TAGS.put("快递编码", KEYWORD_DELIVERY_CODE);
		TAGS.put("快递类型", KEYWORD_DELIVERY_TYPE);
		TAGS.put("渠道名称", KEYWORD_CHANNEL);
		TAGS.put("缺货商品信息", KEYWORD_OUTOFSTOCK_PRODUCT_INFO);
		TAGS.put("缺货商品总数", KEYWORD_OUTOFSTOCK_TOTAL);
		TAGS.put("缺货商品总类数量", KEYWORD_OUTOFSTOCK_KIND);
		TAGS.put("快递公司名称", KEYWORD_DELIVERY_COMPANY);
		TAGS.put("还应付金额", KEYWORD_REQUI_PAY_MONEY);
		TAGS.put("分包订单运单号", KEYWORD_SPLIT_DELIVERY_CODE);
		TAGS.put("分包订单快递公司", KEYWORD_SPLIT_DELIVERY_COMPANY);
		TAGS.put("分包订单包裹数量", KEYWORD_SPLIT_NUMBER);
		TAGS.put("分包订单包裹信息", KEYWORD_PACKAGE_INFO);
		TAGS.put("分仓订单发货仓库信息",KEYWORD_MULTI_BIN);
	}

	private static final int HOUR_RANGE_START = 12;
	private static final int HOUR_RANGE_END = 21;

	@Autowired
	private SmsMessageService smsMessageService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ParentOrderService parentOrderService;
	@Autowired
	private SMSService smsService;

	// 缩写商品名. 如书名:一二三四五六七八, 返回 一二三四五六...(1本)
	private String reduceProductName(String productName, int num) {
		if (productName == null) {
			return "";
		} else {
			return productName.length() <= PRODUCT_NAME_LIMIT ? productName
					+ String.format("(%s本)", num) : productName.substring(0,
					PRODUCT_NAME_LIMIT)
					+ String.format("%s(%s本)", PRODUCT_SUFFIX, num);
		}

	}

	/**
	 * 短信发送时间. 12:00 ~ 21:00 即时发送,时间为null 其他时间都推至当天或者次日的中午12点。
	 * 
	 * @return
	 */
	private Date createSendDate() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		// (0:00~12:00) 则设置为.当天中午12点
		if (hour < HOUR_RANGE_START) {
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			calendar.set(Calendar.MINUTE, 0);
			return calendar.getTime();
		}
		// (21:00 ~ 00:00)
		else if (hour >= HOUR_RANGE_END) {
			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			calendar.set(Calendar.MINUTE, 0);
			return calendar.getTime();
		} else {
			return null;
		}
	}

	private String logo(ParentOrder parentOrder) {
		if(parentOrder.getChannel().equals(Channel.TAOBAO_WINSHARE)|| parentOrder.getChannel().equals(Channel.TAOBAO_MANAGER)
				|| parentOrder.getChannel().equals(Channel.TAOBAO_MEDICINE)){
			return "【文轩网淘宝店】";
		}
		else if (parentOrder.getChannel().equals(Channel.PAIPAI_WINSHARE)){
			return "【新华文轩官方旗舰店】";
		}
		else if(parentOrder.getChannel().equals(Channel.QQ_CHANNEL)){
			return "【新华文轩旗舰店】";
		}
		else {
			return "【新华文轩网络书店】";
		}
	}
	
	//针对所有渠道的订单特定短信发送
	private Map<String, Object> parseOrder(Long typeId, ParentOrder parentOrder){
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProductSale> items = new ArrayList<ProductSale>();
		Map<ProductSale,Integer> outOfStocks = new HashMap<ProductSale,Integer>();
		Set<Order> subOrders = new HashSet<Order>();
		if (parentOrder.getOrderList() != null){
			subOrders = parentOrder.getOrderList();
		}
		Set<OrderDeliverySplit> orderDeliverySplits = new HashSet<OrderDeliverySplit>();
		List<String> packages = new ArrayList<String>();
		List<String> deliveryCompanys = new ArrayList<String>();
		List<String> codes = new ArrayList<String>();
		int outOfStockKind = 0;
		int outOfStockTotal = 0;
		// 记录前两种缺货商品.根据缺货商品种类数发送不同的短信
		for(Order subOrder : subOrders){
			for (OrderItem item : subOrder.getItemList()) {
				if (!item.isVirtual()
						&& item.getDeliveryQuantity() < item.getPurchaseQuantity()) {
					outOfStockTotal += item.getPurchaseQuantity()
							- item.getDeliveryQuantity();
					//考察是否已经在缺货商品种类类表中
					if (outOfStocks.containsKey(item.getProductSale())){
						int originalOut = outOfStocks.get(item.getProductSale()).intValue();
						int newOut = originalOut + (item.getPurchaseQuantity() - item.getDeliveryQuantity());
						outOfStocks.put(item.getProductSale(), new Integer(newOut));
					}
					else{
						outOfStockKind += 1;
						outOfStocks.put(item.getProductSale(), new Integer(item.getPurchaseQuantity()-item.getDeliveryQuantity()));
						items.add(item.getProductSale());
					}
				}
			}
		}
		String lessProductOutOfStorckMsg = null;
		if (outOfStockKind > 0 && outOfStockKind <= OUTOFSTOCK_KIND_LIMIT) {
			StringBuilder sb = new StringBuilder();
			for (ProductSale productSale : items) {
				String productName = productSale.getProduct().getName();
				sb.append(reduceProductName(productName,
						outOfStocks.get(productSale).intValue()));
			}
			lessProductOutOfStorckMsg = sb.toString();
		}
		map.put(KEYWORD_ORDER, parentOrder.getId());
		map.put(KEYWORD_OUTERID, parentOrder.getOuterId() != null ? parentOrder.getOuterId() : "");
		map.put(KEYWORD_LOGO, logo(parentOrder));
		String msg = smsMessageService.getMessage(typeId, parentOrder, outOfStockKind);
		if(msg == null){
			return null;
		}
		List<String> destDcs = new ArrayList<String>();
		for(Order subOrder : subOrders){
			//子订单已发货或者部分发货，才有分包消息
			if(subOrder.isDeliveried()){
				Set<OrderDeliverySplit> subOrderDeliverySplits = subOrder.getDeliverySplits();
				orderDeliverySplits.addAll(subOrderDeliverySplits);
				//将短信中的仓储地点转换为详细的文字
				String destDc = subOrder.getDistributionCenter().getDcDest().getDescription();
				if(!destDcs.contains(destDc)){
					destDcs.add(destDc);
				}
			}
		}
		map.put(KEYWORD_MULTI_BIN, StringUtils.join(destDcs.toArray(),","));
		//由于全渠道的所有订单都会有一个父订单，当父订单的状态为已发货或者部分发货时，就会有发货公司和运单号，如果有分包
		if(parentOrderService.getCurrentStatus(parentOrder) == 2){
			if(!CollectionUtils.isEmpty(orderDeliverySplits)){
				int splitNumber = orderDeliverySplits.size();
				map.put(KEYWORD_SPLIT_NUMBER, splitNumber);
				for(OrderDeliverySplit orderDeliverySplit : orderDeliverySplits){
					String deliveryCompany = orderDeliverySplit.getCompany().getCompany();
					if(!deliveryCompanys.contains(deliveryCompany)){
						deliveryCompanys.add(deliveryCompany);
					}
					String deliveryCode = orderDeliverySplit.getCode();
					if(!codes.contains(deliveryCode)){
						codes.add(deliveryCode);
					}
					String packageinfo = "快递公司：" + (deliveryCompany != null ? deliveryCompany : "") + ",运单号：" 
										+ (deliveryCode != null ? deliveryCode : "");
					packages.add(packageinfo);
				}
				map.put(KEYWORD_PACKAGE_INFO, StringUtils.join(packages.toArray(),","));
				map.put(KEYWORD_SPLIT_DELIVERY_CODE, StringUtils.join(codes.toArray(),","));
				map.put(KEYWORD_SPLIT_DELIVERY_COMPANY, deliveryCompanys != null ? StringUtils.join(deliveryCompanys.toArray(), ",")
						: ""); 
			}
			//如果无分包
			else{
				int splitNumber = subOrders.size();
				map.put(KEYWORD_SPLIT_NUMBER, splitNumber);
				for(Order subOrder : subOrders){
					//如果子订单的状态为取消，则没有发货公司和运单号
					if(subOrder.isDeliveried()){
						String deliveryCompany = subOrder.getDeliveryCompany().getCompany();
						deliveryCompanys.add(deliveryCompany);
						String deliveryCode = subOrder.getDeliveryCode();
						codes.add(deliveryCode);
						String packageinfo = "快递公司：" + (deliveryCompany != null ? deliveryCompany : "") + ",运单号：" 
											+ (deliveryCode != null ? deliveryCode : "");
						packages.add(packageinfo);
					}
				}
				map.put(KEYWORD_PACKAGE_INFO, StringUtils.join(packages.toArray(),","));
				map.put(KEYWORD_SPLIT_DELIVERY_CODE, StringUtils.join(codes.toArray(),","));
				map.put(KEYWORD_SPLIT_DELIVERY_COMPANY, deliveryCompanys != null ? StringUtils.join(deliveryCompanys.toArray(), ",")
						: ""); 
			}
		}
		String channelAlias = parentOrder.getChannel().getAlias(); 
		map.put(KEYWORD_CHANNEL, channelAlias != null ? channelAlias : "");
		map.put(KEYWORD_OUTOFSTOCK_PRODUCT_INFO, 
				lessProductOutOfStorckMsg != null ? lessProductOutOfStorckMsg : "");
		map.put(KEYWORD_OUTOFSTOCK_TOTAL, outOfStockTotal);
		map.put(KEYWORD_OUTOFSTOCK_KIND, outOfStockKind);
		map.put(KEYWORD_SMS_MESSAGE, msg);
		return map;
	}

	/**
	 * 发送短信 订单需要符号 短信规则. 若不符合规则,则不发送短信
	 * 
	 * @param type
	 *            短信类型
	 * @param order
	 *            订单
	 */
	private void sendMessage(Long type, ParentOrder parentOrder) {
		Map<String, Object> msgKeyword = new HashMap<String, Object>();
		msgKeyword = parseOrder(type, parentOrder);
		Set<Order> subOrders = new HashSet<Order>();
		if (parentOrder.getOrderList() != null){
			subOrders = parentOrder.getOrderList();
		}
		String phones = null;
		for(Order subOrder : subOrders){
			phones = subOrder.getConsignee().getMobile();
		}
		//如果parseOrder方法返回的对象为null，则停止
		if(msgKeyword != null){
			Object message = msgKeyword.get(KEYWORD_SMS_MESSAGE);
			if (message != null) {
				SMSMessage msg = new SMSMessage();
				msg.setDate(createSendDate());
				//所有子订单的收货人电话号码也是一样的
				msg.setPhones(phones);
				String msgTemplate = message.toString();
				msg.setOperator(Constant.OPERATORS_WODONG);
				//替换短信模板内容
				for (Map.Entry<String, Object> entry : msgKeyword.entrySet()) {
					if (entry.getValue() != null) {
						msgTemplate = msgTemplate.replaceAll(entry.getKey(), entry
								.getValue().toString());
					}
				}
				msg.setText(msgTemplate);
				smsService.sendMessage(msg);
				LOG.info("短信发送成功，发送对象：" + msg.getPhones() + "，短信内容：" + msg.getText());
			}
		}
	}

	/**
	 * 发货-是否可以忽略短信
	 * 
	 * @param order
	 * @return
	 */
	private boolean ignoreMessage(Order order) {
		List<Long> products = new ArrayList<Long>();
		for (OrderItem oi : order.getItemList()) {
			if (!oi.isVirtual()
					&& oi.getDeliveryQuantity() < oi.getPurchaseQuantity()) {
				products.add(oi.getProductSale().getId());
			}
		}
		if (products.isEmpty()) {
			return false;
		} else {
			return productService.existsMessageIgnoreProduct(products);
		}
	}

	@Override
	public void reassignmentDeliveryCompany(Order order) {
		try {
			ParentOrder parentOrder = order.getParentOrder();
			sendMessage(Code.SMS_ORDER_CHANGE_DELIVERYCOMPANY, parentOrder);
		} catch (Exception e) {
			LOG.error("Order reassignmentDeliveryCompany send message ERROR", e);
		}
	}

	@Override
	//如果所有子订单的状态都为缺货取消，则发送缺货短信，当前所有的缺货取消短信都是停用的
	public void cancel(Order order) {
		try {
			int num = 0;
			ParentOrder parentOrder = order.getParentOrder();
			Set<Order> subOrders = new HashSet<Order>();
			if(parentOrder.getOrderList() != null){
				subOrders = parentOrder.getOrderList();
			}
			int standard = subOrders.size();
			for(Order subOrder : subOrders){
				Long statusId = subOrder.getProcessStatus().getId();
				if (Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL.equals(statusId)){
					num += 1;
				}
			}
			if(num == standard){
				if (ignoreMessage(order)) {
					return;
				}
				sendMessage(Code.SMS_ORDER_STOCKOUT, parentOrder);
			}
		} catch (Exception e) {
			LOG.error("order cancel send message ERROR", e);
		}
	}

	@Override
	public void delivery(Order order) {
		
		/**
		 * 1.天猫渠道的 2.发运公司为"北京小红帽""成都快优达""重庆日报"的三种中的一种
		 * 3.同时满足1、2两条件的订单触发发货短信，有预先定义好的短信模板
		 * 4.如果不能同时满足1、2两个条件，则不发送短信，但是对于其它渠道的发货短信则不受到发货公司的限制
		 * 5.2014.09.24业务部要求取消对整单发货的调整，仅调整部分发货
		 * 6.对于分仓发送的天猫渠道订单，需要单独处理短信模板
		 * 7.2014.11.08拆单功能发布之后，针对的是全渠道，也就是说当当网的订单也会有一个父订单，但是发货仓库实际上只有一个
		 * 8.2015.02.09新增亚马逊的拆单分仓发货短信
		 */
		try {
			
			Set<OrderDeliverySplit> orderDeliverySplits = order.getDeliverySplits();
			Set<Order> subOrders = new HashSet<Order>();
			ParentOrder parentOrder = order.getParentOrder();
			
			// 订单整单发货，发送类型为80002的短信，当前所有的整单发货短信都是停用的
			if(order.getDeliveryQuantity() == order.getPurchaseQuantity()){
				sendMessage(Code.SMS_ORDER_DELIVERY, parentOrder);
			}
		    /**
		     * 回传的订单是子订单，根据子订单获取父订单；根据父订单是否拆分为多个子订单来选择短信模板
		     * 1.父订单未拆分，只子订单只有一个———单仓发货短信
		     */
			if(!parentOrder.isSplit()){
				
				// 部分发货，非天猫、加盟店和亚马逊的其它渠道都只有一个子订单，因此只检查这一个子订单的状态
				if(order.isDeliveried()){
					
					// 单仓发货、发货数量小于购买数量 ——— 缺货短信
					if (order.getDeliveryQuantity() < order.getPurchaseQuantity()) {
						if (!ignoreMessage(order)) {
							sendMessage(Code.SMS_ORDER_PART_DELIVERY, parentOrder);
						}
					}
					  
					// 单仓发货、不论发货数量、分包发货  ——— 分包发货短信
					if(!CollectionUtils.isEmpty(orderDeliverySplits)){
						sendMessage(Code.SMS_ORDER_SPLIT_DELIVERY, parentOrder);
					}
				}
			}
			/**
			 * 2.父订单拆分，即一个父订单对应了多个子订单——— 多仓发货短信
			 */
			else{
				//如果父订单不为空，并且分拆后子订单的数量大于1，则发送拆单短信
				if (parentOrder != null && parentOrder.getOrderList().size() > 1) {
					//分仓发货
					Map<Order, Long> deliveryFlags = new HashMap<Order, Long>();
					//获取每个子订单的发货状态，要求在所有子订单都在SAP处理完成后，才开始发送短信
					int currentStatus = parentOrderService.getCurrentStatus(parentOrder);
					if(currentStatus == 2){
						for(Order subOrder : subOrders){
							//发货数量不为0、发货数量小于购买数量
							if ((subOrder.getDeliveryQuantity() < subOrder.getPurchaseQuantity()) && (subOrder.getDeliveryQuantity() != 0)){
								deliveryFlags.put(subOrder, Code.SMS_ORDER_PART_DELIVERY);
							}
							//发货数量为0
							else if (subOrder.getDeliveryQuantity() == 0){
								deliveryFlags.put(subOrder, Code.SMS_ORDER_STOCKOUT);
							}
							//发货数量不为0、发货数量等于购买数量
							else {
								deliveryFlags.put(subOrder, Code.SMS_ORDER_DELIVERY);
							} 
						}
						//多仓发货短信
						if ((!deliveryFlags.containsValue(Code.SMS_ORDER_PART_DELIVERY) && (!deliveryFlags.containsValue(Code.SMS_ORDER_STOCKOUT)))
								||((deliveryFlags.containsValue(Code.SMS_ORDER_PART_DELIVERY) || deliveryFlags.containsValue(Code.SMS_ORDER_STOCKOUT)))){
							sendMessage(Code.SMS_ORDER_MULTI_BIN, parentOrder);
						}
					}
				}
			}
			
		} catch (Exception e) {
			LOG.error("order delivery send message ERROR", e);
		}

	}

	@Override 
	public void returnedPurchase(ReturnOrder returnOrder) {
		try {
			Long processId = returnOrder.getStatus().getId();
			Long typeId = returnOrder.getType().getId();
			if (Code.RETURN_ORDER_STATUS_REFUNDED.equals(processId)
					&& Code.RETURN_ORDER_TYPE_RETURN.equals(typeId)) {
				Order order = returnOrder.getOriginalOrder();
				ParentOrder parentOrder = order.getParentOrder();
				sendMessage(Code.SMS_ORDER_RETURN, parentOrder);
			}
		} catch (Exception e) {
			LOG.error("Order rejection ERROR", e);
		}
	}

	@Override
	public void replace(ReturnOrder returnOrder) {
		try {
			Long processId = returnOrder.getStatus().getId();
			Long typeId = returnOrder.getType().getId();
			if (Code.RETURN_ORDER_STATUS_CHANGED.equals(processId)
					&& Code.RETURN_ORDER_TYPE_REPLACE.equals(typeId)) {
				Order order = returnOrder.getOriginalOrder();
				ParentOrder parentOrder = order.getParentOrder();
				sendMessage(Code.SMS_ORDER_REPLACE, parentOrder);
			}
		} catch (Exception e) {
			LOG.error("Order replace ERROR", e);
		}

	}

	@Override
	public void retry(List<Order> orders) {
		if (orders != null && !orders.isEmpty()) {
			for (Order o : orders) {
				Long status = o.getProcessStatus().getId();
				o.setForceSendMessage(true);
				if (status
						.equals(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL)) {
					this.cancel(o);
				}
			}
		}
	}

	@Override
	/**
	 * 拆单之后，立即发送不包含快递信息的订单拆分通知短信（仅针对天猫渠道，不针对全渠道）
	 * 所有订单的拆单通知短信都是一样的内容
	 * 2015.02.09新增亚马逊的订单拆分通知短信
	 */
	public void splitOrderMessage(ParentOrder parentOrder) {
		// TODO Auto-generated method stub
		Set<Order> subOrders = new HashSet<Order>();
		if (parentOrder.isSplit()){
			if(parentOrder.getOrderList() != null){
				subOrders = parentOrder.getOrderList();
			}
			//当子订单的数量大于1的时候，才发送订单拆分短信
			if(subOrders.size() > 1){
				sendMessage(Code.SMS_SPLIT_ORDER_MESSAGE, parentOrder);
			}
		}
	}
}
