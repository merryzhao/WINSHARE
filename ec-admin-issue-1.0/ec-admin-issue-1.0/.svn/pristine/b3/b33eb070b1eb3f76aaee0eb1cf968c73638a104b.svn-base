<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script type="text/javascript"></script>
<body>
<div id="content-result">
<h4>订单跟踪创建</h4>
<form action="/order/create" method="GET">
<p><label>订单编号:</label><label name="id">20091204341013</label></p>
<p><label>订单跟踪类型:</label>
<select>
<c:forEach var="code" items="${listCode}" varStatus="status">
<option id="${status.index}" >${code.name}</option>
</c:forEach>
</select>

</p>

<label style="margin:0pt 0pt 10pt 0pt;"> 订单跟踪内容:</label>
<a href="/order/create/"></a>
<textarea   name= "content"  style="overflow:auto" rows="2"> </textarea> 

<div class="center">
<input type="submit" name="create" value="创建"/>
<button name="reset">重置</button>
<button name="back">返回</button>
</div>
</form>
</div>
<script type="text/javascript" src="js/jQuery.js"></script>
<script type="text/javascript" src="js/create.js"></script>
</body>
</html>