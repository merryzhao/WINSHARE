package com.winxuan.ec.exception;

/**
 * 短信异常
 * @author heyadong
 * @version 1.0, 2013-1-17 上午09:32:34
 */
public class SmsMessageException extends Exception{

    private static final long serialVersionUID = 1508735579869536383L;
    public SmsMessageException(){}
    public SmsMessageException(String msg){
        super(msg);
    }
    
}
