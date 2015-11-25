package com.winxuan.ec.admin.controller.order;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.BusinessException;
import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.interceptor.MyInject;

/**
 * @description: 订单批量更改DC
 *
 * @createtime: 2014-3-13 下午2:38:48
 *
 * @author zenghua
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderDcController {
	private static final Log LOG = LogFactory.getLog(OrderDcController.class);
	private static final short AUDIT_RESULT_FAIL = 0;
	
	@Autowired
    private OrderService orderService;
    @Autowired
    private CodeService codeService;
	@Autowired
    private DcService dcService;
	
	
	@RequestMapping(value = "/batchUpdateDc", method = RequestMethod.GET)
	public ModelAndView initUpdateDc() {
		ModelAndView mv = new ModelAndView("/order/order_dc");
		return mv;
	}
	
	@RequestMapping(value = "/batchUpdateDc", method = RequestMethod.POST)
	public ModelAndView updateDc(String orderId, String dcId,
			@MyInject Employee employee) throws BusinessException {
		ModelAndView mv = new ModelAndView("/order/order_dc_success");
		String[] orderIds = orderId.split(ControllerConstant.SPLITCHAR);
		Map<String, Object> messageMap = new HashMap<String, Object>();
		
		if (StringUtils.isBlank(orderId)) {
			mv.addObject(ControllerConstant.RESULT_KEY, AUDIT_RESULT_FAIL);
			mv.addObject(ControllerConstant.MESSAGE_KEY, "订单号不能为空");
			return mv;
		}

		List<String> list = Arrays.asList(orderIds);
		Set<String> orderSet = new HashSet<String>(list);
		for (String s : orderSet) {
			Order order = orderService.get(s.trim());

			if (order == null) {
				messageMap.put(s, "订单号不存在，不能更改状态");
				continue;
			}

			if (!order.getProcessStatus().getId()
					.equals(Code.ORDER_PROCESS_STATUS_NEW)
					&& !order.getProcessStatus().getId()
							.equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)) {
				messageMap.put(s, "订单处理状态为\t" + order.getTransferResult().getName() + "\t已不能更改DC");
				continue;
			}
			Code dc = null;
			try {
				dc = codeService.get(Long.parseLong(dcId));
			} catch (NumberFormatException e) {
				messageMap.put(s, "dc信息有误");
				continue;
			}
			if (dc == null) {
				messageMap.put(s, "订单号DC不能为空");
				continue;
			}
			try {
				dcService.updateOrderDcOriginal(order, dc, employee);
			} catch (ProductSaleStockException e) {
				messageMap.put(s, dc.getName() + "库位不存在,无法更新库存");
			}
		}
		mv.addObject("total", orderIds.length);
		mv.addObject("messageMap", messageMap);
		return mv;
	}
}
