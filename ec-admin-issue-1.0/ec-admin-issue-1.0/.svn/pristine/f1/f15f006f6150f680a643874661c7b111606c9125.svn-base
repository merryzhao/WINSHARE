package com.winxuan.ec.admin.controller.dashboard;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.cookie.DateParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.statistics.DeliveryStatistics;
import com.winxuan.ec.model.statistics.OrderDetail;
import com.winxuan.ec.model.statistics.OrderStatistics;
import com.winxuan.ec.model.statistics.OrderStatisticsParameter;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.statistics.OrderStatisticsService;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 
 * @author cast911
 * @description:
 * @lastupdateTime:2012-11-2下午01:11:38 -_-!
 */
@Controller
@RequestMapping("/dashboard")
public class DashBoardController {

	private static final Log LOG = LogFactory.getLog(DashBoardController.class);

	private static final long ONE_DAY_MSEC = 24 * 3600 * 1000;

	@Autowired
	OrderStatisticsService orderStatisticsService;
	@Autowired
	OrderService orderService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/app", method = RequestMethod.GET)
	public ModelAndView app() {
		return new ModelAndView("/dashboard/app");
	}

	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	public ModelAndView orderStatistics(DashBoardForm dashboardform)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		ModelAndView mav = new ModelAndView();
		if (dashboardform.getEnd() == null) {
			dashboardform.setEnd(new Date());
		}
		OrderStatisticsParameter params = new OrderStatisticsParameter();
		PropertyUtils.copyProperties(params, dashboardform);
		try {
			int result = this.checkTimeSpan(params.getStart(), params.getEnd());
			if (result > MagicNumber.THIRTY) {
				throw new DateParseException("时间跨度过大 控制在" + MagicNumber.THIRTY
						+ "天内");
			}

			List<DeliveryStatistics> deliveryStatistics = orderStatisticsService
					.queryOrderDeliveryStatisticsService();
			OrderStatistics orderStatistics = this
					.sumOrderStatistics(deliveryStatistics);
			mav.addObject("serverTime", System.currentTimeMillis());
			mav.addObject("orderStatistics", orderStatistics);
			mav.addObject("deliveryStatistics", deliveryStatistics);
			mav.addObject("targetTime", this.getMaxTime(deliveryStatistics));
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (DateParseException e) {
			LOG.error(e.getMessage(), e);
			mav.addObject(ControllerConstant.RESULT_KEY, e.getMessage());
		}

		return mav;
	}

	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public ModelAndView queryLastOrder(DashBoardForm dashboardform)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		// 第一次当前时间
		if (dashboardform.getLastUpdateTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dashboardform.getLastUpdateTime());
			calendar.add(Calendar.MINUTE, -MagicNumber.FIVE);
			dashboardform.setStart(calendar.getTime());
		}

		OrderStatisticsParameter params = new OrderStatisticsParameter();
		PropertyUtils.copyProperties(params, dashboardform);
		if (dashboardform.getEnd() == null) {
			params.setEnd(new Date());
		}
		List<OrderDetail> orderDetailList = orderStatisticsService
				.queryLastDeliveryOrder(params,
						dashboardform.getEffectiveLimit());
		ModelAndView mav = new ModelAndView();
		mav.addObject("orderDetailList", orderDetailList);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mav.addObject("lastUpdateTime", sdf.format(new Date()));
		mav.addObject("serverTime", System.currentTimeMillis());
		return mav;
	}

	private OrderStatistics sumOrderStatistics(
			List<DeliveryStatistics> deliveryStatisticList) {
		OrderStatistics orderStatistics = new OrderStatistics();
		BigDecimal totalListPrice = BigDecimal.ZERO;
		BigDecimal deliveryListPrice = BigDecimal.ZERO;
		BigDecimal surplusprice = BigDecimal.ZERO;
		BigDecimal todayPrice = null;
		
		int deliveryOrder = MagicNumber.ZERO;
		int  deliveryProduct = MagicNumber.ZERO;
		int  surplusOrder = MagicNumber.ZERO; 
		int	surplusProduce = MagicNumber.ZERO; 
		int totalOrderNum = MagicNumber.ZERO; 
		int orderNum = MagicNumber.ZERO; 
		int totalProductNum = MagicNumber.ZERO;
		int productNum =MagicNumber.ZERO; 
		int olddate = MagicNumber.ZERO; 
		int nowdate = MagicNumber.ZERO;
		for (DeliveryStatistics deliveryStatistic : deliveryStatisticList) {
			totalListPrice = totalListPrice.add(deliveryStatistic
					.getTotalListPrice());
			deliveryListPrice = deliveryListPrice.add(deliveryStatistic
					.getDeliveryListPrice());
			Calendar calendar = Calendar.getInstance();
			totalOrderNum += deliveryStatistic.getTotalOrderNum();
			totalProductNum += deliveryStatistic.getTotalProductNum();
			deliveryOrder +=deliveryStatistic.getOrderNum();
			deliveryProduct +=deliveryStatistic.getProductNum();
			if (todayPrice == null) {
				olddate = calendar.get(Calendar.DAY_OF_YEAR);
				calendar.setTime(deliveryStatistic.getDate());
				nowdate = calendar.get(Calendar.DAY_OF_YEAR);
				if (olddate == nowdate) {
					todayPrice = deliveryStatistic.getDeliveryListPrice();
					orderNum = deliveryStatistic.getOrderNum();
					productNum = deliveryStatistic.getProductNum();
				}
			}
		}

		
		if(totalListPrice.compareTo(deliveryListPrice)>0){
			surplusprice = totalListPrice.subtract(deliveryListPrice);
		}else{
			surplusprice = BigDecimal.ZERO;
		}
		surplusOrder = totalOrderNum-deliveryOrder;
		if(surplusOrder<MagicNumber.ZERO){
			surplusOrder = MagicNumber.ZERO;
		}
		surplusProduce = totalProductNum - deliveryProduct;
		if(surplusProduce<MagicNumber.ZERO){
			surplusProduce = MagicNumber.ZERO;
		}
		orderStatistics.setSurplusOrder(surplusOrder);
		orderStatistics.setSurplusProduce(surplusProduce);
		orderStatistics.setTodayPrice(todayPrice);
		orderStatistics.setTargetListprice(totalListPrice);
		orderStatistics.setDeliveryListprice(deliveryListPrice);
		orderStatistics.setSurplusprice(surplusprice);
		orderStatistics.setTotalOrderNum(totalOrderNum);
		orderStatistics.setTodayOrderNum(orderNum);
		orderStatistics.setTotalProductNum(totalProductNum);
		orderStatistics.setTodayproductNum(productNum);
		orderStatistics.setDeliveryOrder(deliveryOrder);
		orderStatistics.setDeliveryProduct(deliveryProduct);
		return orderStatistics;
	}

	/**
	 * 计算时间差,控制时间跨度
	 * 
	 * @param startDeliveryTime
	 * @param endDeliveryTime
	 * @return
	 * @throws DateParseException
	 */
	private int checkTimeSpan(Date startDeliveryTime, Date endDeliveryTime)
			throws DateParseException {
		int result = 0;
		if (startDeliveryTime == null) {
			throw new DateParseException("没有开始时间");
		}
		if (endDeliveryTime == null) {
			throw new DateParseException("没有结束时间");
		}
		long startTime = startDeliveryTime.getTime();
		long endTime = endDeliveryTime.getTime();
		if (startTime > endTime) {
			throw new DateParseException("开始时间小于结束时间");
		}
		long resultTime = endTime - startTime;
		result = (int) (resultTime / ONE_DAY_MSEC + MagicNumber.ONE);
		return result;
	}

	/**
	 * 找寻最大时间
	 * 
	 * @param orderDetailList
	 * @return
	 */
	@SuppressWarnings("static-access")
	private Date getMaxTime(List<DeliveryStatistics> deliveryStatisticsList) {
		Date result = null;
		for (DeliveryStatistics deliveryStatistics : deliveryStatisticsList) {
			if (result == null || result.before(deliveryStatistics.getDate())) {
				result = deliveryStatistics.getDate();
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(result);
		calendar.set(calendar.HOUR, MagicNumber.TWENTY_THREE);
		calendar.set(calendar.MINUTE, Integer.valueOf("59"));
		calendar.set(calendar.SECOND, Integer.valueOf("59"));
		return calendar.getTime();
	}

	/**
	 * 设置订单信息
	 * 
	 * @param orderDetailList
	 * @return
	 */
	// private List<OrderDetail> setOrderInfo(List<OrderDetail>
	// orderDetailList){
	// List<OrderDetail> result = new
	// ArrayList<OrderDetail>(orderDetailList.size());
	// for (OrderDetail orderDetail : orderDetailList) {
	// Order o = orderService.get(orderDetail.getOrderId());
	// orderDetail.setOrder(o);
	// result.add(orderDetail);
	// }
	// return result;
	// }

}
