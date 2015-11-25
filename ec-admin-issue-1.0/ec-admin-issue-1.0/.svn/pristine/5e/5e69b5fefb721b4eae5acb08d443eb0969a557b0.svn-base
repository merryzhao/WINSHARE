<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
#treeText {
	width: 280px;
	height: 300px;
	max-width: 280px;
	max-height: 300px;
	border: 0px;
	text-align: left;
}
</style>
</head>
<body>
	<c:if test="${!empty tables}">
		<div class="foldhead">
			<img id="tableTreeImg" src="${contextPath}/imgs/area/close.jpg"
				onclick="showTable('${contextPath}');" /> <span id="dataBaseName">${dataSource.name}</span>
		</div>

		<div id="tablesContent">

			<textarea id="treeText" disabled="disabled">							
									<c:forEach items="${tables}" var="tableName">
										${tableName }
									</c:forEach>								
								</textarea>

		</div>
	</c:if>
	<c:if test="${empty tables}">
	无数据
	</c:if>
	<script type="text/javascript" src="${contextPath}/js/report/grid.js"></script>
</body>
</html>