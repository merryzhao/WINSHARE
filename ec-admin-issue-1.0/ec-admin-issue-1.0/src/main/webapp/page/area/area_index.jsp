<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<link href="${contextPath}/css/jquery.treeTable.css" rel="stylesheet"/>
<title>查询区域-配送方式</title>
</head>
<body>
<div class="frame">
	<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
	<%@include file="../snippets/frame-left-system.jsp"%>
	<div class="frame-main">
		<div class="frame-main-inner" id="content">
			<iframe src="${contextPath}/area/0" name="contentWrap" class="content-iframe" frameborder="0"></iframe>
		</div>
	</div>
</div>
<%@include file="../snippets/scripts.jsp" %>
<script src="${contextPath}/js/jquery.treeTable.min.js"></script>
<script src="${contextPath}/js/channel/index.js"></script>
</body>
</html>