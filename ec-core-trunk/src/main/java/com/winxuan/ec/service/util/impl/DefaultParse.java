package com.winxuan.ec.service.util.impl;

import java.awt.image.BufferedImage;

import com.winxuan.ec.service.util.ImageParse;
import com.winxuan.framework.util.image.ImageTool;

/**
 * 默认转换, 即不做转换
 * @author juqkai(juqkai@gmail.com)
 */
public class DefaultParse implements ImageParse{

    public Boolean parseAndSave(String destPath, BufferedImage srcImg) {
        return ImageTool.save(destPath, srcImg);
    }
}
