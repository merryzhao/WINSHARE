package com.winxuan.ec.task.job.ec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.service.sap.SapOrderService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 
 * @author yangxinyi
 *
 */
@Component("transferOrderSap")
public class TransferOrderSap implements Serializable, TaskAware, TaskConfigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5198144839760934522L;
	private static final Log LOG = LogFactory.getLog(TransferOrderSap.class);
	
	private static final Integer PAGE_SIZE = Integer.valueOf(MagicNumber.FIVE_HUNDRED);
	private static Map<String, Object> parameters = new HashMap<String, Object>();
	private static List<String> errorOrder = new ArrayList<String>();
	static{
		parameters.put("storageType", new Long[]{Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM,Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_PLATFORM});
		parameters.put("transferResult", Code.EC2SAP_STATUS_NONE);
		parameters.put("processStatus",new Long[] { Code.ORDER_PROCESS_STATUS_WAITING_PICKING });
		parameters.put("virtual",false);
		parameters.put("consigneeDc", new Long[] {Code.DC_D803, Code.DC_D801});
	}

	@Autowired
	private SapOrderService sapOrderService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Autowired
	private NotifyService notifyService;

	public void start() {
		int currentPage = MagicNumber.ONE;
		int i = MagicNumber.ZERO;
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(currentPage);
		pagination.setPageSize(PAGE_SIZE);
		List<Order> needTransferOrder = null;
		errorOrder.clear();
		while(i++ < MagicNumber.THREE){
			Date timePara = DateUtils.addMinutes(new Date(), -5);
			parameters.put("endLastProcessTime", timePara);
			if((needTransferOrder = orderService.find(parameters,(short)1, pagination))!=null
					&& !needTransferOrder.isEmpty()){
				hibernateLazyResolver.openSession();
				for(Order order : needTransferOrder){
					try {
						if(!errorOrder.contains(order.getId())){
							sapOrderService.transferOrder(order);
						}
					} catch (Exception e) {
						errorOrder.add(order.getId());
						LOG.error(order.getId() + ": transfer exception: "+e.getMessage(),e);
					}
				}
				hibernateLazyResolver.releaseSession();
			}
		}
		if(errorOrder.size() > MagicNumber.FIVE){
			doNotify();
		}
	}

	@Override
	public void doNotify(String... msg) {
		notifyService.notify(this, String.format("%s 由于过多异常中断!", getName()));
	}
	
	@Override
	public int getNotifyLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return "transferOrderSap";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

}