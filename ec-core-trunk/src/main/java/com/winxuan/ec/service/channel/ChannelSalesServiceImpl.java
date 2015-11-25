package com.winxuan.ec.service.channel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ChannelSalesDao;
import com.winxuan.ec.dao.CodeDao;
import com.winxuan.ec.exception.ChannelSalesException;
import com.winxuan.ec.model.channel.ChannelSalesProduct;
import com.winxuan.ec.model.channel.ChannelSalesRecord;
import com.winxuan.ec.model.channel.ChannelSalesSapRecord;
import com.winxuan.ec.model.channel.ChannelSalesUploadRecord;
import com.winxuan.ec.model.channel.SalesOverRefund;
import com.winxuan.ec.model.channel.SalesRecordDetail;
import com.winxuan.ec.model.channel.SalesRecordDto;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 实现
 * @author heyadong
 *
 */
@Service
@Transactional
public class ChannelSalesServiceImpl implements ChannelSalesService, InitializingBean {
	private static final Log LOG = LogFactory.getLog(ChannelSalesServiceImpl.class);
	
	private static final String UNMAPPED = "1";
	private static final String OVER_SELLING = "2";
	private static final String OVER_RETURN = "3";
	private static final String REPEAT = "4";
	
	//超卖SQL
	private static final String OVER_SALE_SQL = " SELECT c.id, c.status,c.productsale,c.uploadrecord from sales_record sr " + 
			" INNER JOIN ( SELECT t.id, t.status,t.productsale,t.uploadrecord FROM channel_sales_history csh " +
			" RIGHT JOIN (SELECT sur.channel,sr.productsale,SUM(sr.sales) sales,SUM(sr.refund) refund, sr.id, sr.status,sr.uploadrecord " + 
			" FROM sales_upload_record sur INNER JOIN sales_record sr ON (sr.uploadrecord = sur.id) " + 
			" WHERE  sur.id IN(%s) AND sr.status NOT IN(1,2,3,4) AND sr.productsale IS NOT NULL " + 
			" AND sr.sales>0  GROUP BY sr.productsale ) t ON (csh.productsale = t.productsale AND csh.channel=t.channel) " +
			" WHERE (csh.sales - csh.refund < t.sales - t.refund) OR csh.id IS NULL) c " +
			" ON sr.productsale=c.productsale " +
			" WHERE sr.uploadrecord=%s AND sr.sales>0";
	//超退SQL
	private static final String OVER_RETURN_SQL = "SELECT * FROM " +
				" (SELECT sr.id,sr.status,sr.productsale,sr.uploadrecord,sr.channelproduct FROM sales_upload_record sur " + 
				" INNER JOIN sales_record sr  ON sr.uploadrecord = sur.id " + 
				" WHERE sur.id IN(%s) AND sr.status NOT IN(1,2,3,4) AND sr.productsale IS NOT NULL" + 
				" GROUP BY channelproduct HAVING SUM(sr.sales) < SUM(sr.refund)) b";

	@Autowired
	ApplicationContext applicationContext;
	
	@InjectDao
	ChannelSalesDao channelSalesDao;
	
	@InjectDao
	CodeDao codeDao;
	
	JdbcTemplate jdbcTemplate;
	
	ChannelSalesService channelSalesService;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Long transcationUpload(ChannelSalesUploadRecord uploadrecord, List<ChannelSalesRecord> records) {
		uploadrecord.setStatus(new Code(Code.CHANNELSALES_UPLOADED));
		channelSalesDao.save(uploadrecord);
		Long uploadrecordId = uploadrecord.getId();
		
		//获取本次上传商品的对应关系
		Map<String, Long> productMapping = new HashMap<String, Long>();
		for (ChannelSalesRecord r : records) {
			productMapping.put(r.getChannelProduct(), null);
		}
		String joinProduct = "('" + StringUtils.join(productMapping.keySet(),"','") + "')" ;
		String sql = "SELECT channelproduct,productsale FROM sales_channel_product WHERE channel = ? AND channelproduct IN " + joinProduct;
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,uploadrecord.getChannel().getId());
		for (Map<String,Object> m : list){
			productMapping.put(m.get("channelproduct").toString(), Long.valueOf(m.get("productsale").toString()));
		}
		
