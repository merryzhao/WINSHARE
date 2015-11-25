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
</head>
<body>
<div class="ui-grid ui-widget ui-widget-content ui-corner-all"> 
<div class="ui-grid-header ui-widget-header ui-corner-top">已添加计划任务列表 [<fmt:formatDate value="${nowTime}" pattern="yyyy-MM-dd HH:mm:ss"/>]
				<input type="button" id="refreshTriggerButton" onclick="refreshTrigger()"  value="刷新">
</div> 
<table class="ui-grid-content ui-widget-content">
	<thead>
		<tr>
			<th class="ui-state-default">名称</th>
			<th class="ui-state-default">分组</th>
			<th class="ui-state-default">状态</th>
			<th class="ui-state-default">上次时间</th>
			<th class="ui-state-default">下次时间</th>
			<th class="ui-state-default">操作</th>
		</tr>
	</thead>
	<tbody>
	<c:if test="${!empty taskTrigger}">
	<c:forEach items="${taskTrigger}" var="trigger" varStatus="status">
			<c:set var="pp" value="${status.index % 2 == 0 ? 'gg' : 'oo'}" />
			<tr >
				<td colspan="7" class="${pp}"><center>${trigger.description}</center></td>
			</tr>
			<tr >
				<td class="${pp}">${trigger.triggerName}</td>
				<td class="${pp}">${trigger.triggerGroup}</td>
				<td class="${pp}">${trigger.statusName}</td>
				<td class="${pp}"><fmt:formatDate value="${trigger.prevFireDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="${pp}"><fmt:formatDate value="${trigger.nextFireDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="${pp}">
					<a onclick="pauseTrigger('${trigger.triggerName}')" href="javascript:;">暂停</a> | 
					<a onclick="resumeTrigger('${trigger.triggerName}')" href="javascript:;">恢复</a> |
					<a  onclick="clearTrigger('${trigger.triggerName}')" href="javascript:;">删除</a>
				</td>
			</tr>
	</c:forEach>
	</c:if>
	</tbody>
	<tfoot>
	<tr>
			<td colspan="7" class="ui-state-default">
			</td>
		</tr>
		
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
