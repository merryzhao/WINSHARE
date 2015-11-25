package com.winxuan.ec.task.model.ec.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.winxuan.ec.task.model.ec.StockSyncInfo;


/**
 * 
 * @author: HideHai 
 * @date: 2013-10-16
 */
public class StockSyncInfoMapper implements RowMapper<StockSyncInfo>{

	@Override
	public StockSyncInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		StockSyncInfo syncInfo = new StockSyncInfo();
		syncInfo.setId(rs.getLong("id"));
		syncInfo.setLocation(rs.getString("location"));
		syncInfo.setProductsale(rs.getLong("productsale"));
		syncInfo.setOuterId(rs.getString("outerid"));
		syncInfo.setStock(rs.getInt("stock"));
		syncInfo.setChanged(rs.getBoolean("ischanged"));
		syncInfo.setChangeqty(rs.getInt("changeqty"));
		return syncInfo;
	}

}

