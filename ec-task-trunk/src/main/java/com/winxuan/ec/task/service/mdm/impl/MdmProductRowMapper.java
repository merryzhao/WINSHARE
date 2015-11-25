package com.winxuan.ec.task.service.mdm.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.winxuan.ec.task.model.mdm.MdmProduct;

/**
 * HideHai
 */
public class MdmProductRowMapper implements RowMapper<MdmProduct>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -268517800349304511L;

	@Override
	public MdmProduct mapRow(ResultSet rs, int index) throws SQLException {
		MdmProduct product = new MdmProduct();
		product.setMerchId(rs.getInt("MERCHID"));
		product.setSapCode(rs.getString("SAPCODE"));
		product.setMerchType(rs.getString("merchtype"));
		product.setBookName(rs.getString("BOOKNAME"));
		product.setIsbn(rs.getString("ISBN_ISRC"));
		product.setPublisher(rs.getString("PUBLISHER"));
		product.setAuthor(rs.getString("AUTHOR"));
		product.setPrice(rs.getBigDecimal("PRICE"));
		product.setNormalVendorId(rs.getString("NORMALVENDORID"));
		product.setCreateTime(rs.getDate("CREATETIME"));
		product.setLastModifyTime(rs.getDate("LASTMODIFYTIME"));
		product.setSizeFormat(rs.getString("SIZEFORMAT"));
		product.setPageNum(rs.getInt("PAGENUM"));
		product.setWordNumber(rs.getInt("WORDNUMBER"));
		product.setBindingFormat(rs.getString("BINDINGFORMAT"));
		product.setThisEdition(rs.getString("THISEDITION"));
		product.setPrintingTimes(rs.getString("PRINTINGTIMES"));
		product.setThisEditionYearMonth(rs.getString("THISEDITIONYEARMONTH"));
		product.setThisPrintingYearMonth(rs.getString("THISPRINTINGYEARMONTH"));
		product.setTranslator(rs.getString("TRANSLATOR"));
		product.setSubHeading(rs.getString("SUBHEADING"));
		product.setSeriesname(rs.getString("SERIESNAME"));
		product.setUnusable(rs.getString("UNUSABLE"));
		return product;
	}

}
