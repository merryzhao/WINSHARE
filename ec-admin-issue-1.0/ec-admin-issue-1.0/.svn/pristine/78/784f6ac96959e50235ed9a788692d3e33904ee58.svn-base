<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>新建订单.拆单列表</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<table class="list-table">
					<tr>
						<th>订单号</th>
						<th>渠道</th>
						<th>支付类型</th>
						<th>支付状态</th>
						<th>储配方式</th>
						<th>供应类型</th>
						<th>出货DC</th>
					</tr>
					<c:forEach var="order" items="${orderList }">
						<tr>
							<td><a href="/order/${order.id }">${order.id }</a></td>
							<td>${order.channel.name }</td>
							<td>${order.payType.name }</td>
							<td>${order.processStatus.name }</td>
							<td>${order.storageType.name }</td>
							<td>${order.supplyType.name }</td>
							<td>${order.distributionCenter.dcDest.name }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
<%@include file="../snippets/scripts.jsp"%>
</body>
</html>
