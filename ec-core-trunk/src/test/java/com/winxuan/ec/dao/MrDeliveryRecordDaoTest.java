package com.winxuan.ec.dao;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrDeliveryRecord;
import com.winxuan.ec.service.BaseTest;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 
 * @author yangxinyi
 * 
 */
public class MrDeliveryRecordDaoTest extends BaseTest {

	private static final Log LOG = LogFactory
			.getLog(MrDeliveryRecordDaoTest.class);

	@InjectDao
	MrDeliveryRecordDao mrDeliveryRecordDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// @Test
	public void testSaveRecord() {
		MrDeliveryRecord newRecord = new MrDeliveryRecord();
		newRecord.setChannel(new Channel(40L));
		newRecord.setProductSale(new ProductSale(10399049L));
		newRecord.setDc(new Code(Code.DC_D803));
		newRecord.setDeliveryDate(new Date());
		newRecord.setDeliveryType(new Code(Code.MR_LINGSHOW));
		mrDeliveryRecordDao.save(newRecord);
		flush();
		assertEquals("1", String.valueOf(newRecord.getId()));
	}

	// @Test
	// @Rollback(false)
	public void updateMrDeliveryRecordTest() {
		List<MrDeliveryRecord> list = getMrDeliveryRecordLists("2013-09-03",
				"2013-09-04");
		MrDeliveryRecord mrDeliveryRecord = list.get(0);
		updateMrDeliveryRecord(mrDeliveryRecord);
		/*
		 * for (MrDeliveryRecord record : list) {
		 * updateMrDeliveryRecord(record); }
		 */

	}

	private void updateMrDeliveryRecord(MrDeliveryRecord record) {
		String sql = "update mr_delivery_record d "
				+ "set d.deliveryquantity = ?"
				+ " where d.productsale= ? and d.channel = ? and d.deliverydate= ? and d.deliveryquantity < ?";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int resultNum = jdbcTemplate.update(sql, record.getDeliveryQuantity(),
				record.getProductSale().getId(), record.getChannel().getId(),
				format.format(record.getDeliveryDate()),
				record.getDeliveryQuantity());
		logger.info(format.format(record.getDeliveryDate()));
		LOG.info("update record is :" + resultNum);
	}

	// @Test
	// @Rollback(false)
	public void saveTest() {
		List<MrDeliveryRecord> list = getInsertList("2013-07-01", "2013-09-04");
		for (MrDeliveryRecord mrDeliveryRecord : list) {
			mrDeliveryRecordDao.save(mrDeliveryRecord);
		}
	}

	private List<MrDeliveryRecord> getMrDeliveryRecordLists(String startDate,
			String endDate) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT oi.productsale as productsale, o.channel as channel, date_format(o.deliverytime, '%Y%m%d') as deliverydate,SUM(oi.deliveryquantity) AS quantity "
				+ " FROM _order o INNER JOIN order_item oi ON (oi._order = o.id) "
				+ " WHERE o.deliverytime >= '"
				+ startDate
				+ "' and o.deliverytime < '"
				+ endDate
				+ "' AND o.processstatus in (8004,8005,8009,8011)"

