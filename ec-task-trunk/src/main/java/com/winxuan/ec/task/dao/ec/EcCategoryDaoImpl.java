package com.winxuan.ec.task.dao.ec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.winxuan.ec.task.model.robot.RobotCategory;


/**
 * EcCategoryDao
 * 用于..robot采集分类的数据同步..
 * @author Heyadong
 * @version 1.0, 2012-3-23
 */
@Repository("ecCategoryDao")
public class EcCategoryDaoImpl implements EcCategoryDao {
	
	private static final long serialVersionUID = 7019003783322626469L;

	
	private static final String EC_KEY_CODE = "code";
	private static final String EC_KEY_ID = "id";
	
	//robot_id 是 robot 采集系统的分类ID, 需要根据映射关系来 同步商品 以及code
	private static final String EC_SQL_CODE = "SELECT id, code FROM category WHERE robot_id=? ";
	
	//robot_id在ec_core里的分类ID映射关系
	private static final String EC_SQL_ID = "SELECT id FROM category WHERE robot_id=?";
	
	//同步SQL, 更新code, 以及parentID
	private static final String EC_SQL_SYNC = "UPDATE category SET parent=?, code=? WHERE id=? AND code IS NULL";
	
	//获取Product所有ID
	private static final String EC_SQL_PRODUCT_META = "SELECT id FROM product_meta WHERE sort = 11001";
	
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void syncCategory(List<RobotCategory> categories) throws Exception {
		Collections.sort(categories,new Comparator<RobotCategory>(){
			@Override
			public int compare(RobotCategory o1, RobotCategory o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
		
		//批量保存
		this.save(categories);
		
		
		
		/*获取图书所有meta,生成模板 SQL.  替换 ## 为添加的categoryID,批量建立关系*/
		String token = "##";
		List<Integer> metaIds = jdbcTemplateEcCore.queryForList(EC_SQL_PRODUCT_META, Integer.class);
		StringBuilder sqlTemplateBuilder = new StringBuilder("INSERT INTO category_meta(category, meta, _index) VALUES ");
		for (Integer id : metaIds) {
			sqlTemplateBuilder.append(String.format("(%s,%s,%s),",token, id, id));
		}
		sqlTemplateBuilder.deleteCharAt(sqlTemplateBuilder.length() - 1);
		final String sqlTemplate = sqlTemplateBuilder.toString();
		
		
		
		//将robotCategory数据添加到ECcore里,然后将ECcore里面的id来更新code以及parent
		for (RobotCategory robotCategory : categories){
			//查询刚添加的分类ID
			int categoryID = jdbcTemplateEcCore.queryForInt(EC_SQL_ID, robotCategory.getId());

			//为分类添加product meta
			jdbcTemplateEcCore.update(sqlTemplate.replaceAll(token, categoryID + ""));
			
			//查找他的父类ID,以及 Code
			Map<String,Object> parent = jdbcTemplateEcCore.queryForMap(EC_SQL_CODE, robotCategory.getParent());
			
			String parentCode = parent.get(EC_KEY_CODE).toString();
			Assert.notNull(parentCode, String.format("robotID: %s ,  parent's code is null, parentId:%s", robotCategory.getId(), robotCategory.getParent()));
			
			
			Integer categoryId = Integer.valueOf(categoryID);
			Integer parentId = Integer.valueOf(parent.get(EC_KEY_ID).toString());
			
			String code = String.format("%s.%s", parentCode, categoryID);
			
			int i = jdbcTemplateEcCore.update(EC_SQL_SYNC, parentId, code, categoryId);
			Assert.isTrue(i == 1, String.format("synchronize error , updateCount equals %s", i));		
		}
	}
	/**
	 * 分批添加,100一批
	 * @param categories
	 */
	private void save(List<RobotCategory> categories){
		
		String sqlformat = "INSERT INTO category(label, name, available, alias, robot_id, sort) VALUES ";
		StringBuilder sql = new StringBuilder(sqlformat);
		int batch = 100;
		
		int i = 0;
		List<Object> params = new ArrayList<Object>();
		for (RobotCategory robotCategory : categories){
			i++;
			sql.append("(?,?,1,?,?,?),");
			params.add(robotCategory.getLabel());
			params.add(robotCategory.getTitle());
			params.add(robotCategory.getTitle());
			params.add(robotCategory.getId());
			params.add(robotCategory.getId());
			if (i % batch == 0){
				sql.deleteCharAt(sql.length() - 1);
				jdbcTemplateEcCore.update(sql.toString(), params.toArray());
				sql = new StringBuilder(sqlformat);
				params.clear();
			}
		}
		//剩余的保存
		if (!params.isEmpty()){
			sql.deleteCharAt(sql.length() - 1);
			jdbcTemplateEcCore.update(sql.toString(), params.toArray());
			params.clear();
		}
	}
}
