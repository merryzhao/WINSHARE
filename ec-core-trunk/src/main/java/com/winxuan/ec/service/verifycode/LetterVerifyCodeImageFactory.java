package com.winxuan.ec.service.verifycode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.verifycode.VerifyCode;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-25 下午2:08:49 --!
 * @description:字母模式的验证码
 ******************************** 
 */
@Service("letterVerifyCodeImageFactory")
@Transactional(rollbackFor = Exception.class)
public class LetterVerifyCodeImageFactory implements VerifyCodeImageFactory {

    private static final char[] LETTER = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static final int DOT = 1;

    private static final int FONT_SIZE_MAIN = 20;

    private static final int BASE_THE_OFFSET = 3;
    private static final int FC = 180;
    private static final int BC = 250;

    private static final int CREATE_IMAGE_COUNT = 4;

    private static final int DRAW_STRINGX = 18;
    private static final int DRAW_STRINGY = 2;

    @Override
    public VerifyCode createImage() throws IOException {
        return this.createImage(VerifyCode.DEFAULT_WIDTH, VerifyCode.DEFAULT_HEIGHT);
    }

    @Override
    public VerifyCode createImage(int width, int height) throws IOException {
        VerifyCode svc = new VerifyCode();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        Color white = this.getRandomColor();
        g.setColor(white);
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("", Font.ITALIC, FONT_SIZE_MAIN));
        int drawStringX = RandomUtils.nextInt(BASE_THE_OFFSET);
        String randStr = "";
        Random random = new Random();
        BufferedImageOp bufferedImageOp = new BufferedImageOpImpl();
        for (int i = 0; i < CREATE_IMAGE_COUNT; i++) {
            String str = LETTER[random.nextInt(LETTER.length)] + "";
            int drawStringY = DRAW_STRINGY;

            if (RandomUtils.nextBoolean()) {
                drawStringY -= RandomUtils.nextInt(BASE_THE_OFFSET);
                str = str.toUpperCase();
            } else {
                drawStringY += RandomUtils.nextInt(BASE_THE_OFFSET);
                str = str.toLowerCase();
            }

            BufferedImage singImage = this.createSingleIamge(str, white);
            g.drawImage(singImage, bufferedImageOp, drawStringX, drawStringY);
            drawStringX += DRAW_STRINGX + RandomUtils.nextInt(BASE_THE_OFFSET);
            randStr += str;
        }

        this.printLog(g);
        g.dispose();
        svc.setImage(image);
        svc.setVerifyCode(randStr);
        return svc;
    }

    /**
     * 生成log,可以是干扰线,可以是其他文字
     * 
     * @param g
     */
    private void printLog(Graphics2D g) {
        Random random = new Random();
        for (int j = 0; j < 50; j++) {
            int x = random.nextInt(VerifyCode.DEFAULT_WIDTH);
            int y = random.nextInt(VerifyCode.DEFAULT_HEIGHT);
            int x1 = random.nextInt(DOT);
            int y1 = random.nextInt(DOT);
            g.setColor(this.getRandomColor());
            g.drawLine(x, y, x + x1, y + y1);
        }

    }

    /**
     * 创建单个验证字符的图片
     * 
     * @param str
     * @return
     * @throws IOException
     */
    private BufferedImage createSingleIamge(String str, Color bgColor) throws IOException {
        Random random = new Random();
        final int colorElse = 20;
        final int randomRed = colorElse + random.nextInt(80);
        final int randomGreen = colorElse + random.nextInt(100);
        final int randomBlue = colorElse + random.nextInt(90);
        BufferedImage image = new BufferedImage(20, VerifyCode.DEFAULT_HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        g.setColor(bgColor);
        g.fillRect(0, 0, 20, VerifyCode.DEFAULT_HEIGHT);
        final int fontSize = 15;
        g.setFont(new Font("", Font.BOLD, fontSize));

        //生成较为温和的颜色,避免影响阅读
        Color color = new Color(randomRed, randomGreen, randomBlue);
        g.setColor(color);
        int drawStringX = 3 + RandomUtils.nextInt(BASE_THE_OFFSET);
        int drawStringY = 10 + RandomUtils.nextInt(20);
        g.drawString(str, drawStringX, drawStringY);
        return image;

    }

    /**
     * 生成随机颜色
     * 
     * @return
     */
    private Color getRandomColor() {
        Random random = new Random();
        int red = FC + random.nextInt(BC - FC);
        int green = FC + random.nextInt(BC - FC);
        int blue = FC + random.nextInt(BC - FC);
        return new Color(red, green, blue);
    }
}
