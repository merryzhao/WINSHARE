package com.winxuan.ec.support.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.delivery.DeliveryInfo;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.delivery.DeliveryService;

/**
 * @author  周斯礼
 * @version 2013-8-19
 */

@Component("orderDeliveryInfo")
public class OrderDeliveryInfo {

	@Autowired
	private DeliveryService deliveryService;
	
	/**
	 * 判断区域是否存在某种配送方式 
	 * @param area
	 * @param paramValue
	 * @return
	 */
	public DeliveryInfo isAreaSupp(Area area,Long paramValue,String address){
		List<DeliveryInfo> deliveryInfos = deliveryService.findDeliveryInfo(area, null,address);
		for (DeliveryInfo info : deliveryInfos) {// 判断该区域是否有对应的配送方式
			if (info.getDeliveryType().getId().equals(paramValue)) {
				return info;
			}
		}
		return null;
	}
	
	public DeliveryInfo isAreaSupportForEmployee(Area area, Long paramValue, String address, Employee employee){
		List<DeliveryInfo> deliveryInfos = deliveryService.findDeliveryInfoForEmployee(area, null,address,employee);
		return getDeliveryInfoById(paramValue, deliveryInfos);
	}
	
	private DeliveryInfo getDeliveryInfoById(Long paramValue,
			List<DeliveryInfo> deliveryInfos) {
		for (DeliveryInfo info : deliveryInfos) {// 判断该区域是否有对应的配送方式
			if (info.getDeliveryType().getId().equals(paramValue)) {
				return info;
			}
		}
		return null;
	}
}


