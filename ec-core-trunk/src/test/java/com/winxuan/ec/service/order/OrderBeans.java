package com.winxuan.ec.service.order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.Services;
import com.winxuan.ec.service.area.AreaBeans;
import com.winxuan.ec.service.channel.ChannelBeans;
import com.winxuan.framework.util.BigDecimalUtils;

/**
 * 订单工具
 * 
 * @author v1.0 by Heyadong,v2.0 by cast911
 * 
 */
@Component("orderbeans")
public class OrderBeans {

	/**
	 * 中国34个省,
	 */
	private static List<Long> chinaProvince = Arrays.asList(152L, 159L, 168L,
			172L, 177L, 161L, 164L, 167L, 150L, 153L, 165L, 166L, 171L, 174L,
			182L, 155L, 156L, 158L, 160L, 162L, 163L, 157L, 175L, 178L, 181L,
			183L, 154L, 169L, 170L, 173L, 180L, 151L, 176L, 179L);

	boolean isRandomArea = true;

	@Autowired
	private Services services;

	/**
	 * 根据传入的区域,随机的找出该区域的最末级节点.
	 * 
	 * @param area
	 * @return
	 */
	public Area findChild(Area area) {
		Set<Area> areaSet = area.getChildList();
		if (CollectionUtils.isEmpty(areaSet)) {
			return area;
		}
		List<Area> areaList = Arrays.asList(areaSet.toArray(new Area[0]));
		int seedRandom = areaSet.size();
		Random random = new Random();
		int index = random.nextInt(seedRandom);
		return this.findChild(areaList.get(index));
	}

	/**
	 * 创建订单 固定方式运输
	 * 
	 * 运费 0 元
	 * 
	 * @see {下列参数未设置}
	 * @see {com.winxuan.ec.model.order.OrderConsignee} consignee
	 * @see {com.winxuan.ec.model.user.User} creator
	 * @see {com.winxuan.ec.model.user.Customer} customer
	 * @see {com.winxuan.ec.model.order.OrderPayment} paymentList
	 * @see {com.winxuan.ec.model.delivery.DeliveryCompany} deliveryCompany
	 * @see {com.winxuan.ec.model.code.Code} erpStatus
	 * @see {OrderInvoice} invoiceList
	 * @return
	 */
	public Order createOrder() {
		Order order = new Order();
		order.setChannel(ChannelBeans.createChannelEC());
		order.setCreateTime(new Date());
		order.setDeliveryType(new DeliveryType(DeliveryType.FLAT_POST));
		return order;
	}

	/**
	 * orderInvoice 订单发票 状态正常.
	 * <P />
	 * 
	 * @see {下列参数未设置}
	 * @see {Order} order
	 * @see {Employee} Operator
	 * @see {Code} mode
	 * @return
	 */
	public OrderInvoice createOrderInvoice(Order order) {
		OrderInvoice orderInvoice = new OrderInvoice();
		orderInvoice.setAddress(order.getConsignee().getAddress());
		orderInvoice.setCity(order.getConsignee().getCity());
		orderInvoice.setConsignee(order.getConsignee().getConsignee());
		orderInvoice.setContent(order.isExistMerchandiseItem() ? "百货" : "图书");
		orderInvoice.setCountry(order.getConsignee().getCountry());
		orderInvoice.setCreateTime(order.getCreateTime());
		orderInvoice.setMode(new Code(Code.ORDER_INVOICE_MODE_NORMAL));
		orderInvoice
				.setDeliveryOption(order.getConsignee().getDeliveryOption());
		orderInvoice.setDistrict(order.getConsignee().getDistrict());
		orderInvoice.setEmail(order.getConsignee().getEmail());
		orderInvoice.setMobile(order.getConsignee().getMobile());
		orderInvoice.setPhone(order.getConsignee().getPhone());
		orderInvoice.setProvince(order.getConsignee().getProvince());
		orderInvoice.setQuantity(order.getPurchaseQuantity());
		String title = null;
		if (StringUtils.isBlank(order.getConsignee().getInvoiceTitle())) {
			title = order.getConsignee().getConsignee();
		} else {
			title = order.getConsignee().getInvoiceTitle();
		}

		Code titleType = null;
		if (order.getConsignee().getInvoiceTitleType() == null) {
			titleType = new Code(Code.INVOICE_TYPE_GENERAL);
		} else {
			titleType = order.getConsignee().getInvoiceTitleType();
		}
		orderInvoice.setTitle(title);
		orderInvoice.setTitleType(titleType);
		orderInvoice.setType(new Code(Code.INVOICE_TYPE_GENERAL));
		orderInvoice.setStatus(new Code(Code.INVOICE_STATUS_NORMAL));
		orderInvoice.setZipCode(order.getConsignee().getZipCode());
		orderInvoice.setOrder(order);
		// 减少金额,用于测试补开发票
		orderInvoice.setMoney(order.getActualMoney().subtract(new BigDecimal("1")));
		orderInvoice.setOperator(this.services.employeeService
				.get(Employee.SYSTEM));
		orderInvoice.setTransferred(true);
		return orderInvoice;
	}

