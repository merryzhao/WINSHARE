package com.winxuan.ec.service.verifycode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.verifycode.VerifyCode;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-25 下午2:09:07 --!
 * @description:默认模式的验证码,数字模式
 ******************************** 
 */
@Service("defaultVerifyCodeImageFactory")
@Transactional(rollbackFor = Exception.class)
public class DefaultVerifyCodeImageFactory implements VerifyCodeImageFactory {

    @Override
    public VerifyCode createImage() {
        return this.createImage(VerifyCode.DEFAULT_WIDTH, VerifyCode.DEFAULT_HEIGHT);

    }

    /**
     * 产生随机的颜色
     * 
     * @return
     */
    private Color getColor(int fc, int bc) {
        final int color = 255;
        Random random = new Random();
        if (fc > color) {
            fc = color;
        }
        if (bc > color) {
            bc = color;
        }

        int red = fc + random.nextInt(bc - fc);
        int green = fc + random.nextInt(bc - fc);
        int blue = fc + random.nextInt(bc - fc);

        return new Color(red, green, blue);
    }

    @Override
    public VerifyCode createImage(int width, int height) {
        VerifyCode supportVerifyCode = new VerifyCode();
        // 定义图片的宽度和高度
        // 创建图片对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 得到图像的环境对
        Graphics g = image.createGraphics();
        // 用随机颜色填充图像背
        Random random = new Random();
        final int colorFc = 180;
        final int colorBc = 250;
        g.setColor(getColor(colorFc, colorBc));
        g.fillRect(0, 0, width, height);
        final int forNum = 5;
        final int colorFc1 = 50;
        final int colorBc1 = 100;
        final int xLen = 4;
        final int yLen = 4;
        for (int i = 0; i < forNum; i++) {
            g.setColor(getColor(colorFc1, colorBc1));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawOval(x, y, xLen, yLen);
        }
        final int fontSize = 28;
        g.setFont(new Font("", Font.PLAIN, fontSize));
        // 生成随机字符
        String sRand = "";
        final int forNum1 = 4;
        final int colorElse = 20;
        final int random1 = 10;
        final int random2 = 80;
        final int random3 = 100;
        final int random4 = 90;
        final int drawStringX = 9;
        final int drawStringY = 27;
        final int forNum2 = 12;
        final int randomX1 = 9;
        final int randomY1 = 9;
        for (int i = 0; i < forNum1; i++) {
            String rand = String.valueOf(random.nextInt(random1));
            sRand += rand;
            // 生成随机颜色
            g.setColor(new Color(colorElse + random.nextInt(random2), colorElse + random.nextInt(random3), colorElse
                    + random.nextInt(random4)));
            g.drawString(sRand, drawStringX, drawStringY);
            // 生成干扰
            for (int j = 0; j < forNum2; j++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int x1 = random.nextInt(randomX1);
                int y1 = random.nextInt(randomY1);
                g.drawLine(x, y, x + x1, y + y1);
            }
        }

        // 使画像生
        g.dispose();
        supportVerifyCode.setImage(image);
        supportVerifyCode.setVerifyCode(sRand);
        return supportVerifyCode;
    }

}
