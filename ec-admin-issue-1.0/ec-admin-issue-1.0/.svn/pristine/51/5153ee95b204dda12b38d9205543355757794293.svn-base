package com.winxuan.ec.admin.controller.present;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 礼券查询FORM
 * 
 * @author wumaojie
 * @version 1.0,2011-8-31
 */
public class PresentFindForm {

	// 编码类型
	private String codeName;
	// 编码字符串
	private String coding;
	// 状态
	private Long[] status;

	// 启示有效时间
	private String startTime;
	// 截止有效时间
	private String endTime;
	// 时间格式
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

	public String getCodeName() {
		if ("".equals(codeName)) {
			return null;
		}
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding;
	}

	public Long[] getStatus() {
		return status;
	}

	public void setStatus(Long[] status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public List<Long> getStatusList() {
		if (status != null) {
			List<Long> list = new ArrayList<Long>();
			for (Long lon : status) {
				list.add(lon);
			}
			return list;
		}
		return null;
	}
    /**
     * 开始条件时间为 null， 则返回 null。开始条件时间不为null结束条件时间为null，
     * 则返回开始条件时间。两者都有值则返回值较小者。
     * @return
     * @throws ParseException
     */
	public Date getStartDate() throws ParseException {
		//开始条件不为空
		if (endTime != null && !"".equals(startTime)) {
			Date startDate = null;
			Date adate = dateformat.parse(startTime);
			//结束条件不为空,则取两条件中最小的时间
			if(startTime != null && !"".equals(endTime)){
				Date bdate = dateformat.parse(endTime);
				startDate = adate.getTime()<bdate.getTime()?adate:bdate;
			}else{
				startDate = adate;
			}
			return startDate;
		}
		return null;
	}
    /**
     * 结束条件时间为 null， 则返回 null。结束条件时间不为null开始条件时间为null，
     * 则返回结束条件时间。两者都有值则返回值较大者。
     * @return
     * @throws ParseException
     */
	public Date getEndDate() throws ParseException {
		//结束条件不为空
		if (endTime != null && !"".equals(endTime)) {
			Date endDate = null;
			Date bdate = dateformat.parse(endTime);
			//开始条件不为空,则取两条件中最大的时间
			if(startTime != null && !"".equals(startTime)){
				Date adate = dateformat.parse(startTime);
				endDate = adate.getTime()<bdate.getTime()?bdate:adate;
			}else{
				endDate = bdate;
			}
			//本日期 大于目标,需在此日期上加一天，使目标日期包含本日期
			endDate.setDate(endDate.getDate()+1);
			return endDate;
		}
		return null;
	}

	public Object[] getCodingLong() {
		if (coding == null || "".equals(coding.trim())) {
			return null;
		}
		String[] codes = coding.split("\r\n");
		if ("code".equals(codeName)||"customers".equals(codeName)) {
			return codes;
		} else {
			Long[] longs = new Long[codes.length];
			int i = 0;
			for (String string : codes) {
				try {
					longs[i] = Long.valueOf(string);
				} catch (NumberFormatException e) {
					continue;
				}
				i++;
			}
			return longs;
		}
	}
}
