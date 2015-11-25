<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>开放平台应用列表 - 文轩网</title>
<%@include file="../snippets/meta.jsp"%>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>开放平台应用表</h4>
				<!-- 查询表单div -->
					<c:if test="${pagination!=null}">
						${pagination}
					</c:if>
				<table class="list-table">
					<tr>
						<th>应用编号</th>
						<th>应用密钥</th>
						<th>类型</th>
						<th>联系人</th>
						<th>电话</th>
						<th>邮箱</th>
						<th>公司/网址</th>
						<th>操作</th>
 					</tr>
					<c:forEach items="${applications}" var="app">
						<tr>
							<td>${app.id }</td>
							<td>[${app.appSecret}]</td>
							<td>[${app.type}]</td>
							<td>[${app.conginess}]</td>
							<td>[${app.phone}]</td>
							<td>[${app.email}]</td>
							<td>${app.company}</td>
							<td>
							<c:if test="${!app.available}">
								<input class="active" type="button" value="启用" for="${app.id }" title="${app.appSecret}"/>
							</c:if>
							<c:if test="${app.available}">
								<input class="block" type="button" value="停用" for="${app.id }"/>
							</c:if>
							<input class="delete"  type="button" value="删除" for="${app.id }"/>
							</td>
 						</tr>
					</c:forEach>
				</table>
				<c:if test="${pagination!=null}">
						${pagination}
					</c:if>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/operate.js"></script>
	
</body>
</html>