<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信补发</title>
</head>
<body>
<div class="frame">
<!-- 引入top部分 -->
<%@include file="../snippets/frame-top.jsp"%>
<%@include file="../snippets/frame-left-message.jsp"%>
<div class="frame-main">
	<div class="frame-main-inner" id="content">
		<c:if test="${message != null }"><h1 style="color:red">${message	 }</h1></c:if>
		<form action="/message/retry" method="post">
			<h2>EC订单号</h2>
			<textarea rows="" cols="" name="orders" ></textarea>
			<input value="确认发送" type="submit" ></input>
		</form>
	</div>
</div>
</div>

<%@include file="../snippets/scripts.jsp" %>
<script src="../../js/message/new.js"></script>
</body>
</html>