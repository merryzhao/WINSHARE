package com.winxuan.ec.front.controller.shop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopServiceNo;
import com.winxuan.ec.model.shop.ShopServiceTime;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.util.StringUtils;


/**
 * 
 * @author cast911
 * @description：
 * @lastupdateTime:2012-8-22下午03:59:11
 * -_-!
 */
@Component("serviceInfo")
public class ServiceInfo {
	
	private static final String TIME_MIAO = ":00";
	/**
	 * 客服信息
	 * 
	 * @param shop
	 * @param mav
	 */
	public ModelAndView setServiceInfo(Shop shop, ModelAndView mav) {
		List<String> serviceQqNos = new ArrayList<String>();
		List<String> phones = new ArrayList<String>();
		Set<ShopServiceNo> set = shop.getShopServices();
		if (set != null && !set.isEmpty()) {
			Iterator<ShopServiceNo> it = set.iterator();
			while (it.hasNext()) {
				ShopServiceNo shopServiceNo = it.next();
				if (Code.SHOP_SERVICE_TYPE_QQ.equals(shopServiceNo.getType()
						.getId())
						&& !StringUtils.isNullOrEmpty(shopServiceNo
								.getServiceNo())) {// qq客服
					serviceQqNos.add(shopServiceNo.getServiceNo());
				}
				if ((Code.SHOP_SERVICE_TYPE_MOBILE_PHONE.equals(shopServiceNo
						.getType().getId()) || Code.SHOP_SERVICE_TYPE_PHONE
						.equals(shopServiceNo.getType().getId()))
						&& !StringUtils.isNullOrEmpty(shopServiceNo
								.getServiceNo())) {// 电话
					phones.add(shopServiceNo.getServiceNo());
				}
			}
		}

		Set<ShopServiceTime> shopServiceTimes = shop.getShopServiceTimes();
		List<ShopServiceTime> list = new ArrayList<ShopServiceTime>(
				shopServiceTimes);
		/*
		 * ShopServiceTime[] shopServiceTimeArray = shopServiceTimes
		 * .toArray(new ShopServiceTime[shopServiceTimes.size()]);
		 * Arrays.sort(shopServiceTimeArray); list = new
		 * ArrayList<ShopServiceTime>( Arrays.asList(shopServiceTimeArray));
		 */
		mav.addObject("serviceTimes", getServiceTime(list));
		mav.addObject("serviceQqNos", serviceQqNos);
		mav.addObject("phones", phones);
		return mav;
	}
	

	private List<String> getServiceTime(List<ShopServiceTime> list) {
		List<String> serviceTimes = new ArrayList<String>();
		getServiceTime(list, 0, serviceTimes);
		return serviceTimes;
	}
	
	private void getServiceTime(List<ShopServiceTime> list, int start,
			List<String> serviceTimes) {
		if(list == null || list.size()==0){
			return;
		}
		if (start == list.size() - 1) {
			ShopServiceTime shopServiceTime = list.get(start);
			if (shopServiceTime.getWeekdayStartHour() >= shopServiceTime
					.getWeekdayEndHour()) {
				return;
			} else {
				serviceTimes.add(fetchInfo(start, start, shopServiceTime));
				return;
			}
		}
		ShopServiceTime shopServiceTime = list.get(start);
		if (shopServiceTime.getWeekdayStartHour() >= shopServiceTime
				.getWeekdayEndHour()) {
			getServiceTime(list, start + 1, serviceTimes);
			return;
		}
		int end = start;
		for (int i = start + 1; i < list.size(); i++) {
			ShopServiceTime sst = list.get(i);
			if (sst.getWeekdayStartHour() >= sst.getWeekdayEndHour()) {
				serviceTimes.add(fetchInfo(start, i - 1, shopServiceTime));
				getServiceTime(list, i + 1, serviceTimes);
				return;
			} else {
				if (sst.getWeekdayStartHour() != shopServiceTime
						.getWeekdayStartHour()
						|| sst.getWeekdayEndHour() != shopServiceTime
								.getWeekdayEndHour()) {
					serviceTimes.add(fetchInfo(start, i - 1, shopServiceTime));
					getServiceTime(list, i, serviceTimes);
					return;
				}else{
					end = i;
				}
			}
		}
		serviceTimes.add(fetchInfo(start, end, shopServiceTime));
	}
	

	private String fetchInfo(int start, int end, ShopServiceTime shopServiceTime) {
		if (start == end) {
			return weekdaySwitch(shopServiceTime.getWeekday()) + " "
					+ shopServiceTime.getWeekdayStartHour()+TIME_MIAO + "-"
					+ shopServiceTime.getWeekdayEndHour()+TIME_MIAO;
		} else {
			return weekdaySwitch(shopServiceTime.getWeekday()) + "到"
					+ weekdaySwitch(end + 1) + " "
					+ shopServiceTime.getWeekdayStartHour()+TIME_MIAO + "-"
					+ shopServiceTime.getWeekdayEndHour()+TIME_MIAO;
		}
	}

	private String weekdaySwitch(int weekday) {
		switch (weekday) {
		case MagicNumber.ONE:
			return "\u5468\u4e00";
		case MagicNumber.TWO:
			return "\u5468\u4e8c";
		case MagicNumber.THREE:
			return "\u5468\u4e09";
		case MagicNumber.FOUR:
			return "\u5468\u56db";
		case MagicNumber.FIVE:
			return "\u5468\u4e94";
		case MagicNumber.SIX:
			return "\u5468\u516d";
		case MagicNumber.SEVEN:
			return "\u5468\u65e5";
		default:
			return null;
		}
	}


}
