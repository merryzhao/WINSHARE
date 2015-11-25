package com.winxuan.ec.dao.exception;

import java.util.List;

import com.winxuan.ec.model.refund.RefundLog;
/**
 * 日志监控   db_monitor库
 * @author youwen
 *
 */
public interface ExceptionLogDao {
	/**
	 * 保存
	 * @param refundFailed
	 */
	void save(RefundLog refundFailed);
	/**
	 * 查询退款日志
	 * @param id
	 */
	List<RefundLog> get(String id);
}
