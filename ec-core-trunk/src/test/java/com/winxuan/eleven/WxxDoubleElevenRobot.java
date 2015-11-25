package com.winxuan.eleven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.winxuan.eleven.model.DoubleEleven;
import com.winxuan.eleven.model.DoubleElvenGroup;
import com.winxuan.eleven.service.DoubleElevenService;


/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-6 下午1:24:27  --!
 * @description:
 ********************************
 */
@Component("wxxDoubleElevenRobot")
public class WxxDoubleElevenRobot implements DoubleElevenRobot {

    Map<Integer, String> map = new HashMap<Integer, String>();

    {
        map.put(1, "one star");
        map.put(2, "second star");
        map.put(3, "third star");
        map.put(4, "fourth star");
        map.put(5, "fifth star");

    }

    @Autowired
    DoubleElevenService doubleElevenService;

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "/com/winxuan/eleven/applicationContext.xml");
        WxxDoubleElevenRobot wxxDoubleElevenRobot = (WxxDoubleElevenRobot) ctx.getBean("wxxDoubleElevenRobot");
        String result = wxxDoubleElevenRobot.process();
        FileWriter write = new FileWriter(new File("D:\\a.html"));
        write.write(result);
        write.flush();
        write.close();
    }

    @Override
    public String process(List<DoubleEleven> item) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < item.size(); i++) {
            DoubleEleven de = item.get(i);
            builder.append("<li class=\"J_TWidget\" data-widget-type=\"Countdown\"  data-widget-config=\"{'endTime': '2013-11-11 00:00:00','" +
            		"interval': 1000,'timeRunCls':'.ks-countdown-run', " +
            		"'timeUnitCls':{'d': '.ks-d','h': '.ks-h','m': " +
            		"'.ks-m','s': '.ks-s','i': '.ks-i'},'minDigit':" +
            		" 1,'timeEndCls': '.ks-countdown-end'}\">\n");
            builder.append("<p class=\"img\"><span class=\""
                    + map.get(i + 1)
                    + "\"></span><a href=\""+de.getProductUrl()+"\" title=\""+de.getProductName()+"\" target=\"_blank\">" +
                    		"<img src=\""+de.getImagUrl()+"\" alt=\"\" /></a></p>\n");
            builder.append("<div class=\"J_TWidget hidden\" data-widget-type=\"Popup\" data-widget-config=\"{\n");
            builder.append("          'trigger':'.img',\n");
            builder.append("          'align':{\n");
            builder.append("                  'node':'.img',\n");
            builder.append("                  'offset':[0,0],\n");
            builder.append("                  'points':['tr','tl']\n");
            builder.append("                  }\n");
            builder.append("            }\">\n");
            builder.append("    <div class=\"pop\">\n");
            builder.append("<p class=\"intro\">" + de.getIntroduction() + "</p>\n");
            builder.append("  <span class=\"triangleleft\"></span>\n");
            builder.append("</div>\n");
            builder.append("</div>\n");
            builder.append("<p class=\"bookname\"><a href=\"" + de.getProductUrl() + "\" title=\""
                    + de.getProductName() + "\" target=\"_blank\">" + de.getProductName() + "</a></p>\n");
            builder.append("<p class=\"amount\">销量：" + de.getTotalSales() + "</p>\n");
            builder.append("<p class=\"price\">双11价：<strong>￥"
                    + de.getSalePrice().setScale(2, BigDecimal.ROUND_HALF_UP) + "</strong></p>\n");
            builder.append("<p class=\"btn\"><span class=\"ks-countdown-run\"><a href=\"" + de.getFavoriteLink()
                    + "\" title=\"立即收藏\" class=\"fav_btn\">立即收藏</a></span><span class=\"ks-countdown-end\"><a href=\""
                    + de.getProductUrl()
                    + "\" class=\"buy_btn\" target=\"_blank\" title=\"立即购买\">立即购买</a></span></p>\n");
            builder.append("\n");
            builder.append("</li>\n");
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public String process() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= 12; i++) {
            List<DoubleEleven> result = doubleElevenService.findAllByGid(i);
            DoubleElvenGroup deg = doubleElevenService.getGroupById(i);
            if ((i % 4) != 0) {
                builder.append("<li class=\"bestseller\"><h2 class=\"title\">" + deg.getName() + "</h2>\n");
            } else {
                builder.append("<li class=\"bestseller nmr\"><h2 class=\"title\">" + deg.getName() + "</h2>\n");
            }

            builder.append("<ul class=\"booklist\">\n");
            builder.append(this.process(result));
            builder.append("</ul>\n");
            builder.append("<a href=\"\" title=\"查看完整榜单\" class=\"check_all\"></a>\n");
            builder.append("</li>");
        }

        return builder.toString();
    }
}
