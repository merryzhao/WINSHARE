<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-资讯列表</title>
<%@include file="/page/snippets/version2/meta.jsp" %>
</head>

<body>
<%@include file="/page/snippets/version2/header.jsp" %>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a class="link3" title="文轩网" href="http://www.winxuan.com/">文轩网</a> > 促销公告</span></div>
    <div class="news_left">
        <h1>促销公告</h1>
        <dl class="news_list">
        	<c:forEach items="${list}" var="news">
        		<dt><a target="_blank" class="link1" href="${news.url}" title="${news.title}">${news.title}</a> 
        		<b class="l_gray"><fmt:formatDate value="${news.createTime }" pattern="yyyy-MM-dd HH:mm"/></b></dt>
            	<dd>
            		<c:choose>
            			<c:when test="${fn:length(news.content)>150}">
            				${fn:substring(news.content,0,150)}......
            			</c:when>
            			<c:otherwise>
            				news.content
            			</c:otherwise>
            		</c:choose>
        	</c:forEach>
        </dl>
        <div class="com_pages">
        	<winxuan-page:page pagination="${pagination}" bodyStyle="front-default"/>
        </div>
    </div>
    <div class="news_right">
        <h4 class="pro_tit">促销专题</h4>
        <a class="pro_ads" href="javascript:;"><img src="${serverPrefix}/images/ads/ads04.png" alt=""></a> <a class="pro_ads" href="javascript:;"><img src="${serverPrefix}/images/ads/ads04.png" ></a> <a class="pro_ads" href="javascript:;"><img src="${serverPrefix}/images/ads/ads04.png"></a> </div>
    <div class="hei10"></div>
</div>
<%@include file="/page/snippets/version2/footer.jsp" %>
</body>
</html>
