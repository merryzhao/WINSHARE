package com.winxuan.ec.support.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * description
 * 
 * @author yuhu
 * @version 1.0, Jul 16, 2010 2:18:41 PM
 */

public class ImageUtils {

	/**
	 * 压缩图片file为最大边界为maxBoder的缩略图
	 * @param file
	 * @param reFile
	 * @param maxBoder 最大边界值
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public static File dealPic(File file, File reFile,int pix, int maxBoder)
			throws IOException {
		BufferedImage bis = null;
		try {
			bis = ImageIO.read(file);
		} catch (IOException e1) {
			return null;
		}
		int w = bis.getWidth();
		int h = bis.getHeight();
		BufferedImage bid = null;
		AffineTransform transform = new AffineTransform();
		if (w>maxBoder || h>maxBoder) {
			 if(w>=h){
		            int nh = (maxBoder * h) / w;
		            double sx = (double)maxBoder / w;
			        double sy = (double)nh / h;
			        transform.setToScale(sx,sy);
			        AffineTransformOp ato = new AffineTransformOp(transform, null);
			        bid = new BufferedImage(maxBoder, nh, BufferedImage.TYPE_3BYTE_BGR);
			        ato.filter(bis,bid);
			        ImageIO.write(bid, "jpg", reFile);
		      }else{
		            int nw = (maxBoder * w) / h;
		            double sx = (double)nw / w;
			        double sy = (double)maxBoder / h;
			        transform.setToScale(sx,sy);
			        AffineTransformOp ato = new AffineTransformOp(transform, null);
			        bid = new BufferedImage(nw, maxBoder, BufferedImage.TYPE_3BYTE_BGR);
			        ato.filter(bis,bid);
			        ImageIO.write(bid, "jpg", reFile);
		      }
		} else {
			ImageIO.write(bis, "jpg", reFile);
		}

		return reFile;
	}
}
