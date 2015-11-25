<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>附件列表</title>
<style type="text/css">
</style>
<!-- 引入JS -->
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>附件列表</h4>
				<!-- 查询表单div -->
					<c:if test="${pagination!=null}">
						 <winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				<table class="list-table">
					<tr>
						<th>名称</th>
						<th>路径</th>
						<th>类型</th>
						<th>分类</th>
						<th>创建时间</th>
 					</tr>
					<c:forEach items="${list }" var="attach">
						<tr>
							<td><a href="/files/${attach.path}">${attach.name }</a></td>
							<td>${attach.path }</td>
							<td>${attach.type }</td>
							<td>${attach.sort.name }</td>
							<td><fmt:formatDate value="${attach.createTime }" type="both"/></td>
 						</tr>
					</c:forEach>
					<tr>
						<th>名称</th>
						<th>路径</th>
						<th>类型</th>
						<th>分类</th>
						<th>创建时间</th>
 					</tr>
				</table>
				<c:if test="${pagination!=null}">
						 <winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>