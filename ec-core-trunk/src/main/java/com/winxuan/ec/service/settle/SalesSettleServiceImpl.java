package com.winxuan.ec.service.settle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.SalesSettleDao;
import com.winxuan.ec.model.settle.DailySalesItem;
import com.winxuan.framework.dynamicdao.InjectDao;
/**
 * 
 * @author wenchx
 * @version 1.0, 2014-8-4 上午11:15:18
 */
@Service("salesSettleService")
@Transactional(rollbackFor=Exception.class)
public class SalesSettleServiceImpl implements SalesSettleService{
	private static Log log = LogFactory.getLog(SalesSettleServiceImpl.class);
	private static final String FINDCOMPLEX_SQL="SELECT i.item ,p.listprice,i.childnum,i.quantiy "+
			"from interface_sap_complex i,product p,product_sale ps where i.item =ps.outerid and p.id=ps.product and p.complex=0 and ps.storagetype = 6001 and ps.shop=1 and i.complex=?";
	private static final String SEND_SAPORDERITEM_SQL="INSERT INTO interface_sap_sales(outerorder, outeritem, warehouse, returnflag, " +
			"customer, sapid, quantity, listprice, deliverytime, _order, orderitem,channel,createtime)" +
			" values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SS_SQL_QU="select count(*) total from  interface_sap_sales where outerorder=? and outeritem=?";
	@InjectDao
	private SalesSettleDao salesSettleDao;
	
	private JdbcTemplate jdbctemplateSap;
	
 
	@Override
	public int startReturnSales(int limit) {
		return returnSalesExecutor(limit);
	}
	@Override
	public int startRejectSales(int limit) {
		return rejectSalesExecutor(limit);
	}
	@Override
	public int start(int limit) {
		return deliverySalesExecutor(limit);
	}
	@Override
	public int deliverySalesExecutor(int limit) {
		List<String> orders=this.getDeliverySales(limit);
		if (orders.isEmpty()) {
			return orders.size();
		}
		List<DailySalesItem> itemList=this.getDeliverySalesItem(orders);
		List<DailySalesItem> saplist=this.chackOrderItemComplex(itemList);
		for(DailySalesItem item:saplist){//下传sap
			this.sendDailySalesItem(new Object[] {
					item.getOuterOrder(),item.getOuterItem(),item.getWarehouse(),
					item.getReturnFlag(),item.getCustomer(),item.getSapid(),
					item.getQuantity(),item.getListPrice(),item.getDeliverytime(),
					item.getOrder(),item.getOrderItem(),item.getChannel(),new Date()
				});
		}
		//更新下传状态
		String joinorder = "('" + StringUtils.join(orders,"','") + "')"; 
		String markSql = "INSERT INTO order_extend(_order,meta,_value) SELECT id,21,id FROM _order WHERE id IN " + joinorder;
		jdbctemplateSap.update(markSql);
		
		return orders.size();
	}

	@Override
	public int returnSalesExecutor(int limit) {
		List<String> orders=this.getReturnSales(limit);
		if (orders.isEmpty()) {
			return orders.size();
		}
		List<DailySalesItem> itemList=this.getReturnSalesItem(orders);
		List<DailySalesItem> saplist=this.chackOrderItemComplex(itemList);
		for(DailySalesItem item:saplist){//下传sap
			this.sendDailySalesItem(new Object[] {
					item.getOuterOrder(),item.getOuterItem(),item.getWarehouse(),
					item.getReturnFlag(),item.getCustomer(),item.getSapid(),
					item.getQuantity(),item.getListPrice(),item.getDeliverytime(),
					item.getOrder(),item.getOrderItem(),item.getChannel(),new Date()	
				});
		}
		//更新下传状态
		String joinorder = "('" + StringUtils.join(orders,"','") + "')"; 
		String markSql = "INSERT INTO order_extend(_order,meta,_value) SELECT originalorder,23,id FROM returnorder WHERE id IN " + joinorder;
		jdbctemplateSap.update(markSql);
		
		return orders.size();
	}

