package com.winxuan.ec.service.delivery;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.order.OrderLogistics;

/**
 * 物流API 管理工具
 * @author Heyadong
 * @version 1.0 , 2012-1-13
 */
@Component
public class DeliveryAPIManager implements Serializable,ApplicationContextAware{
	
	private static final long serialVersionUID = -5536644274278313340L;
	private Map<String,DeliveryAPI> map = new HashMap<String, DeliveryAPI>();
	private void add(DeliveryAPI api){
		map.put(api.getDeliverCompanyCode(), api);
	}
	public boolean contains(String companyCode){
		return map.containsKey(companyCode);
	}
	/**
	 * 获取物流信息
	 * @param companyCode companyCode
	 * @param deliveryCode 运单号 
	 * @see com.winxuan.ec.model.order.Order.deliverycode
	 * @return 物流信息
	 * @throws Exception 物流异常.可能网络错误.运单号错误.API 接口出错
	 */
	public List<OrderLogistics> getOrderLogistics(String companyCode,String deliveryCode) throws Exception{
		if (!map.containsKey(companyCode)){
			new Exception("companyCode 错误 或 未支持...");
		}
		return map.get(companyCode).getOrderLogistics(deliveryCode);
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		Map<String,DeliveryAPI> apis = applicationContext.getBeansOfType(DeliveryAPI.class);
		for (DeliveryAPI api : apis.values()){
			this.add(api);
		}
	}
}
