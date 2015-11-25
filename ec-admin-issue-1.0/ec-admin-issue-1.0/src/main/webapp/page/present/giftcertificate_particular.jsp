<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>礼券详细</title>
<!-- 引入JS CSS -->
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
table.gc_particular {margin-left: 40px;}
table.gc_particular tr {height: 30px;}
table.gc_particular .title {width: 80px;}
table.gc_particular .content {width: 150px;}

table.gc_status {margin-left: 30px;}
table.gc_status th {background: #6293BB;color: #fff;font-weight: bolder;text-align: center;}
table.gc_status tr {height: 30px;line-height: 20px;border: 1px solid #DFDFDF;text-align: center;}
table.gc_status td{border: 1px solid #DFDFDF;}
table.gc_status tr.hover {background: #ffffe1}
table.gc_status .time {width: 250px;}
table.gc_status .status {width: 100px;}
table.gc_status .number {width: 150px;}
</style>
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

				<h4>礼券详细</h4>
				<hr>
				<div>
					<table class="gc_particular">
						<colgroup>
							<col class="title" />
							<col class="content" />
							<col class="title" />
							<col class="content" />
						</colgroup>
						<tr>
							<td align="right">礼券编号：</td>
							<td>${present.id}</td>
							<td align="right">批次编号：</td>
							<td>${present.batch.id }</td>
						</tr>
						<tr>
							<td align="right">用户名：</td>
							<td>${present.customer.name}</td>
							<td align="right">激活码：</td>
							<td>${present.code}</td>
						</tr>
						<tr>
							<td align="right">面值：</td>
							<td>${present.value}</td>
							<td align="right">状态：</td>
							<td>${present.state.name}</td>
						</tr>
						<tr>
							<td align="right">开始时间：</td>
							<td><fmt:formatDate value="${present.startDate}" type="date" /></td>
							<td align="right">结束时间：</td>
							<td><fmt:formatDate value="${present.endDate}" type="date" /></td>
						</tr>
						<tr>
							<td align="right">关联订单：</td>
							<td>${present.order.id}</td>
							<td align="right">使用时间：</td>
							<td><fmt:formatDate value="${present.payTime}" type="both" /></td>
						</tr>
					</table>
				</div>
				<h4>礼券状态日志</h4>
				<hr>
				<div>
					<table class="gc_status">
						<colgroup>
							<col class="time" />
							<col class="status" />
							<col class="number" />
						</colgroup>
						<tr>
							<th>时间</th>
							<th>状态</th>
							<th>关联订单</th>
						</tr>
						<c:forEach var="presentLog" items="${present.logs}">
						<tr>
							<td><fmt:formatDate value="${presentLog.updateTime}" type="both" /></td>
							<td>${presentLog.state.name}</td>
							<td>${presentLog.order.id}</td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<div class="loading">数据处理中... 请稍候</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>