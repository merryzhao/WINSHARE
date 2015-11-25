<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>应用状态管理</title>
<%@include file="../snippets/meta.jsp"%>
<style>
	table#tablesorter th {
		color: #333;
	}
</style>
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

				<h4>应用状态信息列表</h4>
				<!-- 查询表单div -->
					<c:if test="${pagination!=null}">
						${pagination}
					</c:if>
				<table id="tablesorter" class="tablesorter list-table">
					<tr>
						<th class="{sorter: false}">编号</th>
						<th>应用键</th>
						<th class="{sorter: false}">主机名称</th>
						<th class="{sorter: false}">应用名称</th>
						<th class="{sorter: false}">短消息发送次数</th>
						<th class="{sorter: false}">已发送短信次数</th>
						<th class="{sorter: false}">超时时间</th>
						<th class="{sorter: false}">更新时间</th>
						<th class="{sorter: false}">电话</th>
						<th class="{sorter: false}">操作</th>
 					</tr>
					<c:forEach items="${beats}" var="beat">
						<tr>
							<td>${beat.id}</td>						
							<td>${beat.appkey}</td>
							<td>${beat.hostname}</td>
							<td appkey="td_${beat.appkey}" hostname="td_${beat.hostname}" class="td_click_app"
								onclick="UpdateApp.tdClickEvent"><a
								href="javascript:void(0);">${beat.app}</a></td>
							<td appkey="td_${beat.appkey}" hostname="td_${beat.hostname}" class="td_click_threshold"
								onclick="UpdateThreshold.tdClickEvent"><a
								href="javascript:void(0);">${beat.threshold}</a></td>
							<c:if test="${!beat.available}">
								<td>0</td>
							</c:if>
							<c:if test="${beat.available}">
								<td>${beat.messagesend}</td>
							</c:if>
							<td appkey="td_${beat.appkey}" hostname="td_${beat.hostname}" class="td_click_timeout"
								onclick="UpdateTimeout.tdClickEvent"><a
								href="javascript:void(0);">${beat.timeout}</a></td>
							<td>
								<fmt:formatDate value="${beat.updatetime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td appkey="td_${beat.appkey}" hostname="td_${beat.hostname}" class="td_click_phone"
								onclick="UpdatePhone.tdClickEvent"><a
								href="javascript:void(0);">${beat.phone}</a></td>	
							<td>	                	
							<c:if test="${!beat.available}">
								<input class="active" type="button" value="启用" appkey="${beat.appkey}" hostname="${beat.hostname}" />
							</c:if>
							<c:if test="${beat.available}">
								<input class="block" type="button" value="禁用" appkey="${beat.appkey}" hostname="${beat.hostname}" />
							</c:if>
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
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.tablesorter.pager.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/heartbeat/beat_operate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/heartbeat/beat_updateall.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$(".list-table").tablesorter({
			headers:{
				0:{sorter:false},2:{sorter:false},
				3:{sorter:false},4:{sorter:false},
				5:{sorter:false},6:{sorter:false},
				7:{sorter:false},8:{sorter:false},
				9:{sorter:false}
			},
		});
	});
	</script>
</body>
</html>