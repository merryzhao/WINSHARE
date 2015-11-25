package com.winxuan.ec.admin.controller.monitor;

import java.util.Date;

import com.winxuan.ec.support.util.MagicNumber;

/**
 * 监控商品表单参数
 * @author heyadong
 * @version 1.0, 2012-10-18 下午02:48:23
 */
public class MonitorProductSaleForm {
    //监控频率.分钟为单位
    private int frequency = MagicNumber.THIRTY;
   
    //监控开始日期
    private Date start;
    //监控结束日期
    private Date end;
    private String[] mobiles;
    private String[] emails;
    private String message;
    private String name;
   
    private String description;
    
    private int stockLess;
   
    public int getStockLess() {
        return stockLess;
    }
    public void setStockLess(int stockLess) {
        this.stockLess = stockLess;
    }
  
    public Date getStart() {
        return start;
    }
    public void setStart(Date start) {
        this.start = start;
    }
    public Date getEnd() {
        return end;
    }
    public void setEnd(Date end) {
        this.end = end;
    }
    public String[] getMobiles() {
        return mobiles;
    }
    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }
    public String[] getEmails() {
        return emails;
    }
    public void setEmails(String[] emails) {
        this.emails = emails;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getFrequency() {
        return frequency;
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
