package com.winxuan.ec.service.verifycode;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;


/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-28 下午12:43:33  --!
 * @description:
 ********************************
 */
public class BufferedImageOpImpl implements BufferedImageOp {

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        return src;
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage src) {
        return null;
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        return null;
    }

    @Override
    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        return null;
    }

    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }

}
