package com.winxuan.ec.model.verifycode;

import java.awt.image.BufferedImage;


/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-28 下午1:04:51  --!
 * @description:
 ********************************
 */
public class VerifyCode {

    public static final int DEFAULT_WIDTH = 79;
    public static final int DEFAULT_HEIGHT = 33;

    private String verifyCode;

    private BufferedImage image;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
