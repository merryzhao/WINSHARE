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
	<table class="list-table">
		<tbody>
			<tr>
				<th>ID</th>
				<th>类型</th>
				<th>创建人</th>
				<th>备注</th>
				<th>短信内容</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${list }" var="m">
				<tr>
					
					<td><a href="/message/edit/${m.id }">${m.id }</a></td>
					<td><a href="/message/edit/${m.id }">${m.type.name }</a></td>
					<td>${m.employee.name }</td>
					<td>${m.remark }</td>
					<td style="max-width: 350px;" align="left">${m.message }</td>
					<td>
					<input <c:if test="${m.enable }">disabled="disabled"</c:if> value="启用" type="button" onclick="javascript:$.post('/message/enable/${m.id}',{},function(){location.reload();});" />
					<input <c:if test="${!m.enable }">disabled="disabled"</c:if> value="禁用" type="button" onclick="javascript:$.post('/message/unable/${m.id}',{},function(){location.reload();});" />
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<winxuan-page:page bodyStyle="javascript" pagination="${pagination}"></winxuan-page:page>
	</div>
</div>
</div>
<%@include file="../snippets/scripts.jsp" %>
</body>
</html>