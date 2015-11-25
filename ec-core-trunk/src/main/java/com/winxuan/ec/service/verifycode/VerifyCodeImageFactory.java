/*
 * @(#)VerifyCodeService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.verifycode;

import java.io.IOException;

import com.winxuan.ec.model.verifycode.VerifyCode;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-25 下午1:38:03 --!
 * @description:
 ******************************** 
 */
public interface VerifyCodeImageFactory {

    /**
     * 返回一个SupportVerifyCode verifyCode 是计算好的验证码; BufferedImage
     * 是处理的图片对象,可以用类似ImageIO.write(svc.getImage(), "jpg", outputStream);写出去
     * 
     * @return
     * @throws IOException 
     */
    VerifyCode createImage() throws IOException;
    
    /**
     * 
     *  默认为final int width = 79;
     *   final int height = 33;
     * @return
     * @throws IOException 
     */
    VerifyCode createImage(int width,int height) throws IOException;

}
