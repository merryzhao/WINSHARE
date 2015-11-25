package com.winxuan.ec.service.message;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.SmsMessageDao;
import com.winxuan.ec.exception.SmsMessageException;
import com.winxuan.ec.model.message.SmsOrderMessage;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.ParentOrder;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 短信平台服务
 * 
 * @author heyadong
 * @version 1.0, 2013-1-15 上午10:07:45
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmsMessageServiceImpl implements SmsMessageService {
	@InjectDao
	SmsMessageDao smsMessageDao;

	JdbcTemplate jdbcTemplate;

	private void checkSimilarity(SmsOrderMessage msg)
			throws SmsMessageException {
		// 将以;;分隔的.快递,支付类型,渠道. 替换成正则表达式
		String deliveryTypeRegx = msg.getDeliveryType().replaceAll(";", ";|;");
		deliveryTypeRegx = deliveryTypeRegx.substring(2,
				deliveryTypeRegx.length() - 2);
		String payTyleRgex = msg.getPaytype().replaceAll(";", ";|;");
		payTyleRgex = payTyleRgex.substring(2, payTyleRgex.length() - 2);
		String channelRgex = msg.getChannels().replaceAll(";", ";|;");
		channelRgex = channelRgex.substring(2, channelRgex.length() - 2);

		String sql = "SELECT CONCAT(CONVERT(s.id,char),':',s.remark) FROM sms_order_message s WHERE"
				+ " s.type = ?"
				+ " AND ((? BETWEEN kindgreat AND kindless OR ? BETWEEN kindgreat AND kindless) OR ( kindgreat BETWEEN ? AND ? OR kindless BETWEEN ? AND ? ))"
				+ " AND s.channels REGEXP ?"
				+ " AND s.deliverytype REGEXP ?"
				+ " AND s.paytype REGEXP ?" + " AND s.id != ?";

		List<String> list = jdbcTemplate.queryForList(sql, String.class, msg
				.getType().getId(), msg.getKindgreat(), msg.getKindless(), msg
				.getKindgreat(), msg.getKindless(), msg.getKindgreat(), msg
				.getKindless(), channelRgex, deliveryTypeRegx, payTyleRgex, msg
				.getId() == null ? -1 : msg.getId());
		if (!list.isEmpty()) {
			throw new SmsMessageException(StringUtils.join(list.toArray(), "　"));
		}

	}

	@Override
	public void save(SmsOrderMessage msg) throws SmsMessageException {
		checkSimilarity(msg);
		smsMessageDao.save(msg);

	}

	@Override
	public void update(SmsOrderMessage msg) throws SmsMessageException {
		checkSimilarity(msg);
		smsMessageDao.update(msg);
	}

	@Override
	public SmsOrderMessage get(Long id) {
		return smsMessageDao.get(id);
	}

	@Override
	public List<SmsOrderMessage> query(Map<String, Object> params,
			Pagination pagination) {
		return smsMessageDao.query(params, pagination);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//针对天猫渠道分仓清单的特定短信发送
	public String getMessage(Long type, ParentOrder parentOrder, int outofQuantity){
		String message = null;
		Long payTypeTemp = null;
		Long deliveryTypeTemp = null;
		String payType = null;
		String deliveryType = null;
		String channel = "'%;" + parentOrder.getChannel().getId() + ";%'";
		Set<Order> subOrders = new HashSet<Order>();
		String sql = null;
		if (parentOrder.getOrderList() != null){
			subOrders = parentOrder.getOrderList();
		}
		for(Order subOrder : subOrders){
			payTypeTemp = subOrder.getPayType().getId();
			deliveryTypeTemp = subOrder.getDeliveryType().getId();
		}
		payType = "'%;" + payTypeTemp + ";%'";
		deliveryType = "'%;" + deliveryTypeTemp + ";%'";
		for (Order subOrder : subOrders){
			//如果是天猫的未拆分的订单（即子订单只有一个），在部分发货的情况下，根据快递公司选择特定短信模板
			if (subOrders.size() == 1 && (subOrder.getDeliveryCompany() != null)
					&& (subOrder.getChannel().getId() == 110)
					&& (subOrder.getDeliveryCompany().getId() == 1333
							|| subOrder.getDeliveryCompany().getId() == 21 || subOrder
							.getDeliveryCompany().getId() == 61)
					&& (subOrder.getDeliveryQuantity() < subOrder.getPurchaseQuantity())
					&& type == 80006) {
				// 天猫渠道，指定货运公司发送新的短信模板，其它货运公司发送另外定义的短信模板
				message = "您好：您订单中@product_info@（@number@本）因质检不合格未发出，剩余商品已发，"
						+ "配送快递@split_delivery_company@，运单号@split_delivery_code@，未发商品麻烦登录天猫后台申请退 款，"
						+ "感谢您的合作！【文轩网天猫店】";
				return message;
			}
			//当当的未拆分的订单（即子订单只有一个），在部分发货的情况下，根据快递公司选择特定短信模板
			if (subOrders.size() == 1 && (subOrder.getDeliveryCompany() != null)
					&& (subOrder.getChannel().getId() == 8097)
					&& (subOrder.getDeliveryCompany().getId() == 221
							|| subOrder.getDeliveryCompany().getId() == 382 || subOrder
							.getDeliveryCompany().getId() == 522
							|| subOrder.getDeliveryCompany().getId() == 1310
							|| subOrder.getDeliveryCompany().getId() == 1331
							|| subOrder.getDeliveryCompany().getId() == 1332)
					&& (subOrder.getDeliveryQuantity() < subOrder.getPurchaseQuantity())
					&& type == 80006) {
				message = "您好，您订购的商品由@split_delivery_company@快递发出，快递单号为：" +
						"@split_delivery_code@，其中共有@number@本因质量问题不能发出，将在5个工作" +
						"日内退 款到您的原支付账户。若有疑问，详询4007020808【文轩网当当店】";
				return message;
			}
			//当当的未拆分的订单（即子订单只有一个），在整单发货的情况下，根据快递公司选择特定短信模板
			if (subOrders.size() == 1 && (subOrder.getDeliveryCompany() != null)
					&& (subOrder.getChannel().getId() == 8097)
					&& (subOrder.getDeliveryCompany().getId() == 221
							|| subOrder.getDeliveryCompany().getId() == 382 || subOrder
							.getDeliveryCompany().getId() == 522
							|| subOrder.getDeliveryCompany().getId() == 1310
							|| subOrder.getDeliveryCompany().getId() == 1331
							|| subOrder.getDeliveryCompany().getId() == 1332)
					&& (subOrder.getDeliveryQuantity() == subOrder.getPurchaseQuantity())
					&& type == 80002) {
				message = "您好:您的订单已由@split_delivery_company@快递发送，快递单号为：" +
						"@split_delivery_code@，请保持通讯畅通耐心等待！【文轩网当当店】";
				return message;
			}
		}
		sql = "SELECT s.message FROM sms_order_message s "
			+ " WHERE s.type = " + type + " AND s.channels LIKE "
			+ channel + " AND s.paytype LIKE " + payType
			+ " AND s.deliverytype LIKE " + deliveryType
			+ " AND s.kindgreat <= ? AND s.kindless >= ?" 
			+ " AND s.enable = 1";
		List<String> msgs = jdbcTemplate.queryForList(sql, String.class,
				new Object[] { outofQuantity, outofQuantity });
		if (msgs.isEmpty()) {
			message = null;
		} else {
			message = msgs.get(0);
		}
			return message;
		}
}
