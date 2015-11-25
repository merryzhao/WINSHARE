package com.winxuan.ec.task.dao.robot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.robot.RobotTask;

/**
 * RobotTask Dao 实现类
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
@Repository("robotTaskDao")
public class RobotTaskDaoImpl implements RobotTaskDao {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8766164655902405684L;
	
	@Autowired
	private JdbcTemplate jdbcTemplateRobot;
	
	@Override
	public void addTasks(int priority, int targetType, String... params) {
		if(params.length != 0){
			String sqlformat = "INSERT INTO task(priority, target,status, parameter) VALUES ";
			StringBuilder sql = new StringBuilder(sqlformat);
			int i = 0;
			List<Object> paramsList = new ArrayList<Object>();
			for (String p : params){
				i++;
				sql.append('(').append(priority).append(',')
					.append(RobotTask.TARGET_TYPE_AMAZON_BOOK).append(',')
					.append(RobotTask.TASK_STATUS_NEW).append(",?),");
				paramsList.add(p);
			}
			sql.deleteCharAt(sql.length() - 1);
			jdbcTemplateRobot.update(sql.toString(), paramsList.toArray());
			paramsList.clear();
		}
	}
}
