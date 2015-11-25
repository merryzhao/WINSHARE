package com.winxuan.ec.service.util;

import java.awt.image.BufferedImage;

/**
 * 图片转换接口
 * @author juqkai(juqkai@gmail.com)
 */
public interface ImageParse {
    /**
     * 转换操作
     * @param srcImg
     * @return
     */
    Boolean parseAndSave(String destPath, BufferedImage srcImg);
}
