package com.winxuan.ec.task.service.erp.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.winxuan.ec.task.dao.erp.ErpDaoImpl;
import com.winxuan.ec.task.model.erp.ErpProductStock;


/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-8-6 上午10:32:23  --!
 * @description:
 ********************************
 */
public class ErpProductStockRowMapper implements RowMapper<ErpProductStock> {

	private static final   DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss.s");

	private static final  Logger LOG = LoggerFactory.getLogger(ErpDaoImpl.class);
	
	@Override
	public ErpProductStock mapRow(ResultSet rs, int rowNum) throws SQLException {
		ErpProductStock eps = new ErpProductStock();
		eps.setMerchId(rs.getLong("merchid"));
		eps.setStock(rs.getInt("stock"));
		eps.setDc(rs.getString("dc"));
		try {
			DateTime time = DateTime.parse(rs.getString("cdate"),FORMATTER);
			eps.setCrateTime(time.toDate());
			time = DateTime.parse(rs.getString("udate"),FORMATTER);
			eps.setUpdateTime(time.toDate());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			eps.setCrateTime(rs.getDate("cdate"));
			eps.setUpdateTime(rs.getDate("udate"));
		}
		return eps;
	}

}
