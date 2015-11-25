package com.winxuan.ec.exception;

/**
 * 订单复制异常
 * @author juqkai(juqkai@gmail.com)
 */
public class OrderCloneException extends Exception{
    private static final long serialVersionUID = -5812853485855046431L;

    public OrderCloneException(String msg) {
        super(msg);
    }
}
