package com.winxuan.ec.service.order;

import java.util.Map;

/**
 * 订单报表定制
 * @author heyadong
 * @version 1.0, 2012-8-13 下午01:23:48
 */
public interface OrderReportService {
	/**
	 * 生成发货明细报表
	 * @param path 生成路径,如果文件存在则会覆盖
	 * @param parameters 
	 * @param sort
	 */
	 void generateOrderDeliveryReport(String path, Map<String, Object> parameters, Short sort) throws Exception;
}
