package com.winxuan.ec.task.support.utils;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 电子书封面处理
 * @author luosh
 *
 */
public class ConvertImage {
	private static final Log LOG = LogFactory.getLog(ConvertImage.class);
	private static final String PATH_DIR="/data/ebook/9yuebook/";
//	private static  String FORMATNAME="png";
	/**
	 * ����ƷͼƬ�������
	 * @author xujianguo	
	 * @param srcImageName	ԴͼƬ
	 * @param destImageName Ŀ��ͼƬ
	 * @param width			��ŵĿ��
	 * @param height		��ŵĸ߶�
	 * @throws IOException 
	 */
	public static void scale(String srcImageName, String destImageName,
			String formatName,int width) throws IOException{
		File imgFile = new File(srcImageName);
		BufferedImage srcImage = ImageIO.read(imgFile);
		srcImage = prepareProcessImage(srcImage, width);
		File outFile = new File(destImageName);
		ImageIO.write(srcImage, formatName, outFile);
	}
	public static void scale(File imgFile , File outFile,
			String formatName,int width) throws IOException{
		BufferedImage srcImage = ImageIO.read(imgFile);
		srcImage = prepareProcessImage(srcImage, width);
		ImageIO.write(srcImage, formatName, outFile);
	}
	public static BufferedImage prepareProcessImage(BufferedImage image,
			int backWidth) {
		int imageWidth = image.getWidth();
		if (imageWidth > backWidth ) {
			float zoomOutSale = (float) imageWidth / backWidth ;
			if (zoomOutSale > 1) {
				image = scale(image, zoomOutSale, false);
			}
		}
		return image;
	}
	/**
	 * ��ͼƬ�������
	 * @param bimage ͼƬ
	 * @param scale ��ű���
	 * @param flag  trueΪ�Ŵ� false Ϊ��С
	 * @return
	 */
	public static BufferedImage scale(BufferedImage bimage, float scale, boolean flag) {
		int width = bimage.getWidth();
		int height = bimage.getHeight();
		if (flag) {
			width = (int) (width * scale);
			height = (int) (height * scale);
		} else {
			width = (int) (width / scale);
			height = (int) (height / scale);
		}
		Image image = bimage.getScaledInstance(width, height,
				Image.SCALE_DEFAULT);
		bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bimage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}
//	public static void main(String args[]) throws Exception {
//		ConvertImage.createImage("G:\\xdd\\big1.jpg", "G:\\xdd\\big.jpg", 225, 325);
//		ConvertImage.createImage("G:\\xdd\\big.jpg", "G:\\xdd\\middln.jpg", 140, 200);
//		ConvertImage.createImage("G:\\xdd\\big.jpg", "G:\\xdd\\small.jpg", 80, 115);
//	}
	public static void createImage(String srcImg, String newImg, int width, int height) throws IOException{
		BufferedImage  bufferedImage = loadImg(srcImg);
		Image image = bufferedImage.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		saveImgFile(newImg, bufferedImage,  newImg.substring(newImg.lastIndexOf(".")+1,newImg.length()));
	}
	
//	static private void convert(long bookid) throws IOException{
//		String path=getPath(bookid);
//		String srcfile=path+"middle.jpg";
//		if (!new File(srcfile).exists()){
//			LOG.info("file not found:---"+srcfile);
//			return;
//		}
//		ConvertImage.scale(srcfile, path + "middle.png",FORMATNAME, 160);
//		ConvertImage.scale(srcfile, path + "small.png",FORMATNAME, 100);
//		ConvertImage.scale(srcfile, path + "tiny.png",FORMATNAME, 50);
//		
//	}
	
	
	 /**
     * 读入图片文件
     * @param fnm
     * @return BufferedImage
	 * @throws IOException 
     */
    private static BufferedImage loadImg(String newfnm) throws IOException {
        BufferedImage bi = null;
        bi = ImageIO.read(new File(newfnm));
        return bi;
    }
    
    /**
     * 图片存盘
     * @param fnm
     * @param img
     * @throws IOException 
     */
    public static void saveImgFile(String fnm, BufferedImage img,String imgType) throws IOException {
            ImageIO.write(img, imgType, new File(fnm));
    }
	
	public static String getRangDirName(long bookId) {
		String result = "1-10000";
		try{
	         long min = bookId/10000 * 10000+1;
	         long max = (bookId/10000 + 1) * 10000;
	         result = min+"-"+max;
		}catch(Exception e){	
			LOG.info(e.getMessage());
		}		
		return result;
	}
	public static String getPath(Long bookId){
		return PATH_DIR +getRangDirName(bookId)+"/"+bookId+"/cover/";
	}
	
	public static final int convertImage(String srcImg, String dstImg, int width, int height)
	{
		ProcessBuilder builder;
		List<String> command = new ArrayList<String>();
		command.add("/usr/bin/convert");
		command.add(srcImg);
		command.add("-auto-orient");
		if (width * height > 0)
		{
			command.add("-thumbnail");
			command.add(String.format("%dx%d", new Object[] {
				Integer.valueOf(width), Integer.valueOf(height)
			}));
		}
		command.add(dstImg);
		builder = new ProcessBuilder(command);
		Process process;
		try {
			process = builder.start();
			return process.waitFor();
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
		return 0;
		
	}
}
