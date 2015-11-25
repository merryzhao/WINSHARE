package com.winxuan.ec.service.util.impl;

import java.awt.image.BufferedImage;

import com.winxuan.ec.service.util.ImageParse;
import com.winxuan.framework.util.image.ImageTool;

/**
 * 边缩放
 * @author juqkai(juqkai@gmail.com)
 */
public class SideSizeParse implements ImageParse{
    private int width;
    private int height;
    public SideSizeParse(int width, int height){
        this.width = width;
        this.height = height;
    }
	

    public Boolean parseAndSave(String destPath, BufferedImage srcImg) {
        srcImg = ImageTool.zoom(srcImg, width, height);
        return ImageTool.save(destPath, srcImg);
    }

}
