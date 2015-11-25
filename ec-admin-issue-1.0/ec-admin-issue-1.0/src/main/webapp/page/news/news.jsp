<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.winxuan.com/tag/page"  prefix="winxuan-page"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>新闻发布</title>
<link rel="stylesheet" href="${contextPath}/css/slidingtabs-horizontal.css">
<link rel="stylesheet" href="${contextPath}/css/news.css">
</head>
<body>
<div class="frame">
		<!-- 引入top部分 -->
<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
<%@include file="../snippets/frame-left-order.jsp"%>
<div class="frame-main">
			<!-- 核心内容部分div -->
<div class="frame-main-inner" id="content">
<div >
<div class="layout">
    <div class="news_left">
    
        <h1 >新闻发布 V0.1</h1>
<table class="list-table" >  
<thead>
  <tr>
   <td>ID</td>
   <td>标题</td>
   <td><a href="javascript:;" class="add" >添加新闻</a></td>       		
  </tr>
  </thead>
<tbody>
<c:forEach items="${list}" var="news">
  <tr>
   <td>${news.id}</td>
   <td>${news.title}</td>
     <td><a href="javascript:;" class="edit" >编辑内容</a>||<a href="javascript:;" class="del">删除</a></td>
     <td><input type="hidden" class="newsinfo" value="<c:out value="${news.content}"></c:out>"/></td>       		
  </tr>
  
 </c:forEach>
</tbody>
 
  </table>
        <div class="com_pages">
        	<winxuan-page:page pagination="${pagination}" bodyStyle="front-default"/>
        </div>
    </div>
   
</div>

<div class="text" id="text">
<div class="loaddiv">
<p>
<img alt="..." src="http://static.winxuancdn.com/css/images/loading.gif">
保存中...
</p>
</div>
<div class="header" >
新闻发布
<div style="float:right;">
<a href="javascript:;" class="close">关闭</a>
</div>
</div>
<div>
<span>标题:</span>
<input name="id" type="hidden" value="0" />
<input name="title" class="title" type="text" /><br>
</div>

<textarea name="content" class="content" ></textarea>

<div class="button">
<button>保存当前文本</button></div>

</div>




</div>
</div>
</div>
</div>

<script src="http://static.winxuancdn.com/libs/core.js?20111229"></script>
<script type="text/javascript"
		src="${contextPath}/js/news/news.js"></script>	
</body>
</html>
