<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>版本历史列表</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<link type="text/css" rel="stylesheet" href="../ckeditor/_samples/sample.css" />
	<style type="text/css">
	#roadmap-body{
	width: 500;
	margin-left: 50px;
	margin-top: 30px;
	}
	#show-div{
	margin-left: 50px;
	margin-top: 10px;
	}
	</style>
</head>
<body>
<!-- 引入JS -->
	<%@include file="../snippets/scripts.jsp"%>
	<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
		<div class="frame-main-inner" id="content">
<div id="roadmap-body">
 		<table class="list-table">
			<tr>
				<th>创建时间</th>
				<th>内容</th>
				<th></th>
			</tr>
			<c:forEach items="${list}" var="roadmap">
			<tr>
			<td><fmt:formatDate type="date" value="${roadmap.createTime}"/></td>
			<td>${roadmap.noStylecontent}</td>
			<td>
		    <a class="roadmapinfo" href="#">详情>></a>
		    <span style="display: none;">${roadmap.content}</span>
		    </td>
			</tr>
			</c:forEach>
		</table>
		<c:if test="${pagination!=null}">
			<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
		</c:if>
		</div>
		<div id="roadmap-show">
 		<div id="show-div"><span id="show"></span></div>
 		</div>
 		</div>
 		</div>
 		</div>
	<%@include file="../snippets/scripts.jsp"%>
	<%@include file="../snippets/meta.jsp"%>
	<script type="text/javascript" 
		src="${contextPath}/js/roadmap/roadmap.js"></script>
 
</body>
</html>