		List<Object> params = new ArrayList<Object>();
		String recordSql = "INSERT INTO sales_record(uploadrecord,startdate,enddate,channelproduct,productsale,sales,refund,status) VALUES ";
		StringBuilder sb = new StringBuilder(recordSql);

		
		
		int count = 0;
		for (int i = 0; i < records.size(); i++, count++){
			ChannelSalesRecord r = records.get(i);
			sb.append("(?,?,?,?,?,?,?,?),");
			params.add(uploadrecordId);
			params.add(r.getStartdate());
			params.add(r.getEnddate());
			params.add(r.getChannelProduct());
			params.add(productMapping.get(r.getChannelProduct()));
			params.add(r.getSales());
			params.add(r.getRefund());
			params.add(Code.CHANNELSALES_UPLOADED);
			if (count >= 1000){
				sb.deleteCharAt(sb.length() - 1);
				jdbcTemplate.update(sb.toString(), params.toArray());
				
				
				params.clear();
				count = 0;
				sb = new StringBuilder(recordSql);
			}
		}
		if (count > 0){
			sb.deleteCharAt(sb.length() - 1);
			jdbcTemplate.update(sb.toString(), params.toArray());
			params.clear();
		}
		return uploadrecordId;
	}	
	
	
	@Override
	public void upload(ChannelSalesUploadRecord uploadrecord, List<ChannelSalesRecord> records) {
		//上传记录独立一个事务保存,  原因.与数据验证逻辑在同一个事务会导致超时
		Long uploadrecordId = channelSalesService.transcationUpload(uploadrecord, records);
		uploadrecord = channelSalesDao.get(uploadrecordId);
		
		//上传发货码洋，退货码洋统计更新
		String priceSql = "SELECT IFNULL(SUM(sr.sales * p.listprice),0) AS 'sales', IFNULL(SUM(sr.refund * p.listprice),0) AS 'refund' FROM sales_record sr "
						+ " INNER JOIN product_sale ps ON (ps.id = sr.productsale ) "
						+ " INNER JOIN product p ON (p.id = ps.product)"
						+ " WHERE sr.uploadrecord = ?"
						;
		Map<String,Object> priceMap = jdbcTemplate.queryForMap(priceSql, uploadrecordId);
		uploadrecord.setDeliveryListprice(new BigDecimal(priceMap.get("sales").toString()));
		uploadrecord.setRefundListprice(new BigDecimal(priceMap.get("refund").toString()));
		
		//未匹配上的商品个数
		int x = jdbcTemplate.queryForInt("SELECT COUNT(DISTINCT channelproduct) FROM sales_record  WHERE productsale IS NULL AND uploadrecord = ?", uploadrecordId);
		
		List<SalesRecordDto> overSells = this.overSell(uploadrecordId);
		int f = CollectionUtils.isEmpty(overSells) ? 0 : overSells.size();
		LOG.info("超卖个数：" +  f);
		
		List<SalesOverRefund> overRefunds = this.overRefund(uploadrecordId);
		int t = CollectionUtils.isEmpty(overRefunds) ? 0 : overRefunds.size();
		LOG.info("超退个数：" +  t);
		
		
		uploadrecord.setSysmsg(String.format("商品未匹配:%s, 超卖:%s, 超退%s",x, f, t));
		
		uploadrecord.appendStatusLog(uploadrecord.getUploader(),
				codeDao.get(x + f + t == 0 ? Code.CHANNELSALES_SUCESSFUL : Code.CHANNELSALES_FAILD));
		channelSalesDao.save(uploadrecord);

	}
	@Override
	public List<ChannelSalesUploadRecord> find(Map<String, Object> params,
			 Pagination pagination) {
		return channelSalesDao.find(params, pagination);
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public void delete(Long uploadrecordId, Employee operator) {
		ChannelSalesUploadRecord uploadrecord =  channelSalesDao.get(uploadrecordId);
		if (uploadrecord != null && uploadrecord.isAllowDelete()) {
			uploadrecord.appendStatusLog(operator, codeDao.get(Code.CHANNELSALES_DELETEED));
			channelSalesDao.save(uploadrecord);
		}
	}
	
	@Override
	public ChannelSalesUploadRecord get(Long id){
		return  channelSalesDao.get(id);
	}
	
	@Override
	public List<ChannelSalesProduct> findChannelSalesProduct(
			Map<String, Object> params, Pagination pagination, Short order) {
		return channelSalesDao.findChannelSalesProduct(params, pagination, order);
	}
	@Override
	public void deleteChannelSalesProduct(
			Long[] channelProductID, Employee employee) {
		//将删除的关系数据添加到历史表后,再删除
		if (channelProductID != null && channelProductID.length > 0){
			String ids = "(" + StringUtils.join(channelProductID, ",") + ")";
			String logSql = "INSERT INTO sales_channel_product_his(channel,channelproduct,productsale,type,operator,updatetime,cdate,coperator) "
						+ " SELECT channel,channelproduct,productsale,type,operator,updatetime,NOW(),? FROM sales_channel_product WHERE id IN " + ids
						;
			jdbcTemplate.update(logSql, employee.getId());
			String deleteSql = "DELETE FROM sales_channel_product WHERE id IN " + ids;
			jdbcTemplate.update(deleteSql);
		}
	}
	@Override
	public void appendChannelSalesProduct(List<Long> channels,
			Map<String, Long> channelsalesProducts, Employee employee)
			throws ChannelSalesException {
		for (String id : channelsalesProducts.keySet()) {
			if (!id.matches("[a-zA-Z0-9]+")) {
				throw new ChannelSalesException("渠道商品ID错误" +id);
			}
		}
		
		String checkSqlTemplate = "SELECT channelproduct FROM sales_channel_product WHERE channel IN (%s) AND channelproduct IN ('%s') LIMIT 10";
		
		
		List<String> list = jdbcTemplate.queryForList(
				String.format(checkSqlTemplate, StringUtils.join(channels,","), StringUtils.join(channelsalesProducts.keySet(),"','"))
				, String.class);
		

		
		if (!list.isEmpty()) {
			throw new ChannelSalesException("商品重复添加:" + list.toString());
		} else {
			String sql = "SELECT id FROM product_sale WHERE id IN (" + StringUtils.join(channelsalesProducts.values(),",") + ")";
			List<Long> existsProductSaleId = jdbcTemplate.queryForList(sql, Long.class);
			Set<Long> prodcutSaleSet = new HashSet<Long>(existsProductSaleId);
			List<Long> unkonwProductSaleId = new ArrayList<Long>();
			for (Long id : channelsalesProducts.values()){
				if (!prodcutSaleSet.contains(id)){
					unkonwProductSaleId.add(id);
				}
			}
			if (!unkonwProductSaleId.isEmpty()){
				throw new ChannelSalesException("以下EC商品ID未找到:" + unkonwProductSaleId.toString());
			}
			
			
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			
			String addsql = "INSERT INTO sales_channel_product(channel,channelproduct, productsale,type,operator,updatetime) VALUES ";
			StringBuilder sb = new StringBuilder(addsql);
			int count = 0;
		
			for (Long channelId : channels){
				
				for (Map.Entry<String, Long> m : channelsalesProducts.entrySet()){
					count++;
					sb.append(String.format("(%s,'%s',%s,%s,%s,'%s'),", channelId ,m.getKey(), m.getValue(), Code.CHANNELPRODUCT_REL_HAND, employee.getId(), date));
					
					
					if (count > 1000){
						
						sb.deleteCharAt(sb.length() - 1);
						jdbcTemplate.update(sb.toString());
						count = 0;
						
						sb = new StringBuilder(addsql);
					}
					
				}
			}
			if (count > 0){
				sb.deleteCharAt(sb.length() - 1);
				jdbcTemplate.update(sb.toString());
			}
			
		}
	}
	@Override
	public synchronized void sendToSap(BigDecimal money, Employee employee) throws ChannelSalesException {
		//
		String countSql = "SELECT COUNT(1) FROM sales_upload_record sur "
						+ " INNER JOIN sales_record sr ON (sr.uploadrecord = sur.id)"
						+ " WHERE sur.status  IN (91002,91006) AND sr.status IN (91001)"
						; 
		if (jdbcTemplate.queryForInt(countSql) == 0) {
			throw new ChannelSalesException("当前没有可下传的销售数据！");
		}
		//先进先出,最低下传价格
		String minMoneySql = "SELECT  IFNULL((sr.sales - refund) * p.listprice,0) FROM sales_upload_record sur "
						+	" 	INNER JOIN sales_record sr ON (sr.uploadrecord = sur.id)"
						+	" 	INNER JOIN product_sale ps ON (ps.id = sr.productsale)"
						+	" 	INNER JOIN product p ON (p.id = ps.product)"
						+	" WHERE sur.status  IN (91002,91006) AND sr.status IN (91001)"
						+	" ORDER BY sr.id LIMIT 1"
						;
					
		
		BigDecimal minMoney = jdbcTemplate.queryForObject(minMoneySql, BigDecimal.class);
		LOG.info("最小下传金额：" + minMoney);
		if (money.compareTo(minMoney) < 0) {
			throw new ChannelSalesException("下传SAP低于最低下传金额, 当前最小下传金额:" + minMoney.toString()); 
		}
		
		
		//验证下传金额是否大于可下传金额
		String maxMoneySql = " SELECT  IFNULL(SUM((sales - refund) * p.listprice),0) FROM sales_upload_record sur "
						+   " 	INNER JOIN sales_record sr ON (sr.uploadrecord = sur.id)"
						+	" 	INNER JOIN product_sale ps ON (ps.id = sr.productsale)"
						+	" 	INNER JOIN product p ON (p.id = ps.product)"
						+	" 	WHERE sur.status  IN (91002,91006) AND sr.status IN (91001)"
						;
		
		BigDecimal maxMoney = jdbcTemplate.queryForObject(maxMoneySql, BigDecimal.class);
		if (money.compareTo(maxMoney) > 0){
			throw new ChannelSalesException("下传SAP金额超出, 当前允许下传金额:" + maxMoney.toString()); 
		}
		LOG.info("最大下传金额：" + maxMoney);
		
		ChannelSalesSapRecord sap = new ChannelSalesSapRecord();
		sap.setListprice(money);
		sap.setOperator(employee);
		sap.setUpdatetime(new Date());
		sap.setSales(BigDecimal.ZERO);
		sap.setRefund(BigDecimal.ZERO);
		channelSalesDao.saveSapRecord(sap);
		Long sapRecordId = sap.getId();
		LOG.info("save SapRecord successfully!");
		//获取可以下传SAP的商品销售记录[上传记录为：校验成功，部分上传]并[销售记录为:已上传]
		String salesSql = "SELECT sr.id, sur.id AS 'uploadrecord', sales, refund, p.listprice FROM sales_upload_record sur "
						+ " 	INNER JOIN sales_record sr ON (sr.uploadrecord = sur.id)"
						+ " 	INNER JOIN product_sale ps ON (ps.id = sr.productsale)"
						+ " 	INNER JOIN product p ON (p.id = ps.product)"
						+ " WHERE sur.status  IN (91002,91006) AND sr.status IN (91001) AND sr.id > ?"
						+ " ORDER BY sr.id LIMIT 1000"
						;
		//查询加速
		Long saleRecordId = 0L;
		//记录更新的上传文件ID,后续修改其状态[部分下传SAP,完全下传SAP]
		Set<Long> uploadRecordSet = new HashSet<Long>();
		
		//销售记录ID
		List<Long> salesRecordIds = new ArrayList<Long>();
		L110: while (true) {
			
			List<SalesRecord> list = jdbcTemplate.query(salesSql, new Object[]{saleRecordId}, new BeanPropertyRowMapper<SalesRecord>(SalesRecord.class));

			for (int i = 0; i < list.size(); i++) {
				SalesRecord sr = list.get(i);
				uploadRecordSet.add(sr.uploadrecord);
				
				saleRecordId = sr.id;
				//净销售
				BigDecimal sales = sr.listprice.multiply(new BigDecimal(sr.sales - sr.refund));
				if (money.compareTo(sales) >= 0){
					money = money.subtract(sales);
					salesRecordIds.add(sr.id);
					sap.setSales(sap.getSales().add(sr.listprice.multiply(new BigDecimal(sr.sales))));
					sap.setRefund(sap.getRefund().add(sr.listprice.multiply(new BigDecimal(sr.refund))));
					
				} else {
					if (!salesRecordIds.isEmpty()) {
						jdbcTemplate.update(String.format("UPDATE sales_record sr SET sr.status = 91004,saprecord=%s WHERE sr.id IN (%s)",
									sapRecordId ,	StringUtils.join(salesRecordIds,",")));
					}
					break L110;
				}
			}
			if (!salesRecordIds.isEmpty()) {
				jdbcTemplate.update(String.format("UPDATE sales_record sr SET sr.status = 91004,saprecord=%s WHERE sr.id IN (%s)", sapRecordId ,StringUtils.join(salesRecordIds,",")));
			}
			
			LOG.info("UPDATE sales_record completed!!");
			
			salesRecordIds.clear();
			
			if (money.compareTo(BigDecimal.ZERO) <= 0){
				break;
			}
		}
		
		if (!uploadRecordSet.isEmpty()){
		//更新下SAP记录,下传SAP销售金额，退货金额 ，下传记录状态
		String uploadRecordSql = " UPDATE sales_upload_record sur "
								+ " SET sur.status = IF(EXISTS(SELECT 1 FROM sales_record sr WHERE sr.status = 91001 AND sr.uploadrecord = sur.id), 91006,91007)"
								+ " , sur.sapdeliverylistprice = (SELECT SUM(p.listprice * sr.sales) FROM sales_record sr "
								+ " 	INNER JOIN product_sale ps ON (ps.id =sr.productsale)"
								+ " 	INNER JOIN product p ON (p.id = ps.product)"
								+ " WHERE sr.uploadrecord = sur.id AND sr.status IN (91004,91006,91007))"
								
								+ ", sur.saprefundlistprice = (SELECT SUM(p.listprice * sr.refund) FROM sales_record sr "
								+ " INNER JOIN product_sale ps ON (ps.id =sr.productsale)"
								+ " INNER JOIN product p ON (p.id = ps.product)"
								+ " WHERE  sr.uploadrecord = sur.id AND sr.status IN (91004,91006,91007))"
								+ " WHERE sur.id IN (" + StringUtils.join(uploadRecordSet,",") + ")"
							;
		jdbcTemplate.update(uploadRecordSql);
		}
		channelSalesDao.saveSapRecord(sap);
		
	}
	

	@Override
	public void rollback(Long uploadrecordId, Employee employee)
			throws ChannelSalesException {
		ChannelSalesUploadRecord uploadRecord = channelSalesDao.get(uploadrecordId);
		if (uploadRecord.isAllowRollback()) {
			uploadRecord.appendStatusLog(employee, codeDao.get(Code.CHANNELSALES_ROLLBACK_PREPARE));
			channelSalesDao.save(uploadRecord);
			
			//部分下传SAP或完全下传SAP的,修改为准备冲销.  其他的修改为已冲销(不传SAP)  
			jdbcTemplate.update("UPDATE sales_record SET status = IF(status IN (91006,91007),91009, 91010 ) WHERE uploadrecord = ?", uploadrecordId);
		} else {
			throw new ChannelSalesException("当前记录不允许冲销!");
		}
		
		
	}
	
	
	@Override
	public synchronized void audit(Long uploadRecordId) {
		ChannelSalesUploadRecord uploadRecord = channelSalesDao.get(uploadRecordId);
		
		//修改销售退货同时为0的不下传SAP
		String unSendToSapSql = "SELECT sr.id,sr.`status`,sr.productsale,sr.uploadrecord FROM sales_upload_record sur "
								+ " INNER JOIN sales_record sr ON sur.id=sr.uploadrecord "
								+ " WHERE  sur.id=? AND sr.`status`=? AND sr.sales=0 AND sr.refund=0";
		this.updateSalesRecordStatus(unSendToSapSql, new Object[]{uploadRecordId, Code.CHANNELSALES_UPLOADED}, 
									String.valueOf(Code.CHANNELSALES_SUCESSFUL), uploadRecordId);
		LOG.info("（销售和退货同时为0不下传SAP SQL: " + unSendToSapSql + " ） excute successfully");
		
		//未匹配商品
		String unmappingsql="SELECT id,status,productsale,uploadrecord FROM sales_record  WHERE productsale IS NULL AND uploadrecord = ?";
		this.updateSalesRecordStatus(unmappingsql, new Object[]{uploadRecordId}, UNMAPPED, uploadRecordId);
		
		List<SalesRecordDto> overSales = this.overSell(uploadRecordId);
		this.updateOverSales(overSales, uploadRecordId);
		LOG.info("'audit' method excute statisticsSql（超卖） successfully");
		
		List<SalesOverRefund> overRefunds = this.overRefund(uploadRecordId);
		this.updateOverRefunds(overRefunds, uploadRecordId);
		LOG.info("'audit' method excute refundSql（超退） successfully");
		
		//筛选并更新不合法的商品后，上传记录状态更新为 校验成功
		uploadRecord.setStatus(new Code(Code.CHANNELSALES_SUCESSFUL));
		channelSalesDao.save(uploadRecord);
	}
	
	/**
	 * 更新当前记录超卖的明细
	 * @param overSales
	 * @param originalUploadId
	 */
	public void updateOverSales(List<SalesRecordDto> overSales, Long originalUploadId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update sales_record SET status = 2");
		sb.append(" WHERE id=?");
		sb.append(" AND uploadrecord=?");
		String updateSql = sb.toString();
		int count = 0;
		
		if(CollectionUtils.isNotEmpty(overSales)) {
			for (SalesRecordDto sr : overSales) {
				//TODO productsale在sales_record中不是唯一的，是否把当前记录全部的productsale都更新了？目前只更新一个
				List<Long> srIds = jdbcTemplate.queryForList("SELECT sr.id FROM sales_record sr WHERE sr.productsale=? AND sr.uploadrecord=? AND sr.sales>0 LIMIT 1", 
						Long.class, new Object[]{sr.getProductsale(), originalUploadId});
				if (CollectionUtils.isNotEmpty(srIds)) {
					jdbcTemplate.update(updateSql, srIds.get(0), originalUploadId);
					count++;
				}
			}
		}
		
		LOG.info("更新超卖明细：" + count + " 条");
	}
	
	/**
	 * 更新当前记录 超退 的明细
	 * @param overRefunds
	 * @param originalUploadId
	 */
	public void updateOverRefunds(List<SalesOverRefund> overRefunds, Long originalUploadId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update sales_record SET status = 3");
		sb.append(" WHERE id=?");
		sb.append(" AND uploadrecord=?");
		String updateSql = sb.toString();
		int count = 0;
		if(CollectionUtils.isNotEmpty(overRefunds)) {
			for (SalesOverRefund sor : overRefunds) {
				jdbcTemplate.update(updateSql, sor.getId(), originalUploadId);
				count++;
			}
		}
		LOG.info("更新超退明细：" + count + " 条");
	}
	
	/**
	 * 修改未匹配、销售和退货数量都为0明细的状态
	 * @param sql
	 * @param args
	 * @param status
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateSalesRecordStatus(String sql, Object[] args, String status, Long originalUploadId) {
		List<SalesRecordDto> list = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<SalesRecordDto>(SalesRecordDto.class));
		if (CollectionUtils.isNotEmpty(list)) {
			StringBuffer sb = new StringBuffer();
			sb.append("update sales_record SET status = ");
			sb.append(status);
			sb.append(" WHERE id=?");
			sb.append(" AND uploadrecord=?");
			String updateSql = sb.toString();
			
			//超卖或者超退 判断现有的上传记录是否有改商品，如果有才更新现有记录的商品状态
			if (OVER_SELLING.equals(status) || OVER_RETURN.equals(status)) {
				for (SalesRecordDto sr : list) {
					List<Long> srIds = jdbcTemplate.queryForList("SELECT sr.id FROM sales_record sr WHERE sr.productsale=? AND sr.uploadrecord=? LIMIT 1", 
							Long.class, new Object[]{sr.getProductsale(), originalUploadId});
					if (CollectionUtils.isNotEmpty(srIds)) {
						jdbcTemplate.update(updateSql, srIds.get(0), originalUploadId);
					}
				}
			} else {
				for (SalesRecordDto sr : list) {
					jdbcTemplate.update(updateSql, sr.getId(), sr.getUploadRecord());
				}
			}
			
		}
	}
	
	/**
	 * 校验当前明细和历史明细是否有重复
	 * @param uploadRecordId
	 */
	@Deprecated
	public void checkRepeat(Long uploadRecordId) {
		int limitNum = 2000;
		long srId = 0;
		//获取当前记录明细，排除未匹配的明细
		String detailSql = "SELECT sr.id,sr.status,sr.productsale,sr.channelproduct,sr.uploadrecord,"
						+ " sr.startdate,sr.enddate,sur.channel "
						+ " FROM sales_record sr "
						+ " INNER JOIN sales_upload_record sur ON (sur.id = sr.uploadrecord) "
						+ " WHERE sur.id = ? AND sr.id>? AND sr.productsale IS NOT NULL ORDER BY sr.id LIMIT " + limitNum;
		List<SalesRecordDetail> list = new ArrayList<SalesRecordDetail>();
		
		int sum = 0;
		int n = 0;
		for (;;) {
			long beginTime = System.currentTimeMillis();
			list = jdbcTemplate.query(detailSql, new Object[]{uploadRecordId, srId}, new BeanPropertyRowMapper<SalesRecordDetail>(SalesRecordDetail.class));
			
			if (CollectionUtils.isNotEmpty(list)) {
				srId = this.updateRepeatRecord(list);
				n = list.size();
				sum += n;//累计审核条数
				LOG.info("checkRepeat校验重复：当前校验明细个数：" + n);
				long endTime = System.currentTimeMillis();
				long tempTime = endTime - beginTime;
				LOG.info("checkRepeat当前循环执行时间（毫秒）：" + tempTime);
			}
			
			if (CollectionUtils.isEmpty(list) || list.size() < limitNum) {
				LOG.info("checkRepeat校验明细总个数：" + sum);
				break;
			}
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Deprecated
	public long updateRepeatRecord(List<SalesRecordDetail> list) {
		String sql = "SELECT sr.id FROM sales_record sr "		
					+ " INNER JOIN sales_upload_record sur ON (sur.id = sr.uploadrecord)"	
					+ " WHERE sur.status IN (91002,91006,91007,91009) AND sur.id != ? AND sur.channel = ?"   
					+ " AND sr.status NOT IN(1,2,3,4) AND sr.channelproduct = ? "
					+ " AND (? BETWEEN sr.startdate AND sr.enddate "	
					+ " OR ? BETWEEN sr.startdate AND sr.enddate) LIMIT 1";
		
		StringBuffer sb = new StringBuffer();
		sb.append("update sales_record SET status = ");
		sb.append(REPEAT);
		sb.append(" WHERE id=?");
		sb.append(" AND uploadrecord=?");
		String updateSql = sb.toString();
		
		for (SalesRecordDetail srd : list) {
			List<Long> srIds = jdbcTemplate.queryForList(sql, Long.class, new Object[]{srd.getUploadRecord(),
							srd.getChannel(), srd.getChannelproduct(), srd.getStartdate(), srd.getEnddate()});
			if (CollectionUtils.isNotEmpty(srIds)) {
				jdbcTemplate.update(updateSql, srd.getId(), srd.getUploadRecord());
			}
		}
		
		//获取最后一条明细的ID号
		long srId = list.get(list.size() - 1).getId();
		
		return srId;
	}
	
	@Override
	public BigDecimal getMinMoney() {
		//先进先出,最低下传价格
		String minMoneySql = "SELECT  IFNULL((sr.sales - refund) * p.listprice,0) FROM sales_upload_record sur "
						+	" 	INNER JOIN sales_record sr ON (sr.uploadrecord = sur.id)"
						+	" 	INNER JOIN product_sale ps ON (ps.id = sr.productsale)"
						+	" 	INNER JOIN product p ON (p.id = ps.product)"
						+	" WHERE sur.status  IN (91002,91006) AND sr.status IN (91001)"
						+	" ORDER BY sr.id LIMIT 1"
						;
					
		
		BigDecimal minMoney = jdbcTemplate.queryForObject(minMoneySql, BigDecimal.class);
		if (minMoney == null) {
			minMoney = BigDecimal.ZERO;
		}
		return minMoney;
	}
	
	/**
	 * 验证当前记录的超卖明细
	 * @param uploadrecord
	 */
	public List<SalesRecordDto> overSell(Long uploadrecordId) {
		ChannelSalesUploadRecord uploadrecord = channelSalesDao.get(uploadrecordId);
		String uploadIds = this.getUploadRecordByChannel(uploadrecord);
		LOG.info(uploadrecord.getChannel().getName() + "：已经上传记录ID：" + uploadIds);
		String sellSql = String.format(OVER_SALE_SQL, uploadIds, uploadrecord.getId());
		LOG.info("超卖SQL: " + sellSql);
		List<SalesRecordDto> salesRecordDtos = jdbcTemplate.query(sellSql, new BeanPropertyRowMapper<SalesRecordDto>(SalesRecordDto.class));
		int nums = CollectionUtils.isEmpty(salesRecordDtos) ? 0 : salesRecordDtos.size();
		LOG.info("超卖个数：" + nums);
		return salesRecordDtos;
	}
	
	/**
	 * 验证当前记录的超退明细
	 * @param uploadrecord
	 * @return
	 */
	public List<SalesOverRefund> overRefund(Long uploadrecordId) {
		ChannelSalesUploadRecord uploadrecord = channelSalesDao.get(uploadrecordId);
		String uploadIds = this.getUploadRecordByChannel(uploadrecord);
		LOG.info(uploadrecord.getChannel().getName() + "：已经上传记录ID：" + uploadIds);
		String refundSql = String.format(OVER_RETURN_SQL, uploadIds);
		LOG.info("超退SQL：" + refundSql);
		List<SalesOverRefund> salesOverRefunds = jdbcTemplate.query(refundSql, new BeanPropertyRowMapper<SalesOverRefund>(SalesOverRefund.class));
		
		List<SalesOverRefund> currentSales = new ArrayList<SalesOverRefund>();
		if(CollectionUtils.isNotEmpty(salesOverRefunds)) {
			List<String> channelProductIds = new ArrayList<String>();
			for (SalesOverRefund sor : salesOverRefunds) {
				if(StringUtils.isNotBlank(sor.getChannelproduct())) {
					channelProductIds.add(sor.getChannelproduct());
				}
			}
			String channelProductSql = "SELECT sr.id,sr.uploadrecord,sr.channelproduct,sr.productsale,sr.`status` FROM sales_record sr " +
					" WHERE sr.uploadrecord=" + uploadrecordId + " AND sr.channelproduct IN('" + StringUtils.join(channelProductIds, "','") + "')";
			LOG.info("获取当前超退明细SQL：" + channelProductSql);
			currentSales = jdbcTemplate.query(channelProductSql, new BeanPropertyRowMapper<SalesOverRefund>(SalesOverRefund.class));
		}
		LOG.info("当前记录超退个数：" + currentSales.size());
		return currentSales;
	}
	
	/**
	 * 获取历史记录和当前记录ID
	 * @param uploadrecord
	 * @return
	 */
	public String getUploadRecordByChannel(ChannelSalesUploadRecord uploadrecord) {
		List<Long> list = new ArrayList<Long>();
		String sql = "SELECT id from sales_upload_record sur WHERE sur.channel=? AND sur.status IN (91002,91006,91007) AND sur.id<"+uploadrecord.getId();
		list = jdbcTemplate.queryForList(sql, Long.class, uploadrecord.getChannel().getId());
		//历史记录再加上当前记录
		list.add(uploadrecord.getId());
		String uploadIds = StringUtils.join(list.toArray(), ",");
		return uploadIds;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		channelSalesService = applicationContext.getBean(ChannelSalesService.class);
	}
	
	
	/**
	 * 记录
	 * @author heyadong
	 *
	 */
	public static class SalesRecord {
		private Long id;
		private Long uploadrecord;
		private int sales;
		private int refund;
		private BigDecimal listprice;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getUploadrecord() {
			return uploadrecord;
		}
		public void setUploadrecord(Long uploadrecord) {
			this.uploadrecord = uploadrecord;
		}
		public int getSales() {
			return sales;
		}
		public void setSales(int sales) {
			this.sales = sales;
		}
		public int getRefund() {
			return refund;
		}
		public void setRefund(int refund) {
			this.refund = refund;
		}
		public BigDecimal getListprice() {
			return listprice;
		}
		public void setListprice(BigDecimal listprice) {
			this.listprice = listprice;
		}
	}
	
}
