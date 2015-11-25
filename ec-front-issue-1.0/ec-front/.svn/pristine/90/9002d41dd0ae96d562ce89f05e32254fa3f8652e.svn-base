package com.winxuan.ec.front.controller.cps.extractor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.ec.front.controller.cps.CpsException;
import com.winxuan.ec.front.controller.cps.webservice.pcn.SalFeedbackService;
import com.winxuan.ec.front.controller.cps.webservice.pcn.SalFeedbackServicePortType;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.UnionOrder;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-10
 */
public class PcnExtractor extends AbstractExtractor{
private static final Log LOG = LogFactory.getLog(PcnExtractor.class);
	
	@Override
	public void send(UnionOrder unionOrder) {
		final int notmatchformat =2;
		final int  notfound = 3;
		String code = "CUO_SOFT_2010";
		String xml = generatePDotCnXmlData(unionOrder);
		SalFeedbackService salFeedbackService = new SalFeedbackService();
		SalFeedbackServicePortType portType = salFeedbackService
				.getSalFeedbackServiceHttpPort();
		int result = portType.salOrdFb(code, xml);
		if (result == -1) {
			LOG.info("P.cn 验证码错误");
		} else if (result == 0) {
			LOG.info("P.cn 失败：订单内容字符串非xml格式");
		} else if (result == 1) {
			LOG.info("P.cn 信息反馈成功");
		} else if (result == notmatchformat) {
			LOG.info("P.cn 反馈的内容格式不符合要求");
		} else if (result == notfound) {
			LOG.info("P.cn 未注册的域名");
		}		
	}

	@Override
	public void addParameter(PostMethod postMethod, UnionOrder unionOrder) {
		
	}
	private String generatePDotCnXmlData(UnionOrder unionOrder) {
		Order order = unionOrder.getOrder();
		StringBuffer reXML = new StringBuffer();
		reXML.append("<sal_ord_feedback_info>");
		reXML.append("<dtn>");
		reXML.append(unionOrder.getCookieInfo());
		reXML.append("</dtn>");
		reXML.append("<app_domain>");
		reXML.append("www.winxuan.com");
		reXML.append("</app_domain>");
		reXML.append("<order_no>");
		reXML.append(order.getId());
		reXML.append("</order_no>");
		reXML.append("<trade_time>");
		reXML.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(unionOrder.getCreateDate()));
		reXML.append("</trade_time>");
		reXML.append("<total_money>");
		reXML.append(order.getActualMoney());
		reXML.append("</total_money>");
		reXML.append("<comm>");
		reXML.append(BigDecimal.ZERO);
		reXML.append("</comm>");
		reXML.append("<trade_details>");
		
		for (OrderItem orderItem : order.getItemList()) {
			reXML.append("<trade_detail>");
			reXML.append("<item_code>");
			reXML.append(orderItem.getProductSale().getId());
			reXML.append("</item_code>");
			reXML.append("<item_name>");
			reXML.append(orderItem.getProductSale().getName());
			reXML.append("</item_name>");
			reXML.append("<item_count>");
			reXML.append(orderItem.getPurchaseQuantity());
			reXML.append("</item_count>");
			reXML.append("<price>");
			reXML.append(orderItem.getSalePrice());
			reXML.append("</price>");
			reXML.append("<preference>");
			reXML.append(orderItem.getSalePrice().divide(orderItem.getSalePrice()));
			reXML.append("</preference>");
			reXML.append("<subtotal>");
			reXML.append(orderItem.getListPrice());
			reXML.append("</subtotal>");
			reXML.append("</trade_detail>");
		}
		reXML.append("</trade_details>");
		reXML.append("</sal_ord_feedback_info>");

		return reXML.toString();
	}
	@Override
	protected String extractCookieValue(HttpServletRequest request) throws CpsException {
		String dtn = request.getParameter("dtn");
		if(StringUtils.isBlank(dtn)){
			throw new CpsException("P.Cn Error:连接参数错误，请咨询网站负责人");
		}
		return dtn;
	}


	@Override
	public void sendToCpsGet(UnionOrder unionOrder, String sendUrl) {
		// TODO Auto-generated method stub
		
	}
}
