package com.winxuan.ec.service.channel;

import java.util.List;

import jxl.Workbook;

import com.winxuan.ec.model.channel.ChannelUploadHistory;
import com.winxuan.ec.model.user.Employee;

/**
 * 渠道对帐服务接口
 * @author heyadong
 * @version 1.0, 2012-5-22
 */
public interface ChannelAccountService {
	
	/**
	 * 上传到帐明细
	 * @param account 约定格式的Excel
	 * @param employee
	 * @param filename 文件名,用于日志记录
	 * @throws Exception
	 */
    void uploadAccountDetail(Workbook account, Employee employee, String filename) throws Exception;
	
	/**
	 * 上传核对正确的订单
	 * @param workbook 订单excel
	 * @param employee 上传人
	 * @throws Exception
	 */
	void uploadConfirmOrder(Workbook workbook, Employee employee) throws Exception;
	
	/**
	 * 获取最近上传历史记录
	 * @return 
	 */
	List<ChannelUploadHistory> getHistory();
}
