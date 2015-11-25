package com.winxuan.ec.admin.controller.presentcard;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.winxuan.framework.util.AcceptHashMap;
import com.winxuan.framework.util.DateUtils;
/**
 * 礼券卡查询FORM
 * 
 * @author wumaojie
 * @version 1.0,2011-9-5
 */
public class PresentCardQueryForm {
	//id列表字符串
	private String id;
	//卡类型
	private Long type;
	//卡状态
	private Long[] status;
	//客户姓名
	private String orderId;
	//面值
	private BigDecimal denomination;
	//起始时间
	private String startdate;
	//截止时间
	private String enddate;
	// 时间格式
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	
	private short style;

	public short getStyle() {
		return style;
	}
	public void setStyle(short style) {
		this.style = style;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getType() {
		if(type.intValue()==0){
			return null;
		}
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long[] getStatus() {
		return status;
	}
	public void setStatus(Long[] status) {
		this.status = status;
	}
	public String getOrderId() {
		if(orderId!=null&&!"".equals(orderId)){
			return orderId.trim();
		}
		return null;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getDenomination() {
		return denomination;
	}
	public void setDenomination(BigDecimal denomination) {
		this.denomination = denomination;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	/**
	 * 返回起始有效时间
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public Date getStartDate() throws ParseException{
		if (startdate != null && !"".equals(startdate)) {
			return DateUtils.getEarlyInTheDay(dateformat.parse(startdate));
		}
		return null;
	}
	/**
	 * 返回截止有效时间
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public Date getEndDate() throws ParseException{ 
		if (enddate != null && !"".equals(enddate)) {
			return DateUtils.getLateInTheDay(dateformat.parse(enddate));
		}
		return null;
	}
	
	/**
	 * 返回id数组
	 * 
	 * @return
	 */
	public String[] getIds(){
		if(id==null||"".equals(id)){
			return null;
		}
		String[] ids = id.split("[\\D]");
		return ids;
	}
	
	public Map<String, Object> generateQueryMap() throws ParseException{
		Map<String, Object> parameters = new AcceptHashMap<String, Object>()	
			.acceptIf("ids", getIds(),id!=null)
			.acceptIf("type", type,type!=null&&type!=0)
			.acceptIf("statusList", status,status!=null)
			.acceptIf("orderId",orderId, !StringUtils.isBlank(orderId))
			.acceptIf("denomination",denomination, denomination!=null)
			.acceptIf("startDate", getStartDate(),startdate!=null)
			.acceptIf("endDate", getEndDate(),enddate!=null);	
		return parameters;
	}
	@Override
	public String toString() {
		return "PresentCardQueryForm [id=" + id + ", type=" + type
				+ ", status=" + Arrays.toString(status) + ", name=" + orderId
				+ ", denomination=" + denomination + ", startdate=" + startdate
				+ ", enddate=" + enddate + ", dateformat=" + dateformat + "]";
	}	
}
