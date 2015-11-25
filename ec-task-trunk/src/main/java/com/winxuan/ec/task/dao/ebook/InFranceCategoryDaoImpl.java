package com.winxuan.ec.task.dao.ebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.ebook.InFranceBookCategory;
import com.winxuan.ec.task.model.ebook.InFranceCategory;

/**
 * 
 * @author luosh
 *
 */
@Repository("inFranceCategoryDao")
public class InFranceCategoryDaoImpl implements InFranceCategoryDao{
	private static final String GET_INFRANCE_CATEGORY = "SELECT ID,CODE,NAME,DELETE_FLAG FROM T_IN_FRANCE_CATEGORY WHERE ID=?";
	private static final String FIND_INFRANCE_CATEGORY_BY_CODE ="SELECT ID,CODE,NAME,DELETE_FLAG FROM T_IN_FRANCE_CATEGORY WHERE CODE=?";
	private static final String FIND_INFRANCE_BOOK_CATEGORY_BY_CODE ="SELECT bc.* FROM T_IN_FRANCE_BOOK_CATEGORY bc " +
			"WHERE bc.CATEGORY_ID is not null and bc.CATEGORY_ID <> '' and bc.IN_FRANCE_CATEGORY_ID=?";
	
	@Autowired
	private JdbcTemplate jdbcTemplateEbook;
	@Override
	public InFranceCategory get(Long id) {
		List<Map<String, Object>> list =  jdbcTemplateEbook.queryForList(GET_INFRANCE_CATEGORY,id);
		List<InFranceCategory> fcList = this.getList(list);
		if(fcList != null && fcList.size() > 0){
			return fcList.get(0);
		}
		return null;
	}

	@Override
	public List<InFranceCategory> findByCode(String code) {
		List<Map<String, Object>> list =  jdbcTemplateEbook.queryForList(FIND_INFRANCE_CATEGORY_BY_CODE,code);
		return this.getList(list);
	}

	@Override
	public List<InFranceBookCategory> findByInFranceCategory(Long id) {
		List<Map<String, Object>> list =  jdbcTemplateEbook.queryForList(FIND_INFRANCE_BOOK_CATEGORY_BY_CODE,id);
		List<InFranceBookCategory> listLog = new ArrayList<InFranceBookCategory>();
		if(list == null || list.isEmpty()){
			return null;
		}
		InFranceBookCategory inFranceBookCategory ;
		//封装结果
		for(Map<String, Object> map : list){
			inFranceBookCategory = new InFranceBookCategory();
			inFranceBookCategory.setId(Long.valueOf(map.get("ID").toString()));
			inFranceBookCategory.setIsActive(Integer.valueOf(map.get("IS_ACTIVE").toString()));
			inFranceBookCategory.setCategoryId(Long.valueOf(map.get("CATEGORY_ID").toString()));
			inFranceBookCategory.setInFranceCategoryId(Long.valueOf(map.get("IN_FRANCE_CATEGORY_ID").toString()));
			listLog.add(inFranceBookCategory);
		}
		if(listLog.size()>0){
			return listLog;
		}else{
			return null;
		}
	}
	private List<InFranceCategory> getList(List<Map<String, Object>> list){
		List<InFranceCategory> listLog = new ArrayList<InFranceCategory>();
		if(list == null || list.isEmpty()){
			return null;
		}
		InFranceCategory inFranceCategory ;
		//封装结果
		for(Map<String, Object> map : list){
			inFranceCategory = new InFranceCategory();
			inFranceCategory.setId(Long.valueOf(map.get("ID").toString()));
			inFranceCategory.setCode(map.get("CODE") == null ? null : map.get("CODE").toString());
			inFranceCategory.setName(map.get("NAME") == null ? null : map.get("NAME").toString());
			inFranceCategory.setDeleteFlag(map.get("DELETE_FLAG") == null ? null : Integer.valueOf(map.get("DELETE_FLAG").toString()));
			listLog.add(inFranceCategory);
		}
		if(listLog.size()>0){
			return listLog;
		}else{
			return null;
		}
	}
}
