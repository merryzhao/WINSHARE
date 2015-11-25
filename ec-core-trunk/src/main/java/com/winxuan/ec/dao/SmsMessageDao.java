package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.message.SmsOrderMessage;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 短信消息DAO
 * @author heyadong
 * @version 1.0, 2013-1-14 下午03:37:39
 */
public interface SmsMessageDao {
    @Save
    void save(SmsOrderMessage msg);
    @Update
    void update(SmsOrderMessage msg);
    @Get
    SmsOrderMessage get(Long id);
    
    @Query("SELECT s FROM SmsOrderMessage s")
    List<SmsOrderMessage> query(@ParameterMap Map<String, Object> params,@Page Pagination pagination);
    
}
