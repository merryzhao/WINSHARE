<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-资讯详细</title>
<%@include file="/page/snippets/version2/meta.jsp" %>
</head>
<body>
<%@include file="/page/snippets/version2/header.jsp" %>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a class="link3" title="文轩网" href="http://www.winxuan.com/">文轩网</a> > ${news.type==1?"促销公告":"新闻公告"}</span></div>
    <div class="news_left">
        <h1>${news.title }</h1>
        <h3><span class="fr">
        	<%-- 分享到：<a title="推荐到新浪微博" id="sina" href="javascript:void(0)">sina</a><a title="推荐到腾讯微博" id="qzone" href="javascript:void(0)">qzone</a><a title="推荐到人人" id="renren" href="javascript:void(0)">renren</a><a title="推荐到开心网" id="kaixing" href="javascript:void(0)">kaixing</a><a title="推荐到豆瓣" id="douban" href="javascript:void(0)">douban</a> --%> 
        	</span>
        	时间：<fmt:formatDate value="${news.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </h3>
        <div class="news_con">
          ${news.content}
        </div>
    </div>
 <%--    <div class="news_right">
        <h4 class="pro_tit">促销专题</h4>
        <a class="pro_ads" href="javascript:;"><img src="${serverPrefix}/images/ads/ads04.png"></a> <a class="pro_ads" href="javascript:;"><img src="${serverPrefix}/images/ads/ads04.png"></a> <a class="pro_ads" href="javascript:;"><img src="${serverPrefix}/images/ads/ads04.png"></a> 
        </div> --%>
    <div class="hei10"></div>
</div>
<%@include file="/page/snippets/version2/footer.jsp" %>
</body>
</html>
