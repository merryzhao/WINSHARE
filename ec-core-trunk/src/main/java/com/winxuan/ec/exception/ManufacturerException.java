package com.winxuan.ec.exception;

import java.io.Serializable;

/**
 * 出版社异常
 * @author heyadong
 * @version 1.0, 2012-9-17 下午04:47:41
 */
public class ManufacturerException extends BusinessException {

    private static final long serialVersionUID = 5203894293365134638L;

    public ManufacturerException(Serializable businessObject, String message) {
        super(businessObject, message);
    }


}
