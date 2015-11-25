package com.winxuan.ec.exception;

/**
 * 批量发货异常
 * @author heyadong
 * @version 1.0, 2012-11-13 下午02:40:51
 */
public class BatchDeliveryException extends Exception{
    
    private static final long serialVersionUID = 383150194248015738L;

    public BatchDeliveryException(String msg){
        super(msg);
    }
}
