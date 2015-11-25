package com.winxuan.ec.exception;

/**
 * 搭配推荐异常
 * @author juqkai(juqkai@gmail.com)
 */
public class ProductSaleBundleException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ProductSaleBundleException(String msg) {
        super(msg);
    }
}
