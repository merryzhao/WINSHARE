package com.winxuan.ec.service.message;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.SmsMessageException;
import com.winxuan.ec.model.message.SmsOrderMessage;
import com.winxuan.ec.model.order.ParentOrder;
import com.winxuan.framework.pagination.Pagination;

/**
 * 短信消息服务
 * @author heyadong
 * @version 1.0, 2013-1-14 下午03:40:25
 */
public interface SmsMessageService {
    
    void save(SmsOrderMessage msg) throws SmsMessageException;
    
    void update(SmsOrderMessage msg) throws SmsMessageException;
    
    SmsOrderMessage get(Long id);
    
    List<SmsOrderMessage> query(Map<String, Object> params, Pagination pagination);
    
    /**
     * 获取订单短信模板
     * @param type 订单短信类型
     * @param order 
     * @param outofQuantity 缺货品种数
     * @return 短信模板.   NULL为该订单不符合条件, 
     */   
    String getMessage(Long type, ParentOrder parentOrder, int outofQuantity);
    
}
