package com.winxuan.ec.service.replenishment;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MrDeliveryRecordDao;
import com.winxuan.ec.dao.MrProductFreezeDao;
import com.winxuan.ec.dao.MrProductItemDao;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrDeliveryRecord;
import com.winxuan.ec.model.replenishment.MrDeliveryRecordCurday;
import com.winxuan.ec.model.replenishment.MrDeliveryRecordTemp;
import com.winxuan.ec.support.forecast.Data;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.util.DateUtils;

/**
 * 
 * @author yangxinyi
 *
 */
@Service("mrDeliveryRecordService")
@Transactional(rollbackFor = Exception.class)
public class MrDeliveryRecordServiceImpl implements MrDeliveryRecordService,
		Serializable {

	private static final long serialVersionUID = -6097778228167611354L;

	private static final Log LOG = LogFactory
			.getLog(MrDeliveryRecordServiceImpl.class);

	@InjectDao
	MrDeliveryRecordDao mrDeliveryRecordDao;
	
	@InjectDao
	MrProductItemDao mrProductItemDao;
	
	@InjectDao
	MrProductFreezeDao mrProductFreezeDao;

	private JdbcTemplate jdbcTemplate;

	@Override
	public MrDeliveryRecord get(Long id) {
		return mrDeliveryRecordDao.get(id);
	}
	
	@Override
	public List<MrDeliveryRecord> find(Map<String, Object> parameters) {
		return mrDeliveryRecordDao.find(parameters);
	}
	
	@Override
	public void saveMrDeliveryRecord(String startDate, String endDate) {
//		insertMrDeliveryRecord(startDate, endDate);
//		updateMrDeliveryRecord(startDate, endDate);
		final String queryStr = "select d.id, c.* from "
				+ " (SELECT ifnull(odc.dcdest,110003) dc, oi.productsale as productsale," 
				+ " o.channel as channel, date_format(o.deliverytime, '%Y-%m-%d') as deliverydate,SUM(oi.deliveryquantity) AS deliveryquantity"
				+ " FROM _order o"
				+ " INNER JOIN order_item oi ON (oi._order = o.id)"
				+ " LEFT JOIN order_distribution_center odc on (odc._order = o.id)"
				+ " WHERE o.deliverytime >= ? and o.deliverytime < ? AND oi.deliveryquantity > 0" 
				+ " AND o.processstatus in (8004,8005,8009,8011) AND o.shop = 1"
				+ " GROUP BY oi.productsale,o.channel,deliverydate,dc ) c "
				+ " LEFT JOIN mr_delivery_record d on (c.productsale = d.productsale and c.channel = d.channel and c.deliverydate = d.deliverydate and c.dc = d.dc)"
				+ " where d.productsale is null or c.deliveryquantity != d.deliveryquantity";
		List<MrDeliveryRecordTemp> tempList = jdbcTemplate.query(queryStr, new Object[]{startDate, endDate}, 
				new BeanPropertyRowMapper<MrDeliveryRecordTemp>(MrDeliveryRecordTemp.class));
		for(MrDeliveryRecordTemp mrDeliveryRecordTemp : tempList) {
			this.saveSingleDeliveryRecord(mrDeliveryRecordTemp);
		}
	}
	
	@Override
	public void saveSingleDeliveryRecord(MrDeliveryRecordTemp mrDeliveryRecordTemp){
		try{
			Long deliveryType = Code.MR_LINGSHOW;
			//团购渠道
			if(mrDeliveryRecordTemp.getChannel() == 40 || mrDeliveryRecordTemp.getChannel() == 8087
					|| mrDeliveryRecordTemp.getChannel() == 8095) {
				deliveryType = Code.MR_TUANGOU;
			}
			MrDeliveryRecord mrDeliveryRecord = new MrDeliveryRecord();
			mrDeliveryRecord.setId(mrDeliveryRecordTemp.getId());
			mrDeliveryRecord.setDeliveryType(new Code(deliveryType));
			mrDeliveryRecord.setDc(new Code(mrDeliveryRecordTemp.getDc()));
			mrDeliveryRecord.setDeliveryDate(mrDeliveryRecordTemp.getDeliveryDate());
			mrDeliveryRecord.setChannel(new Channel(mrDeliveryRecordTemp.getChannel()));
			mrDeliveryRecord.setProductSale(new ProductSale(mrDeliveryRecordTemp.getProductSale()));
			mrDeliveryRecord.setDeliveryQuantity(mrDeliveryRecordTemp.getDeliveryQuantity());
			mrDeliveryRecordDao.saveOrUpdate(mrDeliveryRecord);
		}catch(Exception e){
			LOG.info("productsale为" + mrDeliveryRecordTemp.getProductSale() + "商品更新每日发货数据失败！");
		}
	}
	
	/**
	 * 每日发货统计，写入新增记录
	 */
	@Deprecated
	private void insertMrDeliveryRecord(String startDate, String endDate) {
		final String insertSql = "insert into mr_delivery_record(productsale, deliverydate, channel, deliveryquantity, `type`, dc) "
				+ " SELECT c.productsale, c.deliverydate, c.channel, c.quantity, c.ctype, c.dc"
				+ " from (SELECT if(o.channel in(?),?,?) as ctype, ifnull(odc.dcoriginal,110003) dc, oi.productsale as productsale,"
				+ " o.channel as channel, date_format(o.deliverytime, '%Y-%m-%d') as deliverydate,SUM(oi.deliveryquantity) AS quantity FROM _order o"
				+ "	INNER JOIN order_item oi ON (oi._order = o.id)"
				+ " LEFT JOIN order_distribution_center odc on (odc._order = o.id)"
				+ " WHERE o.deliverytime >= ? and o.deliverytime < ? AND o.processstatus in (8004,8005,8009,8011) AND o.shop = 1"
				+ " GROUP BY oi.productsale,o.channel,deliverydate ) c "
				+ " LEFT JOIN mr_delivery_record d"
				+ " on (c.productsale = d.productsale and c.channel = d.channel and c.deliverydate = d.deliverydate)"
				+ " where d.productsale is null";
		int resultNum = jdbcTemplate.update(insertSql,
				"8087, 40, 8095", Code.MR_TUANGOU, Code.MR_LINGSHOW,
				startDate, endDate);
		LOG.info("insert record is :" + resultNum);
	}

	/**
	 * 每日发货统计，更新已有记录
	 */
	@Deprecated
	private void updateMrDeliveryRecord(String startDate, String endDate) {
		final String updateSql = "update mr_delivery_record d,"
				+ " (SELECT oi.productsale as productsale, o.channel as channel, date_format(o.deliverytime, '%Y%m%d') as deliverydate,SUM(oi.deliveryquantity) AS quantity "
				+ " FROM _order o INNER JOIN order_item oi ON (oi._order = o.id) "
				+ " WHERE o.deliverytime >= ? and o.deliverytime < ? AND o.processstatus in (8004,8005,8009,8011) AND o.shop = 1"
				+ " GROUP BY oi.productsale,o.channel,deliverydate ) c "
				+ " set d.deliveryquantity = c.quantity"
				+ " where c.productsale = d.productsale and c.channel = d.channel and c.deliverydate = d.deliverydate and d.deliveryquantity < c.quantity";
		int resultNum = jdbcTemplate.update(updateSql, startDate, endDate);
		LOG.info("update record is :" + resultNum);
	}

	@Override
	public List<MrDeliveryRecordCurday> findCurdateDeliveryRecords() {
		final String queryStr = "SELECT mpi.id as mrProductItem, mdr.productsale, mdr.dc, p.mccategory, p.listprice, mmt.type as mcType,"
				+ " mmt.quantity as fixQuantity, mmt.boundtop as fixBoundTop, mmt.boundbottom as fixBoundBottom, mmt.defaultquantity as fixDefaultQuantity, mmt.ratio as fixRatio,"
				+ " if(pss.stock > pss.sales, pss.stock - pss.sales,0) as availableQuantity,"
				+ " mc.salecycle , mc.replenishmentcycle, mc.transitcycle, psg.grade, mmc.model as mcModel FROM mr_delivery_record mdr"
				+ " left join product_sale_grade psg on psg.productsale = mdr.productsale"
				+ " left join product_sale ps on ps.id = mdr.productsale"
				+ " left join product p on p.id = ps.product"
				+ " left join mr_cycle mc on (mc.mccategory = p.mccategory and mc.dc = mdr.dc)"
				+ " left join mr_mc_type mmt on mmt.mccategory = p.mccategory"
				+ " left join mr_model_choose mmc on mmc.grade = psg.grade"
				+ " left join mr_product_freeze mpf on (mpf.productsale = ps.id and mpf.dc = mdr.dc and mpf.flag != 1)"
				+ " left join mr_product_item mpi on (mpi.productsale = ps.id and mpi.dc = mdr.dc)"
				+ " left join product_sale_stock pss on (pss.productsale = ps.id and pss.dc = mdr.dc)"
				+ " where mpf.id is null and mdr.deliveryDate = DATE_SUB(curdate(),INTERVAL 1 DAY)"
				+ " group by mdr.productsale, mdr.dc";
		LOG.info(queryStr);
		return jdbcTemplate.query(queryStr, new BeanPropertyRowMapper<MrDeliveryRecordCurday>(MrDeliveryRecordCurday.class));
	}

	@Override
	public int findNewDeliveryRecord(String startDate, String endDate) {
		final String queryStr = "select count(1) from "
				+ " (SELECT oi.productsale as productsale, o.channel as channel, date_format(o.deliverytime, '%Y%m%d') as deliverydate,SUM(oi.deliveryquantity) AS quantity"
				+ " FROM _order o"
				+ " INNER JOIN order_item oi ON (oi._order = o.id)"
				+ " WHERE o.deliverytime >= ? and o.deliverytime < ? AND o.processstatus in (8004,8005,8009,8011) AND o.shop = 1"
				+ " GROUP BY oi.productsale,o.channel,deliverydate ) c "
				+ " LEFT JOIN mr_delivery_record d on (c.productsale = d.productsale and c.channel = d.channel and c.deliverydate = d.deliverydate)"
				+ " where d.productsale is null or c.quantity != d.deliveryquantity";
		return jdbcTemplate.queryForInt(queryStr, startDate, endDate);
	}
	
	/**
	 * 当涉及到团购权重时，取大于等于乘以团购权重之后得到的值的最小整数
	 */
	@Override
	public List<Integer> getProductQuantityOld(Long productSale, Long dc, int saleCycle) {
		List<Integer> results = new ArrayList<Integer>();
		for(int i = saleCycle; i > 0; i--) {
//			results.add((int)Math.ceil(jdbcTemplate.queryForObject
//					(PARAM_SQL, Double.class,productSale, dc, i*7, (i-1)*7).doubleValue()));
//			results.add(jdbcTemplate.queryForInt(PARAM_SQL, productSale, dc, i*7, (i-1)*7));
			Double number = jdbcTemplate.queryForObject
			(PARAM_SQL, new Object[]{productSale, dc, i*7, (i-1)*7}, Double.class);
			results.add(number != null ?(int)Math.ceil(number.doubleValue()) : 0);
		}
		return results;
	}
	
	/**
	 * 当涉及到团购权重时，取大于等于乘以团购权重之后得到的值的最小整数
	 */
	@Override
	public List<Data> getProductQuantityNew(Long productSale, Long dc, int saleCycle) {
		List<Data> results = new ArrayList<Data>();
		int flag = 0;
		for(int i = saleCycle; i > 0; i--) {
//			results.add(new Data(flag++, jdbcTemplate.queryForInt(PARAM_SQL, productSale, dc, i*7, (i-1)*7)));
//			results.add(new Data(flag++, (int)Math.ceil(jdbcTemplate.queryForObject
//					(PARAM_SQL, Double.class, productSale, dc, i*7, (i-1)*7).doubleValue())));
			Double number = jdbcTemplate.queryForObject
			(PARAM_SQL, new Object[]{productSale, dc, i*7, (i-1)*7}, Double.class);
			results.add(new Data(flag++, number != null ?(int)Math.ceil(number.doubleValue()) : 0));	
		}
		return results;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 用于获取每日更新数据
	 */
	@Override
	public void newMrDeliveryRecord(){
		String startDate = "";
		String endDate = "";
		DateFormat dateFormat = new SimpleDateFormat(
				DateUtils.SHORT_DATE_FORMAT_STR);
		Date date = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		for(int i = 0; i < MrDeliveryRecordService.INTERVAL_DAY; i++) {
			endDate = dateFormat.format(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, -1);
			startDate = dateFormat.format(cal.getTime());
			
			/**
			 * 如果存在当天更新的数据，则将其写入mrdeliveryrecord表
			 * 更新的数据包括：1）延迟由中启回传至EC的发货数据；2）当日新增的发货数据
			 * 此处的发货数据是已经分渠道乘以相应团购影响权重之后的发货数据
			 * 注意：团购影响权重的计算只参与老模型与新模型，不参与定位表的计算
			 */
			if(this.findNewDeliveryRecord(startDate, endDate) > 0) {
				this.saveMrDeliveryRecord(startDate, endDate);
			}
		}
	}

}
