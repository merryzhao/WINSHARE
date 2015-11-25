package com.winxuan.ec.task.dao.ebook;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.ebook.Vendor;
/**
 * 
 * @author luosh
 *
 */
@Repository("vendorDao")
public class VendorDaoImpl implements VendorDao{
	private static final Log LOG = LogFactory.getLog(VendorDaoImpl.class);
	
	private static final String GET = "SELECT * FROM VENDOR WHERE VENDOR_ID=?";
	@Autowired
	private JdbcTemplate jdbcTemplateEbook;
	@Override
	public Vendor get(Long id) {
		List<Map<String, Object>> list =  jdbcTemplateEbook.queryForList(GET,id);
		if(list == null || list.isEmpty()){
			return null;
		}
		Map<String, Object> map = list.get(0);
		Vendor vendor;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			//封装结果
			vendor = new Vendor();
			vendor.setVendorID(Long.valueOf(map.get("VENDOR_ID").toString()));
			vendor.setVendorName(map.get("VENDOR_NAME").toString());
			vendor.setSimpleName(map.get("VENDOR_SIMPLE_NAME")== null?null:map.get("VENDOR_SIMPLE_NAME").toString());
			vendor.setVendorCode(map.get("VENDOR_CODE")== null?null:map.get("VENDOR_CODE").toString());
			vendor.setVendorIntroduction(map.get("VENDOR_INTRODUCTION")== null?null:map.get("VENDOR_INTRODUCTION").toString());
			vendor.setVendorAddress1(map.get("VENDOR_ADDRESS1")== null?null:map.get("VENDOR_ADDRESS1").toString());
			vendor.setVendorContact1(map.get("VENDOR_CONTACT1")== null?null:map.get("VENDOR_CONTACT1").toString());
			vendor.setVendorTelephone1(map.get("VENDOR_TELEPHONE1")== null?null:map.get("VENDOR_TELEPHONE1").toString());
			vendor.setVendorEmail1(map.get("VENDOR_EMAIL1")== null?null:map.get("VENDOR_EMAIL1").toString());
			vendor.setVendorContact2(map.get("VENDOR_CONTACT2")== null?null:map.get("VENDOR_CONTACT2").toString());
			vendor.setVendorTelephone2(map.get("VENDOR_TELEPHONE2")== null?null:map.get("VENDOR_TELEPHONE2").toString());
			
			vendor.setVendorBank(map.get("VENDOR_BANK")== null?null:map.get("VENDOR_BANK").toString());
			vendor.setVendorBankAccount(map.get("VENDOR_BANK_ACCOUNT")== null?null:map.get("VENDOR_BANK_ACCOUNT").toString());
			vendor.setVendorPublicType(map.get("VENDOR_PUBLIC_TYPE")== null?null:Integer.valueOf(map.get("VENDOR_PUBLIC_TYPE").toString()));
			vendor.setAssignPercent(map.get("ASSIGN_PERCENT")== null?null:BigDecimal.valueOf(Double.valueOf(map.get("ASSIGN_PERCENT").toString())));
			
			vendor.setCreateBy(map.get("CREATE_BY")== null?null:map.get("CREATE_BY").toString());
			vendor.setCreateDatetime(map.get("CREATE_DATETIME")== null?null:ft.parse(map.get("CREATE_DATETIME").toString()));
			vendor.setUpdateBy(map.get("UPDATE_BY")== null?null:map.get("UPDATE_BY").toString());
			vendor.setUpdateDatetime(map.get("UPDATE_DATETIME")== null?null:ft.parse(map.get("UPDATE_DATETIME").toString()));
			vendor.setDeleteFlag(map.get("DELETE_FLAG")== null?null:Integer.valueOf(map.get("DELETE_FLAG").toString()));
				return vendor;
		}catch(ParseException e){
			LOG.info(e.getCause());
		}
		return null;
	}

}
