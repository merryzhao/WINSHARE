package com.winxuan.ec.task.dao.ebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.ebook.BusinessLog;

/**
 * 上传日志DAO
 * @author luosh
 *
 */
@Repository("businessLogDao")
public class BusinessLogDaoImpl implements BusinessLogDao{
	private static final Log LOG = LogFactory.getLog(BusinessLogDaoImpl.class);
	private static final String SELECT_SQL = "SELECT bl.ID,bl.PROGRAM_TYPE,bl.BUSINESS_ID,bl.PROGRAM_TAG,bl.KEYWORD,bl.DISCRIPTION,bl.ERROR_CODE,bl.STEP_CODE,bl.RESULT_STATUS," +
		"bl.CREATE_DATE,bl.STATUS_CODE FROM BUSINESS_LOG bl,BOOK b " +
		"where  BOOK_ID = BUSINESS_ID AND bl.KEYWORD=? and bl.RESULT_STATUS=? and b.VENDOR_ID=? ORDER BY bl.CREATE_DATE desc";
	private static final String SELECT_SQL2 = "SELECT bl.ID,bl.PROGRAM_TYPE,bl.BUSINESS_ID,bl.PROGRAM_TAG,bl.KEYWORD,bl.DISCRIPTION,bl.ERROR_CODE,bl.STEP_CODE,bl.RESULT_STATUS," +
		"bl.CREATE_DATE,bl.STATUS_CODE FROM BUSINESS_LOG bl " +
		"where  bl.KEYWORD=? and bl.RESULT_STATUS=? ORDER BY bl.CREATE_DATE desc";
	private static final String UPDATE_SQL = "UPDATE BUSINESS_LOG SET BUSINESS_ID=?,KEYWORD=?,DISCRIPTION=?,ERROR_CODE=?,STEP_CODE=?," +
		"RESULT_STATUS=?,STATUS_CODE=?  WHERE ID=?";
	private static final String INSERT_BUSINESSLOG_SQL = "INSERT INTO BUSINESS_LOG(PROGRAM_TYPE,BUSINESS_ID,PROGRAM_TAG,KEYWORD,DISCRIPTION,ERROR_CODE,STEP_CODE," +
		"RESULT_STATUS,CREATE_DATE,STATUS_CODE) VALUES (?,?,?,?,?,?,?,?,sysdate(),?)";


	@Autowired
	private JdbcTemplate jdbcTemplateEbook;
	@Override
	public void save(BusinessLog businessLog) {
		if(businessLog.getId() == null){
			businessLog.setId(this.saveNew(businessLog));
		}
		else{
			this.update(businessLog);
		}
	}

	private void update(BusinessLog businessLog) {
		jdbcTemplateEbook.update(UPDATE_SQL, businessLog.getBusinessId(),businessLog.getKeyword(),businessLog.getDiscription(),
				businessLog.getErrorCode(),
		businessLog.getStepCode(),businessLog.getResultStatus(),businessLog.getStatusCode(),businessLog.getId());
	}
	public PreparedStatement getBusinessLigSmt(Connection conn,BusinessLog businessLog) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(INSERT_BUSINESSLOG_SQL,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, businessLog.getProgramType());
			ps.setString(2, businessLog.getBusinessId());
			ps.setInt(3, businessLog.getProgramTag());
			ps.setString(4, businessLog.getKeyword());
			ps.setString(5, businessLog.getDiscription());
			ps.setString(6, businessLog.getErrorCode());
			ps.setInt(7, businessLog.getStepCode());
			ps.setInt(8, businessLog.getResultStatus());
			ps.setInt(9, businessLog.getStatusCode());
             return ps;
		} catch (SQLException e) {
			LOG.info(e.getCause());
		}
		return ps;
	}
	private Long saveNew(final BusinessLog businessLog){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateEbook.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) {
                return getBusinessLigSmt(conn,businessLog);
            }
        }, keyHolder);
		return keyHolder.getKey().longValue();
	}
	@Override
	public List<BusinessLog> find(String name, int resultStatus, Long vendorID) {
		List<Map<String, Object>> list =  jdbcTemplateEbook.queryForList(SELECT_SQL,name,resultStatus,vendorID);
		return this.getList(list);
	}
	
	@Override
	public List<BusinessLog> find(String name, int resultStatus) {
		List<Map<String, Object>> list =  jdbcTemplateEbook.queryForList(SELECT_SQL2,name,resultStatus);
		return this.getList(list);
	}

	private List<BusinessLog> getList(List<Map<String, Object>> list){
		List<BusinessLog> listLog = new ArrayList<BusinessLog>();
		if(list == null || list.isEmpty()){
			return null;
		}
		BusinessLog log ;
		//封装结果
		for(Map<String, Object> map : list){
			log = new BusinessLog();
			log.setId(Long.valueOf(map.get("ID").toString()));
			log.setBusinessId(map.get("BUSINESS_ID") == null ? null : map.get("BUSINESS_ID").toString());
			log.setProgramType(map.get("PROGRAM_TYPE") == null ? null : Integer.valueOf(map.get("PROGRAM_TYPE").toString()));
			log.setProgramTag(map.get("PROGRAM_TAG") == null ? null : Integer.valueOf(map.get("PROGRAM_TAG").toString()));
			log.setKeyword(map.get("KEYWORD") == null ? null : map.get("KEYWORD").toString());
			log.setDiscription(map.get("DISCRIPTION") == null ? null : map.get("DISCRIPTION").toString());
			log.setErrorCode(map.get("ERROR_CODE") == null ? null : map.get("ERROR_CODE").toString());
			log.setStepCode(map.get("STEP_CODE") == null ? null : Integer.valueOf(map.get("STEP_CODE").toString()));
			log.setResultStatus(map.get("RESULT_STATUS") == null ? null : Integer.valueOf(map.get("RESULT_STATUS").toString()));
			log.setStatusCode(map.get("STATUS_CODE") == null ? null : Integer.valueOf(map.get("STATUS_CODE").toString()));
			listLog.add(log);
		}
		if(listLog.size()>0){
			return listLog;
		}else{
			return null;
		}
	}
}
