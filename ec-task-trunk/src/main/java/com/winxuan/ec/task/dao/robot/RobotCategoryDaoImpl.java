package com.winxuan.ec.task.dao.robot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.dao.robot.mapper.RobotCategoryRowMapper;
import com.winxuan.ec.task.model.robot.RobotCategory;

/**
 * 实现类
 * @author Heyadong 
 * @version 1.0, 2012-3-23
 */
@Repository(value="robotCategoryDao")
public class RobotCategoryDaoImpl implements RobotCategoryDao {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4265533442636180686L;
	@Autowired
	private RobotCategoryRowMapper robotCategoryRowMapper;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获取最新抓取的 [图书658390051]分类的SQL,不会有太多
	 */
	private static final String SQL_NEW_CATEGORIES = 
		"SELECT c.id, c.label, c.parent, cm.title ,c.code FROM category c 	" +
			"INNER JOIN category_meta cm ON (c.meta = cm.id) " +
				"WHERE c.code = 'NEW' AND c.label LIKE '658390051-%' ORDER BY id";
	
	@Autowired
	private JdbcTemplate jdbcTemplateRobot;
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<RobotCategory> getNewCategories() throws Exception{
		return jdbcTemplateRobot.query(SQL_NEW_CATEGORIES, robotCategoryRowMapper);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void syncUpdate(List<RobotCategory> categories) throws Exception{
		String code = dateFormat.format(new Date());
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE category SET code='").append(code).append("'").append("WHERE id IN (");
			for (RobotCategory category : categories){
				sql.append(category.getId()).append(',');
			}
		sql.deleteCharAt(sql.length()- 1);
		sql.append(')');
		jdbcTemplateRobot.update(sql.toString());
	}
	

}
