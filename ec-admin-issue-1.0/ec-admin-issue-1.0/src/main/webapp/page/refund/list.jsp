<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan" %>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>退款</title>
</head>
<body>
    <!-- 引入JS -->
	<div class="frame">
	     <!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		 <!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
		    <!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<%@include file="index.jsp"%>
				<c:if test="${pagination!=null&&pagination.pageCount!=0}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<table class="list-table">
						<tr>
							<th>凭证ID</th>
							<th>支付方式</th>
							<th>外部ID</th>
							<th>退款金额</th>
							<th>创建时间</th>
							<th>退款时间</th>
							<th>退款状态</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${credentials }" var="credential">
						<tr>
							<td>
								<a href="/refund/detail?id=${credential.id }">${credential.id }</a>
							</td>
							<td>${credential.payment.name }</td>
							<%-- <td>${credential.customer.id }</td> --%>
							<td>${credential.outerId }</td>
							<%-- <td>${credential.order.id }</td> --%>
							<td>${credential.money }</td>
							<td>${credential.createTime }</td>
							<td>${credential.refundTime }</td>
							<td>
								<c:if test="${credential.status.id == 461013}">等待系统退款</c:if>
								<c:if test="${credential.status.id == 461014}">等待第三方退款</c:if>
								<c:if test="${credential.status.id == 461015}">退款成功</c:if>
								<c:if test="${credential.status.id == 461016}">退款失败</c:if>
								<c:if test="${credential.status.id == 461019}">退款作废</c:if>
							</td>
							<td style="cursor: pointer;">
								<a target="_blank" href="/refund/getRefundLog/${credential.id}">查看日志</a>
							</td>
						</tr>
						</c:forEach>
					</table>
					<c:if test="${pagination!=null&&pagination.pageCount!=0}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>