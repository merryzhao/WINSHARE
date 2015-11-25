<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信平台</title>
</head>
<body>
<div class="frame">
<!-- 引入top部分 -->
<%@include file="../snippets/frame-top.jsp"%>
<%@include file="../snippets/frame-left-message.jsp"%>
<div class="frame-main">
	<div class="frame-main-inner" id="content">
		保存失败!规则重复.!请注意查看
		<hr/>
		<h1>${message }</h1>,
	</div>
</div>
</div>
<%@include file="../snippets/scripts.jsp" %>
</body>
</html>