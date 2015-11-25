package com.winxuan.ec.task.job.ec.order;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.ec.EcOrderService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 转换订单库存json
 * 
 * @author: HideHai
 * @date: 2013-11-5
 */
@Component("parseOrderStock")
public class ParseOrderStock implements TaskAware {

	private static final Log LOG = LogFactory.getLog(ParseOrderStock.class);

	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	@Autowired
	private EcOrderService ecOrderService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;

	@Override
	public String getName() {
		return "parseOrderStock";
	}

	@Override
	public String getDescription() {
		return "转换订单库存json";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		String sql = "SELECT count(o.id) FROM _order o WHERE "
				+ "o.createtime > DATE_SUB(left(now(),10),INTERVAL 3 day) "
				+ "AND o.transferresult = 35002 AND o.storagetype in (6001,6002) "
				+ "AND o.virtual = 0 AND o.shop = 1;";
		LOG.info("最近3天下传订单数量为：" + jdbcTemplateEcCore.queryForInt(sql));
		
		int n = 0;
		int limit = 2000;
		int i = 0;
		while(true) {
			sql = "SELECT o.id as id, i.id as orderitem, i.productsale, s.stockinfo FROM _order o, order_item i, order_item_stock s " +
					"WHERE i._order = o.id AND s._order = o.id AND i.productsale = s.productsale " +
					"AND o.createtime > DATE_SUB(left(now(),10),INTERVAL 3 day) " +
					"AND o.transferresult = 35002 AND o.storagetype in (6001,6002) " +
					"AND o.virtual = 0 AND o.shop = 1 limit "+ n + ", " + limit;
			LOG.info(sql);
	
			List<Map<String, Object>> queryResult = jdbcTemplateEcCore.queryForList(sql);
			if(CollectionUtils.isEmpty(queryResult)){
				break;
			}
			for(Map<String, Object> currentRow : queryResult){
				hibernateLazyResolver.openSession();
				Long orderId = Long.parseLong(currentRow.get("id").toString());
				Long orderItemId = Long.parseLong(currentRow.get("orderitem").toString());
				Long productSaleId = Long.parseLong(currentRow.get("productsale").toString());
				String stockInfo = currentRow.get("stockinfo").toString();
				LOG.info(orderId + "-" + productSaleId + " " + i++);
				ecOrderService.parseOrderStock(orderId, orderItemId, productSaleId, stockInfo);
				hibernateLazyResolver.releaseSession();
			}
			n += limit;
		}
	}
}
