//package com.winxuan.eleven;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//import org.apache.oro.text.regex.MalformedPatternException;
//import org.apache.oro.text.regex.MatchResult;
//import org.apache.oro.text.regex.Pattern;
//import org.apache.oro.text.regex.PatternMatcherInput;
//import org.apache.oro.text.regex.Perl5Compiler;
//import org.apache.oro.text.regex.Perl5Matcher;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
///**
// * ******************************
// * 
// * @author:新华文轩电子商务有限公司,cast911
// * @lastupdateTime:2013-11-6 下午1:20:36 --!
// * @description:
// ******************************** 
// */
//public class AddFavorite {
//
//    public static void main(String[] args) throws IOException, MalformedPatternException {
//        String path = "E:\\新华文轩\\工作安排\\2013\\天猫11.11\\添加立即购买按钮测试\\test.html";
//        File file = new File(path);
//        Document document = Jsoup.parse(file, "UTF-8");
//        String regex = "http://favorite.taobao.com/popup/add_collection.htm?.*id=\\d+.*";
//        Elements elements = document.select("[href~=" + regex + "]");
//        for (Element element : elements) {
//            String href = element.attr("href");
//            String id = AddFavorite.regexId(href);
//            String buyHref = "<a href=\"http://detail.tmall.com/item.htm?id=" + id + "\" class=\"sbtn\">立即购买</a>";
//            element.after(buyHref);
//        }
//
//        String htmlFile = document.html();
//
//        FileWriter write = new FileWriter(new File("D:\\fix.html"));
//        write.write(htmlFile);
//        write.flush();
//        write.close();
//
//    }
//
//    public static String regexId(String content) throws MalformedPatternException {
//
//        String regexId = "id=\\d+";
//        Pattern pattern = new Perl5Compiler().compile(regexId);
//        Perl5Matcher matcher = new Perl5Matcher();
//        PatternMatcherInput matcherInput = new PatternMatcherInput(content);
//        while (matcher.contains(matcherInput, pattern)) {
//            MatchResult result = matcher.getMatch();
//            String[] ids = result.toString().split("=");
//            return ids[1];
//        }
//        return "";
//    }
//
//}
