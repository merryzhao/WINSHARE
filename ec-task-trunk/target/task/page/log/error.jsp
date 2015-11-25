<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Error Log---Winxuan.Com</title>
<%@include file="../snippets/meta.jsp"%>
<link type="text/css" href="${pageContext.request.contextPath}/css/custom/jquery-ui-timepicker-addon.css" rel="stylesheet"/>
</head>
<body>
<div class="ui-grid ui-widget ui-widget-content ui-corner-all"> 
<div class="ui-grid-header ui-widget-header ui-corner-top">
错误日志 [<fmt:formatDate value="${nowTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]
<input type="button" id="refreshLogButton" onclick="refreshLog()"  value="刷新">
</div> 
<table class="ui-grid-content ui-widget-content">
	<thead>
		<tr>
			<th class="ui-state-default">时间</th>
			<th class="ui-state-default">线程</th>
			<th class="ui-state-default">级别</th>
			<th class="ui-state-default">类名</th>
		</tr>
	</thead>
	<tbody>
	<c:if test="${!empty list}">
	<c:forEach items="${list}" var="map">
		<tr>
			<c:forEach items="${map}" var="log" varStatus="status">
				<c:if test="${status.index>3}">
				 </tr>
				 <tr>
				 <td colspan="5" class="ui-widget-content"><font color="#EA4A4F">${log.value}</font></td>
				 </tr>
				</c:if>
				<c:if test="${status.index<=3}">
				<td class="ui-widget-content">${log.value}</td>
				</c:if>
			</c:forEach>
	</c:forEach>
	</tbody>
	</c:if>
	<tfoot>
		
	</tfoot>
</table>
	<div class="ui-grid-footer ui-widget-header ui-corner-bottom ui-helper-clearfix"> 
			<div class="ui-grid-paging ui-helper-clearfix"> 
				<a href="#" class="ui-grid-paging-prev ui-state-default ui-corner-left"><span class="ui-icon ui-icon-triangle-1-w" title="previous set of results"></span></a> 
				<a href="#" class="ui-grid-paging-next ui-state-default ui-corner-right"><span class="ui-icon ui-icon-triangle-1-e" title="next set of results"></span></a> 
			</div> 
			<div class="ui-grid-results"><!--  Showing results 10-20--></div> 
		</div>
</div>
</body>
</html>
