<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>会员管理</title>
<link type="text/css" href="${contextPath}/css/seller/seller_manage.css"
	rel="stylesheet" />
	<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="search">
				<!-- 查询条件部分 -->
				<form action="/customer/search" method="post">
					<p>
						<span>注册名:<textarea style="width: 200px;height: 60px;" id="name" name="name"></textarea> </span> 
						<span>会员类型:</span> 
						<select name="grade">
						        <option value="-1">请选择</option>
								<option value="0">普通会员</option>
								<option value="1">银卡用户</option>
								<option value="2">金卡用户</option>
						</select> 
						<span>会员来源：</span> 
						<select name="source">
						        <option value="-1">请选择</option>
						        <c:forEach var="source" items="${sources.children }">
						        <option value="${source.id }">${source.name }</option>
						        </c:forEach>
						</select> 
						<input id="customerChannelInput" type="hidden" name="channel" value="">
						<button id="customerChannel" type="button">会员渠道</button><label id="channelName"></label> 
						<button type="submit">检索</button>
					</p>
					<p>
						<span> <select name="timeSlot">
								<option value="registerTime">注册时间</option>
								<option value="lastLoginTime">最后登录时间</option>
								<option value="lastTradeTime">最后购买时间</option>
						</select> </span> <span><input name="timeslot" type="radio" value="day" />最近一天</span>
						<span><input name="timeslot" type="radio" value="week" />最近一周</span>
						<span><input name="timeslot" type="radio" value="month" />最近一月</span>
						<span>其他时间:<input type="text" id="starttime"
							name="startTime" bind="datepicker" />至<input type="text"
							id="endtime" name="endTime" bind="datepicker" />
						</span>
					</p>
				</form>
			</div>
			<br>
			<br>
			<!-- 查询结果 -->
			<div id="searchResult">
				<c:if test="${not empty customerList }">
					<c:if test="${not empty pagination}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<table class="list-table">
						<tr>
							<th>注册名</th>
							<th>真实姓名</th>
							<th>邮箱地址</th>
							<th>注册时间</th>
							<th>会员类型</th>
							<th>购买折扣</th>
							<th>最近成交时间</th>
							<th>积分</th>
							<th>状态</th>
						</tr>
						<c:forEach items="${customerList }" var="customer">
							<tr>
								<td><a href="/customer/${customer.id}/_info" >${customer.name }</a></td>
								<td>${customer.realName }</td>
								<td>${customer.email }</td>
								<td><fmt:formatDate value="${customer.registerTime }" type="date" /></td>
								<td><c:if test="${customer.grade==0 }">普通会员</c:if><c:if test="${customer.grade==1 }">银卡会员</c:if><c:if test="${customer.grade==2 }">金卡会员</c:if></td>
								<td></td>
								<td><c:if test="${empty customer.lastTradeTime }">暂无交易</c:if> <c:if test="${not empty customer.lastTradeTime }"><fmt:formatDate value="${customer.lastTradeTime }" type="both" /></c:if> </td>
								<td></td>
								<td><span id="isavailable${customer.id }"><c:if test="${customer.available }">启用</c:if><c:if test="${!customer.available }">禁用</c:if></span><a  onclick="changeState(${customer.id})" href="javascript:void(0);">(修改)</a></td>
							</tr>
						</c:forEach>
						<tr>
							<th>注册名</th>
							<th>真实姓名</th>
							<th>邮箱地址</th>
							<th>注册时间</th>
							<th>会员类型</th>
							<th>购买折扣</th>
							<th>最近成交时间</th>
							<th>积分</th>
							<th>状态</th>
						</tr>
					</table>
					<c:if test="${not empty pagination}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
		<div id="channelDiv">
			<ul id="channel_tree" class="tree"></ul>
			<br />
			<button type=button  id=getChecktree >确定</button>
		</div>
		<%@include file="../snippets/scripts.jsp"%>
	<script>
		$("input[name=timeslot]").click(function() {
			setTime($("input[name=timeslot]:checked").val());
		});
		$("#starttime").click(function() {
			$("input[name=timeslot]").attr("checked", false);
		});
		$("#endtime").click(function() {
			$("input[name=timeslot]").attr("checked", false);
		});
		function setTime(type) {
			var today = new Date();
			var start = null;
			var end = null;
			if (type == "day") {
				start = today.getFullYear() + "-" + (today.getMonth() + 1)
						+ "-" + (today.getDate() - 1);
				end = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
						+ today.getDate();
			}
			if (type == "week") {
				start = today.getFullYear() + "-" + (today.getMonth() + 1)
						+ "-" + (today.getDate() - 7);
				end = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
						+ today.getDate();
			}
			if (type == "month") {
				start = today.getFullYear() + "-" + today.getMonth() + "-"
						+ today.getDate();
				end = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
						+ today.getDate();
			}
			$("#starttime").val(start);
			$("#endtime").val(end);
		}
		function changeState(id){
			 var r=confirm("确认修改状态?");
			  if (r==true){
				  var url = '/customer/changeState/'+id+'?format=json';
					$.ajax({
						async : false,
						cache : false,
						type : 'GET',
						url : url,
						error : function() {
							
						},
						success : function(data) { 
							if(data.result==1){
								$("#isavailable"+id).html(data.message);
							}
						}
					});
			  }
		}
		$("#channelDiv").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true,
			width : 350
		});
		$("#customerChannel").click(function(){
			$("#channelDiv").dialog("open");
		});
		$("#getChecktree").click(function(){
			var node = zTree.getCheckedNodes();
			$("#channelName").text(node[0].name);
			$("#customerChannelInput").val(node[0].id);
			$("#channelDiv").dialog("close");
		});
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/tree/channel_tree2.js"></script>
</body>
</html>
