package com.winxuan.ec.service.delivery;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.winxuan.ec.model.order.OrderLogistics;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.util.IOUtils;
/**
 * 圆通API
 * @author Heyadong
 * @version 1.0 , 2012-1-12
 */
@Component("yuanTongAPI")
public class YuanTongAPI implements DeliveryAPI,InitializingBean{
	private static final Logger LOG = Logger.getLogger(YuanTongAPI.class);
	private static final long serialVersionUID = 1990188986450265988L;
	private static final String CRLF = "\r\n";
	private static String  xmlTemplate = "<BatchQueryRequest><logisticProviderID>YTO</logisticProviderID><clientID>%s</clientID>"
		+ "<orders><order><mailNo>%s</mailNo></order></orders></BatchQueryRequest>";
	
	@Value("${core.delivery.api.yuantong.parternID}")
	private String parternID;
	@Value("${core.delivery.api.yuantong.clientID}")
	private String clientID;
	@Value("${core.delivery.api.yuantong.path}")
	private String path;
	
	private URL url = null;
	
	@Override
	public List<OrderLogistics> getOrderLogistics(String deliveryCode)
			throws Exception {
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		ByteArrayOutputStream baos = null;
		try{
			String xml = String.format(xmlTemplate, clientID, deliveryCode); 
			String digest = xml + parternID;
			String digest64 = Base64.encodeBase64String(MessageDigest.getInstance("MD5").digest(digest.getBytes())).trim();
			socket = new Socket(url.getHost(),url.getPort() > 0 ? url.getPort() : MagicNumber.HTTP_PORT);
			socket.setSoTimeout(MagicNumber.THOUSAND * MagicNumber.TEN);
			StringBuilder request = new StringBuilder();
			String urlXML = URLEncoder.encode(xml, "utf-8");
			String urlDigest = URLEncoder.encode(digest64, "utf-8");
			request.append(String.format("POST %s?logistics_interface=%s&data_digest=%s HTTP/1.0", url.getPath(), urlXML,urlDigest)).append(CRLF)
					.append("Host: ").append(url.getHost()).append(CRLF)
					.append("Accept: ").append("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8").append(CRLF)
					.append("Content-Type: ").append("application/x-www-orm-urlencoded;charset=UTF-8;").append(CRLF)
					.append(CRLF);
			out = socket.getOutputStream();
			out.write(request.toString().getBytes());
			out.flush();
			in = socket.getInputStream();
			baos = new ByteArrayOutputStream();
			
			IOUtils.writeStream(in, baos);
			
			String text = new String(baos.toByteArray());
			int index = text.indexOf("<BatchQueryResponse>");
			if (index >= 0){
				String resXML = text.substring(index);
				BatchQueryResponse yuantong = (BatchQueryResponse) DeliveryUtils.getYuantong().fromXML(resXML);
				if (yuantong.getOrders() != null
						&& !yuantong.getOrders().isEmpty()
						&& yuantong.getOrders().get(0).getSteps() != null
						&& !yuantong.getOrders().get(0).getSteps().isEmpty()){
					List<OrderLogistics> list = new ArrayList<OrderLogistics>();
					for (OrderStep step : yuantong.getOrders().get(0).getSteps()){
						OrderLogistics logistics = new OrderLogistics();
						logistics.setTime(step.getAcceptTime());
						logistics.setContext(String.format("%s %s", step.getAcceptAddress(),step.getRemark()));
						list.add(logistics);
					}
					return list;
				}
			}
		} catch (Exception e) {
			LOG.error(String.format("YuantongAPI error deliveryCode: %s,   Msg: %s", deliveryCode,e.getMessage()), e);
		}finally{
			IOUtils.close(baos,out,in);
			IOUtils.closeSocket(socket);
		}
		return null;
	}

	@Override
	public String getDeliverCompanyCode() {
		return "yuantong";
	}
	
//	public static void main(String[] args) throws Exception {
//		YuanTongAPI yuantong = new YuanTongAPI();
//		yuantong.path = "http://jingang.yto56.com.cn/ordws/VipOrderServlet";
//		yuantong.clientID = "winxuan";
//		yuantong.parternID = "poiuvcnmkjf";
//		yuantong.afterPropertiesSet();
//		yuantong.getOrderLogistics("V016979769");
//	}
	
	/**
	 * 圆通XML
	 * @author Heyadong
	 * @version 1.0 , 2012-1-16
	 */
	@XStreamAlias("BatchQueryResponse")
	protected class BatchQueryResponse{
		@XStreamAlias("logisticProviderID")
		private String logisticProviderID;
		@XStreamAlias("orders")
		private List<OrderContent> orders;
		public String getLogisticProviderID() {
			return logisticProviderID;
		}
		public void setLogisticProviderID(String logisticProviderID) {
			this.logisticProviderID = logisticProviderID;
		}
		public List<OrderContent> getOrders() {
			return orders;
		}
		public void setOrders(List<OrderContent> orders) {
			this.orders = orders;
		}
	}
	/**
	 * 圆通XML
	 * @author Heyadong
	 * @version 1.0 , 2012-1-16
	 */
	@XStreamAlias("order")
	protected class OrderContent{
		@XStreamAlias("mailNo")
		private String mailNo;
		@XStreamAlias("txLogisticID")
		private String txLogisticID;
		@XStreamAlias("mailType")
		private String mailType;
		@XStreamAlias("orderStatus")
		private String orderStatus;
		@XStreamAlias("steps")
		private List<OrderStep> steps;
		public String getMailNo() {
			return mailNo;
		}
		public void setMailNo(String mailNo) {
			this.mailNo = mailNo;
		}
		public String getTxLogisticID() {
			return txLogisticID;
		}
		public void setTxLogisticID(String txLogisticID) {
			this.txLogisticID = txLogisticID;
		}
		public String getMailType() {
			return mailType;
		}
		public void setMailType(String mailType) {
			this.mailType = mailType;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public List<OrderStep> getSteps() {
			return steps;
		}
		public void setSteps(List<OrderStep> steps) {
			this.steps = steps;
		}
	}
	/**
	 * 圆通XML
	 * @author Heyadong
	 * @version 1.0 , 2012-1-16
	 */
	@XStreamAlias("step")
	protected class OrderStep{
		@XStreamAlias("acceptTime")
		private String acceptTime;
		@XStreamAlias("acceptAddress")
		private String acceptAddress;
		@XStreamAlias("status")
		private String status;
		@XStreamAlias("remark")
		private String remark;
		public String getAcceptTime() {
			return acceptTime;
		}
		public void setAcceptTime(String acceptTime) {
			this.acceptTime = acceptTime;
		}
		public String getAcceptAddress() {
			return acceptAddress;
		}
		public void setAcceptAddress(String acceptAddress) {
			this.acceptAddress = acceptAddress;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
	}
	@Override
	public void afterPropertiesSet() throws Exception {
			url = new URL(path);
	}
}
