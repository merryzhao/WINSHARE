package com.winxuan.ec.service.delivery;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.order.OrderLogistics;

/**
 * 申通API
 * @author Heyadong
 * @version 1.0 , 2012-1-12
 */
@Component("shenTongAPI")
public class ShenTongAPI implements DeliveryAPI{
	private static final Logger LOG = Logger.getLogger(ShenTongAPI.class);
	private static final long serialVersionUID = -5413317251040694065L;
	
	@Value("${core.delivery.api.shentong}")
	private String urlPath;
	
	@Override
	public List<OrderLogistics> getOrderLogistics(String deliveryCode) throws Exception {
		Root root = null;
		try{
			root = (Root)DeliveryUtils.getShentong().fromXML(new URL(String.format(urlPath, deliveryCode)));
			if (root != null 
					&& root.getTrack() != null 
					&& root.getTrack().getDetails() != null 
					&& !root.getTrack().getDetails().isEmpty()){
				List<OrderLogistics> list = new ArrayList<OrderLogistics>(root.getTrack().getDetails().size());
				for (Detail detail : root.getTrack().getDetails()){
					OrderLogistics logistics = new OrderLogistics();
					logistics.setContext(detail.getMemo());
					logistics.setTime(detail.getTime());
					list.add(logistics);
				}
				return list;
			} 
		} catch (Exception e) {
			LOG.error(String.format("ShenTongApi error deliveryCode:%s   , Msg:%s", deliveryCode,e.getMessage()), e);
		}
		return null;
	}

	@Override
	public String getDeliverCompanyCode() {
		return "shentong";
	}
	/**
	 * 申通XML
	 * @author Heyadong
	 * @version 1.0 , 2012-1-16
	 */
	protected static class Root{
		private Track track;

		public Track getTrack() {
			return track;
		}

		public void setTrack(Track track) {
			this.track = track;
		}

	}
	/**
	 * 申通XML
	 * @author Heyadong
	 * @version 1.0 , 2012-1-16
	 */
	protected static class Track {
		private String billcode;
		
		private List<Detail> details;
		
		public List<Detail> getDetails() {
			return details;
		}
		public void setDetails(List<Detail> details) {
			this.details = details;
		}
		public String getBillcode() {
			return billcode;
		}
		public void setBillcode(String billcode) {
			this.billcode = billcode;
		}
	}
	/**
	 * 申通XML
	 * @author Heyadong
	 * @version 1.0 , 2012-1-16
	 */
	protected static class Detail{
		private String time;
		private String scantype;
		private String memo;
		
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getScantype() {
			return scantype;
		}
		public void setScantype(String scantype) {
			this.scantype = scantype;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
	
	}
}
