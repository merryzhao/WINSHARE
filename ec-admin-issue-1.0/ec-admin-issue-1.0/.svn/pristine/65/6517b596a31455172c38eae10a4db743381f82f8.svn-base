<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets2/meta.jsp"%>
<title>报表列表</title>
<style type="text/css">
</style>
</head>
<body>	
	<div id="wrap">
	<c:if test="${isAsync == 1}">
		<div class="control-group">
			<span>系统正在导出报表，请稍后在“历史导出记录”中下载导出内容！</span>
		</div>
	</c:if>
	<c:if test="${pagination!=null}">
		<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
	</c:if>
		<c:if test="${grid.exported && exportPagination != null}">
		<div class="control-group">
		<select onchange="setPage(this);">
			<c:forEach items="${exportPagination}" var="exportPage"
				varStatus="status">
				<option value="${status.count}">第${exportPage[0]}-第${exportPage[1]}条</option>
			</c:forEach>
		</select> 
		<%--<input type="button" value="导出" onclick="submitGridForm(${orderColumnIndex},${isAsc},'xls');" class="btn btn-info"/>--%>
		<input type="button" value="导出CSV" onclick="submitGridForm(${orderColumnIndex},${isAsc},'csv');" class="btn btn-info"/>
		</div>
		</c:if>
		<table class="list-table table table-striped" id="product">
			<thead>
			<tr>
				<c:forEach items="${table.header}" var="tableHeader" varStatus="status">
					<th>
						${tableHeader}
						<c:if test="${grid.columns[status.index].order}">
						<c:choose>
							<c:when test="${orderColumnIndex == status.index && isAsc}">
							升序 <a href="javascript:submitGridForm(${status.index},false,'');" onclick="">降序</a>
							</c:when>
							<c:when test="${orderColumnIndex == status.index && !isAsc}">
							<a href="javascript:submitGridForm(${status.index},true,'');">升序</a> 降序
							</c:when>
							<c:otherwise>
							<a href="javascript:submitGridForm(${status.index},true,'');">升序</a> <a href="javascript:submitGridForm(${status.index},false,'');">降序</a>
							</c:otherwise>
						</c:choose>
						</c:if>
					</th>
				</c:forEach>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${table.body}" var="bodytr">
				<tr>
					<c:forEach items="${bodytr}" var="bodytd">
						<td>${bodytd}</td>
					</c:forEach>
				</tr>
			</c:forEach>
			<tr>
				<c:forEach items="${table.footer}" var="foot33">
					<th>${foot33}</th>
				</c:forEach>
			</tr>
			</tbody>
		</table>
		</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath}/js/report/grid.js"></script>
	<script type="text/javascript">
	$(function(){
		$("body").resize(function(){
			parent.window.updateHeight($("body").innerHeight());
		});
		parent.window.updateHeight($("body").innerHeight());
	});
	</script>
	<br>
	<br>
</body>
</html>