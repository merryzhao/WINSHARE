package com.winxuan.ec.task.job.ec.union;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.model.union.Union;
import com.winxuan.ec.model.union.UnionCommission;
import com.winxuan.ec.service.order.UnionOrderService;
import com.winxuan.ec.service.union.UnionCommissionService;
import com.winxuan.ec.service.union.UnionService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-12-5
 */
@Component("calculateunionCommission")
public class CalculateUnionCommission implements Serializable,TaskAware {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3456432186126382774L;
	private static final Log LOG = LogFactory.getLog(CalculateUnionCommission.class);
	@Autowired
	UnionService unionService;
	
	@Autowired
	UnionOrderService unionOrderService;
	
	@Autowired
	UnionCommissionService unionCommissionService;
	
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Override
	public String getName() {
		return "calculateunionCommission";
	}

	@Override
	public String getDescription() {
		return "EC联盟佣金计算";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_FRONT;
	}

	@Override
	public void start() {
		 LOG.info("计算联盟订单start...");
		 hibernateLazyResolver.openSession();
		 List<Union> unions = unionService.find(null);
		 String time = getStringTime();
		 for(Union union : unions){
		 UnionCommission unionCommission = unionCommissionService.get(
					union.getId(), time);
			if (unionCommission == null) {
				UnionCommission unionCommission1 = new UnionCommission();
				BigDecimal effiveMoney = getMonthEffiveMoney(union);
				unionCommission1.setPay(false);
				unionCommission1.setTime(time);
				unionCommission1.setUnion(union);
				unionCommission1.setEffiveMoney(effiveMoney);
				unionCommission1.setCommission(effiveMoney.multiply(union
						.getRate()));
				unionCommissionService.save(unionCommission1);
				LOG.info("联盟"+union.getId()+"保存成功,总金额为"+effiveMoney);
			}
		 }
		 hibernateLazyResolver.releaseSession();
	}
	
	public BigDecimal getMonthEffiveMoney(Union union){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("unionId", union.getId());
		parameters.put("startCreateDate", getMonthFirstDay());
		parameters.put("endCreateDate", getMonthLastDay());
		BigDecimal effiveMoney = BigDecimal.ZERO;
		Pagination pagination = new Pagination();
		int currentPage = 1;
	    List<UnionOrder> unionOrderList = null;
	    while ((unionOrderList = unionOrderService.find(parameters,null,pagination)) != null
				&& unionOrderList.size() > 0) {
	    	for(UnionOrder unionOrder : unionOrderList){
	    		effiveMoney = effiveMoney.add(unionOrder.getOrder().getEffiveMoney());
	    	}
	    	currentPage = currentPage +1;
	    	pagination.setCurrentPage(currentPage);
	    }
		return effiveMoney;
	}
	/**
	 * 得到当前一月的第一天
	 * @return
	 */
	public static Date getMonthFirstDay() {     
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MONTH,-1);
	    calendar.set(Calendar.DAY_OF_MONTH, calendar     
	            .getActualMinimum(Calendar.DAY_OF_MONTH)); 
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	    return calendar.getTime();     
	} 
	
	/**
	 * 得到当前一月的最后一天
	 * @return
	 */
	public static Date getMonthLastDay() {     
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MONTH,-1);
	    calendar.set(Calendar.DAY_OF_MONTH, calendar     
	            .getActualMaximum(Calendar.DAY_OF_MONTH));
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
	    return calendar.getTime();     
	}
	/**
	 * 佣金月份
	 * @return
	 */
	public static String getStringTime(){
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MONTH,-1);
	    Date now =calendar.getTime();
		DateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(now);
	}
}
