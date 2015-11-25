/*
 * @(#)DBUtil.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * description
 * 
 * @author wangbiao
 * @version 1.0 date 2013-9-4 上午10:46:59
 */

public class DBUtil {
	
	private static final Log LOG = LogFactory.getLog(DBUtil.class);
	
	private static String url = "jdbc:mysql://10.1.2.49:33306/newshop";
	private static String user = "yf3_select";
	private static String password = "ewr343tert2132";

	// 通过静态代码块加载Driver
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOG.info("加载MySQL驱动错误，没有找到驱动类！");
		}
	}

	// 提供获取Connection的getConnection()
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			LOG.info("获取数据库连接错误！");
		}
		return null;
	}

	// 定义一个关闭连接资源的方法
	public static void close(Connection connection, Statement statement,
			ResultSet resultSet) {
		try {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			LOG.info("数据库连接关闭失败！");
		}
	}
}
