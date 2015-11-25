package com.winxuan.ec.task.service.ec.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.OrderExtend;
import com.winxuan.ec.task.dao.ec.EcProductDao;
import com.winxuan.ec.task.dao.robot.RobotProductDao;
import com.winxuan.ec.task.dao.robot.RobotTaskDao;
import com.winxuan.ec.task.model.ec.EcProductCategoryStatus;
import com.winxuan.ec.task.model.ec.convert.ChannelSalesInfo;
import com.winxuan.ec.task.model.robot.RobotProduct;
import com.winxuan.ec.task.model.robot.RobotProductCategoryLog;
import com.winxuan.ec.task.model.robot.RobotTask;
import com.winxuan.ec.task.service.ec.EcProductService;

/**
 * 实现类
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
@Service("ecProductService")
@Transactional(rollbackFor=Exception.class)
public class EcProductServiceImpl implements EcProductService {
	private static final long serialVersionUID = 2808419021149544394L;
	private static final int DEFAULT_PRIORITY = 7;
	private static final String FINDCOMPLEX_SQL="SELECT i.item ,p.listprice ,i.childnum,i.quantiy "+
			"from interface_sap_complex i,product p,product_sale ps where i.item =ps.outerid and p.id=ps.product  and p.complex = 0  "
			+ "and ps.storagetype = 6001 and  ps.shop=1 and i.complex=?";
	private static final  String SAVESQL = "INSERT INTO interface_channel_sales(outerorder, outeritem, bill, warehouse, returnflag, " 
			+ "customer, sapid, quantity,  _order, orderitem, channel, sdate, deliveryquantity, settle, historysettle, type, listprice)"
			+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static Log log = LogFactory.getLog(EcProductServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	
	@Autowired
	private EcProductDao ecProductDao;
	
	@Autowired
	private RobotTaskDao robotTaskDao;
	
	@Autowired
	private RobotProductDao robotProductDao;
	
	
	
	

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	@Override
	public List<EcProductCategoryStatus> getNewProducts(int max) {
		return ecProductDao.getNewProducts(max);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void syncProductToTask(List<EcProductCategoryStatus> list) {
		if(!list.isEmpty()){
			int length = list.size();
			String[] params = new String[length];
			for (int i = 0 ; i < length; i++){
				params[i] = list.get(i).getIsbn();
			}
			//添加到robot
			robotTaskDao.addTasks(DEFAULT_PRIORITY, RobotTask.TARGET_TYPE_AMAZON_BOOK, params);
			//更新状态
			ecProductDao.updateIsNew(list);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	@Override
	public List<EcProductCategoryStatus> getMcProducts(Date minDate, int start,
			int limit) {
		return ecProductDao.getMcProducts(minDate, start, limit);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public int syncProductRelation(List<EcProductCategoryStatus> list) {
		//实际同步数量
		int total = 0;
		if(list != null && !list.isEmpty()){
			
			//获取这批商品的ISBN号
			String[] barcodes = new String[list.size()];
			for (int i = 0 ; i < barcodes.length; i++){
				barcodes[i] = list.get(i).getIsbn();
			}
			
			//批量查找这批ISBN号 对应的 robotID
			List<RobotProduct> robotProducts = robotProductDao.findProductByBarcode(barcodes);
			
			if (robotProducts != null && !robotProducts.isEmpty()){
				List<Long> productIds = new ArrayList<Long>();
				
				for (RobotProduct robotProduct : robotProducts) {
					
					//找到,能匹配卓越ISBN商品的    EC商品
					for (EcProductCategoryStatus p : list){
						if (p.getIsbn().equals(robotProduct.getBarcode())) {
							productIds.add(p.getProduct());
						}
					}
					//批量清除原有商品关系
					ecProductDao.deleteProductRelation(productIds);
					//为这批商品添加,卓越的关系.. 根据robotID映射到  文轩实际分类中的ID
					for (long productId : productIds){
						ecProductDao.syncProductCategory(productId, robotProduct.getRobots());
					}
					//批量更新这批商品的 关系种类为 amazon
					ecProductDao.updateProductStatus(EcProductCategoryStatus.CATEGORY_STATUS_AMAZON, productIds);
					
					total += productIds.size();
					productIds.clear();
				}
			
			}
			
		}
		return total;
	}
	
	@Override
    public int syncProductNewCategory(int limit) {
        List<RobotProductCategoryLog> logs = robotProductDao.productCategoryUnprocessedLog(limit);
        int size = logs.size();
        if (!logs.isEmpty()) {
            //重置EC 商品分类状态
            ecProductDao.resetEcProductStatus(logs);
            
            //修改日志处理状态
            robotProductDao.updateLogStatus(logs, RobotProductCategoryLog.STATUS_PROCESSED);
            
        }
        return size;
    }

    @Override
    public int syncProductDescription(int limit) {
        return ecProductDao.syncProductDescription(limit);
    }

	@Override
	public int sumSales(int limit) {
		
		//查找订单发货数量大于0。并且没参与过统计的订单
		String deliverySql = "SELECT o.id FROM _order o"
					+ "	LEFT JOIN order_extend oe ON (oe._order = o.id AND meta = ?)"
					+ " WHERE o.deliverytime > SUBDATE(NOW(),INTERVAL 4 MONTH) AND o.deliverytime < CONCAT(LEFT(NOW(),8),'01') AND oe.id IS NULL "
					+ " LIMIT ?"
					;
		List<String> orders = jdbcTemplateEcCore.queryForList(deliverySql, new Object[]{OrderExtend.STATISTICS_DELIVERY,  limit},String.class);
		if (orders.isEmpty()) {
			return orders.size();
		}
		String joinorder = "('" + StringUtils.join(orders,"','") + "')"; 
		
		//将这部分订单添加到销售历史表,如果已经存在,则不在添加了
		String addDateSql = "INSERT INTO channel_sales_history(channel,productsale,sales,refund)"
							+ " SELECT DISTINCT o.channel,oi.productsale,0,0 FROM _order o"
							+ " 	INNER JOIN order_item oi ON (oi._order = o.id) "
							+ " 	LEFT JOIN channel_sales_history csd ON (csd.channel = o.channel AND csd.productsale = oi.productsale)"
							+ "	WHERE csd.id IS NULL AND o.id IN " + joinorder
							;
		jdbcTemplateEcCore.update(addDateSql);
		
		//累计销售
		String statisticsSql = "UPDATE channel_sales_history csh , " 
								+ "(SELECT o.channel,oi.productsale,SUM(oi.deliveryquantity) as 'sales' FROM _order o"
								+ " INNER JOIN order_item oi ON (oi._order = o.id)"
								+ " WHERE o.id IN " + joinorder
								+ " GROUP BY o.channel,oi.productsale) t"
								+ " SET csh.sales = csh.sales + t.sales"
								+ " WHERE csh.channel = t.channel AND csh.productsale = t.productsale"
								;
		jdbcTemplateEcCore.update(statisticsSql);
		
		//将已统计的订单添加到orderExtend,做标识，
		//原因：DBA不允许在订单表上扩充字段.就在OrderExtend表上,通过meta = 31做的标识
		//拒收,退货同理.退货_value为退货单号
		String markSql = "INSERT INTO order_extend(_order,meta,_value) SELECT id,?,id FROM _order WHERE id IN " + joinorder;
		jdbcTemplateEcCore.update(markSql,OrderExtend.STATISTICS_DELIVERY);
		
		return orders.size();
	}

	@Override
	public int sumReject(int limit) {
		
		String rejectSql = "SELECT o.id FROM _order o"
						+ " LEFT JOIN order_extend oe ON (oe._order = o.id AND meta = ?)"
						+ " WHERE o.processstatus = ? AND oe.id IS NULL AND o.deliverytime > SUBDATE(NOW(),INTERVAL 4 MONTH) AND o.lastprocesstime < CONCAT(LEFT(NOW(),8),'01')"
						+ " LIMIT ?"
						;
		
		
		int meta = OrderExtend.STATISTICS_REJECT;
		Long status = Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL;
		List<String> orders = jdbcTemplateEcCore.queryForList(rejectSql,new Object[]{meta, status , limit},String.class);
		
		
		if (orders.isEmpty()) {
			return 0;
		}
		String joinorder = "('" + StringUtils.join(orders,"','") + "')"; 
		
		//将这部分订单添加到销售历史表,如果已经存在,则不在添加了
		String addDateSql = "INSERT INTO channel_sales_history(channel,productsale,sales,refund)"
							+ " SELECT DISTINCT o.channel,oi.productsale,0,0 FROM _order o"
							+ " 	INNER JOIN order_item oi ON (oi._order = o.id) "
							+ " 	LEFT JOIN channel_sales_history csd ON (csd.channel = o.channel AND csd.productsale = oi.productsale)"
							+ "	WHERE csd.id IS NULL AND o.id IN " + joinorder
							;
		jdbcTemplateEcCore.update(addDateSql);
		
		
		//累计--拒收, 拒收相当于是整单退货,则退货数量为发货数量
		String statisticsSql = "UPDATE channel_sales_history csh , " 
								+ "(SELECT o.channel,oi.productsale,SUM(oi.deliveryquantity) as 'refund' FROM _order o"
								+ " INNER JOIN order_item oi ON (oi._order = o.id)"
								+ " WHERE o.id IN " + joinorder
								+ " GROUP BY o.channel,oi.productsale) t"
								+ " SET csh.refund = csh.refund + t.refund"
								+ " WHERE csh.channel = t.channel AND csh.productsale = t.productsale"
								;
		jdbcTemplateEcCore.update(statisticsSql);
		
		// 加上拒收统计标记.
		String markSql = "INSERT INTO order_extend(_order,meta,_value) SELECT id,?,id FROM _order WHERE id IN " + joinorder;
		jdbcTemplateEcCore.update(markSql,OrderExtend.STATISTICS_REJECT);
		return orders.size();
	}

	@Override
	public int sumRefund(int limit) {
		
		String refundSql = "SELECT ro.id FROM returnorder ro"
						+	" 	INNER JOIN _order o ON (o.id = ro.originalorder)"
						+	" 	LEFT JOIN order_extend oe ON (oe._order = ro.originalorder AND oe.meta = 33 AND oe._value = ro.id)"
						+	" WHERE oe.id IS NULL AND ro.refundtime > SUBDATE(NOW(),INTERVAL 4 MONTH) AND ro.refundtime < CONCAT(LEFT(NOW(),8),'01')"
						+	" AND o.processstatus IN (8004,8005,8009,8011) AND o.deliveryquantity > 0"
						+	" LIMIT " + limit
						;
		
		List<Long> returnorders = jdbcTemplateEcCore.queryForList(refundSql,Long.class);
		if (returnorders.isEmpty()){
			return 0;
		}
		
		String joinreturnorder = "(" + StringUtils.join(returnorders,",") + ")";
		
		//将这部分订单添加到销售历史表,如果已经存在,则不在添加了
		String addDateSql = "INSERT INTO channel_sales_history(channel,productsale,sales,refund)"
							+ " SELECT DISTINCT o.channel,oi.productsale,0,0 FROM returnorder ro"
							+ " 	INNER JOIN returnorder_item ri ON (ri.returnorder = ro.id)"
							+ " 	INNER JOIN order_item oi ON (oi.id = ri.orderitem)"
							+ " 	INNER JOIN _order o ON (o.id = oi._order)"
							+ " 	LEFT JOIN channel_sales_history csd ON (csd.channel = o.channel AND csd.productsale = oi.productsale)"
							+ " WHERE csd.id IS NULL AND ro.id IN " + joinreturnorder
							;
		jdbcTemplateEcCore.update(addDateSql);
		
		//累计--退货
		String statisticsSql = "UPDATE channel_sales_history csh, "
						+	"(SELECT o.channel, oi.productsale,SUM(ri.realquantity) AS 'refund' FROM returnorder ro"
						+	" 	INNER JOIN _order o ON (o.id = ro.originalorder)"
						+	" 	INNER JOIN returnorder_item ri ON (ri.returnorder = ro.id)"
						+	" 	INNER JOIN order_item oi ON (oi.id = ri.orderitem)"
						+	" 	WHERE ri.realquantity > 0 AND ro.id IN " + joinreturnorder
						+	" 	GROUP BY o.channel,oi.productsale ) t"
						+	" SET csh.refund = csh.refund + t.refund"
						+	" WHERE csh.channel = t.channel AND csh.productsale = t.productsale"
						;
		jdbcTemplateEcCore.update(statisticsSql);
		
		// 加上退货统计标记.
		String markSql = "INSERT INTO order_extend(_order,meta,_value) SELECT originalorder,?,id FROM returnorder WHERE id IN " + joinreturnorder;
		jdbcTemplateEcCore.update(markSql,OrderExtend.STATISTICS_RETURN);
		
		return returnorders.size();
		
	}

	@Override
	public void mergeSuning() {
		
		//合并苏宁8090，40的销售数据, 有相同商品的.直接将8090的销售 累加到 40上
		String sumSql = " UPDATE channel_sales_history csh,"
						+	" (SELECT productsale,sales,refund FROM channel_sales_history WHERE channel = 8090) t"
						+	" SET csh.sales = csh.sales + t.sales , csh.refund = csh.refund +t.refund"
						+	" WHERE t.productsale = csh.productsale AND csh.channel = 40"
						;
		
		
		//将苏宁8090渠道中的商品添加 到40。  条件：8090的商品在40中不存在
		String appendSql = "INSERT INTO channel_sales_history(channel,productsale,sales,refund)"
						+ " SELECT 40, suning8090.productsale,suning8090.sales, suning8090.refund"
						+ " FROM channel_sales_history suning8090"
						+ "  LEFT JOIN channel_sales_history suning40 ON ( suning40.channel = 40 AND suning40.productsale = suning8090.productsale)"
						+ " WHERE suning8090.channel = 8090 AND suning40.id IS NULL "
						;
		//清理8090的商品,避免再次合并
		String cleanSql = "DELETE FROM channel_sales_history WHERE channel = 8090";
		
		jdbcTemplateEcCore.update(sumSql);
		jdbcTemplateEcCore.update(appendSql);
		jdbcTemplateEcCore.update(cleanSql);
		
		
		
		
	}

	
	@Override
	public List<ChannelSalesInfo> sumChannelSales(List<ChannelSalesInfo> list, int limit) {
		int srId = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			srId = Integer.valueOf(list.get(list.size()-1).getOuteritem().toString());
		}
		String sql =  "SELECT '11111111111111' AS 'outerorder', sr.id AS 'outeritem', sr.id AS 'bill', ssr.operator," +
				" ps.outerid sapId, sr.sales as quantity,sr.id as orderitem, sur.channel, sr.enddate, sr.sales as deliveryquantity,"
				+ " sr.sales AS settle,sr.sales AS historysettle, p.listprice,if(p.complex=2,1,0) as complex,'I' type ," +
				" '11111111111111' AS 'order',sr.id AS 'orderitem'  FROM sales_record sr "
				+ " INNER JOIN sales_upload_record sur ON (sur.id = sr.uploadrecord)"
				+ " INNER JOIN product_sale ps ON (ps.id = sr.productsale)"
				+ " INNER JOIN product p ON (p.id = ps.product)"
				+ " INNER JOIN sales_sap_record ssr ON (ssr.id = sr.saprecord)"
				+ " WHERE sr.status = ? AND sr.sales > 0 AND sr.id >" + srId + " ORDER BY sr.id LIMIT " + limit;
		List<ChannelSalesInfo> channelSalesInfos = jdbcTemplateEcCore.query(sql, new Object[]{Code.CHANNELSALES_SAP_PREPARE}, 
				new BeanPropertyRowMapper<ChannelSalesInfo>(ChannelSalesInfo.class));
		if (CollectionUtils.isNotEmpty(channelSalesInfos)) {
			this.sendtoSap(channelSalesInfos);
		}
		return channelSalesInfos;
	}

	@Override
	public List<ChannelSalesInfo> sumChannelReturun(List<ChannelSalesInfo> list, int limit) {
		int srId = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			srId = Integer.valueOf(list.get(list.size()-1).getOuteritem().toString());
		}
		
		String sql =  "SELECT '22222222222222' AS 'outerorder',sr.id AS 'outeritem', sr.id AS 'bill', ssr.operator, ps.outerid sapId, "
				+ "sr.refund as quantity,sr.id as orderitem, sur.channel, sr.enddate, sr.refund as deliveryquantity,"
				+ " sr.refund AS settle,sr.refund AS historysettle, p.listprice ,if(p.complex=2,1,0) as complex,'I' type ," +
				"'11111111111111' AS 'order',sr.id AS 'orderitem','X' returnflag FROM sales_record sr "
				+ " INNER JOIN sales_upload_record sur ON (sur.id = sr.uploadrecord)"
				+ " INNER JOIN product_sale ps ON (ps.id = sr.productsale)"
				+ " INNER JOIN product p ON (p.id = ps.product)"
				+ " INNER JOIN sales_sap_record ssr ON (ssr.id = sr.saprecord)"
				+ " WHERE sr.status = ? AND sr.refund > 0  AND sr.id > " + srId + " ORDER BY sr.id LIMIT " + limit;
		List<ChannelSalesInfo> channelSalesInfos = jdbcTemplateEcCore.query(sql, new Object[]{Code.CHANNELSALES_SAP_PREPARE},
				new BeanPropertyRowMapper<ChannelSalesInfo>(ChannelSalesInfo.class));
		if (CollectionUtils.isNotEmpty(channelSalesInfos)) {
			this.sendtoSap(channelSalesInfos);
		}
		return channelSalesInfos;
	}
	
	
	@Override
	public int updateSalesRecord(int limit) {
		String updateSql = "UPDATE sales_record sr SET sr.status = 91007 WHERE sr.status = 91004 LIMIT " + limit;
		return jdbcTemplateEcCore.update(updateSql);
	}

	@Override
	public List<ChannelSalesInfo> sumRollbackSales(List<ChannelSalesInfo> list, int limit) {
		int srId = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			srId = Integer.valueOf(list.get(list.size()-1).getOuteritem().toString());
		}
		
		String sql =  "SELECT '33333333333333' AS 'outerorder',sr.id AS 'outeritem', sr.id AS 'bill', ssr.operator, " +
				"ps.outerid sapId, sr.sales as quantity,sr.id as orderitem, sur.channel, sr.enddate, sr.sales as deliveryquantity,"
				+ " sr.sales AS settle,sr.sales AS historysettle, p.listprice,if(p.complex=2,1,0) as complex ,'R' type ," +
				"'11111111111111' AS 'order',sr.id AS 'orderitem' FROM sales_record sr "
				+ " INNER JOIN sales_upload_record sur ON (sur.id = sr.uploadrecord)"
				+ " INNER JOIN product_sale ps ON (ps.id = sr.productsale)"
				+ " INNER JOIN product p ON (p.id = ps.product)"
				+ " INNER JOIN sales_sap_record ssr ON (ssr.id = sr.saprecord)"
				+ " WHERE sr.status = ? AND sr.sales > 0 AND sr.id >" + srId + " ORDER BY sr.id LIMIT " + limit;
		List<ChannelSalesInfo> channelSalesInfos = jdbcTemplateEcCore.query(sql, new Object[]{Code.CHANNELSALES_ROLLBACK_PREPARE}, 
				new BeanPropertyRowMapper<ChannelSalesInfo>(ChannelSalesInfo.class));
		if (CollectionUtils.isNotEmpty(channelSalesInfos)) {
			this.sendtoSap(channelSalesInfos);
		}
		return channelSalesInfos;
	}

	@Override
	public List<ChannelSalesInfo> sumRollbackReturn(List<ChannelSalesInfo> list, int limit) {
		int srId = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			srId = Integer.valueOf(list.get(list.size()-1).getOuteritem().toString());
		}
		
		String sql =  "SELECT '44444444444444' AS 'outerorder',sr.id AS 'outeritem', sr.id AS 'bill', ssr.operator, ps.outerid sapId, "
				+ "sr.refund as quantity,sr.id as orderitem, sur.channel, sr.enddate, sr.refund as deliveryquantity,"
				+ " sr.refund AS settle,sr.refund AS historysettle, p.listprice,if(p.complex=2,1,0) as complex ,'R' type ," +
				"'11111111111111' AS 'order',sr.id AS 'orderitem','X' returnflag FROM sales_record sr "
				+ " INNER JOIN sales_upload_record sur ON (sur.id = sr.uploadrecord)"
				+ " INNER JOIN product_sale ps ON (ps.id = sr.productsale)"
				+ " INNER JOIN product p ON (p.id = ps.product)"
				+ " INNER JOIN sales_sap_record ssr ON (ssr.id = sr.saprecord)"
				+ " WHERE sr.status = ? AND sr.refund > 0  AND sr.id > " + srId + " ORDER BY sr.id LIMIT " + limit;
		List<ChannelSalesInfo> channelSalesInfos = jdbcTemplateEcCore.query(sql, new Object[]{Code.CHANNELSALES_ROLLBACK_PREPARE},
				new BeanPropertyRowMapper<ChannelSalesInfo>(ChannelSalesInfo.class));
		if (CollectionUtils.isNotEmpty(channelSalesInfos)) {
			this.sendtoSap(channelSalesInfos);
		}
		return channelSalesInfos;
	}

	@Override
	public int updateRollbackRecord(int limit) {
		String updateSql = "UPDATE sales_record sr SET sr.status = 91011 WHERE sr.status = 91009 LIMIT " + limit;
		return jdbcTemplateEcCore.update(updateSql);
	}
	/**
	 * 获取参数
	 * @param params
	 * @param csi
	 * @param outerorder
	 * @param returnflag
	 * @param type
	 * @return   
	 * @return List<Object>   
	 * @throws
	 */
	private List<Object> getParams(List<Object>  params,ChannelSalesInfo csi){
		params.clear();
		params.add(csi.getOuterOrder());
		params.add(csi.getOuteritem());
		params.add(csi.getBill());
		params.add("8A12");
		params.add(csi.getReturnflag());
		params.add(csi.getOperator());
		params.add(csi.getSapId());
		params.add(csi.getQuantity());
		params.add(csi.getOrder());
		params.add(csi.getOrderitem());
		params.add(csi.getChannel());
		params.add(csi.getEnddate());
		params.add(csi.getDeliveryquantity());
		params.add(csi.getSettle());
		params.add(csi.getHistorysettle());
		params.add(csi.getType());
		params.add(csi.getListprice());
		return params;
	}
	private List<ChannelSalesInfo> splistComplex(ChannelSalesInfo item){
	    List<ChannelSalesInfo> saplist=new ArrayList<ChannelSalesInfo>();
		List<Map<String,Object>> complexitem= jdbcTemplateEcCore.queryForList(FINDCOMPLEX_SQL,item.getSapId());
		if(complexitem!=null&&complexitem.size()>0
				&&complexitem.size()==Integer.parseInt(complexitem.get(0).get("childnum").toString())){//子商品
     		for(Map<String,Object> vo:complexitem){
     			ChannelSalesInfo orderItemNew=new ChannelSalesInfo();
				try {
					BeanUtils.copyProperties(orderItemNew,item);
				} catch (Exception e) {
					log.info("splistComplex  exception",e);
					break;
				}
	     			orderItemNew.setListprice(vo.get("listprice").toString());
	     			orderItemNew.setSapId(vo.get("item").toString());
	     			orderItemNew.setQuantity(item.getQuantity()*(Integer)vo.get("quantiy"));
	     			orderItemNew.setDeliveryquantity(item.getDeliveryquantity()*(Integer)vo.get("quantiy"));
	     			//单号取第一位+订单行项+sapid+x
	     			String outerOrder=String.valueOf(item.getOuterOrder().charAt(0))+(item.getOuteritem()+Long.parseLong(orderItemNew.getSapId()))+"X";
	     			orderItemNew.setOuterOrder(outerOrder);
	     			String order=String.valueOf(item.getOrder().charAt(0))+(item.getOrderitem()+Long.parseLong(orderItemNew.getSapId()))+"X";
	     			 orderItemNew.setOrder(order);
	     			saplist.add(orderItemNew);
     			}
     		}
		return saplist;
	}
	/**
	 * 下传sap
	 *@param channelSalesInfos   
	 * @return void   
	 * @throws
	 */
	private void sendtoSap(List<ChannelSalesInfo> channelSalesInfos){
		List<Object> params = new ArrayList<Object>();
		for(ChannelSalesInfo csi : channelSalesInfos) {
			if(csi.isComplex()){
				List<ChannelSalesInfo> returnlist=this.splistComplex(csi);
				for(ChannelSalesInfo entity:returnlist){
					getParams(params, entity);
					jdbcTemplateEcCore.update(SAVESQL, params.toArray());
				}
			}else{
				 getParams(params, csi);
				 jdbcTemplateEcCore.update(SAVESQL, params.toArray());
			 }
		}
		
	}

	@Override
	public int syncResponsibilityInfo(int limit) {
		// TODO Auto-generated method stub
		return ecProductDao.syncResponsibilityInfo(limit);
	}
	
}
