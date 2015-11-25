<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Task List---Winxuan.Com</title>
<%@include file="../snippets/meta.jsp"%>
<link type="text/css" href="${pageContext.request.contextPath}/css/custom/jquery-ui-timepicker-addon.css" rel="stylesheet"/>
</head>
<body>
<div class="ui-grid ui-widget ui-widget-content ui-corner-all"> 
<div class="ui-grid-header ui-widget-header ui-corner-top">未添加执行计划的任务列表 [<fmt:formatDate value="${nowTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]</div> 
<table class="ui-grid-content ui-widget-content">
	<thead>
		<tr>
			<th class="ui-state-default">名称</th>
			<th class="ui-state-default">分组</th>
			<th class="ui-state-default">描述</th>
			<th class="ui-state-default">操作</th>
		</tr>
	</thead>
	<tbody>
	<c:if test="${!empty jobDetails}">
	<c:forEach items="${jobDetails}" var="jobDetail">
		<tr>
			<td class="ui-widget-content">${jobDetail.name}</td>
			<td class="ui-widget-content">${jobDetail.group}</td>
			<td class="ui-widget-content">${jobDetail.description}</td>
			<td class="ui-widget-content">
			<a href="javascript:;" onclick="addTrigger('${jobDetail.name}','${jobDetail.description}')">添 加</a> </td>
		</tr>
	</c:forEach>
	</tbody>
	</c:if>
	<tfoot></tfoot>
</table>
	<div class="ui-grid-footer ui-widget-header ui-corner-bottom ui-helper-clearfix"> 
			<div class="ui-grid-paging ui-helper-clearfix"> 
				<a href="#" class="ui-grid-paging-prev ui-state-default ui-corner-left"><span class="ui-icon ui-icon-triangle-1-w" title="previous set of results"></span></a> 
				<a href="#" class="ui-grid-paging-next ui-state-default ui-corner-right"><span class="ui-icon ui-icon-triangle-1-e" title="next set of results"></span></a> 
			</div> 
			<div class="ui-grid-results"><!--  Showing results 10-20--></div> 
		</div>
</div>
<div id="dialog-modal" title="添加任务运行时间">
	<div id="tabs-trigger">
	<ul>
		<li><a href="#tabs-trigger-1" >指定时间</a></li>
		<li><a href="#tabs-trigger-2" >时间间隔</a></li>
		<li><a href="#tabs-trigger-3" >Cron表达式</a></li>
	</ul>
	<form action="/task/addTrigger" method="post" name="addTriggerForm">
				<div id="tabs-trigger-1">
					<select name="dateTimeType">
						<option value="1">按天重复</option>
						<option value="2">运行一次</option>
					</select>
						<input name="datetime" size="20" readonly="readonly" class="datepicker"/>
						<br/>
				</div>
				<div id="tabs-trigger-2">
					<select name="appointType">
						<option value="1">指定的小时</option>
						<option value="2">指定的分钟</option>
					</select>
					<input name="appointStr" size="30" value="1,5,9,13,16,17,21"/>
					<br/>
				</div>
				<div id="tabs-trigger-3">
					<input name="cron" size="30" value="0/15 * * * * ?"/>
					<br/>
					<input type="hidden" name="taskName" value=""/>
					<input type="hidden" name="type" value=""/>
				</div>
		</form>
		</div>
	</div>
</body>
</html>
