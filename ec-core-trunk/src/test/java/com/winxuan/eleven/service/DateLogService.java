package com.winxuan.eleven.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Colour;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.winxuan.eleven.DateCell;

/**
 * 
 * @author Other
 *
 */
@SuppressWarnings("deprecation")
@Component("dateLogService")
public class DateLogService {
    
    private static final Logger LOG = LoggerFactory
			.getLogger(DateLogService.class);
    
    private static final Integer DAY = -1;
    
    private static final String QT = "qt";
    
    private static final String COUNT = "SELECT count(*) %s from %s %s";
    
    private static final String COUNT_OTHER = "SELECT count(%s) %s from %s %s";
    
    private static final String SUM = "SELECT  SUM(%s) %s from %s %s";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 注意：查询条件如果含有中文需要加 单引号 ' '
     * @param name  日志输出名称
     * @param table 需要查询表名
     * @param begin 开始时间
     * @param end   结束时间
     * @param filed 需要查询的字段以及条件
     * @return      返回总条数
     */
	public int query(String name,String table,Date begin,Date end,Map<String,Object> filed){
		String sql = select(COUNT,"count",table,conditions(begin,end,filed));
		Integer count = jdbcTemplate.queryForInt(sql);
		log(sql,name,count);
		return count;
	}
	
	public int queryCOUNT(String ct,String name,String table,Date begin,Date end,Map<String,Object> filed){
		String sql = selectCOUNT(COUNT_OTHER,ct,"count",table,conditions(begin,end,filed));
		Integer count = jdbcTemplate.queryForInt(sql);
		log(sql,name,count);
		return count;
	}
	
	
	public int querySUM(String sum,String name,String table,Date begin,Date end,Map<String,Object> filed){
		String sql = select(SUM,sum,"sum",table,conditions(begin,end,filed));
		Integer count = jdbcTemplate.queryForInt(sql);
		log(sql,name,count);
		return count;
	}
	
	public int queryTIME(String name,String table,String begin,String end,Map<String,Object> filed){
		String sql = select(COUNT,"count",table,conditions(begin,end,filed));
		Integer count = jdbcTemplate.queryForInt(sql);
		log(sql,name,count);
		return count;
	}
	
	private void log(String sql , String name ,Integer count){
		LOG.info(String.format("%s \n <%s  :  %s>",sql, name,count));
	}
	
	private String select(String count,String name,String table,String where){
		String sql = String.format(count, name,table,where);
		return sql;
	}
	
	private String select(String sum,String count,String name,String table,String where){
		String sql = String.format(sum,count,name,table,where);
		return sql;
	}
	
	private String selectCOUNT(String sum,String count,String name,String table,String where){
		String sql = String.format(sum,count,name,table,where);
		return sql;
	}
	
	private String conditions(Date begin,Date end,Map<String,Object> fileds){
		String cond = "";
		if(begin!=null && end != null || fileds.size()>0){
			cond += "where ";
		}
		if (begin!=null && end != null){
			String qt = "createtime";
			if(fileds!=null && fileds.get(QT)!=null){
				qt = fileds.get(QT).toString();
				fileds.remove(QT);
			}
			cond += qt+ " BETWEEN "+ formarTime(begin,false) + " and " + formarTime(end,true);
			if(fileds!=null && fileds.size()>0){
				cond += " and ";
			}
		}
		if(fileds!=null && fileds.size()>0){
		int s = fileds.size();
			for (Map.Entry<String,Object> filed : fileds.entrySet()) {
				s--;
				cond += filed.getKey() + " " +filed.getValue();
				if(s>0){
					cond += " and ";
				}
			}
		}
		return cond;
	}
	
	private String conditions(String begin,String end,Map<String,Object> fileds){
		String cond = "";
		if(begin!=null && end != null || fileds.size()>0){
			cond += "where ";
		}
		if (begin!=null && end != null){
			String qt = "createtime";
			if(fileds != null && fileds.get(QT) != null){
				qt = fileds.get(QT).toString();
				fileds.remove(QT);
			}
			cond += qt+" BETWEEN "+ begin + " and " + end;
			if(fileds!=null && fileds.size()>0){
				cond += " and ";
			}
		}
		if(fileds!=null && fileds.size()>0){
		int s = fileds.size();
			for (Map.Entry<String,Object> filed : fileds.entrySet()) {
				s--;
				cond += filed.getKey() + " " +filed.getValue();
				if(s>0){
					cond += " and ";
				}
			}
		}
		return cond;
	}
	
