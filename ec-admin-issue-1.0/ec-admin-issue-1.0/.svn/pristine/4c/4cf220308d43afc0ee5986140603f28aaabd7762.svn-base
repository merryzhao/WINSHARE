<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<html>
<head>
<%@include file="../snippets/meta.jsp" %>
<title>渠道管理</title>
</head>
<body>
<div id="content-result">
<h4>${channel.name}列表</h4>
<%@include file="../snippets/list-tools.jsp" %>
<c:choose>
	<c:when test="${!empty channelList}">
	<table class="list-table" style="width:1000px;">
		<colgroup>
			<col class="name"/><col class="type"/><col class="state"/><col class="date-time"/><col class="operate"/>
		</colgroup>
		<tr><th>名称</th><th>类别</th><th>状态</th><th>修改时间</th><th>操作</th></tr>
		<c:forEach var="channel" items="${channelList}" varStatus="status">
			<c:set var="rowStatus" value="odd"/>
			<c:if test="${status.index%2==1}">
				<c:set var="rowStatus" value="even"/>
			</c:if>
			<c:set var="className" value="child-of-node-${channel.parent.id}"/>
			<c:if test="${empty channel.parent}">
			<c:set var="className" value=""/>
			</c:if>
			<tr class="${className} ${rowStatus}" id="node-${channel.id}">
				<td>${channel.name}</td>
				<td>${channel.type.name}</td>
				<td>
				<c:choose>
					<c:when test="${channel.available}"><input type="checkbox" value="1" checked="checked" name="available"/><span>已启用</span></c:when>
					<c:otherwise><input type="checkbox" value="1" name="available"/>未启用</c:otherwise>
				</c:choose>
				</td>
				<td>${channel.createTime}</td>
				<td><a href="#" class="operate-link">添加子渠道</a>|<a href="#" class="operate-link">编辑</a></td>
			</tr>
		</c:forEach>
	</table>
	</c:when>
	<c:otherwise>
		<table class="list-table">
		<colgroup><col class="name"/><col class="type"/><col class="state"/><col class="date-time"/><col class="operate"/></colgroup>
		<tr><th>名称</th><th>类别</th><th>状态</th><th>创建时间</th><th>操作</th></tr>
		</table>
		<div class="no-content">
			<div class="no-content-info">本渠道下暂无子渠道 <a href="#">添加子渠道</a></div>
		</div>
	</c:otherwise>
</c:choose>
<%@include file="../snippets/list-tools.jsp" %>
</div>
<%@include file="../snippets/scripts.jsp" %>
</body>
</html>