	/**
	 * 设置 订单项
	 * 
	 * @return
	 */
	public OrderItem createOrderItem() {
		OrderItem item = new OrderItem();
		return item;
	}

	/**
	 * 创建定单支付方式 邮费0元..回款0元..
	 * 
	 * <P />
	 * 
	 * @see {下列参数未设置}
	 * @see {com.winxuan.ec.model.payment.PaymentCredential} paymentCredential
	 * @see{java.math.BigDecimal PayMoney
	 * @see{Order order
	 * @return
	 */
	public OrderPayment createOrderPayment() {
		OrderPayment op = new OrderPayment();
		op.setCreateTime(new Date());
		op.setDeliveryMoney(BigDecimalUtils.ZERO);
		op.setReturnMoney(BigDecimalUtils.ZERO);
		op.setPayTime(new Date());
		return op;
	}

	public OrderConsignee createOrderConsignee() {
		return this.createOrderConsignee(null);

	}

	/**
	 * OrderConsignee 随机生成目前仅限于中国
	 * 
	 * @see {下列参数未设置}
	 * @see {order}
	 * @return
	 */
	public OrderConsignee createOrderConsignee(Area town) {

		OrderConsignee consignee = new OrderConsignee();
		consignee.setConsignee("系统订单测试" + System.currentTimeMillis());
		consignee.setCountry(AreaBeans.createAreaCountry());
		Area district = null;
		Area city = null;
		Area province = null;
		if (town == null) {
			Area area = this.ranDomArea();
			town = area;
			district = town.getParent();
			city = district.getParent();
			province = city.getParent();

		} else {
			boolean flag = CollectionUtils.isEmpty(town.getChildren());
			Assert.isTrue(flag, "请设置区域信息为最末级节点");
			district = town.getParent();
			city = district.getParent();
			province = city.getParent();
		}
		consignee.setTown(town);
		consignee.setDistrict(district);
		consignee.setCity(city);
		consignee.setProvince(province);
		String address = province.getName() + city.getName()
				+ district.getName() + town.getName();
		consignee.setAddress(address);
		consignee.setDeliveryOption(new Code(Code.DELIVERY_OPTION_WORK_DAY));
		consignee.setEmail("systemn@winxuan.com");
		consignee.setMobile("13982076091");
		consignee.setPhone("88888888");
		consignee.setRemark("中华人民共和国");
		consignee.setOutOfStockOption(new Code(Code.OUT_OF_STOCK_OPTION_SEND));
		consignee.setZipCode("610000");
		consignee.setUnAuditReason(new Code(Code.UN_AUDIT_REASON));
		return consignee;
	}

	private Area ranDomArea() {
		Random random = new Random();
		int index = random.nextInt(chinaProvince.size());
		Area area = services.areaService.get(chinaProvince.get(index));
		Area child = this.findChild(area);
		return child;
	}
}
