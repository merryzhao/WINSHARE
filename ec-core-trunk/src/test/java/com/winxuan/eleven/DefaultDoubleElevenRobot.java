package com.winxuan.eleven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.winxuan.eleven.model.DoubleEleven;
import com.winxuan.eleven.service.DoubleElevenService;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-6 下午1:21:33 --!
 * @description:
 ******************************** 
 */
@Component("defaultDoubleElevenRobot")
public class DefaultDoubleElevenRobot implements DoubleElevenRobot {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "/com/winxuan/eleven/applicationContext.xml");
        DoubleElevenService doubleElevenService = ctx.getBean(DoubleElevenService.class);

        DoubleElevenRobot doubleElevenRobot = (DoubleElevenRobot) ctx.getBean("defaultDoubleElevenRobot");

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            List<DoubleEleven> data = doubleElevenService.findAllByGid(i);
            builder.append(doubleElevenRobot.process(data));
        }

        FileWriter write = new FileWriter(new File("D:\\a.html"));
        write.write(builder.toString());
        write.flush();
        write.close();

        builder = new StringBuilder();
        for (int i = 11; i <= 20; i++) {
            List<DoubleEleven> data = doubleElevenService.findAllByGid(i);
            builder.append(DefaultDoubleElevenRobot.processModel(data));
        }
        write = new FileWriter(new File("D:\\b.html"));
        write.write(builder.toString());
        write.flush();
        write.close();

    };

    public static String processModel(List<DoubleEleven> item) {
        StringBuilder builder = new StringBuilder();
        builder.append("<div class=\"l-personality\">\n");
        builder.append("            <ul class=\"l-top-nav\">\n");
        builder.append("                <li><a href=\"#1\">图书畅销榜</a></li><li>"
                + "<a href=\"#2\">新书热卖榜</a></li><li><a href=\"#3\">文学艺术榜</a></li><li>"
                + "<a href=\"#4\">小说传记榜</a></li><li><a href=\"#5\">人文社科榜</a></li><li><a href=\"#6\">"
                + "儿童文学榜</a></li><li><a href=\"#7\">素质教育榜</a></li><li>"
                + "<a href=\"#8\">经管励志榜</a></li><li><a href=\"#9\">"
                + "科学技术榜</a></li><li><a href=\"#10\">家居生活榜</a></li>\n");
        builder.append("            </ul>\n");
        builder.append("            <ul class=\"l-personality-nav\">\n");
        builder.append("                <li class=\"current\"><a href=\"#11\">"
                + "点赞·破5万</a></li><li><a href=\"#12\">大学生必读</a></li><li><a href=\"#13\">"
                + "女人必备</a></li><li><a href=\"#14\">作家富豪榜" + "</a></li><li><a href=\"#15\">不旅行会死</a>"
                + "</li><li><a href=\"#16\">吃货</a>" + "</li><li><a href=\"#17\">程序员必备"
                + "</a></li><li><a href=\"#18\">重口味" + "</a></li><li><a href=\"#19\">打鸡血"
                + "</a></li><li><a href=\"#20\">心理读物</a></li>\n");
        builder.append("            </ul>\n");
        builder.append("            <div class=\"l-detail\">\n");
        builder.append("                <img src=\"pic/top-list-11.jpg\" />\n");
        builder.append("                <ul class=\"l-middle\">\n");

        for (int i = 0; i < item.size(); i++) {
            if (i == 10) {
                break;
            }
            DoubleEleven doubleEleven = item.get(i);
            builder.append("                    <li>\n");
            builder.append("                        <a href=\"" + doubleEleven.getProductUrl()
                    + "\" target=\"_blank\" class=\"l-face\"><img src=\"" + doubleEleven.getImagUrl() + "\" /></a>\n");
            builder.append("                        <a href=\"" + doubleEleven.getProductUrl()
                    + "\" target=\"_blank\" title=\"" + doubleEleven.getProductName() + "\" class=\"l-name\">"
                    + doubleEleven.getProductName() + "</a>\n");
            builder.append("                        <b class=\"l-price\">双11价：<em>&yen;"
                    + doubleEleven.getSalePrice().setScale(2, BigDecimal.ROUND_HALF_UP)
                            .setScale(2, BigDecimal.ROUND_HALF_UP) + "</em></b>\n");
            builder.append("                        <a href=\"" + doubleEleven.getFavoriteLink()
                    + "\" class=\"l-favor\">立即收藏</a>\n");
            builder.append("                    </li>\n");
        }

        builder.append("                </ul>\n");
        builder.append("                <p class=\"l-more\"><a href=\"#\" target=\"_blank\">完整榜单&gt;</a></p>\n");
        builder.append("            </div>\n");
        builder.append("        </div>\n");
        return builder.toString();
    }

    public String process(List<DoubleEleven> item) {

        StringBuilder builder = new StringBuilder();

        builder.append("<div class=\"l-top\">\n");
        builder.append("        <ul class=\"l-top-nav\">\n");
        builder.append("        <li class=\"current\"><a href=\"#1\">" +
        		"图书畅销榜</a></li><li><a href=\"#2\">新书热卖榜</a>" +
        		"</li><li><a href=\"#3\">文学艺术榜</a></li><li>" +
        		"<a href=\"#4\">小说传记榜</a></li><li><a href=\"#5\">" +
        		"人文社科榜</a></li><li><a href=\"#6\">儿童文学榜</a></li>" +
        		"<li><a href=\"#7\">素质教育榜</a></li><li><a href=\"#8\">" +
        		"经管励志榜</a></li><li><a href=\"#9\">科学技术榜</a></li><li>" +
        		"<a href=\"#10\">家居生活榜</a></li>\n");
        builder.append("    </ul>\n");
        builder.append("    <ul class=\"l-personality-nav\">\n");
        builder.append("        <li><a href=\"#11\">点赞·破5万</a>" +
        		"</li><li><a href=\"#12\">大学生必读</a></li><li>" +
        		"<a href=\"#13\">女人必备</a></li><li><a href=\"#14\">" +
        		"作家富豪榜</a></li><li><a href=\"#15\">不旅行会死</a></li>" +
        		"<li><a href=\"#16\">吃货</a></li><li><a href=\"#17\">" +
        		"程序员必备</a></li><li><a href=\"#18\">重口味</a></li><li>" +
        		"<a href=\"#19\">打鸡血</a></li><li><a href=\"#20\">心理读物</a></li>\n");
        builder.append("    </ul>\n");
        builder.append("<div class=\"l-detail\">\n");
        builder.append("                <img src=\"pic/top-list-1.jpg\" class=\"l-title-img\" />\n");
        builder.append("                <ul class=\"l-big\">\n");
        for (int i = 0; i < 2; i++) {
            DoubleEleven doubleEleven = item.get(i);
            builder.append("                    <li>\n");
            builder.append("                        <a href=\"" + doubleEleven.getProductUrl()
                    + "\" target=\"_blank\" class=\"l-face\"><img src=\"" + doubleEleven.getImagUrl() + "\" /></a>\n");
            builder.append("                        <a href=\"" + doubleEleven.getProductUrl()
                    + "\" target=\"_blank\" title=\"" + doubleEleven.getProductName() + "\" class=\"l-name\">"
                    + doubleEleven.getProductName() + "</a>\n");
            builder.append("                        <b class=\"l-price\">双11价<em>&yen;"
                    + doubleEleven.getSalePrice().setScale(2, BigDecimal.ROUND_HALF_UP) + "</em></b>\n");
            builder.append("                        <a href=\"" + doubleEleven.getFavoriteLink()
                    + "\" class=\"l-favor\"></a>\n");
            builder.append("                        <span class=\"l-sort sort" + (i + 1) + "\"><span>\n");
            builder.append("                    </li>\n");
        }

        builder.append("                </ul>\n");

        builder.append("                <ul class=\"l-middle\">\n");
        for (int i = 2; i < 7; i++) {
            DoubleEleven doubleEleven = item.get(i);
            builder.append("                    <li>\n");
            builder.append("                        <a href=\"" + doubleEleven.getProductUrl()
                    + "\" target=\"_blank\" class=\"l-face\"><img src=\"" + doubleEleven.getImagUrl() + "\" /></a>\n");
            builder.append("                        <a href=\"" + doubleEleven.getProductUrl()
                    + "\" target=\"_blank\" title=\"" + doubleEleven.getProductName() + "\" class=\"l-name\">"
                    + doubleEleven.getProductName() + "</a>\n");
            builder.append("                        <b class=\"l-price\">双11价：<em>&yen;"
                    + doubleEleven.getSalePrice().setScale(2, BigDecimal.ROUND_HALF_UP) + "</em></b>\n");
            builder.append("                        <a href=\"" + doubleEleven.getFavoriteLink()
                    + "\" class=\"l-favor\">立即收藏</a>\n");
            builder.append("                        <span class=\"l-sort sort" + (i + 1) + "\"><span>\n");
            builder.append("                    </li>\n");
        }
        builder.append("                </ul>\n");

        builder.append("                <ul class=\"l-small\">\n");

        for (int i = 7; i < 17; i++) {
            DoubleEleven doubleEleven = item.get(i);
            builder.append("                    <li>\n");
            builder.append("                        <a href=\"" + doubleEleven.getProductUrl()
                    + "\" target=\"_blank\" title=\"" + doubleEleven.getProductName() + "\" class=\"l-name\">"
                    + doubleEleven.getProductName() + "</a>\n");
            builder.append("                        <b class=\"l-price\">双11价：<em>&yen;"
                    + doubleEleven.getSalePrice().setScale(2, BigDecimal.ROUND_HALF_UP) + "</em></b>\n");
            builder.append("                        <span class=\"l-sort\">" + (i + 1) + "<span>\n");
            builder.append("                    </li>\n");

        }
        builder.append("                </ul>\n");
        builder.append("                <p class=\"l-more\"><a href=\"#\" target=\"_blank\">完整榜单&gt;</a></p>\n");
        builder.append("            </div>\n");
        builder.append("</div>\n");
        return builder.toString();

    }

    @Override
    public String process() {
        // TODO Auto-generated method stub
        return null;
    }
}