	@Override
	public int rejectSalesExecutor(int limit) {
		List<String> orders=this.getRejectSales(limit);
		if (orders.isEmpty()) {
			return orders.size();
		}
		List<DailySalesItem> itemList=this.getRejectSalesItem(orders);
		List<DailySalesItem> saplist=this.chackOrderItemComplex(itemList);
		for(DailySalesItem item:saplist){//下传sap
			this.sendDailySalesItem(new Object[] {
					item.getOuterOrder(),item.getOuterItem(),item.getWarehouse(),
					item.getReturnFlag(),item.getCustomer(),item.getSapid(),
					item.getQuantity(),item.getListPrice(),item.getDeliverytime(),
					item.getOrder(),item.getOrderItem(),item.getChannel(),new Date()	
				});
		}
		//更新下传状态
		String joinorder = "('" + StringUtils.join(orders,"','") + "')"; 
		String markSql = "INSERT INTO order_extend(_order,meta,_value) SELECT id,22,id FROM _order WHERE id IN " + joinorder;
		jdbctemplateSap.update(markSql);
		
		return orders.size();
	}
	/**
	 * 获取 未传SAP的已发货订单[已下传的中启订单]
	 * @return
	 */
	public List<String> getDeliverySales(int limit) {
		 String  sql="SELECT o.id FROM _order o "
					+" LEFT JOIN order_extend oe ON (oe._order = o.id AND oe.meta = 21) "
					+" WHERE oe.id IS NULL AND o.deliverytime IS NOT NULL AND o.processstatus IN (8004,8005,8009,8011) " 
					+" AND o.createtime >= '2013-06-01' "
					+" AND o.createtime > SUBDATE(NOW(),INTERVAL 3 MONTH) "
					+" AND o.transferresult != 35003 "
					+" AND o.shop=1 AND o.virtual = 0 AND o.deliveryquantity  > 0" +
					" limit "+limit;//发货订单
		return jdbctemplateSap.queryForList(sql,String.class);
	}
	/**
	 * 获取 未传SAP的已发货订单项[已下传的中启订单]
	 * @return
	 */
	public List<DailySalesItem> getDeliverySalesItem(List<String> orderlist) {
		Map<String, Object>  params=new HashMap<String, Object>();
		params.put("orderlist", orderlist);
		return salesSettleDao.getDeliveryOrderItem(params);
	}
	/**
	 * 获取退货订单数据
	 * @return
	 */
	public List<String> getReturnSales(int limit) {
		 String sql="SELECT ro.id FROM _order o "+
					"	INNER JOIN returnorder ro ON (ro.originalorder = o.id) "+
					"	LEFT JOIN order_extend oe ON (oe._order = o.id AND oe.meta = 23 AND oe._value = ro.id) "+
					"WHERE oe.id IS NULL AND ro.refundtime IS NOT NULL "+
					"AND o.createtime >= '2013-06-01' "+
					"AND ro.refundtime > SUBDATE(NOW(),INTERVAL 1 MONTH) "+ 
					"AND o.transferresult != 35003 "+
					"AND o.shop=1 AND o.virtual = 0 limit "+limit;//退货
		 return jdbctemplateSap.queryForList(sql,String.class);
	}
	/**
	 * 获取退货订单项数据
	 * @return
	 */
	public List<DailySalesItem> getReturnSalesItem(List<String> orderlist) {
		Map<String, Object>  params=new HashMap<String, Object>();
		params.put("orderlist", orderlist);
		return salesSettleDao.getReturnSalesItem(params);
	}
	/**
	 * 获取拒收订单数据
	 * @return
	 */
	public List<String> getRejectSales(int limit) {
		String sql="SELECT o.id FROM _order o "+
				"LEFT JOIN order_extend oe ON (oe._order = o.id AND oe.meta = 22 ) "+
				"WHERE oe.id IS NULL AND o.deliverytime IS NOT NULL AND o.processstatus IN (8009) "+
				"AND o.createtime >= '2013-06-01' "+
				"AND o.transferresult != 35003 "+
				"AND o.shop=1 AND o.virtual = 0 AND o.deliveryquantity  > 0 limit "+limit;//退货
		 return jdbctemplateSap.queryForList(sql,String.class);
	}
	/**
	 * 获取拒收订单项数据
	 * @return
	 */
	public List<DailySalesItem> getRejectSalesItem(List<String> orderlist) {
		Map<String, Object>  params=new HashMap<String, Object>();
		params.put("orderlist", orderlist);
		return salesSettleDao.getRejectSalesItem(params);
	}
	/**
	 * 把结算的订单写入接口表
	 * @param params
	 */
	public void sendDailySalesItem(Object[] params) {
		int total=jdbctemplateSap.queryForInt(SS_SQL_QU,params[0],params[1]);
		if(total<=0){
			jdbctemplateSap.update(SEND_SAPORDERITEM_SQL, params);
		}
	}
	
	public List<DailySalesItem> chackOrderItemComplex(List<DailySalesItem> itemList) {
		List<DailySalesItem> sapList = new ArrayList<DailySalesItem>();
		for(DailySalesItem item:itemList){
			if(item.isComplex()){
				//针对订单中包含的实物套装，增加如下的规则生成实物套装的销售数据:
				//单号第一位+订单行向+套装SAPID+Y
				String complexOuterOrder=String.valueOf(item.getOuterOrder().charAt(0))+(item.getOuterItem()+Long.parseLong(item.getSapid()))+"Y";
				item.setOuterOrder(complexOuterOrder);
				List<Map<String,Object>> complexitem= jdbctemplateSap.queryForList(FINDCOMPLEX_SQL,item.getSapid());
				if(complexitem!=null&&complexitem.size()>0
						&&complexitem.size()==Integer.parseInt(complexitem.get(0).get("childnum").toString())){//子商品
		     		for(Map<String,Object> vo:complexitem){
		     			DailySalesItem orderItemNew=new DailySalesItem();
						try {
							BeanUtils.copyProperties(orderItemNew,item);
						} catch (Exception e) {
							log.info("chackOrderItemComplex  exception",e);
							break;
						}
		     			orderItemNew.setListPrice(new BigDecimal(vo.get("listprice").toString())
		     			                         .multiply(new BigDecimal(vo.get("quantiy").toString())));
		     			orderItemNew.setSapid(vo.get("item").toString());
		     			//取订单号第一位+订单行项+sapid+x
		     			String outerOrder=String.valueOf(item.getOuterOrder().charAt(0))+(item.getOuterItem()+Long.parseLong(orderItemNew.getSapid()))+"X";
		     			orderItemNew.setOuterOrder(outerOrder);
		     			orderItemNew.setQuantity(item.getQuantity()*(Integer)vo.get("quantiy"));
		     			if(item.getReturnFlag()!=null&&"X".equals(item.getReturnFlag())){
		     				String order=String.valueOf(item.getOrder().charAt(0))+(item.getOrderItem()+Long.parseLong(orderItemNew.getSapid()))+"X";
		     				orderItemNew.setOrder(order);
		     			}
		     			sapList.add(orderItemNew);
		     		}
				}
				sapList.add(item);
			}else{
	     		sapList.add(item);
			}
		}
		return sapList;
	}

	public void setJdbctemplateSap(JdbcTemplate jdbctemplateSap) {
		this.jdbctemplateSap = jdbctemplateSap;
	}
}
