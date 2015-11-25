package com.winxuan.ec.service.exception;

import java.util.List;

import com.winxuan.ec.model.refund.RefundLog;


/**
 * 监控日志
 * 
 * @author caoyouwen
 * @version 1.0,2011-11-23
 */
public interface ExceptionLogService {
	/**
	 * 监控日志-普通信息
	 * @param id   查询id
	 * @param info 记录信息
	 * @param message 补充信息
	 */
	void info(String id,String info,String message);
	/**
	 * 监控日志-错误信息
	 * @param id   查询id
	 * @param info 记录信息
	 * @param message 补充信息
	 */
	void error(String id,String info,String message);
	/**
	 * 监控日志-警告信息
	 * @param id   查询id
	 * @param info 记录信息
	 * @param message 补充信息
	 */
	void warn(String id,String info,String message);
	/**
	 * 查询日志
	 * @param id
	 * @return
	 */
	List<RefundLog> get(String id);
}
