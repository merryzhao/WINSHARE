package com.winxuan.ec.service.channel;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ChannelDao;
import com.winxuan.ec.model.channel.ChannelUploadHistory;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * 渠道对帐服务.
 * 根据约定的Excel,导入发货明细
 * @author heyadong
 * @version 1.0, 2012-5-17
 */
@Service("channelAccountService")
@Transactional(rollbackFor = Exception.class)
public class ChannelAccountServiceImpl implements ChannelAccountService{
	
	private static final int BATCH_LIMIT = 500;
	private static final int SHEET = 0;
	private static final int START_ROW = 1;

	// 上传渠道到款信息
	// 所属信息所在列
	private static final int OUTER_ID_CELL = 0;
	private static final int TIME_CELL = 1;
	private static final int PRICE_CELL = 2;
	private static final int CHANNEL_CELL = 3;
	private static final int BUSINESS_TYPE_CELL = 4;
	private static final int CELL_TOTAL = 5;
	
	//上传人工核对正常订单信息
	//所属信息所在列
	private static final int CONFIRM_ORDER_ID_CELL = 0;
	private static final int CONFIRM_REASON_CELL = 1;
	
	
	private JdbcTemplate jdbcTemplate;
	
	@InjectDao
	private ChannelDao channelDao; 
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void uploadAccountDetail(Workbook account, Employee employee, String filename) throws Exception {
	    ChannelUploadHistory history = channelDao.findHistoryFile(filename);
	    
	    if (history != null) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            throw new Exception(String.format("%s 文件 已被 %s 在 %s  上传",filename, history.getEmployee().getName(), sdf.format(history.getUploadtime())));
	    }
		Date uploadTime = new Date();
		Sheet sheet = account.getSheet(SHEET);
		SimpleDateFormat format = new SimpleDateFormat("y/M/d HH:mm");
		SimpleDateFormat format2 = new SimpleDateFormat("M/d/y HH:mm");
		List<ChannelAccount> list = new ArrayList<ChannelAccountServiceImpl.ChannelAccount>(BATCH_LIMIT);
		int rows = sheet.getRows();
		long total = 0; 
		for (int i = START_ROW; i < rows; i++){
			Cell[] cells = sheet.getRow(i);
			if (cells.length != CELL_TOTAL) {
			    continue;
			}
			ChannelAccount channelAccount = new ChannelAccount();
			channelAccount.outerid = cells[OUTER_ID_CELL].getContents().trim();
			if (StringUtils.isBlank(channelAccount.outerid)) {
			    continue;
			}
			channelAccount.businessType = cells[BUSINESS_TYPE_CELL].getContents();
			channelAccount.channel = cells[CHANNEL_CELL].getContents();
			String tempPrice = cells[PRICE_CELL].getContents();
			channelAccount.price = StringUtils.isBlank(tempPrice) ? null : new BigDecimal(tempPrice);
			String tempTime = cells[TIME_CELL].getContents();
			try {
				channelAccount.time = StringUtils.isBlank(tempTime) ? null : tempTime.indexOf("/") <= MagicNumber.TWO ? format2.parse(tempTime) : format.parse(tempTime);
			} catch (ParseException e) {
				throw new Exception(String.format("Excel, %s 行. 时间格式错误. 外部单号:%s", i, channelAccount.outerid));
			}
			list.add(channelAccount);
			total++;
			if (list.size() > BATCH_LIMIT) {
				saveChannelAccount(list, employee, uploadTime);
				list.clear();
			}
		}
		saveChannelAccount(list, employee, uploadTime);
		
		ChannelUploadHistory uploadHistory = new ChannelUploadHistory();
		uploadHistory.setEmployee(employee);
		uploadHistory.setUploadtime(uploadTime);
		uploadHistory.setTotal(total);
		uploadHistory.setFilename(filename);
		channelDao.saveUploadHistory(uploadHistory);
	}

	//批量保存渠道到款明细
	private void saveChannelAccount(List<ChannelAccount> list, Employee employee, Date uploadtime){
		if (!list.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(uploadtime);
			Long operatorId = employee.getId();
			List<Object> parameter = new LinkedList<Object>();
			StringBuilder bathSql = new StringBuilder("INSERT INTO channel_pay_detail(operator,uploadtime,outerid,price,paytime,channel,type) VALUES ");
			for (ChannelAccount channelAccount : list){
				//channel_pay_order(operator, uploadtime,outerid,price,paytime,channel,type)
				bathSql.append(String.format(" (%s,'%s',?,?,?,?,?),", operatorId, date));
				parameter.add(channelAccount.outerid);
				parameter.add(channelAccount.price);
				parameter.add(channelAccount.time);
				parameter.add(channelAccount.channel);
				parameter.add(channelAccount.businessType);
			}
			bathSql.deleteCharAt(bathSql.length() - 1);
			jdbcTemplate.update(bathSql.toString(), parameter.toArray());
		}
	}
	
	/**
	 * 渠道到款明细-数据载体
	 * @author heyadong
	 * @version 1.0, 2012-5-22
	 */
	private class ChannelAccount {
		private String outerid;
		private Date time;
		private String channel;
		private String businessType;
		private BigDecimal price;
	}

	
	@Override
	public void uploadConfirmOrder(Workbook workbook, Employee employee)
			throws Exception {
		Date uploadTime = new Date();
		Sheet sheet = workbook.getSheet(SHEET);
		List<ConfirmOrder> list = new ArrayList<ChannelAccountServiceImpl.ConfirmOrder>(BATCH_LIMIT);
		int rows = sheet.getRows();
		
		for (int i = START_ROW; i < rows; i++){
			Cell[] cells = sheet.getRow(i);
			if (cells.length != MagicNumber.TWO) {
			    continue;
			}
			ConfirmOrder confirmOrder = new ConfirmOrder(cells[CONFIRM_ORDER_ID_CELL].getContents().trim(), cells[CONFIRM_REASON_CELL].getContents());
			list.add(confirmOrder);
			if (list.size() > BATCH_LIMIT) {
				saveConfirmOrder(list, employee, uploadTime);
				list.clear();
			}
		}
		saveConfirmOrder(list, employee, uploadTime);
		
	}
	/**
	 * 确认成功的订单
	 * @author heyadong
	 * @version 1.0, 2012-6-8
	 */
	private class ConfirmOrder {
		private String order; 
		private String reason;
		public ConfirmOrder(String order,String reason){
			this.order = order;
			this.reason = reason;
		}
		
	
	}
	//批量保存审批订单号
	private void saveConfirmOrder(List<ConfirmOrder> list, Employee employee, Date uploadtime) {
		if (!list.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(uploadtime);
			Long operatorId = employee.getId();
			List<Object> parameter = new LinkedList<Object>();
			StringBuilder bathSql = new StringBuilder("INSERT INTO channel_pay_confirm_order(operator,uploadtime,_order,reason) VALUES ");
			for (ConfirmOrder confirmOrder : list){
				//channel_pay_confirm_order(operator, uploadtime,_order ,reason)
				bathSql.append(String.format(" (%s,'%s',?,?),", operatorId, date));
				parameter.add(confirmOrder.order);
				parameter.add(confirmOrder.reason);
			}
			bathSql.deleteCharAt(bathSql.length() - 1);
			jdbcTemplate.update(bathSql.toString(), parameter.toArray());
		}
	}

    @Override
    public List<ChannelUploadHistory> getHistory() {
        Pagination pagination = new Pagination();
        pagination.setPageSize(MagicNumber.THIRTY);
        return channelDao.queryUploadHistory(pagination, (short)0);
    }
}
