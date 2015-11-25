    <%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
        <!DOCTYPE>
        <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>九月手机客户端-文轩网</title>
        <jsp:include page="/page/snippets/v2/meta.jsp"><jsp:param value="help" name="type"/></jsp:include>
        </head>
        <body>
        <div class="wrap">
       
        <div class="quickmark_jy">
        <div class="quickmark_header warp clearfix">
        <h1><a href="http://www.winxuan.com" class="logo"></a></h1>
        <div class="buy">
        <a href="http://www.winxuan.com/help/quickmark_wenxuan.html" class="buy_wenxuan">文轩手机购物</a>
        <a href="http://www.winxuan.com/help/quickmark_jiuyue.html" class="buy_jiuyue">九月读书客户端</a>
        </div>
        </div>
        <div class="bg_warp">
        <div class="content warp">
        <div class="banner" id="banner">
        <div class="banner_list" id="banner_list">
        <img src="http://static.winxuancdn.com/help/images/qu_img_banner.jpg"/>
        </div>
        </div>
        <div class="download">
        <div class="left">
        <div class="phone_download">
        <strong>手机下载</strong>
        <p>手机登陆m.9yue.com下载</p>
        </div>
        <div class="quickmark_download">
        <strong>二维码下载</strong>
        <p><img src="http://static.winxuancdn.com/help/images/quickmark_jiuyue.jpg"/></p>
        </div>
        </div>
        <div class="now_download">
        <div class="pad">
        <img src="http://static.winxuancdn.com/help/images/qu_img_pad.jpg"/>
        <!--<strong>iPad版</strong>-->
        <a href="https://itunes.apple.com/app/jiu-yue-du-shu/id566826904">App Store下载</a>
        </div>
        <div class="apple">
        <img src="http://static.winxuancdn.com/help/images/qu_img_apple.jpg"/>
        <!--<strong>iPhone版</strong>-->
        <a href="https://itunes.apple.com/app/jiu-yue-du-shu-for-iphone/id670670008">App Store下载</a>
        </div>
        <div class="android">
        <img src="http://static.winxuancdn.com/help/images/qu_img_android.jpg"/>
        <!--<strong>Android版</strong>-->
        <a href="http://www.9yue.com/readerapp/android2/9yue_reader.apk">立即下载</a>
        </div>
        <div class="pc">
        <img src="http://static.winxuancdn.com/help/images/qu_img_pc.jpg"/>
        <!--<strong>电脑版</strong>-->
        <a href="http://www.9yue.com/reader/9yueReader0.9.2.msi">立即下载</a>
        </div>
        </div>
        </div>
        <a href="http://reader.9yue.com/channel/question.html" class="common_problems">常见问题></a>
        <div class="feature">
        <ul>
        <li class="feature_1"><p>私人书房，承载所有的故事</p></li>
        <li class="feature_2">夜间模式，阅读不伤眼</li>
        <li class="feature_3">支持丰富的电子书格式</li>
        <li class="feature_4">和印刷书籍一样的书香</li>
        </ul>
        </div>
        </div>
        </div>
        </div>
        <jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
        <script>
        seajs.use(["top","qrcode"], function(top,qrcode) {
        top.init();
        qrcode({client:"jiuyue"});
        })
        </script>
        </div>
        </body>
        </html>













