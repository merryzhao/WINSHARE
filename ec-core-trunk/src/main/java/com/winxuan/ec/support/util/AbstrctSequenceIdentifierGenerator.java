/*
 * @(#)IdentifierGeneratorImpl.java
 *
 * Copyright 2008 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.transaction.Status;
import javax.transaction.Synchronization;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

/**
 * description
 * 
 * @author an.liu
 * @version 1.0,2008-3-4
 */
public abstract class AbstrctSequenceIdentifierGenerator implements IdentifierGenerator,
		Configurable,Serializable {
	

	private static final String TABLE = "table";

	private static final String DEFAULT_TABLE = "common_primary_key";

	private static final String COLUMN = "column";

	private static final String DEFAULT_COLUMN = "primary_key";

	private static final String TARGET_NAME = "target_name";

	private static final String DEFAULT_TARGET_NAME = "target_table";

	private static final String TARGET_VALUE = "target_value";

	private static final String LENGTH = "length";
	
	private static final String STEP = "step";

	private static final int DEFAULT_LENGTH = 6;

	private static final String PATCH = "0";
	
	private static final int DEFAULT_STEP = 1;

	private static final long serialVersionUID = -3367408879315774952L;

	private String table;

	private String column;

	private String targetName;

	private String targetValue;

	private int length;

	private String querySql;

	private String updateSql;
	
	private int currentSequence = -1;
	
	private int maxSequence;
	
	private int step;
	
	private Object lock = new Object();

	
	public void configure(Type type, Properties properties, Dialect dialect)
			throws MappingException {
		table = PropertiesHelper.getString(TABLE, properties, DEFAULT_TABLE);
		column = PropertiesHelper.getString(COLUMN, properties, DEFAULT_COLUMN);
		targetName = PropertiesHelper.getString(TARGET_NAME, properties,
				DEFAULT_TARGET_NAME);
		targetValue = properties.getProperty(TARGET_VALUE);
		length = PropertiesHelper.getInt(LENGTH, properties, DEFAULT_LENGTH);
		querySql = "select " + column + " from " + table + " where "
				+ targetName + " = '" + targetValue + "'";
		updateSql = "update " + table + " set " + column + " = ? where "
				+ targetName + " = '" + targetValue + "'";
		step = PropertiesHelper.getInt(STEP, properties, DEFAULT_STEP);
	}
	
	public int sequelize(SessionImplementor session) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement preStmt = null;
		int defaultTransactionLevel = Connection.TRANSACTION_REPEATABLE_READ;
		try {
			session.getJDBCContext().getTransaction().registerSynchronization(new Synchronization() {
				@Override
				public void beforeCompletion() {
				}
				
				@Override
				public void afterCompletion(int status) {
					if(status == Status.STATUS_ROLLEDBACK){
						currentSequence = -1;
					}
				}
			});
			conn = session.connection();
			defaultTransactionLevel = conn.getTransactionIsolation();
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySql);
			int iPrimaryKey = 0;
			if (rs.next()){
				iPrimaryKey = rs.getInt(1);
			}
			preStmt = conn.prepareStatement(updateSql);
			preStmt.setInt(1, iPrimaryKey + step);
			preStmt.executeUpdate();
			
			return iPrimaryKey;
		} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(),e);	
		} finally {
			if (stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e.getMessage(),e);	
				}
			}
			if (preStmt != null){
				try {
					preStmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e.getMessage(),e);	
				}
			}
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					throw new RuntimeException(e.getMessage(),e);	
				}
			}
			if (conn != null){
				try {
					conn.setTransactionIsolation(defaultTransactionLevel);
//					conn.close();
				} catch (SQLException e) {
					throw new RuntimeException(e.getMessage(),e);	
				}
			}
		}
	
		
		
	}
	
	public String converse(int iPrimaryKey){
		String sPrimaryKey = String.valueOf(iPrimaryKey);
		int primaryKeyLength = sPrimaryKey.length();
		if (primaryKeyLength >= length){
			return sPrimaryKey.substring(primaryKeyLength - length);
		}else {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < length - primaryKeyLength; i++) {				
					buffer.append(PATCH);
				
			}
			buffer.append(sPrimaryKey);
			return buffer.toString();
		}
	}
	
	public abstract String assemble(String sPrimaryKey);

	public Serializable generate(SessionImplementor session, Object obj)
			throws HibernateException {
		synchronized (lock) {
			if (currentSequence == -1 || currentSequence == maxSequence) {
				try {
					currentSequence = sequelize(session);
					maxSequence = currentSequence + step;
				} catch (SQLException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			currentSequence++;
		}
		String sPrimaryKey = converse(currentSequence);
		return assemble(sPrimaryKey);
	}
	
}
