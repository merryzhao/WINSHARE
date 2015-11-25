<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>退款</title>
<style type="text/css" rel="stylesheet">
.order_table td {
	text-align: center;
	padding: 8px;
	border: #00BFFF 2px solid;
	BORDER-COLLAPSE: collapse;
}
.title{
	margin-top:3px;
	width: 98%;
	text-align: center;
	border: #00BFFF 2px solid;	
	padding: 8px;
}
.log-table {
	width: 100%;
	height: 440px;
	overflow: scroll;
	display: block;
	border: #00BFFF 2px solid;
}

.log-table tr {
	display: block;
}

.log-table tr td p {
	width: 200px;
}

.log-table tr td textarea {
	width: 900px;
	height: 110px;
	display: block;
}

.info {
	background-color: #00BFFF
}

.warn {
	background-color: #FFA54F
}

.error {
	background-color: #CD2626
}
</style>
</head>
<body>
	<!-- 引入JS -->
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<div>
				<div>
					<table class="order_table">
						<tr>
							<td colspan="12">原卡原退</td>
						</tr>
						<tr>
							<td class="info">批次号</td>
							<td>${refundCredential.id}</td>
							<td class="info">订单号</td>
							<td>${refundCredential.order.id}</td>
							<td class="info">交易号</td>
							<td>${refundCredential.outerId}</td>
							<td class="info">金额</td>
							<td>${refundCredential.money}</td>
							<td class="info">订单处理时间</td>
							<td>${refundCredential.order.lastProcessTime}</td>
						</tr>
						<tr>
							<td class="info">订单状态</td>
							<td>${refundCredential.order.processStatus.name}</td>
							<td class="info">支付状态</td>
							<td>${refundCredential.order.paymentStatus.name}</td>
							<td class="info">下单渠道</td>
							<td>${refundCredential.order.channel.name}</td>
							<td class="info">退款状态</td>
							<td>${refundCredential.status.name}</td>
							<td class="info">退款时间</td>
							<td>${refundCredential.createTime}</td>
						</tr>
						<c:if test="${fn:length(refundCredential.children)>0}">
							<c:forEach var="children" items="${refundCredential.children}">
								<tr>
									<td colspan="12">换批次号处理 ${children.createTime}</td>
								</tr>
								<tr>
									<td class="info">批次号</td>
									<td>${children.id}</td>
									<td class="info">订单号</td>
									<td>${children.order.id}</td>
									<td class="info">交易号</td>
									<td>${children.outerId}</td>
									<td class="info">金额</td>
									<td>${children.money}</td>
									<td class="info">退款状态</td>
									<td>${children.status.name}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
				<c:if test="${fn:length(refundLogs)>0}">
					<div class="title">退款日志</div>
					<table class="log-table">
						<c:forEach var="refundLog" items="${refundLogs}">
							<tr>
								<td
									class="<c:if test="${refundLog.type==1}">info</c:if><c:if test="${refundLog.type==0}">error</c:if><c:if test="${refundLog.type==2}">warn</c:if>">
									<textarea>${refundLog.message}</textarea>
								</td>
								<td>
									<p>
										执行时间：
										<fmt:formatDate value="${refundLog.createtime}"
											pattern="yyyy-MM-dd hh:mm" />
									</p>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
		</div>
		<%@include file="../snippets/scripts.jsp"%>
</body>
</html>