	private String formarTime(Date date,boolean isStart){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		if(isStart){
			time = "'"+time + " 23:59:59'";  
		}else{
			time = "'"+time + " 00:00:00'";  
		}
		return time;
	}
	
	public String formarTime(Date date,String time,boolean isStart){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time1 = format.format(date);
		if(isStart){
			time1 = "'"+time1 + time +"'";  
		}else{
			time1 = "'"+time1 + time +"'";  
		}
		return time1;
	}
	
	public Date getDay(Integer day){
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.set(Calendar.DATE, aCalendar.get(Calendar.DATE)+day);
		return aCalendar.getTime();
	}
	
	public int getWeek(Date date){  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        return cal.get(Calendar.DAY_OF_WEEK) ;  
    }  
	
	 public String getWeeks(Date date){  
	        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date);  
	        int weekindex = cal.get(Calendar.DAY_OF_WEEK) - 1;  
	        if(weekindex<0){  
	            weekindex = 0;  
	        }   
	        return weeks[weekindex];  
	    }  
	
	
//文档生产	
//----------------------------------------------------------------------------------------	
    public void createXLS(){
    	 try {
    		 Workbook book = Workbook.getWorkbook(new File("C:\\Users\\Other\\Desktop\\datelog\\datelog.xls"));
    		 File out = new File("C:\\Users\\Other\\Desktop\\datelog\\log.xls");
    		 WritableWorkbook wwb = Workbook.createWorkbook(out,book);
			 Sheet[] sheets = book.getSheets();
			 if(getWeek(getDay(0))==2){
				 List<List<DateCell>> datecellslist = new ArrayList<List<DateCell>>();
				 for (int i = -3; i <= -1; i++) {
					 datecellslist.add(findAll(i));
				 }
				 int l = 0;
				 for (Sheet sheet : sheets) {
					 WritableSheet wws = wwb.createSheet(sheet.getName(), l);
					 insertRowData(sheet,wws);
					 l++;
					 for (int i = 0; i < datecellslist.size(); i++) {
						 insertDateLog(sheet,wws,l,datecellslist.get(i));
						 setDayTime(l,wws,-(i+1));
					 }
				 }
			 }else{
				 int l = 0;
				 List<DateCell> datecells = findAll(DAY);
				 for (Sheet sheet : sheets) {
					 WritableSheet wws = wwb.createSheet(sheet.getName(), l);
					 l++;
					 insertRowData(sheet,wws);
					 insertDateLog(sheet,wws,l,datecells);
					 setDayTime(l,wws,-1);
					 
				 }
			 }
			
    		wwb.removeSheet(4);
    		wwb.removeSheet(5);
    		wwb.removeSheet(6);
    		 wwb.write();
    		 wwb.close();  
    		 LOG.info("完成加载....");
		} catch (Exception e) {
			LOG.info("文件异常"+e);  
		}
    }
    
    private void insertRowData(Sheet sheet,WritableSheet wws){  
        try{  
            Label label;  
            for(int i=0;i<4;i++){  
            	Cell[] cells = sheet.getRow(i);
            	for (Cell cell : cells) {
            		label = new Label(cell.getColumn(),cell.getRow(),cell.getContents() ,cell.getCellFormat());  
            		wws.addCell(label); 
				}
            }  
        }catch(Exception e){  
            LOG.info("文件异常"+e);  
        }  
    }   
    
    private void insertDateLog(Sheet sheet,WritableSheet wws,int i, List<DateCell> datecells){
    	try {
    		for (DateCell datecell : datecells) {
    			if(datecell.getSheet()==i){
    				Label label = new Label(datecell.getCol(),datecell.getRow(),datecell.getValue(),getDataCellFormat(CellType.STRING_FORMULA)); 
    				wws.addCell(label);
    				wws.setRowView(datecell.getRow(),500);
    			}
			}
		} catch (RowsExceededException e) {
			LOG.info("文件异常"+e);  
		} catch (WriteException e) {
			LOG.info("文件异常"+e);  
		}
    }
    
    private void setDayTime(Integer sheet,WritableSheet wws,Integer daytime){
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Label label = new Label(0, 3-daytime,simpleDateFormat.format(getDay(daytime)),getDataCellFormat(CellType.DATE_FORMULA));
    	Label label2 = new Label(1, 3-daytime,getWeeks(getDay(daytime)),getDataCellFormat(CellType.STRING_FORMULA));
    	try {
			wws.addCell(label);
			wws.addCell(label2);
		} catch (RowsExceededException e) {
			LOG.info("文件异常"+e);  
		} catch (WriteException e) {
			LOG.info("文件异常"+e);  
		}
    }
    
    /** 
     * 得到数据格式 
     * @return 
     */ 
    private static WritableCellFormat getDataCellFormat(CellType type){  
        WritableCellFormat wcf = null;  
        try {  
            //字体样式  
            if(type == CellType.NUMBER || type == CellType.NUMBER_FORMULA){//数字  
               NumberFormat nf = new NumberFormat("#.00");  
               wcf = new WritableCellFormat(nf);   
            }else if(type == CellType.DATE || type == CellType.DATE_FORMULA){//日期  
                jxl.write.DateFormat df = new jxl.write.DateFormat("yyyy-MM-dd hh:mm:ss");   
                wcf = new jxl.write.WritableCellFormat(df);   
            }else{  
                WritableFont wf = new WritableFont(WritableFont.TIMES,10, WritableFont.NO_BOLD,false);//最后一个为是否italic  
                wcf = new WritableCellFormat(wf);  
            }  
            //对齐方式  
            wcf.setAlignment(Alignment.CENTRE);  
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  
            //边框  
            wcf.setBorder(Border.LEFT,BorderLineStyle.THIN);  
            wcf.setBorder(Border.BOTTOM,BorderLineStyle.THIN);  
            wcf.setBorder(Border.RIGHT,BorderLineStyle.THIN);  
            //背景色  
            wcf.setBackground(Colour.WHITE);  
            wcf.setWrap(true);//自动换行  
              
        } catch (WriteException e) {  
         LOG.info("文件异常"+e);    
        }  
        return wcf;  
    }   
	
    
    private List<DateCell> findAll(Integer daytime){
    	List<DateCell> list = new ArrayList<DateCell>();
    	Date dateTime = getDay(daytime);
		Map<String,Object> area = new HashMap<String, Object>();
		area.put("available", " = 1");
		createCell(2,3,daytime,query("可用地区","area", null, null, area),list);
		createCell(3,3,daytime,query("可用公司","deliverycompany", null, null, area),list);
		createCell(4,3,daytime,query("可用方式","deliverytype", null, null, area),list);
		
		Map<String,Object> ro = new HashMap<String, Object>();
		ro.put("type", " = 24001");
		ro.put(QT, " refundtime");
		createCell(9,3,daytime,query("退货订单数量","returnorder", dateTime, dateTime, ro),list);
		
		Map<String,Object> ro1 = new HashMap<String, Object>();
		ro1.put("type", " = 24002");
		ro1.put(QT, " refundtime");
		createCell(10,3,daytime,query("换货订单数量","returnorder", dateTime, dateTime, ro1),list);
		
		Map<String,Object> ro2 = new HashMap<String, Object>();
		ro2.put("istransferred", " = 1");
		ro2.put(QT, " refundtime");
		createCell(11,3,daytime,query("下传中启退换货数量","returnorder", dateTime, dateTime, ro2),list);
		
		Map<String,Object> ro3 = new HashMap<String, Object>();
		ro3.put("`status`", " in (25004,25005,25006,25007)");
		ro3.put(QT, " refundtime");
		createCell(12,3,daytime,query("回传退换货数量","returnorder",dateTime, dateTime, ro3),list);
		
		createCell(18,2,daytime,query("新增新品数量","product", dateTime, dateTime, null),list);

		Map<String,Object> ro4 = new HashMap<String, Object>();
		ro4.put(QT, "updatetime");
		createCell(19,2,daytime,query("商品信息更新数量","product_sale", dateTime, dateTime, ro4),list);

		createCell(20,2,daytime,query("数据Feed数量","search_index", dateTime, dateTime, null),list);

		Map<String,Object> ro5 = new HashMap<String, Object>();
		ro5.put("c.`user`", "= u.id");
		ro5.put("c.channel", "= 104");
		ro5.put(QT, "u.registertime");
		createCell(5,3,daytime,query("EC新用户数量"," customer c,user u ", dateTime, dateTime, ro5),list);
		
		Map<String,Object> ro6 = new HashMap<String, Object>();
		ro6.put("c.`user`", "= u.id");
		ro6.put("c.channel", " = 104");
		ro6.put(QT, "u.lastlogintime");
		createCell(6,3,daytime,query("登录用户数量"," customer c,user u ", dateTime, dateTime, ro6),list);

		Map<String,Object> ro7 = new HashMap<String, Object>();
		ro7.put("o.customer", "= c.user");
		ro7.put("c.channel", "= 104");
		ro7.put(QT, "o.createtime");
		createCell(7,3,daytime,queryCOUNT("DISTINCT c.user","下单用户数量"," _order o ,customer c ", dateTime, dateTime, ro7),list);

		Map<String,Object> ro8 = new HashMap<String, Object>();
		ro8.put("cc.customer", "= c.user");
		ro8.put(QT, "cc.createtime");
		createCell(8,3,daytime,queryCOUNT("DISTINCT c.user","投诉用户数量","customer c,customer_complain cc ", dateTime, dateTime, ro8),list);

		Map<String,Object> ro9 = new HashMap<String, Object>();
		ro9.put("`status`", "= 461016");
		createCell(16,3,daytime,query("原卡原退失败数量","refund_credential", dateTime, dateTime, ro9),list);

		Map<String,Object> ro10 = new HashMap<String, Object>();
		ro10.put("`status`", "= 461016");
		createCell(15,3,daytime,query("提现账户数量","customer_cashapply", dateTime, dateTime, ro10),list);
		
		Map<String,Object> ro11 = new HashMap<String, Object>();
		ro11.put("istransferred", "= 1");
		ro11.put("deliverycode", " is null");
		createCell(13,3,daytime,query("发票下传中启数量","order_invoice", dateTime, dateTime, ro11),list);
		
		Map<String,Object> ro12 = new HashMap<String, Object>();
		ro12.put("deliverycode", " is not null");
		createCell(14,3,daytime,query("发票回传数量","order_invoice", dateTime, dateTime, ro12),list);
		
		createCell(17,3,daytime,query("礼券生成","present_batch", dateTime, dateTime, null),list);
		
		Map<String,Object> ro13 = new HashMap<String, Object>();
		ro13.put("state", " in (17003)");
		ro13.put(QT,"updatetime");
		createCell(18,3,daytime,query("激活","present_log", dateTime, dateTime, ro13),list);
		
		Map<String,Object> ro14 = new HashMap<String, Object>();
		ro14.put("status", " in (18001)");
		ro14.put(QT,"createdate");
		createCell(19,3,daytime,query("礼品卡生成","presentcard", dateTime, dateTime, ro14),list);
		
		Map<String,Object> ro15 = new HashMap<String, Object>();
		ro15.put("status", " in (18005)");
		ro15.put(QT,"createdate");
		createCell(20,3,daytime,query("激活","presentcard_status_log", dateTime, dateTime, ro15),list);
		
		Map<String,Object> ro16 = new HashMap<String, Object>();
		ro16.put("payment", " in (21)");
		ro16.put(QT, "paymenttime");
		createCell(21,3,daytime,querySUM("money", "使用金额","order_payment", dateTime, dateTime, ro16),list);
		
		Map<String,Object> ro17 = new HashMap<String, Object>();
		ro17.put(QT,"updatetime");
		createCell(2,2,daytime,queryTIME("全量","product_warehouse", formarTime(dateTime, " 09:30:00", false), formarTime(new Date(), " 09:30:00", true), ro17),list);
		
		Map<String,Object> ro18 = new HashMap<String, Object>();
		ro18.put("location", "= '8A17'");
		ro18.put(QT,"updatetime");
		createCell(3,2,daytime,queryTIME("8A17","product_warehouse", formarTime(dateTime, " 09:30:00", false), formarTime(new Date(), " 09:30:00", true), ro18),list);
	
		Map<String,Object> ro19 = new HashMap<String, Object>();
		ro19.put("location", "= 'D818'");
		ro19.put(QT,"updatetime");
		createCell(4,2,daytime,queryTIME("8A17","product_warehouse", formarTime(dateTime, " 09:30:00", false), formarTime(new Date(), " 09:30:00", true), ro19),list);
		
		return list;
    }
	
    private DateCell createCell(Integer col,Integer sheet,Integer row,Integer value,List<DateCell> list){
    	DateCell cell = new DateCell();
    	cell.setCol(col);
    	cell.setRow(3-row);
    	cell.setSheet(sheet);
    	cell.setValue(value.toString());
    	list.add(cell);
    	return cell;
    }
    
    
    private  List<DateCell> datecells(Integer daytime){
    	List<DateCell> datecells = new ArrayList<DateCell>();
    	for (int i = 2; i < 22; i++) {
    		DateCell cell = new DateCell();
        	cell.setCol(i);
        	cell.setRow(3-daytime);
        	cell.setSheet(3);
        	cell.setValue((i*10)+"");
        	datecells.add(cell);
		}
    	return datecells;
    }
}
