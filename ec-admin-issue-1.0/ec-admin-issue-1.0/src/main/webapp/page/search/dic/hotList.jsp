<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>未审核通过词典列表</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<%@include file="../../snippets/meta.jsp"%>
	<link type="text/css" href="${pageContext.request.contextPath}/css/search/dic.css" rel="stylesheet" />
	<style type="text/css">
	.syshot li{
		display: inline;
	}
	</style>
</head>
<body>
	<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-search.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
				<div>
					系统热搜:
					<ul class="syshot">
						<c:forEach items="${syshots}" var="hot">
							<li>${hot.keyword}</li>
						</c:forEach>
					</ul>
				</div>
				<br>
				<button bind="updateHot">更新</button>
				<span class="msg">&nbsp;</span>
				<table width="100%" class="list-table view" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>关键字</th>
							<th>地址</th>
						</tr>
					</thead>
					<tbody id="hotList">
					<c:forEach items="${hots}" var="hot" varStatus="status">
						<tr data-id = "${status.index + 1}">
							<td><input type="text" name="keyword" value="${hot.keyword}" style="width:400px;"></td>
							<td><input type="text" name="href" value="${hot.href}" style="width:400px;"></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
	 		</div>
 		</div>
 	</div>

 	<%@include file="../../snippets/scripts.jsp"%>
 	<script src="${contextPath}/js/search/hot.js"></script>
</body>
</html>