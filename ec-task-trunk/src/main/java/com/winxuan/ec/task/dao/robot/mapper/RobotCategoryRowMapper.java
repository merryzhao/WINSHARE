package com.winxuan.ec.task.dao.robot.mapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.model.robot.RobotCategory;

/**
 * robot映射配置
 * @author Heyadong
 * @version 1.0, 2012-3-23
 */
@Component
public class RobotCategoryRowMapper implements RowMapper<RobotCategory> ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1262101143924456832L;

	@Override
	public RobotCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new RobotCategory(rs.getInt("id"), rs.getString("label"), rs.getString("title"), rs.getInt("parent"), rs.getString("code"));
	}

}