				+ " GROUP BY oi.productsale,o.channel,deliverydate ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MrDeliveryRecord> list = new ArrayList<MrDeliveryRecord>();
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				long productSaleId = rs.getLong("productsale");
				long channelId = rs.getLong("channel");
				String deliverydate = rs.getString("deliverydate");
				int quantity = rs.getInt("quantity");
				MrDeliveryRecord dr = new MrDeliveryRecord();
				dr.setProductSale(new ProductSale(productSaleId));
				dr.setChannel(new Channel(channelId));
				if (null != deliverydate) {
					DateFormat format = new SimpleDateFormat("yyyyMMdd");
					try {
						Date deliveryDate = format.parse(deliverydate);
						dr.setDeliveryDate(deliveryDate);
					} catch (ParseException e) {
						LOG.info("时间格式不对!");
					}
				}
				dr.setDeliveryQuantity(quantity);
				list.add(dr);
			}
		} catch (SQLException e) {
			LOG.info("SQL语句书写有误！" + e.getMessage());
		} finally {
			DBUtil.close(connection, ps, rs);
		}
		return list;
	}

	private List<MrDeliveryRecord> getInsertList(String startDate,
			String endDate) {
		Connection connection = DBUtil.getConnection();
		String sql = " SELECT c.productsale, c.deliverydate, c.channel, c.quantity, c.ctype, c.dc"
				+ " from (SELECT if(c.parent=105,470001,470002) as ctype, ifnull(odc.dcoriginal,110002) dc, oi.productsale as productsale,"
				+ "o.channel as channel, date_format(o.deliverytime, '%Y%m%d') as deliverydate,SUM(oi.deliveryquantity) AS quantity FROM _order o"
				+ " INNER JOIN order_item oi ON (oi._order = o.id)"
				+ " LEFT JOIN channel c on (c.id = o.channel)"
				+ " LEFT JOIN order_distribution_center odc on (odc._order = o.id)"
				+ " WHERE o.deliverytime >='"
				+ startDate
				+ "'  and o.deliverytime < '"
				+ endDate
				+ "' AND o.processstatus in (8004,8005,8009,8011)"
				+ " GROUP BY oi.productsale,o.channel,deliverydate ) c";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MrDeliveryRecord> list = new ArrayList<MrDeliveryRecord>();
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				long productSaleId = rs.getLong("productsale");
				long channelId = rs.getLong("channel");
				long cType = rs.getLong("ctype");
				long dc = rs.getLong("dc");
				int quantity = rs.getInt("quantity");
				String deliverydate = rs.getString("deliverydate");
				MrDeliveryRecord mr = new MrDeliveryRecord();
				mr.setProductSale(new ProductSale(productSaleId));
				mr.setChannel(new Channel(channelId));
				mr.setDeliveryType(new Code(cType));
				mr.setDc(new Code(dc));
				mr.setDeliveryQuantity(quantity);
				if (null != deliverydate) {
					DateFormat format = new SimpleDateFormat("yyyyMMdd");
					try {
						Date deliveryDate = format.parse(deliverydate);
						mr.setDeliveryDate(deliveryDate);
					} catch (ParseException e) {
						LOG.info("时间格式不对!");
					}
				}
				list.add(mr);

			}
		} catch (SQLException e) {
			LOG.info("sql语句 ：      " + sql);
			LOG.info("SQL语句书写有误！" + e.getMessage());
		}

		return list;
	}

	@Test
	@Rollback(false)
	public void updateTestMrDeliveryRecords() throws ParseException {
		String startDate = "2013-09-22";
		String endDate = "2013-09-23";
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT if(o.channel in (8087, 40, 8095),470001,470002) as ctype, ifnull(odc.dcoriginal,110002) dc, oi.productsale as productsale,"
				+ " o.channel as channel, date_format(o.deliverytime, '%Y-%m-%d') as deliverydate,SUM(oi.deliveryquantity) AS quantity FROM _order o"
				+ " INNER JOIN order_item oi ON (oi._order = o.id)"
				+ " LEFT JOIN order_distribution_center odc on (odc._order = o.id)"
				+ " LEFT JOIN product_sale ps on (oi.productsale = ps.id)"
				+ " WHERE o.deliverytime >='"
				+ startDate
				+ "'  and o.deliverytime < '"
				+ endDate
				+ "' AND o.processstatus in (8004,8005,8009,8011)"
//				+ " AND ps.outerid in (101706633,101700951,100819351,200107161,"
//				+ "200130413,200130412,101294282,100831703,101443440,101857329,100660214,"
//				+ "200207623,200186913,101854536,101183587,101876820,101495399,101508647,101854539,"
//				+ "101540634,100716157,101630254,101659238,101591190,101869589,101581716,101844889,101395267,"
//				+ "101690123,101700967,100884737,101467877,100395729,101871916,101871917,101856784,"
//				+ "101856782,101768869,101300283,101625646,101260898,101773335,101831028,101745135,"
//				+ "101853033,101873356,101183113,101347005,101647826,101709519,101119411,101805977,"
//				+ "101871915,101690123,100580649,101016786,100884677,101444535,101868564,100628791,"
//				+ "101061744,101260885,101849887,101538540,101061100,101430934,100416361,101527598,"
//				+ "101105678,101103122,101616423,100064204,101403153,101693521,101917586,101899108,"
//				+ "101913945,101908267,101922874)"
				+ " GROUP BY oi.productsale,o.channel,deliverydate limit 1000";
		PreparedStatement ps = null;
		ResultSet rs = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Long channelId = rs.getLong("channel");
				Long productSaleId = rs.getLong("productsale");
				int quantity = rs.getInt("quantity");
				Date deliveryDate = format.parse(rs.getString("deliverydate"));
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("productSale", productSaleId);
				params.put("channel", channelId);
				params.put("deliveryDate", deliveryDate);
				List<MrDeliveryRecord> records = mrDeliveryRecordDao
						.find(params);
				MrDeliveryRecord mrDeliveryRecord = null;
				if (records != null && records.size() > 0) {
					mrDeliveryRecord = records.get(0);
					if (quantity > mrDeliveryRecord.getDeliveryQuantity()) {
						mrDeliveryRecord.setDeliveryQuantity(quantity);
						mrDeliveryRecordDao.update(mrDeliveryRecord);
					}
				} else {
					mrDeliveryRecord = new MrDeliveryRecord();
					mrDeliveryRecord.setProductSale(new ProductSale(
							productSaleId));
					mrDeliveryRecord.setChannel(new Channel(channelId));
					mrDeliveryRecord.setDeliveryType(new Code(rs
							.getLong("ctype")));
					mrDeliveryRecord.setDc(new Code(rs.getLong("dc")));
					mrDeliveryRecord.setDeliveryQuantity(quantity);
					mrDeliveryRecord.setDeliveryDate(deliveryDate);
					mrDeliveryRecordDao.save(mrDeliveryRecord);
				}
			}
		} catch (SQLException e) {
			LOG.info("sql语句 ：      " + sql);
			LOG.info("SQL语句书写有误！" + e.getMessage());
		}

	}

}
