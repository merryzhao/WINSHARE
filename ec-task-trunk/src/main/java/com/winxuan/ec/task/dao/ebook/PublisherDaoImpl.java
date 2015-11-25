package com.winxuan.ec.task.dao.ebook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.ebook.Publisher;
/**
 * 
 * @author luosh
 *
 */
@Repository("epublisherDao")
public class PublisherDaoImpl implements PublisherDao{
	private static final Log LOG = LogFactory.getLog(PublisherDaoImpl.class);
	private static final String FIND_BY_CODE = "SELECT * FROM PUBLISHER WHERE DELETE_FLAG=0 AND publisher_Code=?";
	private static final String FIND_BY_NAME = "SELECT * FROM PUBLISHER WHERE DELETE_FLAG=0 AND PUBLISHER_NAME=?";
	
	@Autowired
	private JdbcTemplate jdbcTemplateEbook;
	@Override
	public Publisher getPublisherByCode(String publisherCode) {
		List<Map<String, Object>> list =  jdbcTemplateEbook.queryForList(FIND_BY_CODE,publisherCode);
		if(list == null || list.isEmpty()){
			return null;
		}
		Map<String, Object> map = list.get(0);
		return this.getPubllisher(map);
	}

	
	@Override
	public Publisher getPublisherByName(String publisherName) {
		List<Map<String, Object>> list =  jdbcTemplateEbook.queryForList(FIND_BY_NAME,publisherName);
		if(list == null || list.isEmpty()){
			return null;
		}
		Map<String, Object> map = list.get(0);
		return this.getPubllisher(map);
	}

	private Publisher getPubllisher(Map<String, Object> map){
		Publisher publisher;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			//封装结果
				publisher = new Publisher();
				publisher.setId(Long.valueOf(map.get("PUBLISHER_ID").toString()));
				publisher.setPublisherName(map.get("PUBLISHER_NAME").toString());
				publisher.setPublisherFullName(map.get("PUBLISHER_FULL_NAME")== null?null:map.get("PUBLISHER_FULL_NAME").toString());
				publisher.setPublisherIntroduction(map.get("PUBLISHER_INTRODUCTION")== null?null:map.get("PUBLISHER_INTRODUCTION").toString());
				publisher.setPublisherAddress(map.get("PUBLISHER_ADDRESS")== null?null:map.get("PUBLISHER_ADDRESS").toString());
				publisher.setPublisherZipCode(map.get("PUBLISHER_ZIP_CODE")== null?null:map.get("PUBLISHER_ZIP_CODE").toString());
				publisher.setPublisherTelephone(map.get("PUBLISHER_TELEPHONE")== null?null:map.get("PUBLISHER_TELEPHONE").toString());
				publisher.setPublisherFax(map.get("PUBLISHER_FAX")== null?null:map.get("PUBLISHER_FAX").toString());
				publisher.setPublisherEmail(map.get("PUBLISHER_EMAIL")== null?null:map.get("PUBLISHER_EMAIL").toString());
				publisher.setPublisherWebSite(map.get("PUBLISHER_WEB_SITE")== null?null:map.get("PUBLISHER_WEB_SITE").toString());
				publisher.setPublisherType(map.get("PUBLISHER_TYPE")== null?null:Integer.valueOf(map.get("PUBLISHER_TYPE").toString()));
				publisher.setCreateBy(map.get("CREATE_BY")== null?null:map.get("CREATE_BY").toString());
				publisher.setCreateDatetime(map.get("CREATE_DATETIME")== null?null:ft.parse(map.get("CREATE_DATETIME").toString()));
				publisher.setUpdateBy(map.get("UPDATE_BY")== null?null:map.get("UPDATE_BY").toString());
				publisher.setUpdateDatetime(map.get("UPDATE_DATETIME")== null?null:ft.parse(map.get("UPDATE_DATETIME").toString()));
				publisher.setDeleteFlag(map.get("DELETE_FLAG")== null?null:Integer.valueOf(map.get("DELETE_FLAG").toString()));
				return publisher;
		}catch(ParseException e){
			LOG.info(e.getCause());
		}
		return null;
	}
}
